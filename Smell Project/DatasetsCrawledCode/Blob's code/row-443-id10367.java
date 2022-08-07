 private class ClientSelectDeleteMutationPlan implements MutationPlan {
 private final StatementContext context;
 private final TableRef targetTableRef;
 private final QueryPlan dataPlan;
 private final QueryPlan bestPlan;
 private final boolean hasPreOrPostProcessing;
 private final DeletingParallelIteratorFactory parallelIteratorFactory;
 private final List<TableRef> otherTableRefs;
 private final TableRef projectedTableRef;
 private final int maxSize;
 private final int maxSizeBytes;
 private final PhoenixConnection connection;


 public ClientSelectDeleteMutationPlan(TableRef targetTableRef, QueryPlan dataPlan, QueryPlan bestPlan,
 boolean hasPreOrPostProcessing,
 DeletingParallelIteratorFactory parallelIteratorFactory,
 List<TableRef> otherTableRefs, TableRef projectedTableRef, int maxSize,
 int maxSizeBytes, PhoenixConnection connection) {
 this.context = bestPlan.getContext();
 this.targetTableRef = targetTableRef;
 this.dataPlan = dataPlan;
 this.bestPlan = bestPlan;
 this.hasPreOrPostProcessing = hasPreOrPostProcessing;
 this.parallelIteratorFactory = parallelIteratorFactory;
 this.otherTableRefs = otherTableRefs;
 this.projectedTableRef = projectedTableRef;
 this.maxSize = maxSize;
 this.maxSizeBytes = maxSizeBytes;
 this.connection = connection;
        }


 @Override
 public ParameterMetaData getParameterMetaData() {
 return context.getBindManager().getParameterMetaData();
        }


 @Override
 public StatementContext getContext() {
 return context;
        }


 @Override
 public TableRef getTargetRef() {
 return targetTableRef;
        }


 @Override
 public Set<TableRef> getSourceRefs() {
 return dataPlan.getSourceRefs();
        }


 @Override
 public Operation getOperation() {
 return operation;
        }


 @Override
 public MutationState execute() throws SQLException {
 ResultIterator iterator = bestPlan.iterator();
 try {
 // If we're not doing any pre or post processing, we can produce the delete mutations directly
 // in the parallel threads executed for the scan
 if (!hasPreOrPostProcessing) {
 Tuple tuple;
 long totalRowCount = 0;
 if (parallelIteratorFactory != null) {
 parallelIteratorFactory.setQueryPlan(bestPlan);
 parallelIteratorFactory.setOtherTableRefs(otherTableRefs);
 parallelIteratorFactory.setProjectedTableRef(projectedTableRef);
                    }
 while ((tuple=iterator.next()) != null) {// Runs query
 Cell kv = tuple.getValue(0);
 totalRowCount += PLong.INSTANCE.getCodec().decodeLong(kv.getValueArray(), kv.getValueOffset(), SortOrder.getDefault());
                    }
 // Return total number of rows that have been deleted from the table. In the case of auto commit being off
 // the mutations will all be in the mutation state of the current connection. We need to divide by the
 // total number of tables we updated as otherwise the client will get an inflated result.
 int totalTablesUpdateClientSide = 1; // data table is always updated
 PTable bestTable = bestPlan.getTableRef().getTable();
 // global immutable tables are also updated client side (but don't double count the data table)
 if (bestPlan != dataPlan && isMaintainedOnClient(bestTable)) {
 totalTablesUpdateClientSide++;
                    }
 for (TableRef otherTableRef : otherTableRefs) {
 PTable otherTable = otherTableRef.getTable();
 // Don't double count the data table here (which morphs when it becomes a projected table, hence this check)
 if (projectedTableRef != otherTableRef && isMaintainedOnClient(otherTable)) {
 totalTablesUpdateClientSide++;
                        }
                    }
 MutationState state = new MutationState(maxSize, maxSizeBytes, connection, totalRowCount/totalTablesUpdateClientSide);


 // set the read metrics accumulated in the parent context so that it can be published when the mutations are committed.
 state.setReadMetricQueue(context.getReadMetricsQueue());


 return state;
                } else {
 // Otherwise, we have to execute the query and produce the delete mutations in the single thread
 // producing the query results.
 return deleteRows(context, iterator, bestPlan, projectedTableRef, otherTableRefs);
                }
            } finally {
 iterator.close();
            }
        }


 @Override
 public ExplainPlan getExplainPlan() throws SQLException {
 List<String> queryPlanSteps =  bestPlan.getExplainPlan().getPlanSteps();
 List<String> planSteps = Lists.newArrayListWithExpectedSize(queryPlanSteps.size()+1);
 planSteps.add("DELETE ROWS");
 planSteps.addAll(queryPlanSteps);
 return new ExplainPlan(planSteps);
        }


 @Override
 public Long getEstimatedRowsToScan() throws SQLException {
 return bestPlan.getEstimatedRowsToScan();
        }


 @Override
 public Long getEstimatedBytesToScan() throws SQLException {
 return bestPlan.getEstimatedBytesToScan();
        }


 @Override
 public Long getEstimateInfoTimestamp() throws SQLException {
 return bestPlan.getEstimateInfoTimestamp();
        }


 @Override
 public QueryPlan getQueryPlan() {
 return bestPlan;
        }
    }