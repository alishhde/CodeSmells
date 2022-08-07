 static final class SymbolComparator implements Comparator {


 /* (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
 public int compare(Object arg0, Object arg1) {
 
 long addr0=0;
 long addr1=0;
 // arg0 and arg1 will be Symbol objects 
 if (arg0 instanceof Symbol) {
 Symbol S0 = (Symbol) arg0;
 Symbol S1 = (Symbol) arg1;
 addr0 = S0.symbolStart;
 addr1 = S1.symbolStart;
			} else {
 addr0 = ((Long)arg0).longValue();
 addr1 = ((Long)arg1).longValue();
			}
 
 // both +ve
 if (addr0 >= 0 && addr1 >=0) {
 if (addr0 == addr1) return 0;
 if (addr0 < addr1) return -1;
 return 1;
			}
 
 // both -ve 
 if (addr0 < 0 && addr1 < 0) {
 if (addr0 == addr1) return 0;
 if (addr0 < addr1) return 1;
 return -1;
			}
 
 if (addr0 < 0 && addr1 >=0) {
 return 1;
			}
 
 return -1;
		}
 
	}