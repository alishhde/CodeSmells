 @Override
 public ListenableFuture<Void> recover(QueueManagingVirtualHost<?> virtualHost)
    {
 EventLogger eventLogger = virtualHost.getEventLogger();
 MessageStore store = virtualHost.getMessageStore();
 MessageStore.MessageStoreReader storeReader = store.newMessageStoreReader();
 MessageStoreLogSubject logSubject = new MessageStoreLogSubject(virtualHost.getName(), store.getClass().getSimpleName());


 Map<Queue<?>, Integer> queueRecoveries = new TreeMap<>();
 Map<Long, ServerMessage<?>> recoveredMessages = new HashMap<>();
 Map<Long, StoredMessage<?>> unusedMessages = new TreeMap<>();
 Map<UUID, Integer> unknownQueuesWithMessages = new HashMap<>();
 Map<Queue<?>, Integer> queuesWithUnknownMessages = new HashMap<>();


 eventLogger.message(logSubject, MessageStoreMessages.RECOVERY_START());


 storeReader.visitMessages(new MessageVisitor(recoveredMessages, unusedMessages));


 eventLogger.message(logSubject, TransactionLogMessages.RECOVERY_START(null, false));
 try
        {
 storeReader.visitMessageInstances(new MessageInstanceVisitor(virtualHost,
 store,
 queueRecoveries,
 recoveredMessages,
 unusedMessages,
 unknownQueuesWithMessages,
 queuesWithUnknownMessages));
        }
 finally
        {
 if (!unknownQueuesWithMessages.isEmpty())
            {
 unknownQueuesWithMessages.forEach((queueId, count) -> {
 LOGGER.info("Discarded {} entry(s) associated with queue id '{}' as a queue with this "
                                 + "id does not appear in the configuration.",
 count, queueId);
                });
            }
 if (!queuesWithUnknownMessages.isEmpty())
            {
 queuesWithUnknownMessages.forEach((queue, count) -> {
 LOGGER.info("Discarded {} entry(s) associated with queue '{}' as the referenced message "
                                 + "does not exist.",
 count, queue.getName());
                });
            }
        }


 for(Map.Entry<Queue<?>, Integer> entry : queueRecoveries.entrySet())
        {
 Queue<?> queue = entry.getKey();
 Integer deliveredCount = entry.getValue();
 eventLogger.message(logSubject, TransactionLogMessages.RECOVERED(deliveredCount, queue.getName()));
 eventLogger.message(logSubject, TransactionLogMessages.RECOVERY_COMPLETE(queue.getName(), true));
 queue.completeRecovery();
        }


 for (Queue<?> q : virtualHost.getChildren(Queue.class))
        {
 if (!queueRecoveries.containsKey(q))
            {
 q.completeRecovery();
            }
        }


 storeReader.visitDistributedTransactions(new DistributedTransactionVisitor(virtualHost,
 eventLogger,
 logSubject, recoveredMessages, unusedMessages));


 for(StoredMessage<?> m : unusedMessages.values())
        {
 LOGGER.debug("Message id '{}' is orphaned, removing", m.getMessageNumber());
 m.remove();
        }


 if (unusedMessages.size() > 0)
        {
 LOGGER.info("Discarded {} orphaned message(s).", unusedMessages.size());
        }


 eventLogger.message(logSubject, TransactionLogMessages.RECOVERY_COMPLETE(null, false));


 eventLogger.message(logSubject,
 MessageStoreMessages.RECOVERED(recoveredMessages.size() - unusedMessages.size()));
 eventLogger.message(logSubject, MessageStoreMessages.RECOVERY_COMPLETE());


 return Futures.immediateFuture(null);
    }