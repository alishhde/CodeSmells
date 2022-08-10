 Type asTuple() {
 final Type result;
 if (types.size() == 0) {
 result = unit.getEmptyType();
            } else {
 final Type sequentialType;
 if (variadic) {
 Part part = new Part("Sequence", Collections.singletonList(getLast()));
 sequentialType = loadType("ceylon.language", 
 atLeastOne ? "ceylon.language.Sequence" : "ceylon.language.Sequential", 
 part, null);
                } else {
 sequentialType = unit.getEmptyType();
                }
 
 if (variadic && types.size() == 1) {
 result = sequentialType;
                } else {
 Part part = new Part();
 // if we're variadic we put the element type there because we skip it below
 // if we're not variadic we are not going to skip it so let's not union it with itself
 Type union = variadic ? getLast() : null;
 Type tupleType = sequentialType;
 // A,B= 
 // union = null
 // tupleType = []
 // t = B
 // union = B
 // tupleType = [B]
 // tupleType = [B]|[]
 // t = A
 // union = A|B
 // tupleType = [A,[B]|[]]


 // A=,B= 
 // union = null
 // tupleType = []
 // t = B
 // union = B
 // tupleType = [B]
 // tupleType = [B]|[]
 // t = A
 // union = A|B
 // tupleType = [A,[B]|[]]
 // tupleType = [A,[B]|[]]|[]


 // A=,B* 
 // union = B
 // tupleType = [B*]
 // t = A
 // union = A|B
 // tupleType = [A,[B*]]
 // tupleType = [A,[B*]]|[]


 int makeDefaulted = defaulted;
 for (int ii  = types.size()-(variadic? 2 : 1); ii >= 0; ii--) {
 Type t = types.get(ii);
 // FIXME: subtyping in the type parser may cause issues
 if(union != null) // any second element (variadic or not)
 union = ModelUtil.unionType(union, t, unit);
 else
 union = t; // any first element
 part.parameters = Arrays.asList(union, t, tupleType);
 part.name = "Tuple";
 tupleType = loadType("ceylon.language", "ceylon.language.Tuple", part, null);
 if(makeDefaulted > 0){
 makeDefaulted--;
 tupleType = union(Arrays.asList(unit.getEmptyType(), tupleType), unit);
                        }
                    }
 result = tupleType;
                }
            }
 return result;
        }