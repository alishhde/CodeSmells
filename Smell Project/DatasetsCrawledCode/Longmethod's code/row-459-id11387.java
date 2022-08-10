 @Override
 public Provider createProvider(URI remoteURI, ProviderFutureFactory futureFactory) throws Exception {
 CompositeData composite = URISupport.parseComposite(remoteURI);
 Map<String, String> options = composite.getParameters();


 Map<String, String> filtered = PropertyUtil.filterProperties(options, FAILOVER_OPTION_PREFIX);
 Map<String, String> nested = PropertyUtil.filterProperties(filtered, FAILOVER_NESTED_OPTION_PREFIX_ADDON);


 Map<String, String> providerOptions = PropertyUtil.filterProperties(options, "provider.");
 // If we have been given a futures factory to use then we ignore any URI options indicating
 // what to create and just go with what we are given.
 if (futureFactory == null) {
 // Create a configured ProviderFutureFactory for use by the resulting AmqpProvider
 futureFactory = ProviderFutureFactory.create(providerOptions);
 if (!providerOptions.isEmpty()) {
 String msg = ""
                    + " Not all Provider options could be applied during Failover Provider creation."
                    + " Check the options are spelled correctly."
                    + " Unused parameters=[" + providerOptions + "]."
                    + " This provider instance cannot be started.";
 throw new IllegalArgumentException(msg);
            }
        }


 FailoverProvider provider = new FailoverProvider(composite.getComponents(), nested, futureFactory);
 Map<String, String> unused = PropertyUtil.setProperties(provider, filtered);
 if (!unused.isEmpty()) {
 String msg = ""
                + " Not all options could be set on the Failover provider."
                + " Check the options are spelled correctly."
                + " Unused parameters=[" + unused + "]."
                + " This Provider cannot be started.";
 throw new IllegalArgumentException(msg);
        }


 return provider;
    }