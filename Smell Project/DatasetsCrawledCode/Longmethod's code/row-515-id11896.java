 @Override
 public void configure(Context context) {
 this.headerName = context.getString(CONFIG_MULTIPLEX_HEADER_NAME,
 DEFAULT_MULTIPLEX_HEADER);


 Map<String, Channel> channelNameMap = getChannelNameMap();


 defaultChannels = getChannelListFromNames(
 context.getString(CONFIG_DEFAULT_CHANNEL), channelNameMap);


 Map<String, String> mapConfig =
 context.getSubProperties(CONFIG_PREFIX_MAPPING);


 channelMapping = new HashMap<String, List<Channel>>();


 for (String headerValue : mapConfig.keySet()) {
 List<Channel> configuredChannels = getChannelListFromNames(
 mapConfig.get(headerValue),
 channelNameMap);


 //This should not go to default channel(s)
 //because this seems to be a bad way to configure.
 if (configuredChannels.size() == 0) {
 throw new FlumeException("No channel configured for when "
            + "header value is: " + headerValue);
      }


 if (channelMapping.put(headerValue, configuredChannels) != null) {
 throw new FlumeException("Selector channel configured twice");
      }
    }
 //If no mapping is configured, it is ok.
 //All events will go to the default channel(s).
 Map<String, String> optionalChannelsMapping =
 context.getSubProperties(CONFIG_PREFIX_OPTIONAL + ".");


 optionalChannels = new HashMap<String, List<Channel>>();
 for (String hdr : optionalChannelsMapping.keySet()) {
 List<Channel> confChannels = getChannelListFromNames(
 optionalChannelsMapping.get(hdr), channelNameMap);
 if (confChannels.isEmpty()) {
 confChannels = EMPTY_LIST;
      }
 //Remove channels from optional channels, which are already
 //configured to be required channels.


 List<Channel> reqdChannels = channelMapping.get(hdr);
 //Check if there are required channels, else defaults to default channels
 if (reqdChannels == null || reqdChannels.isEmpty()) {
 reqdChannels = defaultChannels;
      }
 for (Channel c : reqdChannels) {
 if (confChannels.contains(c)) {
 confChannels.remove(c);
        }
      }


 if (optionalChannels.put(hdr, confChannels) != null) {
 throw new FlumeException("Selector channel configured twice");
      }
    }


  }