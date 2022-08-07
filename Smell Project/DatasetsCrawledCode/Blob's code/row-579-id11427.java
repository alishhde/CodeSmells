public class WebServer {
 public static final Logger LOG = LoggerFactory.getLogger(WebServer.class);


 private HttpServer2 httpServer;
 private final HasConfig conf;


 private InetSocketAddress httpAddress;
 private InetSocketAddress httpsAddress;


 protected static final String HAS_SERVER_ATTRIBUTE_KEY = "hasserver";


 public WebServer(HasConfig conf) {
 this.conf = conf;
    }


 public HasConfig getConf() {
 return conf;
    }


 private void init() {


 final String pathSpec = "/has/v1/*";


 // add has packages
 httpServer.addJerseyResourcePackage(AsRequestApi.class
                .getPackage().getName(),
 pathSpec);
    }


 public void defineFilter() {
 String authType = conf.getString(WebConfigKey.HAS_AUTHENTICATION_FILTER_AUTH_TYPE);
 if (authType.equals("kerberos")) {
 // add authentication filter for webhdfs
 final String className = conf.getString(
 WebConfigKey.HAS_AUTHENTICATION_FILTER_KEY,
 WebConfigKey.HAS_AUTHENTICATION_FILTER_DEFAULT);


 final String name = className;


 Map<String, String> params = getAuthFilterParams(conf);


 String kadminPathSpec = "/has/v1/kadmin/*";
 String hadminPathSpec = "/has/v1/hadmin/*";
 HttpServer2.defineFilter(httpServer.getWebAppContext(), name, className,
 params, new String[]{kadminPathSpec, hadminPathSpec});
 HttpServer2.LOG.info("Added filter '" + name + "' (class=" + className
                + ")");
        }
    }


 public void defineConfFilter() {
 String confFilterName = ConfFilter.class.getName();
 String confPath = "/has/v1/conf/*";
 HttpServer2.defineFilter(httpServer.getWebAppContext(), confFilterName, confFilterName,
 getAuthFilterParams(conf), new String[]{confPath});
 HttpServer2.LOG.info("Added filter '" + confFilterName + "' (class=" + confFilterName
                + ")");
    }


 private Map<String, String> getAuthFilterParams(HasConfig conf) {
 Map<String, String> params = new HashMap<>();


 String authType = conf.getString(WebConfigKey.HAS_AUTHENTICATION_FILTER_AUTH_TYPE);
 if (authType != null && !authType.isEmpty()) {
 params.put(AuthenticationFilter.AUTH_TYPE, authType);
        }
 String principal = conf.getString(WebConfigKey.HAS_AUTHENTICATION_KERBEROS_PRINCIPAL_KEY);
 if (principal != null && !principal.isEmpty()) {
 try {
 principal = SecurityUtil.getServerPrincipal(principal,
 getHttpsAddress().getHostName());
            } catch (IOException e) {
 LOG.warn("Errors occurred when get server principal. " + e.getMessage());
            }
 params.put(KerberosAuthenticationHandler.PRINCIPAL, principal);
        }
 String keytab = conf.getString(WebConfigKey.HAS_AUTHENTICATION_KERBEROS_KEYTAB_KEY);
 if (keytab != null && !keytab.isEmpty()) {
 params.put(KerberosAuthenticationHandler.KEYTAB, keytab);
        }
 String rule = conf.getString(WebConfigKey.HAS_AUTHENTICATION_KERBEROS_NAME_RULES);
 if (rule != null && !rule.isEmpty()) {
 params.put(KerberosAuthenticationHandler.NAME_RULES, rule);
        } else {
 params.put(KerberosAuthenticationHandler.NAME_RULES, "DEFAULT");
        }
 return params;
    }


 public InetSocketAddress getBindAddress() {
 if (httpAddress != null) {
 return httpAddress;
        } else if (httpsAddress != null) {
 return httpsAddress;
        } else {
 return null;
        }
    }


 /**
     * for information related to the different configuration options and
     * Http Policy is decided.
     *
     * @throws HasException HAS exception when starting web server
     */
 public void start() throws HasException {


 HttpConfig.Policy policy = getHttpPolicy(conf);


 final String bindHost =
 conf.getString(WebConfigKey.HAS_HTTPS_BIND_HOST_KEY);
 InetSocketAddress httpAddr = null;
 if (policy.isHttpEnabled()) {
 final String httpAddrString = conf.getString(
 WebConfigKey.HAS_HTTP_ADDRESS_KEY,
 WebConfigKey.HAS_HTTP_ADDRESS_DEFAULT);
 httpAddr = NetUtils.createSocketAddr(httpAddrString);
 if (bindHost != null && !bindHost.isEmpty()) {
 httpAddr = new InetSocketAddress(bindHost, httpAddr.getPort());
            }
 LOG.info("Get the http address: " + httpAddr);
        }


 InetSocketAddress httpsAddr = null;
 if (policy.isHttpsEnabled()) {
 final String httpsAddrString = conf.getString(
 WebConfigKey.HAS_HTTPS_ADDRESS_KEY,
 WebConfigKey.HAS_HTTPS_ADDRESS_DEFAULT);
 httpsAddr = NetUtils.createSocketAddr(httpsAddrString);


 if (bindHost != null && !bindHost.isEmpty()) {
 httpsAddr = new InetSocketAddress(bindHost, httpsAddr.getPort());
            }
 LOG.info("Get the https address: " + httpsAddr);
        }


 HttpServer2.Builder builder = httpServerTemplateForHAS(conf, httpAddr, httpsAddr, "has");


 try {
 httpServer = builder.build();
        } catch (IOException e) {
 throw new HasException("Errors occurred when building http server. " + e.getMessage());
        }


 init();


 try {
 httpServer.start();
        } catch (IOException e) {
 throw new HasException("Errors occurred when starting http server. " + e.getMessage());
        }
 int connIdx = 0;
 if (policy.isHttpEnabled()) {
 httpAddress = httpServer.getConnectorAddress(connIdx++);
 if (httpAddress != null) {
 conf.setString(WebConfigKey.HAS_HTTP_ADDRESS_KEY,
 NetUtils.getHostPortString(httpAddress));
            }
        }


 if (policy.isHttpsEnabled()) {
 httpsAddress = httpServer.getConnectorAddress(connIdx);
 if (httpsAddress != null) {
 conf.setString(WebConfigKey.HAS_HTTPS_ADDRESS_KEY,
 NetUtils.getHostPortString(httpsAddress));
            }
        }
    }


 public void setWebServerAttribute(HasServer hasServer) {
 httpServer.setAttribute(HAS_SERVER_ATTRIBUTE_KEY, hasServer);
    }


 public static HasServer getHasServerFromContext(ServletContext context) {
 return (HasServer) context.getAttribute(HAS_SERVER_ATTRIBUTE_KEY);
    }


 /**
     * Get http policy.
     *
     * @param conf the HAS config
     * @return HttpConfig.Policy the policy
     */
 public HttpConfig.Policy getHttpPolicy(HasConfig conf) {
 String policyStr = conf.getString(WebConfigKey.HAS_HTTP_POLICY_KEY,
 WebConfigKey.HAS_HTTP_POLICY_DEFAULT);
 HttpConfig.Policy policy = HttpConfig.Policy.fromString(policyStr);
 if (policy == null) {
 throw new HadoopIllegalArgumentException("Unrecognized value '"
                + policyStr + "' for " + WebConfigKey.HAS_HTTP_POLICY_KEY);
        }


 conf.setString(WebConfigKey.HAS_HTTP_POLICY_KEY, policy.name());
 return policy;
    }


 /**
     * Return a HttpServer.Builder that the HAS can use to
     * initialize their HTTP / HTTPS server.
     *
     * @param conf the HAS config
     * @param httpAddr the InetSocketAddress of http
     * @param httpsAddr the InetSocketAddress of https
     * @param name the host name
     * @return HttpServer2.Builder the builder
     * @throws HasException HAS exception
     */
 public HttpServer2.Builder httpServerTemplateForHAS(
 HasConfig conf, final InetSocketAddress httpAddr, final InetSocketAddress httpsAddr,
 String name) throws HasException {
 HttpConfig.Policy policy = getHttpPolicy(conf);


 HttpServer2.Builder builder = new HttpServer2.Builder().setName(name);


 if (policy.isHttpEnabled()) {
 if (httpAddr != null && httpAddr.getPort() == 0) {
 builder.setFindPort(true);
            }


 URI uri = URI.create("http://" + NetUtils.getHostPortString(httpAddr));
 builder.addEndpoint(uri);
 LOG.info("Starting Web-server for " + name + " at: " + uri);
        }


 if (policy.isHttpsEnabled() && httpsAddr != null) {
 HasConfig sslConf = loadSslConfiguration(conf);
 loadSslConfToHttpServerBuilder(builder, sslConf);


 if (httpsAddr != null && httpsAddr.getPort() == 0) {
 builder.setFindPort(true);
            }


 URI uri = URI.create("https://" + NetUtils.getHostPortString(httpsAddr));
 builder.addEndpoint(uri);
 LOG.info("Starting Web-server for " + name + " at: " + uri);
        }


 return builder;
    }


 /**
     * Load HTTPS-related configuration.
     *
     * @param conf HAS config
     * @return HasConfig after loading ssl configuration
     * @throws HasException HAS exception when loading HTTPS related configuration
     */
 public HasConfig loadSslConfiguration(HasConfig conf) throws HasException {
 HasConfig sslConf = new HasConfig();


 String sslConfigString = conf.getString(
 WebConfigKey.HAS_SERVER_HTTPS_KEYSTORE_RESOURCE_KEY,
 WebConfigKey.HAS_SERVER_HTTPS_KEYSTORE_RESOURCE_DEFAULT);
 LOG.info("Get the ssl config file: " + sslConfigString);
 File sslConfig = new File(sslConfigString);
 if (!sslConfig.exists()) {
 throw new HasException("The ssl server config file "
                + sslConfigString + " does not exist.");
        }
 try {
 sslConf.addIniConfig(sslConfig);
        } catch (IOException e) {
 throw new HasException("Errors occurred when adding config. " + e.getMessage());
        }


 final String[] reqSslProps = {
 WebConfigKey.HAS_SERVER_HTTPS_TRUSTSTORE_LOCATION_KEY,
 WebConfigKey.HAS_SERVER_HTTPS_KEYSTORE_LOCATION_KEY,
 WebConfigKey.HAS_SERVER_HTTPS_KEYSTORE_PASSWORD_KEY,
 WebConfigKey.HAS_SERVER_HTTPS_KEYPASSWORD_KEY
        };


 // Check if the required properties are included
 for (String sslProp : reqSslProps) {
 if (sslConf.getString(sslProp) == null) {
 LOG.warn("SSL config " + sslProp + " is missing. If "
                    + WebConfigKey.HAS_SERVER_HTTPS_KEYSTORE_RESOURCE_KEY
                    + " is specified, make sure it is a relative path");
            }
        }


 boolean requireClientAuth = conf.getBoolean(WebConfigKey.HAS_CLIENT_HTTPS_NEED_AUTH_KEY,
 WebConfigKey.HAS_CLIENT_HTTPS_NEED_AUTH_DEFAULT);
 sslConf.setBoolean(WebConfigKey.HAS_CLIENT_HTTPS_NEED_AUTH_KEY, requireClientAuth);
 return sslConf;
    }


 public HttpServer2.Builder loadSslConfToHttpServerBuilder(HttpServer2.Builder builder,
 HasConfig sslConf) {
 return builder
            .needsClientAuth(
 sslConf.getBoolean(WebConfigKey.HAS_CLIENT_HTTPS_NEED_AUTH_KEY,
 WebConfigKey.HAS_CLIENT_HTTPS_NEED_AUTH_DEFAULT))
            .keyPassword(getPassword(sslConf, WebConfigKey.HAS_SERVER_HTTPS_KEYPASSWORD_KEY))
            .keyStore(sslConf.getString("ssl.server.keystore.location"),
 getPassword(sslConf, WebConfigKey.HAS_SERVER_HTTPS_KEYSTORE_PASSWORD_KEY),
 sslConf.getString("ssl.server.keystore.type", "jks"))
            .trustStore(sslConf.getString("ssl.server.truststore.location"),
 getPassword(sslConf, WebConfigKey.HAS_SERVER_HTTPS_TRUSTSTORE_PASSWORD_KEY),
 sslConf.getString("ssl.server.truststore.type", "jks"))
            .excludeCiphers(
 sslConf.getString("ssl.server.exclude.cipher.list"));
    }


 /**
     * Leverages the Configuration.getPassword method to attempt to get
     * passwords from the CredentialProvider API before falling back to
     * clear text in config - if falling back is allowed.
     *
     * @param conf  Configuration instance
     * @param alias name of the credential to retreive
     * @return String credential value or null
     */
 public String getPassword(HasConfig conf, String alias) {


 return conf.getString(alias);
    }


 public void stop() throws Exception {
 if (httpServer != null) {
 httpServer.stop();
        }
    }


 public InetSocketAddress getHttpAddress() {
 return httpAddress;
    }


 public InetSocketAddress getHttpsAddress() {
 return httpsAddress;
    }
}