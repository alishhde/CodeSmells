 static ClassLoader findClassLoader()
 throws ConfigurationError
    { 
 // Figure out which ClassLoader to use for loading the provider
 // class.  If there is a Context ClassLoader then use it.
 ClassLoader context = SecuritySupport.getContextClassLoader();
 ClassLoader system = SecuritySupport.getSystemClassLoader();


 ClassLoader chain = system;
 while (true) {
 if (context == chain) {
 // Assert: we are on JDK 1.1 or we have no Context ClassLoader
 // or any Context ClassLoader in chain of system classloader
 // (including extension ClassLoader) so extend to widest
 // ClassLoader (always look in system ClassLoader if Xalan
 // is in boot/extension/system classpath and in current
 // ClassLoader otherwise); normal classloaders delegate
 // back to system ClassLoader first so this widening doesn't
 // change the fact that context ClassLoader will be consulted
 ClassLoader current = ObjectFactory.class.getClassLoader();


 chain = system;
 while (true) {
 if (current == chain) {
 // Assert: Current ClassLoader in chain of
 // boot/extension/system ClassLoaders
 return system;
                    }
 if (chain == null) {
 break;
                    }
 chain = SecuritySupport.getParentClassLoader(chain);
                }


 // Assert: Current ClassLoader not in chain of
 // boot/extension/system ClassLoaders
 return current;
            }


 if (chain == null) {
 // boot ClassLoader reached
 break;
            }


 // Check for any extension ClassLoaders in chain up to
 // boot ClassLoader
 chain = SecuritySupport.getParentClassLoader(chain);
        };


 // Assert: Context ClassLoader not in chain of
 // boot/extension/system ClassLoaders
 return context;
    } // findClassLoader():ClassLoader