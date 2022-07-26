 @Command(name = "launch", description = "Starts a server, optionally with applications")
 public static class LaunchCommand extends BrooklynCommandWithSystemDefines {


 @Option(name = { "--localBrooklynProperties" }, title = "local brooklyn.properties file",
 description = "Load the given properties file, specific to this launch (appending to and overriding global properties)")
 public String localBrooklynProperties;


 @Option(name = { "--noGlobalBrooklynProperties" }, title = "do not use any global brooklyn.properties file found",
 description = "Do not use the default global brooklyn.properties file found")
 public boolean noGlobalBrooklynProperties = false;


 @Option(name = { "-a", "--app" }, title = "application class or file",
 description = "The Application to start. " +
 "For example, my.AppName, file://my/app.yaml, or classpath://my/AppName.groovy -- "
                        + "note that a BROOKLYN_CLASSPATH environment variable may be required to "
                        + "load classes from other locations")
 public String app;


 @Beta
 @Option(name = { "-s", "--script" }, title = "script URI",
 description = "EXPERIMENTAL. URI for a Groovy script to parse and load." +
 " This script will run before starting the app.")
 public String script = null;


 @Option(name = { "-l", "--location", "--locations" }, title = "location list",
 description = "Specifies the locations where the application will be launched. " +
 "You can specify more than one location as a comma-separated list of values " +
 "(or as a JSON array, if the values are complex)")
 public String locations;


 @Option(name = { "--catalogInitial" }, title = "catalog initial bom URI",
 description = "Specifies a catalog.bom URI to be used to populate the initial catalog, "
                + "loaded on first run, or when persistence is off/empty or the catalog is reset")
 public String catalogInitial;


 @Option(name = { "--catalogReset" }, 
 description = "Specifies that any catalog items which have been persisted should be cleared")
 public boolean catalogReset;


 @Option(name = { "--catalogAdd" }, title = "catalog bom URI to add",
 description = "Specifies a catalog.bom to be added to the catalog")
 public String catalogAdd;


 @Option(name = { "--catalogForce" }, 
 description = "Specifies that catalog items added via the CLI should be forcibly added, "
                + "replacing any identical versions already registered (use with care!)")
 public boolean catalogForce;


 @Option(name = { "-p", "--port" }, title = "port number",
 description = "Use this port for the brooklyn management web console and REST API; "
                    + "default is 8081+ for http, 8443+ for https.")
 public String port;


 @Option(name = { "--https" },
 description = "Launch the web console on https")
 public boolean useHttps = false;
 
 @Option(name = { "-nc", "--noConsole" },
 description = "Do not start the web console or REST API")
 public boolean noConsole = false;


 @Option(name = { "-b", "--bindAddress" },
 description = "Specifies the IP address of the NIC to bind the Brooklyn Management Console to")
 public String bindAddress = null;


 @Option(name = { "-pa", "--publicAddress" },
 description = "Specifies the IP address or hostname that the Brooklyn Management Console will be available on")
 public String publicAddress = null;


 @Option(name = { "--noConsoleSecurity" },
 description = "Whether to disable authentication and security filters for the web console (for use when debugging on a secure network or bound to localhost)")
 public Boolean noConsoleSecurity = false;


 @Option(name = { "--startupContinueOnWebErrors" },
 description = "Continue on web subsystem failures during startup "
                + "(default is to abort if the web API fails to start, as management access is not normally possible)")
 public boolean startupContinueOnWebErrors = false;


 @Option(name = { "--startupFailOnPersistenceErrors" },
 description = "Fail on persistence/HA subsystem failures during startup "
                + "(default is to continue, so errors can be viewed via the API)")
 public boolean startupFailOnPersistenceErrors = false;


 @Option(name = { "--startupFailOnCatalogErrors" },
 description = "Fail on catalog subsystem failures during startup "
                + "(default is to continue, so errors can be viewed via the API)")
 public boolean startupFailOnCatalogErrors = false;


 @Option(name = { "--startupFailOnManagedAppsErrors" },
 description = "Fail startup on errors deploying of managed apps specified via the command line "
                + "(default is to continue, so errors can be viewed via the API)")
 public boolean startupFailOnManagedAppsErrors = false;


 @Beta
 @Option(name = { "--startBrooklynNode" },
 description = "Start a BrooklynNode entity representing this Brooklyn instance")
 public boolean startBrooklynNode = false;


 // Note in some cases, you can get java.util.concurrent.RejectedExecutionException
 // if shutdown is not co-ordinated, looks like: {@linktourl https://gist.github.com/47066f72d6f6f79b953e}
 @Beta
 @Option(name = { "-sk", "--stopOnKeyPress" },
 description = "Shutdown immediately on user text entry after startup (useful for debugging and demos)")
 public boolean stopOnKeyPress = false;


 final static String STOP_WHICH_APPS_ON_SHUTDOWN = "--stopOnShutdown";
 protected final static String STOP_ALL = "all";
 protected final static String STOP_ALL_IF_NOT_PERSISTED = "allIfNotPersisted";
 protected final static String STOP_NONE = "none";
 protected final static String STOP_THESE = "these";        
 protected final static String STOP_THESE_IF_NOT_PERSISTED = "theseIfNotPersisted";
 static { Enums.checkAllEnumeratedIgnoreCase(StopWhichAppsOnShutdown.class, STOP_ALL, STOP_ALL_IF_NOT_PERSISTED, STOP_NONE, STOP_THESE, STOP_THESE_IF_NOT_PERSISTED); }
 
 @Option(name = { STOP_WHICH_APPS_ON_SHUTDOWN },
 allowedValues = { STOP_ALL, STOP_ALL_IF_NOT_PERSISTED, STOP_NONE, STOP_THESE, STOP_THESE_IF_NOT_PERSISTED },
 description = "Which managed applications to stop on shutdown. Possible values are:\n"+
 "all: stop all apps\n"+
 "none: leave all apps running\n"+
 "these: stop the apps explicitly started on this command line, but leave others started subsequently running\n"+
 "theseIfNotPersisted: stop the apps started on this command line IF persistence is not enabled, otherwise leave all running\n"+
 "allIfNotPersisted: stop all apps IF persistence is not enabled, otherwise leave all running")
 public String stopWhichAppsOnShutdown = STOP_THESE_IF_NOT_PERSISTED;


 @Option(name = { "--exitAndLeaveAppsRunningAfterStarting" },
 description = "Once the application to start (from --app) is running exit the process, leaving any entities running. "
                    + "Can be used in combination with --persist auto --persistenceDir <custom folder location> to attach to the running app at a later time.")
 public boolean exitAndLeaveAppsRunningAfterStarting = false;


 final static String PERSIST_OPTION = "--persist";
 protected final static String PERSIST_OPTION_DISABLED = "disabled";
 protected final static String PERSIST_OPTION_AUTO = "auto";
 protected final static String PERSIST_OPTION_REBIND = "rebind";
 protected final static String PERSIST_OPTION_CLEAN = "clean";
 static { Enums.checkAllEnumeratedIgnoreCase(PersistMode.class, PERSIST_OPTION_DISABLED, PERSIST_OPTION_AUTO, PERSIST_OPTION_REBIND, PERSIST_OPTION_CLEAN); }
 
 // TODO currently defaults to disabled; want it to default to on, when we're ready
 // TODO how to force a line-split per option?!
 //      Looks like java.io.airlift.airline.UsagePrinter is splitting the description by word, and
 //      wrapping it automatically.
 //      See https://github.com/airlift/airline/issues/30
 @Option(name = { PERSIST_OPTION }, 
 allowedValues = { PERSIST_OPTION_DISABLED, PERSIST_OPTION_AUTO, PERSIST_OPTION_REBIND, PERSIST_OPTION_CLEAN },
 title = "persistence mode",
 description =
 "The persistence mode. Possible values are: \n"+
 "disabled: will not read or persist any state; \n"+
 "auto: will rebind to any existing state, or start up fresh if no state; \n"+
 "rebind: will rebind to the existing state, or fail if no state available; \n"+
 "clean: will start up fresh (removing any existing state)")
 public String persist = PERSIST_OPTION_DISABLED;


 @Option(name = { "--persistenceDir" }, title = "persistence dir",
 description = "The directory to read/write persisted state (or container name if using an object store)")
 public String persistenceDir;


 @Option(name = { "--persistenceLocation" }, title = "persistence location",
 description = "The location spec for an object store to read/write persisted state")
 public String persistenceLocation;


 final static String HA_OPTION = "--highAvailability";
 protected final static String HA_OPTION_DISABLED = "disabled";
 protected final static String HA_OPTION_AUTO = "auto";
 protected final static String HA_OPTION_MASTER = "master";
 protected final static String HA_OPTION_STANDBY = "standby";
 protected final static String HA_OPTION_HOT_STANDBY = "hot_standby";
 protected final static String HA_OPTION_HOT_BACKUP = "hot_backup";
 static { Enums.checkAllEnumeratedIgnoreCase(HighAvailabilityMode.class, HA_OPTION_AUTO, HA_OPTION_DISABLED, HA_OPTION_MASTER, HA_OPTION_STANDBY, HA_OPTION_HOT_STANDBY, HA_OPTION_HOT_BACKUP); }
 
 @Option(name = { HA_OPTION }, allowedValues = { HA_OPTION_DISABLED, HA_OPTION_AUTO, HA_OPTION_MASTER, HA_OPTION_STANDBY, HA_OPTION_HOT_STANDBY, HA_OPTION_HOT_BACKUP },
 title = "high availability mode",
 description =
 "The high availability mode. Possible values are: \n"+
 "disabled: management node works in isolation - will not cooperate with any other standby/master nodes in management plane; \n"+
 "auto: will look for other management nodes, and will allocate itself as standby or master based on other nodes' states; \n"+
 "master: will startup as master - if there is already a master then fails immediately; \n"+
 "standby: will start up as lukewarm standby with no state - if there is not already a master then fails immediately, "
                        + "and if there is a master which subsequently fails, this node can promote itself; \n"+
 "hot_standby: will start up as hot standby in read-only mode - if there is not already a master then fails immediately, "
                        + "and if there is a master which subseuqently fails, this node can promote itself; \n"+
 "hot_backup: will start up as hot backup in read-only mode - no master is required, and this node will not become a master"
                        )
 public String highAvailability = HA_OPTION_AUTO;


 @VisibleForTesting
 protected ManagementContext explicitManagementContext;
 
 @Override
 public Void call() throws Exception {
 super.call();
 
 // Configure launcher
 BrooklynLauncher launcher;
 AppShutdownHandler shutdownHandler = new AppShutdownHandler();
 failIfArguments();
 try {
 if (log.isDebugEnabled()) log.debug("Invoked launch command {}", this);
 
 if (!quiet) stdout.println(banner);
 
 if (verbose) {
 if (app != null) {
 stdout.println("Launching brooklyn app: " + app + " in " + locations);
                    } else {
 stdout.println("Launching brooklyn server (no app)");
                    }
                }
 
 PersistMode persistMode = computePersistMode();
 HighAvailabilityMode highAvailabilityMode = computeHighAvailabilityMode(persistMode);
 
 StopWhichAppsOnShutdown stopWhichAppsOnShutdownMode = computeStopWhichAppsOnShutdown();
 
 computeLocations();
 
 ResourceUtils utils = ResourceUtils.create(this);
 GroovyClassLoader loader = new GroovyClassLoader(getClass().getClassLoader());
 
 // First, run a setup script if the user has provided one
 if (script != null) {
 execGroovyScript(utils, loader, script);
                }
 
 launcher = createLauncher();


 CatalogInitialization catInit = new CatalogInitialization(catalogInitial, catalogReset, catalogAdd, catalogForce);
 catInit.addPopulationCallback(new Function<CatalogInitialization,Void>() {
 @Override
 public Void apply(CatalogInitialization catInit) {
 try {
 populateCatalog(catInit.getManagementContext().getCatalog());
                        } catch (Throwable e) {
 catInit.handleException(e, "overridden main class populate catalog");
                        }
 
 // Force load of catalog (so web console is up to date)
 confirmCatalog(catInit);
 return null;
                    }
                });
 catInit.setFailOnStartupErrors(startupFailOnCatalogErrors);
 launcher.catalogInitialization(catInit);
 
 launcher.persistMode(persistMode);
 launcher.persistenceDir(persistenceDir);
 launcher.persistenceLocation(persistenceLocation);


 launcher.highAvailabilityMode(highAvailabilityMode);


 launcher.stopWhichAppsOnShutdown(stopWhichAppsOnShutdownMode);
 launcher.shutdownHandler(shutdownHandler);
 
 computeAndSetApp(launcher, utils, loader);
 
 customize(launcher);
 
            } catch (FatalConfigurationRuntimeException e) {
 throw e;
            } catch (Exception e) {
 throw new FatalConfigurationRuntimeException("Fatal error configuring Brooklyn launch: "+e.getMessage(), e);
            }
 
 // Launch server
 try {
 launcher.start();
            } catch (FatalRuntimeException e) {
 // rely on caller logging this propagated exception
 throw e;
            } catch (Exception e) {
 // for other exceptions we log it, possibly redundantly but better too much than too little
 Exceptions.propagateIfFatal(e);
 log.error("Error launching brooklyn: "+Exceptions.collapseText(e), e);
 try {
 launcher.terminate();
                } catch (Exception e2) {
 log.warn("Subsequent error during termination: "+e2);
 log.debug("Details of subsequent error during termination: "+e2, e2);
                }
 Exceptions.propagate(e);
            }
 
 BrooklynServerDetails server = launcher.getServerDetails();
 ManagementContext mgmt = server.getManagementContext();
 
 if (verbose) {
 Entities.dumpInfo(launcher.getApplications());
            }
 
 if (!exitAndLeaveAppsRunningAfterStarting) {
 waitAfterLaunch(mgmt, shutdownHandler);
            }


 // do not shutdown servers here here -- 
 // the BrooklynShutdownHookJob will invoke that and others on System.exit()
 // which happens immediately after.
 // might be nice to do it explicitly here, 
 // but the server shutdown process has some special "shutdown apps" options
 // so we'd want to refactor BrooklynShutdownHookJob to share code
 
 return null;
        }


 /** can be overridden by subclasses which need to customize the launcher and/or management */
 protected void customize(BrooklynLauncher launcher) {
        }
 
 protected void computeLocations() {
 boolean hasLocations = !Strings.isBlank(locations);
 if (app != null) {
 if (hasLocations && isYamlApp()) {
 log.info("YAML app combined with command line locations; YAML locations will take precedence; this behaviour may change in subsequent versions");
                } else if (!hasLocations && isYamlApp()) {
 log.info("No locations supplied; defaulting to locations defined in YAML (if any)");
                } else if (!hasLocations) {
 log.info("No locations supplied; starting with no locations");
                }
            } else if (hasLocations) {
 log.error("Locations specified without any applications; ignoring locations");
            }
        }


 protected boolean isYamlApp() {
 return app != null && app.endsWith(".yaml");
        }


 protected PersistMode computePersistMode() {
 Maybe<PersistMode> persistMode = Enums.valueOfIgnoreCase(PersistMode.class, persist);
 if (!persistMode.isPresent()) {
 if (Strings.isBlank(persist)) {
 throw new FatalConfigurationRuntimeException("Persist mode must not be blank");
                } else {
 throw new FatalConfigurationRuntimeException("Illegal persist setting: "+persist);
                }
            }
 
 if (persistMode.get() == PersistMode.DISABLED) {
 if (Strings.isNonBlank(persistenceDir))
 throw new FatalConfigurationRuntimeException("Cannot specify persistenceDir when persist is disabled");
 if (Strings.isNonBlank(persistenceLocation))
 throw new FatalConfigurationRuntimeException("Cannot specify persistenceLocation when persist is disabled");
            }
 return persistMode.get();
        }


 protected HighAvailabilityMode computeHighAvailabilityMode(PersistMode persistMode) {
 Maybe<HighAvailabilityMode> highAvailabilityMode = Enums.valueOfIgnoreCase(HighAvailabilityMode.class, highAvailability);
 if (!highAvailabilityMode.isPresent()) {
 if (Strings.isBlank(highAvailability)) {
 throw new FatalConfigurationRuntimeException("High availability mode must not be blank");
                } else {
 throw new FatalConfigurationRuntimeException("Illegal highAvailability setting: "+highAvailability);
                }
            }
 
 if (highAvailabilityMode.get() != HighAvailabilityMode.DISABLED) {
 if (persistMode == PersistMode.DISABLED) {
 if (highAvailabilityMode.get() == HighAvailabilityMode.AUTO)
 return HighAvailabilityMode.DISABLED;
 throw new FatalConfigurationRuntimeException("Cannot specify highAvailability when persistence is disabled");
                } else if (persistMode == PersistMode.CLEAN && 
                        (highAvailabilityMode.get() == HighAvailabilityMode.STANDBY 
                        || highAvailabilityMode.get() == HighAvailabilityMode.HOT_STANDBY
                        || highAvailabilityMode.get() == HighAvailabilityMode.HOT_BACKUP)) {
 throw new FatalConfigurationRuntimeException("Cannot specify highAvailability "+highAvailabilityMode.get()+" when persistence is CLEAN");
                }
            }
 return highAvailabilityMode.get();
        }
 
 protected StopWhichAppsOnShutdown computeStopWhichAppsOnShutdown() {
 boolean isDefault = STOP_THESE_IF_NOT_PERSISTED.equals(stopWhichAppsOnShutdown);
 if (exitAndLeaveAppsRunningAfterStarting && isDefault) {
 return StopWhichAppsOnShutdown.NONE;
            } else {
 return Enums.valueOfIgnoreCase(StopWhichAppsOnShutdown.class, stopWhichAppsOnShutdown).get();
            }
        }
 
 @VisibleForTesting
 /** forces the launcher to use the given management context, when programmatically invoked;
         * mainly used when testing to inject a safe (and fast) mgmt context */
 public void useManagementContext(ManagementContext mgmt) {
 explicitManagementContext = mgmt;
        }


 protected BrooklynLauncher createLauncher() {
 BrooklynLauncher launcher;
 launcher = BrooklynLauncher.newInstance();
 launcher.localBrooklynPropertiesFile(localBrooklynProperties)
                    .ignorePersistenceErrors(!startupFailOnPersistenceErrors)
                    .ignoreCatalogErrors(!startupFailOnCatalogErrors)
                    .ignoreWebErrors(startupContinueOnWebErrors)
                    .ignoreAppErrors(!startupFailOnManagedAppsErrors)
                    .locations(Strings.isBlank(locations) ? ImmutableList.<String>of() : JavaStringEscapes.unwrapJsonishListIfPossible(locations));
 
 launcher.webconsole(!noConsole);
 if (useHttps) {
 // true sets it; false (not set) leaves it blank and falls back to config key
 // (no way currently to override config key, but that could be added)
 launcher.webconsoleHttps(useHttps);
            }
 launcher.webconsolePort(port);
 
 if (noGlobalBrooklynProperties) {
 log.debug("Configuring to disable global brooklyn.properties");
 launcher.globalBrooklynPropertiesFile(null);
            }
 if (noConsoleSecurity) {
 log.info("Configuring to disable console security");
 launcher.installSecurityFilter(false);
            }
 if (startBrooklynNode) {
 log.info("Configuring BrooklynNode entity startup");
 launcher.startBrooklynNode(true);
            }
 if (Strings.isNonEmpty(bindAddress)) {
 log.debug("Configuring bind address as "+bindAddress);
 launcher.bindAddress(Networking.getInetAddressWithFixedName(bindAddress));
            }
 if (Strings.isNonEmpty(publicAddress)) {
 log.debug("Configuring public address as "+publicAddress);
 launcher.publicAddress(Networking.getInetAddressWithFixedName(publicAddress));
            }
 if (explicitManagementContext!=null) {
 log.debug("Configuring explicit management context "+explicitManagementContext);
 launcher.managementContext(explicitManagementContext);
            }
 return launcher;
        }


 /** method intended for subclassing, to add custom items to the catalog */
 protected void populateCatalog(BrooklynCatalog catalog) {
 // nothing else added here
        }


 protected void confirmCatalog(CatalogInitialization catInit) {
 // Force load of catalog (so web console is up to date)
 Stopwatch time = Stopwatch.createStarted();
 BrooklynCatalog catalog = catInit.getManagementContext().getCatalog();
 Iterable<CatalogItem<Object, Object>> items = catalog.getCatalogItems();
 for (CatalogItem<Object, Object> item: items) {
 try {
 if (item.getCatalogItemType()==CatalogItemType.TEMPLATE) {
 // skip validation of templates, they might contain instructions,
 // and additionally they might contain multiple items in which case
 // the validation below won't work anyway (you need to go via a deployment plan)
                    } else {
 @SuppressWarnings({ "unchecked", "rawtypes" })
 Object spec = catalog.createSpec((CatalogItem)item);
 if (spec instanceof EntitySpec) {
 BrooklynTypes.getDefinedEntityType(((EntitySpec<?>)spec).getType());
                        }
 log.debug("Catalog loaded spec "+spec+" for item "+item);
                    }
                } catch (Throwable throwable) {
 catInit.handleException(throwable, item);
                }
            }
 log.debug("Catalog (size "+Iterables.size(items)+") confirmed in "+Duration.of(time));                      
 // nothing else added here
        }
 
 /** convenience for subclasses to specify that an app should run,
         * throwing the right (caught) error if another app has already been specified */
 protected void setAppToLaunch(String className) {
 if (app!=null) {
 if (app.equals(className)) return;
 throw new FatalConfigurationRuntimeException("Cannot specify app '"+className+"' when '"+app+"' is already specified; "
                    + "remove one or more conflicting CLI arguments.");
            }
 app = className;
        }
 
 protected void computeAndSetApp(BrooklynLauncher launcher, ResourceUtils utils, GroovyClassLoader loader)
 throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
 if (app != null) {
 // Create the instance of the brooklyn app
 log.debug("Loading the user's application: {}", app);
 
 if (isYamlApp()) {
 log.debug("Loading application as YAML spec: {}", app);
 String content = utils.getResourceAsString(app);
 launcher.application(content);
                } else {
 Object loadedApp = loadApplicationFromClasspathOrParse(utils, loader, app);
 if (loadedApp instanceof ApplicationBuilder) {
 launcher.application((ApplicationBuilder)loadedApp);
                    } else if (loadedApp instanceof Application) {
 launcher.application((AbstractApplication)loadedApp);
                    } else {
 throw new FatalConfigurationRuntimeException("Unexpected application type "+(loadedApp==null ? null : loadedApp.getClass())+", for app "+loadedApp);
                    }
                }
            }
        }
 
 protected void waitAfterLaunch(ManagementContext ctx, AppShutdownHandler shutdownHandler) throws IOException {
 if (stopOnKeyPress) {
 // Wait for the user to type a key
 log.info("Server started. Press return to stop.");
 // Read in another thread so we can use timeout on the wait.
 Task<Void> readTask = ctx.getExecutionManager().submit(new Callable<Void>() {
 @Override
 public Void call() throws Exception {
 stdin.read();
 return null;
                    }
                });
 while (!shutdownHandler.isRequested()) {
 try {
 readTask.get(Duration.ONE_SECOND);
 break;
                    } catch (TimeoutException e) {
 //check if there's a shutdown request
                    } catch (InterruptedException e) {
 Thread.currentThread().interrupt();
 throw Exceptions.propagate(e);
                    } catch (ExecutionException e) {
 throw Exceptions.propagate(e);
                    }
                }
 log.info("Shutting down applications.");
 stopAllApps(ctx.getApplications());
            } else {
 // Block forever so that Brooklyn doesn't exit (until someone does cntrl-c or kill)
 log.info("Launched Brooklyn; will now block until shutdown command received via GUI/API (recommended) or process interrupt.");
 shutdownHandler.waitOnShutdownRequest();
            }
        }


 protected void execGroovyScript(ResourceUtils utils, GroovyClassLoader loader, String script) {
 log.debug("Running the user provided script: {}", script);
 String content = utils.getResourceAsString(script);
 GroovyShell shell = new GroovyShell(loader);
 shell.evaluate(content);
        }


 /**
         * Helper method that gets an instance of a brooklyn {@link AbstractApplication} or an {@link ApplicationBuilder}.
         * Guaranteed to be non-null result of one of those types (throwing exception if app not appropriate).
         */
 @SuppressWarnings("unchecked")
 protected Object loadApplicationFromClasspathOrParse(ResourceUtils utils, GroovyClassLoader loader, String app)
 throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
 
 Class<?> tempclazz;
 log.debug("Loading application as class on classpath: {}", app);
 try {
 tempclazz = loader.loadClass(app, true, false);
            } catch (ClassNotFoundException cnfe) { // Not a class on the classpath
 log.debug("Loading \"{}\" as class on classpath failed, now trying as .groovy source file", app);
 String content = utils.getResourceAsString(app);
 tempclazz = loader.parseClass(content);
            }
 final Class<?> clazz = tempclazz;
 
 // Instantiate an app builder (wrapping app class in ApplicationBuilder, if necessary)
 if (ApplicationBuilder.class.isAssignableFrom(clazz)) {
 Constructor<?> constructor = clazz.getConstructor();
 return (ApplicationBuilder) constructor.newInstance();
            } else if (StartableApplication.class.isAssignableFrom(clazz)) {
 EntitySpec<? extends StartableApplication> appSpec;
 if (tempclazz.isInterface())
 appSpec = EntitySpec.create((Class<? extends StartableApplication>) clazz);
 else
 appSpec = EntitySpec.create(StartableApplication.class, (Class<? extends StartableApplication>) clazz);
 return new ApplicationBuilder(appSpec) {
 @Override protected void doBuild() {
                    }};
            } else if (AbstractApplication.class.isAssignableFrom(clazz)) {
 // TODO If this application overrides init() then in trouble, as that won't get called!
 // TODO grr; what to do about non-startable applications?
 // without this we could return ApplicationBuilder rather than Object
 Constructor<?> constructor = clazz.getConstructor();
 return (AbstractApplication) constructor.newInstance();
            } else if (AbstractEntity.class.isAssignableFrom(clazz)) {
 // TODO Should we really accept any entity type, and just wrap it in an app? That's not documented!
 return new ApplicationBuilder() {
 @Override protected void doBuild() {
 addChild(EntitySpec.create(Entity.class).impl((Class<? extends AbstractEntity>)clazz).additionalInterfaces(clazz.getInterfaces()));
                    }};
            } else if (Entity.class.isAssignableFrom(clazz)) {
 return new ApplicationBuilder() {
 @Override protected void doBuild() {
 addChild(EntitySpec.create((Class<? extends Entity>)clazz));
                    }};
            } else {
 throw new FatalConfigurationRuntimeException("Application class "+clazz+" must extend one of ApplicationBuilder or AbstractApplication");
            }
        }


 @VisibleForTesting
 protected void stopAllApps(Collection<? extends Application> applications) {
 for (Application application : applications) {
 try {
 if (application instanceof Startable) {
                        ((Startable)application).stop();
                    }
                } catch (Exception e) {
 log.error("Error stopping "+application+": "+e, e);
                }
            }
        }
 
 @Override
 public ToStringHelper string() {
 return super.string()
                    .add("app", app)
                    .add("script", script)
                    .add("location", locations)
                    .add("port", port)
                    .add("bindAddress", bindAddress)
                    .add("noConsole", noConsole)
                    .add("noConsoleSecurity", noConsoleSecurity)
                    .add("startupFailOnPersistenceErrors", startupFailOnPersistenceErrors)
                    .add("startupFailsOnCatalogErrors", startupFailOnCatalogErrors)
                    .add("startupContinueOnWebErrors", startupContinueOnWebErrors)
                    .add("startupFailOnManagedAppsErrors", startupFailOnManagedAppsErrors)
                    .add("catalogInitial", catalogInitial)
                    .add("catalogAdd", catalogAdd)
                    .add("catalogReset", catalogReset)
                    .add("catalogForce", catalogForce)
                    .add("stopWhichAppsOnShutdown", stopWhichAppsOnShutdown)
                    .add("stopOnKeyPress", stopOnKeyPress)
                    .add("localBrooklynProperties", localBrooklynProperties)
                    .add("persist", persist)
                    .add("persistenceLocation", persistenceLocation)
                    .add("persistenceDir", persistenceDir)
                    .add("highAvailability", highAvailability)
                    .add("exitAndLeaveAppsRunningAfterStarting", exitAndLeaveAppsRunningAfterStarting);
        }
    }