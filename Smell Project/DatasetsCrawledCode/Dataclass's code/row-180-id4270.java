public class AvroWrapper<T> {
 private T datum;


 /** Wrap null. Construct {@link AvroWrapper} wrapping no datum. */
 public AvroWrapper() { this(null); }


 /** Wrap a datum. */
 public AvroWrapper(T datum) { this.datum = datum; }


 /** Return the wrapped datum. */
 public T datum() { return datum; }


 /** Set the wrapped datum. */
 public void datum(T datum) { this.datum = datum; }


 @Override
 public int hashCode() {
 return (datum == null) ? 0 : datum.hashCode();
  }


 @Override
 public boolean equals(Object obj) {
 if (this == obj)
 return true;
 if (obj == null)
 return false;
 if (getClass() != obj.getClass())
 return false;
 AvroWrapper that = (AvroWrapper)obj;
 if (this.datum == null) {
 return that.datum == null;
    } else return datum.equals(that.datum);
  }


 /** Get the wrapped datum as JSON. */
 @Override
 public String toString() {
 return datum.toString();
  }
}