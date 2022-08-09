@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "faces-config-propertyType", propOrder = {
 "descriptions",
 "displayNames",
 "icon",
 "propertyName",
 "propertyClass",
 "defaultValue",
 "suggestedValue",
 "propertyExtension"
})
public class FacesProperty {


 @XmlTransient
 protected TextMap description = new TextMap();
 @XmlTransient
 protected TextMap displayName = new TextMap();
 @XmlElement(name = "icon", required = true)
 protected LocalCollection<Icon> icon = new LocalCollection<Icon>();
 @XmlElement(name = "property-name", required = true)
 protected java.lang.String propertyName;
 @XmlElement(name = "property-class", required = true)
 protected java.lang.String propertyClass;
 @XmlElement(name = "default-value")
 protected java.lang.String defaultValue;
 @XmlElement(name = "suggested-value")
 protected java.lang.String suggestedValue;
 @XmlElement(name = "property-extension")
 protected List<FacesPropertyExtension> propertyExtension;
 @XmlAttribute
 @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
 @XmlID
 @XmlSchemaType(name = "ID")
 protected java.lang.String id;


 @XmlElement(name = "description", required = true)
 public Text[] getDescriptions() {
 return description.toArray();
    }


 public void setDescriptions(Text[] text) {
 description.set(text);
    }


 public String getDescription() {
 return description.get();
    }


 @XmlElement(name = "display-name", required = true)
 public Text[] getDisplayNames() {
 return displayName.toArray();
    }


 public void setDisplayNames(Text[] text) {
 displayName.set(text);
    }


 public String getDisplayName() {
 return displayName.get();
    }


 public Collection<Icon> getIcons() {
 if (icon == null) {
 icon = new LocalCollection<Icon>();
        }
 return icon;
    }


 public Map<String,Icon> getIconMap() {
 if (icon == null) {
 icon = new LocalCollection<Icon>();
        }
 return icon.toMap();
    }


 public Icon getIcon() {
 return icon.getLocal();
    }


 /**
     * Gets the value of the propertyName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
 public java.lang.String getPropertyName() {
 return propertyName;
    }


 /**
     * Sets the value of the propertyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
 public void setPropertyName(java.lang.String value) {
 this.propertyName = value;
    }


 /**
     * Gets the value of the propertyClass property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
 public java.lang.String getPropertyClass() {
 return propertyClass;
    }


 /**
     * Sets the value of the propertyClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
 public void setPropertyClass(java.lang.String value) {
 this.propertyClass = value;
    }


 /**
     * Gets the value of the defaultValue property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
 public java.lang.String getDefaultValue() {
 return defaultValue;
    }


 /**
     * Sets the value of the defaultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
 public void setDefaultValue(java.lang.String value) {
 this.defaultValue = value;
    }


 /**
     * Gets the value of the suggestedValue property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
 public java.lang.String getSuggestedValue() {
 return suggestedValue;
    }


 /**
     * Sets the value of the suggestedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
 public void setSuggestedValue(java.lang.String value) {
 this.suggestedValue = value;
    }


 /**
     * Gets the value of the propertyExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propertyExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropertyExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FacesPropertyExtension }
     * 
     * 
     */
 public List<FacesPropertyExtension> getPropertyExtension() {
 if (propertyExtension == null) {
 propertyExtension = new ArrayList<FacesPropertyExtension>();
        }
 return this.propertyExtension;
    }


 /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String }
     *     
     */
 public java.lang.String getId() {
 return id;
    }


 /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String }
     *     
     */
 public void setId(java.lang.String value) {
 this.id = value;
    }


}