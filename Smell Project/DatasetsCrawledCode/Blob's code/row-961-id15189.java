public class IteratorTestCaseFinder {
 private static final Logger log = LoggerFactory.getLogger(IteratorTestCaseFinder.class);


 /**
   * Instantiates all test cases provided.
   *
   * @return A list of {@link IteratorTestCase}s.
   */
 public static List<IteratorTestCase> findAllTestCases() {
 log.info("Searching {}", IteratorTestCase.class.getPackage().getName());
 ClassPath cp;
 try {
 cp = ClassPath.from(IteratorTestCaseFinder.class.getClassLoader());
    } catch (IOException e) {
 throw new RuntimeException(e);
    }
 ImmutableSet<ClassInfo> classes = cp
        .getTopLevelClasses(IteratorTestCase.class.getPackage().getName());


 final List<IteratorTestCase> testCases = new ArrayList<>();
 // final Set<Class<? extends IteratorTestCase>> classes =
 // reflections.getSubTypesOf(IteratorTestCase.class);
 for (ClassInfo classInfo : classes) {
 Class<?> clz;
 try {
 clz = Class.forName(classInfo.getName());
      } catch (Exception e) {
 log.warn("Could not get class for " + classInfo.getName(), e);
 continue;
      }


 if (clz.isInterface() || Modifier.isAbstract(clz.getModifiers())
          || !IteratorTestCase.class.isAssignableFrom(clz)) {
 log.debug("Skipping " + clz);
 continue;
      }


 try {
 testCases.add((IteratorTestCase) clz.newInstance());
      } catch (IllegalAccessException | InstantiationException e) {
 log.warn("Could not instantiate {}", clz, e);
      }
    }


 return testCases;
  }
}