public class FlowFileUnpackagerV1 implements FlowFileUnpackager {


 private int flowFilesRead = 0;


 @Override
 public Map<String, String> unpackageFlowFile(final InputStream in, final OutputStream out) throws IOException {
 flowFilesRead++;
 final TarArchiveInputStream tarIn = new TarArchiveInputStream(in);
 final TarArchiveEntry attribEntry = tarIn.getNextTarEntry();
 if (attribEntry == null) {
 return null;
        }


 final Map<String, String> attributes;
 if (attribEntry.getName().equals(FlowFilePackagerV1.FILENAME_ATTRIBUTES)) {
 attributes = getAttributes(tarIn);
        } else {
 throw new IOException("Expected two tar entries: "
                    + FlowFilePackagerV1.FILENAME_CONTENT + " and "
                    + FlowFilePackagerV1.FILENAME_ATTRIBUTES);
        }


 final TarArchiveEntry contentEntry = tarIn.getNextTarEntry();


 if (contentEntry != null && contentEntry.getName().equals(FlowFilePackagerV1.FILENAME_CONTENT)) {
 final byte[] buffer = new byte[512 << 10];//512KB
 int bytesRead = 0;
 while ((bytesRead = tarIn.read(buffer)) != -1) { //still more data to read
 if (bytesRead > 0) {
 out.write(buffer, 0, bytesRead);
                }
            }
 out.flush();
        } else {
 throw new IOException("Expected two tar entries: "
                    + FlowFilePackagerV1.FILENAME_CONTENT + " and "
                    + FlowFilePackagerV1.FILENAME_ATTRIBUTES);
        }


 return attributes;
    }


 protected Map<String, String> getAttributes(final TarArchiveInputStream stream) throws IOException {


 final Properties props = new Properties();
 props.loadFromXML(new NonCloseableInputStream(stream));


 final Map<String, String> result = new HashMap<>();
 for (final Entry<Object, Object> entry : props.entrySet()) {
 final Object keyObject = entry.getKey();
 final Object valueObject = entry.getValue();
 if (!(keyObject instanceof String)) {
 throw new IOException("Flow file attributes object contains key of type "
                        + keyObject.getClass().getCanonicalName()
                        + " but expected java.lang.String");
            } else if (!(keyObject instanceof String)) {
 throw new IOException("Flow file attributes object contains value of type "
                        + keyObject.getClass().getCanonicalName()
                        + " but expected java.lang.String");
            }


 final String key = (String) keyObject;
 final String value = (String) valueObject;
 result.put(key, value);
        }


 return result;
    }


 @Override
 public boolean hasMoreData() throws IOException {
 return flowFilesRead == 0;
    }


 public static final class NonCloseableInputStream extends InputStream {


 final InputStream stream;


 public NonCloseableInputStream(final InputStream stream) {
 this.stream = stream;
        }


 @Override
 public void close() {
        }


 @Override
 public int read() throws IOException {
 return stream.read();
        }


 @Override
 public int available() throws IOException {
 return stream.available();
        }


 @Override
 public synchronized void mark(int readlimit) {
 stream.mark(readlimit);
        }


 @Override
 public synchronized void reset() throws IOException {
 stream.reset();
        }


 @Override
 public boolean markSupported() {
 return stream.markSupported();
        }


 @Override
 public long skip(long n) throws IOException {
 return stream.skip(n);
        }


 @Override
 public int read(byte b[], int off, int len) throws IOException {
 return stream.read(b, off, len);
        }


 @Override
 public int read(byte b[]) throws IOException {
 return stream.read(b);
        }
    }
}