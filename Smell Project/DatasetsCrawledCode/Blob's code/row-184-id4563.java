@SuppressWarnings({ "unchecked", "rawtypes" })
public final class CorePlugin extends Plugin {


 public static final String PLUGIN_ID = "org.eclipse.buildship.core"; //$NON-NLS-1$


 public static final String GRADLE_JOB_FAMILY = PLUGIN_ID + ".jobs";


 private static CorePlugin plugin;


 // do not use generics-aware signature since this causes compilation troubles (JDK, Spock)
 // search the web for -target jsr14 to find out more about this obscurity
 private ServiceRegistration loggerService;
 private ServiceRegistration publishedGradleVersionsService;
 private ServiceRegistration workspaceOperationsService;
 private ServiceRegistration internalGradleWorkspaceService;
 private ServiceRegistration processStreamsProviderService;
 private ServiceRegistration gradleLaunchConfigurationService;
 private ServiceRegistration listenerRegistryService;


 // service tracker for each service to allow to register other service implementations of the
 // same type but with higher prioritization, useful for testing
 private ServiceTracker loggerServiceTracker;
 private ServiceTracker publishedGradleVersionsServiceTracker;
 private ServiceTracker workspaceOperationsServiceTracker;
 private ServiceTracker internalGradleWorkspaceServiceTracker;
 private ServiceTracker processStreamsProviderServiceTracker;
 private ServiceTracker gradleLaunchConfigurationServiceTracker;
 private ServiceTracker listenerRegistryServiceTracker;


 private DefaultModelPersistence modelPersistence;
 private ProjectChangeListener projectChangeListener;
 private SynchronizingBuildScriptUpdateListener buildScriptUpdateListener;
 private InvocationCustomizer invocationCustomizer;
 private ConfigurationManager configurationManager;
 private DefaultExternalLaunchConfigurationManager externalLaunchConfigurationManager;
 private ToolingApiOperationManager operationManager;
 private ExtensionManager extensionManager;


 @Override
 public void start(BundleContext bundleContext) throws Exception {
 super.start(bundleContext);
 plugin = this;
 ensureProxySettingsApplied();
 registerServices(bundleContext);
    }


 @Override
 public void stop(BundleContext context) throws Exception {
 unregisterServices();
 plugin = null;
 super.stop(context);
    }


 private void ensureProxySettingsApplied() throws Exception {
 // the proxy settings are set when the core.net plugin is started
 Platform.getBundle("org.eclipse.core.net").start(Bundle.START_TRANSIENT);
    }


 private void registerServices(BundleContext context) {
 // store services with low ranking such that they can be overridden
 // during testing or the like
 Dictionary<String, Object> preferences = new Hashtable<>();
 preferences.put(Constants.SERVICE_RANKING, 1);


 // initialize service trackers before the services are created
 this.loggerServiceTracker = createServiceTracker(context, Logger.class);
 this.publishedGradleVersionsServiceTracker = createServiceTracker(context, PublishedGradleVersionsWrapper.class);
 this.workspaceOperationsServiceTracker = createServiceTracker(context, WorkspaceOperations.class);
 this.internalGradleWorkspaceServiceTracker = createServiceTracker(context, InternalGradleWorkspace.class);
 this.processStreamsProviderServiceTracker = createServiceTracker(context, ProcessStreamsProvider.class);
 this.gradleLaunchConfigurationServiceTracker = createServiceTracker(context, GradleLaunchConfigurationManager.class);
 this.listenerRegistryServiceTracker = createServiceTracker(context, ListenerRegistry.class);


 // register all services
 this.loggerService = registerService(context, Logger.class, createLogger(), preferences);
 this.publishedGradleVersionsService = registerService(context, PublishedGradleVersionsWrapper.class, createPublishedGradleVersions(), preferences);
 this.workspaceOperationsService = registerService(context, WorkspaceOperations.class, createWorkspaceOperations(), preferences);
 this.internalGradleWorkspaceService = registerService(context, InternalGradleWorkspace.class, createGradleWorkspace(), preferences);
 this.processStreamsProviderService = registerService(context, ProcessStreamsProvider.class, createProcessStreamsProvider(), preferences);
 this.gradleLaunchConfigurationService = registerService(context, GradleLaunchConfigurationManager.class, createGradleLaunchConfigurationManager(), preferences);
 this.listenerRegistryService = registerService(context, ListenerRegistry.class, createListenerRegistry(), preferences);


 this.modelPersistence = DefaultModelPersistence.createAndRegister();
 this.projectChangeListener = ProjectChangeListener.createAndRegister();
 this.buildScriptUpdateListener = SynchronizingBuildScriptUpdateListener.createAndRegister();
 this.invocationCustomizer = new InvocationCustomizerCollector();
 this.configurationManager = new DefaultConfigurationManager();
 this.externalLaunchConfigurationManager = DefaultExternalLaunchConfigurationManager.createAndRegister();
 this.operationManager = new DefaultToolingApiOperationManager();
 this.extensionManager = new DefaultExtensionManager();
    }


 private ServiceTracker createServiceTracker(BundleContext context, Class<?> clazz) {
 ServiceTracker serviceTracker = new ServiceTracker(context, clazz.getName(), null);
 serviceTracker.open();
 return serviceTracker;
    }


 private <T> ServiceRegistration registerService(BundleContext context, Class<T> clazz, T service, Dictionary<String, Object> properties) {
 return context.registerService(clazz.getName(), service, properties);
    }


 private EclipseLogger createLogger() {
 Map<TraceScope, Boolean> tracingEnablement = Maps.newHashMap();
 for (TraceScope scope : CoreTraceScopes.values()) {
 String option = Platform.getDebugOption("org.eclipse.buildship.core/trace/" + scope.getScopeKey());
 tracingEnablement.put(scope, "true".equalsIgnoreCase(option));
        }
 return new EclipseLogger(getLog(), PLUGIN_ID, tracingEnablement);
    }


 private PublishedGradleVersionsWrapper createPublishedGradleVersions() {
 return new PublishedGradleVersionsWrapper();
    }


 private WorkspaceOperations createWorkspaceOperations() {
 return new DefaultWorkspaceOperations();
    }


 private InternalGradleWorkspace createGradleWorkspace() {
 return new DefaultGradleWorkspace();
    }


 private ProcessStreamsProvider createProcessStreamsProvider() {
 return new StdProcessStreamsProvider();
    }


 private GradleLaunchConfigurationManager createGradleLaunchConfigurationManager() {
 return new DefaultGradleLaunchConfigurationManager();
    }


 private ListenerRegistry createListenerRegistry() {
 return new DefaultListenerRegistry();
    }


 private void unregisterServices() {
 this.externalLaunchConfigurationManager.unregister();
 this.buildScriptUpdateListener.close();
 this.projectChangeListener.close();
 this.modelPersistence.close();
 this.listenerRegistryService.unregister();
 this.gradleLaunchConfigurationService.unregister();
 this.processStreamsProviderService.unregister();
 this.internalGradleWorkspaceService.unregister();
 this.workspaceOperationsService.unregister();
 this.publishedGradleVersionsService.unregister();
 this.loggerService.unregister();


 this.listenerRegistryServiceTracker.close();
 this.gradleLaunchConfigurationServiceTracker.close();
 this.processStreamsProviderServiceTracker.close();
 this.internalGradleWorkspaceServiceTracker.close();
 this.workspaceOperationsServiceTracker.close();
 this.publishedGradleVersionsServiceTracker.close();
 this.loggerServiceTracker.close();
    }


 public static CorePlugin getInstance() {
 return plugin;
    }


 public static Logger logger() {
 return (Logger) getInstance().loggerServiceTracker.getService();
    }


 public static PublishedGradleVersionsWrapper publishedGradleVersions() {
 return (PublishedGradleVersionsWrapper) getInstance().publishedGradleVersionsServiceTracker.getService();
    }


 public static WorkspaceOperations workspaceOperations() {
 return (WorkspaceOperations) getInstance().workspaceOperationsServiceTracker.getService();
    }


 public static InternalGradleWorkspace internalGradleWorkspace() {
 return (InternalGradleWorkspace) getInstance().internalGradleWorkspaceServiceTracker.getService();
    }


 public static ProcessStreamsProvider processStreamsProvider() {
 return (ProcessStreamsProvider) getInstance().processStreamsProviderServiceTracker.getService();
    }


 public static GradleLaunchConfigurationManager gradleLaunchConfigurationManager() {
 return (GradleLaunchConfigurationManager) getInstance().gradleLaunchConfigurationServiceTracker.getService();
    }


 public static ListenerRegistry listenerRegistry() {
 return (ListenerRegistry) getInstance().listenerRegistryServiceTracker.getService();
    }


 public static ModelPersistence modelPersistence() {
 return getInstance().modelPersistence;
    }


 public static InvocationCustomizer invocationCustomizer() {
 return getInstance().invocationCustomizer;
    }


 public static ConfigurationManager configurationManager() {
 return getInstance().configurationManager;
    }


 public static ExternalLaunchConfigurationManager externalLaunchConfigurationManager() {
 return getInstance().externalLaunchConfigurationManager;
    }


 public static ToolingApiOperationManager operationManager() {
 return getInstance().operationManager;
    }


 public static ExtensionManager extensionManager() {
 return getInstance().extensionManager;
    }
}