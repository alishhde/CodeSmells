 protected static final class PersistenceWithIntOffset extends PersistenceWithIntOffsetNoLL {
 /**
     * Used by DiskRegion for compaction
     *
     * @since GemFire prPersistSprint1
     */
 private DiskEntry prev;
 /**
     * Used by DiskRegion for compaction
     *
     * @since GemFire prPersistSprint1
     */
 private DiskEntry next;


 @Override
 public DiskEntry getPrev() {
 return this.prev;
    }


 @Override
 public DiskEntry getNext() {
 return this.next;
    }


 @Override
 public void setPrev(DiskEntry v) {
 this.prev = v;
    }


 @Override
 public void setNext(DiskEntry v) {
 this.next = v;
    }
  }