 void cleanup() {
 for (final BundleWire requiredWire : requiredWires.getAllValues()) {
 final ConciergeBundleWiring bw = ((ConciergeBundleWire) requiredWire).providerWiring;
 if (bw != null) {
 bw.inUseSet.remove(revision);
				}
			}
 for (final BundleWire hostWire : providedWires
					.lookup(HostNamespace.HOST_NAMESPACE)) {
 final ConciergeBundleWiring bw = ((ConciergeBundleWire) hostWire).requirerWiring;
 if (bw != null) {
 bw.inUseSet.remove(revision);
				}
			}
		}