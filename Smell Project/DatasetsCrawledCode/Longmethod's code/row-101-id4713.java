 @Override
 public void close() throws IOException {
 boolean success = false;
 try {
 if (meta != null) {
 meta.writeInt(-1); // write EOF marker
 CodecUtil.writeFooter(meta); // write checksum
      }
 if (data != null) {
 CodecUtil.writeFooter(data); // write checksum
      }
 success = true;
    } finally {
 if (success) {
 IOUtils.close(data, meta);
      } else {
 IOUtils.closeWhileHandlingException(data, meta);
      }
 meta = data = null;
    }
  }