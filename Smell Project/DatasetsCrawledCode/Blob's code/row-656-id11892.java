public class ClassStructureImplByJDK extends FamilyClassStructure {


 private final Class<?> clazz;
 private String javaClassName;


 public ClassStructureImplByJDK(final Class<?> clazz) {
 this.clazz = clazz;
    }


 private ClassStructure newInstance(final Class<?> clazz) {
 if (null == clazz) {
 return null;
        }
 return new ClassStructureImplByJDK(clazz);
    }


 private List<ClassStructure> newInstances(final Class[] classArray) {
 final List<ClassStructure> classStructures = new ArrayList<ClassStructure>();
 if (null != classArray) {
 for (final Class<?> clazz : classArray) {
 final ClassStructure classStructure = newInstance(clazz);
 if (null != classStructure) {
 classStructures.add(classStructure);
                }
            }
        }
 return classStructures;
    }


 @Override
 public String getJavaClassName() {
 return null != javaClassName
                ? javaClassName
                : (javaClassName = getJavaClassName(clazz));
    }


 private String getJavaClassName(Class<?> clazz) {
 if (clazz.isArray()) {
 return getJavaClassName(clazz.getComponentType()) + "[]";
        }
 return clazz.getName();
    }




 @Override
 public ClassLoader getClassLoader() {
 return clazz.getClassLoader();
    }


 @Override
 public ClassStructure getSuperClassStructure() {
