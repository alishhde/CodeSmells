 CompletableFuture<MessageId> getLastMessageIdAsync() {
 if (getState() == State.Closing || getState() == State.Closed) {
 return FutureUtil
                .failedFuture(new PulsarClientException.AlreadyClosedException("Consumer was already closed"));
        }


 AtomicLong opTimeoutMs = new AtomicLong(client.getConfiguration().getOperationTimeoutMs());
 Backoff backoff = new Backoff(100, TimeUnit.MILLISECONDS,
 opTimeoutMs.get() * 2, TimeUnit.MILLISECONDS,
 0 , TimeUnit.MILLISECONDS);
 CompletableFuture<MessageId> getLastMessageIdFuture = new CompletableFuture<>();


 internalGetLastMessageIdAsync(backoff, opTimeoutMs, getLastMessageIdFuture);
 return getLastMessageIdFuture;
    }