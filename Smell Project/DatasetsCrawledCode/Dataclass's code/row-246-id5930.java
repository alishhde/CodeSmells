 public static class Exceptions {


 private Exceptions() {
        }


 public static IllegalArgumentException propertyKeyCanNotBeEmpty() {
 return new IllegalArgumentException("Property key can not be the empty string");
        }


 public static IllegalArgumentException propertyKeyCanNotBeNull() {
 return new IllegalArgumentException("Property key can not be null");
        }


 public static IllegalArgumentException propertyValueCanNotBeNull() {
 return new IllegalArgumentException("Property value can not be null");
        }


 public static IllegalArgumentException propertyKeyCanNotBeAHiddenKey(final String key) {
 return new IllegalArgumentException("Property key can not be a hidden key: " + key);
        }


 public static IllegalStateException propertyDoesNotExist() {
 return new IllegalStateException("The property does not exist as it has no key, value, or associated element");
        }


 public static IllegalStateException propertyDoesNotExist(final Element element, final String key) {
 return new IllegalStateException("The property does not exist as the key has no associated value for the provided element: " + element + ":" + key);
        }


 public static IllegalArgumentException dataTypeOfPropertyValueNotSupported(final Object val) {
 return dataTypeOfPropertyValueNotSupported(val, null);
        }


 public static IllegalArgumentException dataTypeOfPropertyValueNotSupported(final Object val, final Exception rootCause) {
 return new IllegalArgumentException(String.format("Property value [%s] is of type %s is not supported", val, val.getClass()), rootCause);
        }


 public static IllegalStateException propertyRemovalNotSupported() {
 return new IllegalStateException("Property removal is not supported");
        }
    }