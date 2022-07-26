@UriEndpoint(firstVersion = "2.1.0", scheme = "quickfix", title = "QuickFix", syntax = "quickfix:configurationName", label = "messaging")
public class QuickfixjEndpoint extends DefaultEndpoint implements QuickfixjEventListener, MultipleConsumersSupport {
 public static final String EVENT_CATEGORY_KEY = "EventCategory";
 public static final String SESSION_ID_KEY = "SessionID";
 public static final String MESSAGE_TYPE_KEY = "MessageType";
 public static final String DATA_DICTIONARY_KEY = "DataDictionary";


 private final QuickfixjEngine engine;
 private final List<QuickfixjConsumer> consumers = new CopyOnWriteArrayList<>();


 @UriPath @Metadata(required = true)
 private String configurationName;
 @UriParam
 private SessionID sessionID;
 @UriParam
 private boolean lazyCreateEngine;


 public QuickfixjEndpoint(QuickfixjEngine engine, String uri, Component component) {
 super(uri, component);
 this.engine = engine;
    }


 public SessionID getSessionID() {
 return sessionID;
    }


 /**
     * The optional sessionID identifies a specific FIX session. The format of the sessionID is:
     * (BeginString):(SenderCompID)[/(SenderSubID)[/(SenderLocationID)]]->(TargetCompID)[/(TargetSubID)[/(TargetLocationID)]]
     */
 public void setSessionID(SessionID sessionID) {
 this.sessionID = sessionID;
    }


 public String getConfigurationName() {
 return configurationName;
    }


 /**
     * The configFile is the name of the QuickFIX/J configuration to use for the FIX engine (located as a resource found in your classpath).
     */
 public void setConfigurationName(String configurationName) {
 this.configurationName = configurationName;
    }


 public boolean isLazyCreateEngine() {
 return lazyCreateEngine;
    }


 /**
     * This option allows to create QuickFIX/J engine on demand.
     * Value true means the engine is started when first message is send or there's consumer configured in route definition.
     * When false value is used, the engine is started at the endpoint creation.
     * When this parameter is missing, the value of component's property lazyCreateEngines is being used.
     */
 public void setLazyCreateEngine(boolean lazyCreateEngine) {
 this.lazyCreateEngine = lazyCreateEngine;
    }


 @Override
 public Consumer createConsumer(Processor processor) throws Exception {
 log.info("Creating QuickFIX/J consumer: {}, ExchangePattern={}", sessionID != null ? sessionID : "No Session", getExchangePattern());
 QuickfixjConsumer consumer = new QuickfixjConsumer(this, processor);
 configureConsumer(consumer);
 consumers.add(consumer);
 return consumer;
    }


 @Override
 public Producer createProducer() throws Exception {
 log.info("Creating QuickFIX/J producer: {}", sessionID != null ? sessionID : "No Session");
 if (isWildcarded()) {
 throw new ResolveEndpointFailedException("Cannot create consumer on wildcarded session identifier: " + sessionID);
        }
 return new QuickfixjProducer(this);
    }


 @Override
 public boolean isSingleton() {
 return true;
    }


 @Override
 public void onEvent(QuickfixjEventCategory eventCategory, SessionID sessionID, Message message) throws Exception {
 if (this.sessionID == null || isMatching(sessionID)) {
 for (QuickfixjConsumer consumer : consumers) {
 Exchange exchange = QuickfixjConverters.toExchange(this, sessionID, message, eventCategory, getExchangePattern());
 consumer.onExchange(exchange);
 if (exchange.getException() != null) {
 throw exchange.getException();
                }
            }
        }
    }


 private boolean isMatching(SessionID sessionID) {
 if (this.sessionID.equals(sessionID)) {
 return true;
        }
 return isMatching(this.sessionID.getBeginString(), sessionID.getBeginString())
            && isMatching(this.sessionID.getSenderCompID(), sessionID.getSenderCompID())
            && isMatching(this.sessionID.getSenderSubID(), sessionID.getSenderSubID())
            && isMatching(this.sessionID.getSenderLocationID(), sessionID.getSenderLocationID())
            && isMatching(this.sessionID.getTargetCompID(), sessionID.getTargetCompID())
            && isMatching(this.sessionID.getTargetSubID(), sessionID.getTargetSubID()) 
            && isMatching(this.sessionID.getTargetLocationID(), sessionID.getTargetLocationID());
    }


 private boolean isMatching(String s1, String s2) {
 return s1.equals("") || s1.equals("*") || s1.equals(s2);
    }


 private boolean isWildcarded() {
 if (sessionID == null) {
 return false;
        }
 return sessionID.getBeginString().equals("*")
            || sessionID.getSenderCompID().equals("*")
            || sessionID.getSenderSubID().equals("*")
            || sessionID.getSenderLocationID().equals("*")
            || sessionID.getTargetCompID().equals("*")
            || sessionID.getTargetSubID().equals("*")
            || sessionID.getTargetLocationID().equals("*");
    }


 @Override
 public boolean isMultipleConsumersSupported() {
 return true;
    }


 /**
     * Initializing and starts the engine if it wasn't initialized so far.
     */
 public void ensureInitialized() throws Exception {
 if (!engine.isInitialized()) {
 synchronized (engine) {
 if (!engine.isInitialized()) {
 engine.initializeEngine();
 engine.start();
                }
            }
        }
    }


 public QuickfixjEngine getEngine() {
 return engine;
    }
 
 @Override
 protected void doStop() throws Exception {
 // clear list of consumers
 consumers.clear();
    }
}