 public static class Builder {
 private String omServiceId;
 private String omNodeId;
 private InetSocketAddress rpcAddress;
 private int rpcPort;
 private int ratisPort;


 public Builder setRpcAddress(InetSocketAddress rpcAddr) {
 this.rpcAddress = rpcAddr;
 this.rpcPort = rpcAddress.getPort();
 return this;
    }


 public Builder setRatisPort(int port) {
 this.ratisPort = port;
 return this;
    }


 public Builder setOMServiceId(String serviceId) {
 this.omServiceId = serviceId;
 return this;
    }


 public Builder setOMNodeId(String nodeId) {
 this.omNodeId = nodeId;
 return this;
    }


 public OMNodeDetails build() {
 return new OMNodeDetails(omServiceId, omNodeId, rpcAddress, rpcPort,
 ratisPort);
    }
  }