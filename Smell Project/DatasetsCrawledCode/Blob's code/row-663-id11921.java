@Component
public class VirtualMachineEntityImpl implements VirtualMachineEntity {


 @Inject
 private VMEntityManager manager;


 private VMEntityVO vmEntityVO;


 public VirtualMachineEntityImpl() {
    }


 public void init(String vmId) {
 this.vmEntityVO = this.manager.loadVirtualMachine(vmId);
    }


 public void init(String vmId, String owner, String hostName, String displayName, int cpu, int speed, long memory, List<String> computeTags,
 List<String> rootDiskTags, List<String> networks) {
 init(vmId);
 this.vmEntityVO.setOwner(owner);
 this.vmEntityVO.setHostname(hostName);
 this.vmEntityVO.setDisplayname(displayName);
 this.vmEntityVO.setComputeTags(computeTags);
 this.vmEntityVO.setRootDiskTags(rootDiskTags);
 this.vmEntityVO.setNetworkIds(networks);


 manager.saveVirtualMachine(vmEntityVO);
    }


 public VirtualMachineEntityImpl(String vmId, VMEntityManager manager) {
 this.manager = manager;
 this.vmEntityVO = this.manager.loadVirtualMachine(vmId);
    }


 public VirtualMachineEntityImpl(String vmId, String owner, String hostName, String displayName, int cpu, int speed, long memory, List<String> computeTags,
 List<String> rootDiskTags, List<String> networks, VMEntityManager manager) {
 this(vmId, manager);
 this.vmEntityVO.setOwner(owner);
 this.vmEntityVO.setHostname(hostName);
 this.vmEntityVO.setDisplayname(displayName);
 this.vmEntityVO.setComputeTags(computeTags);
 this.vmEntityVO.setRootDiskTags(rootDiskTags);
 this.vmEntityVO.setNetworkIds(networks);


 manager.saveVirtualMachine(vmEntityVO);
    }


 @Override
 public String getUuid() {
 return vmEntityVO.getUuid();
    }


 @Override
 public long getId() {
 return vmEntityVO.getId();
    }


 @Override
 public String getCurrentState() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public String getDesiredState() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public Date getCreatedTime() {
 return vmEntityVO.getCreated();
    }


 @Override
 public Date getLastUpdatedTime() {
 return vmEntityVO.getUpdateTime();
    }


 @Override
 public String getOwner() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public Map<String, String> getDetails() {
 return vmEntityVO.getDetails();
    }


 @Override
 public void addDetail(String name, String value) {
 vmEntityVO.setDetail(name, value);
    }


 @Override
 public void delDetail(String name, String value) {
 // TODO Auto-generated method stub
    }


 @Override
 public void updateDetail(String name, String value) {
 // TODO Auto-generated method stub
    }


 @Override
 public List<Method> getApplicableActions() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public List<String> listVolumeIds() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public List<VolumeEntity> listVolumes() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public List<String> listNicUuids() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public List<NicEntity> listNics() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public TemplateEntity getTemplate() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public List<String> listTags() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public void addTag() {
 // TODO Auto-generated method stub


    }


 @Override
 public void delTag() {
 // TODO Auto-generated method stub


    }


 @Override
 public String reserve(DeploymentPlanner plannerToUse, DeploymentPlan plan, ExcludeList exclude, String caller) throws InsufficientCapacityException,
 ResourceUnavailableException {
 return manager.reserveVirtualMachine(this.vmEntityVO, plannerToUse, plan, exclude);
    }


 @Override
 public void migrateTo(String reservationId, String caller) {
 // TODO Auto-generated method stub


    }


 @Override
 public void deploy(String reservationId, String caller, Map<VirtualMachineProfile.Param, Object> params, boolean deployOnGivenHost) throws InsufficientCapacityException,
 ResourceUnavailableException {
 manager.deployVirtualMachine(reservationId, this.vmEntityVO, caller, params, deployOnGivenHost);
    }


 @Override
 public boolean stop(String caller) throws ResourceUnavailableException {
 return manager.stopvirtualmachine(this.vmEntityVO, caller);
    }


 @Override
 public boolean stopForced(String caller) throws ResourceUnavailableException {
 return manager.stopvirtualmachineforced(this.vmEntityVO, caller);
    }


 @Override
 public void cleanup() {
 // TODO Auto-generated method stub


    }


 @Override
 public boolean destroy(String caller, boolean expunge) throws AgentUnavailableException, OperationTimedoutException, ConcurrentOperationException {
 return manager.destroyVirtualMachine(this.vmEntityVO, caller, expunge);
    }


 @Override
 public VirtualMachineEntity duplicate(String externalId) {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public SnapshotEntity takeSnapshotOf() {
 // TODO Auto-generated method stub
 return null;
    }


 @Override
 public void attach(VolumeEntity volume, short deviceId) {
 // TODO Auto-generated method stub


    }


 @Override
 public void detach(VolumeEntity volume) {
 // TODO Auto-generated method stub


    }


 @Override
 public void connectTo(NetworkEntity network, short nicId) {
 // TODO Auto-generated method stub


    }


 @Override
 public void disconnectFrom(NetworkEntity netowrk, short nicId) {
 // TODO Auto-generated method stub


    }


}