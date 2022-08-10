 public ServiceDefinition[] findServicesByToolID(final String toolId) {
 try {
 ensureDiskCacheLoaded();
 accessLock.readLock().lock();


 final boolean returnAll = toolId == null || toolId.length() == 0;
 final List<ServiceDefinition> requestedDefinitions = new ArrayList<ServiceDefinition>();


 // Iterate the map of service types. Values are a map of service
 // instances.
 for (final Map<GUID, ServiceDefinition> mapServiceInstances : mapServices.values()) {
 for (final ServiceDefinition definition : mapServiceInstances.values()) {
 /*
                     * NB! some service definitions in Dev12 QU1 may have null
                     * ToolID
                     */
 if (returnAll || toolId.equalsIgnoreCase(definition.getToolID())) {
 requestedDefinitions.add((ServiceDefinition) definition.clone());
                    }
                }
            }


 // Return null if no matching definitions were found.
 if (requestedDefinitions.size() == 0) {
 return null;
            }


 // Return an array of the matching service definitions.
 return requestedDefinitions.toArray(new ServiceDefinition[requestedDefinitions.size()]);
        } finally {
 accessLock.readLock().unlock();
        }
    }