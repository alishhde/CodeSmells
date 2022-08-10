 private synchronized void checkHealth() {
 CalculatedStatus status = calculateStatus();
 boolean healthy = status.isHealthy();
 long now = System.currentTimeMillis();


 if (healthy) {
 stateLastGood.set(now);
 if (lastPublished == LastPublished.FAILED) {
 if (currentRecoveryStartTime == null) {
 LOG.info("{} check for {}, now recovering: {}", new Object[] {this, entity, getDescription(status)});
 currentRecoveryStartTime = now;
 schedulePublish();
                } else {
 if (LOG.isTraceEnabled()) LOG.trace("{} check for {}, continuing recovering: {}", new Object[] {this, entity, getDescription(status)});
                }
            } else {
 if (currentFailureStartTime != null) {
 LOG.info("{} check for {}, now healthy: {}", new Object[] {this, entity, getDescription(status)});
 currentFailureStartTime = null;
                } else {
 if (LOG.isTraceEnabled()) LOG.trace("{} check for {}, still healthy: {}", new Object[] {this, entity, getDescription(status)});
                }
            }
        } else {
 stateLastFail.set(now);
 if (lastPublished != LastPublished.FAILED) {
 if (currentFailureStartTime == null) {
 LOG.info("{} check for {}, now failing: {}", new Object[] {this, entity, getDescription(status)});
 currentFailureStartTime = now;
 schedulePublish();
                } else {
 if (LOG.isTraceEnabled()) LOG.trace("{} check for {}, continuing failing: {}", new Object[] {this, entity, getDescription(status)});
                }
            } else {
 if (currentRecoveryStartTime != null) {
 LOG.info("{} check for {}, now failing: {}", new Object[] {this, entity, getDescription(status)});
 currentRecoveryStartTime = null;
                } else {
 if (LOG.isTraceEnabled()) LOG.trace("{} check for {}, still failed: {}", new Object[] {this, entity, getDescription(status)});
                }
            }
        }
    }