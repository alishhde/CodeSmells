public class AppService implements ApplicationService {
 
 private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);
 
 /**
	 * Create an register an application service using the default alias {@link ApplicationService#ALIAS}.
	 * @param provider Provider to create topology instances for registered applications.
	 * @param submitter Submitter for registered applications.
	 * @return Application service instance.
	 */
 public static ApplicationService createAndRegister(TopologyProvider provider, DirectSubmitter<Topology, Job> submitter) {
 
 AppService service = new AppService(provider, submitter, ALIAS);
 
 submitter.getServices().addService(ApplicationService.class, service);
 
 return service;
 
    }
 
 private final Map<String,BiConsumer<Topology, JsonObject>> applications =
 Collections.synchronizedMap(new HashMap<>());
 
 private final TopologyProvider provider;
 private final DirectSubmitter<Topology, Job> submitter;
 
 /**
     * Create an {@code ApplicationService} instance.
     * @param provider Provider to create topology instances for registered applications.
     * @param submitter Submitter for registered applications.
     * @param alias Alias used to register the control MBean.
     */
 public AppService(TopologyProvider provider,
 DirectSubmitter<Topology, Job> submitter, String alias) {
 this.provider = provider;
 this.submitter = submitter;
 
 ControlService cs = submitter.getServices().getService(ControlService.class);
 if (cs != null)
 cs.registerControl(ApplicationServiceMXBean.TYPE,
 ALIAS+System.currentTimeMillis(), alias,
 ApplicationServiceMXBean.class,
 new AppServiceControl(this));
 
    }


 @Override
 public void registerTopology(String applicationName, BiConsumer<Topology, JsonObject> builder) {
 if (applicationName == null || applicationName.isEmpty()) {
 throw new IllegalArgumentException();
        }
 logger.trace("Register application name: {}", applicationName);
 applications.put(applicationName, builder);
    }
 
 /**
     * Create a new class loader for the jar and register any
     * topology application that is registered as a service provider.
     */
 @Override
 public void registerJar(String jarURL, String jsonConfig) throws Exception {
 logger.trace("Register jar: {}", jarURL);
 
 // If it's a http URL download it otherwise use directly.
 URL url = new URL(jarURL);
 String protocol = url.getProtocol();
 if ("http".equals(protocol) || "https".equals(protocol)) {
 url = downloadJar(url);
        }
 URLClassLoader loader = new URLClassLoader(new URL[] {url});
 
 for (TopologyBuilder topoBuilder : ServiceLoader.load(TopologyBuilder.class, loader)) {
 registerTopology(topoBuilder.getName(), topoBuilder.getBuilder());
        }
    }
 
 /**
     * Download an HTTP URL to a local file.
     * @param url URL to download from.
     * @return URL of the local file.
     */
 private URL downloadJar(URL url) throws Exception {
 HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
 int responseCode = httpConn.getResponseCode();
 if (responseCode != HttpURLConnection.HTTP_OK) {
 logger.error("Error response code for URL: {} : response code={}", url.toExternalForm(), responseCode);
 throw new IOException();
        }
 
 String fileName = "";
 String disposition = httpConn.getHeaderField("Content-Disposition");


 if (disposition != null) {
 // extracts file name from header field
 int index = disposition.indexOf("filename=");
 if (index > 0) {
 fileName = disposition.substring(index + 10,
 disposition.length() - 1);
            }
        } else {
 // extracts file name from URL path
 String path = url.getPath();
 if (!path.isEmpty()) {
 int lastSlash = path.lastIndexOf("/");
 if (lastSlash == -1)
 fileName = path;
 else
 fileName = path.substring(lastSlash+1);                  
            }
        }
 // TODO - allow persistence across reboots
 // For now just store in a temp directory
 Path dir = Files.createTempDirectory("edgentjars");
 File file;
 if (fileName.isEmpty())
 file = File.createTempFile("edgent", "jar", dir.toFile());
 else
 file = new File(dir.toFile(), fileName);
 
 InputStream inputStream = httpConn.getInputStream();
 FileOutputStream outputStream = new FileOutputStream(file);


 int bytesRead;
 byte[] buffer = new byte[4096];
 while ((bytesRead = inputStream.read(buffer)) != -1) {
 outputStream.write(buffer, 0, bytesRead);
        }


 outputStream.flush();
 outputStream.close();
 inputStream.close();
 
 logger.trace("Register jar downloaded as: {}", file);
 
 return file.toURI().toURL();
    }
 
 @Override
 public Set<String> getApplicationNames() {
 synchronized (applications) {
 return new HashSet<>(applications.keySet());
        }
    }   


 BiConsumer<Topology, JsonObject> getBuilder(String applicationName) {
 return applications.get(applicationName);
    }
 
 TopologyProvider getProvider() {
 return provider;
    }
 
 DirectSubmitter<Topology, Job> getSubmitter() {
 return submitter;
    }
}