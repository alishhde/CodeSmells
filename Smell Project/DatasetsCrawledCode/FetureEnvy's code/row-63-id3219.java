 public void transformKeyReferences(RefTransformer visitor) {
 configs.forEach(c -> c.transformKeyReferences(visitor));
  }