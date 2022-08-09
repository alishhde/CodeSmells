@XmlRootElement(name = "roles", namespace = "http://org.apache.cxf.fediz/")
public class Roles {


 private Collection<Role> roles;


 public Roles() {
    }


 public Roles(Collection<Role> roles) {
 this.roles = roles;
    }


 @XmlElementRef
 public Collection<Role> getRoles() {
 return roles;
    }


 public void setRoles(Collection<Role> roles) {
 this.roles = roles;
    }
}