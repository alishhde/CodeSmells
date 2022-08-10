 static String replaceSubstitution(String base, Pattern from, String to, 
 boolean repeat) {
 Matcher match = from.matcher(base);
 if (repeat) {
 return match.replaceAll(to);
      } else {
 return match.replaceFirst(to);
      }
    }