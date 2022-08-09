 public static class Builder {


 private File path;


 private String interval;


 private boolean incremental;


 private File out;


 private String filter;


 private boolean ignoreMissingSegments;


 private Builder() {
 // Prevent external instantiation.
        }


 /**
         * The path to an existing segment store. This parameter is required.
         *
         * @param path the path to an existing segment store.
         * @return this builder.
         */
 public Builder withPath(File path) {
 this.path = checkNotNull(path);
 return this;
        }


 /**
         * The two node records to diff specified as a record ID interval. This
         * parameter is required.
         * <p>
         * The interval is specified as two record IDs separated by two full
         * stops ({@code ..}). In example, {@code 333dc24d-438f-4cca-8b21-3ebf67c05856:12345..46116fda-7a72-4dbc-af88-a09322a7753a:67890}.
         * Instead of using a full record ID, it is possible to use the special
         * placeholder {@code head}. This placeholder is translated to the
         * record ID of the most recent head state.
         *
         * @param interval an interval between two node record IDs.
         * @return this builder.
         */
 public Builder withInterval(String interval) {
 this.interval = checkNotNull(interval);
 return this;
        }


 /**
         * Set whether or not to perform an incremental diff of the specified
         * interval. An incremental diff shows every change between the two
         * records at every revision available to the segment store. This
         * parameter is not mandatory and defaults to {@code false}.
         *
         * @param incremental {@code true} to perform an incremental diff,
         *                    {@code false} otherwise.
         * @return this builder.
         */
 public Builder withIncremental(boolean incremental) {
 this.incremental = incremental;
 return this;
        }


 /**
         * The file where the output of this command is stored. this parameter
         * is mandatory.
         *
         * @param file the output file.
         * @return this builder.
         */
 public Builder withOutput(File file) {
 this.out = checkNotNull(file);
 return this;
        }


 /**
         * The path to a subtree. If specified, this parameter allows to
         * restrict the diff to the specified subtree. This parameter is not
         * mandatory and defaults to the entire tree.
         *
         * @param filter a path used as as filter for the resulting diff.
         * @return this builder.
         */
 public Builder withFilter(String filter) {
 this.filter = checkNotNull(filter);
 return this;
        }


 /**
         * Whether to ignore exceptions caused by missing segments in the
         * segment store. This parameter is not mandatory and defaults to {@code
         * false}.
         *
         * @param ignoreMissingSegments {@code true} to ignore exceptions caused
         *                              by missing segments, {@code false}
         *                              otherwise.
         * @return this builder.
         */
 public Builder withIgnoreMissingSegments(boolean ignoreMissingSegments) {
 this.ignoreMissingSegments = ignoreMissingSegments;
 return this;
        }


 /**
         * Create an executable version of the {@link Diff} command.
         *
         * @return an instance of {@link Runnable}.
         */
 public Diff build() {
 checkNotNull(path);
 checkNotNull(interval);
 checkNotNull(out);
 checkNotNull(filter);
 return new Diff(this);
        }


    }