@PublicEvolving
public class ByteValueParser extends FieldParser<ByteValue> {
 
 private ByteValue result;
 
 @Override
 public int parseField(byte[] bytes, int startPos, int limit, byte[] delimiter, ByteValue reusable) {


 if (startPos == limit) {
 setErrorState(ParseErrorState.EMPTY_COLUMN);
 return -1;
		}


 int val = 0;
 boolean neg = false;
 
 this.result = reusable;


 final int delimLimit = limit - delimiter.length + 1;
 
 if (bytes[startPos] == '-') {
 neg = true;
 startPos++;
 
 // check for empty field with only the sign
 if (startPos == limit || (startPos < delimLimit && delimiterNext(bytes, startPos, delimiter))) {
 setErrorState(ParseErrorState.NUMERIC_VALUE_ORPHAN_SIGN);
 return -1;
			}
		}


 for (int i = startPos; i < limit; i++) {


 if (i < delimLimit && delimiterNext(bytes, i, delimiter)) {
 if (i == startPos) {
 setErrorState(ParseErrorState.EMPTY_COLUMN);
 return -1;
				}
 reusable.setValue((byte) (neg ? -val : val));
 return i + delimiter.length;
			}
 if (bytes[i] < 48 || bytes[i] > 57) {
 setErrorState(ParseErrorState.NUMERIC_VALUE_ILLEGAL_CHARACTER);
 return -1;
			}
 val *= 10;
 val += bytes[i] - 48;
 
 if (val > Byte.MAX_VALUE && (!neg || val > -Byte.MIN_VALUE)) {
 setErrorState(ParseErrorState.NUMERIC_VALUE_OVERFLOW_UNDERFLOW);
 return -1;
			}
		}


 reusable.setValue((byte) (neg ? -val : val));
 return limit;
	}
 
 @Override
 public ByteValue createValue() {
 return new ByteValue();
	}


 @Override
 public ByteValue getLastResult() {
 return this.result;
	}
}