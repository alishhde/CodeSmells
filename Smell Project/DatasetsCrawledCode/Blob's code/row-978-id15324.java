public class TimestampTracker implements AutoCloseable {


 private static final Logger log = LoggerFactory.getLogger(TimestampTracker.class);
 private volatile long zkTimestamp = -1;
 private final Environment env;
 private final SortedSet<Long> timestamps = new TreeSet<>();
 private volatile PersistentNode node = null;
 private final TransactorID tid;
 private final Timer timer;


 private boolean closed = false;
 private int allocationsInProgress = 0;
 private boolean updatingZk = false;


 public TimestampTracker(Environment env, TransactorID tid, long updatePeriodMs) {
 Objects.requireNonNull(env, "environment cannot be null");
 Objects.requireNonNull(tid, "tid cannot be null");
 Preconditions.checkArgument(updatePeriodMs > 0, "update period must be positive");
 this.env = env;
 this.tid = tid;


 TimerTask tt = new TimerTask() {


 private int sawZeroCount = 0;


 @Override
 public void run() {
 try {
 long ts = 0;


 synchronized (TimestampTracker.this) {
 if (closed) {
 return;
            }


 if (allocationsInProgress > 0) {
 sawZeroCount = 0;
 if (!timestamps.isEmpty()) {
 if (updatingZk) {
 throw new IllegalStateException("expected updatingZk to be false");
                }
 ts = timestamps.first();
 updatingZk = true;
              }
            } else if (allocationsInProgress == 0) {
 sawZeroCount++;
 if (sawZeroCount >= 2) {
 sawZeroCount = 0;
 closeZkNode();
              }
            } else {
 throw new IllegalStateException("allocationsInProgress = " + allocationsInProgress);
            }


          }


 // update can be done outside of sync block as timer has one thread and future
 // executions of run method will block until this method returns
 if (updatingZk) {
 try {
 updateZkNode(ts);
            } finally {
 synchronized (TimestampTracker.this) {
 updatingZk = false;
              }
            }
          }
        } catch (Exception e) {
 log.error("Exception occurred in Zookeeper update thread", e);
        }
      }
    };
 timer = new Timer("TimestampTracker timer", true);
 timer.schedule(tt, updatePeriodMs, updatePeriodMs);
  }


 public TimestampTracker(Environment env, TransactorID tid) {
 this(env, tid, env.getConfiguration().getLong(FluoConfigurationImpl.ZK_UPDATE_PERIOD_PROP,
 FluoConfigurationImpl.ZK_UPDATE_PERIOD_MS_DEFAULT));
  }


 /**
   * Allocate a timestamp
   */
 public Stamp allocateTimestamp() {


 synchronized (this) {
 Preconditions.checkState(!closed, "tracker closed ");


 if (node == null) {
 Preconditions.checkState(allocationsInProgress == 0,
 "expected allocationsInProgress == 0 when node == null");
 Preconditions.checkState(!updatingZk, "unexpected concurrent ZK update");


 createZkNode(getTimestamp().getTxTimestamp());
      }


 allocationsInProgress++;
    }


 try {
 Stamp ts = getTimestamp();


 synchronized (this) {
 timestamps.add(ts.getTxTimestamp());
      }


 return ts;
    } catch (RuntimeException re) {
 synchronized (this) {
 allocationsInProgress--;
      }
 throw re;
    }
  }


 /**
   * Remove a timestamp (of completed transaction)
   */
 public synchronized void removeTimestamp(long ts) throws NoSuchElementException {
 Preconditions.checkState(!closed, "tracker closed ");
 Preconditions.checkState(allocationsInProgress > 0,
 "allocationsInProgress should be > 0 " + allocationsInProgress);
 Objects.requireNonNull(node);
 if (timestamps.remove(ts) == false) {
 throw new NoSuchElementException(
 "Timestamp " + ts + " was previously removed or does not exist");
    }


 allocationsInProgress--;
  }


 private Stamp getTimestamp() {
 return env.getSharedResources().getOracleClient().getStamp();
  }


 private void createZkNode(long ts) {
 Preconditions.checkState(node == null, "expected node to be null");
 node = new PersistentNode(env.getSharedResources().getCurator(), CreateMode.EPHEMERAL, false,
 getNodePath(), LongUtil.toByteArray(ts));
 CuratorUtil.startAndWait(node, 10);
 zkTimestamp = ts;
  }


 private void closeZkNode() {
 try {
 if (node != null) {
 node.close();
 node = null;
      }
    } catch (IOException e) {
 log.error("Failed to close timestamp tracker ephemeral node");
 throw new IllegalStateException(e);
    }
  }


 private void updateZkNode(long ts) {
 if (ts != zkTimestamp) {
 try {
 node.setData(LongUtil.toByteArray(ts));
      } catch (Exception e) {
 throw new IllegalStateException(e);
      }
    }
 zkTimestamp = ts;
  }


 @VisibleForTesting
 public synchronized void updateZkNode() {
 Preconditions.checkState(!updatingZk, "unexpected concurrent ZK update");


 if (allocationsInProgress > 0) {
 if (!timestamps.isEmpty()) {
 updateZkNode(timestamps.first());
      }
    } else if (allocationsInProgress == 0) {
 closeZkNode();
    } else {
 throw new IllegalStateException("allocationsInProgress = " + allocationsInProgress);
    }
  }


 @VisibleForTesting
 public long getOldestActiveTimestamp() {
 return timestamps.first();
  }


 @VisibleForTesting
 public long getZookeeperTimestamp() {
 return zkTimestamp;
  }


 @VisibleForTesting
 public boolean isEmpty() {
 return timestamps.isEmpty();
  }


 @VisibleForTesting
 public String getNodePath() {
 return ZookeeperPath.TRANSACTOR_TIMESTAMPS + "/" + tid;
  }


 @Override
 public synchronized void close() {
 Preconditions.checkState(!closed, "tracker already closed");
 closed = true;
 timer.cancel();
 closeZkNode();
  }
}