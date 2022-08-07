@JacksonXmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {


 private static final long serialVersionUID = 4247427179764560935L;


 private Integer id;
 private String username;
 private String name;
 @JsonProperty("first_name")
 private String firstName;
 @JsonProperty("last_name")
 private String lastName;
 private String email;
 private String url;
 private String description;
 private String link;
 private String locale;
 private String nickname;
 private String slug;
 @JsonProperty("registered_date")
 private String registeredDate;
 private List<String> roles;
 private Map<String, String> capabilities;
 @JsonProperty("extra_capabilities")
 private Map<String, String> extraCapabilities;
 @JsonProperty("avatar_urls")
 private Map<String, String> avatarUrls;
 @JsonProperty("meta")
 private List<Map<String, String>> meta;


 public User() {


    }


 public Integer getId() {
 return id;
    }


 public void setId(Integer id) {
 this.id = id;
    }


 public String getUsername() {
 return username;
    }


 public void setUsername(String username) {
 this.username = username;
    }


 public String getName() {
 return name;
    }


 public void setName(String name) {
 this.name = name;
    }


 public String getFirstName() {
 return firstName;
    }


 public void setFirstName(String firstName) {
 this.firstName = firstName;
    }


 public String getLastName() {
 return lastName;
    }


 public void setLastName(String lastName) {
 this.lastName = lastName;
    }


 public String getEmail() {
 return email;
    }


 public void setEmail(String email) {
 this.email = email;
    }


 public String getUrl() {
 return url;
    }


 public void setUrl(String url) {
 this.url = url;
    }


 public String getDescription() {
 return description;
    }


 public void setDescription(String description) {
 this.description = description;
    }


 public String getLink() {
 return link;
    }


 public void setLink(String link) {
 this.link = link;
    }


 public String getLocale() {
 return locale;
    }


 public void setLocale(String locale) {
 this.locale = locale;
    }


 public String getNickname() {
 return nickname;
    }


 public void setNickname(String nickname) {
 this.nickname = nickname;
    }


 public String getSlug() {
 return slug;
    }


 public void setSlug(String slug) {
 this.slug = slug;
    }


 public String getRegisteredDate() {
 return registeredDate;
    }


 public void setRegisteredDate(String registeredDate) {
 this.registeredDate = registeredDate;
    }


 public List<String> getRoles() {
 return roles;
    }


 public void setRoles(List<String> roles) {
 this.roles = roles;
    }


 public Map<String, String> getCapabilities() {
 return capabilities;
    }


 public void setCapabilities(Map<String, String> capabilities) {
 this.capabilities = capabilities;
    }


 public Map<String, String> getExtraCapabilities() {
 return extraCapabilities;
    }


 public void setExtraCapabilities(Map<String, String> extraCapabilities) {
 this.extraCapabilities = extraCapabilities;
    }


 public Map<String, String> getAvatarUrls() {
 return avatarUrls;
    }


 public void setAvatarUrls(Map<String, String> avatarUrls) {
 this.avatarUrls = avatarUrls;
    }


 public List<Map<String, String>> getMeta() {
 return meta;
    }


 public void setMeta(List<Map<String, String>> meta) {
 this.meta = meta;
    }


 @Override
 public String toString() {
 return toStringHelper(this).addValue(this.id).addValue(this.username).addValue(this.email).addValue(this.name).toString();
    }


}