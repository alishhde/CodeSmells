@javax.annotation.Generated(value="protoc", comments="annotations:TraceInfo.java.pb.meta")
public final class TraceInfo extends
 com.google.protobuf.GeneratedMessageV3 implements
 // @@protoc_insertion_point(message_implements:facebook.remote_execution.TraceInfo)
 TraceInfoOrBuilder {
private static final long serialVersionUID = 0L;
 // Use TraceInfo.newBuilder() to construct.
 private TraceInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
 super(builder);
  }
 private TraceInfo() {
 traceId_ = "";
 edgeId_ = "";
  }


 @java.lang.Override
 public final com.google.protobuf.UnknownFieldSet
 getUnknownFields() {
 return this.unknownFields;
  }
 private TraceInfo(
 com.google.protobuf.CodedInputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws com.google.protobuf.InvalidProtocolBufferException {
 this();
 if (extensionRegistry == null) {
 throw new java.lang.NullPointerException();
    }
 int mutable_bitField0_ = 0;
 com.google.protobuf.UnknownFieldSet.Builder unknownFields =
 com.google.protobuf.UnknownFieldSet.newBuilder();
 try {
 boolean done = false;
 while (!done) {
 int tag = input.readTag();
 switch (tag) {
 case 0:
 done = true;
 break;
 case 10: {
 java.lang.String s = input.readStringRequireUtf8();


 traceId_ = s;
 break;
          }
 case 18: {
 java.lang.String s = input.readStringRequireUtf8();


 edgeId_ = s;
 break;
          }
 default: {
 if (!parseUnknownFieldProto3(
 input, unknownFields, extensionRegistry, tag)) {
 done = true;
            }
 break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
 throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
 throw new com.google.protobuf.InvalidProtocolBufferException(
 e).setUnfinishedMessage(this);
    } finally {
 this.unknownFields = unknownFields.build();
 makeExtensionsImmutable();
    }
  }
 public static final com.google.protobuf.Descriptors.Descriptor
 getDescriptor() {
 return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_TraceInfo_descriptor;
  }


 @java.lang.Override
 protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
 internalGetFieldAccessorTable() {
 return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_TraceInfo_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
 com.facebook.buck.remoteexecution.proto.TraceInfo.class, com.facebook.buck.remoteexecution.proto.TraceInfo.Builder.class);
  }


 public static final int TRACE_ID_FIELD_NUMBER = 1;
 private volatile java.lang.Object traceId_;
 /**
   * <pre>
   * ID for all the trace information corresponding to the current session.
   * </pre>
   *
   * <code>string trace_id = 1;</code>
   */
 public java.lang.String getTraceId() {
 java.lang.Object ref = traceId_;
 if (ref instanceof java.lang.String) {
 return (java.lang.String) ref;
    } else {
 com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
 java.lang.String s = bs.toStringUtf8();
 traceId_ = s;
 return s;
    }
  }
 /**
   * <pre>
   * ID for all the trace information corresponding to the current session.
   * </pre>
   *
   * <code>string trace_id = 1;</code>
   */
 public com.google.protobuf.ByteString
 getTraceIdBytes() {
 java.lang.Object ref = traceId_;
 if (ref instanceof java.lang.String) {
 com.google.protobuf.ByteString b = 
 com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
 traceId_ = b;
 return b;
    } else {
 return (com.google.protobuf.ByteString) ref;
    }
  }


 public static final int EDGE_ID_FIELD_NUMBER = 2;
 private volatile java.lang.Object edgeId_;
 /**
   * <pre>
   * ID of an edge that needs to be closed (ie, added a end point).
   * </pre>
   *
   * <code>string edge_id = 2;</code>
   */
 public java.lang.String getEdgeId() {
 java.lang.Object ref = edgeId_;
 if (ref instanceof java.lang.String) {
 return (java.lang.String) ref;
    } else {
 com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
 java.lang.String s = bs.toStringUtf8();
 edgeId_ = s;
 return s;
    }
  }
 /**
   * <pre>
   * ID of an edge that needs to be closed (ie, added a end point).
   * </pre>
   *
   * <code>string edge_id = 2;</code>
   */
 public com.google.protobuf.ByteString
 getEdgeIdBytes() {
 java.lang.Object ref = edgeId_;
 if (ref instanceof java.lang.String) {
 com.google.protobuf.ByteString b = 
 com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
 edgeId_ = b;
 return b;
    } else {
 return (com.google.protobuf.ByteString) ref;
    }
  }


 private byte memoizedIsInitialized = -1;
 @java.lang.Override
 public final boolean isInitialized() {
 byte isInitialized = memoizedIsInitialized;
 if (isInitialized == 1) return true;
 if (isInitialized == 0) return false;


 memoizedIsInitialized = 1;
 return true;
  }


 @java.lang.Override
 public void writeTo(com.google.protobuf.CodedOutputStream output)
 throws java.io.IOException {
 if (!getTraceIdBytes().isEmpty()) {
 com.google.protobuf.GeneratedMessageV3.writeString(output, 1, traceId_);
    }
 if (!getEdgeIdBytes().isEmpty()) {
 com.google.protobuf.GeneratedMessageV3.writeString(output, 2, edgeId_);
    }
 unknownFields.writeTo(output);
  }


 @java.lang.Override
 public int getSerializedSize() {
 int size = memoizedSize;
 if (size != -1) return size;


 size = 0;
 if (!getTraceIdBytes().isEmpty()) {
 size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, traceId_);
    }
 if (!getEdgeIdBytes().isEmpty()) {
 size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, edgeId_);
    }
 size += unknownFields.getSerializedSize();
 memoizedSize = size;
 return size;
  }


 @java.lang.Override
 public boolean equals(final java.lang.Object obj) {
 if (obj == this) {
 return true;
    }
 if (!(obj instanceof com.facebook.buck.remoteexecution.proto.TraceInfo)) {
 return super.equals(obj);
    }
 com.facebook.buck.remoteexecution.proto.TraceInfo other = (com.facebook.buck.remoteexecution.proto.TraceInfo) obj;


 boolean result = true;
 result = result && getTraceId()
        .equals(other.getTraceId());
 result = result && getEdgeId()
        .equals(other.getEdgeId());
 result = result && unknownFields.equals(other.unknownFields);
 return result;
  }


 @java.lang.Override
 public int hashCode() {
 if (memoizedHashCode != 0) {
 return memoizedHashCode;
    }
 int hash = 41;
 hash = (19 * hash) + getDescriptor().hashCode();
 hash = (37 * hash) + TRACE_ID_FIELD_NUMBER;
 hash = (53 * hash) + getTraceId().hashCode();
 hash = (37 * hash) + EDGE_ID_FIELD_NUMBER;
 hash = (53 * hash) + getEdgeId().hashCode();
 hash = (29 * hash) + unknownFields.hashCode();
 memoizedHashCode = hash;
 return hash;
  }


 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseFrom(
 java.nio.ByteBuffer data)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return PARSER.parseFrom(data);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseFrom(
 java.nio.ByteBuffer data,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return PARSER.parseFrom(data, extensionRegistry);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseFrom(
 com.google.protobuf.ByteString data)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return PARSER.parseFrom(data);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseFrom(
 com.google.protobuf.ByteString data,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return PARSER.parseFrom(data, extensionRegistry);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseFrom(byte[] data)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return PARSER.parseFrom(data);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseFrom(
 byte[] data,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return PARSER.parseFrom(data, extensionRegistry);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseFrom(java.io.InputStream input)
 throws java.io.IOException {
 return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseFrom(
 java.io.InputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws java.io.IOException {
 return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseDelimitedFrom(java.io.InputStream input)
 throws java.io.IOException {
 return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseDelimitedFrom(
 java.io.InputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws java.io.IOException {
 return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseFrom(
 com.google.protobuf.CodedInputStream input)
 throws java.io.IOException {
 return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
 public static com.facebook.buck.remoteexecution.proto.TraceInfo parseFrom(
 com.google.protobuf.CodedInputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws java.io.IOException {
 return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }


 @java.lang.Override
 public Builder newBuilderForType() { return newBuilder(); }
 public static Builder newBuilder() {
 return DEFAULT_INSTANCE.toBuilder();
  }
 public static Builder newBuilder(com.facebook.buck.remoteexecution.proto.TraceInfo prototype) {
 return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
 @java.lang.Override
 public Builder toBuilder() {
 return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }


 @java.lang.Override
 protected Builder newBuilderForType(
 com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
 Builder builder = new Builder(parent);
 return builder;
  }
 /**
   * <pre>
   * Contains tracing information.
   * </pre>
   *
   * Protobuf type {@code facebook.remote_execution.TraceInfo}
   */
 public static final class Builder extends
 com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
 // @@protoc_insertion_point(builder_implements:facebook.remote_execution.TraceInfo)
 com.facebook.buck.remoteexecution.proto.TraceInfoOrBuilder {
 public static final com.google.protobuf.Descriptors.Descriptor
 getDescriptor() {
 return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_TraceInfo_descriptor;
    }


 @java.lang.Override
 protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
 internalGetFieldAccessorTable() {
 return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_TraceInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
 com.facebook.buck.remoteexecution.proto.TraceInfo.class, com.facebook.buck.remoteexecution.proto.TraceInfo.Builder.class);
    }


 // Construct using com.facebook.buck.remoteexecution.proto.TraceInfo.newBuilder()
 private Builder() {
 maybeForceBuilderInitialization();
    }


 private Builder(
 com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
 super(parent);
 maybeForceBuilderInitialization();
    }
 private void maybeForceBuilderInitialization() {
 if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
 @java.lang.Override
 public Builder clear() {
 super.clear();
 traceId_ = "";


 edgeId_ = "";


 return this;
    }


 @java.lang.Override
 public com.google.protobuf.Descriptors.Descriptor
 getDescriptorForType() {
 return com.facebook.buck.remoteexecution.proto.RemoteExecutionMetadataProto.internal_static_facebook_remote_execution_TraceInfo_descriptor;
    }


 @java.lang.Override
 public com.facebook.buck.remoteexecution.proto.TraceInfo getDefaultInstanceForType() {
 return com.facebook.buck.remoteexecution.proto.TraceInfo.getDefaultInstance();
    }


 @java.lang.Override
 public com.facebook.buck.remoteexecution.proto.TraceInfo build() {
 com.facebook.buck.remoteexecution.proto.TraceInfo result = buildPartial();
 if (!result.isInitialized()) {
 throw newUninitializedMessageException(result);
      }
 return result;
    }


 @java.lang.Override
 public com.facebook.buck.remoteexecution.proto.TraceInfo buildPartial() {
 com.facebook.buck.remoteexecution.proto.TraceInfo result = new com.facebook.buck.remoteexecution.proto.TraceInfo(this);
 result.traceId_ = traceId_;
 result.edgeId_ = edgeId_;
 onBuilt();
 return result;
    }


 @java.lang.Override
 public Builder clone() {
 return (Builder) super.clone();
    }
 @java.lang.Override
 public Builder setField(
 com.google.protobuf.Descriptors.FieldDescriptor field,
 java.lang.Object value) {
 return (Builder) super.setField(field, value);
    }
 @java.lang.Override
 public Builder clearField(
 com.google.protobuf.Descriptors.FieldDescriptor field) {
 return (Builder) super.clearField(field);
    }
 @java.lang.Override
 public Builder clearOneof(
 com.google.protobuf.Descriptors.OneofDescriptor oneof) {
 return (Builder) super.clearOneof(oneof);
    }
 @java.lang.Override
 public Builder setRepeatedField(
 com.google.protobuf.Descriptors.FieldDescriptor field,
 int index, java.lang.Object value) {
 return (Builder) super.setRepeatedField(field, index, value);
    }
 @java.lang.Override
 public Builder addRepeatedField(
 com.google.protobuf.Descriptors.FieldDescriptor field,
 java.lang.Object value) {
 return (Builder) super.addRepeatedField(field, value);
    }
 @java.lang.Override
 public Builder mergeFrom(com.google.protobuf.Message other) {
 if (other instanceof com.facebook.buck.remoteexecution.proto.TraceInfo) {
 return mergeFrom((com.facebook.buck.remoteexecution.proto.TraceInfo)other);
      } else {
 super.mergeFrom(other);
 return this;
      }
    }


 public Builder mergeFrom(com.facebook.buck.remoteexecution.proto.TraceInfo other) {
 if (other == com.facebook.buck.remoteexecution.proto.TraceInfo.getDefaultInstance()) return this;
 if (!other.getTraceId().isEmpty()) {
 traceId_ = other.traceId_;
 onChanged();
      }
 if (!other.getEdgeId().isEmpty()) {
 edgeId_ = other.edgeId_;
 onChanged();
      }
 this.mergeUnknownFields(other.unknownFields);
 onChanged();
 return this;
    }


 @java.lang.Override
 public final boolean isInitialized() {
 return true;
    }


 @java.lang.Override
 public Builder mergeFrom(
 com.google.protobuf.CodedInputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws java.io.IOException {
 com.facebook.buck.remoteexecution.proto.TraceInfo parsedMessage = null;
 try {
 parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
 parsedMessage = (com.facebook.buck.remoteexecution.proto.TraceInfo) e.getUnfinishedMessage();
 throw e.unwrapIOException();
      } finally {
 if (parsedMessage != null) {
 mergeFrom(parsedMessage);
        }
      }
 return this;
    }


 private java.lang.Object traceId_ = "";
 /**
     * <pre>
     * ID for all the trace information corresponding to the current session.
     * </pre>
     *
     * <code>string trace_id = 1;</code>
     */
 public java.lang.String getTraceId() {
 java.lang.Object ref = traceId_;
 if (!(ref instanceof java.lang.String)) {
 com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
 java.lang.String s = bs.toStringUtf8();
 traceId_ = s;
 return s;
      } else {
 return (java.lang.String) ref;
      }
    }
 /**
     * <pre>
     * ID for all the trace information corresponding to the current session.
     * </pre>
     *
     * <code>string trace_id = 1;</code>
     */
 public com.google.protobuf.ByteString
 getTraceIdBytes() {
 java.lang.Object ref = traceId_;
 if (ref instanceof String) {
 com.google.protobuf.ByteString b = 
 com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
 traceId_ = b;
 return b;
      } else {
 return (com.google.protobuf.ByteString) ref;
      }
    }
 /**
     * <pre>
     * ID for all the trace information corresponding to the current session.
     * </pre>
     *
     * <code>string trace_id = 1;</code>
     */
 public Builder setTraceId(
 java.lang.String value) {
 if (value == null) {
 throw new NullPointerException();
  }
 
 traceId_ = value;
 onChanged();
 return this;
    }
 /**
     * <pre>
     * ID for all the trace information corresponding to the current session.
     * </pre>
     *
     * <code>string trace_id = 1;</code>
     */
 public Builder clearTraceId() {
 
 traceId_ = getDefaultInstance().getTraceId();
 onChanged();
 return this;
    }
 /**
     * <pre>
     * ID for all the trace information corresponding to the current session.
     * </pre>
     *
     * <code>string trace_id = 1;</code>
     */
 public Builder setTraceIdBytes(
 com.google.protobuf.ByteString value) {
 if (value == null) {
 throw new NullPointerException();
  }
 checkByteStringIsUtf8(value);
 
 traceId_ = value;
 onChanged();
 return this;
    }


 private java.lang.Object edgeId_ = "";
 /**
     * <pre>
     * ID of an edge that needs to be closed (ie, added a end point).
     * </pre>
     *
     * <code>string edge_id = 2;</code>
     */
 public java.lang.String getEdgeId() {
 java.lang.Object ref = edgeId_;
 if (!(ref instanceof java.lang.String)) {
 com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
 java.lang.String s = bs.toStringUtf8();
 edgeId_ = s;
 return s;
      } else {
 return (java.lang.String) ref;
      }
    }
 /**
     * <pre>
     * ID of an edge that needs to be closed (ie, added a end point).
     * </pre>
     *
     * <code>string edge_id = 2;</code>
     */
 public com.google.protobuf.ByteString
 getEdgeIdBytes() {
 java.lang.Object ref = edgeId_;
 if (ref instanceof String) {
 com.google.protobuf.ByteString b = 
 com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
 edgeId_ = b;
 return b;
      } else {
 return (com.google.protobuf.ByteString) ref;
      }
    }
 /**
     * <pre>
     * ID of an edge that needs to be closed (ie, added a end point).
     * </pre>
     *
     * <code>string edge_id = 2;</code>
     */
 public Builder setEdgeId(
 java.lang.String value) {
 if (value == null) {
 throw new NullPointerException();
  }
 
 edgeId_ = value;
 onChanged();
 return this;
    }
 /**
     * <pre>
     * ID of an edge that needs to be closed (ie, added a end point).
     * </pre>
     *
     * <code>string edge_id = 2;</code>
     */
 public Builder clearEdgeId() {
 
 edgeId_ = getDefaultInstance().getEdgeId();
 onChanged();
 return this;
    }
 /**
     * <pre>
     * ID of an edge that needs to be closed (ie, added a end point).
     * </pre>
     *
     * <code>string edge_id = 2;</code>
     */
 public Builder setEdgeIdBytes(
 com.google.protobuf.ByteString value) {
 if (value == null) {
 throw new NullPointerException();
  }
 checkByteStringIsUtf8(value);
 
 edgeId_ = value;
 onChanged();
 return this;
    }
 @java.lang.Override
 public final Builder setUnknownFields(
 final com.google.protobuf.UnknownFieldSet unknownFields) {
 return super.setUnknownFieldsProto3(unknownFields);
    }


 @java.lang.Override
 public final Builder mergeUnknownFields(
 final com.google.protobuf.UnknownFieldSet unknownFields) {
 return super.mergeUnknownFields(unknownFields);
    }




 // @@protoc_insertion_point(builder_scope:facebook.remote_execution.TraceInfo)
  }


 // @@protoc_insertion_point(class_scope:facebook.remote_execution.TraceInfo)
 private static final com.facebook.buck.remoteexecution.proto.TraceInfo DEFAULT_INSTANCE;
 static {
 DEFAULT_INSTANCE = new com.facebook.buck.remoteexecution.proto.TraceInfo();
  }


 public static com.facebook.buck.remoteexecution.proto.TraceInfo getDefaultInstance() {
 return DEFAULT_INSTANCE;
  }


 private static final com.google.protobuf.Parser<TraceInfo>
 PARSER = new com.google.protobuf.AbstractParser<TraceInfo>() {
 @java.lang.Override
 public TraceInfo parsePartialFrom(
 com.google.protobuf.CodedInputStream input,
 com.google.protobuf.ExtensionRegistryLite extensionRegistry)
 throws com.google.protobuf.InvalidProtocolBufferException {
 return new TraceInfo(input, extensionRegistry);
    }
  };


 public static com.google.protobuf.Parser<TraceInfo> parser() {
 return PARSER;
  }


 @java.lang.Override
 public com.google.protobuf.Parser<TraceInfo> getParserForType() {
 return PARSER;
  }


 @java.lang.Override
 public com.facebook.buck.remoteexecution.proto.TraceInfo getDefaultInstanceForType() {
 return DEFAULT_INSTANCE;
  }


}