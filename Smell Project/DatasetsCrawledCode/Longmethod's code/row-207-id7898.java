 private static ImmutableDictionaryReader loadDictionary(PinotDataBuffer dictionaryBuffer, ColumnMetadata metadata,
 boolean loadOnHeap) {
 FieldSpec.DataType dataType = metadata.getDataType();
 if (loadOnHeap) {
 String columnName = metadata.getColumnName();
 LOGGER.info("Loading on-heap dictionary for column: {}", columnName);
    }


 int length = metadata.getCardinality();
 switch (dataType) {
 case INT:
 return (loadOnHeap) ? new OnHeapIntDictionary(dictionaryBuffer, length)
            : new IntDictionary(dictionaryBuffer, length);


 case LONG:
 return (loadOnHeap) ? new OnHeapLongDictionary(dictionaryBuffer, length)
            : new LongDictionary(dictionaryBuffer, length);


 case FLOAT:
 return (loadOnHeap) ? new OnHeapFloatDictionary(dictionaryBuffer, length)
            : new FloatDictionary(dictionaryBuffer, length);


 case DOUBLE:
 return (loadOnHeap) ? new OnHeapDoubleDictionary(dictionaryBuffer, length)
            : new DoubleDictionary(dictionaryBuffer, length);


 case STRING:
 int numBytesPerValue = metadata.getColumnMaxLength();
 byte paddingByte = (byte) metadata.getPaddingCharacter();
 return loadOnHeap ? new OnHeapStringDictionary(dictionaryBuffer, length, numBytesPerValue, paddingByte)
            : new StringDictionary(dictionaryBuffer, length, numBytesPerValue, paddingByte);


 case BYTES:
 numBytesPerValue = metadata.getColumnMaxLength();
 return new BytesDictionary(dictionaryBuffer, length, numBytesPerValue);


 default:
 throw new IllegalStateException("Illegal data type for dictionary: " + dataType);
    }
  }