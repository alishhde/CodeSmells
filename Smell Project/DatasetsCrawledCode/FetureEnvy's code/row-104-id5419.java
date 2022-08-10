 public static ResumptionAction fromName(String name) {
 if (name == null || name.length() == 0) {
 throw new IllegalArgumentException(
 String.format("Invalid ResumptionAction name: %s",
 name));
    }
 for (int i = 0; i < PRIVATE_VALUES.length; i++) {
 if (name.equals(PRIVATE_VALUES[i].name)) {
 return PRIVATE_VALUES[i];
      }
    }
 throw new IllegalArgumentException(
 String.format("Invalid ResumptionAction name: %s", name));
  }