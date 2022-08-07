 final class SequenceTermination extends TimerTask {


 private long maxInactivityTimeout;


 void updateInactivityTimeout(long timeout) {
 maxInactivityTimeout = Math.max(maxInactivityTimeout, timeout);
        }


 public void run() {
 synchronized (DestinationSequence.this) {
 DestinationSequence.this.scheduledTermination = null;
 RMEndpoint rme = destination.getReliableEndpoint();
 long lat = Math.max(rme.getLastControlMessage(), rme.getLastApplicationMessage());
 if (0 == lat) {
 return;
                }
 long now = System.currentTimeMillis();
 if (now - lat >= maxInactivityTimeout) {


 // terminate regardless outstanding acknowledgments - as we assume that the client is
 // gone there is no point in sending a SequenceAcknowledgment
 LogUtils.log(LOG, Level.WARNING, "TERMINATING_INACTIVE_SEQ_MSG",
 DestinationSequence.this.getIdentifier().getValue());
 DestinationSequence.this.destination.terminateSequence(DestinationSequence.this, true);
 Source source = rme.getSource();
 if (source != null) {
 SourceSequence ss = source.getAssociatedSequence(DestinationSequence.this.getIdentifier());
 if (ss != null) {
 source.removeSequence(ss);
                        }
                    }
                } else {
 // reschedule
 SequenceTermination st = new SequenceTermination();
 st.updateInactivityTimeout(maxInactivityTimeout);
 DestinationSequence.this.destination.getManager().getTimer()
                        .schedule(st, maxInactivityTimeout);
                }
            }
        }
    }