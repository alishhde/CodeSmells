 @Override
 public void reset() {
 RepeatableIteratorWrapper<E> changedIterator = this.changedIterator;
 if (changedIterator != null) {
 currentIterator = changedIterator;
 changedIteratorFieldUpdater.compareAndSet(this, changedIterator, null);
      }
 currentIterator.reset();
   }