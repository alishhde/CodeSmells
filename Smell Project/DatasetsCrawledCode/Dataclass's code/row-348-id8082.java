@InterfaceAudience.Public
@InterfaceStability.Evolving
public class MetricsTag implements MetricsInfo {
 private final MetricsInfo info;
 private final String value;


 /**
   * Construct the tag with name, description and value
   * @param info  of the tag
   * @param value of the tag
   */
 public MetricsTag(MetricsInfo info, String value) {
 this.info = checkNotNull(info, "tag info");
 this.value = value;
  }


 @Override public String name() {
 return info.name();
  }


 @Override public String description() {
 return info.description();
  }


 /**
   * @return the info object of the tag
   */
 public MetricsInfo info() {
 return info;
  }


 /**
   * Get the value of the tag
   * @return  the value
   */
 public String value() {
 return value;
  }


 @Override public boolean equals(Object obj) {
 if (obj instanceof MetricsTag) {
 final MetricsTag other = (MetricsTag) obj;
 return Objects.equal(info, other.info()) &&
 Objects.equal(value, other.value());
    }
 return false;
  }


 @Override public int hashCode() {
 return Objects.hashCode(info, value);
  }


 @Override public String toString() {
 return Objects.toStringHelper(this)
        .add("info", info)
        .add("value", value())
        .toString();
  }
}