 @Override
 protected boolean isUnreachableException(Throwable ex) {
 if (super.isUnreachableException(ex)) {
 return true;
        }


 if (ex instanceof SocketTimeoutException)
 return true;


 List<String> exceptionList = new ArrayList<>();
 exceptionList.add(ex.getClass().getName());


 Throwable t = ex.getCause();
 int depth = 0;
 while (t != null && depth < 5) {
 exceptionList.add(t.getClass().getName());
 depth++;
 if (t instanceof ConnectException) {
 return true;
            }
 t = t.getCause();
        }


 logger.trace("Not an unreachable exception with causes {}", exceptionList);
 return false;
    }