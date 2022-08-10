 public Builder mergeFrom(com.google.protobuf.CodedInputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry) throws java.io.IOException {
 com.google.protobuf.UnknownFieldSet.Builder unknownFields = com.google.protobuf.UnknownFieldSet.newBuilder(this.getUnknownFields());
 while (true) {
 int tag = 0;
 try {
 tag = input.readTag();
					} catch (Exception e) {
 // do nothing
					}
 switch (tag) {
 case 0 :
 this.setUnknownFields(unknownFields.build());
 onChanged();
 return this;
 default : {
 if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
 this.setUnknownFields(unknownFields.build());
 onChanged();
 return this;
							}
 break;
						}
 case 10 : {
 bitField0_ |= 0x00000001;
 message_ = input.readBytes();
 break;
						}
 case 16 : {
 int rawValue = input.readEnum();
 org.eclipse.orion.server.cf.loggregator.LoggregatorMessage.Message.MessageType value = org.eclipse.orion.server.cf.loggregator.LoggregatorMessage.Message.MessageType.valueOf(rawValue);
 if (value == null) {
 unknownFields.mergeVarintField(2, rawValue);
							} else {
 bitField0_ |= 0x00000002;
 messageType_ = value;
							}
 break;
						}
 case 24 : {
 bitField0_ |= 0x00000004;
 timestamp_ = input.readSInt64();
 break;
						}
 case 34 : {
 bitField0_ |= 0x00000008;
 appId_ = input.readBytes();
 break;
						}
 case 50 : {
 bitField0_ |= 0x00000010;
 sourceId_ = input.readBytes();
 break;
						}
 case 58 : {
 ensureDrainUrlsIsMutable();
 drainUrls_.add(input.readBytes());
 break;
						}
 case 66 : {
 bitField0_ |= 0x00000040;
 sourceName_ = input.readBytes();
 break;
						}
					}
				}
			}