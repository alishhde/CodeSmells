public class TfsGitTreeEntriesJson {
 private final String objectId;
 private final List<TfsGitTreeEntryJson> treeEntries;
 private final int size;


 @JsonCreator
 public TfsGitTreeEntriesJson(
 @JsonProperty("objectId") final String objectId,
 @JsonProperty("treeEntries") final List<TfsGitTreeEntryJson> treeEntries,
 @JsonProperty("size") final int size) throws JsonProcessingException {
 this.objectId = objectId;
 this.treeEntries = treeEntries;
 this.size = size;
    }


 public String getObjectId() {
 return objectId;
    }


 public List<TfsGitTreeEntryJson> getTreeEntries() {
 return treeEntries;
    }


 public int getSize() {
 return size;
    }
}