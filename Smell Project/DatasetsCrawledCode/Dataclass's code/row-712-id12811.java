 public static class WithName extends INodeReference {


 private final byte[] name;


 /**
     * The id of the last snapshot in the src tree when this WithName node was 
     * generated. When calculating the quota usage of the referred node, only 
     * the files/dirs existing when this snapshot was taken will be counted for 
     * this WithName node and propagated along its ancestor path.
     */
 private final int lastSnapshotId;
 
 public WithName(INodeDirectory parent, WithCount referred, byte[] name,
 int lastSnapshotId) {
 super(parent, referred);
 this.name = name;
 this.lastSnapshotId = lastSnapshotId;
 referred.addReference(this);
    }


 @Override
 public final byte[] getLocalNameBytes() {
 return name;
    }


 @Override
 public final void setLocalName(byte[] name) {
 throw new UnsupportedOperationException("Cannot set name: " + getClass()
          + " is immutable.");
    }
 
 public int getLastSnapshotId() {
 return lastSnapshotId;
    }
 
 @Override
 public final ContentSummaryComputationContext computeContentSummary(
 int snapshotId, ContentSummaryComputationContext summary) {
 final int s = snapshotId < lastSnapshotId ? snapshotId : lastSnapshotId;
 // only count storagespace for WithName
 final QuotaCounts q = computeQuotaUsage(
 summary.getBlockStoragePolicySuite(), getStoragePolicyID(), false, s);
 summary.getCounts().addContent(Content.DISKSPACE, q.getStorageSpace());
 summary.getCounts().addTypeSpaces(q.getTypeSpaces());
 return summary;
    }


 @Override
 public final QuotaCounts computeQuotaUsage(BlockStoragePolicySuite bsps,
 byte blockStoragePolicyId, boolean useCache, int lastSnapshotId) {
 // if this.lastSnapshotId < lastSnapshotId, the rename of the referred
 // node happened before the rename of its ancestor. This should be
 // impossible since for WithName node we only count its children at the
 // time of the rename.
 Preconditions.checkState(lastSnapshotId == Snapshot.CURRENT_STATE_ID
          || this.lastSnapshotId >= lastSnapshotId);
 final INode referred = this.getReferredINode().asReference()
          .getReferredINode();
 // We will continue the quota usage computation using the same snapshot id
 // as time line (if the given snapshot id is valid). Also, we cannot use 
 // cache for the referred node since its cached quota may have already 
 // been updated by changes in the current tree.
 int id = lastSnapshotId != Snapshot.CURRENT_STATE_ID ? 
 lastSnapshotId : this.lastSnapshotId;
 return referred.computeQuotaUsage(bsps, blockStoragePolicyId, false, id);
    }
 
 @Override
 public void cleanSubtree(ReclaimContext reclaimContext, final int snapshot,
 int prior) {
 // since WithName node resides in deleted list acting as a snapshot copy,
 // the parameter snapshot must be non-null
 Preconditions.checkArgument(snapshot != Snapshot.CURRENT_STATE_ID);
 // if prior is NO_SNAPSHOT_ID, we need to check snapshot belonging to the
 // previous WithName instance
 if (prior == Snapshot.NO_SNAPSHOT_ID) {
 prior = getPriorSnapshot(this);
      }
 
 if (prior != Snapshot.NO_SNAPSHOT_ID
          && Snapshot.ID_INTEGER_COMPARATOR.compare(snapshot, prior) <= 0) {
 return;
      }


 // record the old quota delta
 QuotaCounts old = reclaimContext.quotaDelta().getCountsCopy();
 getReferredINode().cleanSubtree(reclaimContext, snapshot, prior);
 INodeReference ref = getReferredINode().getParentReference();
 if (ref != null) {
 QuotaCounts current = reclaimContext.quotaDelta().getCountsCopy();
 current.subtract(old);
 // we need to update the quota usage along the parent path from ref
 reclaimContext.quotaDelta().addUpdatePath(ref, current);
      }
 
 if (snapshot < lastSnapshotId) {
 // for a WithName node, when we compute its quota usage, we only count
 // in all the nodes existing at the time of the corresponding rename op.
 // Thus if we are deleting a snapshot before/at the snapshot associated 
 // with lastSnapshotId, we do not need to update the quota upwards.
 reclaimContext.quotaDelta().setCounts(old);
      }
    }
 
 @Override
 public void destroyAndCollectBlocks(ReclaimContext reclaimContext) {
 int snapshot = getSelfSnapshot();
 reclaimContext.quotaDelta().add(computeQuotaUsage(reclaimContext.bsps));
 if (removeReference(this) <= 0) {
 getReferredINode().destroyAndCollectBlocks(reclaimContext.getCopy());
      } else {
 int prior = getPriorSnapshot(this);
 INode referred = getReferredINode().asReference().getReferredINode();


 if (snapshot != Snapshot.NO_SNAPSHOT_ID) {
 if (prior != Snapshot.NO_SNAPSHOT_ID && snapshot <= prior) {
 // the snapshot to be deleted has been deleted while traversing 
 // the src tree of the previous rename operation. This usually 
 // happens when rename's src and dst are under the same 
 // snapshottable directory. E.g., the following operation sequence:
 // 1. create snapshot s1 on /test
 // 2. rename /test/foo/bar to /test/foo2/bar
 // 3. create snapshot s2 on /test
 // 4. rename foo2 again
 // 5. delete snapshot s2
 return;
          }
 ReclaimContext newCtx = reclaimContext.getCopy();
 referred.cleanSubtree(newCtx, snapshot, prior);
 INodeReference ref = getReferredINode().getParentReference();
 if (ref != null) {
 // we need to update the quota usage along the parent path from ref
 reclaimContext.quotaDelta().addUpdatePath(ref,
 newCtx.quotaDelta().getCountsCopy());
          }
        }
      }
    }
 
 private int getSelfSnapshot() {
 INode referred = getReferredINode().asReference().getReferredINode();
 int snapshot = Snapshot.NO_SNAPSHOT_ID;
 if (referred.isFile() && referred.asFile().isWithSnapshot()) {
 snapshot = referred.asFile().getDiffs().getPrior(lastSnapshotId);
      } else if (referred.isDirectory()) {
 DirectoryWithSnapshotFeature sf = referred.asDirectory()
            .getDirectoryWithSnapshotFeature();
 if (sf != null) {
 snapshot = sf.getDiffs().getPrior(lastSnapshotId);
        }
      }
 return snapshot;
    }
  }