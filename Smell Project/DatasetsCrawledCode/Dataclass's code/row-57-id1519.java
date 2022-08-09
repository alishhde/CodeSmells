public class Status {


 private StatusCode code;
 private String message;
 private String detail;


 public StatusCode getCode() {
 return code;
	}


 public Status setCode(StatusCode code) {
 this.code = code;
 return this;
	}


 public String getMessage() {
 return message;
	}


 public Status setMessage(String message) {
 this.message = message;
 return this;
	}


 public String getDetail() {
 return detail;
	}


 public Status setDetail(String detail) {
 this.detail = detail;
 return this;
	}
}