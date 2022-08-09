 static class ComparerHolder {
 static final String UNSAFE_COMPARER_NAME = ComparerHolder.class.getName() + "$UnsafeComparer";


 static final Comparer BEST_COMPARER = getBestComparer();


 static Comparer getBestComparer() {
 try {
 Class<?> theClass = Class.forName(UNSAFE_COMPARER_NAME);


 @SuppressWarnings("unchecked")
 Comparer comparer = (Comparer) theClass.getConstructor().newInstance();
 return comparer;
      } catch (Throwable t) { // ensure we really catch *everything*
 return PureJavaComparer.INSTANCE;
      }
    }


 static final class PureJavaComparer extends Comparer {
 static final PureJavaComparer INSTANCE = new PureJavaComparer();


 private PureJavaComparer() {}


 @Override
 public int compareTo(byte [] buf1, int o1, int l1, ByteBuffer buf2, int o2, int l2) {
 int end1 = o1 + l1;
 int end2 = o2 + l2;
 for (int i = o1, j = o2; i < end1 && j < end2; i++, j++) {
 int a = buf1[i] & 0xFF;
 int b = buf2.get(j) & 0xFF;
 if (a != b) {
 return a - b;
          }
        }
 return l1 - l2;
      }


 @Override
 public int compareTo(ByteBuffer buf1, int o1, int l1, ByteBuffer buf2, int o2, int l2) {
 int end1 = o1 + l1;
 int end2 = o2 + l2;
 for (int i = o1, j = o2; i < end1 && j < end2; i++, j++) {
 int a = buf1.get(i) & 0xFF;
 int b = buf2.get(j) & 0xFF;
 if (a != b) {
 return a - b;
          }
        }
 return l1 - l2;
      }
    }


 static final class UnsafeComparer extends Comparer {


 public UnsafeComparer() {}


 static {
 if(!UNSAFE_UNALIGNED) {
 throw new Error();
        }
      }


 @Override
 public int compareTo(byte[] buf1, int o1, int l1, ByteBuffer buf2, int o2, int l2) {
 long offset2Adj;
 Object refObj2 = null;
 if (buf2.isDirect()) {
 offset2Adj = o2 + ((DirectBuffer)buf2).address();
        } else {
 offset2Adj = o2 + buf2.arrayOffset() + UnsafeAccess.BYTE_ARRAY_BASE_OFFSET;
 refObj2 = buf2.array();
        }
 return compareToUnsafe(buf1, o1 + UnsafeAccess.BYTE_ARRAY_BASE_OFFSET, l1,
 refObj2, offset2Adj, l2);
      }


 @Override
 public int compareTo(ByteBuffer buf1, int o1, int l1, ByteBuffer buf2, int o2, int l2) {
 long offset1Adj, offset2Adj;
 Object refObj1 = null, refObj2 = null;
 if (buf1.isDirect()) {
 offset1Adj = o1 + ((DirectBuffer) buf1).address();
        } else {
 offset1Adj = o1 + buf1.arrayOffset() + UnsafeAccess.BYTE_ARRAY_BASE_OFFSET;
 refObj1 = buf1.array();
        }
 if (buf2.isDirect()) {
 offset2Adj = o2 + ((DirectBuffer) buf2).address();
        } else {
 offset2Adj = o2 + buf2.arrayOffset() + UnsafeAccess.BYTE_ARRAY_BASE_OFFSET;
 refObj2 = buf2.array();
        }
 return compareToUnsafe(refObj1, offset1Adj, l1, refObj2, offset2Adj, l2);
      }
    }
  }