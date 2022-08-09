@XmlRootElement(name = "realm")
@XmlType
public class RealmTO implements EntityTO, TemplatableTO {


 private static final long serialVersionUID = 516330662956254391L;


 private String key;


 private String name;


 private String parent;


 private String fullPath;


 private String accountPolicy;


 private String passwordPolicy;


 private final List<String> actions = new ArrayList<>();


 @XmlJavaTypeAdapter(XmlGenericMapAdapter.class)
 private final Map<String, AnyTO> templates = new HashMap<>();


 private final Set<String> resources = new HashSet<>();


 @Override
 public String getKey() {
 return key;
    }


 @Override
 public void setKey(final String key) {
 this.key = key;
    }


 public String getName() {
 return name;
    }


 public void setName(final String name) {
 this.name = name;
    }


 public String getParent() {
 return parent;
    }


 public void setParent(final String parent) {
 this.parent = parent;
    }


 public String getFullPath() {
 return fullPath;
    }


 @PathParam("fullPath")
 public void setFullPath(final String fullPath) {
 this.fullPath = fullPath;
    }


 public String getAccountPolicy() {
 return accountPolicy;
    }


 public void setAccountPolicy(final String accountPolicy) {
 this.accountPolicy = accountPolicy;
    }


 public String getPasswordPolicy() {
 return passwordPolicy;
    }


 public void setPasswordPolicy(final String passwordPolicy) {
 this.passwordPolicy = passwordPolicy;
    }


 @XmlElementWrapper(name = "actions")
 @XmlElement(name = "action")
 @JsonProperty("actions")
 public List<String> getActions() {
 return actions;
    }


 @JsonProperty
 @Override
 public Map<String, AnyTO> getTemplates() {
 return templates;
    }


 @XmlElementWrapper(name = "resources")
 @XmlElement(name = "resource")
 @JsonProperty("resources")
 public Set<String> getResources() {
 return resources;
    }


}