public abstract class CancelableThread extends Thread {


 protected boolean running = true;
 protected boolean canceled = false;


 public synchronized final boolean isRunning() {
 return running;
    }


 public synchronized final void setRunning(boolean running) {
 this.running = running;
    }


 /**
     * Subclasses shouldn't do any potentially conflicting UI work before
     * checking to see if the thread has been canceled.
     */
 public synchronized final boolean isCanceled() {
 return canceled;
    }


 public synchronized final void cancel() {
 this.canceled = true;
    }


 /**
     * Cancels the thread given if it's running.
     */
 public static void cancelThread(CancelableThread thread) {
 if ( thread != null ) {
 synchronized ( thread ) {
 if ( thread.isRunning() ) {
 thread.cancel();
                }
            }
        }
    }


}