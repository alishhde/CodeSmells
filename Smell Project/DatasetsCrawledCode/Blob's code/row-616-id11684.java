public class Problems {


 /** Represents compiler fatal errors. */
 public enum FatalError {
 FILE_NOT_FOUND("File '%s' not found.", 1),
 UNKNOWN_INPUT_TYPE("Cannot recognize input type for file '%s'.", 1),
 OUTPUT_LOCATION("Output location '%s' must be a directory or .zip file.", 1),
 CANNOT_EXTRACT_ZIP("Cannot extract zip '%s'.", 1),
 CANNOT_CREATE_ZIP("Cannot create zip '%s': %s.", 2),
 CANNOT_CLOSE_ZIP("Cannot close zip: %s.", 1),
 CANNOT_CREATE_TEMP_DIR("Cannot create temporary directory: %s.", 1),
 CANNOT_OPEN_FILE("Cannot open file: %s.", 1),
 CANNOT_WRITE_FILE("Cannot write file: %s.", 1),
 CANNOT_COPY_FILE("Cannot copy file: %s.", 1),
 PACKAGE_INFO_PARSE("Resource '%s' was found but it failed to parse.", 1),
 CLASS_PATH_URL("Class path entry '%s' is not a valid url.", 1),
 GWT_INCOMPATIBLE_FOUND_IN_COMPILE(
 "@GwtIncompatible annotations found in %s "
            + "Please run this library through the @GwtIncompatible stripper tool.",
 1),
    ;


 // used for customized message.
 private final String message;
 // number of arguments the message takes.
 private final int numberOfArguments;


 FatalError(String message, int numberOfArguments) {
 this.message = message;
 this.numberOfArguments = numberOfArguments;
    }


 public String getMessage() {
 return message;
    }


 private int getNumberOfArguments() {
 return numberOfArguments;
    }
  }


 /** Represents the severity of the problem */
 public enum Severity {
 ERROR("Error"),
 WARNING("Warning"),
 INFO("Info");


 Severity(String messagePrefix) {
 this.messagePrefix = messagePrefix;
    }


 private final String messagePrefix;


 public String getMessagePrefix() {
 return messagePrefix;
    }
  }


 private final Multimap<Severity, String> problemsBySeverity = LinkedHashMultimap.create();


 public void fatal(FatalError fatalError, Object... args) {
 checkArgument(fatalError.getNumberOfArguments() == args.length);
 problemsBySeverity.put(
 Severity.ERROR, "Error: " + String.format(fatalError.getMessage(), args));
 abort();
  }


 public void error(SourcePosition sourcePosition, String detailMessage, Object... args) {
 problem(Severity.ERROR, sourcePosition, detailMessage, args);
  }


 public void error(int lineNumber, String filePath, String detailMessage, Object... args) {
 problem(Severity.ERROR, lineNumber, filePath, detailMessage, args);
  }


 public void warning(SourcePosition sourcePosition, String detailMessage, Object... args) {
 problem(Severity.WARNING, sourcePosition, detailMessage, args);
  }


 private void problem(
 Severity severity, SourcePosition sourcePosition, String detailMessage, Object... args) {
 problem(
 severity,
 // SourcePosition lines are 0 based.
 sourcePosition.getStartFilePosition().getLine() + 1,
 sourcePosition.getFilePath(),
 detailMessage,
 args);
  }


 private void problem(
 Severity severity, int lineNumber, String filePath, String detailMessage, Object... args) {
 String message = args.length == 0 ? detailMessage : String.format(detailMessage, args);
 problemsBySeverity.put(
 severity,
 String.format(
 "%s:%s:%s: %s",
 severity.getMessagePrefix(),
 filePath.substring(filePath.lastIndexOf('/') + 1),
 lineNumber,
 message));
  }


 public void error(String detailMessage, Object... args) {
 problemsBySeverity.put(Severity.ERROR, "Error: " + String.format(detailMessage, args));
  }


 public void warning(String detailMessage, Object... args) {
 problemsBySeverity.put(Severity.WARNING, String.format(detailMessage, args));
  }


 public void info(String detailMessage, Object... args) {
 problemsBySeverity.put(Severity.INFO, String.format(detailMessage, args));
  }


 /** Prints all problems to provided output and returns the exit code. */
 public int reportAndGetExitCode(PrintStream output) {
 return reportAndGetExitCode(new PrintWriter(output, true));
  }


 /** Prints all problems to provided output and returns the exit code. */
 public int reportAndGetExitCode(PrintWriter output) {
 for (Map.Entry<Severity, String> severityMessagePair : problemsBySeverity.entries()) {
 output.println(severityMessagePair.getValue());
    }
 if (hasErrors() || hasWarnings()) {
 output.printf(
 "%d error(s), %d warning(s).\n",
 problemsBySeverity.get(Severity.ERROR).size(),
 problemsBySeverity.get(Severity.WARNING).size());
    }


 return hasErrors() ? 1 : 0;
  }


 public boolean hasWarnings() {
 return problemsBySeverity.containsKey(Severity.WARNING);
  }


 public boolean hasErrors() {
 return problemsBySeverity.containsKey(Severity.ERROR);
  }


 public boolean hasProblems() {
 return !problemsBySeverity.isEmpty();
  }


 public void abortIfHasErrors() {
 if (hasErrors()) {
 abort();
    }
  }


 public void abort() {
 throw new Exit(this);
  }


 public List<String> getErrors() {
 return getMessages(Severity.ERROR);
  }


 public List<String> getWarnings() {
 return getMessages(Severity.WARNING);
  }


 public List<String> getInfoMessages() {
 return getMessages(Severity.INFO);
  }


 public List<String> getMessages() {
 return getMessages(EnumSet.allOf(Severity.class));
  }


 private List<String> getMessages(Severity severity) {
 return getMessages(Collections.singleton(severity));
  }


 private List<String> getMessages(Collection<Severity> severities) {
 return problemsBySeverity
        .entries()
        .stream()
        .filter(e -> severities.contains(e.getKey()))
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());
  }


 /**
   * Exit is thrown to signal that a System.exit should be performed at a higher level.
   *
   * <p>Note: It should never be caught except on the top level.
   */
 public static class Exit extends java.lang.Error {
 private final Problems problems;


 private Exit(Problems problems) {
 this.problems = problems;
    }


 public Problems getProblems() {
 return problems;
    }
  }
}