public abstract class NexmarkQueryTransform<T extends KnownSize>
 extends PTransform<PCollection<Event>, PCollection<T>> {


 private transient PCollection<KV<Long, String>> sideInput = null;


 protected NexmarkQueryTransform(String name) {
 super(name);
  }


 /** Whether this query expects a side input to be populated. Defaults to {@code false}. */
 public boolean needsSideInput() {
 return false;
  }


 /**
   * Set the side input for the query.
   *
   * <p>Note that due to the nature of side inputs, this instance of the query is now fixed and can
   * only be safely applied in the pipeline where the side input was created.
   */
 public void setSideInput(PCollection<KV<Long, String>> sideInput) {
 this.sideInput = sideInput;
  }


 /** Get the side input, if any. */
 public @Nullable PCollection<KV<Long, String>> getSideInput() {
 return sideInput;
  }
}