 private void setOptionalAttribute(BeanDefinitionBuilder builder,
 Map<String, Object> providedProperties,
 String propertyPrefix,
 String attributeValue,
 String attributeName) {
 String propertyKey;
 if ("username".equals(attributeName)) {
 String userKey = (propertyPrefix != null ?
 propertyPrefix + "user" :
 "user");
 if (providedProperties.containsKey(userKey)) {
 propertyKey = userKey;
			}
 else {
 propertyKey = (propertyPrefix != null ?
 propertyPrefix + attributeName :
 attributeName);
			}
		}
 else {
 propertyKey = (propertyPrefix != null ?
 propertyPrefix + attributeToPropertyMap.get(attributeName) :
 attributeToPropertyMap.get(attributeName));
		}


 if (StringUtils.hasText(attributeValue)) {
 if (logger.isDebugEnabled()) {
 if ("password".equals(attributeName)) {
 logger.debug("Registering optional attribute " + attributeToPropertyMap.get(attributeName) +
 " with attribute value ******");
				}
 else {
 logger.debug("Registering optional attribute " + attributeToPropertyMap.get(attributeName) +
 " with attribute value " + attributeValue);
				}
			}
 builder.addPropertyValue(attributeToPropertyMap.get(attributeName), attributeValue);
		}
 else if (providedProperties.containsKey(propertyKey)) {
 if (logger.isDebugEnabled()) {
 logger.debug("Registering optional attribute " + attributeToPropertyMap.get(attributeName) +
 " with property value " +
						("password".equals(attributeName) ? "******" : providedProperties.get(propertyKey)));
			}
 builder.addPropertyValue(attributeToPropertyMap.get(attributeName), providedProperties.get(propertyKey));
		}
 removeProvidedProperty(providedProperties, propertyKey);
	}