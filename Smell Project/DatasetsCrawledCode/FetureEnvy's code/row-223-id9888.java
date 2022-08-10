 private IgniteFuture<UUID> startRemoteListenAsync(BinaryRawReaderEx reader, IgniteMessaging messaging) {
 Object nativeFilter = reader.readObjectDetached();


 long ptr = reader.readLong();  // interop pointer


 Object topic = reader.readObjectDetached();


 PlatformMessageFilter filter = platformCtx.createRemoteMessageFilter(nativeFilter, ptr);


 return messaging.remoteListenAsync(topic, filter);
    }