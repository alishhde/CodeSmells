public class SageRuntimeException extends RuntimeException implements SageExceptable
{
 protected final int kind;




 public SageRuntimeException()
  {
 kind = UNKNOWN;
  }


 public SageRuntimeException(String message, int kind)
  {
 super(message);


 this.kind = kind;
  }


 public SageRuntimeException(Throwable cause, int kind)
  {
 super(cause);


 this.kind = kind;
  }


 public SageRuntimeException(String message, Throwable cause, int kind)
  {
 super(message, cause);


 this.kind = kind;
  }


 public int getKind()
  {
 return (kind);
  }


 public boolean isKind(int kind)
  {
 return ((this.kind & kind) != 0);
  }


 public String getMessage()
  {
 return ("kind=" + kind + "; " + super.getMessage());
  }
}