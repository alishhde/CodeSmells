public final class ObjectLruCache extends AbstractLruCache {


 /** The array of values */
 Object[] values = new Object[INITIAL_SIZE];


 /**
     * Create a new ObjectLruCache.
     * @param maxSize the maximum size the cache can grow to
     */
 public ObjectLruCache(int maxSize) {
 super(maxSize);
    }


 /**
     * Overridden method to return values array.
     */
 Object getValuesArray() {
 return values;
    }


 /**
     * Overridden method to allocate new values array.
     */
 void allocNewValuesArray(int newSize) {
 super.allocNewValuesArray(newSize);
 values = new Object[newSize];
    }


 /**
     * Overridden method to repopulate with key plus value at given offset.
     */
 void put(long key, Object oldvalues, int offset) {
 Object[] v = (Object[])oldvalues;
 put(key, v[offset]);
    }


 /**
     * Returns the value mapped by the given key. Also promotes this key to the most
     * recently used.
     * @return the value or null if it cannot be found
     */
 public Object get(long key) {
 int index = getIndexAndPromote(key) ;
 if (index != -1) {
 return values[index];
        }
 return null;
    }


 /**
     * Add the key/value pair to the map.
     */
 public void put(long key, Object value) {
 int index = putIndexAndPromote(key) ;
 values[index] = value;
 checkRehash();
    }
}