 private static Class<?> stringToClass(String klass) throws FrontendException {
 if ("string".equalsIgnoreCase(klass)) {
 return String.class;
        } else if ("int".equalsIgnoreCase(klass)) {
 return Integer.TYPE;
        } else if ("double".equalsIgnoreCase(klass)) {
 return Double.TYPE;
        } else if ("float".equalsIgnoreCase(klass)){
 return Float.TYPE;
        } else if ("long".equalsIgnoreCase(klass)) {
 return Long.TYPE;
        } else if ("double[]".equalsIgnoreCase(klass)) {
 return DOUBLE_ARRAY_CLASS;
        } else if ("int[]".equalsIgnoreCase(klass)) {
 return INT_ARRAY_CLASS;
        } else if ("long[]".equalsIgnoreCase(klass)) {
 return LONG_ARRAY_CLASS;
        } else if ("float[]".equalsIgnoreCase(klass)) {
 return FLOAT_ARRAY_CLASS;
        } else if ("string[]".equalsIgnoreCase(klass)) {
 return STRING_ARRAY_CLASS;
        } else {
 throw new FrontendException("unable to find matching class for " + klass);
        }


    }