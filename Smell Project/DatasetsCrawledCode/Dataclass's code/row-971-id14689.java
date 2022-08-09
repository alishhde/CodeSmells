@SuppressWarnings("serial")
public abstract class MqttMessageDeliveryEvent extends MqttIntegrationEvent {


 private final int messageId;


 private final String clientId;


 private final int clientInstance;


 public MqttMessageDeliveryEvent(Object source, int messageId, String clientId, int clientInstance) {
 super(source);
 this.messageId = messageId;
 this.clientId = clientId;
 this.clientInstance = clientInstance;
	}


 public int getMessageId() {
 return this.messageId;
	}


 public String getClientId() {
 return this.clientId;
	}


 public int getClientInstance() {
 return this.clientInstance;
	}


}