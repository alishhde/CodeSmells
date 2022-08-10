 @Override
 public long exportTo(final ContentClaim claim, final Path destination, final boolean append, final long offset, final long length) throws IOException {
 if (claim == null) {
 if (append) {
 return 0L;
            }
 Files.createFile(destination);
 return 0L;
        }


 final StandardOpenOption openOption = append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE;
 try (final InputStream in = read(claim);
 final OutputStream destinationStream = Files.newOutputStream(destination, openOption)) {


 if (offset > 0) {
 StreamUtils.skip(in, offset);
            }


 StreamUtils.copy(in, destinationStream, length);
 return length;
        }
    }