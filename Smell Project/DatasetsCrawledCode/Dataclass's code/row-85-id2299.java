public final class StableValue<T> {


 private final T value;
 private final Assumption assumption;


 public StableValue(T value, String name) {
 this.value = value;
 this.assumption = Truffle.getRuntime().createAssumption(name);
    }


 public T getValue() {
 return value;
    }


 public Assumption getAssumption() {
 return assumption;
    }


 @Override
 public String toString() {
 return "[" + value + ", " + assumption + "]";
    }
}