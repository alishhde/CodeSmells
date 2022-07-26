public class RemoteInterpreterEventServer implements RemoteInterpreterEventService.Iface {


 private static final Logger LOGGER = LoggerFactory.getLogger(RemoteInterpreterEventServer.class);


 private String portRange;
 private int port;
 private String host;
 private TThreadPoolServer thriftServer;
 private InterpreterSettingManager interpreterSettingManager;


 private final ScheduledExecutorService appendService =
 Executors.newSingleThreadScheduledExecutor();
 private ScheduledFuture<?> appendFuture;
 private AppendOutputRunner runner;
 private final RemoteInterpreterProcessListener listener;
 private final ApplicationEventListener appListener;
 private final Gson gson = new Gson();


 public RemoteInterpreterEventServer(ZeppelinConfiguration zConf,
 InterpreterSettingManager interpreterSettingManager) {
 this.portRange = zConf.getZeppelinServerRPCPortRange();
 this.interpreterSettingManager = interpreterSettingManager;
 this.listener = interpreterSettingManager.getRemoteInterpreterProcessListener();
 this.appListener = interpreterSettingManager.getAppEventListener();
  }


 public void start() throws IOException {
 Thread startingThread = new Thread() {
 @Override
 public void run() {
 TServerSocket tSocket = null;
 try {
 tSocket = RemoteInterpreterUtils.createTServerSocket(portRange);
 port = tSocket.getServerSocket().getLocalPort();
 host = RemoteInterpreterUtils.findAvailableHostAddress();
        } catch (IOException e1) {
 throw new RuntimeException(e1);
        }


 LOGGER.info("InterpreterEventServer is starting at {}:{}", host, port);
 RemoteInterpreterEventService.Processor processor =
 new RemoteInterpreterEventService.Processor(RemoteInterpreterEventServer.this);
 thriftServer = new TThreadPoolServer(
 new TThreadPoolServer.Args(tSocket).processor(processor));
 thriftServer.serve();
      }
    };
 startingThread.start();
 long start = System.currentTimeMillis();
 while ((System.currentTimeMillis() - start) < 30 * 1000) {
 if (thriftServer != null && thriftServer.isServing()) {
 break;
      }
 try {
 Thread.sleep(500);
      } catch (InterruptedException e) {
 throw new IOException(e);
      }
    }


 if (thriftServer != null && !thriftServer.isServing()) {
 throw new IOException("Fail to start InterpreterEventServer in 30 seconds.");
    }
 LOGGER.info("RemoteInterpreterEventServer is started");


 runner = new AppendOutputRunner(listener);
 appendFuture = appendService.scheduleWithFixedDelay(
 runner, 0, AppendOutputRunner.BUFFER_TIME_MS, TimeUnit.MILLISECONDS);
  }


 public void stop() {
 if (thriftServer != null) {
 thriftServer.stop();
    }
 if (appendFuture != null) {
 appendFuture.cancel(true);
    }
 LOGGER.info("RemoteInterpreterEventServer is stopped");
  }




 public int getPort() {
 return port;
  }


 public String getHost() {
 return host;
  }


 @Override
 public void registerInterpreterProcess(RegisterInfo registerInfo) throws TException {
 InterpreterGroup interpreterGroup =
 interpreterSettingManager.getInterpreterGroupById(registerInfo.getInterpreterGroupId());
 if (interpreterGroup == null) {
 LOGGER.warn("No such interpreterGroup: " + registerInfo.getInterpreterGroupId());
 return;
    }
 RemoteInterpreterProcess interpreterProcess =
        ((ManagedInterpreterGroup) interpreterGroup).getInterpreterProcess();
 if (interpreterProcess == null) {
 LOGGER.warn("Interpreter process does not existed yet for InterpreterGroup: " +
 registerInfo.getInterpreterGroupId());
    }


 interpreterProcess.processStarted(registerInfo.port, registerInfo.host);
  }


 @Override
 public void appendOutput(OutputAppendEvent event) throws TException {
 if (event.getAppId() == null) {
 runner.appendBuffer(
 event.getNoteId(), event.getParagraphId(), event.getIndex(), event.getData());
    } else {
 appListener.onOutputAppend(event.getNoteId(), event.getParagraphId(), event.getIndex(),
 event.getAppId(), event.getData());
    }
  }


 @Override
 public void updateOutput(OutputUpdateEvent event) throws TException {
 if (event.getAppId() == null) {
 listener.onOutputUpdated(event.getNoteId(), event.getParagraphId(), event.getIndex(),
 InterpreterResult.Type.valueOf(event.getType()), event.getData());
    } else {
 appListener.onOutputUpdated(event.getNoteId(), event.getParagraphId(), event.getIndex(),
 event.getAppId(), InterpreterResult.Type.valueOf(event.getType()), event.getData());
    }
  }


 @Override
 public void updateAllOutput(OutputUpdateAllEvent event) throws TException {
 listener.onOutputClear(event.getNoteId(), event.getParagraphId());
 for (int i = 0; i < event.getMsg().size(); i++) {
 RemoteInterpreterResultMessage msg = event.getMsg().get(i);
 listener.onOutputUpdated(event.getNoteId(), event.getParagraphId(), i,
 InterpreterResult.Type.valueOf(msg.getType()), msg.getData());
    }
  }


 @Override
 public void appendAppOutput(AppOutputAppendEvent event) throws TException {
 appListener.onOutputAppend(event.noteId, event.paragraphId, event.index, event.appId,
 event.data);
  }


 @Override
 public void updateAppOutput(AppOutputUpdateEvent event) throws TException {
 appListener.onOutputUpdated(event.noteId, event.paragraphId, event.index, event.appId,
 InterpreterResult.Type.valueOf(event.type), event.data);
  }


 @Override
 public void updateAppStatus(AppStatusUpdateEvent event) throws TException {
 appListener.onStatusChange(event.noteId, event.paragraphId, event.appId, event.status);
  }


 @Override
 public void runParagraphs(RunParagraphsEvent event) throws TException {
 try {
 listener.runParagraphs(event.getNoteId(), event.getParagraphIndices(),
 event.getParagraphIds(), event.getCurParagraphId());
 if (InterpreterContext.get() != null) {
 LOGGER.info("complete runParagraphs." + InterpreterContext.get().getParagraphId() + " "
          + event);
      } else {
 LOGGER.info("complete runParagraphs." + event);
      }
    } catch (IOException e) {
 throw new TException(e);
    }
  }


 @Override
 public void addAngularObject(String intpGroupId, String json) throws TException {
 LOGGER.debug("Add AngularObject, interpreterGroupId: " + intpGroupId + ", json: " + json);
 AngularObject angularObject = AngularObject.fromJson(json);
 InterpreterGroup interpreterGroup =
 interpreterSettingManager.getInterpreterGroupById(intpGroupId);
 if (interpreterGroup == null) {
 throw new TException("Invalid InterpreterGroupId: " + intpGroupId);
    }
 interpreterGroup.getAngularObjectRegistry().add(angularObject.getName(),
 angularObject.get(), angularObject.getNoteId(), angularObject.getParagraphId());
  }


 @Override
 public void updateAngularObject(String intpGroupId, String json) throws TException {
 AngularObject angularObject = AngularObject.fromJson(json);
 InterpreterGroup interpreterGroup =
 interpreterSettingManager.getInterpreterGroupById(intpGroupId);
 if (interpreterGroup == null) {
 throw new TException("Invalid InterpreterGroupId: " + intpGroupId);
    }
 AngularObject localAngularObject = interpreterGroup.getAngularObjectRegistry().get(
 angularObject.getName(), angularObject.getNoteId(), angularObject.getParagraphId());
 if (localAngularObject instanceof RemoteAngularObject) {
 // to avoid ping-pong loop
      ((RemoteAngularObject) localAngularObject).set(
 angularObject.get(), true, false);
    } else {
 localAngularObject.set(angularObject.get());
    }
  }


 @Override
 public void removeAngularObject(String intpGroupId,
 String noteId,
 String paragraphId,
 String name) throws TException {
 InterpreterGroup interpreterGroup =
 interpreterSettingManager.getInterpreterGroupById(intpGroupId);
 if (interpreterGroup == null) {
 throw new TException("Invalid InterpreterGroupId: " + intpGroupId);
    }
 interpreterGroup.getAngularObjectRegistry().remove(name, noteId, paragraphId);
  }


 @Override
 public void sendParagraphInfo(String intpGroupId, String json) throws TException {
 InterpreterGroup interpreterGroup =
 interpreterSettingManager.getInterpreterGroupById(intpGroupId);
 if (interpreterGroup == null) {
 throw new TException("Invalid InterpreterGroupId: " + intpGroupId);
    }


 Map<String, String> paraInfos = gson.fromJson(json,
 new TypeToken<Map<String, String>>() {
        }.getType());
 String noteId = paraInfos.get("noteId");
 String paraId = paraInfos.get("paraId");
 String settingId = RemoteInterpreterUtils.
 getInterpreterSettingId(interpreterGroup.getId());
 if (noteId != null && paraId != null && settingId != null) {
 listener.onParaInfosReceived(noteId, paraId, settingId, paraInfos);
    }
  }


 @Override
 public List<String> getAllResources(String intpGroupId) throws TException {
 ResourceSet resourceSet = getAllResourcePoolExcept(intpGroupId);
 List<String> resourceList = new LinkedList<>();
 for (Resource r : resourceSet) {
 resourceList.add(r.toJson());
    }
 return resourceList;
  }


 @Override
 public ByteBuffer getResource(String resourceIdJson) throws TException {
 ResourceId resourceId = ResourceId.fromJson(resourceIdJson);
 Object o = getResource(resourceId);
 ByteBuffer obj;
 if (o == null) {
 obj = ByteBuffer.allocate(0);
    } else {
 try {
 obj = Resource.serializeObject(o);
      } catch (IOException e) {
 throw new TException(e);
      }
    }
 return obj;
  }


 /**
   *
   * @param intpGroupId caller interpreter group id
   * @param invokeMethodJson invoke information
   * @return
   * @throws TException
   */
 @Override
 public ByteBuffer invokeMethod(String intpGroupId, String invokeMethodJson) throws TException {
 InvokeResourceMethodEventMessage invokeMethodMessage =
 InvokeResourceMethodEventMessage.fromJson(invokeMethodJson);
 Object ret = invokeResourceMethod(intpGroupId, invokeMethodMessage);
 ByteBuffer obj = null;
 if (ret == null) {
 obj = ByteBuffer.allocate(0);
    } else {
 try {
 obj = Resource.serializeObject(ret);
      } catch (IOException e) {
 LOGGER.error("invokeMethod failed", e);
      }
    }
 return obj;
  }


 @Override
 public List<ParagraphInfo> getParagraphList(String user, String noteId)
 throws TException, ServiceException {
 LOGGER.info("get paragraph list from remote interpreter noteId: " + noteId
        + ", user = " + user);


 if (user != null && noteId != null) {
 List<ParagraphInfo> paragraphInfos = listener.getParagraphList(user, noteId);
 return paragraphInfos;
    } else {
 LOGGER.error("user or noteId is null!");
 return null;
    }
  }


 private Object invokeResourceMethod(String intpGroupId,
 final InvokeResourceMethodEventMessage message) {
 final ResourceId resourceId = message.resourceId;
 ManagedInterpreterGroup intpGroup =
 interpreterSettingManager.getInterpreterGroupById(resourceId.getResourcePoolId());
 if (intpGroup == null) {
 return null;
    }


 RemoteInterpreterProcess remoteInterpreterProcess = intpGroup.getRemoteInterpreterProcess();
 if (remoteInterpreterProcess == null) {
 ResourcePool localPool = intpGroup.getResourcePool();
 if (localPool != null) {
 Resource res = localPool.get(resourceId.getName());
 if (res != null) {
 try {
 return res.invokeMethod(
 message.methodName,
 message.getParamTypes(),
 message.params,
 message.returnResourceName);
          } catch (Exception e) {
 LOGGER.error(e.getMessage(), e);
 return null;
          }
        } else {
 // object is null. can't invoke any method
 LOGGER.error("Can't invoke method {} on null object", message.methodName);
 return null;
        }
      } else {
 LOGGER.error("no resource pool");
 return null;
      }
    } else if (remoteInterpreterProcess.isRunning()) {
 ByteBuffer res = remoteInterpreterProcess.callRemoteFunction(
 new RemoteInterpreterProcess.RemoteFunction<ByteBuffer>() {
 @Override
 public ByteBuffer call(RemoteInterpreterService.Client client) throws Exception {
 return client.resourceInvokeMethod(
 resourceId.getNoteId(),
 resourceId.getParagraphId(),
 resourceId.getName(),
 message.toJson());
            }
          }
      );


 try {
 return Resource.deserializeObject(res);
      } catch (Exception e) {
 LOGGER.error(e.getMessage(), e);
      }
 return null;
    }
 return null;
  }


 private Object getResource(final ResourceId resourceId) {
 ManagedInterpreterGroup intpGroup = interpreterSettingManager
        .getInterpreterGroupById(resourceId.getResourcePoolId());
 if (intpGroup == null) {
 return null;
    }
 RemoteInterpreterProcess remoteInterpreterProcess = intpGroup.getRemoteInterpreterProcess();
 ByteBuffer buffer = remoteInterpreterProcess.callRemoteFunction(
 new RemoteInterpreterProcess.RemoteFunction<ByteBuffer>() {
 @Override
 public ByteBuffer call(RemoteInterpreterService.Client client) throws Exception {
 return client.resourceGet(
 resourceId.getNoteId(),
 resourceId.getParagraphId(),
 resourceId.getName());
          }
        }
    );


 try {
 Object o = Resource.deserializeObject(buffer);
 return o;
    } catch (Exception e) {
 LOGGER.error(e.getMessage(), e);
    }
 return null;
  }


 private ResourceSet getAllResourcePoolExcept(String interpreterGroupId) {
 ResourceSet resourceSet = new ResourceSet();
 for (ManagedInterpreterGroup intpGroup : interpreterSettingManager.getAllInterpreterGroup()) {
 if (intpGroup.getId().equals(interpreterGroupId)) {
 continue;
      }


 RemoteInterpreterProcess remoteInterpreterProcess = intpGroup.getRemoteInterpreterProcess();
 if (remoteInterpreterProcess == null) {
 ResourcePool localPool = intpGroup.getResourcePool();
 if (localPool != null) {
 resourceSet.addAll(localPool.getAll());
        }
      } else if (remoteInterpreterProcess.isRunning()) {
 List<String> resourceList = remoteInterpreterProcess.callRemoteFunction(
 new RemoteInterpreterProcess.RemoteFunction<List<String>>() {
 @Override
 public List<String> call(RemoteInterpreterService.Client client) throws Exception {
 return client.resourcePoolGetAll();
              }
            }
        );
 for (String res : resourceList) {
 resourceSet.add(RemoteResource.fromJson(res));
        }
      }
    }
 return resourceSet;
  }
}