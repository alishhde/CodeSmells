 private static class Success<V> extends Try<V> {


 private V value;


 public Success(V value) {


 super();


 this.value = value;


        }


 @Override


 public Boolean isSuccess() {


 return true;


        }


 @Override


 public Boolean isFailure() {


 return false;


        }


 @Override


 public void throwException() {


 //log.error("Method throwException() called on a Success instance");


        }


 @Override
 public V get() {
 return value;
        }


 @Override
 public Throwable getError() {
 return null;
        }
    }