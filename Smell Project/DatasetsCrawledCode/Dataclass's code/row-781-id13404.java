@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
 "content"
})
@XmlRootElement(name = "cache-policy-conf-other")
public class CachePolicyConfOther {


 @XmlMixed
 @XmlAnyElement
 protected List<Object> content;


 /**
     * Gets the value of the content property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * {@link String }
     */
 public List<Object> getContent() {
 if (content == null) {
 content = new ArrayList<Object>();
        }
 return this.content;
    }


}