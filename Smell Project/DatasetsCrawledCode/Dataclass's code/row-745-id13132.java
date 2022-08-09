public class SftpFileSystemInitializationContext {
 private final String id;
 private final URI uri;
 private final Map<String, ?> environment;
 private String host;
 private int port;
 private BasicCredentialsProvider credentials;
 private PropertyResolver propertyResolver;
 private long maxConnectTime;
 private long maxAuthTime;


 /**
     * @param id The unique identifier assigned to the file-system being created
     * @param uri The original {@link URI} that triggered the file-system creation
     * @param env The environment settings passed along with the URI (may be {@code null})
     */
 public SftpFileSystemInitializationContext(String id, URI uri, Map<String, ?> env) {
 this.id = id;
 this.uri = uri;
 this.environment = env;
    }


 /**
     * @return The unique identifier assigned to the file-system being created
     */
 public String getId() {
 return id;
    }


 /**
     * @return The original {@link URI} that triggered the file-system creation
     */
 public URI getUri() {
 return uri;
    }


 /**
     * @return The environment settings passed along with the URI (may be {@code null})
     */
 public Map<String, ?> getEnvironment() {
 return environment;
    }


 public String getHost() {
 return host;
    }


 public void setHost(String host) {
 this.host = host;
    }


 /**
     * @return The <U>resolved</U> target port from the URI
     */
 public int getPort() {
 return port;
    }


 public void setPort(int port) {
 this.port = port;
    }


 /**
     * @return The credentials recovered from the URI
     */
 public BasicCredentialsProvider getCredentials() {
 return credentials;
    }


 public void setCredentials(BasicCredentialsProvider credentials) {
 this.credentials = credentials;
    }


 /**
     * @return A {@link PropertyResolver} for easy access of any query parameters
     * encoded in the URI
     */
 public PropertyResolver getPropertyResolver() {
 return propertyResolver;
    }


 public void setPropertyResolver(PropertyResolver propertyResolver) {
 this.propertyResolver = propertyResolver;
    }


 /**
     * @return The <U>resolved</U> max. connect timeout (msec.)
     */
 public long getMaxConnectTime() {
 return maxConnectTime;
    }


 public void setMaxConnectTime(long maxConnectTime) {
 this.maxConnectTime = maxConnectTime;
    }


 /**
     * @return The <U>resolved</U> max. authentication timeout (msec.)
     */
 public long getMaxAuthTime() {
 return maxAuthTime;
    }


 public void setMaxAuthTime(long maxAuthTime) {
 this.maxAuthTime = maxAuthTime;
    }


 @Override
 public String toString() {
 return getClass().getSimpleName() + "[" + getId() + "]";
    }
}