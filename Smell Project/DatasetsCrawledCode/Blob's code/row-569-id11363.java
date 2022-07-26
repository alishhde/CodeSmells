@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EObject", propOrder = { "extensions" })
public class EObject {


 @XmlElement(name = "Extension", namespace = "http://www.omg.org/XMI")
 protected List<Extension> extensions;
 @XmlAttribute(namespace = "http://www.omg.org/XMI")
 @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
 @XmlID
 protected String id;
 @XmlAttribute(namespace = "http://www.omg.org/XMI")
 protected QName type;
 @XmlAttribute(namespace = "http://www.omg.org/XMI")
 protected String version;
 @XmlAttribute
 protected String href;
 @XmlAttribute(namespace = "http://www.omg.org/XMI")
 @XmlIDREF
 protected Object idref;
 @XmlAttribute(namespace = "http://www.omg.org/XMI")
 protected String label;
 @XmlAttribute(namespace = "http://www.omg.org/XMI")
 protected String uuid;


 /**
	 * Gets the value of the extensions property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the extensions property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getExtensions().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Extension }
	 * 
	 * 
	 */
 public List<Extension> getExtensions() {
 if (extensions == null) {
 extensions = new ArrayList<Extension>();
		}
 return this.extensions;
	}


 /**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
 public String getId() {
 return id;
	}


 /**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
 public void setId(String value) {
 this.id = value;
	}


 /**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link QName }
	 * 
	 */
 public QName getType() {
 return type;
	}


 /**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link QName }
	 * 
	 */
 public void setType(QName value) {
 this.type = value;
	}


 /**
	 * Gets the value of the version property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
 public String getVersion() {
 if (version == null) {
 return "2.0";
		} else {
 return version;
		}
	}


 /**
	 * Sets the value of the version property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
 public void setVersion(String value) {
 this.version = value;
	}


 /**
	 * Gets the value of the href property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
 public String getHref() {
 return href;
	}


 /**
	 * Sets the value of the href property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
 public void setHref(String value) {
 this.href = value;
	}


 /**
	 * Gets the value of the idref property.
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
 public Object getIdref() {
 return idref;
	}


 /**
	 * Sets the value of the idref property.
	 * 
	 * @param value
	 *            allowed object is {@link Object }
	 * 
	 */
 public void setIdref(Object value) {
 this.idref = value;
	}


 /**
	 * Gets the value of the label property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
 public String getLabel() {
 return label;
	}


 /**
	 * Sets the value of the label property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
 public void setLabel(String value) {
 this.label = value;
	}


 /**
	 * Gets the value of the uuid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
 public String getUuid() {
 return uuid;
	}


 /**
	 * Sets the value of the uuid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
 public void setUuid(String value) {
 this.uuid = value;
	}


}