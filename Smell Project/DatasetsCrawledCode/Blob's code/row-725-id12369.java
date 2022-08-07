public class ModuleOptionsReferenceDoc {


 /**
	 * Matches "//^<type>.<name>" exactly.
	 */
 private static final Pattern FENCE_START_REGEX = Pattern.compile("^//\\^([^.]+)\\.([^.]+)$");


 private ModuleRegistry moduleRegistry = new ResourceModuleRegistry("file:./modules");


 private ModuleOptionsMetadataResolver moduleOptionsMetadataResolver = new DefaultModuleOptionsMetadataResolver();


 private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();


 public static void main(String... paths) throws IOException {
 ModuleOptionsReferenceDoc runner = new ModuleOptionsReferenceDoc();
 for (String path : paths) {
 runner.updateSingleFile(path);
		}
	}


 private void updateSingleFile(String path) throws IOException {
 File originalFile = new File(path);
 Assert.isTrue(originalFile.exists() && !originalFile.isDirectory(),
 String.format("'%s' does not exist or points to a directory", originalFile.getAbsolutePath()));


 File backup = new File(originalFile.getAbsolutePath() + ".backup");
 originalFile.renameTo(backup);
 BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(backup), "UTF-8"));


 PrintStream out = new PrintStream(new FileOutputStream(originalFile), false, "UTF-8");


 ModuleType type = null;
 String name = null;
 int openingLineNumber = 0;
 int ln = 1;
 for (String line = reader.readLine(); line != null; line = reader.readLine(), ln++) {
 Matcher startMatcher = FENCE_START_REGEX.matcher(line);
 if (startMatcher.matches()) {
 checkPreviousTagHasBeenClosed(originalFile, backup, out, type, name, openingLineNumber);
 type = ModuleType.valueOf(startMatcher.group(1));
 name = startMatcher.group(2);
 openingLineNumber = ln;
 out.println(line);
			}
 else if (type != null && line.equals(String.format("//$%s.%s", type, name))) {
 generateWarning(out, name, type);
 generateAsciidoc(out, name, type);
 type = null;
 name = null;
 out.println(line);
			}
 else if (type == null) {
 out.println(line);
			}
		}
 checkPreviousTagHasBeenClosed(originalFile, backup, out, type, name, openingLineNumber);


 out.close();
 reader.close();


 backup.delete();


	}


 private void checkPreviousTagHasBeenClosed(File originalFile, File backup, PrintStream out, ModuleType type,
 String name, int openingLineNumber) {
 if (type != null) {
 out.close();
 originalFile.delete();
 backup.renameTo(originalFile);
 throw new IllegalStateException(String.format(
 "In %s, found '//^%s.%s' @line %d with no matching '//$%2$s.%3$s'",
 originalFile.getAbsolutePath(), type, name, openingLineNumber));
		}
	}


 private void generateWarning(PrintStream out, String name, ModuleType type) {
 out.format("// DO NOT MODIFY THE LINES BELOW UNTIL THE CLOSING '//$%s.%s' TAG%n", type, name);
 out.format("// THIS SNIPPET HAS BEEN GENERATED BY %s AND MANUAL EDITS WILL BE LOST%n",
 ModuleOptionsReferenceDoc.class.getSimpleName());
	}


 private void generateAsciidoc(PrintStream out, String name, ModuleType type)
 throws IOException {
 ModuleDefinition def = moduleRegistry.findDefinition(name, type);
 ModuleOptionsMetadata moduleOptionsMetadata = moduleOptionsMetadataResolver.resolve(def);


 Resource moduleLoc = resourcePatternResolver.getResource(((SimpleModuleDefinition) def).getLocation());
 ClassLoader moduleClassLoader = ModuleUtils.createModuleDiscoveryClassLoader(moduleLoc, ModuleOptionsReferenceDoc.class.getClassLoader());
 if (!moduleOptionsMetadata.iterator().hasNext()) {
 out.format("The **%s** %s has no particular option (in addition to options shared by all modules)%n%n",
 pt(def.getName()), pt(def.getType()));
 return;
		}


 out.format("The **%s** %s has the following options:%n%n", pt(def.getName()), pt(def.getType()));
 List<ModuleOption> options = new ArrayList<ModuleOption>();
 for (ModuleOption mo : moduleOptionsMetadata) {
 options.add(mo);
		}
 Collections.sort(options, new Comparator<ModuleOption>() {


 @Override
 public int compare(ModuleOption o1, ModuleOption o2) {
 return o1.getName().compareTo(o2.getName());
			}
		});


 for (ModuleOption mo : options) {
 String prettyDefault = prettifyDefaultValue(mo);
 String maybeEnumHint = generateEnumValues(mo, moduleClassLoader);
 out.format("%s:: %s *(%s, %s%s)*%n", pt(mo.getName()), pt(mo.getDescription()),
 pt(shortClassName(mo.getType())),
 prettyDefault, maybeEnumHint);
		}
	}


 private String shortClassName(String fqName) {
 int lastDot = fqName.lastIndexOf('.');
 return lastDot >= 0 ? fqName.substring(lastDot + 1) : fqName;
	}




 /**
	 * When the type of an option is an enum, document all possible values
	 */
 private String generateEnumValues(ModuleOption mo, ClassLoader moduleClassLoader) {
 // Attempt to convert back to com.acme.Foo$Bar form
 String canonical = mo.getType();
 String system = canonical.replaceAll("(.*\\p{Upper}[^\\.]*)\\.(\\p{Upper}.*)", "$1\\$$2");
 Class<?> clazz = null;
 try {
 clazz = Class.forName(system, false, moduleClassLoader);
		}
 catch (ClassNotFoundException e) {
 return "";
		}
 if (Enum.class.isAssignableFrom(clazz)) {
 String values = StringUtils.arrayToCommaDelimitedString(clazz.getEnumConstants());
 return String.format(", possible values: `%s`", values);
		}
 else
 return "";
	}


 private String prettifyDefaultValue(ModuleOption mo) {
 if (mo.getDefaultValue() == null) {
 return "no default";
		}
 String result = stringify(mo.getDefaultValue());
 result = result.replace(ModulePlaceholders.XD_STREAM_NAME, "<stream name>");
 result = result.replace(ModulePlaceholders.XD_JOB_NAME, "<job name>");
 return "default: `" + result + "`";
	}


 private String stringify(Object element) {
 Class<?> clazz = element.getClass();
 if (clazz == byte[].class) {
 return Arrays.toString((byte[]) element);
		}
 else if (clazz == short[].class) {
 return Arrays.toString((short[]) element);
		}
 else if (clazz == int[].class) {
 return Arrays.toString((int[]) element);
		}
 else if (clazz == long[].class) {
 return Arrays.toString((long[]) element);
		}
 else if (clazz == char[].class) {
 return Arrays.toString((char[]) element);
		}
 else if (clazz == float[].class) {
 return Arrays.toString((float[]) element);
		}
 else if (clazz == double[].class) {
 return Arrays.toString((double[]) element);
		}
 else if (clazz == boolean[].class) {
 return Arrays.toString((boolean[]) element);
		}
 else if (element instanceof Object[]) {
 return Arrays.deepToString((Object[]) element);
		}
 else {
 return element.toString();
		}
	}


 /**
	 * Return an asciidoc passthrough version of some text, in case the original text contains characters
	 * that would be (mis)interpreted by asciidoc.
	 */
 private String pt(Object original) {
 return "$$" + original + "$$";
	}




}