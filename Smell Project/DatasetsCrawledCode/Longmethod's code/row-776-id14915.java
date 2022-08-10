 InitializeParams get(String id) throws LanguageServerException {
 InitializeParams initializeParams = new InitializeParams();
 LOG.debug("Initialize params constructing: started");


 Boolean locality = localityRegistry.get(id);
 LOG.debug("Locality: {}", locality);


 Integer processId = locality ? ProcessIdProvider.get() : null;
 initializeParams.setProcessId(processId);
 LOG.debug("Process id: {}", processId);


 String projectsRoot = projectsRootRegistry.getOrNull(id);


 String rootPath;
 if (projectsRoot != null) {
 rootPath = projectsRoot;
    } else {
 rootPath = Paths.get(rootUri).toAbsolutePath().toString();
    }
 initializeParams.setRootPath(rootPath);
 LOG.debug("Root path: {}", rootPath);


 String rootUri;
 if (projectsRoot != null) {
 rootUri = Paths.get(projectsRoot).toUri().toString();
    } else {
 rootUri = this.rootUri.toString();
    }
 initializeParams.setRootUri(rootUri);
 LOG.debug("Root URI: {}", rootUri);


 ClientCapabilities capabilities = ClientCapabilitiesProvider.get();
 initializeParams.setCapabilities(capabilities);
 LOG.debug("Client capabilities: {}", capabilities);


 String clientName = ClientCapabilitiesProvider.CLIENT_NAME;
 initializeParams.setClientName(clientName);
 LOG.debug("Client name: {}", clientName);


 LOG.debug("Initialize params constructing: finished");
 return initializeParams;
  }