 private void positionWriterAtCheckpoint() {
 writerChkptDK = new CheckpointDataKey(jobExecutionImpl.getJobInstance().getInstanceId(), step.getId(), CheckpointType.WRITER);


 CheckpointData writerData = persistenceManagerService.getCheckpointData(writerChkptDK);
 try {
 // check for data in backing store
 if (writerData != null) {
 byte[] writertoken = writerData.getRestartToken();
 TCCLObjectInputStream writerOIS;
 try {
 writerProxy.open((Serializable) dataRepresentationService.toJavaRepresentation(writertoken));
                } catch (Exception ex) {
 // is this what I should be throwing here?
 throw new BatchContainerServiceException("Cannot read the checkpoint data for [" + step.getId() + "]", ex);
                }
            } else {
 // no chkpt data exists in the backing store
 writerData = null;
 try {
 writerProxy.open(null);
                } catch (Exception ex) {
 throw new BatchContainerServiceException("Cannot open the step [" + step.getId() + "]", ex);
                }
            }
        } catch (ClassCastException e) {
 throw new IllegalStateException("Expected CheckpointData but found" + writerData);
        }
    }