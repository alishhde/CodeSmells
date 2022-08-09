 @XmlRootElement(name = "header")
 @XmlAccessorType(XmlAccessType.FIELD)
 public static class Header {


 @XmlAttribute
 private String key;


 @XmlAttribute
 private String type;


 @XmlValue
 private String value;


 public String getKey() {
 return key;
        }


 public void setKey(String key) {
 this.key = key;
        }


 public String getType() {
 return type;
        }


 public void setType(String type) {
 this.type = type;
        }


 public String getValue() {
 return value;
        }


 public void setValue(String value) {
 this.value = value;
        }
    }