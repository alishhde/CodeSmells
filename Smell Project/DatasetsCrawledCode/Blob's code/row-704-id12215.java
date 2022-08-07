public class GridH2DmlResponse implements Message, GridCacheQueryMarshallable {
 /** */
 private static final long serialVersionUID = 0L;


 /** Request id. */
 @GridToStringInclude
 private long reqId;


 /** Number of updated rows. */
 @GridToStringInclude
 private long updCnt;


 /** Error message. */
 @GridToStringInclude
 private String err;


 /** Keys that failed. */
 @GridToStringInclude
 @GridDirectTransient
 private Object[] errKeys;


 /** Keys that failed (after marshalling). */
 private byte[] errKeysBytes;


 /**
     * Default constructor.
     */
 public GridH2DmlResponse() {
 // No-op.
    }


 /**
     * Constructor.
     *
     * @param reqId Request id.
     * @param updCnt Updated row number.
     * @param errKeys Erroneous keys.
     * @param error Error message.
     */
 public GridH2DmlResponse(long reqId, long updCnt, Object[] errKeys, String error) {
 this.reqId = reqId;
 this.updCnt = updCnt;
 this.errKeys = errKeys;
 this.err = error;
    }


 /**
     * @return Request id.
     */
 public long requestId() {
 return reqId;
    }


 /**
     * @return Update counter.
     */
 public long updateCounter() {
 return updCnt;
    }


 /**
     * @return Error keys.
     */
 public Object[] errorKeys() {
 return errKeys;
    }


 /**
     * @return Error message.
     */
 public String error() {
 return err;
    }


 /** {@inheritDoc} */
 @Override public void marshall(Marshaller m) {
 if (errKeysBytes != null || errKeys == null)
 return;


 try {
 errKeysBytes = U.marshal(m, errKeys);
        }
 catch (IgniteCheckedException e) {
 throw new IgniteException(e);
        }
    }


 /** {@inheritDoc} */
 @SuppressWarnings("IfMayBeConditional")
 @Override public void unmarshall(Marshaller m, GridKernalContext ctx) {
 if (errKeys != null || errKeysBytes == null)
 return;


 try {
 final ClassLoader ldr = U.resolveClassLoader(ctx.config());


 if (m instanceof BinaryMarshaller)
 // To avoid deserializing of enum types.
 errKeys = ((BinaryMarshaller)m).binaryMarshaller().unmarshal(errKeysBytes, ldr);
 else
 errKeys = U.unmarshal(m, errKeysBytes, ldr);
        }
 catch (IgniteCheckedException e) {
 throw new IgniteException(e);
        }
    }


 /** {@inheritDoc} */
 @Override public String toString() {
 return S.toString(GridH2DmlResponse.class, this);
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
 if (!writer.writeString("err", err))
 return false;


 writer.incrementState();


 case 1:
 if (!writer.writeByteArray("errKeysBytes", errKeysBytes))
 return false;


 writer.incrementState();


 case 2:
 if (!writer.writeLong("reqId", reqId))
 return false;


 writer.incrementState();


 case 3:
 if (!writer.writeLong("updCnt", updCnt))
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
 err = reader.readString("err");


 if (!reader.isLastRead())
 return false;


 reader.incrementState();


 case 1:
 errKeysBytes = reader.readByteArray("errKeysBytes");


 if (!reader.isLastRead())
 return false;


 reader.incrementState();


 case 2:
 reqId = reader.readLong("reqId");


 if (!reader.isLastRead())
 return false;


 reader.incrementState();


 case 3:
 updCnt = reader.readLong("updCnt");


 if (!reader.isLastRead())
 return false;


 reader.incrementState();


        }


 return reader.afterMessageRead(GridH2DmlResponse.class);
    }


 /** {@inheritDoc} */
 @Override public short directType() {
 return -56;
    }


 /** {@inheritDoc} */
 @Override public byte fieldsCount() {
 return 4;
    }


 @Override public void onAckReceived() {
 // No-op
    }
}