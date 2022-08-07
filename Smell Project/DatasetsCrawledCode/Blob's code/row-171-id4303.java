public class FileIODecorator extends AbstractFileIO {
 /** File I/O delegate */
 protected final FileIO delegate;


 /**
     *
     * @param delegate File I/O delegate
     */
 public FileIODecorator(FileIO delegate) {
 this.delegate = delegate;
    }


 /** {@inheritDoc} */
 @Override public int getFileSystemBlockSize() {
 return delegate.getFileSystemBlockSize();
    }


 /** {@inheritDoc} */
 @Override public long getSparseSize() {
 return delegate.getSparseSize();
    }


 /** {@inheritDoc} */
 @Override public int punchHole(long pos, int len) {
 return delegate.punchHole(pos, len);
    }


 /** {@inheritDoc} */
 @Override public long position() throws IOException {
 return delegate.position();
    }


 /** {@inheritDoc} */
 @Override public void position(long newPosition) throws IOException {
 delegate.position(newPosition);
    }


 /** {@inheritDoc} */
 @Override public int read(ByteBuffer destBuf) throws IOException {
 return delegate.read(destBuf);
    }


 /** {@inheritDoc} */
 @Override public int read(ByteBuffer destBuf, long position) throws IOException {
 return delegate.read(destBuf, position);
    }


 /** {@inheritDoc} */
 @Override public int read(byte[] buf, int off, int len) throws IOException {
 return delegate.read(buf, off, len);
    }


 /** {@inheritDoc} */
 @Override public int write(ByteBuffer srcBuf) throws IOException {
 return delegate.write(srcBuf);
    }


 /** {@inheritDoc} */
 @Override public int write(ByteBuffer srcBuf, long position) throws IOException {
 return delegate.write(srcBuf, position);
    }


 /** {@inheritDoc} */
 @Override public int write(byte[] buf, int off, int len) throws IOException {
 return delegate.write(buf, off, len);
    }


 /** {@inheritDoc} */
 @Override public MappedByteBuffer map(int sizeBytes) throws IOException {
 return delegate.map(sizeBytes);
    }


 /** {@inheritDoc} */
 @Override public void force() throws IOException {
 delegate.force();
    }


 /** {@inheritDoc} */
 @Override public void force(boolean withMetadata) throws IOException {
 delegate.force(withMetadata);
    }


 /** {@inheritDoc} */
 @Override public long size() throws IOException {
 return delegate.size();
    }


 /** {@inheritDoc} */
 @Override public void clear() throws IOException {
 delegate.clear();
    }


 /** {@inheritDoc} */
 @Override public void close() throws IOException {
 delegate.close();
    }
}