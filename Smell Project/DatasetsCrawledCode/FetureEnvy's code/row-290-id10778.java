 public static Predicate<OperatingSystem> isUnix() {
 return new Predicate<OperatingSystem>() {
 @Override
 public boolean apply(OperatingSystem os) {
 if (os.getFamily() != null) {
 switch (os.getFamily()) {
 case WINDOWS:
 return false;
               }
            }
 for (String toMatch : searchStrings(os))
 if (toMatch != null && toMatch.toLowerCase().indexOf("windows") != -1)
 return false;
 return true;
         }


 @Override
 public String toString() {
 return "isUnix()";
         }
      };
   }