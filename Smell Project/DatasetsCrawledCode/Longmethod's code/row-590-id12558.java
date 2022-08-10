 @Override
 public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event)
 throws Exception {


 try {
 if (isConnected() && (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)
          || event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)
          || event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED))) {
 synchronized (this) {
 Participant participant = leaderLatch.getLeader();
 if (isLeader(participant) && !leaderLatch.hasLeadership()) {
 // in case current instance becomes leader, we want to know who came before it.
 currentLeader = participant;
          }
        }
      }
    } catch (InterruptedException e) {
 log.warn("Oracle leadership watcher has been interrupted unexpectedly");
    }
  }