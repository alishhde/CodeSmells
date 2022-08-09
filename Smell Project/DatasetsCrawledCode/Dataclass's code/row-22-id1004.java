public class SysInfo implements Serializable {


 static final long serialVersionUID = -3096346807579L;


 public int numCores;
 public long maxMemory;


 public SysInfo(int nc, long mm) {
 numCores = nc;
 maxMemory = mm;
    }
}