@Entity
public class Customer1131 {


 @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;
 private String firstName;
 private String lastName;


 protected Customer1131() {}


 public Customer1131(String firstName, String lastName) {
 this.firstName = firstName;
 this.lastName = lastName;
	}


 @Override
 public String toString() {
 return String.format("Customer1131[id=%d, firstName='%s', lastName='%s']", id, firstName, lastName);
	}


}