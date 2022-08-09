public class ErrorDataException extends Exception {


 /** serialVersionUID. */
 private static final long serialVersionUID = -9052741930614009382L;
 
 /** The rpc data package. */
 private RpcDataPackage rpcDataPackage;
 
 /** The error code. */
 private int errorCode;
 
 /**
     * Gets the error code.
     *
     * @return the error code
     */
 public int getErrorCode() {
 return errorCode;
    }


 /**
     * Sets the error code.
     *
     * @param errorCode the new error code
     */
 public void setErrorCode(int errorCode) {
 this.errorCode = errorCode;
    }


 /**
     * Gets the rpc data package.
     *
     * @return the rpc data package
     */
 public RpcDataPackage getRpcDataPackage() {
 return rpcDataPackage;
    }


 /**
     * Sets the rpc data package.
     *
     * @param rpcDataPackage the new rpc data package
     */
 public void setRpcDataPackage(RpcDataPackage rpcDataPackage) {
 this.rpcDataPackage = rpcDataPackage;
    }


 /**
     * Instantiates a new error data exception.
     */
 public ErrorDataException() {
 super();
    }


 /**
     * Instantiates a new error data exception.
     *
     * @param message the message
     * @param cause the cause
     */
 public ErrorDataException(String message, Throwable cause) {
 super(message, cause);
    }
 
 /**
     * Instantiates a new error data exception.
     *
     * @param message the message
     * @param cause the cause
     * @param errorCode the error code
     */
 public ErrorDataException(String message, Throwable cause, int errorCode) {
 super(message, cause);
 this.errorCode = errorCode;
    }


 /**
     * Instantiates a new error data exception.
     *
     * @param message the message
     */
 public ErrorDataException(String message) {
 super(message);
    }


 /**
     * Instantiates a new error data exception.
     *
     * @param message the message
     * @param errorCode the error code
     */
 public ErrorDataException(String message, int errorCode) {
 super(message);
 this.errorCode = errorCode;
    }
 
 /**
     * Instantiates a new error data exception.
     *
     * @param cause the cause
     */
 public ErrorDataException(Throwable cause) {
 super(cause);
    }
 
 /**
     * Instantiates a new error data exception.
     *
     * @param cause the cause
     * @param errorCode the error code
     */
 public ErrorDataException(Throwable cause, int errorCode) {
 super(cause);
 this.errorCode = errorCode;
    }


 
}