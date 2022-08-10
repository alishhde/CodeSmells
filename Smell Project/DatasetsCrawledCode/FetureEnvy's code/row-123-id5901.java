 public int tightMarshal1(OpenWireFormat wireFormat, Object o, BooleanStream bs) throws IOException {


 MessageDispatchNotification info = (MessageDispatchNotification)o;


 int rc = super.tightMarshal1(wireFormat, o, bs);
 rc += tightMarshalCachedObject1(wireFormat, (DataStructure)info.getConsumerId(), bs);
 rc += tightMarshalCachedObject1(wireFormat, (DataStructure)info.getDestination(), bs);
 rc += tightMarshalLong1(wireFormat, info.getDeliverySequenceId(), bs);
 rc += tightMarshalNestedObject1(wireFormat, (DataStructure)info.getMessageId(), bs);


 return rc + 0;
    }