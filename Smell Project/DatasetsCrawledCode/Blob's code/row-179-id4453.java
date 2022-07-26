public class SSLConfigClient extends SSLConfig {


 private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SSLConfigClient.class);


 private final Properties properties;
 private final boolean userSslEnabled;
 private final String trustStoreType;
 private final String trustStorePath;
 private final String trustStorePassword;
 private final boolean disableHostVerification;
 private final boolean disableCertificateVerification;
 private final boolean useSystemTrustStore;
 private final String protocol;
 private final int handshakeTimeout;
 private final String provider;


 private final String emptyString = new String();


 public SSLConfigClient(Properties properties) throws DrillException {
 this.properties = properties;
 userSslEnabled = getBooleanProperty(DrillProperties.ENABLE_TLS);
 trustStoreType = getStringProperty(DrillProperties.TRUSTSTORE_TYPE, "JKS");
 trustStorePath = getStringProperty(DrillProperties.TRUSTSTORE_PATH, "");
 trustStorePassword = getStringProperty(DrillProperties.TRUSTSTORE_PASSWORD, "");
 disableHostVerification = getBooleanProperty(DrillProperties.DISABLE_HOST_VERIFICATION);
 disableCertificateVerification = getBooleanProperty(DrillProperties.DISABLE_CERT_VERIFICATION);
 useSystemTrustStore = getBooleanProperty(DrillProperties.USE_SYSTEM_TRUSTSTORE);
 protocol = getStringProperty(DrillProperties.TLS_PROTOCOL, DEFAULT_SSL_PROTOCOL);
 int hsTimeout = getIntProperty(DrillProperties.TLS_HANDSHAKE_TIMEOUT, DEFAULT_SSL_HANDSHAKE_TIMEOUT_MS);
 if (hsTimeout <= 0) {
 hsTimeout = DEFAULT_SSL_HANDSHAKE_TIMEOUT_MS;
    }
 handshakeTimeout = hsTimeout;
 // If provider is OPENSSL then to debug or run this code in an IDE, you will need to enable
 // the dependency on netty-tcnative with the correct classifier for the platform you use.
 // This can be done by enabling the openssl profile.
 // If the IDE is Eclipse, it requires you to install an additional Eclipse plugin available here:
 // http://repo1.maven.org/maven2/kr/motd/maven/os-maven-plugin/1.6.1/os-maven-plugin-1.6.1.jar
 // or from your local maven repository:
 // ~/.m2/repository/kr/motd/maven/os-maven-plugin/1.6.1/os-maven-plugin-1.6.1.jar
 // Note that installing this plugin may require you to start with a new workspace
 provider = getStringProperty(DrillProperties.TLS_PROVIDER, DEFAULT_SSL_PROVIDER);
  }


 private boolean getBooleanProperty(String propName) {
 return (properties != null) && (properties.containsKey(propName))
        && (properties.getProperty(propName).compareToIgnoreCase("true") == 0);
  }


 private String getStringProperty(String name, String defaultValue) {
 String value = "";
 if ( (properties != null) && (properties.containsKey(name))) {
 value = properties.getProperty(name);
    }
 if (value.isEmpty()) {
 value = defaultValue;
    }
 value = value.trim();
 return value;
  }


 private int getIntProperty(String name, int defaultValue) {
 int value = defaultValue;
 if (properties != null) {
 String property = properties.getProperty(name);
 if (property != null && property.length() > 0) {
 value = Integer.decode(property);
      }
    }
 return value;
  }


 public void validateKeyStore() throws DrillException {


  }


 @Override
 public SslContext initNettySslContext() throws DrillException {
 final SslContext sslCtx;


 if (!userSslEnabled) {
 return null;
    }


 TrustManagerFactory tmf;
 try {
 tmf = initializeTrustManagerFactory();
 sslCtx = SslContextBuilder.forClient()
          .sslProvider(getProvider())
          .trustManager(tmf)
          .protocols(protocol)
          .build();
    } catch (Exception e) {
 // Catch any SSL initialization Exceptions here and abort.
 throw new DrillException(new StringBuilder()
          .append("SSL is enabled but cannot be initialized due to the following exception: ")
          .append("[ ")
          .append(e.getMessage())
          .append("]. ")
          .toString());
    }
 this.nettySslContext = sslCtx;
 return sslCtx;
  }


 @Override
 public SSLContext initJDKSSLContext() throws DrillException {
 final SSLContext sslCtx;


 if (!userSslEnabled) {
 return null;
    }


 TrustManagerFactory tmf;
 try {
 tmf = initializeTrustManagerFactory();
 sslCtx = SSLContext.getInstance(protocol);
 sslCtx.init(null, tmf.getTrustManagers(), null);
    } catch (Exception e) {
 // Catch any SSL initialization Exceptions here and abort.
 throw new DrillException(new StringBuilder()
          .append("SSL is enabled but cannot be initialized due to the following exception: ")
          .append("[ ")
          .append(e.getMessage())
          .append("]. ")
          .toString());
    }
 this.jdkSSlContext = sslCtx;
 return sslCtx;
  }


 @Override
 public SSLEngine createSSLEngine(BufferAllocator allocator, String peerHost, int peerPort) {
 SSLEngine engine = super.createSSLEngine(allocator, peerHost, peerPort);


 if (!this.disableHostVerification()) {
 SSLParameters sslParameters = engine.getSSLParameters();
 // only available since Java 7
 sslParameters.setEndpointIdentificationAlgorithm("HTTPS");
 engine.setSSLParameters(sslParameters);
    }


 engine.setUseClientMode(true);


 try {
 engine.setEnableSessionCreation(true);
    } catch (Exception e) {
 // Openssl implementation may throw this.
 logger.debug("Session creation not enabled. Exception: {}", e.getMessage());
    }


 return engine;
  }


 @Override
 public boolean isUserSslEnabled() {
 return userSslEnabled;
  }


 @Override
 public boolean isHttpsEnabled() {
 return false;
  }


 @Override
 public String getKeyStoreType() {
 return emptyString;
  }


 @Override
 public String getKeyStorePath() {
 return emptyString;
  }


 @Override
 public String getKeyStorePassword() {
 return emptyString;
  }


 @Override
 public String getKeyPassword() {
 return emptyString;
  }


 @Override
 public String getTrustStoreType() {
 return trustStoreType;
  }


 @Override
 public boolean hasTrustStorePath() {
 return !trustStorePath.isEmpty();
  }


 @Override
 public String getTrustStorePath() {
 return trustStorePath;
  }


 @Override
 public boolean hasTrustStorePassword() {
 return !trustStorePassword.isEmpty();
  }


 @Override
 public String getTrustStorePassword() {
 return trustStorePassword;
  }


 @Override
 public String getProtocol() {
 return protocol;
  }


 @Override
 public SslProvider getProvider() {
 return provider.equalsIgnoreCase("JDK") ? SslProvider.JDK : SslProvider.OPENSSL;
  }


 @Override
 public int getHandshakeTimeout() {
 return handshakeTimeout;
  }


 @Override
 public Mode getMode() {
 return Mode.CLIENT;
  }


 @Override
 public boolean disableHostVerification() {
 return disableHostVerification;
  }


 @Override
 public boolean disableCertificateVerification() {
 return disableCertificateVerification;
  }


 @Override
 public boolean useSystemTrustStore() {
 return useSystemTrustStore;
  }


 public boolean isSslValid() {
 return true;
  }


}