@HaHotStateRequired
public class SensorResource extends AbstractBrooklynRestResource implements SensorApi {


 private static final Logger log = LoggerFactory.getLogger(SensorResource.class);


 @Override
 public List<SensorSummary> list(final String application, final String entityToken) {
 final Entity entity = brooklyn().getEntity(application, entityToken);
 if (!Entitlements.isEntitled(mgmt().getEntitlementManager(), Entitlements.SEE_ENTITY, entity)) {
 throw WebResourceUtils.forbidden("User '%s' is not authorized to see entity '%s'",
 Entitlements.getEntitlementContext().user(), entity);
        }


 List<SensorSummary> result = Lists.newArrayList();
 
 for (AttributeSensor<?> sensor : filter(entity.getEntityType().getSensors(), AttributeSensor.class)) {
 // Exclude config that user is not allowed to see
 if (!Entitlements.isEntitled(mgmt().getEntitlementManager(), Entitlements.SEE_SENSOR, new EntityAndItem<String>(entity, sensor.getName()))) {
 log.trace("User {} not authorized to see sensor {} of entity {}; excluding from AttributeSensor list results", 
 new Object[] {Entitlements.getEntitlementContext().user(), sensor.getName(), entity});
 continue;
            }
 result.add(SensorTransformer.sensorSummary(entity, sensor, ui.getBaseUriBuilder()));
        }
 
 return result;
    }


 @Override
 public Map<String, Object> batchSensorRead(final String application, final String entityToken, final Boolean raw) {
 final Entity entity = brooklyn().getEntity(application, entityToken);
 if (!Entitlements.isEntitled(mgmt().getEntitlementManager(), Entitlements.SEE_ENTITY, entity)) {
 throw WebResourceUtils.forbidden("User '%s' is not authorized to see entity '%s'",
 Entitlements.getEntitlementContext().user(), entity);
        }


 Map<String, Object> sensorMap = Maps.newHashMap();
 @SuppressWarnings("rawtypes")
 Iterable<AttributeSensor> sensors = filter(entity.getEntityType().getSensors(), AttributeSensor.class);


 for (AttributeSensor<?> sensor : sensors) {
 // Exclude sensors that user is not allowed to see
 if (!Entitlements.isEntitled(mgmt().getEntitlementManager(), Entitlements.SEE_SENSOR, new EntityAndItem<String>(entity, sensor.getName()))) {
 log.trace("User {} not authorized to see sensor {} of entity {}; excluding from current-state results", 
 new Object[] {Entitlements.getEntitlementContext().user(), sensor.getName(), entity});
 continue;
            }


 Object value = entity.getAttribute(findSensor(entity, sensor.getName()));
 sensorMap.put(sensor.getName(), 
 resolving(value).preferJson(true).asJerseyOutermostReturnValue(false).raw(raw).context(entity).timeout(Duration.ZERO).renderAs(sensor).resolve());
        }
 return sensorMap;
    }


 protected Object get(boolean preferJson, String application, String entityToken, String sensorName, Boolean raw) {
 final Entity entity = brooklyn().getEntity(application, entityToken);
 AttributeSensor<?> sensor = findSensor(entity, sensorName);
 
 if (!Entitlements.isEntitled(mgmt().getEntitlementManager(), Entitlements.SEE_ENTITY, entity)) {
 throw WebResourceUtils.forbidden("User '%s' is not authorized to see entity '%s'",
 Entitlements.getEntitlementContext().user(), entity);
        }
 if (!Entitlements.isEntitled(mgmt().getEntitlementManager(), Entitlements.SEE_SENSOR, new EntityAndItem<String>(entity, sensor.getName()))) {
 throw WebResourceUtils.forbidden("User '%s' is not authorized to see entity '%s' sensor '%s'",
 Entitlements.getEntitlementContext().user(), entity, sensor.getName());
        }
 
 Object value = entity.getAttribute(sensor);
 return resolving(value).preferJson(preferJson).asJerseyOutermostReturnValue(true).raw(raw).context(entity).immediately(true).renderAs(sensor).resolve();
    }


 @Override
 public String getPlain(String application, String entityToken, String sensorName, final Boolean raw) {
 return (String) get(false, application, entityToken, sensorName, raw);
    }


 @Override
 public Object get(final String application, final String entityToken, String sensorName, final Boolean raw) {
 return get(true, application, entityToken, sensorName, raw);
    }


 private AttributeSensor<?> findSensor(Entity entity, String name) {
 Sensor<?> s = entity.getEntityType().getSensor(name);
 if (s instanceof AttributeSensor) return (AttributeSensor<?>) s;
 return new BasicAttributeSensor<Object>(Object.class, name);
    }
 
 @SuppressWarnings({ "rawtypes", "unchecked" })
 @Override
 public void setFromMap(String application, String entityToken, Map newValues) {
 final Entity entity = brooklyn().getEntity(application, entityToken);
 if (!Entitlements.isEntitled(mgmt().getEntitlementManager(), Entitlements.MODIFY_ENTITY, entity)) {
 throw WebResourceUtils.forbidden("User '%s' is not authorized to modify entity '%s'",
 Entitlements.getEntitlementContext().user(), entity);
        }


 if (log.isDebugEnabled())
 log.debug("REST user "+Entitlements.getEntitlementContext()+" setting sensors "+newValues);
 for (Object entry: newValues.entrySet()) {
 String sensorName = Strings.toString(((Map.Entry)entry).getKey());
 Object newValue = ((Map.Entry)entry).getValue();
 
 AttributeSensor sensor = findSensor(entity, sensorName);
 entity.sensors().set(sensor, newValue);
        }
    }
 
 @SuppressWarnings({ "rawtypes", "unchecked" })
 @Override
 public void set(String application, String entityToken, String sensorName, Object newValue) {
 final Entity entity = brooklyn().getEntity(application, entityToken);
 if (!Entitlements.isEntitled(mgmt().getEntitlementManager(), Entitlements.MODIFY_ENTITY, entity)) {
 throw WebResourceUtils.forbidden("User '%s' is not authorized to modify entity '%s'",
 Entitlements.getEntitlementContext().user(), entity);
        }
 
 AttributeSensor sensor = findSensor(entity, sensorName);
 if (log.isDebugEnabled())
 log.debug("REST user "+Entitlements.getEntitlementContext()+" setting sensor "+sensorName+" to "+newValue);
 entity.sensors().set(sensor, newValue);
    }
 
 @Override
 public void delete(String application, String entityToken, String sensorName) {
 final Entity entity = brooklyn().getEntity(application, entityToken);
 if (!Entitlements.isEntitled(mgmt().getEntitlementManager(), Entitlements.MODIFY_ENTITY, entity)) {
 throw WebResourceUtils.forbidden("User '%s' is not authorized to modify entity '%s'",
 Entitlements.getEntitlementContext().user(), entity);
        }
 
 AttributeSensor<?> sensor = findSensor(entity, sensorName);
 if (log.isDebugEnabled())
 log.debug("REST user "+Entitlements.getEntitlementContext()+" deleting sensor "+sensorName);
        ((EntityInternal)entity).sensors().remove(sensor);
    }
 
}