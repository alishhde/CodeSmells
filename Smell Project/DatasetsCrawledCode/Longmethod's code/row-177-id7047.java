 protected MqttDeliveryToken restoreToken(MqttPublish message) {
 final String methodName = "restoreToken";
 MqttDeliveryToken token;
 synchronized(tokens) {
 String key = Integer.toString(message.getMessageId());
 if (this.tokens.containsKey(key)) {
 token = (MqttDeliveryToken)this.tokens.get(key);
 //@TRACE 302=existing key={0} message={1} token={2}
 log.fine(CLASS_NAME,methodName, "302",new Object[]{key, message,token});
			} else {
 token = new MqttDeliveryToken(logContext);
 token.internalTok.setKey(key);
 this.tokens.put(key, token);
 //@TRACE 303=creating new token key={0} message={1} token={2}
 log.fine(CLASS_NAME,methodName,"303",new Object[]{key, message, token});
			}
		}
 return token;
	}