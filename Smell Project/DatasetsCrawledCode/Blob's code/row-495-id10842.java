 private static class BucketList<V> implements Iterable<Bucket<V>> {
 private final ArrayList<Bucket<V>> bucketList;
 private final List<Bucket<V>> immutableVisibleList;


 private BucketList(ArrayList<Bucket<V>> bucketList, ArrayList<Bucket<V>> publicBucketList) {
 this.bucketList = bucketList;


 int displayIndex = 0;
 for (Bucket<V> bucket : publicBucketList) {
 bucket.displayIndex = displayIndex++;
            }
 immutableVisibleList = Collections.unmodifiableList(publicBucketList);
        }


 private int getBucketCount() {
 return immutableVisibleList.size();
        }


 private int getBucketIndex(CharSequence name, Collator collatorPrimaryOnly) {
 // binary search
 int start = 0;
 int limit = bucketList.size();
 while ((start + 1) < limit) {
 int i = (start + limit) / 2;
 Bucket<V> bucket = bucketList.get(i);
 int nameVsBucket = collatorPrimaryOnly.compare(name, bucket.lowerBoundary);
 if (nameVsBucket < 0) {
 limit = i;
                } else {
 start = i;
                }
            }
 Bucket<V> bucket = bucketList.get(start);
 if (bucket.displayBucket != null) {
 bucket = bucket.displayBucket;
            }
 return bucket.displayIndex;
        }


 /**
         * Private iterator over all the buckets, visible and invisible
         */
 private Iterator<Bucket<V>> fullIterator() {
 return bucketList.iterator();
        }


 /**
         * Iterator over just the visible buckets.
         */
 @Override
 public Iterator<Bucket<V>> iterator() {
 return immutableVisibleList.iterator(); // use immutable list to prevent remove().
        }
    }