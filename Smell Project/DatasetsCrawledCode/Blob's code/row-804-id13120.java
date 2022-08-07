public class BatchWriterReplicationReplayer implements AccumuloReplicationReplayer {
 private static final Logger log = LoggerFactory.getLogger(BatchWriterReplicationReplayer.class);


 @Override
 public long replicateLog(ClientContext context, String tableName, WalEdits data)
 throws RemoteReplicationException {
 final LogFileKey key = new LogFileKey();
 final LogFileValue value = new LogFileValue();
 final long memoryInBytes = context.getConfiguration()
        .getAsBytes(Property.TSERV_REPLICATION_BW_REPLAYER_MEMORY);


 BatchWriter bw = null;
 long mutationsApplied = 0L;
 try {
 for (ByteBuffer edit : data.getEdits()) {
 DataInputStream dis = new DataInputStream(ByteBufferUtil.toByteArrayInputStream(edit));
 try {
 key.readFields(dis);
 // TODO this is brittle because AccumuloReplicaSystem isn't actually calling
 // LogFileValue.write, but we're expecting
 // what we receive to be readable by the LogFileValue.
 value.readFields(dis);
        } catch (IOException e) {
 log.error("Could not deserialize edit from stream", e);
 throw new RemoteReplicationException(RemoteReplicationErrorCode.COULD_NOT_DESERIALIZE,
 "Could not deserialize edit from stream");
        }


 // Create the batchScanner if we don't already have one.
 if (bw == null) {
 BatchWriterConfig bwConfig = new BatchWriterConfig();
 bwConfig.setMaxMemory(memoryInBytes);
 try {
 bw = context.createBatchWriter(tableName, bwConfig);
          } catch (TableNotFoundException e) {
 throw new RemoteReplicationException(RemoteReplicationErrorCode.TABLE_DOES_NOT_EXIST,
 "Table " + tableName + " does not exist");
          }
        }


 log.info("Applying {} mutations to table {} as part of batch", value.mutations.size(),
 tableName);


 // If we got a ServerMutation, we have to make sure that we preserve the systemTimestamp
 // otherwise
 // the local system will assign a new timestamp.
 List<Mutation> mutationsCopy = new ArrayList<>(value.mutations.size());
 long mutationsCopied = 0L;
 for (Mutation orig : value.mutations) {
 if (orig instanceof ServerMutation) {
 mutationsCopied++;


 ServerMutation origServer = (ServerMutation) orig;
 Mutation copy = new Mutation(orig.getRow());
 for (ColumnUpdate update : orig.getUpdates()) {
 long timestamp;


 // If the update doesn't have a timestamp, pull it from the ServerMutation
 if (!update.hasTimestamp()) {
 timestamp = origServer.getSystemTimestamp();
              } else {
 timestamp = update.getTimestamp();
              }


 // TODO ACCUMULO-2937 cache the CVs
 if (update.isDeleted()) {
 copy.putDelete(update.getColumnFamily(), update.getColumnQualifier(),
 new ColumnVisibility(update.getColumnVisibility()), timestamp);
              } else {
 copy.put(update.getColumnFamily(), update.getColumnQualifier(),
 new ColumnVisibility(update.getColumnVisibility()), timestamp,
 update.getValue());
              }
            }


 // We also need to preserve the replicationSource information to prevent cycles
 Set<String> replicationSources = orig.getReplicationSources();
 if (replicationSources != null && !replicationSources.isEmpty()) {
 for (String replicationSource : replicationSources) {
 copy.addReplicationSource(replicationSource);
              }
            }


 mutationsCopy.add(copy);
          } else {
 mutationsCopy.add(orig);
          }
        }


 log.debug("Copied {} mutations to ensure server-assigned timestamps are propagated",
 mutationsCopied);


 try {
 bw.addMutations(mutationsCopy);
        } catch (MutationsRejectedException e) {
 log.error("Could not apply mutations to {}", tableName);
 throw new RemoteReplicationException(RemoteReplicationErrorCode.COULD_NOT_APPLY,
 "Could not apply mutations to " + tableName);
        }


 log.debug("{} mutations added to the BatchScanner", mutationsCopy.size());


 mutationsApplied += mutationsCopy.size();
      }
    } finally {
 if (bw != null) {
 try {
 bw.close();
        } catch (MutationsRejectedException e) {
 log.error("Could not apply mutations to {}", tableName);
 throw new RemoteReplicationException(RemoteReplicationErrorCode.COULD_NOT_APPLY,
 "Could not apply mutations to " + tableName);
        }
      }
    }


 log.info("Applied {} mutations in total to {}", mutationsApplied, tableName);


 return mutationsApplied;
  }


}