public class InternalCacheBuilder {
 private static final Logger logger = LogService.getLogger();


 private static final String USE_ASYNC_EVENT_LISTENERS_PROPERTY =
 GEMFIRE_PREFIX + "Cache.ASYNC_EVENT_LISTENERS";


 private static final boolean IS_EXISTING_OK_DEFAULT = true;
 private static final boolean IS_CLIENT_DEFAULT = false;


 private final Properties configProperties;
 private final CacheConfig cacheConfig;
 private final CompositeMeterRegistryFactory compositeMeterRegistryFactory;
 private final Consumer<CompositeMeterRegistry> metricsSessionInitializer;
 private final Supplier<InternalDistributedSystem> singletonSystemSupplier;
 private final Supplier<InternalCache> singletonCacheSupplier;
 private final InternalDistributedSystemConstructor internalDistributedSystemConstructor;
 private final InternalCacheConstructor internalCacheConstructor;


 private boolean isExistingOk = IS_EXISTING_OK_DEFAULT;
 private boolean isClient = IS_CLIENT_DEFAULT;


 /**
   * Setting useAsyncEventListeners to true will invoke event listeners in asynchronously.
   *
   * <p>
   * Default is specified by system property {@code gemfire.Cache.ASYNC_EVENT_LISTENERS}.
   */
 private boolean useAsyncEventListeners = Boolean.getBoolean(USE_ASYNC_EVENT_LISTENERS_PROPERTY);


 private PoolFactory poolFactory;
 private TypeRegistry typeRegistry;


 /**
   * Creates a cache factory with default configuration properties.
   */
 public InternalCacheBuilder() {
 this(new Properties(), new CacheConfig());
  }


 /**
   * Create a cache factory initialized with the given configuration properties. For a list of valid
   * configuration properties and their meanings see {@link ConfigurationProperties}.
   *
   * @param configProperties the configuration properties to initialize the factory with.
   */
 public InternalCacheBuilder(Properties configProperties) {
 this(configProperties == null ? new Properties() : configProperties, new CacheConfig());
  }


 /**
   * Creates a cache factory with default configuration properties.
   */
 public InternalCacheBuilder(CacheConfig cacheConfig) {
 this(new Properties(), cacheConfig);
  }


 private InternalCacheBuilder(Properties configProperties, CacheConfig cacheConfig) {
 this(configProperties,
 cacheConfig,
 new CacheMeterRegistryFactory(),
 CacheLifecycleMetricsSession.builder()::build,
 InternalDistributedSystem::getConnectedInstance,
 InternalDistributedSystem::connectInternal,
 GemFireCacheImpl::getInstance,
 GemFireCacheImpl::new);
  }


 @VisibleForTesting
 InternalCacheBuilder(Properties configProperties,
 CacheConfig cacheConfig,
 CompositeMeterRegistryFactory compositeMeterRegistryFactory,
 Consumer<CompositeMeterRegistry> metricsSessionInitializer,
 Supplier<InternalDistributedSystem> singletonSystemSupplier,
 InternalDistributedSystemConstructor internalDistributedSystemConstructor,
 Supplier<InternalCache> singletonCacheSupplier,
 InternalCacheConstructor internalCacheConstructor) {
 this.configProperties = configProperties;
 this.cacheConfig = cacheConfig;
 this.compositeMeterRegistryFactory = compositeMeterRegistryFactory;
 this.metricsSessionInitializer = metricsSessionInitializer;
 this.singletonSystemSupplier = singletonSystemSupplier;
 this.internalDistributedSystemConstructor = internalDistributedSystemConstructor;
 this.internalCacheConstructor = internalCacheConstructor;
 this.singletonCacheSupplier = singletonCacheSupplier;
  }


 /**
   * @see CacheFactory#create()
   *
   * @throws CacheXmlException If a problem occurs while parsing the declarative caching XML file.
   * @throws TimeoutException If a {@link Region#put(Object, Object)} times out while initializing
   *         the cache.
   * @throws CacheWriterException If a {@code CacheWriterException} is thrown while initializing the
   *         cache.
   * @throws GatewayException If a {@code GatewayException} is thrown while initializing the cache.
   * @throws RegionExistsException If the declarative caching XML file describes a region that
   *         already exists (including the root region).
   * @throws IllegalStateException if cache already exists and is not compatible with the new
   *         configuration.
   * @throws AuthenticationFailedException if authentication fails.
   * @throws AuthenticationRequiredException if the distributed system is in secure mode and this
   *         new member is not configured with security credentials.
   */
 public InternalCache create()
 throws TimeoutException, CacheWriterException, GatewayException, RegionExistsException {
 synchronized (InternalCacheBuilder.class) {
 InternalDistributedSystem internalDistributedSystem = findInternalDistributedSystem()
          .orElseGet(() -> createInternalDistributedSystem());
 return create(internalDistributedSystem);
    }
  }


 /**
   * @see CacheFactory#create(DistributedSystem)
   *
   * @throws IllegalArgumentException If {@code system} is not {@link DistributedSystem#isConnected
   *         connected}.
   * @throws CacheExistsException If an open cache already exists.
   * @throws CacheXmlException If a problem occurs while parsing the declarative caching XML file.
   * @throws TimeoutException If a {@link Region#put(Object, Object)} times out while initializing
   *         the cache.
   * @throws CacheWriterException If a {@code CacheWriterException} is thrown while initializing the
   *         cache.
   * @throws GatewayException If a {@code GatewayException} is thrown while initializing the cache.
   * @throws RegionExistsException If the declarative caching XML file describes a region that
   *         already exists (including the root region).
   */
 public InternalCache create(InternalDistributedSystem internalDistributedSystem)
 throws TimeoutException, CacheWriterException, GatewayException, RegionExistsException {
 requireNonNull(internalDistributedSystem, "internalDistributedSystem");
 try {
 synchronized (InternalCacheBuilder.class) {
 synchronized (GemFireCacheImpl.class) {
 InternalCache cache =
 existingCache(internalDistributedSystem::getCache, singletonCacheSupplier);
 if (cache == null) {


 int systemId = internalDistributedSystem.getConfig().getDistributedSystemId();
 String memberName = internalDistributedSystem.getName();
 String hostName = internalDistributedSystem.getDistributedMember().getHost();


 CompositeMeterRegistry compositeMeterRegistry = compositeMeterRegistryFactory
                .create(systemId, memberName, hostName);


 metricsSessionInitializer.accept(compositeMeterRegistry);


 cache =
 internalCacheConstructor.construct(isClient, poolFactory, internalDistributedSystem,
 cacheConfig, useAsyncEventListeners, typeRegistry, compositeMeterRegistry);


 internalDistributedSystem.setCache(cache);
 cache.initialize();


          } else {
 internalDistributedSystem.setCache(cache);
          }


 return cache;
        }
      }
    } catch (CacheXmlException | IllegalArgumentException e) {
 logger.error(e.getLocalizedMessage());
 throw e;
    } catch (Error | RuntimeException e) {
 logger.error(e);
 throw e;
    }
  }


 /**
   * @see CacheFactory#set(String, String)
   */
 public InternalCacheBuilder set(String name, String value) {
 configProperties.setProperty(name, value);
 return this;
  }


 /**
   * @see CacheFactory#setPdxReadSerialized(boolean)
   */
 public InternalCacheBuilder setPdxReadSerialized(boolean readSerialized) {
 cacheConfig.setPdxReadSerialized(readSerialized);
 return this;
  }


 /**
   * @see CacheFactory#setSecurityManager(SecurityManager)
   */
 public InternalCacheBuilder setSecurityManager(SecurityManager securityManager) {
 cacheConfig.setSecurityManager(securityManager);
 return this;
  }


 /**
   * @see CacheFactory#setPostProcessor(PostProcessor)
   */
 public InternalCacheBuilder setPostProcessor(PostProcessor postProcessor) {
 cacheConfig.setPostProcessor(postProcessor);
 return this;
  }


 /**
   * @see CacheFactory#setPdxSerializer(PdxSerializer)
   */
 public InternalCacheBuilder setPdxSerializer(PdxSerializer serializer) {
 cacheConfig.setPdxSerializer(serializer);
 return this;
  }


 /**
   * @see CacheFactory#setPdxDiskStore(String)
   */
 public InternalCacheBuilder setPdxDiskStore(String diskStoreName) {
 cacheConfig.setPdxDiskStore(diskStoreName);
 return this;
  }


 /**
   * @see CacheFactory#setPdxPersistent(boolean)
   */
 public InternalCacheBuilder setPdxPersistent(boolean isPersistent) {
 cacheConfig.setPdxPersistent(isPersistent);
 return this;
  }


 /**
   * @see CacheFactory#setPdxIgnoreUnreadFields(boolean)
   */
 public InternalCacheBuilder setPdxIgnoreUnreadFields(boolean ignore) {
 cacheConfig.setPdxIgnoreUnreadFields(ignore);
 return this;
  }


 public InternalCacheBuilder setCacheXMLDescription(String cacheXML) {
 if (cacheXML != null) {
 cacheConfig.setCacheXMLDescription(cacheXML);
    }
 return this;
  }


 /**
   * @param isExistingOk default is true.
   */
 public InternalCacheBuilder setIsExistingOk(boolean isExistingOk) {
 this.isExistingOk = isExistingOk;
 return this;
  }


 /**
   * @param isClient default is false.
   */
 public InternalCacheBuilder setIsClient(boolean isClient) {
 this.isClient = isClient;
 return this;
  }


 /**
   * @param useAsyncEventListeners default is specified by the system property
   *        {@code gemfire.Cache.ASYNC_EVENT_LISTENERS}.
   */
 public InternalCacheBuilder setUseAsyncEventListeners(boolean useAsyncEventListeners) {
 this.useAsyncEventListeners = useAsyncEventListeners;
 return this;
  }


 /**
   * @param poolFactory default is null.
   */
 public InternalCacheBuilder setPoolFactory(PoolFactory poolFactory) {
 this.poolFactory = poolFactory;
 return this;
  }


 /**
   * @param typeRegistry default is null.
   */
 public InternalCacheBuilder setTypeRegistry(TypeRegistry typeRegistry) {
 this.typeRegistry = typeRegistry;
 return this;
  }


 private Optional<InternalDistributedSystem> findInternalDistributedSystem() {
 InternalDistributedSystem internalDistributedSystem = null;
 if (configProperties.isEmpty() && !ALLOW_MULTIPLE_SYSTEMS) {
 // any ds will do
 internalDistributedSystem = singletonSystemSupplier.get();
 validateUsabilityOfSecurityCallbacks(internalDistributedSystem, cacheConfig);
    }
 return Optional.ofNullable(internalDistributedSystem);
  }


 private InternalDistributedSystem createInternalDistributedSystem() {
 SecurityConfig securityConfig = new SecurityConfig(
 cacheConfig.getSecurityManager(),
 cacheConfig.getPostProcessor());


 return internalDistributedSystemConstructor.construct(configProperties, securityConfig);
  }


 private InternalCache existingCache(Supplier<? extends InternalCache> systemCacheSupplier,
 Supplier<? extends InternalCache> singletonCacheSupplier) {
 InternalCache cache = ALLOW_MULTIPLE_SYSTEMS
        ? systemCacheSupplier.get()
        : singletonCacheSupplier.get();


 if (validateExistingCache(cache)) {
 return cache;
    }


 return null;
  }


 /**
   * Validates that isExistingOk is true and existing cache is compatible with cacheConfig.
   *
   * if instance exists and cacheConfig is incompatible
   * if instance exists and isExistingOk is false
   */
 private boolean validateExistingCache(InternalCache existingCache) {
 if (existingCache == null || existingCache.isClosed()) {
 return false;
    }


 if (isExistingOk) {
 cacheConfig.validateCacheConfig(existingCache);
    } else {
 existingCache.throwCacheExistsException();
    }


 return true;
  }


 /**
   * if existing DistributedSystem connection cannot use specified SecurityManager or
   * PostProcessor.
   */
 private static void validateUsabilityOfSecurityCallbacks(
 InternalDistributedSystem internalDistributedSystem, CacheConfig cacheConfig)
 throws GemFireSecurityException {
 if (internalDistributedSystem == null) {
 return;
    }
 // pre-existing DistributedSystem already has an incompatible SecurityService in use
 if (cacheConfig.getSecurityManager() != null) {
 throw new GemFireSecurityException(
 "Existing DistributedSystem connection cannot use specified SecurityManager");
    }
 if (cacheConfig.getPostProcessor() != null) {
 throw new GemFireSecurityException(
 "Existing DistributedSystem connection cannot use specified PostProcessor");
    }
  }


 @VisibleForTesting
 interface InternalCacheConstructor {
 InternalCache construct(boolean isClient, PoolFactory poolFactory,
 InternalDistributedSystem internalDistributedSystem, CacheConfig cacheConfig,
 boolean useAsyncEventListeners, TypeRegistry typeRegistry, MeterRegistry meterRegistry);
  }


 @VisibleForTesting
 interface InternalDistributedSystemConstructor {
 InternalDistributedSystem construct(Properties configProperties, SecurityConfig securityConfig);
  }
}