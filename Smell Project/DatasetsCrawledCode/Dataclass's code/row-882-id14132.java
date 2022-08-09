public abstract class AbstractControllerService extends AbstractConfigurableComponent implements ControllerService {


 private String identifier;
 private ControllerServiceLookup serviceLookup;
 private ComponentLog logger;
 private StateManager stateManager;
 private volatile ConfigurationContext configurationContext;
 private volatile boolean enabled = false;


 @Override
 public final void initialize(final ControllerServiceInitializationContext context) throws InitializationException {
 this.identifier = context.getIdentifier();
 serviceLookup = context.getControllerServiceLookup();
 logger = context.getLogger();
 stateManager = context.getStateManager();
 init(context);
    }


 @Override
 public String getIdentifier() {
 return identifier;
    }


 /**
     * @return the {@link ControllerServiceLookup} that was passed to the
     * {@link #init(ControllerServiceInitializationContext)} method
     */
 protected final ControllerServiceLookup getControllerServiceLookup() {
 return serviceLookup;
    }


 /**
     * Provides a mechanism by which subclasses can perform initialization of
     * the Controller Service before it is scheduled to be run
     *
     * @param config of initialization context
     * @throws InitializationException if unable to init
     */
 protected void init(final ControllerServiceInitializationContext config) throws InitializationException {
    }


 @OnEnabled
 public final void enabled() {
 this.enabled = true;
    }


 @OnDisabled
 public final void disabled() {
 this.enabled = false;
    }


 public boolean isEnabled() {
 return this.enabled;
    }


 /**
     * @return the logger that has been provided to the component by the
     * framework in its initialize method
     */
 protected ComponentLog getLogger() {
 return logger;
    }


 /**
     * @return the StateManager that can be used to store and retrieve state for this Controller Service
     */
 protected StateManager getStateManager() {
 return stateManager;
    }


 @OnEnabled
 public final void abstractStoreConfigContext(final ConfigurationContext configContext) {
 this.configurationContext = configContext;
    }


 @OnDisabled
 public final void abstractClearConfigContext() {
 this.configurationContext = null;
    }


 protected ConfigurationContext getConfigurationContext() {
 final ConfigurationContext context = this.configurationContext;
 if (context == null) {
 throw new IllegalStateException("No Configuration Context exists");
        }


 return configurationContext;
    }


 protected PropertyValue getProperty(final PropertyDescriptor descriptor) {
 return getConfigurationContext().getProperty(descriptor);
    }
}