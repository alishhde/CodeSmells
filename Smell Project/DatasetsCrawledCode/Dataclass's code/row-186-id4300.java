 interface Failover {
 String PREFIX = HdfsClientConfigKeys.PREFIX + "failover.";


 String PROXY_PROVIDER_KEY_PREFIX = PREFIX + "proxy.provider";
 String MAX_ATTEMPTS_KEY = PREFIX + "max.attempts";
 int MAX_ATTEMPTS_DEFAULT = 15;
 String SLEEPTIME_BASE_KEY = PREFIX + "sleep.base.millis";
 int SLEEPTIME_BASE_DEFAULT = 500;
 String SLEEPTIME_MAX_KEY = PREFIX + "sleep.max.millis";
 int SLEEPTIME_MAX_DEFAULT = 15000;
 String CONNECTION_RETRIES_KEY = PREFIX + "connection.retries";
 int CONNECTION_RETRIES_DEFAULT = 0;
 String CONNECTION_RETRIES_ON_SOCKET_TIMEOUTS_KEY =
 PREFIX + "connection.retries.on.timeouts";
 int CONNECTION_RETRIES_ON_SOCKET_TIMEOUTS_DEFAULT = 0;
 String RANDOM_ORDER = PREFIX + "random.order";
 boolean RANDOM_ORDER_DEFAULT = false;
 String RESOLVE_ADDRESS_NEEDED_KEY = PREFIX + "resolve-needed";
 boolean RESOLVE_ADDRESS_NEEDED_DEFAULT = false;
 String RESOLVE_SERVICE_KEY = PREFIX + "resolver.impl";
  }