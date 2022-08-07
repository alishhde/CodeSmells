 private static class createJob_resultStandardScheme extends StandardScheme<createJob_result> {


 public void read(org.apache.thrift.protocol.TProtocol iprot, createJob_result struct) throws org.apache.thrift.TException {
 org.apache.thrift.protocol.TField schemeField;
 iprot.readStructBegin();
 while (true)
        {
 schemeField = iprot.readFieldBegin();
 if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
 break;
          }
 switch (schemeField.id) {
 case 0: // SUCCESS
 if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
 struct.success = new Response();
 struct.success.read(iprot);
 struct.setSuccessIsSet(true);
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


 public void write(org.apache.thrift.protocol.TProtocol oprot, createJob_result struct) throws org.apache.thrift.TException {
 struct.validate();


 oprot.writeStructBegin(STRUCT_DESC);
 if (struct.success != null) {
 oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
 struct.success.write(oprot);
 oprot.writeFieldEnd();
        }
 oprot.writeFieldStop();
 oprot.writeStructEnd();
      }


    }