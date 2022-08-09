public class LimitedSizeDataCollection<STORAGE_DATA extends ComparableStorageData> implements SWCollection<STORAGE_DATA> {


 private final HashMap<STORAGE_DATA, LinkedList<STORAGE_DATA>> data;
 private final int limitedSize;
 private volatile boolean writing;
 private volatile boolean reading;


 LimitedSizeDataCollection(int limitedSize) {
 this.data = new HashMap<>();
 this.writing = false;
 this.reading = false;
 this.limitedSize = limitedSize;
    }


 public void finishWriting() {
 writing = false;
    }


 @Override public void writing() {
 writing = true;
    }


 @Override public boolean isWriting() {
 return writing;
    }


 @Override public void finishReading() {
 reading = false;
    }


 @Override public void reading() {
 reading = true;
    }


 @Override public boolean isReading() {
 return reading;
    }


 @Override public int size() {
 return data.size();
    }


 @Override public void clear() {
 data.clear();
    }


 @Override public boolean containsKey(STORAGE_DATA key) {
 throw new UnsupportedOperationException("Limited size data collection doesn't support containsKey operation.");
    }


 @Override public STORAGE_DATA get(STORAGE_DATA key) {
 throw new UnsupportedOperationException("Limited size data collection doesn't support get operation.");
    }


 @Override public void put(STORAGE_DATA value) {
 LinkedList<STORAGE_DATA> storageDataList = this.data.get(value);
 if (storageDataList == null) {
 storageDataList = new LinkedList<>();
 data.put(value, storageDataList);
        }


 if (storageDataList.size() < limitedSize) {
 storageDataList.add(value);
 return;
        }


 for (int i = 0; i < storageDataList.size(); i++) {
 STORAGE_DATA storageData = storageDataList.get(i);
 if (value.compareTo(storageData) <= 0) {
 if (i == 0) {
 // input value is less than the smallest in top N list, ignore
                } else {
 // Remove the smallest in top N list
 // add the current value into the right position
 storageDataList.add(i, value);
 storageDataList.removeFirst();
                }
 return;
            }
        }


 // Add the value as biggest in top N list
 storageDataList.addLast(value);
 storageDataList.removeFirst();
    }


 @Override public Collection<STORAGE_DATA> collection() {
 List<STORAGE_DATA> collection = new ArrayList<>();
 data.values().forEach(e -> e.forEach(collection::add));
 return collection;
    }
}