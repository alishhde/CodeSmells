 public boolean hasMatchingKey(Node model1, Node model2) {
 return keyProvider.getKey(model1).equals(keyProvider.getKey(model2));
  }