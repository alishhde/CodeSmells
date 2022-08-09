public class Status {
 public String getAction() {
 return action;
    }


 public Result getResult() {
 return result;
    }


 public String getDetails() {
 return details;
    }


 private String action;
 private Result result;
 private String details;


 public Status(String action, Result result, String details) {
 this.action = action;
 this.result = result;
 this.details = details;
    }
 public static enum Result {
 SUCCESSFUL,
 FAILED,
    }


 @Override
 public String toString() {
 return String.format("%s\t%s\t%s", action, result, details);
    }
}