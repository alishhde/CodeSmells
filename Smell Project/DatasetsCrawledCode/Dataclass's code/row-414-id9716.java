 @WeakOuter
 final class EntrySet extends AbstractSet<Map.Entry<K,V>> {
 public final int size()                 { return size; }
 public final void clear()               { HashMap.this.clear(); }
 public final Iterator<Map.Entry<K,V>> iterator() {
 return new EntryIterator();
        }
 public final boolean contains(Object o) {
 if (!(o instanceof Map.Entry))
 return false;
 Map.Entry<?,?> e = (Map.Entry<?,?>) o;
 Object key = e.getKey();
 Node<K,V> candidate = getNode(hash(key), key);
 return candidate != null && candidate.equals(e);
        }
 public final boolean remove(Object o) {
 if (o instanceof Map.Entry) {
 Map.Entry<?,?> e = (Map.Entry<?,?>) o;
 Object key = e.getKey();
 Object value = e.getValue();
 return removeNode(hash(key), key, value, true, true) != null;
            }
 return false;
        }
 public final Spliterator<Map.Entry<K,V>> spliterator() {
 return new EntrySpliterator<>(HashMap.this, 0, -1, 0, 0);
        }
 public final void forEach(Consumer<? super Map.Entry<K,V>> action) {
 Node<K,V>[] tab;
 if (action == null)
 throw new NullPointerException();
 if (size > 0 && (tab = table) != null) {
 int mc = modCount;
 // Android-changed: Detect changes to modCount early.
 for (int i = 0; (i < tab.length && modCount == mc); ++i) {
 for (Node<K,V> e = tab[i]; e != null; e = e.next)
 action.accept(e);
                }
 if (modCount != mc)
 throw new ConcurrentModificationException();
            }
        }


 /*-[
        - (NSUInteger)countByEnumeratingWithState:(NSFastEnumerationState *)state
                                          objects:(__unsafe_unretained id *)stackbuf
                                            count:(NSUInteger)len {
          return [this$0_ enumerateEntriesWithState:state objects:stackbuf count:len];
        }

        RETAINED_WITH_CHILD(this$0_)
        ]-*/
    }