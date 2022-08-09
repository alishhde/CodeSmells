public class OptionSet implements UaStructure {


 public static final NodeId TypeId = Identifiers.OptionSet;
 public static final NodeId BinaryEncodingId = Identifiers.OptionSet_Encoding_DefaultBinary;
 public static final NodeId XmlEncodingId = Identifiers.OptionSet_Encoding_DefaultXml;


 protected final ByteString value;
 protected final ByteString validBits;


 public OptionSet() {
 this.value = null;
 this.validBits = null;
    }


 public OptionSet(ByteString value, ByteString validBits) {
 this.value = value;
 this.validBits = validBits;
    }


 public ByteString getValue() { return value; }


 public ByteString getValidBits() { return validBits; }


 @Override
 public NodeId getTypeId() { return TypeId; }


 @Override
 public NodeId getBinaryEncodingId() { return BinaryEncodingId; }


 @Override
 public NodeId getXmlEncodingId() { return XmlEncodingId; }


 @Override
 public String toString() {
 return MoreObjects.toStringHelper(this)
            .add("Value", value)
            .add("ValidBits", validBits)
            .toString();
    }


 public static class Codec extends BuiltinDataTypeCodec<OptionSet> {


 @Override
 public Class<OptionSet> getType() {
 return OptionSet.class;
        }


 @Override
 public OptionSet decode(UaDecoder decoder) throws UaSerializationException {
 ByteString value = decoder.readByteString("Value");
 ByteString validBits = decoder.readByteString("ValidBits");


 return new OptionSet(value, validBits);
        }


 @Override
 public void encode(OptionSet value, UaEncoder encoder) throws UaSerializationException {
 encoder.writeByteString("Value", value.value);
 encoder.writeByteString("ValidBits", value.validBits);
        }
    }


}