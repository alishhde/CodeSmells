 private Invocation next() {
 if (interceptors.hasNext()) {
 final Interceptor interceptor = interceptors.next();
 final Object nextInstance = interceptor.getInstance();
 final Method nextMethod = interceptor.getMethod();


 if (nextMethod.getParameterTypes().length == 1 && nextMethod.getParameterTypes()[0] == InvocationContext.class) {
 return new InterceptorInvocation(nextInstance, nextMethod, this);
            } else {
 return new LifecycleInvocation(nextInstance, nextMethod, this, parameters);
            }
        } else if (method != null) {
 //EJB 3.1, it is allowed that timeout method does not have parameter Timer.class,
 //However, while invoking the timeout method, the timer value is passed, as it is also required by InnvocationContext.getTimer() method
 final Object[] methodParameters;
 if (operation.equals(Operation.TIMEOUT) && method.getParameterTypes().length == 0) {
 methodParameters = new Object[0];
            } else {
 methodParameters = parameters;
            }
 return new BeanInvocation(target, method, methodParameters);
        } else {
 return new NoOpInvocation();
        }
    }