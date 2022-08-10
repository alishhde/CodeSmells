 @Override
 public int compare(PropertyDescriptor d1, PropertyDescriptor d2) {
 String g1 = group(d1);
 String g2 = group(d2);
 Integer go1 = groupOrder(g1);
 Integer go2 = groupOrder(g2);


 int result = go1.compareTo(go2);
 if (result != 0) {
 return result;
            }


 result = g1.compareTo(g2);
 if (result != 0) {
 return result;
            }


 Integer po1 = propertyOrder(d1);
 Integer po2 = propertyOrder(d2);
 result = po1.compareTo(po2);
 if (result != 0) {
 return result;
            }


 return d1.getName().compareTo(d2.getName());
        }