 private Object invoke(String methodName, Object returnValueIfNonExistent,
 Class<?>[] paramTypes, Object[] params)
 throws DocletInvokeException {
 Method meth;
 try {
 meth = docletClass.getMethod(methodName, paramTypes);
            } catch (NoSuchMethodException exc) {
 if (returnValueIfNonExistent == null) {
 messager.error(Messager.NOPOS, "main.doclet_method_not_found",
 docletClassName, methodName);
 throw new DocletInvokeException();
                } else {
 return returnValueIfNonExistent;
                }
            } catch (SecurityException exc) {
 messager.error(Messager.NOPOS, "main.doclet_method_not_accessible",
 docletClassName, methodName);
 throw new DocletInvokeException();
            }
 if (!Modifier.isStatic(meth.getModifiers())) {
 messager.error(Messager.NOPOS, "main.doclet_method_must_be_static",
 docletClassName, methodName);
 throw new DocletInvokeException();
            }
 ClassLoader savedCCL =
 Thread.currentThread().getContextClassLoader();
 try {
 if (appClassLoader != null) // will be null if doclet class provided via API
 Thread.currentThread().setContextClassLoader(appClassLoader);
 return meth.invoke(null , params);
            } catch (IllegalArgumentException | NullPointerException exc) {
 messager.error(Messager.NOPOS, "main.internal_error_exception_thrown",
 docletClassName, methodName, exc.toString());
 throw new DocletInvokeException();
            } catch (IllegalAccessException exc) {
 messager.error(Messager.NOPOS, "main.doclet_method_not_accessible",
 docletClassName, methodName);
 throw new DocletInvokeException();
            }
 catch (InvocationTargetException exc) {
 Throwable err = exc.getTargetException();
 if (apiMode)
 throw new ClientCodeException(err);
 if (err instanceof java.lang.OutOfMemoryError) {
 messager.error(Messager.NOPOS, "main.out.of.memory");
                } else {
 messager.error(Messager.NOPOS, "main.exception_thrown",
 docletClassName, methodName, exc.toString());
 exc.getTargetException().printStackTrace(System.err);
                }
 throw new DocletInvokeException();
            } finally {
 Thread.currentThread().setContextClassLoader(savedCCL);
            }
    }