public class HandleConfigDriveIsoCommand extends Command {


 @LogLevel(LogLevel.Log4jLevel.Off)
 private String isoData;


 private String isoFile;
 private boolean create = false;
 private DataStoreTO destStore;


 public HandleConfigDriveIsoCommand(String isoFile, String isoData, DataStoreTO destStore, boolean create) {
 this.isoFile = isoFile;
 this.isoData = isoData;
 this.destStore = destStore;
 this.create = create;
    }


 @Override
 public boolean executeInSequence() {
 return false;
    }


 public String getIsoData() {
 return isoData;
    }


 public boolean isCreate() {
 return create;
    }


 public DataStoreTO getDestStore() {
 return destStore;
    }


 public String getIsoFile() {
 return isoFile;
    }
}