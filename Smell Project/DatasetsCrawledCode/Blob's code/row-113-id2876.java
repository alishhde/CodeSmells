class ManualImplementationLoaderService<T>
{
 private static final String SERVICE_CONFIG = "META-INF/services/";
 private static final String FILE_ENCODING = "UTF-8";


 protected List<Class<?>> foundServiceClasses = new ArrayList<>();
 private Class<T> serviceType;
 private ClassLoader currentClassLoader;


 ManualImplementationLoaderService(Class<T> serviceType, ClassLoader currentClassLoader)
    {
 this.serviceType = serviceType;
 this.currentClassLoader = currentClassLoader;
    }


 List<T> loadServiceImplementations()
    {
 List<Class<?>> result = resolveServiceImplementations();


 if (result == null)
        {
 return Collections.emptyList();
        }


 List<T> foundServices = new ArrayList<>();


 for (Class<?> serviceClass : result)
        {
 foundServices.add(createInstance(serviceClass));
        }


 return foundServices;
    }


 private List<Class<?>> resolveServiceImplementations()
    {
 for (URL configFile : getConfigFileList())
        {
 loadConfiguredServices(configFile);
        }


 return foundServiceClasses;
    }


 private List<URL> getConfigFileList()
    {
 List<URL> serviceFiles = new ArrayList<>();


 try
        {
 Enumeration<URL> serviceFileEnumerator = currentClassLoader.getResources(getConfigFileLocation());


 while (serviceFileEnumerator.hasMoreElements())
            {
 serviceFiles.add(serviceFileEnumerator.nextElement());
            }
        }
 catch (Exception e)
        {
 throw new IllegalStateException(
 "Failed to load " + serviceType.getName() + " configured in " + getConfigFileLocation(), e);
        }
 return serviceFiles;
    }


 private String getConfigFileLocation()
    {
 return SERVICE_CONFIG + serviceType.getName();
    }


 private void loadConfiguredServices(URL serviceFile)
    {
 InputStream inputStream = null;


 try
        {
 String serviceClassName;
 inputStream = serviceFile.openStream();
 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, FILE_ENCODING));


 while ((serviceClassName = bufferedReader.readLine()) != null)
            {
 serviceClassName = extractConfiguredServiceClassName(serviceClassName);
 if (!"".equals(serviceClassName))
                {
 loadService(serviceClassName);
                }
            }
        }
 catch (Exception e)
        {
 throw new IllegalStateException("Failed to process service-config: " + serviceFile, e);
        }
 finally
        {
 if (inputStream != null)
            {
 try
                {
 inputStream.close();
                }
 catch (Exception e)
                {
 throw new IllegalStateException("Failed to close " + serviceFile, e);
                }
            }
        }
    }


 private String extractConfiguredServiceClassName(String currentConfigLine)
    {
 int startOfComment = currentConfigLine.indexOf('#');


 if (startOfComment > -1)
        {
 currentConfigLine = currentConfigLine.substring(0, startOfComment);
        }
 return currentConfigLine.trim();
    }


 private void loadService(String serviceClassName)
    {
 Class<T> serviceClass = (Class<T>) loadClass(serviceClassName);


 if (serviceClass != null && !foundServiceClasses.contains(serviceClass))
        {
 foundServiceClasses.add(serviceClass);
        }
 else if (serviceClass == null)
        {
 throw new IllegalStateException(serviceClassName + " couldn't be loaded. " +
 "Please ensure that this class is in the classpath or remove the entry from "
                    + getConfigFileLocation() + ".");
        }
    }


 private Class<? extends T> loadClass(String serviceClassName)
    {
 Class<?> targetClass = ClassUtil.getClassFromName(serviceClassName);


 if (targetClass == null)
        {
 targetClass = loadClassForName(serviceClassName, currentClassLoader);


 if (targetClass == null)
            {
 return null;
            }
        }


 return targetClass.asSubclass(serviceType);
    }


 private static Class<?> loadClassForName(String serviceClassName, ClassLoader classLoader)
    {
 if (classLoader == null)
        {
 return null;
        }


 try
        {
 return classLoader.loadClass(serviceClassName);
        }
 catch (Exception e)
        {
 return loadClassForName(serviceClassName, classLoader.getParent());
        }
    }


 private T createInstance(Class<?> serviceClass)
    {
 try
        {
 Constructor<?> constructor = serviceClass.getDeclaredConstructor();
 constructor.setAccessible(true);
 return (T) constructor.newInstance();
        }
 catch (Exception e)
        {
 return null;
        }
    }


 /**
     * {@inheritDoc}
     */
 @Override
 public String toString()
    {
 return "Config file: " + getConfigFileLocation();
    }
}