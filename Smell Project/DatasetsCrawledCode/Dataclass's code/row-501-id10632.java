 protected class ValueIterable implements Iterable<VALUEIN> {
 private ValueIterator iterator = new ValueIterator();
 @Override
 public Iterator<VALUEIN> iterator() {
 return iterator;
    } 
  }