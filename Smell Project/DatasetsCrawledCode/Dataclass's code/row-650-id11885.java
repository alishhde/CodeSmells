/*package*/ class WafFolderContent {
 private final WafFolder rootFolder;
 private final List<WafFolder> subFolders;
 private final List<WafFile> files;


 /**
   * Creates instance of the folder content.
   * @param rootFolder root folder
   * @param subFolders sub folders
   * @param files files
   */
 public WafFolderContent(WafFolder rootFolder, List<WafFolder> subFolders, List<WafFile> files) {
 this.rootFolder = rootFolder;
 this.subFolders = subFolders;
 this.files = files;
  }


 /**
   * Gets root folder.
   * @return root folder
   */
 public WafFolder getRootFolder() {
 return rootFolder;
  }


 /**
   * Gets sub folders.
   * @return sub folders
   */
 public List<WafFolder> getSubFolders() {
 return subFolders;
  }


 /**
   * Gets files.
   * @return files
   */
 public List<WafFile> getFiles() {
 return files;
  }
 
 
}