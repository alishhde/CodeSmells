 @VisibleForTesting
 @Nonnull
 static Supplier<Object> supplierFromDimensionSelector(final DimensionSelector selector)
  {
 Preconditions.checkNotNull(selector, "selector");
 return () -> {
 final IndexedInts row = selector.getRow();


 if (row.size() == 1) {
 return selector.lookupName(row.get(0));
      } else {
 // Can't handle non-singly-valued rows in expressions.
 // Treat them as nulls until we think of something better to do.
 return null;
      }
    };
  }