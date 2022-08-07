public interface JsonObjectMapper<N, P> {


 default String toJson(Object value) throws IOException {
 return null;
	}


 default void toJson(Object value, Writer writer) throws IOException {


	}


 default N toJsonNode(Object value) throws IOException {
 return null;
	}


 default <T> T fromJson(Object json, Class<T> valueType) throws IOException {
 return null;
	}


 /**
	 * Deserialize a JSON to an expected {@link ResolvableType}.
	 * @param json the JSON to deserialize
	 * @param valueType the {@link ResolvableType} for the target object.
	 * @param <T> the expected object type
	 * @return deserialization result object
	 * @throws IOException a JSON parsing exception
	 * @since 5.2
	 */
 default <T> T fromJson(Object json, ResolvableType valueType) throws IOException {
 return null;
	}


 default <T> T fromJson(Object json, Map<String, Object> javaTypes) throws IOException {
 return null;
	}


 default <T> T fromJson(P parser, Type valueType) throws IOException {
 return null;
	}


 default void populateJavaTypes(Map<String, Object> map, Object object) {
 Class<?> targetClass = object.getClass();
 Class<?> contentClass = null;
 Class<?> keyClass = null;
 map.put(JsonHeaders.TYPE_ID, targetClass);
 if (object instanceof Collection && !((Collection<?>) object).isEmpty()) {
 Object firstElement = ((Collection<?>) object).iterator().next();
 if (firstElement != null) {
 contentClass = firstElement.getClass();
 map.put(JsonHeaders.CONTENT_TYPE_ID, contentClass);
			}
		}
 if (object instanceof Map && !((Map<?, ?>) object).isEmpty()) {
 Object firstValue = ((Map<?, ?>) object).values().iterator().next();
 if (firstValue != null) {
 contentClass = firstValue.getClass();
 map.put(JsonHeaders.CONTENT_TYPE_ID, contentClass);
			}
 Object firstKey = ((Map<?, ?>) object).keySet().iterator().next();
 if (firstKey != null) {
 keyClass = firstKey.getClass();
 map.put(JsonHeaders.KEY_TYPE_ID, keyClass);
			}
		}




 map.put(JsonHeaders.RESOLVABLE_TYPE, buildResolvableType(targetClass, contentClass, keyClass));
	}


 static ResolvableType buildResolvableType(Class<?> targetClass, @Nullable Class<?> contentClass,
 @Nullable Class<?> keyClass) {


 if (keyClass != null) {
 return TypeDescriptor
					.map(targetClass,
 TypeDescriptor.valueOf(keyClass),
 TypeDescriptor.valueOf(contentClass))
					.getResolvableType();
		}
 else if (contentClass != null) {
 return TypeDescriptor
					.collection(targetClass,
 TypeDescriptor.valueOf(contentClass))
					.getResolvableType();
		}
 else {
 return ResolvableType.forClass(targetClass);
		}
	}


}