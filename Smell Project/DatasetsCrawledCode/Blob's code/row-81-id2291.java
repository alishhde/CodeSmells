 private static final class XdrInputFormat extends PInputStream {


 private static final int READ_BUFFER_SIZE = 32 * 1024;


 private final class Buffer {
 private final byte[] buf;
 private int size;
 private int offset;


 Buffer(byte[] buf) {
 this.buf = buf;
            }


 int readInt() {
 return ((buf[offset++] & 0xff) << 24 | (buf[offset++] & 0xff) << 16 | (buf[offset++] & 0xff) << 8 | (buf[offset++] & 0xff));
            }


 double readDouble() {
 long val = ((long) (buf[offset++] & 0xff) << 56 | (long) (buf[offset++] & 0xff) << 48 | (long) (buf[offset++] & 0xff) << 40 | (long) (buf[offset++] & 0xff) << 32 |
                                (long) (buf[offset++] & 0xff) << 24 | (long) (buf[offset++] & 0xff) << 16 | (long) (buf[offset++] & 0xff) << 8 | buf[offset++] & 0xff);
 return Double.longBitsToDouble(val);
            }


 @SuppressWarnings("deprecation")
 String readString(int len) {
 /*
                 * This fast path uses a cheaper String constructor if all incoming bytes are in the
                 * 0-127 range.
                 */
 boolean fastEncode = true;
 for (int i = 0; i < len; i++) {
 byte b = buf[offset + i];
 if (b < 0) {
 fastEncode = false;
 break;
                    }
                }
 String result;
 if (fastEncode) {
 result = new String(buf, 0, offset, len);
                } else {
 result = new String(buf, offset, len, StandardCharsets.UTF_8);
                }
 offset += len;
 WeakReference<String> entry;
 if ((entry = strings.get(result)) != null) {
 String string = entry.get();
 if (string != null) {
 return string;
                    }
                }
 strings.put(result, new WeakReference<>(result));
 return result;
            }


 void readRaw(byte[] data) {
 System.arraycopy(buf, offset, data, 0, data.length);
 offset += data.length;
            }


 void readData(int n) throws IOException {
 if (offset + n > size) {
 if (offset != size) {
 // copy end piece to beginning
 System.arraycopy(buf, offset, buf, 0, size - offset);
                    }
 size -= offset;
 offset = 0;
 while (size < n) {
 // read some more data
 int nread = is.read(buf, size, buf.length - size);
 if (nread <= 0) {
 throw RInternalError.unimplemented("handle unexpected eof");
                        }
 size += nread;
                    }
                }
            }
        }


 /**
         * This buffer is used under normal circumstances, i.e. when the read data blocks are
         * smaller than the initial buffer. The ensureData method creates a special buffer for
         * reading big chunks of data exceeding the default buffer.
         */
 private final Buffer defaultBuffer;


 private final WeakHashMap<String, WeakReference<String>> strings = RContext.getInstance().stringMap;


 XdrInputFormat(InputStream is) {
 super(is);
 if (is instanceof PByteArrayInputStream) {
 // we already have the data and we have read the beginning
 PByteArrayInputStream pbis = (PByteArrayInputStream) is;
 defaultBuffer = new Buffer(pbis.getData());
 defaultBuffer.size = pbis.getData().length;
 defaultBuffer.offset = pbis.pos();
            } else {
 defaultBuffer = new Buffer(new byte[READ_BUFFER_SIZE]);
 defaultBuffer.size = 0;
 defaultBuffer.offset = 0;
            }
        }


 @Override
 int readInt() throws IOException {
 return ensureData(4).readInt();
        }


 @Override
 double readDouble() throws IOException {
 return ensureData(8).readDouble();
        }


 @Override
 String readString(int len) throws IOException {
 return ensureData(len).readString(len);
        }


 @Override
 void readRaw(byte[] data) throws IOException {
 ensureData(data.length).readRaw(data);
        }


 private Buffer ensureData(int n) throws IOException {
 Buffer usedBuffer;
 if (n > defaultBuffer.buf.length) {
 if (is instanceof PByteArrayInputStream) {
 // If the input stream is instance of PByteArrayInputStream, the buffer is
 // preloaded and thus no more data can be read beyond the current buffer.
 throw new IOException("Premature EOF");
                }


 // create an enlarged copy of the default buffer
 byte[] enlargedBuf = new byte[n];
 System.arraycopy(defaultBuffer.buf, defaultBuffer.offset, enlargedBuf, defaultBuffer.offset, defaultBuffer.size - defaultBuffer.offset);
 usedBuffer = new Buffer(enlargedBuf);
 usedBuffer.offset = defaultBuffer.offset;
 usedBuffer.size = defaultBuffer.size;


 // reset the default buffer
 defaultBuffer.offset = defaultBuffer.size = 0;


 usedBuffer.readData(n);
 // The previous statement should entirely fill the temporary buffer.
 // It is assumed that the caller will read n bytes, making the temporary buffer
 // disposable. Next time, the default buffer will be used again, unless
 // n > defaultBuffer.buf.length.
 assert usedBuffer.size == n;
            } else {
 usedBuffer = defaultBuffer;
 usedBuffer.readData(n);
            }
 return usedBuffer;
        }
    }