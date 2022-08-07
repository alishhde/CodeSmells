 private static final class TomcatInjections { // load when needed
 private TomcatInjections() {
 // no-op
        }


 private static Map<String, Map<String, String>> buildInjectionMap(final NamingResourcesImpl namingResources) {
 final Map<String, Map<String, String>> injectionMap = new HashMap<>();
 for (final Injectable resource : namingResources.findLocalEjbs()) {
 addInjectionTarget(resource, injectionMap);
            }
 for (final Injectable resource : namingResources.findEjbs()) {
 addInjectionTarget(resource, injectionMap);
            }
 for (final Injectable resource : namingResources.findEnvironments()) {
 addInjectionTarget(resource, injectionMap);
            }
 for (final Injectable resource : namingResources.findMessageDestinationRefs()) {
 addInjectionTarget(resource, injectionMap);
            }
 for (final Injectable resource : namingResources.findResourceEnvRefs()) {
 addInjectionTarget(resource, injectionMap);
            }
 for (final Injectable resource : namingResources.findResources()) {
 addInjectionTarget(resource, injectionMap);
            }
 for (final Injectable resource : namingResources.findServices()) {
 addInjectionTarget(resource, injectionMap);
            }
 return injectionMap;
        }


 private static void addInjectionTarget(final Injectable resource, final Map<String, Map<String, String>> injectionMap) {
 final List<InjectionTarget> injectionTargets = resource.getInjectionTargets();
 if (injectionTargets != null && !injectionTargets.isEmpty()) {
 final String jndiName = resource.getName();
 for (final InjectionTarget injectionTarget : injectionTargets) {
 final String clazz = injectionTarget.getTargetClass();
 Map<String, String> injections = injectionMap.get(clazz);
 if (injections == null) {
 injections = new HashMap<>();
 injectionMap.put(clazz, injections);
                    }
 injections.put(injectionTarget.getTargetName(), jndiName);
                }
            }
        }
    }