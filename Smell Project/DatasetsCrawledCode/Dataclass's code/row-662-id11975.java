 @InterfaceAudience.LimitedPrivate({"MapReduce"})
 @InterfaceStability.Unstable
 public static class Context {
 private final MapTask mapTask;
 private final JobConf jobConf;
 private final TaskReporter reporter;


 public Context(MapTask mapTask, JobConf jobConf, TaskReporter reporter) {
 this.mapTask = mapTask;
 this.jobConf = jobConf;
 this.reporter = reporter;
    }


 public MapTask getMapTask() {
 return mapTask;
    }


 public JobConf getJobConf() {
 return jobConf;
    }


 public TaskReporter getReporter() {
 return reporter;
    }
  }