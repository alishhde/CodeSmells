public class ServletContextListenerStartup implements ServletContextListener {
 /** Configuration file path parameter name. */
 public static final String IGNITE_CFG_FILE_PATH_PARAM = "IgniteConfigurationFilePath";


 /** Names of started Ignite instances. */
 private final Collection<String> igniteInstanceNames = new ArrayList<>();


 /** {@inheritDoc} */
 @Override public void contextInitialized(ServletContextEvent evt) {
 ServletContext ctx = evt.getServletContext();


 String cfgFile = ctx.getInitParameter(IGNITE_CFG_FILE_PATH_PARAM);


 Collection<IgniteConfiguration> cfgs;
 GridSpringResourceContext rsrcCtx = null;


 if (cfgFile != null) {
 URL cfgUrl = null;


 try {
 cfgUrl = evt.getServletContext().getResource("/META-INF/" + cfgFile);
            }
 catch (MalformedURLException ignored) {
 // Ignore, we still need to try with IGNITE_HOME.
            }


 if (cfgUrl == null)
 // Try with IGNITE_HOME and with context class loader.
 cfgUrl = U.resolveIgniteUrl(cfgFile);


 if (cfgUrl == null)
 throw new IgniteException("Failed to find Spring configuration file (path provided should be " +
 "either absolute, relative to IGNITE_HOME, or relative to META-INF folder): " + cfgFile);


 IgniteBiTuple<Collection<IgniteConfiguration>, ? extends GridSpringResourceContext> t;


 try {
 t = IgnitionEx.loadConfigurations(cfgUrl);
            }
 catch (IgniteCheckedException e) {
 throw new IgniteException("Failed to load Ignite configuration.", e);
            }


 cfgs = t.get1();
 rsrcCtx  = t.get2();


 if (cfgs.isEmpty())
 throw new IgniteException("Can't find grid factory configuration in: " + cfgUrl);
        }
 else
 cfgs = Collections.<IgniteConfiguration>singleton(new IgniteConfiguration());


 try {
 assert !cfgs.isEmpty();


 for (IgniteConfiguration cfg : cfgs) {
 assert cfg != null;


 Ignite ignite;


 synchronized (ServletContextListenerStartup.class) {
 try {
 ignite = G.ignite(cfg.getIgniteInstanceName());
                    }
 catch (IgniteIllegalStateException ignored) {
 ignite = IgnitionEx.start(new IgniteConfiguration(cfg), rsrcCtx);
                    }
                }


 // Check if grid is not null - started properly.
 if (ignite != null)
 igniteInstanceNames.add(ignite.name());
            }
        }
 catch (IgniteCheckedException e) {
 // Stop started grids only.
 for (String name : igniteInstanceNames)
 G.stop(name, true);


 throw new IgniteException("Failed to start Ignite.", e);
        }
    }


 /** {@inheritDoc} */
 @Override public void contextDestroyed(ServletContextEvent evt) {
 // Stop started grids only.
 for (String name: igniteInstanceNames)
 G.stop(name, true);
    }


 /** {@inheritDoc} */
 @Override public String toString() {
 return S.toString(ServletContextListenerStartup.class, this);
    }
}