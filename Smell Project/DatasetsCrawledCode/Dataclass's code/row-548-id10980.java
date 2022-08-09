 public static class Undertow {


 /**
		 * Maximum size of the HTTP post content. When the value is -1, the default, the
		 * size is unlimited.
		 */
 private DataSize maxHttpPostSize = DataSize.ofBytes(-1);


 /**
		 * Size of each buffer. The default is derived from the maximum amount of memory
		 * that is available to the JVM.
		 */
 private DataSize bufferSize;


 /**
		 * Number of I/O threads to create for the worker. The default is derived from the
		 * number of available processors.
		 */
 private Integer ioThreads;


 /**
		 * Number of worker threads. The default is 8 times the number of I/O threads.
		 */
 private Integer workerThreads;


 /**
		 * Whether to allocate buffers outside the Java heap. The default is derived from
		 * the maximum amount of memory that is available to the JVM.
		 */
 private Boolean directBuffers;


 /**
		 * Whether servlet filters should be initialized on startup.
		 */
 private boolean eagerFilterInit = true;


 private final Accesslog accesslog = new Accesslog();


 public DataSize getMaxHttpPostSize() {
 return this.maxHttpPostSize;
		}


 public void setMaxHttpPostSize(DataSize maxHttpPostSize) {
 this.maxHttpPostSize = maxHttpPostSize;
		}


 public DataSize getBufferSize() {
 return this.bufferSize;
		}


 public void setBufferSize(DataSize bufferSize) {
 this.bufferSize = bufferSize;
		}


 public Integer getIoThreads() {
 return this.ioThreads;
		}


 public void setIoThreads(Integer ioThreads) {
 this.ioThreads = ioThreads;
		}


 public Integer getWorkerThreads() {
 return this.workerThreads;
		}


 public void setWorkerThreads(Integer workerThreads) {
 this.workerThreads = workerThreads;
		}


 public Boolean getDirectBuffers() {
 return this.directBuffers;
		}


 public void setDirectBuffers(Boolean directBuffers) {
 this.directBuffers = directBuffers;
		}


 public boolean isEagerFilterInit() {
 return this.eagerFilterInit;
		}


 public void setEagerFilterInit(boolean eagerFilterInit) {
 this.eagerFilterInit = eagerFilterInit;
		}


 public Accesslog getAccesslog() {
 return this.accesslog;
		}


 /**
		 * Undertow access log properties.
		 */
 public static class Accesslog {


 /**
			 * Whether to enable the access log.
			 */
 private boolean enabled = false;


 /**
			 * Format pattern for access logs.
			 */
 private String pattern = "common";


 /**
			 * Log file name prefix.
			 */
 protected String prefix = "access_log.";


 /**
			 * Log file name suffix.
			 */
 private String suffix = "log";


 /**
			 * Undertow access log directory.
			 */
 private File dir = new File("logs");


 /**
			 * Whether to enable access log rotation.
			 */
 private boolean rotate = true;


 public boolean isEnabled() {
 return this.enabled;
			}


 public void setEnabled(boolean enabled) {
 this.enabled = enabled;
			}


 public String getPattern() {
 return this.pattern;
			}


 public void setPattern(String pattern) {
 this.pattern = pattern;
			}


 public String getPrefix() {
 return this.prefix;
			}


 public void setPrefix(String prefix) {
 this.prefix = prefix;
			}


 public String getSuffix() {
 return this.suffix;
			}


 public void setSuffix(String suffix) {
 this.suffix = suffix;
			}


 public File getDir() {
 return this.dir;
			}


 public void setDir(File dir) {
 this.dir = dir;
			}


 public boolean isRotate() {
 return this.rotate;
			}


 public void setRotate(boolean rotate) {
 this.rotate = rotate;
			}


		}


	}