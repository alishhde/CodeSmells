 public SocketServer(NetworkConfig config, SSLConfig sslConfig, MetricRegistry registry, ArrayList<Port> portList) {
 this.host = config.hostName;
 this.port = config.port;
 this.numProcessorThreads = config.numIoThreads;
 this.maxQueuedRequests = config.queuedMaxRequests;
 this.sendBufferSize = config.socketSendBufferBytes;
 this.recvBufferSize = config.socketReceiveBufferBytes;
 this.maxRequestSize = config.socketRequestMaxBytes;
 processors = new ArrayList<Processor>(numProcessorThreads);
 requestResponseChannel = new SocketRequestResponseChannel(numProcessorThreads, maxQueuedRequests);
 metrics = new ServerNetworkMetrics(requestResponseChannel, registry, processors);
 this.acceptors = new ArrayList<Acceptor>();
 this.ports = new HashMap<PortType, Port>();
 this.validatePorts(portList);
 this.initializeSSLFactory(sslConfig);
  }