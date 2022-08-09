public class DruidDataSource extends DruidAbstractDataSource implements DruidDataSourceMBean, ManagedDataSource, Referenceable, Closeable, Cloneable, ConnectionPoolDataSource, MBeanRegistration {


 private final static Log LOG                       = LogFactory.getLog(DruidDataSource.class);
 private static final long serialVersionUID          = 1L;
 // stats
 private volatile long recycleErrorCount         = 0L;
 private long connectCount              = 0L;
 private long closeCount                = 0L;
 private volatile long connectErrorCount         = 0L;
 private long recycleCount              = 0L;
 private long removeAbandonedCount      = 0L;
 private long notEmptyWaitCount         = 0L;
 private long notEmptySignalCount       = 0L;
 private long notEmptyWaitNanos         = 0L;
 private int keepAliveCheckCount       = 0;
 private int activePeak                = 0;
 private long activePeakTime            = 0;
 private int poolingPeak               = 0;
 private long poolingPeakTime           = 0;
 // store
 private volatile DruidConnectionHolder[] connections;
 private int poolingCount              = 0;
 private int activeCount               = 0;
 private volatile long discardCount              = 0;
 private int notEmptyWaitThreadCount   = 0;
 private int notEmptyWaitThreadPeak    = 0;
 //
 private DruidConnectionHolder[]          evictConnections;
 private DruidConnectionHolder[]          keepAliveConnections;


 // threads
 private volatile ScheduledFuture<?>      destroySchedulerFuture;
 private DestroyTask destroyTask;


 private volatile Future<?>               createSchedulerFuture;


 private CreateConnectionThread createConnectionThread;
 private DestroyConnectionThread destroyConnectionThread;
 private LogStatsThread logStatsThread;
 private int createTaskCount;


 private final CountDownLatch initedLatch               = new CountDownLatch(2);


 private volatile boolean enable                    = true;


 private boolean resetStatEnable           = true;
 private volatile long resetCount                = 0L;


 private String initStackTrace;


 private volatile boolean closing                   = false;
 private volatile boolean closed                    = false;
 private long closeTimeMillis           = -1L;


 protected JdbcDataSourceStat dataSourceStat;


 private boolean useGlobalDataSourceStat   = false;
 private boolean mbeanRegistered           = false;
 public static ThreadLocal<Long>          waitNanosLocal            = new ThreadLocal<Long>();
 private boolean logDifferentThread        = true;
 private volatile boolean keepAlive                 = false;
 private boolean asyncInit                 = false;
 protected boolean killWhenSocketReadTimeout = false;


 private static List<Filter>              autoFilters               = null;
 private boolean loadSpifilterSkip         = false;


 protected static final AtomicLongFieldUpdater<DruidDataSource> recycleErrorCountUpdater
            = AtomicLongFieldUpdater.newUpdater(DruidDataSource.class, "recycleErrorCount");
 protected static final AtomicLongFieldUpdater<DruidDataSource> connectErrorCountUpdater
            = AtomicLongFieldUpdater.newUpdater(DruidDataSource.class, "connectErrorCount");
 protected static final AtomicLongFieldUpdater<DruidDataSource> resetCountUpdater
            = AtomicLongFieldUpdater.newUpdater(DruidDataSource.class, "resetCount");




 public DruidDataSource(){
 this(false);
    }


 public DruidDataSource(boolean fairLock){
 super(fairLock);


 configFromPropety(System.getProperties());
    }


 public boolean isAsyncInit() {
 return asyncInit;
    }


 public void setAsyncInit(boolean asyncInit) {
 this.asyncInit = asyncInit;
    }


 public void configFromPropety(Properties properties) {
        {
 String property = properties.getProperty("druid.name");
 if (property != null) {
 this.setName(property);
            }
        }
        {
 String property = properties.getProperty("druid.url");
 if (property != null) {
 this.setUrl(property);
            }
        }
        {
 String property = properties.getProperty("druid.username");
 if (property != null) {
 this.setUsername(property);
            }
        }
        {
 String property = properties.getProperty("druid.password");
 if (property != null) {
 this.setPassword(property);
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.testWhileIdle");
 if (value != null) {
 this.testWhileIdle = value;
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.testOnBorrow");
 if (value != null) {
 this.testOnBorrow = value;
            }
        }
        {
 String property = properties.getProperty("druid.validationQuery");
 if (property != null && property.length() > 0) {
 this.setValidationQuery(property);
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.useGlobalDataSourceStat");
 if (value != null) {
 this.setUseGlobalDataSourceStat(value);
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.useGloalDataSourceStat"); // compatible for early versions
 if (value != null) {
 this.setUseGlobalDataSourceStat(value);
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.asyncInit"); // compatible for early versions
 if (value != null) {
 this.setAsyncInit(value);
            }
        }
        {
 String property = properties.getProperty("druid.filters");


 if (property != null && property.length() > 0) {
 try {
 this.setFilters(property);
                } catch (SQLException e) {
 LOG.error("setFilters error", e);
                }
            }
        }
        {
 String property = properties.getProperty(Constants.DRUID_TIME_BETWEEN_LOG_STATS_MILLIS);
 if (property != null && property.length() > 0) {
 try {
 long value = Long.parseLong(property);
 this.setTimeBetweenLogStatsMillis(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property '" + Constants.DRUID_TIME_BETWEEN_LOG_STATS_MILLIS + "'", e);
                }
            }
        }
        {
 String property = properties.getProperty(Constants.DRUID_STAT_SQL_MAX_SIZE);
 if (property != null && property.length() > 0) {
 try {
 int value = Integer.parseInt(property);
 if (dataSourceStat != null) {
 dataSourceStat.setMaxSqlSize(value);
                    }
                } catch (NumberFormatException e) {
 LOG.error("illegal property '" + Constants.DRUID_STAT_SQL_MAX_SIZE + "'", e);
                }
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.clearFiltersEnable");
 if (value != null) {
 this.setClearFiltersEnable(value);
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.resetStatEnable");
 if (value != null) {
 this.setResetStatEnable(value);
            }
        }
        {
 String property = properties.getProperty("druid.notFullTimeoutRetryCount");
 if (property != null && property.length() > 0) {
 try {
 int value = Integer.parseInt(property);
 this.setNotFullTimeoutRetryCount(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.notFullTimeoutRetryCount'", e);
                }
            }
        }
        {
 String property = properties.getProperty("druid.timeBetweenEvictionRunsMillis");
 if (property != null && property.length() > 0) {
 try {
 long value = Long.parseLong(property);
 this.setTimeBetweenEvictionRunsMillis(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.timeBetweenEvictionRunsMillis'", e);
                }
            }
        }
        {
 String property = properties.getProperty("druid.maxWaitThreadCount");
 if (property != null && property.length() > 0) {
 try {
 int value = Integer.parseInt(property);
 this.setMaxWaitThreadCount(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.maxWaitThreadCount'", e);
                }
            }
        }
        {
 String property = properties.getProperty("druid.maxWait");
 if (property != null && property.length() > 0) {
 try {
 int value = Integer.parseInt(property);
 this.setMaxWait(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.maxWait'", e);
                }
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.failFast");
 if (value != null) {
 this.setFailFast(value);
            }
        }
        {
 String property = properties.getProperty("druid.phyTimeoutMillis");
 if (property != null && property.length() > 0) {
 try {
 long value = Long.parseLong(property);
 this.setPhyTimeoutMillis(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.phyTimeoutMillis'", e);
                }
            }
        }
        {
 String property = properties.getProperty("druid.phyMaxUseCount");
 if (property != null && property.length() > 0) {
 try {
 long value = Long.parseLong(property);
 this.setPhyMaxUseCount(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.phyMaxUseCount'", e);
                }
            }
        }
        {
 String property = properties.getProperty("druid.minEvictableIdleTimeMillis");
 if (property != null && property.length() > 0) {
 try {
 long value = Long.parseLong(property);
 this.setMinEvictableIdleTimeMillis(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.minEvictableIdleTimeMillis'", e);
                }
            }
        }
        {
 String property = properties.getProperty("druid.maxEvictableIdleTimeMillis");
 if (property != null && property.length() > 0) {
 try {
 long value = Long.parseLong(property);
 this.setMaxEvictableIdleTimeMillis(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.maxEvictableIdleTimeMillis'", e);
                }
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.keepAlive");
 if (value != null) {
 this.setKeepAlive(value);
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.poolPreparedStatements");
 if (value != null) {
 this.setPoolPreparedStatements0(value);
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.initVariants");
 if (value != null) {
 this.setInitVariants(value);
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.initGlobalVariants");
 if (value != null) {
 this.setInitGlobalVariants(value);
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.useUnfairLock");
 if (value != null) {
 this.setUseUnfairLock(value);
            }
        }
        {
 String property = properties.getProperty("druid.driverClassName");
 if (property != null) {
 this.setDriverClassName(property);
            }
        }
        {
 String property = properties.getProperty("druid.initialSize");
 if (property != null && property.length() > 0) {
 try {
 int value = Integer.parseInt(property);
 this.setInitialSize(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.initialSize'", e);
                }
            }
        }
        {
 String property = properties.getProperty("druid.minIdle");
 if (property != null && property.length() > 0) {
 try {
 int value = Integer.parseInt(property);
 this.setMinIdle(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.minIdle'", e);
                }
            }
        }
        {
 String property = properties.getProperty("druid.maxActive");
 if (property != null && property.length() > 0) {
 try {
 int value = Integer.parseInt(property);
 this.setMaxActive(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.maxActive'", e);
                }
            }
        }
        {
 Boolean value = getBoolean(properties, "druid.killWhenSocketReadTimeout");
 if (value != null) {
 setKillWhenSocketReadTimeout(value);
            }
        }
        {
 String property = properties.getProperty("druid.connectProperties");
 if (property != null) {
 this.setConnectionProperties(property);
            }
        }
        {
 String property = properties.getProperty("druid.maxPoolPreparedStatementPerConnectionSize");
 if (property != null && property.length() > 0) {
 try {
 int value = Integer.parseInt(property);
 this.setMaxPoolPreparedStatementPerConnectionSize(value);
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.maxPoolPreparedStatementPerConnectionSize'", e);
                }
            }
        }
        {
 String property = properties.getProperty("druid.initConnectionSqls");
 if (property != null && property.length() > 0) {
 try {
 StringTokenizer tokenizer = new StringTokenizer(property, ";");
 setConnectionInitSqls(Collections.list(tokenizer));
                } catch (NumberFormatException e) {
 LOG.error("illegal property 'druid.initConnectionSqls'", e);
                }
            }
        }
        {
 String property = System.getProperty("druid.load.spifilter.skip");
 if (property != null && !"false".equals(property)) {
 loadSpifilterSkip = true;
            }
        }
    }


 public boolean isKillWhenSocketReadTimeout() {
 return killWhenSocketReadTimeout;
    }


 public void setKillWhenSocketReadTimeout(boolean killWhenSocketTimeOut) {
 this.killWhenSocketReadTimeout = killWhenSocketTimeOut;
    }


 public boolean isUseGlobalDataSourceStat() {
 return useGlobalDataSourceStat;
    }


 public void setUseGlobalDataSourceStat(boolean useGlobalDataSourceStat) {
 this.useGlobalDataSourceStat = useGlobalDataSourceStat;
    }


 public boolean isKeepAlive() {
 return keepAlive;
    }


 public void setKeepAlive(boolean keepAlive) {
 this.keepAlive = keepAlive;
    }


 public String getInitStackTrace() {
 return initStackTrace;
    }


 public boolean isResetStatEnable() {
 return resetStatEnable;
    }


 public void setResetStatEnable(boolean resetStatEnable) {
 this.resetStatEnable = resetStatEnable;
 if (dataSourceStat != null) {
 dataSourceStat.setResetStatEnable(resetStatEnable);
        }
    }


 public long getDiscardCount() {
 return discardCount;
    }


 public void restart() throws SQLException {
 lock.lock();
 try {
 if (activeCount > 0) {
 throw new SQLException("can not restart, activeCount not zero. " + activeCount);
            }
 if (LOG.isInfoEnabled()) {
 LOG.info("{dataSource-" + this.getID() + "} restart");
            }


 this.close();
 this.resetStat();
 this.inited = false;
 this.enable = true;
 this.closed = false;
        } finally {
 lock.unlock();
        }
    }


 public void resetStat() {
 if (!isResetStatEnable()) {
 return;
        }


 lock.lock();
 try {
 connectCount = 0;
 closeCount = 0;
 discardCount = 0;
 recycleCount = 0;
 createCount = 0L;
 directCreateCount = 0;
 destroyCount = 0L;
 removeAbandonedCount = 0;
 notEmptyWaitCount = 0;
 notEmptySignalCount = 0L;
 notEmptyWaitNanos = 0;


 activePeak = activeCount;
 activePeakTime = 0;
 poolingPeak = 0;
 createTimespan = 0;
 lastError = null;
 lastErrorTimeMillis = 0;
 lastCreateError = null;
 lastCreateErrorTimeMillis = 0;
        } finally {
 lock.unlock();
        }


 connectErrorCountUpdater.set(this, 0);
 errorCountUpdater.set(this, 0);
 commitCountUpdater.set(this, 0);
 rollbackCountUpdater.set(this, 0);
 startTransactionCountUpdater.set(this, 0);
 cachedPreparedStatementHitCountUpdater.set(this, 0);
 closedPreparedStatementCountUpdater.set(this, 0);
 preparedStatementCountUpdater.set(this, 0);
 transactionHistogram.reset();
 cachedPreparedStatementDeleteCountUpdater.set(this, 0);
 recycleErrorCountUpdater.set(this, 0);


 resetCountUpdater.incrementAndGet(this);
    }


 public long getResetCount() {
 return this.resetCount;
    }


 public boolean isEnable() {
 return enable;
    }


 public void setEnable(boolean enable) {
 lock.lock();
 try {
 this.enable = enable;
 if (!enable) {
 notEmpty.signalAll();
 notEmptySignalCount++;
            }
        } finally {
 lock.unlock();
        }
    }


 public void setPoolPreparedStatements(boolean value) {
 setPoolPreparedStatements0(value);
    }


 private void setPoolPreparedStatements0(boolean value) {
 if (this.poolPreparedStatements == value) {
 return;
        }


 this.poolPreparedStatements = value;


 if (!inited) {
 return;
        }


 if (LOG.isInfoEnabled()) {
 LOG.info("set poolPreparedStatements " + this.poolPreparedStatements + " -> " + value);
        }


 if (!value) {
 lock.lock();
 try {


 for (int i = 0; i < poolingCount; ++i) {
 DruidConnectionHolder connection = connections[i];


 for (PreparedStatementHolder holder : connection.getStatementPool().getMap().values()) {
 closePreapredStatement(holder);
                    }


 connection.getStatementPool().getMap().clear();
                }
            } finally {
 lock.unlock();
            }
        }
    }


 public void setMaxActive(int maxActive) {
 if (this.maxActive == maxActive) {
 return;
        }


 if (maxActive == 0) {
 throw new IllegalArgumentException("maxActive can't not set zero");
        }


 if (!inited) {
 this.maxActive = maxActive;
 return;
        }


 if (maxActive < this.minIdle) {
 throw new IllegalArgumentException("maxActive less than minIdle, " + maxActive + " < " + this.minIdle);
        }


 if (LOG.isInfoEnabled()) {
 LOG.info("maxActive changed : " + this.maxActive + " -> " + maxActive);
        }


 lock.lock();
 try {
 int allCount = this.poolingCount + this.activeCount;


 if (maxActive > allCount) {
 this.connections = Arrays.copyOf(this.connections, maxActive);
 evictConnections = new DruidConnectionHolder[maxActive];
 keepAliveConnections = new DruidConnectionHolder[maxActive];
            } else {
 this.connections = Arrays.copyOf(this.connections, allCount);
 evictConnections = new DruidConnectionHolder[allCount];
 keepAliveConnections = new DruidConnectionHolder[allCount];
            }


 this.maxActive = maxActive;
        } finally {
 lock.unlock();
        }
    }


 @SuppressWarnings("rawtypes")
 public void setConnectProperties(Properties properties) {
 if (properties == null) {
 properties = new Properties();
        }


 boolean equals;
 if (properties.size() == this.connectProperties.size()) {
 equals = true;
 for (Map.Entry entry : properties.entrySet()) {
 Object value = this.connectProperties.get(entry.getKey());
 Object entryValue = entry.getValue();
 if (value == null && entryValue != null) {
 equals = false;
 break;
                }


 if (!value.equals(entry.getValue())) {
 equals = false;
 break;
                }
            }
        } else {
 equals = false;
        }


 if (!equals) {
 if (inited && LOG.isInfoEnabled()) {
 LOG.info("connectProperties changed : " + this.connectProperties + " -> " + properties);
            }


 configFromPropety(properties);


 for (Filter filter : this.filters) {
 filter.configFromProperties(properties);
            }


 if (exceptionSorter != null) {
 exceptionSorter.configFromProperties(properties);
            }


 if (validConnectionChecker != null) {
 validConnectionChecker.configFromProperties(properties);
            }


 if (statLogger != null) {
 statLogger.configFromProperties(properties);
            }
        }


 this.connectProperties = properties;
    }


 public void init() throws SQLException {
 if (inited) {
 return;
        }


 // bug fixed for dead lock, for issue #2980
 DruidDriver.getInstance();


 final ReentrantLock lock = this.lock;
 try {
 lock.lockInterruptibly();
        } catch (InterruptedException e) {
 throw new SQLException("interrupt", e);
        }


 boolean init = false;
 try {
 if (inited) {
 return;
            }


 initStackTrace = Utils.toString(Thread.currentThread().getStackTrace());


 this.id = DruidDriver.createDataSourceId();
 if (this.id > 1) {
 long delta = (this.id - 1) * 100000;
 this.connectionIdSeedUpdater.addAndGet(this, delta);
 this.statementIdSeedUpdater.addAndGet(this, delta);
 this.resultSetIdSeedUpdater.addAndGet(this, delta);
 this.transactionIdSeedUpdater.addAndGet(this, delta);
            }


 if (this.jdbcUrl != null) {
 this.jdbcUrl = this.jdbcUrl.trim();
 initFromWrapDriverUrl();
            }


 for (Filter filter : filters) {
 filter.init(this);
            }


 if (this.dbType == null || this.dbType.length() == 0) {
 this.dbType = JdbcUtils.getDbType(jdbcUrl, null);
            }


 if (JdbcConstants.MYSQL.equals(this.dbType)
                    || JdbcConstants.MARIADB.equals(this.dbType)
                    || JdbcConstants.ALIYUN_ADS.equals(this.dbType)) {
 boolean cacheServerConfigurationSet = false;
 if (this.connectProperties.containsKey("cacheServerConfiguration")) {
 cacheServerConfigurationSet = true;
                } else if (this.jdbcUrl.indexOf("cacheServerConfiguration") != -1) {
 cacheServerConfigurationSet = true;
                }
 if (cacheServerConfigurationSet) {
 this.connectProperties.put("cacheServerConfiguration", "true");
                }
            }


 if (maxActive <= 0) {
 throw new IllegalArgumentException("illegal maxActive " + maxActive);
            }


 if (maxActive < minIdle) {
 throw new IllegalArgumentException("illegal maxActive " + maxActive);
            }


 if (getInitialSize() > maxActive) {
 throw new IllegalArgumentException("illegal initialSize " + this.initialSize + ", maxActive " + maxActive);
            }


 if (timeBetweenLogStatsMillis > 0 && useGlobalDataSourceStat) {
 throw new IllegalArgumentException("timeBetweenLogStatsMillis not support useGlobalDataSourceStat=true");
            }


 if (maxEvictableIdleTimeMillis < minEvictableIdleTimeMillis) {
 throw new SQLException("maxEvictableIdleTimeMillis must be grater than minEvictableIdleTimeMillis");
            }


 if (this.driverClass != null) {
 this.driverClass = driverClass.trim();
            }


 initFromSPIServiceLoader();


 if (this.driver == null) {
 if (this.driverClass == null || this.driverClass.isEmpty()) {
 this.driverClass = JdbcUtils.getDriverClassName(this.jdbcUrl);
                }


 if (MockDriver.class.getName().equals(driverClass)) {
 driver = MockDriver.instance;
                } else {
 if (jdbcUrl == null && (driverClass == null || driverClass.length() == 0)) {
 throw new SQLException("url not set");
                    }
 driver = JdbcUtils.createDriver(driverClassLoader, driverClass);
                }
            } else {
 if (this.driverClass == null) {
 this.driverClass = driver.getClass().getName();
                }
            }


 initCheck();


 initExceptionSorter();
 initValidConnectionChecker();
 validationQueryCheck();


 if (isUseGlobalDataSourceStat()) {
 dataSourceStat = JdbcDataSourceStat.getGlobal();
 if (dataSourceStat == null) {
 dataSourceStat = new JdbcDataSourceStat("Global", "Global", this.dbType);
 JdbcDataSourceStat.setGlobal(dataSourceStat);
                }
 if (dataSourceStat.getDbType() == null) {
 dataSourceStat.setDbType(this.dbType);
                }
            } else {
 dataSourceStat = new JdbcDataSourceStat(this.name, this.jdbcUrl, this.dbType, this.connectProperties);
            }
 dataSourceStat.setResetStatEnable(this.resetStatEnable);


 connections = new DruidConnectionHolder[maxActive];
 evictConnections = new DruidConnectionHolder[maxActive];
 keepAliveConnections = new DruidConnectionHolder[maxActive];


 SQLException connectError = null;


 if (createScheduler != null && asyncInit) {
 for (int i = 0; i < initialSize; ++i) {
 createTaskCount++;
 CreateConnectionTask task = new CreateConnectionTask(true);
 this.createSchedulerFuture = createScheduler.submit(task);
                }
            } else if (!asyncInit) {
 // init connections
 while (poolingCount < initialSize) {
 try {
 PhysicalConnectionInfo pyConnectInfo = createPhysicalConnection();
 DruidConnectionHolder holder = new DruidConnectionHolder(this, pyConnectInfo);
 connections[poolingCount++] = holder;
                    } catch (SQLException ex) {
 LOG.error("init datasource error, url: " + this.getUrl(), ex);
 if (initExceptionThrow) {
 connectError = ex;
 break;
                        } else {
 Thread.sleep(3000);
                        }
                    }
                }


 if (poolingCount > 0) {
 poolingPeak = poolingCount;
 poolingPeakTime = System.currentTimeMillis();
                }
            }


 createAndLogThread();
 createAndStartCreatorThread();
 createAndStartDestroyThread();


 initedLatch.await();
 init = true;


 initedTime = new Date();
 registerMbean();


 if (connectError != null && poolingCount == 0) {
 throw connectError;
            }


 if (keepAlive) {
 // async fill to minIdle
 if (createScheduler != null) {
 for (int i = 0; i < minIdle; ++i) {
 createTaskCount++;
 CreateConnectionTask task = new CreateConnectionTask(true);
 this.createSchedulerFuture = createScheduler.submit(task);
                    }
                } else {
 this.emptySignal();
                }
            }


        } catch (SQLException e) {
 LOG.error("{dataSource-" + this.getID() + "} init error", e);
 throw e;
        } catch (InterruptedException e) {
 throw new SQLException(e.getMessage(), e);
        } catch (RuntimeException e){
 LOG.error("{dataSource-" + this.getID() + "} init error", e);
 throw e;
        } catch (Error e){
 LOG.error("{dataSource-" + this.getID() + "} init error", e);
 throw e;


        } finally {
 inited = true;
 lock.unlock();


 if (init && LOG.isInfoEnabled()) {
 String msg = "{dataSource-" + this.getID();


 if (this.name != null && !this.name.isEmpty()) {
 msg += ",";
 msg += this.name;
                }


 msg += "} inited";


 LOG.info(msg);
            }
        }
    }


 private void createAndLogThread() {
 if (this.timeBetweenLogStatsMillis <= 0) {
 return;
        }


 String threadName = "Druid-ConnectionPool-Log-" + System.identityHashCode(this);
 logStatsThread = new LogStatsThread(threadName);
 logStatsThread.start();


 this.resetStatEnable = false;
    }


 protected void createAndStartDestroyThread() {
 destroyTask = new DestroyTask();


 if (destroyScheduler != null) {
 long period = timeBetweenEvictionRunsMillis;
 if (period <= 0) {
 period = 1000;
            }
 destroySchedulerFuture = destroyScheduler.scheduleAtFixedRate(destroyTask, period, period,
 TimeUnit.MILLISECONDS);
 initedLatch.countDown();
 return;
        }


 String threadName = "Druid-ConnectionPool-Destroy-" + System.identityHashCode(this);
 destroyConnectionThread = new DestroyConnectionThread(threadName);
 destroyConnectionThread.start();
    }


 protected void createAndStartCreatorThread() {
 if (createScheduler == null) {
 String threadName = "Druid-ConnectionPool-Create-" + System.identityHashCode(this);
 createConnectionThread = new CreateConnectionThread(threadName);
 createConnectionThread.start();
 return;
        }


 initedLatch.countDown();
    }


 /**
     * load filters from SPI ServiceLoader
     * 
     * @see ServiceLoader
     */
 private void initFromSPIServiceLoader() {
 if (loadSpifilterSkip) {
 return;
        }


 if (autoFilters == null) {
 List<Filter> filters = new ArrayList<Filter>();
 ServiceLoader<Filter> autoFilterLoader = ServiceLoader.load(Filter.class);


 for (Filter filter : autoFilterLoader) {
 AutoLoad autoLoad = filter.getClass().getAnnotation(AutoLoad.class);
 if (autoLoad != null && autoLoad.value()) {
 filters.add(filter);
                }
            }
 autoFilters = filters;
        }


 for (Filter filter : autoFilters) {
 if (LOG.isInfoEnabled()) {
 LOG.info("load filter from spi :" + filter.getClass().getName());
            }
 addFilter(filter);
        }
    }


 private void initFromWrapDriverUrl() throws SQLException {
 if (!jdbcUrl.startsWith(DruidDriver.DEFAULT_PREFIX)) {
 return;
        }


 DataSourceProxyConfig config = DruidDriver.parseConfig(jdbcUrl, null);
 this.driverClass = config.getRawDriverClassName();


 LOG.error("error url : '" + jdbcUrl + "', it should be : '" + config.getRawUrl() + "'");


 this.jdbcUrl = config.getRawUrl();
 if (this.name == null) {
 this.name = config.getName();
        }


 for (Filter filter : config.getFilters()) {
 addFilter(filter);
        }
    }


 /**
