 public void tryStoreVectorizedKey(HiveKey key, boolean partColsIsNull, int batchIndex)
 throws HiveException, IOException {
 // Assumption - batchIndex is increasing; startVectorizedBatch was called
 int size = indexes.size();
 int index = size < topN ? size : evicted;
 keys[index] = Arrays.copyOf(key.getBytes(), key.getLength());
 distKeyLengths[index] = key.getDistKeyLength();
 hashes[index] = key.hashCode();
 Integer collisionIndex = indexes.store(index);
 if (null != collisionIndex) {
 /*
       * since there is a collision index will be used for the next value 
       * so have the map point back to original index.
       */
 if ( indexes instanceof HashForGroup ) {
 indexes.store(collisionIndex);
      }
 // forward conditional on the survival of the corresponding key currently in indexes.
      ++batchNumForwards;
 batchIndexToResult[batchIndex] = MAY_FORWARD - collisionIndex;
 return;
    }
 indexToBatchIndex[index] = batchIndex;
 batchIndexToResult[batchIndex] = index;
 if (size != topN) return;
 evicted = indexes.removeBiggest();  // remove the biggest key
 if (index == evicted) {
 excluded++;
 batchIndexToResult[batchIndex] = EXCLUDE;
 indexToBatchIndex[index] = -1;
 return; // input key is bigger than any of keys in hash
    }
 removed(evicted);
 int evictedBatchIndex = indexToBatchIndex[evicted];
 if (evictedBatchIndex >= 0) {
 // reset the result for the evicted index
 batchIndexToResult[evictedBatchIndex] = EXCLUDE;
 indexToBatchIndex[evicted] = -1;
    }
 // Evict all results grouped with this index; it cannot be any key further in the batch.
 // If we evict a key from this batch, the keys grouped with it cannot be earlier that that key.
 // If we evict a key that is not from this batch, initial i = (-1) + 1 = 0, as intended.
 int evictedForward = (MAY_FORWARD - evicted);
 for (int i = evictedBatchIndex + 1; i < batchIndex && (batchNumForwards > 0); ++i) {
 if (batchIndexToResult[i] == evictedForward) {
 batchIndexToResult[i] = EXCLUDE;
        --batchNumForwards;
      }
    }
  }