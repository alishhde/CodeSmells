 @Override
 public void onTrigger(final ProcessContext context, final ProcessSession session) {
 List<FlowFile> flowFiles = session.get(context.getProperty(BATCH_SIZE).evaluateAttributeExpressions().asInteger());
 if (flowFiles == null || flowFiles.size() == 0) {
 return;
        }


 Map<ItemKeys, FlowFile> keysToFlowFileMap = new HashMap<>();


 final String table = context.getProperty(TABLE).evaluateAttributeExpressions().getValue();


 final String hashKeyName = context.getProperty(HASH_KEY_NAME).evaluateAttributeExpressions().getValue();
 final String hashKeyValueType = context.getProperty(HASH_KEY_VALUE_TYPE).getValue();
 final String rangeKeyName = context.getProperty(RANGE_KEY_NAME).evaluateAttributeExpressions().getValue();
 final String rangeKeyValueType = context.getProperty(RANGE_KEY_VALUE_TYPE).getValue();
 final String jsonDocument = context.getProperty(JSON_DOCUMENT).evaluateAttributeExpressions().getValue();
 final String charset = context.getProperty(DOCUMENT_CHARSET).evaluateAttributeExpressions().getValue();


 TableWriteItems tableWriteItems = new TableWriteItems(table);


 for (FlowFile flowFile : flowFiles) {
 final Object hashKeyValue = getValue(context, HASH_KEY_VALUE_TYPE, HASH_KEY_VALUE, flowFile);
 final Object rangeKeyValue = getValue(context, RANGE_KEY_VALUE_TYPE, RANGE_KEY_VALUE, flowFile);


 if (!isHashKeyValueConsistent(hashKeyName, hashKeyValue, session, flowFile)) {
 continue;
            }


 if (!isRangeKeyValueConsistent(rangeKeyName, rangeKeyValue, session, flowFile)) {
 continue;
            }


 if (!isDataValid(flowFile, jsonDocument)) {
 flowFile = session.putAttribute(flowFile, AWS_DYNAMO_DB_ITEM_SIZE_ERROR, "Max size of item + attribute should be 400kb but was " + flowFile.getSize() + jsonDocument.length());
 session.transfer(flowFile, REL_FAILURE);
 continue;
            }


 ByteArrayOutputStream baos = new ByteArrayOutputStream();
 session.exportTo(flowFile, baos);


 try {
 if (rangeKeyValue == null || StringUtils.isBlank(rangeKeyValue.toString())) {
 tableWriteItems.addItemToPut(new Item().withKeyComponent(hashKeyName, hashKeyValue)
                        .withJSON(jsonDocument, IOUtils.toString(baos.toByteArray(), charset)));
                } else {
 tableWriteItems.addItemToPut(new Item().withKeyComponent(hashKeyName, hashKeyValue)
                        .withKeyComponent(rangeKeyName, rangeKeyValue)
                        .withJSON(jsonDocument, IOUtils.toString(baos.toByteArray(), charset)));
                }
            } catch (IOException ioe) {
 getLogger().error("IOException while creating put item : " + ioe.getMessage());
 flowFile = session.putAttribute(flowFile, DYNAMODB_ITEM_IO_ERROR, ioe.getMessage());
 session.transfer(flowFile, REL_FAILURE);
            }
 keysToFlowFileMap.put(new ItemKeys(hashKeyValue, rangeKeyValue), flowFile);
        }


 if (keysToFlowFileMap.isEmpty()) {
 return;
        }


 final DynamoDB dynamoDB = getDynamoDB();


 try {
 BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(tableWriteItems);


 handleUnprocessedItems(session, keysToFlowFileMap, table, hashKeyName, hashKeyValueType, rangeKeyName,
 rangeKeyValueType, outcome);


 // Handle any remaining flowfiles
 for (FlowFile flowFile : keysToFlowFileMap.values()) {
 getLogger().debug("Successful posted items to dynamodb : " + table);
 session.transfer(flowFile, REL_SUCCESS);
            }
        } catch (AmazonServiceException exception) {
 getLogger().error("Could not process flowFiles due to service exception : " + exception.getMessage());
 List<FlowFile> failedFlowFiles = processServiceException(session, flowFiles, exception);
 session.transfer(failedFlowFiles, REL_FAILURE);
        } catch (AmazonClientException exception) {
 getLogger().error("Could not process flowFiles due to client exception : " + exception.getMessage());
 List<FlowFile> failedFlowFiles = processClientException(session, flowFiles, exception);
 session.transfer(failedFlowFiles, REL_FAILURE);
        } catch (Exception exception) {
 getLogger().error("Could not process flowFiles due to exception : " + exception.getMessage());
 List<FlowFile> failedFlowFiles = processException(session, flowFiles, exception);
 session.transfer(failedFlowFiles, REL_FAILURE);
        }
    }