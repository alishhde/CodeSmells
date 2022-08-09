public class BaseClassInfo {
 //~ Instance fields ----------------------------------------------------------------------------------------------------------


 protected String name;
 protected String nameAndLoader; // A combinarion of class name and loader, uniquely identifying this ClassInfo


 // Management of multiple versions for the same-named (but possibly not same-code) class, loaded by different classloaders
 protected int classLoaderId; // IDs of all loaders with which versions of this class are loaded


 // Data used by our object allocation instrumentation mechanism: integer class ID
 private int instrClassId;


 //~ Constructors -------------------------------------------------------------------------------------------------------------


 public BaseClassInfo(String className, int classLoaderId) {
 this.name = className.intern();
 this.classLoaderId = classLoaderId;
 nameAndLoader = (name + "#" + classLoaderId).intern(); // NOI18N
 instrClassId = -1;
    }


 //~ Methods ------------------------------------------------------------------------------------------------------------------


 public void setInstrClassId(int id) {
 instrClassId = id;
    }


 public int getInstrClassId() {
 return instrClassId;
    }


 public void setLoaderId(int loaderId) {
 classLoaderId = loaderId;
    }


 public int getLoaderId() {
 return classLoaderId;
    }


 public String getName() {
 return name;
    }


 public String getNameAndLoader() {
 return nameAndLoader;
    }


 public String toString() {
 return name;
    }
}