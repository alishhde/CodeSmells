 private static Class<?> loadClass2(String className, Class<?> callingClass)
 throws ClassNotFoundException {
 try {
 return Class.forName(className);
        } catch (ClassNotFoundException ex) {
 try {
 if (ClassLoaderUtils.class.getClassLoader() != null) {
 return ClassLoaderUtils.class.getClassLoader().loadClass(className);
                }
            } catch (ClassNotFoundException exc) {
 if (callingClass != null && callingClass.getClassLoader() != null) {
 return callingClass.getClassLoader().loadClass(className);
                }
            }
 LOG.debug(ex.getMessage(), ex);
 throw ex;
        }
    }