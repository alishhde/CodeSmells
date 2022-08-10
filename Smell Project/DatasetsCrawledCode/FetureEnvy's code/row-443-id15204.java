 public static int reconfigureNetworking()
  {
 // This uses values from the property settings
 if (Sage.getBoolean(NET_CONFIG_WIRED, true))
    {
 // Bring down the wireless interface if it's there
 bringDownWireless();


 setupNetworking(Sage.get("linux/wired_network_port", "eth0"));
    }
 else
    {
 // Bring down the wired interface if it's there
 if (Sage.getBoolean("linux/disable_wired_when_wireless_is_enabled", false))
 bringDownWired();


 // Be sure the wired interface is loaded (it may need to be before it is configured)
 IOUtils.exec2("ifconfig " + Sage.get("linux/wireless_network_port", "eth1") + " up");


 // Setup the wireless networking properties before we try to connect to the network or it won't work
 IOUtils.exec2("iwconfig " + Sage.get("linux/wireless_network_port", "eth1") + " essid " + Sage.get(NET_CONFIG_SSID, "any"));


 String crypto = Sage.get(NET_CONFIG_ENCRYPTION, "WPA");
 if ("None".equals(crypto))
      {
 IOUtils.exec2("iwconfig " + Sage.get("linux/wireless_network_port", "eth1") + " key off");
      }
 else
      {
 // Check if the key is all hex
 String key = Sage.get(NET_CONFIG_ENCRYPTION_KEY, "");
 boolean hexKey = true;
 for (int i = 0; i < key.length(); i++)
        {
 if (Character.digit(key.charAt(i), 16) < 0)
          {
 hexKey = false;
 break;
          }
        }
 if ("WEP".equals(crypto))
        {
 IOUtils.exec2("iwconfig " + Sage.get("linux/wireless_network_port", "eth1") + " key on");
 if (hexKey)
 IOUtils.exec2("iwconfig " + Sage.get("linux/wireless_network_port", "eth1") + " key " + Sage.get(NET_CONFIG_ENCRYPTION_KEY, ""));
 else
 IOUtils.exec2("iwconfig " + Sage.get("linux/wireless_network_port", "eth1") + " key s:" + Sage.get(NET_CONFIG_ENCRYPTION_KEY, ""));
        }
 else // WPA
        {
 // NOT FINISHED YET, we'll need to setup a configuration file for wpa_supplicant and then run it
        }
      }


 setupNetworking(Sage.get("linux/wireless_network_port", "eth1"));
    }
 return 0;
  }