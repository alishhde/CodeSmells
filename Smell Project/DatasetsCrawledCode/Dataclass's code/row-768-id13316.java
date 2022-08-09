public class PartitionDescriptor extends Descriptor {


 /** Type token for ser/de partition descriptor list */
 private static final Type DESCRIPTOR_LIST_TYPE = new TypeToken<ArrayList<PartitionDescriptor>>(){}.getType();


 @Getter
 private final DatasetDescriptor dataset;


 public PartitionDescriptor(String name, DatasetDescriptor dataset) {
 super(name);
 this.dataset = dataset;
  }


 @Override
 public PartitionDescriptor copy() {
 return new PartitionDescriptor(getName(), dataset);
  }


 public PartitionDescriptor copyWithNewDataset(DatasetDescriptor dataset) {
 return new PartitionDescriptor(getName(), dataset);
  }


 @Override
 public boolean equals(Object o) {
 if (this == o) {
 return true;
    }
 if (o == null || getClass() != o.getClass()) {
 return false;
    }


 PartitionDescriptor that = (PartitionDescriptor) o;
 return dataset.equals(that.dataset) && getName().equals(that.getName());
  }


 @Override
 public int hashCode() {
 int result = dataset.hashCode();
 result = 31 * result + getName().hashCode();
 return result;
  }


 /**
   * Serialize a list of partition descriptors as json string
   */
 public static String toPartitionJsonList(List<PartitionDescriptor> descriptors) {
 return Descriptor.GSON.toJson(descriptors, DESCRIPTOR_LIST_TYPE);
  }


 /**
   * Deserialize the string, resulted from {@link #toPartitionJsonList(List)}, to a list of partition descriptors
   */
 public static List<PartitionDescriptor> fromPartitionJsonList(String jsonList) {
 return Descriptor.GSON.fromJson(jsonList, DESCRIPTOR_LIST_TYPE);
  }
}