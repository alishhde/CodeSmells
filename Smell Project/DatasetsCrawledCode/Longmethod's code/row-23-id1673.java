 public void read(org.apache.thrift.protocol.TProtocol iprot, FetchRuleKeyLogsRequest struct) throws org.apache.thrift.TException {
 org.apache.thrift.protocol.TField schemeField;
 iprot.readStructBegin();
 while (true)
      {
 schemeField = iprot.readFieldBegin();
 if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
 break;
        }
 switch (schemeField.id) {
 case 1: // RULE_KEYS
 if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
 org.apache.thrift.protocol.TList _list184 = iprot.readListBegin();
 struct.ruleKeys = new java.util.ArrayList<java.lang.String>(_list184.size);
 java.lang.String _elem185;
 for (int _i186 = 0; _i186 < _list184.size; ++_i186)
                {
 _elem185 = iprot.readString();
 struct.ruleKeys.add(_elem185);
                }
 iprot.readListEnd();
              }
 struct.setRuleKeysIsSet(true);
            } else { 
 org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
 break;
 case 2: // REPOSITORY
 if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
 struct.repository = iprot.readString();
 struct.setRepositoryIsSet(true);
            } else { 
 org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
 break;
 case 3: // SCHEDULE_TYPE
 if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
 struct.scheduleType = iprot.readString();
 struct.setScheduleTypeIsSet(true);
            } else { 
 org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
 break;
 case 4: // DISTRIBUTED_BUILD_MODE_ENABLED
 if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
 struct.distributedBuildModeEnabled = iprot.readBool();
 struct.setDistributedBuildModeEnabledIsSet(true);
            } else { 
 org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
 break;
 default:
 org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
 iprot.readFieldEnd();
      }
 iprot.readStructEnd();


 // check for required fields of primitive type, which can't be checked in the validate method
 struct.validate();
    }