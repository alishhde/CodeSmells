 public static byte[] getIP() {
 try {
 Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
 InetAddress ip = null;
 byte[] internalIP = null;
 while (allNetInterfaces.hasMoreElements()) {
 NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
 Enumeration addresses = netInterface.getInetAddresses();
 while (addresses.hasMoreElements()) {
 ip = (InetAddress) addresses.nextElement();
 if (ip != null && ip instanceof Inet4Address) {
 byte[] ipByte = ip.getAddress();
 if (ipByte.length == 4) {
 if (ipCheck(ipByte)) {
 if (!isInternalIP(ipByte)) {
 return ipByte;
                                } else if (internalIP == null) {
 internalIP = ipByte;
                                }
                            }
                        }
                    }
                }
            }
 if (internalIP != null) {
 return internalIP;
            } else {
 throw new RuntimeException("Can not get local ip");
            }
        } catch (Exception e) {
 throw new RuntimeException("Can not get local ip", e);
        }
    }