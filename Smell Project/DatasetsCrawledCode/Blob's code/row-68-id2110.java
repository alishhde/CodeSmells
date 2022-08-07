 private static class Map4<K, V> extends Map3<K, V> {
 /** */
 private static final long serialVersionUID = 0L;


 /** */
 protected K k4;


 /** */
 protected V v4;


 /**
         * Constructs map.
         */
 Map4() {
 // No-op.
        }


 /**
         * Constructs map.
         *
         * @param k1 Key1.
         * @param v1 Value1.
         * @param k2 Key2.
         * @param v2 Value2.
         * @param k3 Key3.
         * @param v3 Value3.
         * @param k4 Key4.
         * @param v4 Value4.
         */
 Map4(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
 super(k1, v1, k2, v2, k3, v3);


 this.k4 = k4;
 this.v4 = v4;
        }


 /** {@inheritDoc} */
 @Override public boolean isFull() {
 return size() == 4;
        }


 /** {@inheritDoc} */
 @Nullable @Override public V remove(Object key) {
 if (F.eq(key, k4)) {
 V res = v4;


 v4 = null;
 k4 = null;


 return res;
            }


 return super.remove(key);
        }


 /** {@inheritDoc} */
 @Override public int size() {
 return super.size() + (k4 != null ? 1 : 0);
        }


 /** {@inheritDoc} */
 @Override public boolean containsKey(Object k) {
 return super.containsKey(k) || (k4 != null && F.eq(k, k4));
        }


 /** {@inheritDoc} */
 @Override public boolean containsValue(Object v) {
 return super.containsValue(v) || (k4 != null && F.eq(v, v4));
        }


 /** {@inheritDoc} */
 @Nullable @Override public V get(Object k) {
 V v = super.get(k);


 return v != null ? v : (k4 != null && F.eq(k, k4)) ? v4 : null;
        }


 /**
         * Puts key-value pair into map only if given key is already contained in the map
         * or there are free slots.
         * Note that this implementation of {@link Map#put(Object, Object)} does not match
         * general contract of {@link Map} interface and serves only for internal purposes.
         *
         * @param key Key.
         * @param val Value.
         * @return Previous value associated with given key.
         */
 @Nullable @Override public V put(K key, V val) throws NullPointerException {
 V oldVal = get(key);


 if (k1 == null || F.eq(k1, key)) {
 k1 = key;
 v1 = val;
            }
 else if (k2 == null || F.eq(k2, key)) {
 k2 = key;
 v2 = val;
            }
 else if (k3 == null || F.eq(k3, key)) {
 k3 = key;
 v3 = val;
            }
 else if (k4 == null || F.eq(k4, key)) {
 k4 = key;
 v4 = val;
            }


 return oldVal;
        }


 /** {@inheritDoc} */
 @Override public Set<Entry<K, V>> entrySet() {
 return new AbstractSet<Entry<K, V>>() {
 @Override public Iterator<Entry<K, V>> iterator() {
 return new Iterator<Entry<K, V>>() {
 private int idx;


 private Entry<K, V> next;


                        {
 if (k1 != null) {
 idx = 1;
 next = e(k1, v1);
                            }
 else if (k2 != null) {
 idx = 2;
 next = e(k2, v2);
                            }
 else if (k3 != null) {
 idx = 3;
 next = e(k3, v3);
                            }
 else if (k4 != null) {
 idx = 4;
 next = e(k4, v4);
                            }
                        }


 @Override public boolean hasNext() {
 return next != null;
                        }


 @SuppressWarnings("fallthrough")
 @Override public Entry<K, V> next() {
 if (!hasNext())
 throw new NoSuchElementException();


 Entry<K, V> old = next;


 next = null;


 switch (idx) {
 case 1:
 if (k2 != null) {
 idx = 2;
 next = e(k2, v2);


 break;
                                    }


 case 2:
 if (k3 != null) {
 idx = 3;
 next = e(k3, v3);


 break;
                                    }


 case 3:
 if (k4 != null) {
 idx = 4;
 next = e(k4, v4);


 break;
                                    }
                            }


 return old;
                        }


 @Override public void remove() {
 throw new UnsupportedOperationException();
                        }
                    };
                }


 @Override public int size() {
 return Map4.this.size();
                }
            };
        }
    }