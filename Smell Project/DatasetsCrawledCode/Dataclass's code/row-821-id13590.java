@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Applications {


 private List<AppInfo> app;


 public List<AppInfo> getApp() {
 return app;
    }


 public void setApp(List<AppInfo> app) {
 this.app = app;
    }


}