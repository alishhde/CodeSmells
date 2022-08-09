public class RabbitGatewaySupport implements InitializingBean {


 /** Logger available to subclasses. */
 protected final Log logger = LogFactory.getLog(getClass()); // NOSONAR


 private RabbitOperations rabbitOperations;


 /**
	 * Set the Rabbit connection factory to be used by the gateway.
	 * Will automatically create a RabbitTemplate for the given ConnectionFactory.
	 * @param connectionFactory The connection factory.
	 * @see #createRabbitTemplate
	 * @see #setConnectionFactory(org.springframework.amqp.rabbit.connection.ConnectionFactory)
	 */
 public final void setConnectionFactory(ConnectionFactory connectionFactory) {
 this.rabbitOperations = createRabbitTemplate(connectionFactory);
	}


 /**
	 * Create a RabbitTemplate for the given ConnectionFactory.
	 * Only invoked if populating the gateway with a ConnectionFactory reference.
	 *
	 * @param connectionFactory the Rabbit ConnectionFactory to create a RabbitTemplate for
	 * @return the new RabbitTemplate instance
	 * @see #setConnectionFactory
	 */
 protected RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
 return new RabbitTemplate(connectionFactory);
	}


 /**
	 * @return The Rabbit ConnectionFactory used by the gateway.
	 */
 @Nullable
 public final ConnectionFactory getConnectionFactory() {
 return (this.rabbitOperations != null ? this.rabbitOperations.getConnectionFactory() : null);
	}


 /**
	 * Set the {@link RabbitOperations} for the gateway.
	 * @param rabbitOperations The Rabbit operations.
	 * @see #setConnectionFactory(org.springframework.amqp.rabbit.connection.ConnectionFactory)
	 */
 public final void setRabbitOperations(RabbitOperations rabbitOperations) {
 this.rabbitOperations = rabbitOperations;
	}


 /**
	 * @return The {@link RabbitOperations} for the gateway.
	 */
 public final RabbitOperations getRabbitOperations() {
 return this.rabbitOperations;
	}


 @Override
 public final void afterPropertiesSet() throws IllegalArgumentException, BeanInitializationException {
 if (this.rabbitOperations == null) {
 throw new IllegalArgumentException("'connectionFactory' or 'rabbitTemplate' is required");
		}
 try {
 initGateway();
		}
 catch (Exception ex) {
 throw new BeanInitializationException("Initialization of Rabbit gateway failed: " + ex.getMessage(), ex);
		}
	}


 /**
	 * Subclasses can override this for custom initialization behavior.
	 * Gets called after population of this instance's bean properties.
	 */
 protected void initGateway() {
	}


}