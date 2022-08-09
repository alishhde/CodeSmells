public class IgfsHandshakeResponse implements Externalizable {
 /** */
 private static final long serialVersionUID = 0L;


 /** IGFS name. */
 private String igfsName;


 /** Server block size. */
 private long blockSize;


 /** Whether to force sampling on client's side. */
 private Boolean sampling;


 /**
     * {@link Externalizable} support.
     */
 public IgfsHandshakeResponse() {
 // No-op.
    }


 /**
     * Constructor.
     *
     * @param blockSize Server default block size.
     */
 public IgfsHandshakeResponse(String igfsName, long blockSize, Boolean sampling) {
 this.igfsName = igfsName;
 this.blockSize = blockSize;
 this.sampling = sampling;
    }


 /**
     * @return IGFS name.
     */
 public String igfsName() {
 return igfsName;
    }


 /**
     * @return Server default block size.
     */
 public long blockSize() {
 return blockSize;
    }


 /**
     * @return Sampling flag.
     */
 public Boolean sampling() {
 return sampling;
    }


 /** {@inheritDoc} */
 @Override public void writeExternal(ObjectOutput out) throws IOException {
 U.writeString(out, igfsName);


 out.writeLong(blockSize);


 if (sampling != null) {
 out.writeBoolean(true);
 out.writeBoolean(sampling);
        }
 else
 out.writeBoolean(false);
    }


 /** {@inheritDoc} */
 @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
 igfsName = U.readString(in);


 blockSize = in.readLong();


 if (in.readBoolean())
 sampling = in.readBoolean();
    }
}