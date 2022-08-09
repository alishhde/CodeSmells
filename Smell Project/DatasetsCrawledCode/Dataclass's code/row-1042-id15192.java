public class SlaveSynchronize {
 private static final InternalLogger log = InternalLoggerFactory.getLogger(LoggerName.BROKER_LOGGER_NAME);
 private final BrokerController brokerController;
 private volatile String masterAddr = null;


 public SlaveSynchronize(BrokerController brokerController) {
 this.brokerController = brokerController;
    }


 public String getMasterAddr() {
 return masterAddr;
    }


 public void setMasterAddr(String masterAddr) {
 this.masterAddr = masterAddr;
    }


 public void syncAll() {
 this.syncTopicConfig();
 this.syncConsumerOffset();
 this.syncDelayOffset();
 this.syncSubscriptionGroupConfig();
    }


 private void syncTopicConfig() {
 String masterAddrBak = this.masterAddr;
 if (masterAddrBak != null && !masterAddrBak.equals(brokerController.getBrokerAddr())) {
 try {
 TopicConfigSerializeWrapper topicWrapper =
 this.brokerController.getBrokerOuterAPI().getAllTopicConfig(masterAddrBak);
 if (!this.brokerController.getTopicConfigManager().getDataVersion()
                    .equals(topicWrapper.getDataVersion())) {


 this.brokerController.getTopicConfigManager().getDataVersion()
                        .assignNewOne(topicWrapper.getDataVersion());
 this.brokerController.getTopicConfigManager().getTopicConfigTable().clear();
 this.brokerController.getTopicConfigManager().getTopicConfigTable()
                        .putAll(topicWrapper.getTopicConfigTable());
 this.brokerController.getTopicConfigManager().persist();


 log.info("Update slave topic config from master, {}", masterAddrBak);
                }
            } catch (Exception e) {
 log.error("SyncTopicConfig Exception, {}", masterAddrBak, e);
            }
        }
    }


 private void syncConsumerOffset() {
 String masterAddrBak = this.masterAddr;
 if (masterAddrBak != null && !masterAddrBak.equals(brokerController.getBrokerAddr())) {
 try {
 ConsumerOffsetSerializeWrapper offsetWrapper =
 this.brokerController.getBrokerOuterAPI().getAllConsumerOffset(masterAddrBak);
 this.brokerController.getConsumerOffsetManager().getOffsetTable()
                    .putAll(offsetWrapper.getOffsetTable());
 this.brokerController.getConsumerOffsetManager().persist();
 log.info("Update slave consumer offset from master, {}", masterAddrBak);
            } catch (Exception e) {
 log.error("SyncConsumerOffset Exception, {}", masterAddrBak, e);
            }
        }
    }


 private void syncDelayOffset() {
 String masterAddrBak = this.masterAddr;
 if (masterAddrBak != null && !masterAddrBak.equals(brokerController.getBrokerAddr())) {
 try {
 String delayOffset =
 this.brokerController.getBrokerOuterAPI().getAllDelayOffset(masterAddrBak);
 if (delayOffset != null) {


 String fileName =
 StorePathConfigHelper.getDelayOffsetStorePath(this.brokerController
                            .getMessageStoreConfig().getStorePathRootDir());
 try {
 MixAll.string2File(delayOffset, fileName);
                    } catch (IOException e) {
 log.error("Persist file Exception, {}", fileName, e);
                    }
                }
 log.info("Update slave delay offset from master, {}", masterAddrBak);
            } catch (Exception e) {
 log.error("SyncDelayOffset Exception, {}", masterAddrBak, e);
            }
        }
    }


 private void syncSubscriptionGroupConfig() {
 String masterAddrBak = this.masterAddr;
 if (masterAddrBak != null  && !masterAddrBak.equals(brokerController.getBrokerAddr())) {
 try {
 SubscriptionGroupWrapper subscriptionWrapper =
 this.brokerController.getBrokerOuterAPI()
                        .getAllSubscriptionGroupConfig(masterAddrBak);


 if (!this.brokerController.getSubscriptionGroupManager().getDataVersion()
                    .equals(subscriptionWrapper.getDataVersion())) {
 SubscriptionGroupManager subscriptionGroupManager =
 this.brokerController.getSubscriptionGroupManager();
 subscriptionGroupManager.getDataVersion().assignNewOne(
 subscriptionWrapper.getDataVersion());
 subscriptionGroupManager.getSubscriptionGroupTable().clear();
 subscriptionGroupManager.getSubscriptionGroupTable().putAll(
 subscriptionWrapper.getSubscriptionGroupTable());
 subscriptionGroupManager.persist();
 log.info("Update slave Subscription Group from master, {}", masterAddrBak);
                }
            } catch (Exception e) {
 log.error("SyncSubscriptionGroup Exception, {}", masterAddrBak, e);
            }
        }
    }
}