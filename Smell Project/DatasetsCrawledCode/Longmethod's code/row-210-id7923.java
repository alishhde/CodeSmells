 @Override
 public TypedValue read(EvaluationContext context, @Nullable Object target, String name) throws AccessException {
 Assert.state(target != null, "Target must not be null");
 Class<?> type = (target instanceof Class ? (Class<?>) target : target.getClass());


 if (type.isArray() && name.equals("length")) {
 if (target instanceof Class) {
 throw new AccessException("Cannot access length on array class itself");
			}
 return new TypedValue(Array.getLength(target));
		}


 PropertyCacheKey cacheKey = new PropertyCacheKey(type, name, target instanceof Class);
 InvokerPair invoker = this.readerCache.get(cacheKey);
 this.lastReadInvokerPair = invoker;


 if (invoker == null || invoker.member instanceof Method) {
 Method method = (Method) (invoker != null ? invoker.member : null);
 if (method == null) {
 method = findGetterForProperty(name, type, target);
 if (method != null) {
 // Treat it like a property...
 // The readerCache will only contain gettable properties (let's not worry about setters for now).
 Property property = new Property(type, method, null);
 TypeDescriptor typeDescriptor = new TypeDescriptor(property);
 invoker = new InvokerPair(method, typeDescriptor);
 this.lastReadInvokerPair = invoker;
 this.readerCache.put(cacheKey, invoker);
				}
			}
 if (method != null) {
 try {
 ReflectionUtils.makeAccessible(method);
 Object value = method.invoke(target);
 return new TypedValue(value, invoker.typeDescriptor.narrow(value));
				}
 catch (Exception ex) {
 throw new AccessException("Unable to access property '" + name + "' through getter method", ex);
				}
			}
		}


 if (invoker == null || invoker.member instanceof Field) {
 Field field = (Field) (invoker == null ? null : invoker.member);
 if (field == null) {
 field = findField(name, type, target);
 if (field != null) {
 invoker = new InvokerPair(field, new TypeDescriptor(field));
 this.lastReadInvokerPair = invoker;
 this.readerCache.put(cacheKey, invoker);
				}
			}
 if (field != null) {
 try {
 ReflectionUtils.makeAccessible(field);
 Object value = field.get(target);
 return new TypedValue(value, invoker.typeDescriptor.narrow(value));
				}
 catch (Exception ex) {
 throw new AccessException("Unable to access field '" + name + "'", ex);
				}
			}
		}


 throw new AccessException("Neither getter method nor field found for property '" + name + "'");
	}