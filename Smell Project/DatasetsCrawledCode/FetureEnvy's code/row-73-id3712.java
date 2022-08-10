 @Override
 public void addOptionValues(List<OptionValue> optionValues, Map<String, Object> context, Delegator delegator) {
 // first expand any conditions that need expanding based on the current context
 EntityCondition findCondition = null;
 if (UtilValidate.isNotEmpty(this.constraintList)) {
 List<EntityCondition> expandedConditionList = new LinkedList<>();
 for (EntityFinderUtil.Condition condition : constraintList) {
 ModelEntity modelEntity = delegator.getModelEntity(this.entityName);
 if (modelEntity == null) {
 throw new IllegalArgumentException("Error in entity-options: could not find entity [" + this.entityName
                                + "]");
                    }
 EntityCondition createdCondition = condition.createCondition(context, modelEntity,
 delegator.getModelFieldTypeReader(modelEntity));
 if (createdCondition != null) {
 expandedConditionList.add(createdCondition);
                    }
                }
 findCondition = EntityCondition.makeCondition(expandedConditionList);
            }


 try {
 Locale locale = UtilMisc.ensureLocale(context.get("locale"));
 ModelEntity modelEntity = delegator.getModelEntity(this.entityName);
 Boolean localizedOrderBy = UtilValidate.isNotEmpty(this.orderByList)
                        && ModelUtil.isPotentialLocalizedFields(modelEntity, this.orderByList);


 List<GenericValue> values = null;
 if (!localizedOrderBy) {
 values = delegator.findList(this.entityName, findCondition, null, this.orderByList, null, this.cache);
                } else {
 //if entity has localized label
 values = delegator.findList(this.entityName, findCondition, null, null, null, this.cache);
 values = EntityUtil.localizedOrderBy(values, this.orderByList, locale);
                }


 // filter-by-date if requested
 if ("true".equals(this.filterByDate)) {
 values = EntityUtil.filterByDate(values, true);
                } else if (!"false".equals(this.filterByDate)) {
 // not explicitly true or false, check to see if has fromDate and thruDate, if so do the filter
 if (modelEntity != null && modelEntity.isField("fromDate") && modelEntity.isField("thruDate")) {
 values = EntityUtil.filterByDate(values, true);
                    }
                }


 for (GenericValue value : values) {
 // add key and description with string expansion, ie expanding ${} stuff, passing locale explicitly to expand value string because it won't be found in the Entity
 MapStack<String> localContext = MapStack.create(context);
 // Rendering code might try to modify the GenericEntity instance,
 // so we make a copy of it.
 Map<String, Object> genericEntityClone = UtilGenerics.cast(value.clone());
 localContext.push(genericEntityClone);


 // expand with the new localContext, which is locale aware
 String optionDesc = this.description.expandString(localContext, locale);


 Object keyFieldObject = value.get(this.getKeyFieldName());
 if (keyFieldObject == null) {
 throw new IllegalArgumentException(
 "The entity-options identifier (from key-name attribute, or default to the field name) ["
                                        + this.getKeyFieldName() + "], may not be a valid key field name for the entity ["
                                        + this.entityName + "].");
                    }
 String keyFieldValue = keyFieldObject.toString();
 optionValues.add(new OptionValue(keyFieldValue, optionDesc));
                }
            } catch (GenericEntityException e) {
 Debug.logError(e, "Error getting entity options in form", module);
            }
        }