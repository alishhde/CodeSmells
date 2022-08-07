 protected static class CreateFileThread extends Thread {
 protected final HDFSSession session;
 protected final Path path;
 protected final InputStream input;
 protected Throwable exception = null;


 public CreateFileThread(HDFSSession session, Path path, InputStream input) {
 super();
 this.session = session;
 this.path = path;
 this.input = input;
 setDaemon(true);
    }


 public void run() {
 try {
 session.createFile(path,input);
      } catch (Throwable e) {
 this.exception = e;
      }
    }


 public void finishUp() throws InterruptedException, IOException {
 join();
 Throwable thr = exception;
 if (thr != null) {
 if (thr instanceof IOException) {
 throw (IOException) thr;
        } else if (thr instanceof RuntimeException) {
 throw (RuntimeException) thr;
        } else {
 throw (Error) thr;
        }
      }
    }
  }