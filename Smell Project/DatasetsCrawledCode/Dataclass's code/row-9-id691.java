public class Ipam {
 private String driver;
 private List<IpamConfig> config;
 private Map<String, String> options;


 public String getDriver() {
 return driver;
  }


 public void setDriver(String driver) {
 this.driver = driver;
  }


 public Ipam withDriver(String driver) {
 this.driver = driver;
 return this;
  }


 public List<IpamConfig> getConfig() {
 return config;
  }


 public void setConfig(List<IpamConfig> config) {
 this.config = config;
  }


 public Ipam withConfig(List<IpamConfig> config) {
 this.config = config;
 return this;
  }


 public Map<String, String> getOptions() {
 return options;
  }


 public void setOptions(Map<String, String> options) {
 this.options = options;
  }


 public Ipam withOptions(Map<String, String> options) {
 this.options = options;
 return this;
  }


 @Override
 public boolean equals(Object obj) {
 if (this == obj) {
 return true;
    }
 if (!(obj instanceof Ipam)) {
 return false;
    }
 final Ipam that = (Ipam) obj;
 return Objects.equals(driver, that.driver)
        && getConfig().equals(that.getConfig())
        && getOptions().equals(that.getOptions());
  }


 @Override
 public int hashCode() {
 int hash = 7;
 hash = 31 * hash + Objects.hashCode(driver);
 hash = 31 * hash + getConfig().hashCode();
 hash = 31 * hash + getOptions().hashCode();
 return hash;
  }


 @Override
 public String toString() {
 return "Ipam{"
        + "driver='"
        + driver
        + '\''
        + ", config="
        + config
        + ", options="
        + options
        + '}';
  }
}