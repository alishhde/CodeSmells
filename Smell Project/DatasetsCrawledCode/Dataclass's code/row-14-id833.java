 final static class TypeList {
 Hashtable types;


 TypeList()
        {


        }


 TypeList(Vector typeNames)
        {
 types = new Hashtable();
 for ( int i = 0; i < typeNames.size(); i++ ) {
 String t = ((String) typeNames.elementAt(i)).toLowerCase();
 types.put(t, t);
            }
        }


 final boolean contains(String type)
        {
 if ( types == null ) {
 return true; //defaults to all
            }
 return types.containsKey(type);
        }
    }