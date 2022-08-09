public class LiteralKey {


 private Object value;


 private String type;


 private String lang;


 public LiteralKey(Object value, String type, String lang) {
 this.value = value;
 this.type = type != null ? type.intern() : null;
 this.lang = lang != null ? lang.intern() : null;
    }


 public String getLang() {
 return lang;
    }


 public String getType() {
 return type;
    }


 public Object getValue() {
 return value;
    }


 @Override
 public boolean equals(Object o) {
 if (this == o) return true;
 if (o == null || getClass() != o.getClass()) return false;


 LiteralKey that = (LiteralKey) o;


 if (lang != null ? !lang.equals(that.lang) : that.lang != null) return false;
 if (type != null ? !type.equals(that.type) : that.type != null) return false;
 return value.equals(that.value);


    }


 @Override
 public int hashCode() {
 int result = value.hashCode();
 result = 31 * result + (type != null ? type.hashCode() : 0);
 result = 31 * result + (lang != null ? lang.hashCode() : 0);
 return result;
    }
}