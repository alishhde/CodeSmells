 public void read(org.apache.thrift.protocol.TProtocol iprot, WMTrigger struct) throws org.apache.thrift.TException {
 org.apache.thrift.protocol.TField schemeField;
 iprot.readStructBegin();
 while (true)
      {
 schemeField = iprot.readFieldBegin();
 if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
 break;
        }
 switch (schemeField.id) {
 case 1: // RESOURCE_PLAN_NAME
 if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
 struct.resourcePlanName = iprot.readString();
 struct.setResourcePlanNameIsSet(true);
            } else { 
 org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
 break;
 case 2: // TRIGGER_NAME
 if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
 struct.triggerName = iprot.readString();
 struct.setTriggerNameIsSet(true);
            } else { 
 org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
 break;
 case 3: // TRIGGER_EXPRESSION
 if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
 struct.triggerExpression = iprot.readString();
 struct.setTriggerExpressionIsSet(true);
            } else { 
 org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
 break;
 case 4: // ACTION_EXPRESSION
 if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
 struct.actionExpression = iprot.readString();
 struct.setActionExpressionIsSet(true);
            } else { 
 org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
 break;
 case 5: // IS_IN_UNMANAGED
 if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
 struct.isInUnmanaged = iprot.readBool();
 struct.setIsInUnmanagedIsSet(true);
            } else { 
 org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
 break;
 case 6: // NS
 if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
 struct.ns = iprot.readString();
 struct.setNsIsSet(true);
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
 struct.validate();
    }