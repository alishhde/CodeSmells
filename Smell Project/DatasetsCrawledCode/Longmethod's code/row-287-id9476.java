 public PigServer(PigContext context, boolean connect) throws ExecException {
 this.pigContext = context;
 currDAG = new Graph(false);


 jobName = pigContext.getProperties().getProperty(
 PigContext.JOB_NAME,
 PigContext.JOB_NAME_PREFIX + ":DefaultJobName");


 if (connect) {
 pigContext.connect();
        }


 this.filter = new BlackAndWhitelistFilter(this);


 addHadoopProperties();
 addJarsFromProperties();
 markPredeployedJarsFromProperties();


 if (ScriptState.get() == null) {
 // If Pig was started via command line, ScriptState should have been
 // already initialized in Main. If so, we should not overwrite it.
 ScriptState.start(pigContext.getExecutionEngine().instantiateScriptState());
        }
 PigStats.start(pigContext.getExecutionEngine().instantiatePigStats());


 // log ATS event includes the caller context
 String auditId = PigATSClient.getPigAuditId(pigContext);
 String callerId = (String)pigContext.getProperties().get(PigConfiguration.PIG_LOG_TRACE_ID);
 log.info("Pig Script ID for the session: " + auditId);
 if (callerId != null) {
 log.info("Caller ID for session: " + callerId);
        }
 if (Boolean.parseBoolean(pigContext.getProperties()
                .getProperty(PigConfiguration.PIG_ATS_ENABLED))) {
 if (Boolean.parseBoolean(pigContext.getProperties()
                    .getProperty("yarn.timeline-service.enabled", "false"))) {
 PigATSClient.ATSEvent event = new PigATSClient.ATSEvent(auditId, callerId);
 try {
 PigATSClient.getInstance().logEvent(event);
                } catch (Exception e) {
 log.warn("Error posting to ATS: ", e);
                }
            } else {
 log.warn("ATS is disabled since"
                        + " yarn.timeline-service.enabled set to false");
            }


        }


 // set hdfs caller context
 Class callerContextClass = null;
 try {
 callerContextClass = Class.forName("org.apache.hadoop.ipc.CallerContext");
        } catch (ClassNotFoundException e) {
 // If pre-Hadoop 2.8.0, skip setting CallerContext
        }
 if (callerContextClass != null) {
 try {
 // Reflection for the following code since it is only available since hadoop 2.8.0:
 // CallerContext hdfsContext = new CallerContext.Builder(auditId).build();
 // CallerContext.setCurrent(hdfsContext);
 Class callerContextBuilderClass = Class.forName("org.apache.hadoop.ipc.CallerContext$Builder");
 Constructor callerContextBuilderConstruct = callerContextBuilderClass.getConstructor(String.class);
 Object builder = callerContextBuilderConstruct.newInstance(auditId);
 Method builderBuildMethod = builder.getClass().getMethod("build");
 Object hdfsContext = builderBuildMethod.invoke(builder);
 Method callerContextSetCurrentMethod = callerContextClass.getMethod("setCurrent", hdfsContext.getClass());
 callerContextSetCurrentMethod.invoke(callerContextClass, hdfsContext);
            } catch (Exception e) {
 // Shall not happen unless API change in future Hadoop commons
 throw new ExecException(e);
            }
        }
    }