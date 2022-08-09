public final class DirContextType {
 private String name;


 private DirContextType(String name) {
 this.name = name;
    }


 public String toString() {
 return name;
    }
 
 /**
     * The type of {@link DirContext} returned by {@link ContextSource#getReadOnlyContext()}
     */
 public static final DirContextType READ_ONLY = new DirContextType("READ_ONLY");
 
 /**
     * The type of {@link DirContext} returned by {@link ContextSource#getReadWriteContext()}
     */
 public static final DirContextType READ_WRITE = new DirContextType("READ_WRITE");
}