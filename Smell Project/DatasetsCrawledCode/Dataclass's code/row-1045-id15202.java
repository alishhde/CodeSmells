@SuppressWarnings("unchecked")
public class BoltExecutorStats extends CommonStats {
 MultiCountStatAndMetric executedStats;
 MultiLatencyStatAndMetric processLatencyStats;
 MultiLatencyStatAndMetric executeLatencyStats;


 public BoltExecutorStats(int rate, int numStatBuckets) {
 super(rate, numStatBuckets);
 this.executedStats = new MultiCountStatAndMetric(numStatBuckets);
 this.processLatencyStats = new MultiLatencyStatAndMetric(numStatBuckets);
 this.executeLatencyStats = new MultiLatencyStatAndMetric(numStatBuckets);
    }


 public MultiCountStatAndMetric getExecuted() {
 return executedStats;
    }


 public MultiLatencyStatAndMetric getProcessLatencies() {
 return processLatencyStats;
    }


 public MultiLatencyStatAndMetric getExecuteLatencies() {
 return executeLatencyStats;
    }


 @Override
 public void cleanupStats() {
 executedStats.close();
 processLatencyStats.close();
 executeLatencyStats.close();
 super.cleanupStats();
    }


 public void boltExecuteTuple(String component, String stream, long latencyMs) {
 List key = Lists.newArrayList(component, stream);
 this.getExecuted().incBy(key, this.rate);
 this.getExecuteLatencies().record(key, latencyMs);
    }


 public void boltAckedTuple(String component, String stream, long latencyMs, Counter ackedCounter) {
 List key = Lists.newArrayList(component, stream);
 this.getAcked().incBy(key, this.rate);
 ackedCounter.inc(this.rate);
 this.getProcessLatencies().record(key, latencyMs);
    }


 public void boltFailedTuple(String component, String stream, long latencyMs, Counter failedCounter) {
 List key = Lists.newArrayList(component, stream);
 this.getFailed().incBy(key, this.rate);
 failedCounter.inc(this.rate);
    }


 @Override
 public ExecutorStats renderStats() {
 ExecutorStats ret = new ExecutorStats();
 // common stats
 ret.set_emitted(valueStat(getEmitted()));
 ret.set_transferred(valueStat(getTransferred()));
 ret.set_rate(this.rate);


 // bolt stats
 BoltStats boltStats = new BoltStats(
 ClientStatsUtil.windowSetConverter(valueStat(getAcked()), ClientStatsUtil.TO_GSID, ClientStatsUtil.IDENTITY),
 ClientStatsUtil.windowSetConverter(valueStat(getFailed()), ClientStatsUtil.TO_GSID, ClientStatsUtil.IDENTITY),
 ClientStatsUtil.windowSetConverter(valueStat(processLatencyStats), ClientStatsUtil.TO_GSID, ClientStatsUtil.IDENTITY),
 ClientStatsUtil.windowSetConverter(valueStat(executedStats), ClientStatsUtil.TO_GSID, ClientStatsUtil.IDENTITY),
 ClientStatsUtil.windowSetConverter(valueStat(executeLatencyStats), ClientStatsUtil.TO_GSID, ClientStatsUtil.IDENTITY));
 ret.set_specific(ExecutorSpecificStats.bolt(boltStats));


 return ret;
    }
}