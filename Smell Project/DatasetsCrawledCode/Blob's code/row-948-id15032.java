public class LogTransformer extends Transformer {
 private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


 @Override
 public Object transformRow(Map<String, Object> row, Context ctx) {
 String expr = ctx.getEntityAttribute(LOG_TEMPLATE);
 String level = ctx.replaceTokens(ctx.getEntityAttribute(LOG_LEVEL));


 if (expr == null || level == null) return row;


 if ("info".equals(level)) {
 if (log.isInfoEnabled())
 log.info(ctx.replaceTokens(expr));
    } else if ("trace".equals(level)) {
 if (log.isTraceEnabled())
 log.trace(ctx.replaceTokens(expr));
    } else if ("warn".equals(level)) {
 if (log.isWarnEnabled())
 log.warn(ctx.replaceTokens(expr));
    } else if ("error".equals(level)) {
 if (log.isErrorEnabled())
 log.error(ctx.replaceTokens(expr));
    } else if ("debug".equals(level)) {
 if (log.isDebugEnabled())
 log.debug(ctx.replaceTokens(expr));
    }


 return row;
  }


 public static final String LOG_TEMPLATE = "logTemplate";
 public static final String LOG_LEVEL = "logLevel";
}