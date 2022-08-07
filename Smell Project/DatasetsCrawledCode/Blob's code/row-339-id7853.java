public final class CConstantValueSupportImpl implements CConstantValueSupport {
 private final NativeLibraries nativeLibraries;
 private final MetaAccessProvider metaAccess;


 public CConstantValueSupportImpl(NativeLibraries nativeLibraries, MetaAccessProvider metaAccess) {
 this.nativeLibraries = nativeLibraries;
 this.metaAccess = metaAccess;
    }


 @Override
 public <T> T getCConstantValue(Class<?> declaringClass, String methodName, Class<T> returnType) {
 ResolvedJavaMethod method;
 try {
 method = metaAccess.lookupJavaMethod(declaringClass.getMethod(methodName));
        } catch (NoSuchMethodException | SecurityException e) {
 throw VMError.shouldNotReachHere("Method not found: " + declaringClass.getName() + "." + methodName);
        }
 if (method.getAnnotation(CConstant.class) == null) {
 throw VMError.shouldNotReachHere("Method " + declaringClass.getName() + "." + methodName + " is not annotated with @" + CConstant.class.getSimpleName());
        }


 ConstantInfo constantInfo = (ConstantInfo) nativeLibraries.findElementInfo(method);
 Object value = constantInfo.getValueInfo().getProperty();
 switch (constantInfo.getKind()) {
 case INTEGER:
 case POINTER:
 Long longValue = (Long) value;
 if (returnType == Boolean.class) {
 return returnType.cast(Boolean.valueOf(longValue.longValue() != 0));
                } else if (returnType == Integer.class) {
 return returnType.cast(Integer.valueOf((int) longValue.longValue()));
                } else if (returnType == Long.class) {
 return returnType.cast(value);
                }
 break;


 case FLOAT:
 if (returnType == Double.class) {
 return returnType.cast(value);
                }
 break;


 case STRING:
 if (returnType == String.class) {
 return returnType.cast(value);
                }
 break;


 case BYTEARRAY:
 if (returnType == byte[].class) {
 return returnType.cast(value);
                }
 break;
        }


 throw VMError.shouldNotReachHere("Unexpected returnType: " + returnType.getName());
    }
}