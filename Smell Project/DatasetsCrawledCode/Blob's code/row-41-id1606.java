public class GroupByMergingQueryRunnerV2 implements QueryRunner<Row>
{
 private static final Logger log = new Logger(GroupByMergingQueryRunnerV2.class);
 private static final String CTX_KEY_MERGE_RUNNERS_USING_CHAINED_EXECUTION = "mergeRunnersUsingChainedExecution";


 private final GroupByQueryConfig config;
 private final Iterable<QueryRunner<Row>> queryables;
 private final ListeningExecutorService exec;
 private final QueryWatcher queryWatcher;
 private final int concurrencyHint;
 private final BlockingPool<ByteBuffer> mergeBufferPool;
 private final ObjectMapper spillMapper;
 private final String processingTmpDir;
 private final int mergeBufferSize;


 public GroupByMergingQueryRunnerV2(
 GroupByQueryConfig config,
 ExecutorService exec,
 QueryWatcher queryWatcher,
 Iterable<QueryRunner<Row>> queryables,
 int concurrencyHint,
 BlockingPool<ByteBuffer> mergeBufferPool,
 int mergeBufferSize,
 ObjectMapper spillMapper,
 String processingTmpDir
  )
  {
 this.config = config;
 this.exec = MoreExecutors.listeningDecorator(exec);
 this.queryWatcher = queryWatcher;
 this.queryables = Iterables.unmodifiableIterable(Iterables.filter(queryables, Predicates.notNull()));
 this.concurrencyHint = concurrencyHint;
 this.mergeBufferPool = mergeBufferPool;
 this.spillMapper = spillMapper;
 this.processingTmpDir = processingTmpDir;
 this.mergeBufferSize = mergeBufferSize;
  }


 @Override
 public Sequence<Row> run(final QueryPlus<Row> queryPlus, final Map<String, Object> responseContext)
  {
 final GroupByQuery query = (GroupByQuery) queryPlus.getQuery();
 final GroupByQueryConfig querySpecificConfig = config.withOverrides(query);


 // CTX_KEY_MERGE_RUNNERS_USING_CHAINED_EXECUTION is here because realtime servers use nested mergeRunners calls
 // (one for the entire query and one for each sink). We only want the outer call to actually do merging with a
 // merge buffer, otherwise the query will allocate too many merge buffers. This is potentially sub-optimal as it
 // will involve materializing the results for each sink before starting to feed them into the outer merge buffer.
 // I'm not sure of a better way to do this without tweaking how realtime servers do queries.
 final boolean forceChainedExecution = query.getContextBoolean(
 CTX_KEY_MERGE_RUNNERS_USING_CHAINED_EXECUTION,
 false
    );
 final QueryPlus<Row> queryPlusForRunners = queryPlus
        .withQuery(
 query.withOverriddenContext(ImmutableMap.of(CTX_KEY_MERGE_RUNNERS_USING_CHAINED_EXECUTION, true))
        )
        .withoutThreadUnsafeState();


 if (QueryContexts.isBySegment(query) || forceChainedExecution) {
 ChainedExecutionQueryRunner<Row> runner = new ChainedExecutionQueryRunner<>(exec, queryWatcher, queryables);
 return runner.run(queryPlusForRunners, responseContext);
    }


 final boolean isSingleThreaded = querySpecificConfig.isSingleThreaded();


 final AggregatorFactory[] combiningAggregatorFactories = new AggregatorFactory[query.getAggregatorSpecs().size()];
 for (int i = 0; i < query.getAggregatorSpecs().size(); i++) {
 combiningAggregatorFactories[i] = query.getAggregatorSpecs().get(i).getCombiningFactory();
    }


 final File temporaryStorageDirectory = new File(
 processingTmpDir,
 StringUtils.format("druid-groupBy-%s_%s", UUID.randomUUID(), query.getId())
    );


 final int priority = QueryContexts.getPriority(query);


 // Figure out timeoutAt time now, so we can apply the timeout to both the mergeBufferPool.take and the actual
 // query processing together.
 final long queryTimeout = QueryContexts.getTimeout(query);
 final boolean hasTimeout = QueryContexts.hasTimeout(query);
 final long timeoutAt = System.currentTimeMillis() + queryTimeout;


 return new BaseSequence<>(
 new BaseSequence.IteratorMaker<Row, CloseableGrouperIterator<RowBasedKey, Row>>()
        {
 @Override
 public CloseableGrouperIterator<RowBasedKey, Row> make()
          {
 final List<ReferenceCountingResourceHolder> resources = new ArrayList<>();


 try {
 final LimitedTemporaryStorage temporaryStorage = new LimitedTemporaryStorage(
 temporaryStorageDirectory,
 querySpecificConfig.getMaxOnDiskStorage()
              );
 final ReferenceCountingResourceHolder<LimitedTemporaryStorage> temporaryStorageHolder =
 ReferenceCountingResourceHolder.fromCloseable(temporaryStorage);
 resources.add(temporaryStorageHolder);


 // If parallelCombine is enabled, we need two merge buffers for parallel aggregating and parallel combining
 final int numMergeBuffers = querySpecificConfig.getNumParallelCombineThreads() > 1 ? 2 : 1;


 final List<ReferenceCountingResourceHolder<ByteBuffer>> mergeBufferHolders = getMergeBuffersHolder(
 numMergeBuffers,
 hasTimeout,
 timeoutAt
              );
 resources.addAll(mergeBufferHolders);


 final ReferenceCountingResourceHolder<ByteBuffer> mergeBufferHolder = mergeBufferHolders.get(0);
 final ReferenceCountingResourceHolder<ByteBuffer> combineBufferHolder = numMergeBuffers == 2 ?
 mergeBufferHolders.get(1) :
 null;


 Pair<Grouper<RowBasedKey>, Accumulator<AggregateResult, Row>> pair =
 RowBasedGrouperHelper.createGrouperAccumulatorPair(
 query,
 false,
 null,
 config,
 Suppliers.ofInstance(mergeBufferHolder.get()),
 combineBufferHolder,
 concurrencyHint,
 temporaryStorage,
 spillMapper,
 combiningAggregatorFactories,
 exec,
 priority,
 hasTimeout,
 timeoutAt,
 mergeBufferSize
                  );
 final Grouper<RowBasedKey> grouper = pair.lhs;
 final Accumulator<AggregateResult, Row> accumulator = pair.rhs;
 grouper.init();


 final ReferenceCountingResourceHolder<Grouper<RowBasedKey>> grouperHolder =
 ReferenceCountingResourceHolder.fromCloseable(grouper);
 resources.add(grouperHolder);


 ListenableFuture<List<AggregateResult>> futures = Futures.allAsList(
 Lists.newArrayList(
 Iterables.transform(
 queryables,
 new Function<QueryRunner<Row>, ListenableFuture<AggregateResult>>()
                          {
 @Override
 public ListenableFuture<AggregateResult> apply(final QueryRunner<Row> input)
                            {
 if (input == null) {
 throw new ISE(
 "Null queryRunner! Looks to be some segment unmapping action happening"
                                );
                              }


 ListenableFuture<AggregateResult> future = exec.submit(
 new AbstractPrioritizedCallable<AggregateResult>(priority)
                                  {
 @Override
 public AggregateResult call()
                                    {
 try (
 // These variables are used to close releasers automatically.
 @SuppressWarnings("unused")
 Releaser bufferReleaser = mergeBufferHolder.increment();
 @SuppressWarnings("unused")
 Releaser grouperReleaser = grouperHolder.increment()
                                      ) {
 final AggregateResult retVal = input.run(queryPlusForRunners, responseContext)
                                                                            .accumulate(
 AggregateResult.ok(),
 accumulator
                                                                            );


 // Return true if OK, false if resources were exhausted.
 return retVal;
                                      }
 catch (QueryInterruptedException e) {
 throw e;
                                      }
 catch (Exception e) {
 log.error(e, "Exception with one of the sequences!");
 throw new RuntimeException(e);
                                      }
                                    }
                                  }
                              );


 if (isSingleThreaded) {
 waitForFutureCompletion(
 query,
 Futures.allAsList(ImmutableList.of(future)),
 hasTimeout,
 timeoutAt - System.currentTimeMillis()
                                );
                              }


 return future;
                            }
                          }
                      )
                  )
              );


 if (!isSingleThreaded) {
 waitForFutureCompletion(query, futures, hasTimeout, timeoutAt - System.currentTimeMillis());
              }


 return RowBasedGrouperHelper.makeGrouperIterator(
 grouper,
 query,
 new Closeable()
                  {
 @Override
 public void close()
                    {
 for (Closeable closeable : Lists.reverse(resources)) {
 CloseQuietly.close(closeable);
                      }
                    }
                  }
              );
            }
 catch (Throwable e) {
 // Exception caught while setting up the iterator; release resources.
 for (Closeable closeable : Lists.reverse(resources)) {
 CloseQuietly.close(closeable);
              }
 throw e;
            }
          }


 @Override
 public void cleanup(CloseableGrouperIterator<RowBasedKey, Row> iterFromMake)
          {
 iterFromMake.close();
          }
        }
    );
  }


 private List<ReferenceCountingResourceHolder<ByteBuffer>> getMergeBuffersHolder(
 int numBuffers,
 boolean hasTimeout,
 long timeoutAt
  )
  {
 try {
 if (numBuffers > mergeBufferPool.maxSize()) {
 throw new ResourceLimitExceededException(
 "Query needs " + numBuffers + " merge buffers, but only "
            + mergeBufferPool.maxSize() + " merge buffers were configured. "
            + "Try raising druid.processing.numMergeBuffers."
        );
      }
 final List<ReferenceCountingResourceHolder<ByteBuffer>> mergeBufferHolder;
 // This will potentially block if there are no merge buffers left in the pool.
 if (hasTimeout) {
 final long timeout = timeoutAt - System.currentTimeMillis();
 if (timeout <= 0) {
 throw new TimeoutException();
        }
 if ((mergeBufferHolder = mergeBufferPool.takeBatch(numBuffers, timeout)).isEmpty()) {
 throw new TimeoutException("Cannot acquire enough merge buffers");
        }
      } else {
 mergeBufferHolder = mergeBufferPool.takeBatch(numBuffers);
      }
 return mergeBufferHolder;
    }
 catch (Exception e) {
 throw new QueryInterruptedException(e);
    }
  }


 private void waitForFutureCompletion(
 GroupByQuery query,
 ListenableFuture<List<AggregateResult>> future,
 boolean hasTimeout,
 long timeout
  )
  {
 try {
 if (queryWatcher != null) {
 queryWatcher.registerQuery(query, future);
      }


 if (hasTimeout && timeout <= 0) {
 throw new TimeoutException();
      }


 final List<AggregateResult> results = hasTimeout ? future.get(timeout, TimeUnit.MILLISECONDS) : future.get();


 for (AggregateResult result : results) {
 if (!result.isOk()) {
 future.cancel(true);
 throw new ResourceLimitExceededException(result.getReason());
        }
      }
    }
 catch (InterruptedException e) {
 log.warn(e, "Query interrupted, cancelling pending results, query id [%s]", query.getId());
 future.cancel(true);
 throw new QueryInterruptedException(e);
    }
 catch (CancellationException e) {
 throw new QueryInterruptedException(e);
    }
 catch (TimeoutException e) {
 log.info("Query timeout, cancelling pending results for query id [%s]", query.getId());
 future.cancel(true);
 throw new QueryInterruptedException(e);
    }
 catch (ExecutionException e) {
 throw new RuntimeException(e);
    }
  }


}