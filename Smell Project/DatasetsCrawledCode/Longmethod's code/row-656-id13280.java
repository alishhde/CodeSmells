 @Override
 public T get(Duration duration) throws InterruptedException, ExecutionException, TimeoutException {
 long start = System.currentTimeMillis();
 Long end  = duration==null ? null : start + duration.toMillisecondsRoundingUp();
 while (end==null || end > System.currentTimeMillis()) {
 if (cancelled) throw new CancellationException();
 if (internalFuture == null) {
 synchronized (this) {
 long remaining = end - System.currentTimeMillis();
 if (internalFuture==null && remaining>0)
 wait(remaining);
                }
            }
 if (internalFuture != null) break;
        }
 Long remaining = end==null ? null : end -  System.currentTimeMillis();
 if (isDone()) {
 return internalFuture.get(1, TimeUnit.MILLISECONDS);
        } else if (remaining == null) {
 return internalFuture.get();
        } else if (remaining > 0) {
 return internalFuture.get(remaining, TimeUnit.MILLISECONDS);
        } else {
 throw new TimeoutException();
        }
    }