 protected NetworkVO getDefaultNetworkForAdvancedZone(DataCenter dc) {
 if (dc.getNetworkType() != NetworkType.Advanced) {
 throw new CloudRuntimeException("Zone " + dc + " is not advanced.");
        }


 if (dc.isSecurityGroupEnabled()) {
 List<NetworkVO> networks = _networkDao.listByZoneSecurityGroup(dc.getId());
 if (CollectionUtils.isEmpty(networks)) {
 throw new CloudRuntimeException("Can not found security enabled network in SG Zone " + dc);
            }


 return networks.get(0);
        }
 else {
 TrafficType defaultTrafficType = TrafficType.Public;
 List<NetworkVO> defaultNetworks = _networkDao.listByZoneAndTrafficType(dc.getId(), defaultTrafficType);


 // api should never allow this situation to happen
 if (defaultNetworks.size() != 1) {
 throw new CloudRuntimeException("Found " + defaultNetworks.size() + " networks of type " + defaultTrafficType + " when expect to find 1");
            }


 return defaultNetworks.get(0);
        }
    }