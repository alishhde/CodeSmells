 public class Header {


 public String key;
 public String val;


 public Header(String key, String val) {
 this.key = key;
 this.val = val;
      }


 public String getEncodedKey() {
 return encode(key);
      }


 public String getEncodedValue() {
 return encode(val);
      }
   }