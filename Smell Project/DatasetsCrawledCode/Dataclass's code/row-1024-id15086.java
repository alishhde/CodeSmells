public class InstantiatedVersionedLabel extends VersionedLabel implements InstantiatedVersionedComponent {
 private final String instanceId;
 private final String groupId;


 public InstantiatedVersionedLabel(final String instanceId, final String instanceGroupId) {
 this.instanceId = instanceId;
 this.groupId = instanceGroupId;
    }


 @Override
 public String getInstanceId() {
 return instanceId;
    }


 @Override
 public String getInstanceGroupId() {
 return groupId;
    }
}