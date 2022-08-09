@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serialization-registration-type",
 namespace = "http://geode.apache.org/schema/cache",
 propOrder = {"serializers", "instantiators"})
@Experimental
public class SerializationRegistrationType {


 @XmlElement(name = "serializer", namespace = "http://geode.apache.org/schema/cache")
 protected List<Serializer> serializers;
 @XmlElement(name = "instantiator", namespace = "http://geode.apache.org/schema/cache")
 protected List<Instantiator> instantiators;


 /**
   * Gets the value of the serializer property.
   *
   * <p>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the serializer property.
   *
   * <p>
   * For example, to add a new item, do as follows:
   *
   * <pre>
   * getSerializer().add(newItem);
   * </pre>
   *
   *
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link SerializationRegistrationType.Serializer }
   *
   *
   */
 public List<Serializer> getSerializers() {
 if (serializers == null) {
 serializers = new ArrayList<Serializer>();
    }
 return this.serializers;
  }


 /**
   * Gets the value of the instantiator property.
   *
   * <p>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the instantiator property.
   *
   * <p>
   * For example, to add a new item, do as follows:
   *
   * <pre>
   * getInstantiator().add(newItem);
   * </pre>
   *
   *
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link SerializationRegistrationType.Instantiator }
   *
   *
   */
 public List<Instantiator> getInstantiators() {
 if (instantiators == null) {
 instantiators = new ArrayList<Instantiator>();
    }
 return this.instantiators;
  }




 /**
   * <p>
   * Java class for anonymous complex type.
   *
   * <p>
   * The following schema fragment specifies the expected content contained within this class.
   *
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;sequence>
   *         &lt;element name="class-name" type="{http://geode.apache.org/schema/cache}class-name-type"/>
   *       &lt;/sequence>
   *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   *
   *
   */
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name = "", propOrder = {"className"})
 public static class Instantiator {


 @XmlElement(name = "class-name", namespace = "http://geode.apache.org/schema/cache",
 required = true)
 protected String className;
 @XmlAttribute(name = "id", required = true)
 protected String id;


 /**
     * Gets the value of the className property.
     *
     * possible object is
     * {@link String }
     *
     */
 public String getClassName() {
 return className;
    }


 /**
     * Sets the value of the className property.
     *
     * allowed object is
     * {@link String }
     *
     */
 public void setClassName(String value) {
 this.className = value;
    }


 /**
     * Gets the value of the id property.
     *
     * possible object is
     * {@link String }
     *
     */
 public String getId() {
 return id;
    }


 /**
     * Sets the value of the id property.
     *
     * allowed object is
     * {@link String }
     *
     */
 public void setId(String value) {
 this.id = value;
    }


  }




 /**
   * <p>
   * Java class for anonymous complex type.
   *
   * <p>
   * The following schema fragment specifies the expected content contained within this class.
   *
   * <pre>
   * &lt;complexType>
   *   &lt;complexContent>
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
   *       &lt;sequence>
   *         &lt;element name="class-name" type="{http://geode.apache.org/schema/cache}class-name-type"/>
   *       &lt;/sequence>
   *     &lt;/restriction>
   *   &lt;/complexContent>
   * &lt;/complexType>
   * </pre>
   *
   *
   */
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name = "", propOrder = {"className"})
 public static class Serializer {


 @XmlElement(name = "class-name", namespace = "http://geode.apache.org/schema/cache",
 required = true)
 protected String className;


 /**
     * Gets the value of the className property.
     *
     * possible object is
     * {@link String }
     *
     */
 public String getClassName() {
 return className;
    }


 /**
     * Sets the value of the className property.
     *
     * allowed object is
     * {@link String }
     *
     */
 public void setClassName(String value) {
 this.className = value;
    }


  }


}