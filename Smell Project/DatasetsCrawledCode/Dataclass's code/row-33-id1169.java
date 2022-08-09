 private final static class DuplicatableProgressTrackingInputStream
 extends ProgressTrackingInputStream implements DuplicatableInputStream {


 private DuplicatableProgressTrackingInputStream(
 final InputStream source, final ProgressTracker progressTracker) {
 super(source, progressTracker);


 if (!(source instanceof DuplicatableInputStream)) {
 throw new IllegalStateException("Source MUST be a DuplicatableInputStream");
            }
        }


 /**
         * The progress tracking input stream resulting from this call will re-use the progress tracker from the parent
         * progress tracking input stream after resetting it, thus invalidating the progress tracked by the parent
         * stream until now. To ensure correctness of the progress tracking functionality, do NOT read from the parent
         * stream after duplicating from it.
         * @return The duplicated progress tracking input stream.
         */
 @Override
 public InputStream duplicate() {
 return ProgressTrackingInputStreamFactory.create(
                    ((DuplicatableInputStream) getSource()).duplicate(),
 getProgressTracker().reset());
        }
    }