public final class InMemoryStateStore extends AbstractStateStore {


 private Map<String, EntityState> entityStates = new HashMap<>();
 // Keep it sorted
 private SortedMap<String, InstanceState> instanceStates = Collections
            .synchronizedSortedMap(new TreeMap<String, InstanceState>());


 private static final StateStore STORE = new InMemoryStateStore();


 private InMemoryStateStore() {}


 public static StateStore get() {
 return STORE;
    }


 @Override
 public void putEntity(EntityState entityState) throws StateStoreException {
 String key = new EntityID(entityState.getEntity()).getKey();
 if (entityStates.containsKey(key)) {
 throw new StateStoreException("Entity with key, " + key + " already exists.");
        }
 entityStates.put(key, entityState);
    }


 @Override
 public EntityState getEntity(EntityID entityId) throws StateStoreException {
 if (!entityStates.containsKey(entityId.getKey())) {
 throw new StateStoreException("Entity with key, " + entityId + " does not exist.");
        }
 return entityStates.get(entityId.getKey());
    }


 @Override
 public boolean entityExists(EntityID entityId) {
 return entityStates.containsKey(entityId.getKey());
    }


 @Override
 public Collection<Entity> getEntities(EntityState.STATE state) {
 Collection<Entity> entities = new ArrayList<>();
 for (EntityState entityState : entityStates.values()) {
 if (entityState.getCurrentState().equals(state)) {
 entities.add(entityState.getEntity());
            }
        }
 return entities;
    }


 @Override
 public Collection<EntityState> getAllEntities() {
 return entityStates.values();
    }


 @Override
 public void updateEntity(EntityState entityState) throws StateStoreException {
 String key = new EntityID(entityState.getEntity()).getKey();
 if (!entityStates.containsKey(key)) {
 throw new StateStoreException("Entity with key, " + key + " does not exist.");
        }
 entityStates.put(key, entityState);
    }


 @Override
 public void deleteEntity(EntityID entityId) throws StateStoreException {
 if (!entityStates.containsKey(entityId.getKey())) {
 throw new StateStoreException("Entity with key, " + entityId + " does not exist.");
        }
 deleteExecutionInstances(entityId);
 entityStates.remove(entityId.getKey());
    }


 @Override
 public void deleteEntities() throws StateStoreException {
 entityStates.clear();
    }


 @Override
 public boolean isEntityCompleted(EntityID entityId) {
 // ToDo need to implement this, currently returning false.
 return false;
    }


 @Override
 public void putExecutionInstance(InstanceState instanceState) throws StateStoreException {
 String key = new InstanceID(instanceState.getInstance()).getKey();
 if (instanceStates.containsKey(key)) {
 throw new StateStoreException("Instance with key, " + key + " already exists.");
        }
 instanceStates.put(key, instanceState);
    }


 @Override
 public InstanceState getExecutionInstance(InstanceID instanceId) throws StateStoreException {
 if (!instanceStates.containsKey(instanceId.getKey())) {
 throw new StateStoreException("Instance with key, " + instanceId + " does not exist.");
        }
 return instanceStates.get(instanceId.toString());
    }


 @Override
 public InstanceState getExecutionInstance(String externalID) throws StateStoreException {
 if (StringUtils.isEmpty(externalID)) {
 throw new StateStoreException("External ID for retrieving instance cannot be null");
        }
 for (InstanceState instanceState : instanceStates.values()) {
 if (externalID.equals(instanceState.getInstance().getExternalID())) {
 return instanceState;
            }
        }
 return null;
    }


 @Override
 public void updateExecutionInstance(InstanceState instanceState) throws StateStoreException {
 String key = new InstanceID(instanceState.getInstance()).getKey();
 if (!instanceStates.containsKey(key)) {
 throw new StateStoreException("Instance with key, " + key + " does not exist.");
        }
 instanceStates.put(key, instanceState);
    }


 @Override
 public Collection<InstanceState> getAllExecutionInstances(Entity entity, String cluster)
 throws StateStoreException {
 EntityClusterID id = new EntityClusterID(entity, cluster);
 if (!entityStates.containsKey(id.getEntityID().getKey())) {
 throw new StateStoreException("Entity with key, " + id.getEntityID().getKey() + " does not exist.");
        }
 Collection<InstanceState> instances = new ArrayList<>();
 for (Map.Entry<String, InstanceState> instanceState : instanceStates.entrySet()) {
 if (instanceState.getKey().startsWith(id.toString())) {
 instances.add(instanceState.getValue());
            }
        }
 return instances;
    }


 @Override
 public Collection<InstanceState> getExecutionInstances(Entity entity, String cluster,
 Collection<InstanceState.STATE> states) throws StateStoreException {
 EntityClusterID id = new EntityClusterID(entity, cluster);
 return getExecutionInstances(id, states);
    }


 @Override
 public Collection<InstanceState> getExecutionInstances(Entity entity, String cluster,
 Collection<InstanceState.STATE> states, DateTime start, DateTime end) throws StateStoreException {
 List<InstanceState> instancesToReturn = new ArrayList<>();
 EntityClusterID id = new EntityClusterID(entity, cluster);
 for (InstanceState state : getExecutionInstances(id, states)) {
 ExecutionInstance instance = state.getInstance();
 DateTime instanceTime = instance.getInstanceTime();
 // Start date inclusive and end date exclusive.
 // If start date and end date are equal no instances will be added.
 if ((instanceTime.isEqual(start) || instanceTime.isAfter(start))
                    && instanceTime.isBefore(end)) {
 instancesToReturn.add(state);
            }
        }
 return instancesToReturn;
    }


 @Override
 public Collection<InstanceState> getExecutionInstances(EntityClusterID entityId,
 Collection<InstanceState.STATE> states) throws StateStoreException {
 Collection<InstanceState> instances = new ArrayList<>();
 for (Map.Entry<String, InstanceState> instanceState : instanceStates.entrySet()) {
 if (instanceState.getKey().startsWith(entityId.toString())
                    && states.contains(instanceState.getValue().getCurrentState())) {
 instances.add(instanceState.getValue());
            }
        }
 return instances;
    }


 @Override
 public Map<InstanceState.STATE, Long> getExecutionInstanceSummary(Entity entity, String cluster,
 DateTime start, DateTime end) throws StateStoreException {
 Map<InstanceState.STATE, Long> summary = new HashMap<>();
 for (InstanceState state : getAllExecutionInstances(entity, cluster)) {
 ExecutionInstance instance = state.getInstance();
 DateTime instanceTime = instance.getInstanceTime();
 // Start date inclusive and end date exclusive.
 // If start date and end date are equal no instances will be added.
 if ((instanceTime.isEqual(start) || instanceTime.isAfter(start))
                    && instanceTime.isBefore(end)) {
 if (summary.containsKey(state.getCurrentState())) {
 summary.put(state.getCurrentState(), summary.get(state.getCurrentState()) + 1L);
                } else {
 summary.put(state.getCurrentState(), 1L);
                }
            }
        }
 return summary;
    }


 @Override
 public InstanceState getLastExecutionInstance(Entity entity, String cluster) throws StateStoreException {
 EntityClusterID id = new EntityClusterID(entity, cluster);
 if (!entityStates.containsKey(id.getEntityID().getKey())) {
 throw new StateStoreException("Entity with key, " + id.getEntityID().getKey() + " does not exist.");
        }
 InstanceState latestState = null;
 // TODO : Very crude. Iterating over all entries and getting the last one.
 for (Map.Entry<String, InstanceState> instanceState : instanceStates.entrySet()) {
 if (instanceState.getKey().startsWith(id.toString())) {
 latestState = instanceState.getValue();
            }
        }
 return latestState;
    }


 @Override
 public boolean executionInstanceExists(InstanceID instanceId) {
 return instanceStates.containsKey(instanceId.toString());
    }


 @Override
 public void deleteExecutionInstances(EntityID entityId) {
 for (String instanceKey : Lists.newArrayList(instanceStates.keySet())) {
 if (instanceKey.startsWith(entityId.getKey())) {
 instanceStates.remove(instanceKey);
            }
        }
    }


 @Override
 public void deleteExecutionInstances() {
 instanceStates.clear();
    }


 @Override
 public void deleteExecutionInstance(InstanceID instanceID) throws StateStoreException {
 if (!instanceStates.containsKey(instanceID.toString())) {
 throw new StateStoreException("Instance with key, " + instanceID.toString() + " does not exist.");
        }
 instanceStates.remove(instanceID.toString());
    }


 @Override
 public void clear() {
 entityStates.clear();
 instanceStates.clear();
    }
}