 @Override
 @Nullable
 public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
 // Invocation on EntityManager interface coming in...


 if (method.getName().equals("equals")) {
 // Only consider equal when proxies are identical.
 return (proxy == args[0]);
			}
 else if (method.getName().equals("hashCode")) {
 // Use hashCode of EntityManager proxy.
 return hashCode();
			}
 else if (method.getName().equals("toString")) {
 // Deliver toString without touching a target EntityManager.
 return "Shared EntityManager proxy for target factory [" + this.targetFactory + "]";
			}
 else if (method.getName().equals("getEntityManagerFactory")) {
 // JPA 2.0: return EntityManagerFactory without creating an EntityManager.
 return this.targetFactory;
			}
 else if (method.getName().equals("getCriteriaBuilder") || method.getName().equals("getMetamodel")) {
 // JPA 2.0: return EntityManagerFactory's CriteriaBuilder/Metamodel (avoid creation of EntityManager)
 try {
 return EntityManagerFactory.class.getMethod(method.getName()).invoke(this.targetFactory);
				}
 catch (InvocationTargetException ex) {
 throw ex.getTargetException();
				}
			}
 else if (method.getName().equals("unwrap")) {
 // JPA 2.0: handle unwrap method - could be a proxy match.
 Class<?> targetClass = (Class<?>) args[0];
 if (targetClass != null && targetClass.isInstance(proxy)) {
 return proxy;
				}
			}
 else if (method.getName().equals("isOpen")) {
 // Handle isOpen method: always return true.
 return true;
			}
 else if (method.getName().equals("close")) {
 // Handle close method: suppress, not valid.
 return null;
			}
 else if (method.getName().equals("getTransaction")) {
 throw new IllegalStateException(
 "Not allowed to create transaction on shared EntityManager - " +
 "use Spring transactions or EJB CMT instead");
			}


 // Determine current EntityManager: either the transactional one
 // managed by the factory or a temporary one for the given invocation.
 EntityManager target = EntityManagerFactoryUtils.doGetTransactionalEntityManager(
 this.targetFactory, this.properties, this.synchronizedWithTransaction);


 if (method.getName().equals("getTargetEntityManager")) {
 // Handle EntityManagerProxy interface.
 if (target == null) {
 throw new IllegalStateException("No transactional EntityManager available");
				}
 return target;
			}
 else if (method.getName().equals("unwrap")) {
 Class<?> targetClass = (Class<?>) args[0];
 if (targetClass == null) {
 return (target != null ? target : proxy);
				}
 // We need a transactional target now.
 if (target == null) {
 throw new IllegalStateException("No transactional EntityManager available");
				}
 // Still perform unwrap call on target EntityManager.
			}
 else if (transactionRequiringMethods.contains(method.getName())) {
 // We need a transactional target now, according to the JPA spec.
 // Otherwise, the operation would get accepted but remain unflushed...
 if (target == null || (!TransactionSynchronizationManager.isActualTransactionActive() &&
						!target.getTransaction().isActive())) {
 throw new TransactionRequiredException("No EntityManager with actual transaction available " +
 "for current thread - cannot reliably process '" + method.getName() + "' call");
				}
			}


 // Regular EntityManager operations.
 boolean isNewEm = false;
 if (target == null) {
 logger.debug("Creating new EntityManager for shared EntityManager invocation");
 target = (!CollectionUtils.isEmpty(this.properties) ?
 this.targetFactory.createEntityManager(this.properties) :
 this.targetFactory.createEntityManager());
 isNewEm = true;
			}


 // Invoke method on current EntityManager.
 try {
 Object result = method.invoke(target, args);
 if (result instanceof Query) {
 Query query = (Query) result;
 if (isNewEm) {
 Class<?>[] ifcs = ClassUtils.getAllInterfacesForClass(query.getClass(), this.proxyClassLoader);
 result = Proxy.newProxyInstance(this.proxyClassLoader, ifcs,
 new DeferredQueryInvocationHandler(query, target));
 isNewEm = false;
					}
 else {
 EntityManagerFactoryUtils.applyTransactionTimeout(query, this.targetFactory);
					}
				}
 return result;
			}
 catch (InvocationTargetException ex) {
 throw ex.getTargetException();
			}
 finally {
 if (isNewEm) {
 EntityManagerFactoryUtils.closeEntityManager(target);
				}
			}
		}