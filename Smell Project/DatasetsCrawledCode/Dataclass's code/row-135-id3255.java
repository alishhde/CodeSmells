 @AutoValue.Builder
 abstract static class Builder<T> {
 abstract Builder<T> setHosts(List<String> hosts);


 abstract Builder<T> setPort(Integer port);


 abstract Builder<T> setKeyspace(String keyspace);


 abstract Builder<T> setEntity(Class<T> entity);


 abstract Builder<T> setUsername(String username);


 abstract Builder<T> setPassword(String password);


 abstract Builder<T> setLocalDc(String localDc);


 abstract Builder<T> setConsistencyLevel(String consistencyLevel);


 abstract Builder<T> setMutationType(MutationType mutationType);


 abstract Write<T> build();
    }