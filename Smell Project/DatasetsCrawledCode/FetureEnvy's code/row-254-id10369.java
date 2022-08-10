 @Override public boolean readFrom(ByteBuffer buf, MessageReader reader) {
 reader.setBuffer(buf);


 if (!reader.beforeMessageRead())
 return false;


 if (!super.readFrom(buf, reader))
 return false;


 switch (reader.state()) {
 case 3:
 futId = reader.readLong("futId");


 if (!reader.isLastRead())
 return false;


 reader.incrementState();


 case 4:
 locksArr = reader.readObjectArray("locksArr", MessageCollectionItemType.MSG, TxLockList.class);


 if (!reader.isLastRead())
 return false;


 reader.incrementState();


 case 5:
 nearTxKeysArr = reader.readObjectArray("nearTxKeysArr", MessageCollectionItemType.MSG, IgniteTxKey.class);


 if (!reader.isLastRead())
 return false;


 reader.incrementState();


 case 6:
 txKeysArr = reader.readObjectArray("txKeysArr", MessageCollectionItemType.MSG, IgniteTxKey.class);


 if (!reader.isLastRead())
 return false;


 reader.incrementState();


        }


 return reader.afterMessageRead(TxLocksResponse.class);
    }