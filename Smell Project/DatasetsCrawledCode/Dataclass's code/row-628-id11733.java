@InterfaceAudience.Private
@InterfaceStability.Evolving
public class BlockMissingException extends IOException {


 private static final long serialVersionUID = 1L;


 private String filename;
 private long offset;


 /**
   * An exception that indicates that file was corrupted.
   * @param filename name of corrupted file
   * @param description a description of the corruption details
   */
 public BlockMissingException(String filename, String description, long offset) {
 super(description);
 this.filename = filename;
 this.offset = offset;
  }


 /**
   * Returns the name of the corrupted file.
   * @return name of corrupted file
   */
 public String getFile() {
 return filename;
  }


 /**
   * Returns the offset at which this file is corrupted
   * @return offset of corrupted file
   */
 public long getOffset() {
 return offset;
  }
}