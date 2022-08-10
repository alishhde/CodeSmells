 private void doDefensiveChecks(DistribPhase phase) {
 boolean isReplayOrPeersync = (updateCommand.getFlags() & (UpdateCommand.REPLAY | UpdateCommand.PEER_SYNC)) != 0;
 if (isReplayOrPeersync) return;


 String from = req.getParams().get(DISTRIB_FROM);
 ClusterState clusterState = zkController.getClusterState();


 DocCollection docCollection = clusterState.getCollection(collection);
 Slice mySlice = docCollection.getSlice(cloudDesc.getShardId());
 boolean localIsLeader = cloudDesc.isLeader();
 if (DistribPhase.FROMLEADER == phase && localIsLeader && from != null) { // from will be null on log replay
 String fromShard = req.getParams().get(DISTRIB_FROM_PARENT);
 if (fromShard != null) {
 if (mySlice.getState() == Slice.State.ACTIVE)  {
 throw new SolrException(SolrException.ErrorCode.SERVICE_UNAVAILABLE,
 "Request says it is coming from parent shard leader but we are in active state");
        }
 // shard splitting case -- check ranges to see if we are a sub-shard
 Slice fromSlice = docCollection.getSlice(fromShard);
 DocRouter.Range parentRange = fromSlice.getRange();
 if (parentRange == null) parentRange = new DocRouter.Range(Integer.MIN_VALUE, Integer.MAX_VALUE);
 if (mySlice.getRange() != null && !mySlice.getRange().isSubsetOf(parentRange)) {
 throw new SolrException(SolrException.ErrorCode.SERVICE_UNAVAILABLE,
 "Request says it is coming from parent shard leader but parent hash range is not superset of my range");
        }
      } else {
 String fromCollection = req.getParams().get(DISTRIB_FROM_COLLECTION); // is it because of a routing rule?
 if (fromCollection == null)  {
 log.error("Request says it is coming from leader, but we are the leader: " + req.getParamString());
 SolrException solrExc = new SolrException(SolrException.ErrorCode.SERVICE_UNAVAILABLE, "Request says it is coming from leader, but we are the leader");
 solrExc.setMetadata("cause", "LeaderChanged");
 throw solrExc;
        }
      }
    }


 int count = 0;
 while (((isLeader && !localIsLeader) || (isSubShardLeader && !localIsLeader)) && count < 5) {
 count++;
 // re-getting localIsLeader since we published to ZK first before setting localIsLeader value
 localIsLeader = cloudDesc.isLeader();
 try {
 Thread.sleep(500);
      } catch (InterruptedException e) {
 Thread.currentThread().interrupt();
      }
    }


 if ((isLeader && !localIsLeader) || (isSubShardLeader && !localIsLeader)) {
 log.error("ClusterState says we are the leader, but locally we don't think so");
 throw new SolrException(SolrException.ErrorCode.SERVICE_UNAVAILABLE,
 "ClusterState says we are the leader (" + zkController.getBaseUrl()
              + "/" + req.getCore().getName() + "), but locally we don't think so. Request came from " + from);
    }
  }