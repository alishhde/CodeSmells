 private static Collection<Path> collectBasePaths(Iterable<? extends BuildTarget> targets) {
 return StreamSupport.stream(targets.spliterator(), false)
        .map(BuildTarget::getBasePath)
        .collect(ImmutableSet.toImmutableSet());
  }