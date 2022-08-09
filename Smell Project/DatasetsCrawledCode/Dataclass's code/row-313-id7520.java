 public static class DAOAttachment {
 private final AttachmentId attachmentId;
 private final BlobId blobId;
 private final String type;
 private final long size;


 private DAOAttachment(AttachmentId attachmentId, BlobId blobId, String type, long size) {
 this.attachmentId = attachmentId;
 this.blobId = blobId;
 this.type = type;
 this.size = size;
        }


 public AttachmentId getAttachmentId() {
 return attachmentId;
        }


 public BlobId getBlobId() {
 return blobId;
        }


 public String getType() {
 return type;
        }


 public long getSize() {
 return size;
        }


 public Attachment toAttachment(byte[] data) {
 return Attachment.builder()
                .attachmentId(attachmentId)
                .type(type)
                .bytes(data)
                .build();
        }


 @Override
 public final boolean equals(Object o) {
 if (o instanceof DAOAttachment) {
 DAOAttachment that = (DAOAttachment) o;


 return Objects.equals(this.size, that.size)
                    && Objects.equals(this.attachmentId, that.attachmentId)
                    && Objects.equals(this.blobId, that.blobId)
                    && Objects.equals(this.type, that.type);
            }
 return false;
        }


 @Override
 public final int hashCode() {
 return Objects.hash(attachmentId, blobId, type, size);
        }
    }