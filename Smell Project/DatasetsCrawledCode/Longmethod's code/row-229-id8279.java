 private void processAsSubstitutableExport(boolean isFragment, Requirement requirement, List<Capability> capabilities) {
 String namespace = requirement.getNamespace();
 if (!PackageNamespace.PACKAGE_NAMESPACE.equals(namespace)) {
 return;
		}
 Resource resource = requirement.getResource();
 Wiring wiring = wirings.get(resource);
 if (isFragment) {
 List<Wire> fragmentWires = wiring.getRequiredResourceWires(HostNamespace.HOST_NAMESPACE);
 for (Wire fragmentWire : fragmentWires) {
 Resource host = fragmentWire.getProvider();
 processResourceCapabilities(
 wirings.get(host).getResourceCapabilities(namespace),
 requirement,
 capabilities);
			}
		}
 else {
 List<Capability> resourceCapabilities = wiring.getResourceCapabilities(namespace);
 processResourceCapabilities(resourceCapabilities, requirement, capabilities);
		}
	}