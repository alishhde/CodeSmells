 public String readNullTerminatedString(int length) {
 if (length == 0) {
 return "";
    }
 int stringLength = length;
 int lastIndex = position + length - 1;
 if (lastIndex < limit && data[lastIndex] == 0) {
 stringLength--;
    }
 String result = Util.fromUtf8Bytes(data, position, stringLength);
 position += length;
 return result;
  }