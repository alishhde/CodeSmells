public final class AtlasPerfTracer {
 protected final Logger logger;
 protected final String tag;
 private final long startTimeMs;


 private static long reportingThresholdMs = 0L;


 public static Logger getPerfLogger(String name) {
 return LoggerFactory.getLogger("org.apache.atlas.perf." + name);
    }


 public static Logger getPerfLogger(Class<?> cls) {
 return AtlasPerfTracer.getPerfLogger(cls.getName());
    }


 public static boolean isPerfTraceEnabled(Logger logger) {
 return logger.isDebugEnabled();
    }


 public static AtlasPerfTracer getPerfTracer(Logger logger, String tag) {
 return new AtlasPerfTracer(logger, tag);
    }


 public static void log(AtlasPerfTracer tracer) {
 if (tracer != null) {
 tracer.log();
        }
    }


 private AtlasPerfTracer(Logger logger, String tag) {
 this.logger = logger;
 this.tag    = tag;
 startTimeMs = System.currentTimeMillis();
    }


 public String getTag() {
 return tag;
    }


 public long getStartTime() {
 return startTimeMs;
    }


 public long getElapsedTime() {
 return System.currentTimeMillis() - startTimeMs;
    }


 public void log() {
 long elapsedTime = getElapsedTime();
 if (elapsedTime > reportingThresholdMs) {
 logger.debug("PERF|{}|{}", tag, elapsedTime);
        }
    }
}