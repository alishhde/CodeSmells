public interface MetricsIndexerSource extends BaseSource {
 // Metrics2 and JMX constants
 String METRICS_NAME = "PhoenixIndexer";
 String METRICS_CONTEXT = "phoenix";
 String METRICS_DESCRIPTION = "Metrics about the Phoenix Indexer";
 String METRICS_JMX_CONTEXT = "RegionServer,sub=" + METRICS_NAME;


 String INDEX_PREPARE_TIME = "indexPrepareTime";
 String INDEX_PREPARE_TIME_DESC = "Histogram for the time in milliseconds for preparing an index write";
 String SLOW_INDEX_PREPARE = "slowIndexPrepareCalls";
 String SLOW_INDEX_PREPARE_DESC = "The number of index preparations slower than the configured threshold";


 String INDEX_WRITE_TIME = "indexWriteTime";
 String INDEX_WRITE_TIME_DESC = "Histogram for the time in milliseconds for writing an index update";
 String SLOW_INDEX_WRITE = "slowIndexWriteCalls";
 String SLOW_INDEX_WRITE_DESC = "The number of index writes slower than the configured threshold";


 String DUPLICATE_KEY_TIME = "duplicateKeyCheckTime";
 String DUPLICATE_KEY_TIME_DESC = "Histogram for the time in milliseconds to handle ON DUPLICATE keywords";
 String SLOW_DUPLICATE_KEY = "slowDuplicateKeyCheckCalls";
 String SLOW_DUPLICATE_KEY_DESC = "The number of on duplicate key checks slower than the configured threshold";


 String PRE_WAL_RESTORE_TIME = "preWALRestoreTime";
 String PRE_WAL_RESTORE_TIME_DESC = "Histogram for the time in milliseconds for Indexer's preWALRestore";
 String SLOW_PRE_WAL_RESTORE = "slowPreWALRestoreCalls";
 String SLOW_PRE_WAL_RESTORE_DESC = "The number of preWALRestore calls slower than the configured threshold";


 String POST_PUT_TIME = "postPutTime";
 String POST_PUT_TIME_DESC = "Histogram for the time in milliseconds for Indexer's postPut";
 String SLOW_POST_PUT = "slowPostPutCalls";
 String SLOW_POST_PUT_DESC = "The number of postPut calls slower than the configured threshold";


 String POST_DELETE_TIME = "postDeleteTime";
 String POST_DELETE_TIME_DESC = "Histogram for the time in milliseconds for Indexer's postDelete";
 String SLOW_POST_DELETE = "slowPostDeleteCalls";
 String SLOW_POST_DELETE_DESC = "The number of postDelete calls slower than the configured threshold";


 String POST_OPEN_TIME = "postOpenTime";
 String POST_OPEN_TIME_DESC = "Histogram for the time in milliseconds for Indexer's postOpen";
 String SLOW_POST_OPEN = "slowPostOpenCalls";
 String SLOW_POST_OPEN_DESC = "The number of postOpen calls slower than the configured threshold";


 /**
   * Updates the index preparation time histogram (preBatchMutate).
   *
   * @param t time taken in milliseconds
   */
 void updateIndexPrepareTime(long t);


 /**
   * Increments the number of slow calls prepare an index write.
   */
 void incrementNumSlowIndexPrepareCalls();


 /**
   * Updates the index write time histogram (postBatchMutate).
   *
   * @param t time taken in milliseconds
   */
 void updateIndexWriteTime(long t);


 /**
   * Increments the number of slow calls to write to the index.
   */
 void incrementNumSlowIndexWriteCalls();


 /**
   * Updates the preWALRestore time histogram.
   *
   * @param t time taken in milliseconds
   */
 void updatePreWALRestoreTime(long t);


 /**
   * Increments the number of slow preWALRestore calls.
   */
 void incrementNumSlowPreWALRestoreCalls();


 /**
   * Updates the postPut time histogram.
   *
   * @param t time taken in milliseconds
   */
 void updatePostPutTime(long t);


 /**
   * Increments the number of slow postPut calls.
   */
 void incrementNumSlowPostPutCalls();


 /**
   * Updates the postDelete time histogram.
   *
   * @param t time taken in milliseconds
   */
 void updatePostDeleteTime(long t);


 /**
   * Increments the number of slow postDelete calls.
   */
 void incrementNumSlowPostDeleteCalls();


 /**
   * Updates the postOpen time histogram.
   *
   * @param t time taken in milliseconds
   */
 void updatePostOpenTime(long t);


 /**
   * Increments the number of slow postOpen calls.
   */
 void incrementNumSlowPostOpenCalls();


 /**
   * Updates the preIncrementAfterRowLock time histogram.
   *
   * @param t time taken in milliseconds
   */
 void updateDuplicateKeyCheckTime(long t);


 /**
   * Increments the number of slow preIncrementAfteRowLock calls.
   */
 void incrementSlowDuplicateKeyCheckCalls();
}