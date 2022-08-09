@SuppressWarnings("serial")
public class GitHubUser implements Serializable {


 private final long id;


 private final String url;


 private final String login;


 private final String avatarUrl;


 private final String gravatarId;


 private String name;


 private String email;


 public GitHubUser(long id, String url, String login, String avatarUrl, String gravatarId) {
 this.id = id;
 this.url = url;
 this.login = login;
 this.avatarUrl = avatarUrl;
 this.gravatarId = gravatarId;
    }


 public Long getId() { return id; }
 
 public String getUrl() { return url; }


 public String getLogin() { return login; }


 public String getAvatarUrl() { return avatarUrl; }


 public String getGravatarId() { return gravatarId; }
 
 public String getName() { return name; }
 
 public void setName(String name) { this.name = name; }
 
 public String getEmail() { return email; }
 
 public void setEmail(String email) { this.email = email; }
}