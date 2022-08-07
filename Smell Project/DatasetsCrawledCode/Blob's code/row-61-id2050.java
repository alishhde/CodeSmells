public interface DedicatedService extends PluggableService {


 DedicatePodResponse createDedicatePodResponse(DedicatedResources resource);


 DedicateClusterResponse createDedicateClusterResponse(DedicatedResources resource);


 DedicateHostResponse createDedicateHostResponse(DedicatedResources resource);


 Pair<List<? extends DedicatedResourceVO>, Integer> listDedicatedPods(ListDedicatedPodsCmd cmd);


 Pair<List<? extends DedicatedResourceVO>, Integer> listDedicatedHosts(ListDedicatedHostsCmd cmd);


 Pair<List<? extends DedicatedResourceVO>, Integer> listDedicatedClusters(ListDedicatedClustersCmd cmd);


 boolean releaseDedicatedResource(Long zoneId, Long podId, Long clusterId, Long hostId);


 DedicateZoneResponse createDedicateZoneResponse(DedicatedResources resource);


 Pair<List<? extends DedicatedResourceVO>, Integer> listDedicatedZones(ListDedicatedZonesCmd cmd);


 List<DedicatedResourceVO> dedicateZone(Long zoneId, Long domainId, String accountName);


 List<DedicatedResourceVO> dedicatePod(Long podId, Long domainId, String accountName);


 List<DedicatedResourceVO> dedicateCluster(Long clusterId, Long domainId, String accountName);


 List<DedicatedResourceVO> dedicateHost(Long hostId, Long domainId, String accountName);


}