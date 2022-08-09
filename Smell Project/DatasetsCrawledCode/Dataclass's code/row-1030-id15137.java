public class ObjectInFolderListImpl extends AbstractExtensionData implements ObjectInFolderList {


 private static final long serialVersionUID = 1L;


 private List<ObjectInFolderData> objects;
 private Boolean hasMoreItems = Boolean.FALSE;
 private BigInteger numItems;


 @Override
 public List<ObjectInFolderData> getObjects() {
 if (objects == null) {
 objects = new ArrayList<ObjectInFolderData>(0);
        }


 return objects;
    }


 public void setObjects(List<ObjectInFolderData> objects) {
 this.objects = objects;
    }


 @Override
 public Boolean hasMoreItems() {
 return hasMoreItems;
    }


 public void setHasMoreItems(Boolean hasMoreItems) {
 this.hasMoreItems = hasMoreItems;
    }


 @Override
 public BigInteger getNumItems() {
 return numItems;
    }


 public void setNumItems(BigInteger numItems) {
 this.numItems = numItems;
    }


 @Override
 public String toString() {
 return "ObjectInFolder List [objects=" + objects + ", has more items=" + hasMoreItems + ", num items="
                + numItems + "]" + super.toString();
    }
}