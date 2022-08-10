 @Override
 @MultiMQAdminCmdMethod
 public Map<String, ConsumerGroupRollBackStat> resetOffset(ResetOffsetRequest resetOffsetRequest) {
 Map<String, ConsumerGroupRollBackStat> groupRollbackStats = Maps.newHashMap();
 for (String consumerGroup : resetOffsetRequest.getConsumerGroupList()) {
 try {
 Map<MessageQueue, Long> rollbackStatsMap =
 mqAdminExt.resetOffsetByTimestamp(resetOffsetRequest.getTopic(), consumerGroup, resetOffsetRequest.getResetTime(), resetOffsetRequest.isForce());
 ConsumerGroupRollBackStat consumerGroupRollBackStat = new ConsumerGroupRollBackStat(true);
 List<RollbackStats> rollbackStatsList = consumerGroupRollBackStat.getRollbackStatsList();
 for (Map.Entry<MessageQueue, Long> rollbackStatsEntty : rollbackStatsMap.entrySet()) {
 RollbackStats rollbackStats = new RollbackStats();
 rollbackStats.setRollbackOffset(rollbackStatsEntty.getValue());
 rollbackStats.setQueueId(rollbackStatsEntty.getKey().getQueueId());
 rollbackStats.setBrokerName(rollbackStatsEntty.getKey().getBrokerName());
 rollbackStatsList.add(rollbackStats);
                }
 groupRollbackStats.put(consumerGroup, consumerGroupRollBackStat);
            }
 catch (MQClientException e) {
 if (ResponseCode.CONSUMER_NOT_ONLINE == e.getResponseCode()) {
 try {
 ConsumerGroupRollBackStat consumerGroupRollBackStat = new ConsumerGroupRollBackStat(true);
 List<RollbackStats> rollbackStatsList = mqAdminExt.resetOffsetByTimestampOld(consumerGroup, resetOffsetRequest.getTopic(), resetOffsetRequest.getResetTime(), true);
 consumerGroupRollBackStat.setRollbackStatsList(rollbackStatsList);
 groupRollbackStats.put(consumerGroup, consumerGroupRollBackStat);
 continue;
                    }
 catch (Exception err) {
 logger.error("op=resetOffset_which_not_online_error", err);
                    }
                }
 else {
 logger.error("op=resetOffset_error", e);
                }
 groupRollbackStats.put(consumerGroup, new ConsumerGroupRollBackStat(false, e.getMessage()));
            }
 catch (Exception e) {
 logger.error("op=resetOffset_error", e);
 groupRollbackStats.put(consumerGroup, new ConsumerGroupRollBackStat(false, e.getMessage()));
            }
        }
 return groupRollbackStats;
    }