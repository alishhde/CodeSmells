@Document
public class Customer extends AbstractDocument {


 private String firstname, lastname;


 @Field("email")
 @Indexed(unique = true)
 private EmailAddress emailAddress;
 private Set<Address> addresses = new HashSet<Address>();


 /**
	 * Creates a new {@link Customer} from the given firstname and lastname.
	 * 
	 * @param firstname must not be {@literal null} or empty.
	 * @param lastname must not be {@literal null} or empty.
	 */
 public Customer(String firstname, String lastname) {


 Assert.hasText(firstname);
 Assert.hasText(lastname);


 this.firstname = firstname;
 this.lastname = lastname;
	}


 protected Customer() {


	}


 /**
	 * Adds the given {@link Address} to the {@link Customer}.
	 * 
	 * @param address must not be {@literal null}.
	 */
 public void add(Address address) {


 Assert.notNull(address);
 this.addresses.add(address);
	}


 /**
	 * Returns the firstname of the {@link Customer}.
	 * 
	 * @return
	 */
 public String getFirstname() {
 return firstname;
	}


 /**
	 * Returns the lastname of the {@link Customer}.
	 * 
	 * @return
	 */
 public String getLastname() {
 return lastname;
	}


 /**
	 * Sets the lastname of the {@link Customer}.
	 * 
	 * @param lastname
	 */
 public void setLastname(String lastname) {
 this.lastname = lastname;
	}


 /**
	 * Returns the {@link EmailAddress} of the {@link Customer}.
	 * 
	 * @return
	 */
 public EmailAddress getEmailAddress() {
 return emailAddress;
	}


 /**
	 * Sets the {@link Customer}'s {@link EmailAddress}.
	 * 
	 * @param emailAddress must not be {@literal null}.
	 */
 public void setEmailAddress(EmailAddress emailAddress) {
 this.emailAddress = emailAddress;
	}


 /**
	 * Return the {@link Customer}'s addresses.
	 * 
	 * @return
	 */
 public Set<Address> getAddresses() {
 return Collections.unmodifiableSet(addresses);
	}
}