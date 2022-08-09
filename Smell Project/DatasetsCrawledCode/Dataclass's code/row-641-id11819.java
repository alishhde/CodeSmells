public abstract class AbstractDeadLetterStrategy implements DeadLetterStrategy {
 private static final Logger LOG = LoggerFactory.getLogger(AbstractDeadLetterStrategy.class);
 private boolean processNonPersistent = false;
 private boolean processExpired = true;
 private boolean enableAudit = true;
 private final ActiveMQMessageAudit messageAudit = new ActiveMQMessageAudit();
 private long expiration;


 @Override
 public void rollback(Message message) {
 if (message != null && this.enableAudit) {
 messageAudit.rollback(message);
        }
    }


 @Override
 public boolean isSendToDeadLetterQueue(Message message) {
 boolean result = false;
 if (message != null) {
 result = true;
 if (enableAudit && messageAudit.isDuplicate(message)) {
 result = false;
 LOG.debug("Not adding duplicate to DLQ: {}, dest: {}", message.getMessageId(), message.getDestination());
            }
 if (!message.isPersistent() && !processNonPersistent) {
 result = false;
            }
 if (message.isExpired() && !processExpired) {
 result = false;
            }
        }
 return result;
    }


 /**
     * @return the processExpired
     */
 @Override
 public boolean isProcessExpired() {
 return this.processExpired;
    }


 /**
     * @param processExpired the processExpired to set
     */
 @Override
 public void setProcessExpired(boolean processExpired) {
 this.processExpired = processExpired;
    }


 /**
     * @return the processNonPersistent
     */
 @Override
 public boolean isProcessNonPersistent() {
 return this.processNonPersistent;
    }


 /**
     * @param processNonPersistent the processNonPersistent to set
     */
 @Override
 public void setProcessNonPersistent(boolean processNonPersistent) {
 this.processNonPersistent = processNonPersistent;
    }


 public boolean isEnableAudit() {
 return enableAudit;
    }


 public void setEnableAudit(boolean enableAudit) {
 this.enableAudit = enableAudit;
    }


 public long getExpiration() {
 return expiration;
    }


 public void setExpiration(long expiration) {
 this.expiration = expiration;
    }


 public int getMaxProducersToAudit() {
 return messageAudit.getMaximumNumberOfProducersToTrack();
    }


 public void setMaxProducersToAudit(int maxProducersToAudit) {
 messageAudit.setMaximumNumberOfProducersToTrack(maxProducersToAudit);
    }


 public void setMaxAuditDepth(int maxAuditDepth) {
 messageAudit.setAuditDepth(maxAuditDepth);
    }


 public int getMaxAuditDepth() {
 return messageAudit.getAuditDepth();
    }


}