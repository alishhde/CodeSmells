public class FileSinkOptionsMetadata implements ProfileNamesProvider {


 private static final String USE_SPEL_PROFILE = "use-expression";


 private static final String USE_LITERAL_STRING_PROFILE = "use-string";


 private boolean binary = false;


 private String charset = "UTF-8";


 private String dir = "/tmp/xd/output/";


 private String name = XD_STREAM_NAME;


 private String suffix = "out";


 private Mode mode = APPEND;


 private String nameExpression;


 private String dirExpression;


 @NotNull
 public Mode getMode() {
 return mode;
	}


 @ModuleOption("what to do if the file already exists")
 public void setMode(Mode mode) {
 this.mode = mode;
	}


 /**
	 * Return dot + suffix if suffix is set, or the empty string otherwise.
	 */
 public String getExtensionWithDot() {
 return StringUtils.hasText(suffix) ? "." + suffix.trim() : "";
	}




 @ModuleOption("filename extension to use")
 public void setSuffix(String suffix) {
 this.suffix = suffix;
	}


 public String getName() {
 return name;
	}


 @ModuleOption("filename pattern to use")
 public void setName(String name) {
 this.name = name;
	}


 @NotBlank
 public String getDir() {
 return dir;
	}


 @ModuleOption("the directory in which files will be created")
 public void setDir(String dir) {
 this.dir = dir;
	}


 public boolean isBinary() {
 return binary;
	}


 @ModuleOption("if false, will append a newline character at the end of each line")
 public void setBinary(boolean binary) {
 this.binary = binary;
	}


 @ModuleOption("the charset to use when writing a String payload")
 public void setCharset(String charset) {
 this.charset = charset;
	}


 @NotBlank
 public String getCharset() {
 return charset;
	}




 public String getNameExpression() {
 return nameExpression;
	}


 @ModuleOption("spring expression used to define filename")
 public void setNameExpression(String nameExpression) {
 this.nameExpression = nameExpression;
	}


 public String getDirExpression() {
 return dirExpression;
	}


 @ModuleOption("spring expression used to define directory name")
 public void setDirExpression(String dirExpression) {
 this.dirExpression = dirExpression;
	}


 public static enum Mode {
 APPEND, REPLACE, FAIL, IGNORE;
	}


 @Override
 public String[] profilesToActivate() {
 return (nameExpression != null || dirExpression != null) ? new String[] { USE_SPEL_PROFILE }
				: new String[] { USE_LITERAL_STRING_PROFILE };
	}
}