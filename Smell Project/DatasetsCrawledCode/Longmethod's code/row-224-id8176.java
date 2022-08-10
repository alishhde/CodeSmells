 private static DimFilter negate(final DimFilter filter)
  {
 if (Filtration.matchEverything().equals(filter)) {
 return Filtration.matchNothing();
    } else if (Filtration.matchNothing().equals(filter)) {
 return Filtration.matchEverything();
    } else if (filter instanceof NotDimFilter) {
 return ((NotDimFilter) filter).getField();
    } else if (filter instanceof BoundDimFilter) {
 final BoundDimFilter negated = Bounds.not((BoundDimFilter) filter);
 return negated != null ? negated : new NotDimFilter(filter);
    } else {
 return new NotDimFilter(filter);
    }
  }