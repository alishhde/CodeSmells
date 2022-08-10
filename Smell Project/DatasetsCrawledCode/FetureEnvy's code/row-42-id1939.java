 public static String repeat(String s, int c) {
 StringBuilder sb = new StringBuilder();
 for (int i = 0; i < c; i++)
 sb.append(s);
 return sb.toString();
  }