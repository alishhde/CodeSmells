@Experimental
class ValueEncoder {


 private final ValueSerializer valueSerializer;


 public ValueEncoder(ValueSerializer valueSerializer) {
 this.valueSerializer = valueSerializer;
  }


 /**
   * Encodes a Java object into a Protobuf encoded value.
   *
   * @param unencodedValue Java object to encode.
   * @return Encoded value of the Java object.
   */
 BasicTypes.EncodedValue encodeValue(Object unencodedValue) {
 BasicTypes.EncodedValue.Builder builder = BasicTypes.EncodedValue.newBuilder();


 if (valueSerializer.supportsPrimitives()) {
 ByteString customBytes = customSerialize(unencodedValue);
 return builder.setCustomObjectResult(customBytes).build();
    }


 if (Objects.isNull(unencodedValue)) {
 builder.setNullResult(NullValue.NULL_VALUE);
    } else if (Integer.class.equals(unencodedValue.getClass())) {
 builder.setIntResult((Integer) unencodedValue);
    } else if (Long.class.equals(unencodedValue.getClass())) {
 builder.setLongResult((Long) unencodedValue);
    } else if (Short.class.equals(unencodedValue.getClass())) {
 builder.setShortResult((Short) unencodedValue);
    } else if (Byte.class.equals(unencodedValue.getClass())) {
 builder.setByteResult((Byte) unencodedValue);
    } else if (Double.class.equals(unencodedValue.getClass())) {
 builder.setDoubleResult((Double) unencodedValue);
    } else if (Float.class.equals(unencodedValue.getClass())) {
 builder.setFloatResult((Float) unencodedValue);
    } else if (byte[].class.equals(unencodedValue.getClass())) {
 builder.setBinaryResult(ByteString.copyFrom((byte[]) unencodedValue));
    } else if (Boolean.class.equals(unencodedValue.getClass())) {
 builder.setBooleanResult((Boolean) unencodedValue);
    } else if (String.class.equals(unencodedValue.getClass())) {
 builder.setStringResult((String) unencodedValue);
    } else if (JSONWrapper.class.isAssignableFrom(unencodedValue.getClass())) {
 builder.setJsonObjectResult(((JSONWrapper) unencodedValue).getJSON());
    } else {
 ByteString customBytes = customSerialize(unencodedValue);
 if (customBytes != null) {
 builder.setCustomObjectResult(customBytes);
      } else {
 throw new IllegalStateException("We don't know how to handle an object of type "
            + unencodedValue.getClass() + ": " + unencodedValue);
      }
    }


 return builder.build();
  }


 private ByteString customSerialize(Object unencodedValue) {
 try {
 ByteString customBytes = valueSerializer.serialize(unencodedValue);
 return customBytes;
    } catch (IOException e) {
 throw new IllegalStateException(e);
    }
  }


 /**
   * Decodes a Protobuf encoded value into a Java object.
   *
   * @param encodedValue Encoded value to decode.
   * @return Decoded Java object.
   */
 Object decodeValue(BasicTypes.EncodedValue encodedValue) {
 switch (encodedValue.getValueCase()) {
 case BINARYRESULT:
 return encodedValue.getBinaryResult().toByteArray();
 case BOOLEANRESULT:
 return encodedValue.getBooleanResult();
 case BYTERESULT:
 return (byte) encodedValue.getByteResult();
 case DOUBLERESULT:
 return encodedValue.getDoubleResult();
 case FLOATRESULT:
 return encodedValue.getFloatResult();
 case INTRESULT:
 return encodedValue.getIntResult();
 case LONGRESULT:
 return encodedValue.getLongResult();
 case SHORTRESULT:
 return (short) encodedValue.getShortResult();
 case STRINGRESULT:
 return encodedValue.getStringResult();
 case JSONOBJECTRESULT:
 return JSONWrapper.wrapJSON(encodedValue.getJsonObjectResult());
 case NULLRESULT:
 return null;
 case CUSTOMOBJECTRESULT:
 try {
 return valueSerializer.deserialize(encodedValue.getCustomObjectResult());
        } catch (IOException | ClassNotFoundException e) {
 throw new IllegalStateException(e);
        }
 default:
 throw new IllegalStateException(
 "Can't decode a value of type " + encodedValue.getValueCase() + ": " + encodedValue);
    }
  }


 /**
   * Encodes a Java object key and a Java object value into a Protobuf encoded entry.
   *
   * @param unencodedKey Java object key to encode.
   * @param unencodedValue Java object value to encode.
   * @return Encoded entry of the Java object key and value.
   */
 BasicTypes.Entry encodeEntry(Object unencodedKey, Object unencodedValue) {
 if (unencodedValue == null) {
 return BasicTypes.Entry.newBuilder().setKey(encodeValue(unencodedKey)).build();
    }
 return BasicTypes.Entry.newBuilder().setKey(encodeValue(unencodedKey))
        .setValue(encodeValue(unencodedValue)).build();
  }
}