 public static Class<?> getPropertyEditorClass(final Object bean, final String name)
 throws IllegalAccessException, InvocationTargetException,
 NoSuchMethodException {


 return PropertyUtilsBean.getInstance().getPropertyEditorClass(bean, name);


    }