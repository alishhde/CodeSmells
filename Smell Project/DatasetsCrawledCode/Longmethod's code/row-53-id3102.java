 private static int[] getIntArray(String string) {
 if (string.equals("[]")) {
 return new int[]{};
        }
 string = string.substring(1, string.length() - 1);
 String[] splits = string.split(",");
 int[] array = new int[splits.length];
 for (int i = 0; i < splits.length; i++) {
 array[i] = Integer.parseInt(splits[i]);
        }
 return array;
    }