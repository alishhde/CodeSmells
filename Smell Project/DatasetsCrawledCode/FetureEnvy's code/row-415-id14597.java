 @SuppressWarnings(value = "unchecked")
 private void performCommonProcessing(Operation currentOperation, KuduExecutionContext kuduExecutionContext)
  {
 currentOperation.setExternalConsistencyMode(kuduExecutionContext.getExternalConsistencyMode());
 Long propagatedTimeStamp = kuduExecutionContext.getPropagatedTimestamp();
 if ( propagatedTimeStamp != null) { // set propagation timestamp only if enabled
 currentOperation.setPropagatedTimestamp(propagatedTimeStamp);
    }
 PartialRow partialRow = currentOperation.getRow();
 Object payload = kuduExecutionContext.getPayload();
 Set<String> doNotWriteColumns = kuduExecutionContext.getDoNotWriteColumns();
 if (doNotWriteColumns == null) {
 doNotWriteColumns = new HashSet<>();
    }
 for (String columnName: kuduColumnBasedGetters.keySet()) {
 if ( doNotWriteColumns.contains(columnName)) {
 continue;
      }
 ColumnSchema columnSchema = allColumnDefs.get(columnName);
 Type dataType = columnSchema.getType();
 try {
 switch (dataType) {
 case STRING:
 PojoUtils.Getter<Object, String> stringGetter = ((PojoUtils.Getter<Object, String>)kuduColumnBasedGetters
                .get(columnName));
 if (stringGetter != null) {
 final String stringValue = stringGetter.get(payload);
 if (stringValue != null) {
 partialRow.addString(columnName, stringValue);
              }
            }
 break;
 case BINARY:
 PojoUtils.Getter<Object, ByteBuffer> byteBufferGetter = ((PojoUtils.Getter<Object, ByteBuffer>)
 kuduColumnBasedGetters.get(columnName));
 if (byteBufferGetter != null) {
 final ByteBuffer byteBufferValue = byteBufferGetter.get(payload);
 if (byteBufferValue != null) {
 partialRow.addBinary(columnName, byteBufferValue);
              }
            }
 break;
 case BOOL:
 PojoUtils.GetterBoolean<Object> boolGetter = ((PojoUtils.GetterBoolean<Object>)kuduColumnBasedGetters.get(
 columnName));
 if (boolGetter != null) {
 final boolean boolValue = boolGetter.get(payload);
 partialRow.addBoolean(columnName, boolValue);
            }
 break;
 case DOUBLE:
 PojoUtils.GetterDouble<Object> doubleGetter = ((PojoUtils.GetterDouble<Object>)kuduColumnBasedGetters.get(
 columnName));
 if (doubleGetter != null) {
 final double doubleValue = doubleGetter.get(payload);
 partialRow.addDouble(columnName, doubleValue);
            }
 break;
 case FLOAT:
 PojoUtils.GetterFloat<Object> floatGetter = ((PojoUtils.GetterFloat<Object>)kuduColumnBasedGetters.get(
 columnName));
 if (floatGetter != null) {
 final float floatValue = floatGetter.get(payload);
 partialRow.addFloat(columnName, floatValue);
            }
 break;
 case INT8:
 PojoUtils.GetterByte<Object> byteGetter = ((PojoUtils.GetterByte<Object>)kuduColumnBasedGetters.get(
 columnName));
 if (byteGetter != null) {
 final byte byteValue = byteGetter.get(payload);
 partialRow.addByte(columnName, byteValue);
            }
 break;
 case INT16:
 PojoUtils.GetterShort<Object> shortGetter = ((PojoUtils.GetterShort<Object>)kuduColumnBasedGetters.get(
 columnName));
 if (shortGetter != null) {
 final short shortValue = shortGetter.get(payload);
 partialRow.addShort(columnName, shortValue);
            }
 break;
 case INT32:
 PojoUtils.GetterInt<Object> intGetter = ((PojoUtils.GetterInt<Object>)
 kuduColumnBasedGetters.get(columnName));
 if (intGetter != null) {
 final int intValue = intGetter.get(payload);
 partialRow.addInt(columnName, intValue);
            }
 break;
 case INT64:
 case UNIXTIME_MICROS:
 PojoUtils.GetterLong<Object> longGetter = ((PojoUtils.GetterLong<Object>)kuduColumnBasedGetters.get(
 columnName));
 if (longGetter != null) {
 final long longValue = longGetter.get(payload);
 partialRow.addLong(columnName, longValue);
            }
 break;
 default:
 LOG.error(columnName + " is not of the supported data type");
 throw new UnsupportedOperationException("Kudu does not support data type for column " + columnName);
        }
      } catch ( Exception ex ) {
 LOG.error(" Exception while fetching the value of " + columnName + " because " + ex.getMessage());
 partialRow.setNull(columnName);
      }
    }
 try {
 kuduSession.apply(currentOperation);
    } catch (KuduException e) {
 throw new RuntimeException("Could not execute operation because " + e.getMessage(), e);
    }
  }