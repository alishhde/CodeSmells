 private BundleEvent initializeEvent(Bundle bundle) {
 switch (bundle.getState()) {
 case Bundle.INSTALLED:
 return new BundleEvent(BundleEvent.INSTALLED, bundle);
 case Bundle.RESOLVED:
 return new BundleEvent(BundleEvent.RESOLVED, bundle);
 default:
 return new BundleEvent(BundleEvent.STARTED, bundle);
        }
    }