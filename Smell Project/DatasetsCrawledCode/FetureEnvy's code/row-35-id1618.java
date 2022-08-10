 public static long gzip(final ByteSource in, final ByteSink out, Predicate<Throwable> shouldRetry)
  {
 return StreamUtils.retryCopy(
 in,
 new ByteSink()
        {
 @Override
 public OutputStream openStream() throws IOException
          {
 return new GZIPOutputStream(out.openStream());
          }
        },
 shouldRetry,
 DEFAULT_RETRY_COUNT
    );
  }