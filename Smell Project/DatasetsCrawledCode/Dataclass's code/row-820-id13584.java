 public static final class Builder extends
 com.google.protobuf.GeneratedMessage.Builder<Builder> implements
 // @@protoc_insertion_point(builder_implements:com.alibaba.otter.canal.protocol.RowChange)
 RowChangeOrBuilder {
 public static final com.google.protobuf.Descriptors.Descriptor
 getDescriptor() {
 return CanalEntry.internal_static_com_alibaba_otter_canal_protocol_RowChange_descriptor;
      }


 protected FieldAccessorTable
 internalGetFieldAccessorTable() {
 return CanalEntry.internal_static_com_alibaba_otter_canal_protocol_RowChange_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
 RowChange.class, Builder.class);
      }


 // Construct using com.alibaba.otter.canal.protocol.CanalEntry.RowChange.newBuilder()
 private Builder() {
 maybeForceBuilderInitialization();
      }


 private Builder(
 BuilderParent parent) {
 super(parent);
 maybeForceBuilderInitialization();
      }
 private void maybeForceBuilderInitialization() {
 if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
 getRowDatasFieldBuilder();
 getPropsFieldBuilder();
        }
      }
 private static Builder create() {
 return new Builder();
      }


 public Builder clear() {
 super.clear();
 tableId_ = 0L;
 bitField0_ = (bitField0_ & ~0x00000001);
 eventType_ = EventType.UPDATE;
 bitField0_ = (bitField0_ & ~0x00000002);
 isDdl_ = false;
 bitField0_ = (bitField0_ & ~0x00000004);
 sql_ = "";
 bitField0_ = (bitField0_ & ~0x00000008);
 if (rowDatasBuilder_ == null) {
 rowDatas_ = java.util.Collections.emptyList();
 bitField0_ = (bitField0_ & ~0x00000010);
        } else {
 rowDatasBuilder_.clear();
        }
 if (propsBuilder_ == null) {
 props_ = java.util.Collections.emptyList();
 bitField0_ = (bitField0_ & ~0x00000020);
        } else {
 propsBuilder_.clear();
        }
 ddlSchemaName_ = "";
 bitField0_ = (bitField0_ & ~0x00000040);
 return this;
      }


 public Builder clone() {
 return create().mergeFrom(buildPartial());
      }


 public com.google.protobuf.Descriptors.Descriptor
 getDescriptorForType() {
 return CanalEntry.internal_static_com_alibaba_otter_canal_protocol_RowChange_descriptor;
      }


 public RowChange getDefaultInstanceForType() {
 return RowChange.getDefaultInstance();
      }


 public RowChange build() {
 RowChange result = buildPartial();
 if (!result.isInitialized()) {
 throw newUninitializedMessageException(result);
        }
 return result;
      }


 public RowChange buildPartial() {
 RowChange result = new RowChange(this);
 int from_bitField0_ = bitField0_;
 int to_bitField0_ = 0;
 if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
 to_bitField0_ |= 0x00000001;
        }
 result.tableId_ = tableId_;
 if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
 to_bitField0_ |= 0x00000002;
        }
 result.eventType_ = eventType_;
 if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
 to_bitField0_ |= 0x00000004;
        }
 result.isDdl_ = isDdl_;
 if (((from_bitField0_ & 0x00000008) == 0x00000008)) {
 to_bitField0_ |= 0x00000008;
        }
 result.sql_ = sql_;
 if (rowDatasBuilder_ == null) {
 if (((bitField0_ & 0x00000010) == 0x00000010)) {
 rowDatas_ = java.util.Collections.unmodifiableList(rowDatas_);
 bitField0_ = (bitField0_ & ~0x00000010);
          }
 result.rowDatas_ = rowDatas_;
        } else {
 result.rowDatas_ = rowDatasBuilder_.build();
        }
 if (propsBuilder_ == null) {
 if (((bitField0_ & 0x00000020) == 0x00000020)) {
 props_ = java.util.Collections.unmodifiableList(props_);
 bitField0_ = (bitField0_ & ~0x00000020);
          }
 result.props_ = props_;
        } else {
 result.props_ = propsBuilder_.build();
        }
 if (((from_bitField0_ & 0x00000040) == 0x00000040)) {
 to_bitField0_ |= 0x00000010;
        }
 result.ddlSchemaName_ = ddlSchemaName_;
 result.bitField0_ = to_bitField0_;
 onBuilt();
 return result;
      }


 public Builder mergeFrom(com.google.protobuf.Message other) {
 if (other instanceof RowChange) {
 return mergeFrom((RowChange)other);
        } else {
 super.mergeFrom(other);
 return this;
        }
      }


 public Builder mergeFrom(RowChange other) {
 if (other == RowChange.getDefaultInstance()) return this;
 if (other.hasTableId()) {
 setTableId(other.getTableId());
        }
 if (other.hasEventType()) {
 setEventType(other.getEventType());
        }
 if (other.hasIsDdl()) {
 setIsDdl(other.getIsDdl());
        }
 if (other.hasSql()) {
 bitField0_ |= 0x00000008;
 sql_ = other.sql_;
 onChanged();
        }
 if (rowDatasBuilder_ == null) {
 if (!other.rowDatas_.isEmpty()) {
 if (rowDatas_.isEmpty()) {
 rowDatas_ = other.rowDatas_;
 bitField0_ = (bitField0_ & ~0x00000010);
            } else {
 ensureRowDatasIsMutable();
 rowDatas_.addAll(other.rowDatas_);
            }
 onChanged();
          }
        } else {
 if (!other.rowDatas_.isEmpty()) {
 if (rowDatasBuilder_.isEmpty()) {
 rowDatasBuilder_.dispose();
 rowDatasBuilder_ = null;
 rowDatas_ = other.rowDatas_;
 bitField0_ = (bitField0_ & ~0x00000010);
 rowDatasBuilder_ =
 com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
 getRowDatasFieldBuilder() : null;
            } else {
 rowDatasBuilder_.addAllMessages(other.rowDatas_);
            }
          }
        }
 if (propsBuilder_ == null) {
 if (!other.props_.isEmpty()) {
 if (props_.isEmpty()) {
 props_ = other.props_;
 bitField0_ = (bitField0_ & ~0x00000020);
            } else {
 ensurePropsIsMutable();
 props_.addAll(other.props_);
            }
 onChanged();
          }
        } else {
 if (!other.props_.isEmpty()) {
 if (propsBuilder_.isEmpty()) {
 propsBuilder_.dispose();
 propsBuilder_ = null;
 props_ = other.props_;
 bitField0_ = (bitField0_ & ~0x00000020);
 propsBuilder_ =
 com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders ?
 getPropsFieldBuilder() : null;
            } else {
 propsBuilder_.addAllMessages(other.props_);
            }
          }
        }
 if (other.hasDdlSchemaName()) {
 bitField0_ |= 0x00000040;
 ddlSchemaName_ = other.ddlSchemaName_;
 onChanged();
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
 RowChange parsedMessage = null;
 try {
 parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
 parsedMessage = (RowChange) e.getUnfinishedMessage();
 throw e;
        } finally {
 if (parsedMessage != null) {
 mergeFrom(parsedMessage);
          }
        }
 return this;
      }
 private int bitField0_;


 private long tableId_ ;
 /**
       * <code>optional int64 tableId = 1;</code>
       *
       * <pre>
