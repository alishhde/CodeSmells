 private ApplicationDTO buildApplicationDTO(
 ApplicationRuntimeInformation ari) {


 ApplicationDTO applicationDTO = new ApplicationDTO(){};


 applicationDTO.name = getServiceName(
 ari._cachingServiceReference::getProperty);
 applicationDTO.base = _whiteboard.getApplicationBase(
 ari._cachingServiceReference::getProperty);
 applicationDTO.serviceId =
            (Long)ari._cachingServiceReference.getProperty("service.id");


 applicationDTO.resourceDTOs = getApplicationEndpointsStream(
 applicationDTO.name).toArray(
 ResourceDTO[]::new
            );


 applicationDTO.extensionDTOs = getApplicationExtensionsStream(
 applicationDTO.name).toArray(
 ExtensionDTO[]::new
            );


 Map<String, Set<ExtensionDTO>> nameBoundExtensions =
 new HashMap<>();


 Map<ExtensionDTO, Set<ResourceDTO>> extensionResources =
 new HashMap<>();


 for (ExtensionDTO extensionDTO : applicationDTO.extensionDTOs) {
 if (extensionDTO.nameBindings == null) {
 continue;
            }


 for (String nameBinding : extensionDTO.nameBindings) {
 Set<ExtensionDTO> extensionDTOS =
 nameBoundExtensions.computeIfAbsent(
 nameBinding,
 __ -> new HashSet<>()
                );


 extensionDTOS.add(extensionDTO);
            }
        }


 for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {
 for (ResourceMethodInfoDTO resourceMethodInfo :
 resourceDTO.resourceMethods) {


 if (resourceMethodInfo.nameBindings == null) {
 continue;
                }


 for (String nameBinding : resourceMethodInfo.nameBindings) {
 Set<ExtensionDTO> extensionDTOS = nameBoundExtensions.get(
 nameBinding);


 if (extensionDTOS != null) {
 for (ExtensionDTO extensionDTO : extensionDTOS) {
 Set<ResourceDTO> resourceDTOS =
 extensionResources.computeIfAbsent(
 extensionDTO, __ -> new HashSet<>());


 resourceDTOS.add(resourceDTO);
                        }
                    }
                }
            }
        }


 extensionResources.forEach(
            (extensionDTO, resourceDTOS) ->
 extensionDTO.filteredByName = resourceDTOS.toArray(
 new ResourceDTO[0])
        );


 CxfJaxrsServiceRegistrator cxfJaxRsServiceRegistrator =
 ari._cxfJaxRsServiceRegistrator;


 Bus bus = cxfJaxRsServiceRegistrator.getBus();
 Iterable<Class<?>> resourceClasses =
 cxfJaxRsServiceRegistrator.getStaticResourceClasses();


 ArrayList<ResourceMethodInfoDTO> resourceMethodInfoDTOS =
 new ArrayList<>();


 for (Class<?> resourceClass : resourceClasses) {
 resourceMethodInfoDTOS.addAll(
 ClassIntrospector.getResourceMethodInfos(resourceClass, bus));
        }


 applicationDTO.resourceMethods = resourceMethodInfoDTOS.toArray(
 new ResourceMethodInfoDTO[0]);


 return applicationDTO;
    }