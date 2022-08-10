 private void log(Operation op, OperationProcessingContext context, String msg, Level logLevel) {
 String hostId = context.host != null ? context.host.getId() : "";
 String path = op.getUri() != null ? op.getUri().getPath() : "";
 Filter filter = this.filters.get(context.currentFilterPosition);
 String filterName = filter != null ? filter.getClass().getSimpleName() : "";
 String logMsg = String.format("(host: %s, op %d %s %s) filter %s: %s",
 hostId, op.getId(), op.getAction(),  path, filterName, msg);
 Level level = logLevel != null ? logLevel : Level.INFO;
 Utils.log(getClass(), op.getUri().getPath(), level, logMsg);
    }