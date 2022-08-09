 @JsonDeserialize(using = AggregationsDeserializer.class)
 static class Aggregations implements Iterable<Aggregation> {


 private final List<? extends Aggregation> aggregations;
 private Map<String, Aggregation> aggregationsAsMap;


 Aggregations(List<? extends Aggregation> aggregations) {
 this.aggregations = Objects.requireNonNull(aggregations, "aggregations");
    }


 /**
     * Iterates over the {@link Aggregation}s.
     */
 @Override public final Iterator<Aggregation> iterator() {
 return asList().iterator();
    }


 /**
     * The list of {@link Aggregation}s.
     */
 final List<Aggregation> asList() {
 return Collections.unmodifiableList(aggregations);
    }


 /**
     * Returns the {@link Aggregation}s keyed by aggregation name. Lazy init.
     */
 final Map<String, Aggregation> asMap() {
 if (aggregationsAsMap == null) {
 Map<String, Aggregation> map = new LinkedHashMap<>(aggregations.size());
 for (Aggregation aggregation : aggregations) {
 map.put(aggregation.getName(), aggregation);
        }
 this.aggregationsAsMap = unmodifiableMap(map);
      }
 return aggregationsAsMap;
    }


 /**
     * Returns the aggregation that is associated with the specified name.
     */
 @SuppressWarnings("unchecked")
 public final <A extends Aggregation> A get(String name) {
 return (A) asMap().get(name);
    }


 @Override public final boolean equals(Object obj) {
 if (obj == null || getClass() != obj.getClass()) {
 return false;
      }
 return aggregations.equals(((Aggregations) obj).aggregations);
    }


 @Override public final int hashCode() {
 return Objects.hash(getClass(), aggregations);
    }


  }