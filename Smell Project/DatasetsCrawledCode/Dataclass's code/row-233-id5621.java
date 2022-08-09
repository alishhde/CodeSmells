 public static class Offset implements FileOffset {
 public long lastSyncPoint;
 public long recordsSinceLastSync;
 public long currentRecord;
 private long currRecordEndOffset;
 private long prevRecordEndOffset;


 public Offset(long lastSyncPoint, long recordsSinceLastSync, long currentRecord) {
 this(lastSyncPoint, recordsSinceLastSync, currentRecord, 0, 0 );
    }


 public Offset(long lastSyncPoint, long recordsSinceLastSync, long currentRecord
                  , long currRecordEndOffset, long prevRecordEndOffset) {
 this.lastSyncPoint = lastSyncPoint;
 this.recordsSinceLastSync = recordsSinceLastSync;
 this.currentRecord = currentRecord;
 this.prevRecordEndOffset = prevRecordEndOffset;
 this.currRecordEndOffset = currRecordEndOffset;
    }


 public Offset(String offset) {
 try {
 if(offset==null) {
 throw new IllegalArgumentException("offset cannot be null");
        }
 if(offset.equalsIgnoreCase("0")) {
 this.lastSyncPoint = 0;
 this.recordsSinceLastSync = 0;
 this.currentRecord = 0;
 this.prevRecordEndOffset = 0;
 this.currRecordEndOffset = 0;
        } else {
 String[] parts = offset.split(":");
 this.lastSyncPoint = Long.parseLong(parts[0].split("=")[1]);
 this.recordsSinceLastSync = Long.parseLong(parts[1].split("=")[1]);
 this.currentRecord = Long.parseLong(parts[2].split("=")[1]);
 this.prevRecordEndOffset = 0;
 this.currRecordEndOffset = 0;
        }
      } catch (Exception e) {
 throw new IllegalArgumentException("'" + offset +
 "' cannot be interpreted. It is not in expected format for SequenceFileReader." +
 " Format e.g. {sync=123:afterSync=345:record=67}");
      }
    }


 @Override
 public String toString() {
 return '{' +
 "sync=" + lastSyncPoint +
 ":afterSync=" + recordsSinceLastSync +
 ":record=" + currentRecord +
 ":}";
    }


 @Override
 public boolean isNextOffset(FileOffset rhs) {
 if(rhs instanceof Offset) {
 Offset other = ((Offset) rhs);
 return other.currentRecord > currentRecord+1;
      }
 return false;
    }


 @Override
 public int compareTo(FileOffset o) {
 Offset rhs = ((Offset) o);
 if(currentRecord<rhs.currentRecord) {
 return -1;
      }
 if(currentRecord==rhs.currentRecord) {
 return 0;
      }
 return 1;
    }


 @Override
 public boolean equals(Object o) {
 if (this == o) { return true; }
 if (!(o instanceof Offset)) { return false; }


 Offset offset = (Offset) o;


 return currentRecord == offset.currentRecord;
    }


 @Override
 public int hashCode() {
 return (int) (currentRecord ^ (currentRecord >>> 32));
    }
 
 void increment(boolean syncSeen, long newBytePosition) {
 if(!syncSeen) {
        ++recordsSinceLastSync;
      }  else {
 recordsSinceLastSync = 1;
 lastSyncPoint = prevRecordEndOffset;
      }
      ++currentRecord;
 prevRecordEndOffset = currRecordEndOffset;
 currentRecord = newBytePosition;
    }


 @Override
 public Offset clone() {
 return new Offset(lastSyncPoint, recordsSinceLastSync, currentRecord, currRecordEndOffset, prevRecordEndOffset);
    }


  } //class Offset