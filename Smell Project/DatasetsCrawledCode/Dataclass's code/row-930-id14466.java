@XmlTransient
public abstract class PendingActionNotificationResponse
 extends ImmutableObject implements ResponseData {


 /** The inner name type that contains a name and the result boolean. */
 @Embed
 static class NameOrId extends ImmutableObject {
 @XmlValue
 String value;


 @XmlAttribute(name = "paResult")
 boolean actionResult;
  }


 @XmlTransient
 NameOrId nameOrId;


 @XmlElement(name = "paTRID")
 Trid trid;


 @XmlElement(name = "paDate")
 DateTime processedDate;


 public String getNameAsString() {
 return nameOrId.value;
  }


 @VisibleForTesting
 public Trid getTrid() {
 return trid;
  }


 @VisibleForTesting
 public boolean getActionResult() {
 return nameOrId.actionResult;
  }


 protected static <T extends PendingActionNotificationResponse> T init(
 T response, String nameOrId, boolean actionResult, Trid trid, DateTime processedDate) {
 response.nameOrId = new NameOrId();
 response.nameOrId.value = nameOrId;
 response.nameOrId.actionResult = actionResult;
 response.trid = trid;
 response.processedDate = processedDate;
 return response;
  }


 /** An adapter to output the XML in response to resolving a pending command on a domain. */
 @Embed
 @XmlRootElement(name = "panData", namespace = "urn:ietf:params:xml:ns:domain-1.0")
 @XmlType(
 propOrder = {"name", "trid", "processedDate"},
 namespace = "urn:ietf:params:xml:ns:domain-1.0")
 public static class DomainPendingActionNotificationResponse
 extends PendingActionNotificationResponse {


 @XmlElement
 NameOrId getName() {
 return nameOrId;
    }


 public static DomainPendingActionNotificationResponse create(
 String fullyQualifiedDomainName, boolean actionResult, Trid trid, DateTime processedDate) {
 return init(
 new DomainPendingActionNotificationResponse(),
 fullyQualifiedDomainName,
 actionResult,
 trid,
 processedDate);
    }
  }


 /** An adapter to output the XML in response to resolving a pending command on a contact. */
 @Embed
 @XmlRootElement(name = "panData", namespace = "urn:ietf:params:xml:ns:contact-1.0")
 @XmlType(
 propOrder = {"id", "trid", "processedDate"},
 namespace = "urn:ietf:params:xml:ns:contact-1.0")
 public static class ContactPendingActionNotificationResponse
 extends PendingActionNotificationResponse {


 @XmlElement
 NameOrId getId() {
 return nameOrId;
    }


 public static ContactPendingActionNotificationResponse create(
 String contactId, boolean actionResult, Trid trid, DateTime processedDate) {
 return init(
 new ContactPendingActionNotificationResponse(),
 contactId,
 actionResult,
 trid,
 processedDate);
    }
  }


 /** An adapter to output the XML in response to resolving a pending command on a host. */
 @Embed
 @XmlRootElement(name = "panData", namespace = "urn:ietf:params:xml:ns:domain-1.0")
 @XmlType(
 propOrder = {"name", "trid", "processedDate"},
 namespace = "urn:ietf:params:xml:ns:domain-1.0"
  )
 public static class HostPendingActionNotificationResponse
 extends PendingActionNotificationResponse {


 @XmlElement
 NameOrId getName() {
 return nameOrId;
    }


 public static HostPendingActionNotificationResponse create(
 String fullyQualifiedHostName, boolean actionResult, Trid trid, DateTime processedDate) {
 return init(
 new HostPendingActionNotificationResponse(),
 fullyQualifiedHostName,
 actionResult,
 trid,
 processedDate);
    }
  }
}