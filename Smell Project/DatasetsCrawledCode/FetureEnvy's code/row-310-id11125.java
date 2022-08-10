 private ShardHandler getAndPrepShardHandler(SolrQueryRequest req, ResponseBuilder rb) {
 ShardHandler shardHandler = null;


 CoreContainer cc = req.getCore().getCoreContainer();
 boolean isZkAware = cc.isZooKeeperAware();
 rb.isDistrib = req.getParams().getBool(DISTRIB, isZkAware);
 if (!rb.isDistrib) {
 // for back compat, a shards param with URLs like localhost:8983/solr will mean that this
 // search is distributed.
 final String shards = req.getParams().get(ShardParams.SHARDS);
 rb.isDistrib = ((shards != null) && (shards.indexOf('/') > 0));
    }
 
 if (rb.isDistrib) {
 shardHandler = shardHandlerFactory.getShardHandler();
 shardHandler.prepDistributed(rb);
 if (!rb.isDistrib) {
 shardHandler = null; // request is not distributed after all and so the shard handler is not needed
      }
    }


 if (isZkAware) {
 String shardsTolerant = req.getParams().get(ShardParams.SHARDS_TOLERANT);
 boolean requireZkConnected = shardsTolerant != null && shardsTolerant.equals(ShardParams.REQUIRE_ZK_CONNECTED);
 ZkController zkController = cc.getZkController();
 boolean zkConnected = zkController != null && ! zkController.getZkClient().getConnectionManager().isLikelyExpired();
 if (requireZkConnected && false == zkConnected) {
 throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "ZooKeeper is not connected");
      } else {
 NamedList<Object> headers = rb.rsp.getResponseHeader();
 if (headers != null) {
 headers.add("zkConnected", zkConnected);
        }
      }
    }


 return shardHandler;
  }