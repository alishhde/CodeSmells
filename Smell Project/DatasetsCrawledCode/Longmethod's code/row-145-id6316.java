 public String getDeviceDisplayName() {
 String displayName = "";
 if (this.properties == null) {
 return displayName;
        }
 String deviceDisplayNameOption = (String) this.properties.get(DEVICE_DISPLAY_NAME);


 // Use the device name from SystemService. This should be kura.device.name from
 // the properties file.
 if ("device-name".equals(deviceDisplayNameOption)) {
 displayName = this.systemService.getDeviceName();
        }
 // Try to get the device hostname
 else if ("hostname".equals(deviceDisplayNameOption)) {
 displayName = this.systemService.getHostname();
        }
 // Return the custom field defined by the user
 else if ("custom".equals(deviceDisplayNameOption)
                && this.properties.get(DEVICE_CUSTOM_NAME) instanceof String) {
 displayName = (String) this.properties.get(DEVICE_CUSTOM_NAME);
        }
 // Return empty string to the server
 else if ("server".equals(deviceDisplayNameOption)) {
 displayName = "";
        }


 return displayName;
    }