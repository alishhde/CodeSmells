public class BasicBundleInfo {


 private String pkgName;


 /**
     * The main dex depends on + the md5 that is currently dependent
     */
 private String unique_tag;


 private String applicationName;


 private String version;




 public Boolean getIsMBundle() {
 return isMBundle;
    }


 public void setIsMBundle(boolean mainBundle) {
 isMBundle = mainBundle;
    }


 private Boolean isMBundle = false;


 private List<String> dependency = Lists.newArrayList();


 private List<String> activities = Lists.newArrayList();


 private List<String> services = Lists.newArrayList();


 private List<String> receivers = Lists.newArrayList();


 private List<String> contentProviders = Lists.newArrayList();


 private HashMap<String,String> remoteFragments= new HashMap<String,String>();


 private HashMap<String,String> remoteViews = new HashMap<String,String>();


 private HashMap<String,String> remoteTransactors = new HashMap<String,String>();


 private Boolean isInternal = true;


 public HashMap<String, String> getRemoteViews() {
 return remoteViews;
    }


 public void setRemoteViews(HashMap<String, String> remoteViews) {
 this.remoteViews = remoteViews;
    }


 public HashMap<String, String> getRemoteTransactors() {
 return remoteTransactors;
    }


 public void setRemoteTransactors(HashMap<String, String> remoteTransactors) {
 this.remoteTransactors = remoteTransactors;
    }


 public HashMap<String, String> getRemoteFragments() {
 return remoteFragments;
    }


 public void setRemoteFragments(HashMap<String, String> remoteFragments) {
 this.remoteFragments = remoteFragments;
    }


 public String getPkgName() {
 return pkgName;
    }


 public void setPkgName(String pkgName) {
 this.pkgName = pkgName;
    }


 public String getApplicationName() {
 return applicationName;
    }


 public void setApplicationName(String applicationName) {
 this.applicationName = applicationName;
    }


 public String getVersion() {
 return version;
    }


 public void setVersion(String version) {
 this.version = version;
    }


 public List<String> getDependency() {
 return dependency;
    }


 public void setDependency(List<String> dependency) {
 this.dependency = dependency;
    }


 public List<String> getActivities() {
 return activities;
    }


 public void setActivities(List<String> activities) {
 this.activities = activities;
    }


 public List<String> getServices() {
 return services;
    }


 public void setServices(List<String> services) {
 this.services = services;
    }


 public List<String> getReceivers() {
 return receivers;
    }


 public void setReceivers(List<String> receivers) {
 this.receivers = receivers;
    }


 public List<String> getContentProviders() {
 return contentProviders;
    }


 public void setContentProviders(List<String> contentProviders) {
 this.contentProviders = contentProviders;
    }


 public boolean getIsInternal() {
 return isInternal;
    }


 public void setIsInternal(boolean internal) {
 isInternal = internal;
    }


 public String getUnique_tag() {
 return unique_tag;
    }


 public void setUnique_tag(String unique_tag) {
 this.unique_tag = unique_tag;
    }
}