 @Override
 public List<? extends Host> discoverHosts(final AddHostCmd cmd) throws IllegalArgumentException, DiscoveryException, InvalidParameterValueException {
 Long dcId = cmd.getZoneId();
 final Long podId = cmd.getPodId();
 final Long clusterId = cmd.getClusterId();
 String clusterName = cmd.getClusterName();
 final String url = cmd.getUrl();
 final String username = cmd.getUsername();
 final String password = cmd.getPassword();
 final List<String> hostTags = cmd.getHostTags();


 dcId = _accountMgr.checkAccessAndSpecifyAuthority(CallContext.current().getCallingAccount(), dcId);


 // this is for standalone option
 if (clusterName == null && clusterId == null) {
 clusterName = "Standalone-" + url;
        }


 if (clusterId != null) {
 final ClusterVO cluster = _clusterDao.findById(clusterId);
 if (cluster == null) {
 final InvalidParameterValueException ex = new InvalidParameterValueException("can not find cluster for specified clusterId");
 ex.addProxyObject(clusterId.toString(), "clusterId");
 throw ex;
            } else {
 if (cluster.getGuid() == null) {
 final List<HostVO> hosts = listAllHostsInCluster(clusterId);
 if (!hosts.isEmpty()) {
 final CloudRuntimeException ex =
 new CloudRuntimeException("Guid is not updated for cluster with specified cluster id; need to wait for hosts in this cluster to come up");
 ex.addProxyObject(cluster.getUuid(), "clusterId");
 throw ex;
                    }
                }
            }
        }


 return discoverHostsFull(dcId, podId, clusterId, clusterName, url, username, password, cmd.getHypervisor(), hostTags, cmd.getFullUrlParams(), false);
    }