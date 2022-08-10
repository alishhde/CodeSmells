 private static void doRawReceiveFile(File path, int size, InputStream clientInput)
 throws IOException {
 // Create a temp file to receive the payload, so we don't need to worry about
 // partially-received files.  The host takes care of deleting temp files.
 File tempfile =
 File.createTempFile(
 AgentUtil.TEMP_PREFIX + path.getName() + "-", ".tmp", path.getParentFile());
 FileOutputStream output = new FileOutputStream(tempfile);


 // Keep track of our starting time so we can enforce a timeout on slow but steady uploads.
 long receiveStartMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
 // Keep track of the total received size to verify the payload.
 long totalSize = 0;
 long totalReceiveTimeoutMs =
 RECEIVE_TIMEOUT_MS + TOTAL_RECEIVE_TIMEOUT_MS_PER_MB * (size / 1024 / 1024);
 try {
 int bufferSize = 128 * 1024;
 byte[] buf = new byte[bufferSize];
 while (true) {
 long currentTimeMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
 if (currentTimeMs - receiveStartMs > totalReceiveTimeoutMs) {
 throw new RuntimeException("Receive failed to complete before timeout.");
        }
 int remaining = size - (int) totalSize;
 if (remaining == 0) {
 break;
        }
 int want = bufferSize;
 if (want > remaining) {
 want = remaining;
        }
 int got = clientInput.read(buf, 0, want);
 if (got == -1) {
 break;
        }
 output.write(buf, 0, got);
 totalSize += got;
      }
    } finally {
 output.close();
    }
 if (totalSize != size) {
 throw new RuntimeException("Received only " + totalSize + " of " + size + " bytes.");
    }
 boolean success = tempfile.renameTo(path);
 if (!success) {
 throw new RuntimeException("Failed to rename temp file.");
    }
  }