 static Class findProviderClass(String className, ClassLoader cl,
 boolean doFallback)
 throws ClassNotFoundException, ConfigurationError
    {   
 //throw security exception if the calling thread is not allowed to access the
 //class. Restrict the access to the package classes as specified in java.security policy.
 SecurityManager security = System.getSecurityManager();
 try{
 if (security != null){
 final int lastDot = className.lastIndexOf('.');
 String packageName = className;
 if (lastDot != -1) packageName = className.substring(0, lastDot);
 security.checkPackageAccess(packageName);
                 }   
        }catch(SecurityException e){
 throw e;
        }
 
 Class providerClass;
 if (cl == null) {
 // XXX Use the bootstrap ClassLoader.  There is no way to
 // load a class using the bootstrap ClassLoader that works
 // in both JDK 1.1 and Java 2.  However, this should still
 // work b/c the following should be true:
 //
 // (cl == null) iff current ClassLoader == null
 //
 // Thus Class.forName(String) will use the current
 // ClassLoader which will be the bootstrap ClassLoader.
 providerClass = Class.forName(className);
        } else {
 try {
 providerClass = cl.loadClass(className);
            } catch (ClassNotFoundException x) {
 if (doFallback) {
 // Fall back to current classloader
 ClassLoader current = ObjectFactory.class.getClassLoader();
 if (current == null) {
 providerClass = Class.forName(className);
                    } else if (cl != current) {
 cl = current;
 providerClass = cl.loadClass(className);
                    } else {
 throw x;
                    }
                } else {
 throw x;
                }
            }
        }


 return providerClass;
    }