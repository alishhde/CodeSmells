 public Future<?> monitorUntil(final ActiveAnnotationContext ctx, final CancelIndicator cancelIndicator, final Function0<? extends Boolean> isFinished) {
 Future<?> _xblockexpression = null;
      {
 final Runnable _function = () -> {
 try {
 while ((!(isFinished.apply()).booleanValue())) {
              {
 boolean _isCanceled = cancelIndicator.isCanceled();
 if (_isCanceled) {
 CompilationUnitImpl _compilationUnit = ctx.getCompilationUnit();
 _compilationUnit.setCanceled(true);
 return;
                }
 Thread.sleep(100);
              }
            }
          } catch (Throwable _e) {
 throw Exceptions.sneakyThrow(_e);
          }
        };
 final Runnable r = _function;
 Future<?> _xtrycatchfinallyexpression = null;
 try {
 _xtrycatchfinallyexpression = this.pool.submit(r);
        } catch (final Throwable _t) {
 if (_t instanceof RejectedExecutionException) {
 final RejectedExecutionException e = (RejectedExecutionException)_t;
 AnnotationProcessor.CancellationObserver.log.debug(e.getMessage(), e);
 new Thread(r).start();
          } else {
 throw Exceptions.sneakyThrow(_t);
          }
        }
 _xblockexpression = _xtrycatchfinallyexpression;
      }
 return _xblockexpression;
    }