public class SimpleMessage implements Message {


 private final MessageId messageId;
 private final String subType;
 private final String mediaType;
 private final SharedInputStream content;
 private final int bodyStartOctet;
 private final Date internalDate;
 private final long size;
 private final Long textualLineCount;
 private final List<Property> properties;
 private final List<MessageAttachment> attachments;


 public SimpleMessage(MessageId messageId, SharedInputStream content, long size, Date internalDate, String subType, String mediaType, int bodyStartOctet, Long textualLineCount, List<Property> properties, List<MessageAttachment> attachments) {
 this.messageId = messageId;
 this.subType = subType;
 this.mediaType = mediaType;
 this.content = content;
 this.bodyStartOctet = bodyStartOctet;
 this.internalDate = internalDate;
 this.size = size;
 this.textualLineCount = textualLineCount;
 this.properties = properties;
 this.attachments = attachments;
    }


 public SimpleMessage(MessageId messageId, SharedInputStream content, long size, Date internalDate, String subType, String mediaType, int bodyStartOctet, Long textualLineCount, List<Property> properties) {
 this(messageId, content, size, internalDate, subType, mediaType, bodyStartOctet, textualLineCount, properties, ImmutableList.<MessageAttachment>of());
    }


 @Override
 public MessageId getMessageId() {
 return messageId;
    }


 @Override
 public Date getInternalDate() {
 return internalDate;
    }


 @Override
 public InputStream getBodyContent() throws IOException {
 return content.newStream(bodyStartOctet, -1);
    }


 @Override
 public String getMediaType() {
 return mediaType;
    }


 @Override
 public String getSubType() {
 return subType;
    }


 @Override
 public long getBodyOctets() {
 return getFullContentOctets() - bodyStartOctet;
    }


 @Override
 public long getHeaderOctets() {
 return bodyStartOctet;
    }


 @Override
 public long getFullContentOctets() {
 return size;
    }


 @Override
 public Long getTextualLineCount() {
 return textualLineCount;
    }


 @Override
 public InputStream getHeaderContent() throws IOException {
 long headerEnd = bodyStartOctet;
 if (headerEnd < 0) {
 headerEnd = 0;
        }
 return content.newStream(0, headerEnd);
    }


 @Override
 public InputStream getFullContent() throws IOException {
 return content.newStream(0, -1);
    }


 @Override
 public List<Property> getProperties() {
 return properties;
    }


 @Override
 public List<MessageAttachment> getAttachments() {
 return attachments;
    }
}