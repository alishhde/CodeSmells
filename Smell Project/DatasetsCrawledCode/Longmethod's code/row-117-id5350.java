 static ResolvableType forType(
 @Nullable Type type, @Nullable TypeProvider typeProvider, @Nullable VariableResolver variableResolver) {


 if (type == null && typeProvider != null) {
 type = SerializableTypeWrapper.forTypeProvider(typeProvider);
		}
 if (type == null) {
 return NONE;
		}


 // For simple Class references, build the wrapper right away -
 // no expensive resolution necessary, so not worth caching...
 if (type instanceof Class) {
 return new ResolvableType(type, typeProvider, variableResolver, (ResolvableType) null);
		}


 // Purge empty entries on access since we don't have a clean-up thread or the like.
 cache.purgeUnreferencedEntries();


 // Check the cache - we may have a ResolvableType which has been resolved before...
 ResolvableType resultType = new ResolvableType(type, typeProvider, variableResolver);
 ResolvableType cachedType = cache.get(resultType);
 if (cachedType == null) {
 cachedType = new ResolvableType(type, typeProvider, variableResolver, resultType.hash);
 cache.put(cachedType, cachedType);
		}
 resultType.resolved = cachedType.resolved;
 return resultType;
	}