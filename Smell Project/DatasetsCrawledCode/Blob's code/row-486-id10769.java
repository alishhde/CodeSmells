 public static class MissedUpdatesFinder extends MissedUpdatesFinderBase {
 private long ourHighThreshold; // 80th percentile
 private long ourHighest;  // currently just used for logging/debugging purposes
 private String logPrefix;
 private long nUpdates;


 MissedUpdatesFinder(List<Long> ourUpdates, String logPrefix, long nUpdates,
 long ourLowThreshold, long ourHighThreshold) {
 super(ourUpdates, ourLowThreshold);


 this.logPrefix = logPrefix;
 this.ourHighThreshold = ourHighThreshold;
 this.ourHighest = ourUpdates.get(0);
 this.nUpdates = nUpdates;
    }


 public MissedUpdatesRequest find(List<Long> otherVersions, Object updateFrom, Supplier<Boolean> canHandleVersionRanges) {
 otherVersions.sort(absComparator);
 if (debug) {
 log.debug("{} sorted versions from {} = {}", logPrefix, otherVersions, updateFrom);
      }


 long otherHigh = percentile(otherVersions, .2f);
 long otherLow = percentile(otherVersions, .8f);
 long otherHighest = otherVersions.get(0);


 if (ourHighThreshold < otherLow) {
 // Small overlap between version windows and ours is older
 // This means that we might miss updates if we attempted to use this method.
 // Since there exists just one replica that is so much newer, we must
 // fail the sync.
 log.info("{} Our versions are too old. ourHighThreshold={} otherLowThreshold={} ourHighest={} otherHighest={}",
 logPrefix, ourHighThreshold, otherLow, ourHighest, otherHighest);
 return MissedUpdatesRequest.UNABLE_TO_SYNC;
      }


 if (ourLowThreshold > otherHigh && ourHighest >= otherHighest) {
 // Small overlap between windows and ours is newer.
 // Using this list to sync would result in requesting/replaying results we don't need
 // and possibly bringing deleted docs back to life.
 log.info("{} Our versions are newer. ourHighThreshold={} otherLowThreshold={} ourHighest={} otherHighest={}",
 logPrefix, ourHighThreshold, otherLow, ourHighest, otherHighest);


 // Because our versions are newer, IndexFingerprint with the remote would not match us.
 // We return true on our side, but the remote peersync with us should fail.
 return MissedUpdatesRequest.ALREADY_IN_SYNC;
      }


 boolean completeList = otherVersions.size() < nUpdates;


 MissedUpdatesRequest updatesRequest;
 if (canHandleVersionRanges.get()) {
 updatesRequest = handleVersionsWithRanges(otherVersions, completeList);
      } else {
 updatesRequest = handleIndividualVersions(otherVersions, completeList);
      }


 if (updatesRequest.totalRequestedUpdates > nUpdates) {
 log.info("{} PeerSync will fail because number of missed updates is more than:{}", logPrefix, nUpdates);
 return MissedUpdatesRequest.UNABLE_TO_SYNC;
      }


 if (updatesRequest == MissedUpdatesRequest.EMPTY) {
 log.info("{} No additional versions requested. ourHighThreshold={} otherLowThreshold={} ourHighest={} otherHighest={}",
 logPrefix, ourHighThreshold, otherLow, ourHighest, otherHighest);
      }


 return updatesRequest;
    }
  }