public class KafkaTestServer {
 public static final int CACHE_TTL_MS = 1;


 private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTestServer.class);


 private int kafkaPort = -1;
 private TestingServer zkServer;
 private KafkaServerStartable kafkaServer = null;
 private File sentrySitePath = null;


 public KafkaTestServer(File sentrySitePath) throws Exception {
 this.sentrySitePath = sentrySitePath;
 createZkServer();
 this.kafkaPort = TestUtils.getFreePort();
 createKafkaServer();
    }


 public void start() throws Exception {
 kafkaServer.startup();
 LOGGER.info("Started Kafka broker.");
    }


 public void shutdown() {
 if (kafkaServer != null) {
 kafkaServer.shutdown();
 kafkaServer.awaitShutdown();
 LOGGER.info("Stopped Kafka server.");
        }


 if (zkServer != null) {
 try {
 zkServer.stop();
 LOGGER.info("Stopped ZK server.");
            } catch (IOException e) {
 LOGGER.error("Failed to shutdown ZK server.", e);
            }
        }
    }


 private Path getTempDirectory() {
 Path tempDirectory = null;
 try {
 tempDirectory = Files.createTempDirectory("kafka-sentry-");
        } catch (IOException e) {
 LOGGER.error("Failed to create temp dir for Kafka's log dir.");
 throw new RuntimeException(e);
        }
 return tempDirectory;
    }


 private void setupKafkaProps(Properties props) throws UnknownHostException {
 props.put("listeners", "SSL://" + InetAddress.getLocalHost().getHostAddress() + ":" + kafkaPort);
 props.put("log.dir", getTempDirectory().toAbsolutePath().toString());
 props.put("zookeeper.connect", zkServer.getConnectString());
 props.put("replica.socket.timeout.ms", "1500");
 props.put("controller.socket.timeout.ms", "1500");
 props.put("controlled.shutdown.enable", true);
 props.put("delete.topic.enable", false);
 props.put("controlled.shutdown.retry.backoff.ms", "100");
 props.put("port", kafkaPort);
 props.put("offsets.topic.replication.factor", "1");
 props.put("authorizer.class.name", "org.apache.sentry.kafka.authorizer.SentryKafkaAuthorizer");
 props.put("sentry.kafka.site.url", "file://" + sentrySitePath.getAbsolutePath());
 props.put("allow.everyone.if.no.acl.found", "true");
 props.put("ssl.keystore.location", KafkaTestServer.class.getResource("/test.keystore.jks").getPath());
 props.put("ssl.keystore.password", "test-ks-passwd");
 props.put("ssl.key.password", "test-key-passwd");
 props.put("ssl.truststore.location", KafkaTestServer.class.getResource("/test.truststore.jks").getPath());
 props.put("ssl.truststore.password", "test-ts-passwd");
 props.put("security.inter.broker.protocol", "SSL");
 props.put("ssl.client.auth", "required");
 props.put(KafkaAuthConf.KAFKA_SUPER_USERS, "User:CN=superuser;User:CN=superuser1; User:CN=Superuser2 ");
 props.put(KafkaAuthConf.SENTRY_KAFKA_CACHING_ENABLE_NAME, "true");
 props.put(KafkaAuthConf.SENTRY_KAFKA_CACHING_TTL_MS_NAME, String.valueOf(CACHE_TTL_MS));
    }


 private void createKafkaServer() throws UnknownHostException {
 Properties props = new Properties();
 setupKafkaProps(props);
 kafkaServer = KafkaServerStartable.fromProps(props);
    }


 private void createZkServer() throws Exception {
 try {
 zkServer = new TestingServer();
        } catch (Exception e) {
 LOGGER.error("Failed to create testing zookeeper server.");
 throw new RuntimeException(e);
        }
    }


 public String getBootstrapServers() throws UnknownHostException {
 return InetAddress.getLocalHost().getHostAddress() + ":" + kafkaPort;
    }
}