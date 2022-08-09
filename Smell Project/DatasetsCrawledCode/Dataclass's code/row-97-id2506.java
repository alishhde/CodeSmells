public class TransformIterator<I, O> implements Iterator<O> {


 /** The iterator being used */
 private Iterator<? extends I> iterator;
 /** The transformer being used */
 private Transformer<? super I, ? extends O> transformer;


 //-----------------------------------------------------------------------
 /**
     * Constructs a new <code>TransformIterator</code> that will not function
     * until the {@link #setIterator(Iterator) setIterator} and
     * {@link #setTransformer(Transformer)} methods are invoked.
     */
 public TransformIterator() {
 super();
    }


 /**
     * Constructs a new <code>TransformIterator</code> that won't transform
     * elements from the given iterator.
     *
     * @param iterator  the iterator to use
     */
 public TransformIterator(final Iterator<? extends I> iterator) {
 super();
 this.iterator = iterator;
    }


 /**
     * Constructs a new <code>TransformIterator</code> that will use the
     * given iterator and transformer.  If the given transformer is null,
     * then objects will not be transformed.
     *
     * @param iterator  the iterator to use
     * @param transformer  the transformer to use
     */
 public TransformIterator(final Iterator<? extends I> iterator,
 final Transformer<? super I, ? extends O> transformer) {
 super();
 this.iterator = iterator;
 this.transformer = transformer;
    }


 //-----------------------------------------------------------------------
 @Override
 public boolean hasNext() {
 return iterator.hasNext();
    }


 /**
     * Gets the next object from the iteration, transforming it using the
     * current transformer. If the transformer is null, no transformation
     * occurs and the object from the iterator is returned directly.
     *
     * @return the next object
     * @throws java.util.NoSuchElementException if there are no more elements
     */
 @Override
 public O next() {
 return transform(iterator.next());
    }


 @Override
 public void remove() {
 iterator.remove();
    }


 //-----------------------------------------------------------------------
 /**
     * Gets the iterator this iterator is using.
     *
     * @return the iterator.
     */
 public Iterator<? extends I> getIterator() {
 return iterator;
    }


 /**
     * Sets the iterator for this iterator to use.
     * If iteration has started, this effectively resets the iterator.
     *
     * @param iterator  the iterator to use
     */
 public void setIterator(final Iterator<? extends I> iterator) {
 this.iterator = iterator;
    }


 //-----------------------------------------------------------------------
 /**
     * Gets the transformer this iterator is using.
     *
     * @return the transformer.
     */
 public Transformer<? super I, ? extends O> getTransformer() {
 return transformer;
    }


 /**
     * Sets the transformer this the iterator to use.
     * A null transformer is a no-op transformer.
     *
     * @param transformer  the transformer to use
     */
 public void setTransformer(final Transformer<? super I, ? extends O> transformer) {
 this.transformer = transformer;
    }


 //-----------------------------------------------------------------------
 /**
     * Transforms the given object using the transformer.
     * If the transformer is null, the original object is returned as-is.
     *
     * @param source  the object to transform
     * @return the transformed object
     */
 protected O transform(final I source) {
 return transformer.transform(source);
    }
}