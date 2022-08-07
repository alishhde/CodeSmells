public class WSS4JInInterceptorFactory {


 private Properties properties;


 public Properties getProperties() {
 return properties;
    }


 public void setProperties(Properties properties) {
 this.properties = properties;
    }


 public WSS4JInInterceptor create() {
 final Map<String, Object> map = new HashMap<String, Object>();
 for (Map.Entry<Object, Object> entry : properties.entrySet()) {
 map.put(entry.getKey().toString(), entry.getValue());
        }
 properties.clear();
 return new WSS4JInInterceptor(map);
    }
}