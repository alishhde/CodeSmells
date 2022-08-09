 private static class ProxiedNiFiClient implements NiFiClient {


 private final String proxiedEntity;
 private final NiFiClient wrappedClient;


 public ProxiedNiFiClient(final NiFiClient wrappedClient, final String proxiedEntity) {
 this.proxiedEntity = proxiedEntity;
 this.wrappedClient = wrappedClient;
        }


 @Override
 public ControllerClient getControllerClient() {
 return wrappedClient.getControllerClientForProxiedEntities(proxiedEntity);
        }


 @Override
 public ControllerClient getControllerClientForProxiedEntities(String... proxiedEntity) {
 return wrappedClient.getControllerClientForProxiedEntities(proxiedEntity);
        }


 @Override
 public ControllerClient getControllerClientForToken(String token) {
 return wrappedClient.getControllerClientForToken(token);
        }


 @Override
 public FlowClient getFlowClient() {
 return wrappedClient.getFlowClientForProxiedEntities(proxiedEntity);
        }


 @Override
 public FlowClient getFlowClientForProxiedEntities(String... proxiedEntity) {
 return wrappedClient.getFlowClientForProxiedEntities(proxiedEntity);
        }


 @Override
 public FlowClient getFlowClientForToken(String token) {
 return wrappedClient.getFlowClientForToken(token);
        }


 @Override
 public ProcessGroupClient getProcessGroupClient() {
 return wrappedClient.getProcessGroupClientForProxiedEntities(proxiedEntity);
        }


 @Override
 public ProcessGroupClient getProcessGroupClientForProxiedEntities(String... proxiedEntity) {
 return wrappedClient.getProcessGroupClientForProxiedEntities(proxiedEntity);
        }


 @Override
 public ProcessGroupClient getProcessGroupClientForToken(String token) {
 return wrappedClient.getProcessGroupClientForToken(token);
        }


 @Override
 public VersionsClient getVersionsClient() {
 return wrappedClient.getVersionsClientForProxiedEntities(proxiedEntity);
        }


 @Override
 public VersionsClient getVersionsClientForProxiedEntities(String... proxiedEntity) {
 return wrappedClient.getVersionsClientForProxiedEntities(proxiedEntity);
        }


 @Override
 public VersionsClient getVersionsClientForToken(String token) {
 return wrappedClient.getVersionsClientForToken(token);
        }


 @Override
 public void close() throws IOException {
 wrappedClient.close();
        }
    }