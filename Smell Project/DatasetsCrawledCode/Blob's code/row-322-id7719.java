@Component
@Reference(name = "shell", strategy = ReferenceStrategy.EVENT, policy = ReferencePolicy.DYNAMIC,
 referenceInterface = Shell.class, cardinality = ReferenceCardinality.OPTIONAL_UNARY)
public class JdkDelegatingLogListener extends AbstractFlashingObject implements LogListener {


 public static final String DO_NOT_LOG = "#DO_NOT_LOG";
 private final static Logger LOGGER = HandlerUtils.getLogger(JdkDelegatingLogListener.class);


 @Reference(policy = ReferencePolicy.DYNAMIC)
 private volatile LogReaderService logReaderService;


 public static String cleanThrowable(final Throwable throwable) {
 final StringBuilder result = new StringBuilder();
 result.append(LINE_SEPARATOR);
 result.append(throwable.toString().replace(DO_NOT_LOG, ""));
 result.append(LINE_SEPARATOR);
 for (final StackTraceElement ste : throwable.getStackTrace()) {
 result.append(ste);
 result.append(LINE_SEPARATOR);
    }
 return result.toString();
  }


 @SuppressWarnings("unchecked")
 protected void activate(final ComponentContext context) {
 logReaderService.addLogListener(this);
 final Enumeration<LogEntry> latestLogs = logReaderService.getLog();
 if (latestLogs.hasMoreElements()) {
 logNow(latestLogs.nextElement(), false);
    }
  }


 private String buildMessage(final LogEntry entry) {
 final StringBuilder sb = new StringBuilder();
 sb.append("[").append(entry.getBundle()).append("] ").append(entry.getMessage());
 return sb.toString();
  }


 private boolean containsDoNotLogTag(final Throwable throwable) {
 if (throwable == null) {
 return false;
    }
 if (throwable.getMessage().contains(DO_NOT_LOG)) {
 return true;
    }
 final StringWriter sw = new StringWriter();
 throwable.printStackTrace(new PrintWriter(sw));
 return sw.toString().contains(DO_NOT_LOG);
  }


 protected void deactivate(final ComponentContext context) {
 logReaderService.removeLogListener(this);
  }


 public void logged(final LogEntry entry) {
 if (containsDoNotLogTag(entry.getException())) {
 // Only log Felix stack trace in development mode, discard log
 // otherwise
 if (isDevelopmentMode()) {
 logNow(entry, true);
      }
    } else {
 logNow(entry, false);
    }
  }


 private void logNow(final LogEntry entry, final boolean removeDoNotLogTag) {
 final int osgiLevel = entry.getLevel();
 Level jdkLevel = Level.FINEST;


 // Convert the OSGi level into a JDK logger level
 if (osgiLevel == LogService.LOG_DEBUG) {
 jdkLevel = Level.FINE;
    } else if (osgiLevel == LogService.LOG_INFO) {
 jdkLevel = Level.INFO;
    } else if (osgiLevel == LogService.LOG_WARNING) {
 jdkLevel = Level.WARNING;
    } else if (osgiLevel == LogService.LOG_ERROR) {
 jdkLevel = Level.SEVERE;
    }


 if (jdkLevel.intValue() <= Level.INFO.intValue()) {
 // Not very important message, so just flash it if possible and
 // we're in development mode
 if (isDevelopmentMode()) {
 flash(jdkLevel, buildMessage(entry), MY_SLOT);
 // Immediately clear it once the timeout has been reached
 flash(jdkLevel, "", MY_SLOT);
      }
    } else {
 // Important log message, so log it via JDK
 if (removeDoNotLogTag) {
 LOGGER.log(jdkLevel, buildMessage(entry) + cleanThrowable(entry.getException()));
      } else {
 LOGGER.log(jdkLevel, buildMessage(entry), entry.getException());
      }
    }
  }
}