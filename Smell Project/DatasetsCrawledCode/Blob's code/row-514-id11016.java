 public static final class Builder extends
 com.google.protobuf.GeneratedMessage.Builder<Builder>
 implements org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProtoOrBuilder {
 public static final com.google.protobuf.Descriptors.Descriptor
 getDescriptor() {
 return org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.internal_static_UpdateQueryRequestProto_descriptor;
      }


 protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
 internalGetFieldAccessorTable() {
 return org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.internal_static_UpdateQueryRequestProto_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
 org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto.class, org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto.Builder.class);
      }


 // Construct using org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto.newBuilder()
 private Builder() {
 maybeForceBuilderInitialization();
      }


 private Builder(
 com.google.protobuf.GeneratedMessage.BuilderParent parent) {
 super(parent);
 maybeForceBuilderInitialization();
      }
 private void maybeForceBuilderInitialization() {
 if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
 private static Builder create() {
 return new Builder();
      }


 public Builder clear() {
 super.clear();
 guaranteedTaskCount_ = 0;
 bitField0_ = (bitField0_ & ~0x00000001);
 return this;
      }


 public Builder clone() {
 return create().mergeFrom(buildPartial());
      }


 public com.google.protobuf.Descriptors.Descriptor
 getDescriptorForType() {
 return org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.internal_static_UpdateQueryRequestProto_descriptor;
      }


 public org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto getDefaultInstanceForType() {
 return org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto.getDefaultInstance();
      }


 public org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto build() {
 org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto result = buildPartial();
 if (!result.isInitialized()) {
 throw newUninitializedMessageException(result);
        }
 return result;
      }


 public org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto buildPartial() {
 org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto result = new org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto(this);
 int from_bitField0_ = bitField0_;
 int to_bitField0_ = 0;
 if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
 to_bitField0_ |= 0x00000001;
        }
 result.guaranteedTaskCount_ = guaranteedTaskCount_;
 result.bitField0_ = to_bitField0_;
 onBuilt();
 return result;
      }


 public Builder mergeFrom(com.google.protobuf.Message other) {
 if (other instanceof org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto) {
 return mergeFrom((org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto)other);
        } else {
 super.mergeFrom(other);
 return this;
        }
      }


 public Builder mergeFrom(org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto other) {
 if (other == org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto.getDefaultInstance()) return this;
 if (other.hasGuaranteedTaskCount()) {
 setGuaranteedTaskCount(other.getGuaranteedTaskCount());
        }
 this.mergeUnknownFields(other.getUnknownFields());
 return this;
      }


 public final boolean isInitialized() {
 return true;
      }


 public Builder mergeFrom(
 com.google.protobuf.CodedInputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws java.io.IOException {
 org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto parsedMessage = null;
 try {
 parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
 parsedMessage = (org.apache.hadoop.hive.llap.plugin.rpc.LlapPluginProtocolProtos.UpdateQueryRequestProto) e.getUnfinishedMessage();
 throw e;
        } finally {
 if (parsedMessage != null) {
 mergeFrom(parsedMessage);
          }
        }
 return this;
      }
 private int bitField0_;


 // optional int32 guaranteed_task_count = 1;
 private int guaranteedTaskCount_ ;
 /**
       * <code>optional int32 guaranteed_task_count = 1;</code>
       */
 public boolean hasGuaranteedTaskCount() {
 return ((bitField0_ & 0x00000001) == 0x00000001);
      }
 /**
       * <code>optional int32 guaranteed_task_count = 1;</code>
       */
 public int getGuaranteedTaskCount() {
 return guaranteedTaskCount_;
      }
 /**
       * <code>optional int32 guaranteed_task_count = 1;</code>
       */
 public Builder setGuaranteedTaskCount(int value) {
 bitField0_ |= 0x00000001;
 guaranteedTaskCount_ = value;
 onChanged();
 return this;
      }
 /**
       * <code>optional int32 guaranteed_task_count = 1;</code>
       */
 public Builder clearGuaranteedTaskCount() {
 bitField0_ = (bitField0_ & ~0x00000001);
 guaranteedTaskCount_ = 0;
 onChanged();
 return this;
      }


 // @@protoc_insertion_point(builder_scope:UpdateQueryRequestProto)
    }