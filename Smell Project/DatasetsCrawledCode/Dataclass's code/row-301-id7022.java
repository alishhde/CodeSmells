@SuppressWarnings({"unchecked", "rawtypes"})
public final class None<T> extends Option<T> {
 private static final None INSTANCE = new None<>();


 /**
     * Get the static instance.
     * @param <T> The type of this no-value object.
     * @return the static instance
     */
 public static final <T> None<T> getInstance() {
 return INSTANCE;
    }


 /**
     * Default constructor, does nothing.
     */
 public None() {
 // super(null);
 // no-op
    }


 @Override
 public boolean hasValue() {
 return false;
    }


 @Override
 public T getValue() {
 throw new NoSuchElementException("None does not contain a value");
    }


 @Override
 public String toString() {
 return "None()";
    }


 @Override
 public boolean equals(Object other) {
 return (other == null || other.getClass() != None.class) ? false : true;
    }


 @Override
 public int hashCode() {
 return -31;
    }


}