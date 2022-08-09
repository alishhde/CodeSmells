 public static final class OpCopyBlockProto extends
 com.google.protobuf.GeneratedMessage
 implements OpCopyBlockProtoOrBuilder {
 // Use OpCopyBlockProto.newBuilder() to construct.
 private OpCopyBlockProto(Builder builder) {
 super(builder);
    }
 private OpCopyBlockProto(boolean noInit) {}
 
 private static final OpCopyBlockProto defaultInstance;
 public static OpCopyBlockProto getDefaultInstance() {
 return defaultInstance;
    }
 
 public OpCopyBlockProto getDefaultInstanceForType() {
 return defaultInstance;
    }
 
 public static final com.google.protobuf.Descriptors.Descriptor
 getDescriptor() {
 return org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.internal_static_OpCopyBlockProto_descriptor;
    }
 
 protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
 internalGetFieldAccessorTable() {
 return org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.internal_static_OpCopyBlockProto_fieldAccessorTable;
    }
 
 private int bitField0_;
 // required .BaseHeaderProto header = 1;
 public static final int HEADER_FIELD_NUMBER = 1;
 private org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto header_;
 public boolean hasHeader() {
 return ((bitField0_ & 0x00000001) == 0x00000001);
    }
 public org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto getHeader() {
 return header_;
    }
 public org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProtoOrBuilder getHeaderOrBuilder() {
 return header_;
    }
 
 private void initFields() {
 header_ = org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.getDefaultInstance();
    }
 private byte memoizedIsInitialized = -1;
 public final boolean isInitialized() {
 byte isInitialized = memoizedIsInitialized;
 if (isInitialized != -1) return isInitialized == 1;
 
 if (!hasHeader()) {
 memoizedIsInitialized = 0;
 return false;
      }
 if (!getHeader().isInitialized()) {
 memoizedIsInitialized = 0;
 return false;
      }
 memoizedIsInitialized = 1;
 return true;
    }
 
 public void writeTo(com.google.protobuf.CodedOutputStream output)
 throws java.io.IOException {
 getSerializedSize();
 if (((bitField0_ & 0x00000001) == 0x00000001)) {
 output.writeMessage(1, header_);
      }
 getUnknownFields().writeTo(output);
    }
 
 private int memoizedSerializedSize = -1;
 public int getSerializedSize() {
 int size = memoizedSerializedSize;
 if (size != -1) return size;
 
 size = 0;
 if (((bitField0_ & 0x00000001) == 0x00000001)) {
 size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, header_);
      }
 size += getUnknownFields().getSerializedSize();
 memoizedSerializedSize = size;
 return size;
    }
 
 private static final long serialVersionUID = 0L;
 @java.lang.Override
 protected java.lang.Object writeReplace()
 throws java.io.ObjectStreamException {
 return super.writeReplace();
    }
 
 @java.lang.Override
 public boolean equals(final java.lang.Object obj) {
 if (obj == this) {
 return true;
      }
 if (!(obj instanceof org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto)) {
 return super.equals(obj);
      }
 org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto other = (org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto) obj;
 
 boolean result = true;
 result = result && (hasHeader() == other.hasHeader());
 if (hasHeader()) {
 result = result && getHeader()
            .equals(other.getHeader());
      }
 result = result &&
 getUnknownFields().equals(other.getUnknownFields());
 return result;
    }
 
 @java.lang.Override
 public int hashCode() {
 int hash = 41;
 hash = (19 * hash) + getDescriptorForType().hashCode();
 if (hasHeader()) {
 hash = (37 * hash) + HEADER_FIELD_NUMBER;
 hash = (53 * hash) + getHeader().hashCode();
      }
 hash = (29 * hash) + getUnknownFields().hashCode();
 return hash;
    }
 
 public static org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto parseFrom(
 com.google.protobuf.ByteString data)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return newBuilder().mergeFrom(data).buildParsed();
    }
 public static org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto parseFrom(
 com.google.protobuf.ByteString data,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
 public static org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto parseFrom(byte[] data)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return newBuilder().mergeFrom(data).buildParsed();
    }
 public static org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto parseFrom(
 byte[] data,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
 public static org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto parseFrom(java.io.InputStream input)
 throws java.io.IOException {
 return newBuilder().mergeFrom(input).buildParsed();
    }
 public static org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto parseFrom(
 java.io.InputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws java.io.IOException {
 return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
 public static org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto parseDelimitedFrom(java.io.InputStream input)
 throws java.io.IOException {
 Builder builder = newBuilder();
 if (builder.mergeDelimitedFrom(input)) {
 return builder.buildParsed();
      } else {
 return null;
      }
    }
 public static org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto parseDelimitedFrom(
 java.io.InputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws java.io.IOException {
 Builder builder = newBuilder();
 if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
 return builder.buildParsed();
      } else {
 return null;
      }
    }
 public static org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto parseFrom(
 com.google.protobuf.CodedInputStream input)
 throws java.io.IOException {
 return newBuilder().mergeFrom(input).buildParsed();
    }
 public static org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto parseFrom(
 com.google.protobuf.CodedInputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws java.io.IOException {
 return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
 
 public static Builder newBuilder() { return Builder.create(); }
 public Builder newBuilderForType() { return newBuilder(); }
 public static Builder newBuilder(org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto prototype) {
 return newBuilder().mergeFrom(prototype);
    }
 public Builder toBuilder() { return newBuilder(this); }
 
 @java.lang.Override
 protected Builder newBuilderForType(
 com.google.protobuf.GeneratedMessage.BuilderParent parent) {
 Builder builder = new Builder(parent);
 return builder;
    }
 public static final class Builder extends
 com.google.protobuf.GeneratedMessage.Builder<Builder>
 implements org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProtoOrBuilder {
 public static final com.google.protobuf.Descriptors.Descriptor
 getDescriptor() {
 return org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.internal_static_OpCopyBlockProto_descriptor;
      }
 
 protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
 internalGetFieldAccessorTable() {
 return org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.internal_static_OpCopyBlockProto_fieldAccessorTable;
      }
 
 // Construct using org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto.newBuilder()
 private Builder() {
 maybeForceBuilderInitialization();
      }
 
 private Builder(BuilderParent parent) {
 super(parent);
 maybeForceBuilderInitialization();
      }
 private void maybeForceBuilderInitialization() {
 if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
 getHeaderFieldBuilder();
        }
      }
 private static Builder create() {
 return new Builder();
      }
 
 public Builder clear() {
 super.clear();
 if (headerBuilder_ == null) {
 header_ = org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.getDefaultInstance();
        } else {
 headerBuilder_.clear();
        }
 bitField0_ = (bitField0_ & ~0x00000001);
 return this;
      }
 
 public Builder clone() {
 return create().mergeFrom(buildPartial());
      }
 
 public com.google.protobuf.Descriptors.Descriptor
 getDescriptorForType() {
 return org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto.getDescriptor();
      }
 
 public org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto getDefaultInstanceForType() {
 return org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto.getDefaultInstance();
      }
 
 public org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto build() {
 org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto result = buildPartial();
 if (!result.isInitialized()) {
 throw newUninitializedMessageException(result);
        }
 return result;
      }
 
 private org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto buildParsed()
 throws com.google.protobuf.InvalidProtocolBufferException {
 org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto result = buildPartial();
 if (!result.isInitialized()) {
 throw newUninitializedMessageException(
 result).asInvalidProtocolBufferException();
        }
 return result;
      }
 
 public org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto buildPartial() {
 org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto result = new org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto(this);
 int from_bitField0_ = bitField0_;
 int to_bitField0_ = 0;
 if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
 to_bitField0_ |= 0x00000001;
        }
 if (headerBuilder_ == null) {
 result.header_ = header_;
        } else {
 result.header_ = headerBuilder_.build();
        }
 result.bitField0_ = to_bitField0_;
 onBuilt();
 return result;
      }
 
 public Builder mergeFrom(com.google.protobuf.Message other) {
 if (other instanceof org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto) {
 return mergeFrom((org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto)other);
        } else {
 super.mergeFrom(other);
 return this;
        }
      }
 
 public Builder mergeFrom(org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto other) {
 if (other == org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.OpCopyBlockProto.getDefaultInstance()) return this;
 if (other.hasHeader()) {
 mergeHeader(other.getHeader());
        }
 this.mergeUnknownFields(other.getUnknownFields());
 return this;
      }
 
 public final boolean isInitialized() {
 if (!hasHeader()) {
 
 return false;
        }
 if (!getHeader().isInitialized()) {
 
 return false;
        }
 return true;
      }
 
 public Builder mergeFrom(
 com.google.protobuf.CodedInputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws java.io.IOException {
 com.google.protobuf.UnknownFieldSet.Builder unknownFields =
 com.google.protobuf.UnknownFieldSet.newBuilder(
 this.getUnknownFields());
 while (true) {
 int tag = input.readTag();
 switch (tag) {
 case 0:
 this.setUnknownFields(unknownFields.build());
 onChanged();
 return this;
 default: {
 if (!parseUnknownField(input, unknownFields,
 extensionRegistry, tag)) {
 this.setUnknownFields(unknownFields.build());
 onChanged();
 return this;
              }
 break;
            }
 case 10: {
 org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.Builder subBuilder = org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.newBuilder();
 if (hasHeader()) {
 subBuilder.mergeFrom(getHeader());
              }
 input.readMessage(subBuilder, extensionRegistry);
 setHeader(subBuilder.buildPartial());
 break;
            }
          }
        }
      }
 
 private int bitField0_;
 
 // required .BaseHeaderProto header = 1;
 private org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto header_ = org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.getDefaultInstance();
 private com.google.protobuf.SingleFieldBuilder<
 org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto, org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.Builder, org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProtoOrBuilder> headerBuilder_;
 public boolean hasHeader() {
 return ((bitField0_ & 0x00000001) == 0x00000001);
      }
 public org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto getHeader() {
 if (headerBuilder_ == null) {
 return header_;
        } else {
 return headerBuilder_.getMessage();
        }
      }
 public Builder setHeader(org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto value) {
 if (headerBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 header_ = value;
 onChanged();
        } else {
 headerBuilder_.setMessage(value);
        }
 bitField0_ |= 0x00000001;
 return this;
      }
 public Builder setHeader(
 org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.Builder builderForValue) {
 if (headerBuilder_ == null) {
 header_ = builderForValue.build();
 onChanged();
        } else {
 headerBuilder_.setMessage(builderForValue.build());
        }
 bitField0_ |= 0x00000001;
 return this;
      }
 public Builder mergeHeader(org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto value) {
 if (headerBuilder_ == null) {
 if (((bitField0_ & 0x00000001) == 0x00000001) &&
 header_ != org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.getDefaultInstance()) {
 header_ =
 org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.newBuilder(header_).mergeFrom(value).buildPartial();
          } else {
 header_ = value;
          }
 onChanged();
        } else {
 headerBuilder_.mergeFrom(value);
        }
 bitField0_ |= 0x00000001;
 return this;
      }
 public Builder clearHeader() {
 if (headerBuilder_ == null) {
 header_ = org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.getDefaultInstance();
 onChanged();
        } else {
 headerBuilder_.clear();
        }
 bitField0_ = (bitField0_ & ~0x00000001);
 return this;
      }
 public org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.Builder getHeaderBuilder() {
 bitField0_ |= 0x00000001;
 onChanged();
 return getHeaderFieldBuilder().getBuilder();
      }
 public org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProtoOrBuilder getHeaderOrBuilder() {
 if (headerBuilder_ != null) {
 return headerBuilder_.getMessageOrBuilder();
        } else {
 return header_;
        }
      }
 private com.google.protobuf.SingleFieldBuilder<
 org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto, org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.Builder, org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProtoOrBuilder> 
 getHeaderFieldBuilder() {
 if (headerBuilder_ == null) {
 headerBuilder_ = new com.google.protobuf.SingleFieldBuilder<
 org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto, org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProto.Builder, org.apache.hadoop.hdfs.protocol.proto.DataTransferProtos.BaseHeaderProtoOrBuilder>(
 header_,
 getParentForChildren(),
 isClean());
 header_ = null;
        }
 return headerBuilder_;
      }
 
 // @@protoc_insertion_point(builder_scope:OpCopyBlockProto)
    }
 
 static {
 defaultInstance = new OpCopyBlockProto(true);
 defaultInstance.initFields();
    }
 
 // @@protoc_insertion_point(class_scope:OpCopyBlockProto)
  }