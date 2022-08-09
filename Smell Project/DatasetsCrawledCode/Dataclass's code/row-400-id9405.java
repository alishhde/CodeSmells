public final class CurrentCreationalContext<T> {
 private final ThreadLocal<CreationalContext<T>> creationalContext = new ThreadLocal<CreationalContext<T>>();


 public CreationalContext<T> get() {
 return creationalContext.get();
    }


 public void set(CreationalContext<T> value) {
 creationalContext.set(value);
    }


 public void remove() {
 creationalContext.remove();
    }
}