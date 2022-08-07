public class ArrayMap<K, V> extends AbstractMap<K, V> {


 private Object[] table;
 private int size;
 protected transient Collection<V> values;


 public ArrayMap() {
 this(32);
    }


 public ArrayMap(int capacity) {
 table = new Object[capacity * 2];
 size = 0;
    }


 @Override
 @SuppressWarnings("unchecked")
 public V get(Object key) {
 for (int i = 0, l = size << 1; i < l; i += 2) {
 if (key.equals(table[i])) {
 return (V) table[i + 1];
            }
        }
 return null;
    }


 @Override
 @SuppressWarnings("unchecked")
 public V put(K key, V value) {
 for (int i = 0, l = size << 1; i < l; i += 2) {
 if (key.equals(table[i])) {
 V old = (V) table[i + 1];
 table[i + 1] = value;
 return old;
            }
        }
 if (size * 2 == table.length) {
 Object[] n = new Object[table.length * 2];
 System.arraycopy(table, 0, n, 0, table.length);
 table = n;
        }
 int i = size++ << 1;
 table[i++] = key;
 table[i] = value;
 return null;
    }


 @SuppressWarnings("unchecked")
 public V getOrCompute(K key) {
 for (int i = 0, l = size << 1; i < l; i += 2) {
 if (key.equals(table[i])) {
 return (V) table[i + 1];
            }
        }
 V v = compute(key);
 if (size << 1 == table.length) {
 Object[] n = new Object[table.length << 1];
 System.arraycopy(table, 0, n, 0, table.length);
 table = n;
        }
 int i = size++ << 1;
 table[i++] = key;
 table[i] = v;
 return v;
    }


 protected V compute(K key) {
 throw new UnsupportedOperationException();
    }


 @Override
 public Collection<V> values() {
 if (values == null) {
 values = new AbstractCollection<V>() {
 @Override
 public Iterator<V> iterator() {
 return new Iterator<V>() {
 int index = 0;


 public boolean hasNext() {
 return index < size;
                        }


 @SuppressWarnings("unchecked")
 public V next() {
 if (index >= size) {
 throw new NoSuchElementException();
                            }
 return (V) table[(index++ << 1) + 1];
                        }
 
 public void remove() {
 throw new UnsupportedOperationException();
                        }
                    };
                }


 @Override
 public int size() {
 return size;
                }
            };
        }
 return values;
    }


 @Override
 public Set<Entry<K, V>> entrySet() {
 return new AbstractSet<Entry<K, V>>() {
 @Override
 public Iterator<Entry<K, V>> iterator() {
 return new Iterator<Entry<K, V>>() {
 FastEntry<K, V> entry = new FastEntry<K, V>();
 int index = 0;


 public boolean hasNext() {
 return index < size;
                    }


 @SuppressWarnings("unchecked")
 public FastEntry<K, V> next() {
 if (index >= size) {
 throw new NoSuchElementException();
                        }
 int i = index << 1;
 entry.key = (K) table[i];
 entry.value = (V) table[i + 1];
 index++;
 return entry;
                    }


 public void remove() {
 throw new UnsupportedOperationException();
                    }
                };
            }


 @Override
 public int size() {
 return size;
            }
        };
    }


 static class FastEntry<K, V> implements Entry<K, V> {
 K key;
 V value;


 public K getKey() {
 return key;
        }




 public V getValue() {
 return value;
        }


 public V setValue(V value) {
 throw new UnsupportedOperationException();
        }
    }
}