public class NetworkBridgeStatistics extends StatsImpl {


 protected CountStatisticImpl enqueues;
 protected CountStatisticImpl dequeues;
 protected CountStatisticImpl receivedCount;


 public NetworkBridgeStatistics() {
 enqueues = new CountStatisticImpl("enqueues", "The current number of enqueues this bridge has, which is the number of potential messages to be forwarded.");
 dequeues = new CountStatisticImpl("dequeues", "The current number of dequeues this bridge has, which is the number of messages received by the remote broker.");
 receivedCount = new CountStatisticImpl("receivedCount", "The number of messages that have been received by the NetworkBridge from the remote broker.  Only applies for Duplex bridges.");


 addStatistic("enqueues", enqueues);
 addStatistic("dequeues", dequeues);
 addStatistic("receivedCount", receivedCount);
    }


 /**
     * The current number of enqueues this bridge has, which is the number of potential messages to be forwarded
     * Messages may not be forwarded if there is no subscription
     *
     * @return
     */
 public CountStatisticImpl getEnqueues() {
 return enqueues;
    }


 /**
     * The current number of dequeues this bridge has, which is the number of
     * messages actually sent to and received by the remote broker.
     *
     * @return
     */
 public CountStatisticImpl getDequeues() {
 return dequeues;
    }


 /**
     * The number of messages that have been received by the NetworkBridge from the remote broker.
     * Only applies for Duplex bridges.
     *
     * @return
     */
 public CountStatisticImpl getReceivedCount() {
 return receivedCount;
    }


 @Override
 public void reset() {
 if (this.isDoReset()) {
 super.reset();
 enqueues.reset();
 dequeues.reset();
 receivedCount.reset();
        }
    }


 @Override
 public void setEnabled(boolean enabled) {
 super.setEnabled(enabled);
 enqueues.setEnabled(enabled);
 dequeues.setEnabled(enabled);
 receivedCount.setEnabled(enabled);
    }


 public void setParent(NetworkBridgeStatistics parent) {
 if (parent != null) {
 enqueues.setParent(parent.enqueues);
 dequeues.setParent(parent.dequeues);
 receivedCount.setParent(parent.receivedCount);
        } else {
 enqueues.setParent(null);
 dequeues.setParent(null);
 receivedCount.setParent(null);
        }
    }


}