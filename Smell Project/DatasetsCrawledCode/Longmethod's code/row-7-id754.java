 @SuppressWarnings("unused")
 private String format(String s, Object[] arguments) {


 if (arguments == null) {
 return s;
        }


 // A very simple implementation of format
 int i = 0;
 while (i < arguments.length) {
 String delimiter = "{" + i + "}";
 while (s.contains(delimiter)) {
 s = s.replace(delimiter, String.valueOf(arguments[i]));
            }
 i++;
        }
 return s;
    }