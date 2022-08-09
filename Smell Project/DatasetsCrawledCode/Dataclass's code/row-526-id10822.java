 public class Uses {
 private final int typeNameIndex;
 
 public Uses(int typeNameIndex) {
 this.typeNameIndex = typeNameIndex;
		}


 public String getTypeName() {
 return cpool.getConstantString_CONSTANTClass(typeNameIndex);
		}


 public int getTypeNameIndex() {
 return typeNameIndex;
		}
 
 public String toString() {
 StringBuilder s =new StringBuilder();
 s.append("uses ").append(getTypeName().replace('/', '.'));
 return s.toString().trim();
		}
	}