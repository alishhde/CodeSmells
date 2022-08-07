 protected static class OnheapDecodedCell implements ExtendedCell {
 private static final long FIXED_OVERHEAD = ClassSize.align(ClassSize.OBJECT
        + (3 * ClassSize.REFERENCE) + (2 * Bytes.SIZEOF_LONG) + (7 * Bytes.SIZEOF_INT)
        + (Bytes.SIZEOF_SHORT) + (2 * Bytes.SIZEOF_BYTE) + (3 * ClassSize.ARRAY));
 private byte[] keyOnlyBuffer;
 private short rowLength;
 private int familyOffset;
 private byte familyLength;
 private int qualifierOffset;
 private int qualifierLength;
 private long timestamp;
 private byte typeByte;
 private byte[] valueBuffer;
 private int valueOffset;
 private int valueLength;
 private byte[] tagsBuffer;
 private int tagsOffset;
 private int tagsLength;
 private long seqId;


 protected OnheapDecodedCell(byte[] keyBuffer, short rowLength, int familyOffset,
 byte familyLength, int qualOffset, int qualLength, long timeStamp, byte typeByte,
 byte[] valueBuffer, int valueOffset, int valueLen, long seqId, byte[] tagsBuffer,
 int tagsOffset, int tagsLength) {
 this.keyOnlyBuffer = keyBuffer;
 this.rowLength = rowLength;
 this.familyOffset = familyOffset;
 this.familyLength = familyLength;
 this.qualifierOffset = qualOffset;
 this.qualifierLength = qualLength;
 this.timestamp = timeStamp;
 this.typeByte = typeByte;
 this.valueBuffer = valueBuffer;
 this.valueOffset = valueOffset;
 this.valueLength = valueLen;
 this.tagsBuffer = tagsBuffer;
 this.tagsOffset = tagsOffset;
 this.tagsLength = tagsLength;
 setSequenceId(seqId);
    }


 @Override
 public byte[] getRowArray() {
 return keyOnlyBuffer;
    }


 @Override
 public byte[] getFamilyArray() {
 return keyOnlyBuffer;
    }


 @Override
 public byte[] getQualifierArray() {
 return keyOnlyBuffer;
    }


 @Override
 public int getRowOffset() {
 return Bytes.SIZEOF_SHORT;
    }


 @Override
 public short getRowLength() {
 return rowLength;
    }


 @Override
 public int getFamilyOffset() {
 return familyOffset;
    }


 @Override
 public byte getFamilyLength() {
 return familyLength;
    }


 @Override
 public int getQualifierOffset() {
 return qualifierOffset;
    }


 @Override
 public int getQualifierLength() {
 return qualifierLength;
    }


 @Override
 public long getTimestamp() {
 return timestamp;
    }


 @Override
 public byte getTypeByte() {
 return typeByte;
    }


 @Override
 public long getSequenceId() {
 return seqId;
    }


 @Override
 public byte[] getValueArray() {
 return this.valueBuffer;
    }


 @Override
 public int getValueOffset() {
 return valueOffset;
    }


 @Override
 public int getValueLength() {
 return valueLength;
    }


 @Override
 public byte[] getTagsArray() {
 return this.tagsBuffer;
    }


 @Override
 public int getTagsOffset() {
 return this.tagsOffset;
    }


 @Override
 public int getTagsLength() {
 return tagsLength;
    }


 @Override
 public String toString() {
 return KeyValue.keyToString(this.keyOnlyBuffer, 0, KeyValueUtil.keyLength(this)) + "/vlen="
          + getValueLength() + "/seqid=" + seqId;
    }


 @Override
 public void setSequenceId(long seqId) {
 this.seqId = seqId;
    }


 @Override
 public long heapSize() {
 return FIXED_OVERHEAD + rowLength + familyLength + qualifierLength + valueLength + tagsLength;
    }


 @Override
 public int write(OutputStream out, boolean withTags) throws IOException {
 int lenToWrite = getSerializedSize(withTags);
 ByteBufferUtils.putInt(out, keyOnlyBuffer.length);
 ByteBufferUtils.putInt(out, valueLength);
 // Write key
 out.write(keyOnlyBuffer);
 // Write value
 out.write(this.valueBuffer, this.valueOffset, this.valueLength);
 if (withTags && this.tagsLength > 0) {
 // 2 bytes tags length followed by tags bytes
 // tags length is serialized with 2 bytes only(short way) even if the type is int.
 // As this is non -ve numbers, we save the sign bit. See HBASE-11437
 out.write((byte) (0xff & (this.tagsLength >> 8)));
 out.write((byte) (0xff & this.tagsLength));
 out.write(this.tagsBuffer, this.tagsOffset, this.tagsLength);
      }
 return lenToWrite;
    }


 @Override
 public int getSerializedSize(boolean withTags) {
 return KeyValueUtil.length(rowLength, familyLength, qualifierLength, valueLength, tagsLength,
 withTags);
    }


 @Override
 public void write(ByteBuffer buf, int offset) {
 // This is not used in actual flow. Throwing UnsupportedOperationException
 throw new UnsupportedOperationException();
    }


 @Override
 public void setTimestamp(long ts) throws IOException {
 // This is not used in actual flow. Throwing UnsupportedOperationException
 throw new UnsupportedOperationException();
    }


 @Override
 public void setTimestamp(byte[] ts) throws IOException {
 // This is not used in actual flow. Throwing UnsupportedOperationException
 throw new UnsupportedOperationException();
    }


 @Override
 public ExtendedCell deepClone() {
 // This is not used in actual flow. Throwing UnsupportedOperationException
 throw new UnsupportedOperationException();
    }
  }