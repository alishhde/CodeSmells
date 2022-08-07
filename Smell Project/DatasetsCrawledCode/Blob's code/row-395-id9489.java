@Configuration
public class WebSocketSecurityConfig
 extends AbstractSecurityWebSocketMessageBrokerConfigurer {


 // @formatter:off
 @Override
 protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
 messages
			.simpMessageDestMatchers("/queue/**", "/topic/**").denyAll()
			.simpSubscribeDestMatchers("/queue/**/*-user*", "/topic/**/*-user*").denyAll()
			.anyMessage().authenticated();
	}
 // @formatter:on
}