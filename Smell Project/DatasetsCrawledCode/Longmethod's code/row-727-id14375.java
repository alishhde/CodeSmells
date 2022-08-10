 public List<Long> getOrderedLogFileIds() {
 File fileLogDir = new File(logDir);
 String[] logFileNames = null;
 List<Long> logFileIds = null;
 if (!fileLogDir.exists()) {
 LOGGER.log(Level.INFO, "log dir " + logDir + " doesn't exist.  returning empty list");
 return Collections.emptyList();
        }
 if (!fileLogDir.isDirectory()) {
 throw new IllegalStateException("log dir " + logDir + " exists but it is not a directory");
        }
 logFileNames = fileLogDir.list((dir, name) -> name.startsWith(logFilePrefix));
 if (logFileNames == null) {
 throw new IllegalStateException("listing of log dir (" + logDir + ") files returned null. "
                    + "Either an IO error occurred or the dir was just deleted by another process/thread");
        }
 if (logFileNames.length == 0) {
 LOGGER.log(Level.INFO, "the log dir (" + logDir + ") is empty. returning empty list");
 return Collections.emptyList();
        }
 logFileIds = new ArrayList<>();
 for (String fileName : logFileNames) {
 logFileIds.add(Long.parseLong(fileName.substring(logFilePrefix.length() + 1)));
        }
 logFileIds.sort(Long::compareTo);
 return logFileIds;
    }