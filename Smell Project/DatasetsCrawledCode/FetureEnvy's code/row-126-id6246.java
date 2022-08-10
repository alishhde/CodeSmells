 public static <T, K, V> Collector<T, ?, ImmutableSetMultimap<K, V>> toImmutableSetMultimap(
 Function<? super T, ? extends K> keyFunction,
 Function<? super T, ? extends V> valueFunction) {
 checkNotNull(keyFunction, "keyFunction");
 checkNotNull(valueFunction, "valueFunction");
 return Collector.of(
 ImmutableSetMultimap::<K, V>builder,
        (builder, t) -> builder.put(keyFunction.apply(t), valueFunction.apply(t)),
 ImmutableSetMultimap.Builder::combine,
 ImmutableSetMultimap.Builder::build);
  }