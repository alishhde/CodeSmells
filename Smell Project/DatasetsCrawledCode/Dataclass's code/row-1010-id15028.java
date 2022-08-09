public class Signal {
 public enum Type { LEAVE_LOOP, LEAVE_ROUTINE, LEAVE_PROGRAM, SQLEXCEPTION, NOTFOUND, UNSUPPORTED_OPERATION, USERDEFINED };
 Type type;
 String value = "";
 Exception exception = null;
 
 Signal(Type type, String value) {
 this.type = type;
 this.value = value;
 this.exception = null;
  }
 
 Signal(Type type, String value, Exception exception) {
 this.type = type;
 this.value = value;
 this.exception = exception;
  }
 
 /**
   * Get the signal value (message text)
   */
 public String getValue() {
 return value;
  }
}