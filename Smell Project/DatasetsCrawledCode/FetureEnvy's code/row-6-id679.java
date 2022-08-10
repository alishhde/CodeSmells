 public static int getSiblingIndexWithClassName(Element element, String className) {
 int index = 0;
 while (element != null) {
 element = (Element) element.getPreviousSibling();
 if (element != null && Elements.hasClassName(className, element)) {
        ++index;
      }
    }
 return index;
  }