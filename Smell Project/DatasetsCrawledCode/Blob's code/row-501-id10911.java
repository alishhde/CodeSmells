 public static final class Builder extends
 com.google.protobuf.GeneratedMessage.Builder<Builder>
 implements org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpecOrBuilder {
 public static final com.google.protobuf.Descriptors.Descriptor
 getDescriptor() {
 return org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.internal_static_SignableVertexSpec_descriptor;
      }


 protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
 internalGetFieldAccessorTable() {
 return org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.internal_static_SignableVertexSpec_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec.class, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec.Builder.class);
      }


 // Construct using org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec.newBuilder()
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
 getQueryIdentifierFieldBuilder();
 getProcessorDescriptorFieldBuilder();
 getInputSpecsFieldBuilder();
 getOutputSpecsFieldBuilder();
 getGroupedInputSpecsFieldBuilder();
        }
      }
 private static Builder create() {
 return new Builder();
      }


 public Builder clear() {
 super.clear();
 user_ = "";
 bitField0_ = (bitField0_ & ~0x00000001);
 signatureKeyId_ = 0L;
 bitField0_ = (bitField0_ & ~0x00000002);
 if (queryIdentifierBuilder_ == null) {
 queryIdentifier_ = org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto.getDefaultInstance();
        } else {
 queryIdentifierBuilder_.clear();
        }
 bitField0_ = (bitField0_ & ~0x00000004);
 hiveQueryId_ = "";
 bitField0_ = (bitField0_ & ~0x00000008);
 dagName_ = "";
 bitField0_ = (bitField0_ & ~0x00000010);
 vertexName_ = "";
 bitField0_ = (bitField0_ & ~0x00000020);
 vertexIndex_ = 0;
 bitField0_ = (bitField0_ & ~0x00000040);
 tokenIdentifier_ = "";
 bitField0_ = (bitField0_ & ~0x00000080);
 if (processorDescriptorBuilder_ == null) {
 processorDescriptor_ = org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto.getDefaultInstance();
        } else {
 processorDescriptorBuilder_.clear();
        }
 bitField0_ = (bitField0_ & ~0x00000100);
 if (inputSpecsBuilder_ == null) {
 inputSpecs_ = java.util.Collections.emptyList();
 bitField0_ = (bitField0_ & ~0x00000200);
        } else {
 inputSpecsBuilder_.clear();
        }
 if (outputSpecsBuilder_ == null) {
 outputSpecs_ = java.util.Collections.emptyList();
 bitField0_ = (bitField0_ & ~0x00000400);
        } else {
 outputSpecsBuilder_.clear();
        }
 if (groupedInputSpecsBuilder_ == null) {
 groupedInputSpecs_ = java.util.Collections.emptyList();
 bitField0_ = (bitField0_ & ~0x00000800);
        } else {
 groupedInputSpecsBuilder_.clear();
        }
 vertexParallelism_ = 0;
 bitField0_ = (bitField0_ & ~0x00001000);
 isExternalSubmission_ = false;
 bitField0_ = (bitField0_ & ~0x00002000);
 return this;
      }


 public Builder clone() {
 return create().mergeFrom(buildPartial());
      }


 public com.google.protobuf.Descriptors.Descriptor
 getDescriptorForType() {
 return org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.internal_static_SignableVertexSpec_descriptor;
      }


 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec getDefaultInstanceForType() {
 return org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec.getDefaultInstance();
      }


 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec build() {
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec result = buildPartial();
 if (!result.isInitialized()) {
 throw newUninitializedMessageException(result);
        }
 return result;
      }


 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec buildPartial() {
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec result = new org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec(this);
 int from_bitField0_ = bitField0_;
 int to_bitField0_ = 0;
 if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
 to_bitField0_ |= 0x00000001;
        }
 result.user_ = user_;
 if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
 to_bitField0_ |= 0x00000002;
        }
 result.signatureKeyId_ = signatureKeyId_;
 if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
 to_bitField0_ |= 0x00000004;
        }
 if (queryIdentifierBuilder_ == null) {
 result.queryIdentifier_ = queryIdentifier_;
        } else {
 result.queryIdentifier_ = queryIdentifierBuilder_.build();
        }
 if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
 to_bitField0_ |= 0x00000008;
        }
 result.hiveQueryId_ = hiveQueryId_;
 if (((from_bitField0_ & 0x00000010) == 0x00000010)) {
 to_bitField0_ |= 0x00000010;
        }
 result.dagName_ = dagName_;
 if (((from_bitField0_ & 0x00000020) == 0x00000020)) {
 to_bitField0_ |= 0x00000020;
        }
 result.vertexName_ = vertexName_;
 if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
 to_bitField0_ |= 0x00000040;
        }
 result.vertexIndex_ = vertexIndex_;
 if (((from_bitField0_ & 0x00000080) == 0x00000080)) {
 to_bitField0_ |= 0x00000080;
        }
 result.tokenIdentifier_ = tokenIdentifier_;
 if (((from_bitField0_ & 0x00000100) == 0x00000100)) {
 to_bitField0_ |= 0x00000100;
        }
 if (processorDescriptorBuilder_ == null) {
 result.processorDescriptor_ = processorDescriptor_;
        } else {
 result.processorDescriptor_ = processorDescriptorBuilder_.build();
        }
 if (inputSpecsBuilder_ == null) {
 if (((bitField0_ & 0x00000200) == 0x00000200)) {
 inputSpecs_ = java.util.Collections.unmodifiableList(inputSpecs_);
 bitField0_ = (bitField0_ & ~0x00000200);
          }
 result.inputSpecs_ = inputSpecs_;
        } else {
 result.inputSpecs_ = inputSpecsBuilder_.build();
        }
 if (outputSpecsBuilder_ == null) {
 if (((bitField0_ & 0x00000400) == 0x00000400)) {
 outputSpecs_ = java.util.Collections.unmodifiableList(outputSpecs_);
 bitField0_ = (bitField0_ & ~0x00000400);
          }
 result.outputSpecs_ = outputSpecs_;
        } else {
 result.outputSpecs_ = outputSpecsBuilder_.build();
        }
 if (groupedInputSpecsBuilder_ == null) {
 if (((bitField0_ & 0x00000800) == 0x00000800)) {
 groupedInputSpecs_ = java.util.Collections.unmodifiableList(groupedInputSpecs_);
 bitField0_ = (bitField0_ & ~0x00000800);
          }
 result.groupedInputSpecs_ = groupedInputSpecs_;
        } else {
 result.groupedInputSpecs_ = groupedInputSpecsBuilder_.build();
        }
 if (((from_bitField0_ & 0x00001000) == 0x00001000)) {
 to_bitField0_ |= 0x00000200;
        }
 result.vertexParallelism_ = vertexParallelism_;
 if (((from_bitField0_ & 0x00002000) == 0x00002000)) {
 to_bitField0_ |= 0x00000400;
        }
 result.isExternalSubmission_ = isExternalSubmission_;
 result.bitField0_ = to_bitField0_;
 onBuilt();
 return result;
      }


 public Builder mergeFrom(com.google.protobuf.Message other) {
 if (other instanceof org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec) {
 return mergeFrom((org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec)other);
        } else {
 super.mergeFrom(other);
 return this;
        }
      }


 public Builder mergeFrom(org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec other) {
 if (other == org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec.getDefaultInstance()) return this;
 if (other.hasUser()) {
 bitField0_ |= 0x00000001;
 user_ = other.user_;
 onChanged();
        }
 if (other.hasSignatureKeyId()) {
 setSignatureKeyId(other.getSignatureKeyId());
        }
 if (other.hasQueryIdentifier()) {
 mergeQueryIdentifier(other.getQueryIdentifier());
        }
 if (other.hasHiveQueryId()) {
 bitField0_ |= 0x00000008;
 hiveQueryId_ = other.hiveQueryId_;
 onChanged();
        }
 if (other.hasDagName()) {
 bitField0_ |= 0x00000010;
 dagName_ = other.dagName_;
 onChanged();
        }
 if (other.hasVertexName()) {
 bitField0_ |= 0x00000020;
 vertexName_ = other.vertexName_;
 onChanged();
        }
 if (other.hasVertexIndex()) {
 setVertexIndex(other.getVertexIndex());
        }
 if (other.hasTokenIdentifier()) {
 bitField0_ |= 0x00000080;
 tokenIdentifier_ = other.tokenIdentifier_;
 onChanged();
        }
 if (other.hasProcessorDescriptor()) {
 mergeProcessorDescriptor(other.getProcessorDescriptor());
        }
 if (inputSpecsBuilder_ == null) {
 if (!other.inputSpecs_.isEmpty()) {
 if (inputSpecs_.isEmpty()) {
 inputSpecs_ = other.inputSpecs_;
 bitField0_ = (bitField0_ & ~0x00000200);
            } else {
 ensureInputSpecsIsMutable();
 inputSpecs_.addAll(other.inputSpecs_);
            }
 onChanged();
          }
        } else {
 if (!other.inputSpecs_.isEmpty()) {
 if (inputSpecsBuilder_.isEmpty()) {
 inputSpecsBuilder_.dispose();
 inputSpecsBuilder_ = null;
 inputSpecs_ = other.inputSpecs_;
 bitField0_ = (bitField0_ & ~0x00000200);
 inputSpecsBuilder_ = 
 com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
 getInputSpecsFieldBuilder() : null;
            } else {
 inputSpecsBuilder_.addAllMessages(other.inputSpecs_);
            }
          }
        }
 if (outputSpecsBuilder_ == null) {
 if (!other.outputSpecs_.isEmpty()) {
 if (outputSpecs_.isEmpty()) {
 outputSpecs_ = other.outputSpecs_;
 bitField0_ = (bitField0_ & ~0x00000400);
            } else {
 ensureOutputSpecsIsMutable();
 outputSpecs_.addAll(other.outputSpecs_);
            }
 onChanged();
          }
        } else {
 if (!other.outputSpecs_.isEmpty()) {
 if (outputSpecsBuilder_.isEmpty()) {
 outputSpecsBuilder_.dispose();
 outputSpecsBuilder_ = null;
 outputSpecs_ = other.outputSpecs_;
 bitField0_ = (bitField0_ & ~0x00000400);
 outputSpecsBuilder_ = 
 com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
 getOutputSpecsFieldBuilder() : null;
            } else {
 outputSpecsBuilder_.addAllMessages(other.outputSpecs_);
            }
          }
        }
 if (groupedInputSpecsBuilder_ == null) {
 if (!other.groupedInputSpecs_.isEmpty()) {
 if (groupedInputSpecs_.isEmpty()) {
 groupedInputSpecs_ = other.groupedInputSpecs_;
 bitField0_ = (bitField0_ & ~0x00000800);
            } else {
 ensureGroupedInputSpecsIsMutable();
 groupedInputSpecs_.addAll(other.groupedInputSpecs_);
            }
 onChanged();
          }
        } else {
 if (!other.groupedInputSpecs_.isEmpty()) {
 if (groupedInputSpecsBuilder_.isEmpty()) {
 groupedInputSpecsBuilder_.dispose();
 groupedInputSpecsBuilder_ = null;
 groupedInputSpecs_ = other.groupedInputSpecs_;
 bitField0_ = (bitField0_ & ~0x00000800);
 groupedInputSpecsBuilder_ = 
 com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
 getGroupedInputSpecsFieldBuilder() : null;
            } else {
 groupedInputSpecsBuilder_.addAllMessages(other.groupedInputSpecs_);
            }
          }
        }
 if (other.hasVertexParallelism()) {
 setVertexParallelism(other.getVertexParallelism());
        }
 if (other.hasIsExternalSubmission()) {
 setIsExternalSubmission(other.getIsExternalSubmission());
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
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec parsedMessage = null;
 try {
 parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
 parsedMessage = (org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.SignableVertexSpec) e.getUnfinishedMessage();
 throw e;
        } finally {
 if (parsedMessage != null) {
 mergeFrom(parsedMessage);
          }
        }
 return this;
      }
 private int bitField0_;


 // optional string user = 1;
 private java.lang.Object user_ = "";
 /**
       * <code>optional string user = 1;</code>
       */
 public boolean hasUser() {
 return ((bitField0_ & 0x00000001) == 0x00000001);
      }
 /**
       * <code>optional string user = 1;</code>
       */
 public java.lang.String getUser() {
 java.lang.Object ref = user_;
 if (!(ref instanceof java.lang.String)) {
 java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
 user_ = s;
 return s;
        } else {
 return (java.lang.String) ref;
        }
      }
 /**
       * <code>optional string user = 1;</code>
       */
 public com.google.protobuf.ByteString
 getUserBytes() {
 java.lang.Object ref = user_;
 if (ref instanceof String) {
 com.google.protobuf.ByteString b = 
 com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
 user_ = b;
 return b;
        } else {
 return (com.google.protobuf.ByteString) ref;
        }
      }
 /**
       * <code>optional string user = 1;</code>
       */
 public Builder setUser(
 java.lang.String value) {
 if (value == null) {
 throw new NullPointerException();
  }
 bitField0_ |= 0x00000001;
 user_ = value;
 onChanged();
 return this;
      }
 /**
       * <code>optional string user = 1;</code>
       */
 public Builder clearUser() {
 bitField0_ = (bitField0_ & ~0x00000001);
 user_ = getDefaultInstance().getUser();
 onChanged();
 return this;
      }
 /**
       * <code>optional string user = 1;</code>
       */
 public Builder setUserBytes(
 com.google.protobuf.ByteString value) {
 if (value == null) {
 throw new NullPointerException();
  }
 bitField0_ |= 0x00000001;
 user_ = value;
 onChanged();
 return this;
      }


 // optional int64 signatureKeyId = 2;
 private long signatureKeyId_ ;
 /**
       * <code>optional int64 signatureKeyId = 2;</code>
       */
 public boolean hasSignatureKeyId() {
 return ((bitField0_ & 0x00000002) == 0x00000002);
      }
 /**
       * <code>optional int64 signatureKeyId = 2;</code>
       */
 public long getSignatureKeyId() {
 return signatureKeyId_;
      }
 /**
       * <code>optional int64 signatureKeyId = 2;</code>
       */
 public Builder setSignatureKeyId(long value) {
 bitField0_ |= 0x00000002;
 signatureKeyId_ = value;
 onChanged();
 return this;
      }
 /**
       * <code>optional int64 signatureKeyId = 2;</code>
       */
 public Builder clearSignatureKeyId() {
 bitField0_ = (bitField0_ & ~0x00000002);
 signatureKeyId_ = 0L;
 onChanged();
 return this;
      }


 // optional .QueryIdentifierProto query_identifier = 3;
 private org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto queryIdentifier_ = org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto.getDefaultInstance();
 private com.google.protobuf.SingleFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProtoOrBuilder> queryIdentifierBuilder_;
 /**
       * <code>optional .QueryIdentifierProto query_identifier = 3;</code>
       */
 public boolean hasQueryIdentifier() {
 return ((bitField0_ & 0x00000004) == 0x00000004);
      }
 /**
       * <code>optional .QueryIdentifierProto query_identifier = 3;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto getQueryIdentifier() {
 if (queryIdentifierBuilder_ == null) {
 return queryIdentifier_;
        } else {
 return queryIdentifierBuilder_.getMessage();
        }
      }
 /**
       * <code>optional .QueryIdentifierProto query_identifier = 3;</code>
       */
 public Builder setQueryIdentifier(org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto value) {
 if (queryIdentifierBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 queryIdentifier_ = value;
 onChanged();
        } else {
 queryIdentifierBuilder_.setMessage(value);
        }
 bitField0_ |= 0x00000004;
 return this;
      }
 /**
       * <code>optional .QueryIdentifierProto query_identifier = 3;</code>
       */
 public Builder setQueryIdentifier(
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto.Builder builderForValue) {
 if (queryIdentifierBuilder_ == null) {
 queryIdentifier_ = builderForValue.build();
 onChanged();
        } else {
 queryIdentifierBuilder_.setMessage(builderForValue.build());
        }
 bitField0_ |= 0x00000004;
 return this;
      }
 /**
       * <code>optional .QueryIdentifierProto query_identifier = 3;</code>
       */
 public Builder mergeQueryIdentifier(org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto value) {
 if (queryIdentifierBuilder_ == null) {
 if (((bitField0_ & 0x00000004) == 0x00000004) &&
 queryIdentifier_ != org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto.getDefaultInstance()) {
 queryIdentifier_ =
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto.newBuilder(queryIdentifier_).mergeFrom(value).buildPartial();
          } else {
 queryIdentifier_ = value;
          }
 onChanged();
        } else {
 queryIdentifierBuilder_.mergeFrom(value);
        }
 bitField0_ |= 0x00000004;
 return this;
      }
 /**
       * <code>optional .QueryIdentifierProto query_identifier = 3;</code>
       */
 public Builder clearQueryIdentifier() {
 if (queryIdentifierBuilder_ == null) {
 queryIdentifier_ = org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto.getDefaultInstance();
 onChanged();
        } else {
 queryIdentifierBuilder_.clear();
        }
 bitField0_ = (bitField0_ & ~0x00000004);
 return this;
      }
 /**
       * <code>optional .QueryIdentifierProto query_identifier = 3;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto.Builder getQueryIdentifierBuilder() {
 bitField0_ |= 0x00000004;
 onChanged();
 return getQueryIdentifierFieldBuilder().getBuilder();
      }
 /**
       * <code>optional .QueryIdentifierProto query_identifier = 3;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProtoOrBuilder getQueryIdentifierOrBuilder() {
 if (queryIdentifierBuilder_ != null) {
 return queryIdentifierBuilder_.getMessageOrBuilder();
        } else {
 return queryIdentifier_;
        }
      }
 /**
       * <code>optional .QueryIdentifierProto query_identifier = 3;</code>
       */
 private com.google.protobuf.SingleFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProtoOrBuilder> 
 getQueryIdentifierFieldBuilder() {
 if (queryIdentifierBuilder_ == null) {
 queryIdentifierBuilder_ = new com.google.protobuf.SingleFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.QueryIdentifierProtoOrBuilder>(
 queryIdentifier_,
 getParentForChildren(),
 isClean());
 queryIdentifier_ = null;
        }
 return queryIdentifierBuilder_;
      }


 // optional string hive_query_id = 4;
 private java.lang.Object hiveQueryId_ = "";
 /**
       * <code>optional string hive_query_id = 4;</code>
       */
 public boolean hasHiveQueryId() {
 return ((bitField0_ & 0x00000008) == 0x00000008);
      }
 /**
       * <code>optional string hive_query_id = 4;</code>
       */
 public java.lang.String getHiveQueryId() {
 java.lang.Object ref = hiveQueryId_;
 if (!(ref instanceof java.lang.String)) {
 java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
 hiveQueryId_ = s;
 return s;
        } else {
 return (java.lang.String) ref;
        }
      }
 /**
       * <code>optional string hive_query_id = 4;</code>
       */
 public com.google.protobuf.ByteString
 getHiveQueryIdBytes() {
 java.lang.Object ref = hiveQueryId_;
 if (ref instanceof String) {
 com.google.protobuf.ByteString b = 
 com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
 hiveQueryId_ = b;
 return b;
        } else {
 return (com.google.protobuf.ByteString) ref;
        }
      }
 /**
       * <code>optional string hive_query_id = 4;</code>
       */
 public Builder setHiveQueryId(
 java.lang.String value) {
 if (value == null) {
 throw new NullPointerException();
  }
 bitField0_ |= 0x00000008;
 hiveQueryId_ = value;
 onChanged();
 return this;
      }
 /**
       * <code>optional string hive_query_id = 4;</code>
       */
 public Builder clearHiveQueryId() {
 bitField0_ = (bitField0_ & ~0x00000008);
 hiveQueryId_ = getDefaultInstance().getHiveQueryId();
 onChanged();
 return this;
      }
 /**
       * <code>optional string hive_query_id = 4;</code>
       */
 public Builder setHiveQueryIdBytes(
 com.google.protobuf.ByteString value) {
 if (value == null) {
 throw new NullPointerException();
  }
 bitField0_ |= 0x00000008;
 hiveQueryId_ = value;
 onChanged();
 return this;
      }


 // optional string dag_name = 5;
 private java.lang.Object dagName_ = "";
 /**
       * <code>optional string dag_name = 5;</code>
       *
       * <pre>
       * Display names cannot be modified by the client for now. If needed, they should be sent to HS2 who will put them here.
       * </pre>
       */
 public boolean hasDagName() {
 return ((bitField0_ & 0x00000010) == 0x00000010);
      }
 /**
       * <code>optional string dag_name = 5;</code>
       *
       * <pre>
       * Display names cannot be modified by the client for now. If needed, they should be sent to HS2 who will put them here.
       * </pre>
       */
 public java.lang.String getDagName() {
 java.lang.Object ref = dagName_;
 if (!(ref instanceof java.lang.String)) {
 java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
 dagName_ = s;
 return s;
        } else {
 return (java.lang.String) ref;
        }
      }
 /**
       * <code>optional string dag_name = 5;</code>
       *
       * <pre>
       * Display names cannot be modified by the client for now. If needed, they should be sent to HS2 who will put them here.
       * </pre>
       */
 public com.google.protobuf.ByteString
 getDagNameBytes() {
 java.lang.Object ref = dagName_;
 if (ref instanceof String) {
 com.google.protobuf.ByteString b = 
 com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
 dagName_ = b;
 return b;
        } else {
 return (com.google.protobuf.ByteString) ref;
        }
      }
 /**
       * <code>optional string dag_name = 5;</code>
       *
       * <pre>
       * Display names cannot be modified by the client for now. If needed, they should be sent to HS2 who will put them here.
       * </pre>
       */
 public Builder setDagName(
 java.lang.String value) {
 if (value == null) {
 throw new NullPointerException();
  }
 bitField0_ |= 0x00000010;
 dagName_ = value;
 onChanged();
 return this;
      }
 /**
       * <code>optional string dag_name = 5;</code>
       *
       * <pre>
       * Display names cannot be modified by the client for now. If needed, they should be sent to HS2 who will put them here.
       * </pre>
       */
 public Builder clearDagName() {
 bitField0_ = (bitField0_ & ~0x00000010);
 dagName_ = getDefaultInstance().getDagName();
 onChanged();
 return this;
      }
 /**
       * <code>optional string dag_name = 5;</code>
       *
       * <pre>
       * Display names cannot be modified by the client for now. If needed, they should be sent to HS2 who will put them here.
       * </pre>
       */
 public Builder setDagNameBytes(
 com.google.protobuf.ByteString value) {
 if (value == null) {
 throw new NullPointerException();
  }
 bitField0_ |= 0x00000010;
 dagName_ = value;
 onChanged();
 return this;
      }


 // optional string vertex_name = 6;
 private java.lang.Object vertexName_ = "";
 /**
       * <code>optional string vertex_name = 6;</code>
       */
 public boolean hasVertexName() {
 return ((bitField0_ & 0x00000020) == 0x00000020);
      }
 /**
       * <code>optional string vertex_name = 6;</code>
       */
 public java.lang.String getVertexName() {
 java.lang.Object ref = vertexName_;
 if (!(ref instanceof java.lang.String)) {
 java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
 vertexName_ = s;
 return s;
        } else {
 return (java.lang.String) ref;
        }
      }
 /**
       * <code>optional string vertex_name = 6;</code>
       */
 public com.google.protobuf.ByteString
 getVertexNameBytes() {
 java.lang.Object ref = vertexName_;
 if (ref instanceof String) {
 com.google.protobuf.ByteString b = 
 com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
 vertexName_ = b;
 return b;
        } else {
 return (com.google.protobuf.ByteString) ref;
        }
      }
 /**
       * <code>optional string vertex_name = 6;</code>
       */
 public Builder setVertexName(
 java.lang.String value) {
 if (value == null) {
 throw new NullPointerException();
  }
 bitField0_ |= 0x00000020;
 vertexName_ = value;
 onChanged();
 return this;
      }
 /**
       * <code>optional string vertex_name = 6;</code>
       */
 public Builder clearVertexName() {
 bitField0_ = (bitField0_ & ~0x00000020);
 vertexName_ = getDefaultInstance().getVertexName();
 onChanged();
 return this;
      }
 /**
       * <code>optional string vertex_name = 6;</code>
       */
 public Builder setVertexNameBytes(
 com.google.protobuf.ByteString value) {
 if (value == null) {
 throw new NullPointerException();
  }
 bitField0_ |= 0x00000020;
 vertexName_ = value;
 onChanged();
 return this;
      }


 // optional int32 vertex_index = 7;
 private int vertexIndex_ ;
 /**
       * <code>optional int32 vertex_index = 7;</code>
       */
 public boolean hasVertexIndex() {
 return ((bitField0_ & 0x00000040) == 0x00000040);
      }
 /**
       * <code>optional int32 vertex_index = 7;</code>
       */
 public int getVertexIndex() {
 return vertexIndex_;
      }
 /**
       * <code>optional int32 vertex_index = 7;</code>
       */
 public Builder setVertexIndex(int value) {
 bitField0_ |= 0x00000040;
 vertexIndex_ = value;
 onChanged();
 return this;
      }
 /**
       * <code>optional int32 vertex_index = 7;</code>
       */
 public Builder clearVertexIndex() {
 bitField0_ = (bitField0_ & ~0x00000040);
 vertexIndex_ = 0;
 onChanged();
 return this;
      }


 // optional string token_identifier = 8;
 private java.lang.Object tokenIdentifier_ = "";
 /**
       * <code>optional string token_identifier = 8;</code>
       *
       * <pre>
       * The core vertex stuff 
       * </pre>
       */
 public boolean hasTokenIdentifier() {
 return ((bitField0_ & 0x00000080) == 0x00000080);
      }
 /**
       * <code>optional string token_identifier = 8;</code>
       *
       * <pre>
       * The core vertex stuff 
       * </pre>
       */
 public java.lang.String getTokenIdentifier() {
 java.lang.Object ref = tokenIdentifier_;
 if (!(ref instanceof java.lang.String)) {
 java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
 tokenIdentifier_ = s;
 return s;
        } else {
 return (java.lang.String) ref;
        }
      }
 /**
       * <code>optional string token_identifier = 8;</code>
       *
       * <pre>
       * The core vertex stuff 
       * </pre>
       */
 public com.google.protobuf.ByteString
 getTokenIdentifierBytes() {
 java.lang.Object ref = tokenIdentifier_;
 if (ref instanceof String) {
 com.google.protobuf.ByteString b = 
 com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
 tokenIdentifier_ = b;
 return b;
        } else {
 return (com.google.protobuf.ByteString) ref;
        }
      }
 /**
       * <code>optional string token_identifier = 8;</code>
       *
       * <pre>
       * The core vertex stuff 
       * </pre>
       */
 public Builder setTokenIdentifier(
 java.lang.String value) {
 if (value == null) {
 throw new NullPointerException();
  }
 bitField0_ |= 0x00000080;
 tokenIdentifier_ = value;
 onChanged();
 return this;
      }
 /**
       * <code>optional string token_identifier = 8;</code>
       *
       * <pre>
       * The core vertex stuff 
       * </pre>
       */
 public Builder clearTokenIdentifier() {
 bitField0_ = (bitField0_ & ~0x00000080);
 tokenIdentifier_ = getDefaultInstance().getTokenIdentifier();
 onChanged();
 return this;
      }
 /**
       * <code>optional string token_identifier = 8;</code>
       *
       * <pre>
       * The core vertex stuff 
       * </pre>
       */
 public Builder setTokenIdentifierBytes(
 com.google.protobuf.ByteString value) {
 if (value == null) {
 throw new NullPointerException();
  }
 bitField0_ |= 0x00000080;
 tokenIdentifier_ = value;
 onChanged();
 return this;
      }


 // optional .EntityDescriptorProto processor_descriptor = 9;
 private org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto processorDescriptor_ = org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto.getDefaultInstance();
 private com.google.protobuf.SingleFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProtoOrBuilder> processorDescriptorBuilder_;
 /**
       * <code>optional .EntityDescriptorProto processor_descriptor = 9;</code>
       */
 public boolean hasProcessorDescriptor() {
 return ((bitField0_ & 0x00000100) == 0x00000100);
      }
 /**
       * <code>optional .EntityDescriptorProto processor_descriptor = 9;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto getProcessorDescriptor() {
 if (processorDescriptorBuilder_ == null) {
 return processorDescriptor_;
        } else {
 return processorDescriptorBuilder_.getMessage();
        }
      }
 /**
       * <code>optional .EntityDescriptorProto processor_descriptor = 9;</code>
       */
 public Builder setProcessorDescriptor(org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto value) {
 if (processorDescriptorBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 processorDescriptor_ = value;
 onChanged();
        } else {
 processorDescriptorBuilder_.setMessage(value);
        }
 bitField0_ |= 0x00000100;
 return this;
      }
 /**
       * <code>optional .EntityDescriptorProto processor_descriptor = 9;</code>
       */
 public Builder setProcessorDescriptor(
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto.Builder builderForValue) {
 if (processorDescriptorBuilder_ == null) {
 processorDescriptor_ = builderForValue.build();
 onChanged();
        } else {
 processorDescriptorBuilder_.setMessage(builderForValue.build());
        }
 bitField0_ |= 0x00000100;
 return this;
      }
 /**
       * <code>optional .EntityDescriptorProto processor_descriptor = 9;</code>
       */
 public Builder mergeProcessorDescriptor(org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto value) {
 if (processorDescriptorBuilder_ == null) {
 if (((bitField0_ & 0x00000100) == 0x00000100) &&
 processorDescriptor_ != org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto.getDefaultInstance()) {
 processorDescriptor_ =
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto.newBuilder(processorDescriptor_).mergeFrom(value).buildPartial();
          } else {
 processorDescriptor_ = value;
          }
 onChanged();
        } else {
 processorDescriptorBuilder_.mergeFrom(value);
        }
 bitField0_ |= 0x00000100;
 return this;
      }
 /**
       * <code>optional .EntityDescriptorProto processor_descriptor = 9;</code>
       */
 public Builder clearProcessorDescriptor() {
 if (processorDescriptorBuilder_ == null) {
 processorDescriptor_ = org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto.getDefaultInstance();
 onChanged();
        } else {
 processorDescriptorBuilder_.clear();
        }
 bitField0_ = (bitField0_ & ~0x00000100);
 return this;
      }
 /**
       * <code>optional .EntityDescriptorProto processor_descriptor = 9;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto.Builder getProcessorDescriptorBuilder() {
 bitField0_ |= 0x00000100;
 onChanged();
 return getProcessorDescriptorFieldBuilder().getBuilder();
      }
 /**
       * <code>optional .EntityDescriptorProto processor_descriptor = 9;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProtoOrBuilder getProcessorDescriptorOrBuilder() {
 if (processorDescriptorBuilder_ != null) {
 return processorDescriptorBuilder_.getMessageOrBuilder();
        } else {
 return processorDescriptor_;
        }
      }
 /**
       * <code>optional .EntityDescriptorProto processor_descriptor = 9;</code>
       */
 private com.google.protobuf.SingleFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProtoOrBuilder> 
 getProcessorDescriptorFieldBuilder() {
 if (processorDescriptorBuilder_ == null) {
 processorDescriptorBuilder_ = new com.google.protobuf.SingleFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.EntityDescriptorProtoOrBuilder>(
 processorDescriptor_,
 getParentForChildren(),
 isClean());
 processorDescriptor_ = null;
        }
 return processorDescriptorBuilder_;
      }


 // repeated .IOSpecProto input_specs = 10;
 private java.util.List<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto> inputSpecs_ =
 java.util.Collections.emptyList();
 private void ensureInputSpecsIsMutable() {
 if (!((bitField0_ & 0x00000200) == 0x00000200)) {
 inputSpecs_ = new java.util.ArrayList<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto>(inputSpecs_);
 bitField0_ |= 0x00000200;
         }
      }


 private com.google.protobuf.RepeatedFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProtoOrBuilder> inputSpecsBuilder_;


 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public java.util.List<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto> getInputSpecsList() {
 if (inputSpecsBuilder_ == null) {
 return java.util.Collections.unmodifiableList(inputSpecs_);
        } else {
 return inputSpecsBuilder_.getMessageList();
        }
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public int getInputSpecsCount() {
 if (inputSpecsBuilder_ == null) {
 return inputSpecs_.size();
        } else {
 return inputSpecsBuilder_.getCount();
        }
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto getInputSpecs(int index) {
 if (inputSpecsBuilder_ == null) {
 return inputSpecs_.get(index);
        } else {
 return inputSpecsBuilder_.getMessage(index);
        }
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public Builder setInputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto value) {
 if (inputSpecsBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 ensureInputSpecsIsMutable();
 inputSpecs_.set(index, value);
 onChanged();
        } else {
 inputSpecsBuilder_.setMessage(index, value);
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public Builder setInputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder builderForValue) {
 if (inputSpecsBuilder_ == null) {
 ensureInputSpecsIsMutable();
 inputSpecs_.set(index, builderForValue.build());
 onChanged();
        } else {
 inputSpecsBuilder_.setMessage(index, builderForValue.build());
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public Builder addInputSpecs(org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto value) {
 if (inputSpecsBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 ensureInputSpecsIsMutable();
 inputSpecs_.add(value);
 onChanged();
        } else {
 inputSpecsBuilder_.addMessage(value);
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public Builder addInputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto value) {
 if (inputSpecsBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 ensureInputSpecsIsMutable();
 inputSpecs_.add(index, value);
 onChanged();
        } else {
 inputSpecsBuilder_.addMessage(index, value);
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public Builder addInputSpecs(
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder builderForValue) {
 if (inputSpecsBuilder_ == null) {
 ensureInputSpecsIsMutable();
 inputSpecs_.add(builderForValue.build());
 onChanged();
        } else {
 inputSpecsBuilder_.addMessage(builderForValue.build());
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public Builder addInputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder builderForValue) {
 if (inputSpecsBuilder_ == null) {
 ensureInputSpecsIsMutable();
 inputSpecs_.add(index, builderForValue.build());
 onChanged();
        } else {
 inputSpecsBuilder_.addMessage(index, builderForValue.build());
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public Builder addAllInputSpecs(
 java.lang.Iterable<? extends org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto> values) {
 if (inputSpecsBuilder_ == null) {
 ensureInputSpecsIsMutable();
 super.addAll(values, inputSpecs_);
 onChanged();
        } else {
 inputSpecsBuilder_.addAllMessages(values);
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public Builder clearInputSpecs() {
 if (inputSpecsBuilder_ == null) {
 inputSpecs_ = java.util.Collections.emptyList();
 bitField0_ = (bitField0_ & ~0x00000200);
 onChanged();
        } else {
 inputSpecsBuilder_.clear();
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public Builder removeInputSpecs(int index) {
 if (inputSpecsBuilder_ == null) {
 ensureInputSpecsIsMutable();
 inputSpecs_.remove(index);
 onChanged();
        } else {
 inputSpecsBuilder_.remove(index);
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder getInputSpecsBuilder(
 int index) {
 return getInputSpecsFieldBuilder().getBuilder(index);
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProtoOrBuilder getInputSpecsOrBuilder(
 int index) {
 if (inputSpecsBuilder_ == null) {
 return inputSpecs_.get(index);  } else {
 return inputSpecsBuilder_.getMessageOrBuilder(index);
        }
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public java.util.List<? extends org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProtoOrBuilder> 
 getInputSpecsOrBuilderList() {
 if (inputSpecsBuilder_ != null) {
 return inputSpecsBuilder_.getMessageOrBuilderList();
        } else {
 return java.util.Collections.unmodifiableList(inputSpecs_);
        }
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder addInputSpecsBuilder() {
 return getInputSpecsFieldBuilder().addBuilder(
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.getDefaultInstance());
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder addInputSpecsBuilder(
 int index) {
 return getInputSpecsFieldBuilder().addBuilder(
 index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.getDefaultInstance());
      }
 /**
       * <code>repeated .IOSpecProto input_specs = 10;</code>
       */
 public java.util.List<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder> 
 getInputSpecsBuilderList() {
 return getInputSpecsFieldBuilder().getBuilderList();
      }
 private com.google.protobuf.RepeatedFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProtoOrBuilder> 
 getInputSpecsFieldBuilder() {
 if (inputSpecsBuilder_ == null) {
 inputSpecsBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProtoOrBuilder>(
 inputSpecs_,
                  ((bitField0_ & 0x00000200) == 0x00000200),
 getParentForChildren(),
 isClean());
 inputSpecs_ = null;
        }
 return inputSpecsBuilder_;
      }


 // repeated .IOSpecProto output_specs = 11;
 private java.util.List<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto> outputSpecs_ =
 java.util.Collections.emptyList();
 private void ensureOutputSpecsIsMutable() {
 if (!((bitField0_ & 0x00000400) == 0x00000400)) {
 outputSpecs_ = new java.util.ArrayList<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto>(outputSpecs_);
 bitField0_ |= 0x00000400;
         }
      }


 private com.google.protobuf.RepeatedFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProtoOrBuilder> outputSpecsBuilder_;


 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public java.util.List<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto> getOutputSpecsList() {
 if (outputSpecsBuilder_ == null) {
 return java.util.Collections.unmodifiableList(outputSpecs_);
        } else {
 return outputSpecsBuilder_.getMessageList();
        }
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public int getOutputSpecsCount() {
 if (outputSpecsBuilder_ == null) {
 return outputSpecs_.size();
        } else {
 return outputSpecsBuilder_.getCount();
        }
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto getOutputSpecs(int index) {
 if (outputSpecsBuilder_ == null) {
 return outputSpecs_.get(index);
        } else {
 return outputSpecsBuilder_.getMessage(index);
        }
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public Builder setOutputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto value) {
 if (outputSpecsBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 ensureOutputSpecsIsMutable();
 outputSpecs_.set(index, value);
 onChanged();
        } else {
 outputSpecsBuilder_.setMessage(index, value);
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public Builder setOutputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder builderForValue) {
 if (outputSpecsBuilder_ == null) {
 ensureOutputSpecsIsMutable();
 outputSpecs_.set(index, builderForValue.build());
 onChanged();
        } else {
 outputSpecsBuilder_.setMessage(index, builderForValue.build());
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public Builder addOutputSpecs(org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto value) {
 if (outputSpecsBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 ensureOutputSpecsIsMutable();
 outputSpecs_.add(value);
 onChanged();
        } else {
 outputSpecsBuilder_.addMessage(value);
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public Builder addOutputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto value) {
 if (outputSpecsBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 ensureOutputSpecsIsMutable();
 outputSpecs_.add(index, value);
 onChanged();
        } else {
 outputSpecsBuilder_.addMessage(index, value);
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public Builder addOutputSpecs(
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder builderForValue) {
 if (outputSpecsBuilder_ == null) {
 ensureOutputSpecsIsMutable();
 outputSpecs_.add(builderForValue.build());
 onChanged();
        } else {
 outputSpecsBuilder_.addMessage(builderForValue.build());
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public Builder addOutputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder builderForValue) {
 if (outputSpecsBuilder_ == null) {
 ensureOutputSpecsIsMutable();
 outputSpecs_.add(index, builderForValue.build());
 onChanged();
        } else {
 outputSpecsBuilder_.addMessage(index, builderForValue.build());
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public Builder addAllOutputSpecs(
 java.lang.Iterable<? extends org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto> values) {
 if (outputSpecsBuilder_ == null) {
 ensureOutputSpecsIsMutable();
 super.addAll(values, outputSpecs_);
 onChanged();
        } else {
 outputSpecsBuilder_.addAllMessages(values);
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public Builder clearOutputSpecs() {
 if (outputSpecsBuilder_ == null) {
 outputSpecs_ = java.util.Collections.emptyList();
 bitField0_ = (bitField0_ & ~0x00000400);
 onChanged();
        } else {
 outputSpecsBuilder_.clear();
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public Builder removeOutputSpecs(int index) {
 if (outputSpecsBuilder_ == null) {
 ensureOutputSpecsIsMutable();
 outputSpecs_.remove(index);
 onChanged();
        } else {
 outputSpecsBuilder_.remove(index);
        }
 return this;
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder getOutputSpecsBuilder(
 int index) {
 return getOutputSpecsFieldBuilder().getBuilder(index);
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProtoOrBuilder getOutputSpecsOrBuilder(
 int index) {
 if (outputSpecsBuilder_ == null) {
 return outputSpecs_.get(index);  } else {
 return outputSpecsBuilder_.getMessageOrBuilder(index);
        }
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public java.util.List<? extends org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProtoOrBuilder> 
 getOutputSpecsOrBuilderList() {
 if (outputSpecsBuilder_ != null) {
 return outputSpecsBuilder_.getMessageOrBuilderList();
        } else {
 return java.util.Collections.unmodifiableList(outputSpecs_);
        }
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder addOutputSpecsBuilder() {
 return getOutputSpecsFieldBuilder().addBuilder(
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.getDefaultInstance());
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder addOutputSpecsBuilder(
 int index) {
 return getOutputSpecsFieldBuilder().addBuilder(
 index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.getDefaultInstance());
      }
 /**
       * <code>repeated .IOSpecProto output_specs = 11;</code>
       */
 public java.util.List<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder> 
 getOutputSpecsBuilderList() {
 return getOutputSpecsFieldBuilder().getBuilderList();
      }
 private com.google.protobuf.RepeatedFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProtoOrBuilder> 
 getOutputSpecsFieldBuilder() {
 if (outputSpecsBuilder_ == null) {
 outputSpecsBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.IOSpecProtoOrBuilder>(
 outputSpecs_,
                  ((bitField0_ & 0x00000400) == 0x00000400),
 getParentForChildren(),
 isClean());
 outputSpecs_ = null;
        }
 return outputSpecsBuilder_;
      }


 // repeated .GroupInputSpecProto grouped_input_specs = 12;
 private java.util.List<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto> groupedInputSpecs_ =
 java.util.Collections.emptyList();
 private void ensureGroupedInputSpecsIsMutable() {
 if (!((bitField0_ & 0x00000800) == 0x00000800)) {
 groupedInputSpecs_ = new java.util.ArrayList<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto>(groupedInputSpecs_);
 bitField0_ |= 0x00000800;
         }
      }


 private com.google.protobuf.RepeatedFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProtoOrBuilder> groupedInputSpecsBuilder_;


 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public java.util.List<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto> getGroupedInputSpecsList() {
 if (groupedInputSpecsBuilder_ == null) {
 return java.util.Collections.unmodifiableList(groupedInputSpecs_);
        } else {
 return groupedInputSpecsBuilder_.getMessageList();
        }
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public int getGroupedInputSpecsCount() {
 if (groupedInputSpecsBuilder_ == null) {
 return groupedInputSpecs_.size();
        } else {
 return groupedInputSpecsBuilder_.getCount();
        }
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto getGroupedInputSpecs(int index) {
 if (groupedInputSpecsBuilder_ == null) {
 return groupedInputSpecs_.get(index);
        } else {
 return groupedInputSpecsBuilder_.getMessage(index);
        }
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public Builder setGroupedInputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto value) {
 if (groupedInputSpecsBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 ensureGroupedInputSpecsIsMutable();
 groupedInputSpecs_.set(index, value);
 onChanged();
        } else {
 groupedInputSpecsBuilder_.setMessage(index, value);
        }
 return this;
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public Builder setGroupedInputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.Builder builderForValue) {
 if (groupedInputSpecsBuilder_ == null) {
 ensureGroupedInputSpecsIsMutable();
 groupedInputSpecs_.set(index, builderForValue.build());
 onChanged();
        } else {
 groupedInputSpecsBuilder_.setMessage(index, builderForValue.build());
        }
 return this;
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public Builder addGroupedInputSpecs(org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto value) {
 if (groupedInputSpecsBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 ensureGroupedInputSpecsIsMutable();
 groupedInputSpecs_.add(value);
 onChanged();
        } else {
 groupedInputSpecsBuilder_.addMessage(value);
        }
 return this;
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public Builder addGroupedInputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto value) {
 if (groupedInputSpecsBuilder_ == null) {
 if (value == null) {
 throw new NullPointerException();
          }
 ensureGroupedInputSpecsIsMutable();
 groupedInputSpecs_.add(index, value);
 onChanged();
        } else {
 groupedInputSpecsBuilder_.addMessage(index, value);
        }
 return this;
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public Builder addGroupedInputSpecs(
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.Builder builderForValue) {
 if (groupedInputSpecsBuilder_ == null) {
 ensureGroupedInputSpecsIsMutable();
 groupedInputSpecs_.add(builderForValue.build());
 onChanged();
        } else {
 groupedInputSpecsBuilder_.addMessage(builderForValue.build());
        }
 return this;
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public Builder addGroupedInputSpecs(
 int index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.Builder builderForValue) {
 if (groupedInputSpecsBuilder_ == null) {
 ensureGroupedInputSpecsIsMutable();
 groupedInputSpecs_.add(index, builderForValue.build());
 onChanged();
        } else {
 groupedInputSpecsBuilder_.addMessage(index, builderForValue.build());
        }
 return this;
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public Builder addAllGroupedInputSpecs(
 java.lang.Iterable<? extends org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto> values) {
 if (groupedInputSpecsBuilder_ == null) {
 ensureGroupedInputSpecsIsMutable();
 super.addAll(values, groupedInputSpecs_);
 onChanged();
        } else {
 groupedInputSpecsBuilder_.addAllMessages(values);
        }
 return this;
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public Builder clearGroupedInputSpecs() {
 if (groupedInputSpecsBuilder_ == null) {
 groupedInputSpecs_ = java.util.Collections.emptyList();
 bitField0_ = (bitField0_ & ~0x00000800);
 onChanged();
        } else {
 groupedInputSpecsBuilder_.clear();
        }
 return this;
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public Builder removeGroupedInputSpecs(int index) {
 if (groupedInputSpecsBuilder_ == null) {
 ensureGroupedInputSpecsIsMutable();
 groupedInputSpecs_.remove(index);
 onChanged();
        } else {
 groupedInputSpecsBuilder_.remove(index);
        }
 return this;
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.Builder getGroupedInputSpecsBuilder(
 int index) {
 return getGroupedInputSpecsFieldBuilder().getBuilder(index);
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProtoOrBuilder getGroupedInputSpecsOrBuilder(
 int index) {
 if (groupedInputSpecsBuilder_ == null) {
 return groupedInputSpecs_.get(index);  } else {
 return groupedInputSpecsBuilder_.getMessageOrBuilder(index);
        }
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public java.util.List<? extends org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProtoOrBuilder> 
 getGroupedInputSpecsOrBuilderList() {
 if (groupedInputSpecsBuilder_ != null) {
 return groupedInputSpecsBuilder_.getMessageOrBuilderList();
        } else {
 return java.util.Collections.unmodifiableList(groupedInputSpecs_);
        }
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.Builder addGroupedInputSpecsBuilder() {
 return getGroupedInputSpecsFieldBuilder().addBuilder(
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.getDefaultInstance());
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.Builder addGroupedInputSpecsBuilder(
 int index) {
 return getGroupedInputSpecsFieldBuilder().addBuilder(
 index, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.getDefaultInstance());
      }
 /**
       * <code>repeated .GroupInputSpecProto grouped_input_specs = 12;</code>
       */
 public java.util.List<org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.Builder> 
 getGroupedInputSpecsBuilderList() {
 return getGroupedInputSpecsFieldBuilder().getBuilderList();
      }
 private com.google.protobuf.RepeatedFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProtoOrBuilder> 
 getGroupedInputSpecsFieldBuilder() {
 if (groupedInputSpecsBuilder_ == null) {
 groupedInputSpecsBuilder_ = new com.google.protobuf.RepeatedFieldBuilder<
 org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProto.Builder, org.apache.hadoop.hive.llap.daemon.rpc.LlapDaemonProtocolProtos.GroupInputSpecProtoOrBuilder>(
 groupedInputSpecs_,
                  ((bitField0_ & 0x00000800) == 0x00000800),
 getParentForChildren(),
 isClean());
 groupedInputSpecs_ = null;
        }
 return groupedInputSpecsBuilder_;
      }


 // optional int32 vertex_parallelism = 13;
 private int vertexParallelism_ ;
 /**
       * <code>optional int32 vertex_parallelism = 13;</code>
       *
       * <pre>
       * An internal field required for Tez.
       * </pre>
       */
 public boolean hasVertexParallelism() {
 return ((bitField0_ & 0x00001000) == 0x00001000);
      }
 /**
       * <code>optional int32 vertex_parallelism = 13;</code>
       *
       * <pre>
       * An internal field required for Tez.
       * </pre>
       */
 public int getVertexParallelism() {
 return vertexParallelism_;
      }
 /**
       * <code>optional int32 vertex_parallelism = 13;</code>
       *
       * <pre>
       * An internal field required for Tez.
       * </pre>
       */
 public Builder setVertexParallelism(int value) {
 bitField0_ |= 0x00001000;
 vertexParallelism_ = value;
 onChanged();
 return this;
      }
 /**
       * <code>optional int32 vertex_parallelism = 13;</code>
       *
       * <pre>
       * An internal field required for Tez.
       * </pre>
       */
 public Builder clearVertexParallelism() {
 bitField0_ = (bitField0_ & ~0x00001000);
 vertexParallelism_ = 0;
 onChanged();
 return this;
      }


 // optional bool is_external_submission = 14 [default = false];
 private boolean isExternalSubmission_ ;
 /**
       * <code>optional bool is_external_submission = 14 [default = false];</code>
       */
 public boolean hasIsExternalSubmission() {
 return ((bitField0_ & 0x00002000) == 0x00002000);
      }
 /**
       * <code>optional bool is_external_submission = 14 [default = false];</code>
       */
 public boolean getIsExternalSubmission() {
 return isExternalSubmission_;
      }
 /**
       * <code>optional bool is_external_submission = 14 [default = false];</code>
       */
 public Builder setIsExternalSubmission(boolean value) {
 bitField0_ |= 0x00002000;
 isExternalSubmission_ = value;
 onChanged();
 return this;
      }
 /**
       * <code>optional bool is_external_submission = 14 [default = false];</code>
       */
 public Builder clearIsExternalSubmission() {
 bitField0_ = (bitField0_ & ~0x00002000);
 isExternalSubmission_ = false;
 onChanged();
 return this;
      }


 // @@protoc_insertion_point(builder_scope:SignableVertexSpec)
    }