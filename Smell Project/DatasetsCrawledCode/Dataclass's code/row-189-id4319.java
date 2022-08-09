@ConfigurationProperties(prefix = "camel.opentracing")
public class OpenTracingConfigurationProperties {


 /**
     * Sets exclude pattern(s) that will disable tracing for Camel messages that
     * matches the pattern.
     */
 private Set<String> excludePatterns;
 /**
     * Activate or deactivate dash encoding in headers (required by JMS) for
     * messaging
     */
 private Boolean encoding;


 public Set<String> getExcludePatterns() {
 return excludePatterns;
    }


 public void setExcludePatterns(Set<String> excludePatterns) {
 this.excludePatterns = excludePatterns;
    }


 public Boolean getEncoding() {
 return encoding;
    }


 public void setEncoding(Boolean encoding) {
 this.encoding = encoding;
    }
}