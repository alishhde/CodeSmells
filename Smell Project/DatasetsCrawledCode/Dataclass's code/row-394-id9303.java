 @Immutable
 public static final class Result {


 /** Outcome categories for individual DN lines. */
 public enum Outcome { OK, WARNING, ERROR }


 private final int code;
 private final String description;
 private final Outcome outcome;


 private Result(int code, String description) {
 this.code = code;
 this.description = description;
 if (2000 <= code && code <= 2099) {
 this.outcome = Outcome.OK;
      } else if (3500 <= code && code <= 3699) {
 this.outcome = Outcome.WARNING;
      } else if (4500 <= code && code <= 4699) {
 this.outcome = Outcome.ERROR;
      } else {
 throw new IllegalArgumentException("Invalid DN result code: " + code);
      }
    }


 public int getCode() {
 return code;
    }


 public String getDescription() {
 return description;
    }


 public Outcome getOutcome() {
 return outcome;
    }


 @Override
 public String toString() {
 return toStringHelper(this)
          .add("code", code)
          .add("outcome", outcome)
          .add("description", description)
          .toString();
    }
  }