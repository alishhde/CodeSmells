public class ChannelTypeXmlResult {


 private ChannelType channelType;
 private ConfigDescription configDescription;
 private boolean system;


 public ChannelTypeXmlResult(ChannelType channelType, ConfigDescription configDescription) {
 this(channelType, configDescription, false);
    }


 public ChannelTypeXmlResult(ChannelType channelType, ConfigDescription configDescription, boolean system) {
 this.channelType = channelType;
 this.configDescription = configDescription;
 this.system = system;
    }


 public ChannelType toChannelType() {
 return this.channelType;
    }


 public ConfigDescription getConfigDescription() {
 return this.configDescription;
    }


 public boolean isSystem() {
 return system;
    }


 @Override
 public String toString() {
 return "ChannelTypeXmlResult [channelType=" + channelType + ", configDescription=" + configDescription + "]";
    }


}