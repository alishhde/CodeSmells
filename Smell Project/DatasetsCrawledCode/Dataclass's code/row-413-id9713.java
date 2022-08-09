public class ClasspathUriUtil {


 public static final String CLASSPATH_SCHEME = "classpath";


 public static boolean isClasspathUri(URI uri) {
 if (uri == null)
 return false;
 String scheme = uri.scheme();
 return CLASSPATH_SCHEME.equals(scheme);
    }


}