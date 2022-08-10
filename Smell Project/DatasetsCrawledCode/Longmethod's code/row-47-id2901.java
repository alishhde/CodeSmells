 @Override
 public <T> T callWithTimeout(Callable<T> callable, long timeoutDuration, TimeUnit timeoutUnit)
 throws ExecutionException {
 checkNotNull(callable);
 checkNotNull(timeoutUnit);
 try {
 return callable.call();
    } catch (RuntimeException e) {
 throw new UncheckedExecutionException(e);
    } catch (Exception e) {
 throw new ExecutionException(e);
    } catch (Error e) {
 throw new ExecutionError(e);
    } catch (Throwable e) {
 // It's a non-Error, non-Exception Throwable. Such classes are usually intended to extend
 // Exception, so we'll treat it like an Exception.
 throw new ExecutionException(e);
    }
  }