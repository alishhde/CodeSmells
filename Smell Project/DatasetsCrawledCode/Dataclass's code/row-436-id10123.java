public class BuildProperties extends AbstractProperties {


 public BuildProperties(PropertiesAccessor accessor) {
 super(accessor);
    }


 public Map<String, String> getAllProps() {
 return accessor.getBuildProperties();
    }


}