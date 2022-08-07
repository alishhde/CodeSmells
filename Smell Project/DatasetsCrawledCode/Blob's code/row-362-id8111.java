public class UpdateErrors implements Message {
 /** */
 private static final long serialVersionUID = 0L;


 /** Failed keys. */
 @GridToStringInclude
 @GridDirectCollection(KeyCacheObject.class)
 private List<KeyCacheObject> failedKeys;


 /** Update error. */
 @GridDirectTransient
 @GridToStringInclude
 private IgniteCheckedException err;


 /** Serialized update error. */
 private byte[] errBytes;


 /**
     *
     */
 public UpdateErrors() {
 // No-op.
    }


 /**
     * @param err Error.
     */
 public UpdateErrors(IgniteCheckedException err) {
 assert err != null;


 this.err = err;
    }


 /**
     * @param err Error.
     */
 public void onError(IgniteCheckedException err){
 this.err = err;
    }


 /**
     * @return Error.
     */
 public IgniteCheckedException error() {
 return err;
    }


 /**
     * @return Failed keys.
     */
 public Collection<KeyCacheObject> failedKeys() {
 return failedKeys;
    }


 /**
     * Adds key to collection of failed keys.
     *
     * @param key Key to add.
     * @param e Error cause.
     */
 void addFailedKey(KeyCacheObject key, Throwable e) {
 if (failedKeys == null)
 failedKeys = new ArrayList<>();


 failedKeys.add(key);


 if (err == null)
 err = new IgniteCheckedException("Failed to update keys.");


 err.addSuppressed(e);
    }


 /**
     * @param keys Keys.
     * @param e Error.
     */
 void addFailedKeys(Collection<KeyCacheObject> keys, Throwable e) {
 if (failedKeys == null)
 failedKeys = new ArrayList<>(keys.size());


 failedKeys.addAll(keys);


 if (err == null)
 err = new IgniteCheckedException("Failed to update keys on primary node.");


 err.addSuppressed(e);
    }


 /** {@inheritDoc} */
 void prepareMarshal(GridCacheMessage msg, GridCacheContext cctx) throws IgniteCheckedException {
 msg.prepareMarshalCacheObjects(failedKeys, cctx);


 if (errBytes == null)
 errBytes = U.marshal(cctx.marshaller(), err);
    }


 /** {@inheritDoc} */
 void finishUnmarshal(GridCacheMessage msg, GridCacheContext cctx, ClassLoader ldr) throws IgniteCheckedException {
 msg.finishUnmarshalCacheObjects(failedKeys, cctx, ldr);


 if (errBytes != null && err == null)
 err = U.unmarshal(cctx.marshaller(), errBytes, U.resolveClassLoader(ldr, cctx.gridConfig()));
    }


 /** {@inheritDoc} */
 @Override public boolean writeTo(ByteBuffer buf, MessageWriter writer) {
 writer.setBuffer(buf);


 if (!writer.isHeaderWritten()) {
 if (!writer.writeHeader(directType(), fieldsCount()))
 return false;


 writer.onHeaderWritten();
        }


 switch (writer.state()) {
 case 0:
 if (!writer.writeByteArray("errBytes", errBytes))
 return false;


 writer.incrementState();


 case 1:
 if (!writer.writeCollection("failedKeys", failedKeys, MessageCollectionItemType.MSG))
 return false;


 writer.incrementState();


        }


 return true;
    }


 /** {@inheritDoc} */
 @Override public boolean readFrom(ByteBuffer buf, MessageReader reader) {
 reader.setBuffer(buf);


 if (!reader.beforeMessageRead())
 return false;


 switch (reader.state()) {
 case 0:
 errBytes = reader.readByteArray("errBytes");


 if (!reader.isLastRead())
 return false;


 reader.incrementState();


 case 1:
 failedKeys = reader.readCollection("failedKeys", MessageCollectionItemType.MSG);


 if (!reader.isLastRead())
 return false;


 reader.incrementState();


        }


 return reader.afterMessageRead(UpdateErrors.class);
    }


 /** {@inheritDoc} */
 @Override public short directType() {
 return -49;
    }


 /** {@inheritDoc} */
 @Override public byte fieldsCount() {
 return 2;
    }


 /** {@inheritDoc} */
 @Override public void onAckReceived() {
 // No-op.
    }


 /** {@inheritDoc} */
 @Override public String toString() {
 return S.toString(UpdateErrors.class, this);
    }
}