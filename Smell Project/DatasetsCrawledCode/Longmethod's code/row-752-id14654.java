 private <T extends ResourceBase> boolean mergeResourceMap(
 Map<String, T> fragmentResources, Map<String, T> mainResources,
 Map<String, T> tempResources, WebXml fragment) {
 for (T resource : fragmentResources.values()) {
 String resourceName = resource.getName();
 if (mainResources.containsKey(resourceName)) {
 mainResources.get(resourceName).getInjectionTargets().addAll(
 resource.getInjectionTargets());
            } else {
 // Not defined in main web.xml
 T existingResource = tempResources.get(resourceName);
 if (existingResource != null) {
 if (!existingResource.equals(resource)) {
 log.error(sm.getString(
 "webXml.mergeConflictResource",
 resourceName,
 fragment.getName(),
 fragment.getURL()));
 return false;
                    }
                } else {
 tempResources.put(resourceName, resource);
                }
            }
        }
 return true;
    }