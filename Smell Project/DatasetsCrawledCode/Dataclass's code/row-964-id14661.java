@org.apache.hadoop.classification.InterfaceStability.Evolving
public abstract class AbstractStoreOutputOperator<T, S extends Connectable> extends BaseOperator
{
 protected S store;


 /**
   * The input port on which tuples are received for writing.
   */
 @InputPortFieldAnnotation(optional = true)
 public final transient DefaultInputPort<T> input = new DefaultInputPort<T>()
  {
 @Override
 public void process(T t)
    {
 processTuple(t);
    }


  };


 /**
   * Gets the store.
   * @return the store.
   */
 public S getStore()
  {
 return store;
  }


 /**
   * Sets the store.
   * @param store a {@link Connectable}.
   */
 public void setStore(S store)
  {
 this.store = store;
  }


 @Override
 public void setup(OperatorContext context)
  {
 try {
 store.connect();
    } catch (IOException ex) {
 throw new RuntimeException(ex);
    }
  }


 @Override
 public void beginWindow(long windowId)
  {
  }


 @Override
 public void teardown()
  {
 try {
 store.disconnect();
    } catch (IOException ex) {
 throw new RuntimeException(ex);
    }
  }


 /**
   * Processes the incoming tuple, presumably store the data in the tuple to the store
   *
   * @param tuple a tuple.
   */
 public abstract void processTuple(T tuple);


}