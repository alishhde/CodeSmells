public class BasicAttributeSensor<T> extends BasicSensor<T> implements AttributeSensor<T> {
 private static final long serialVersionUID = -2493209215974820300L;
 
 private final SensorPersistenceMode persistence;


 public BasicAttributeSensor(Class<T> type, String name) {
 this(type, name, name);
    }
 
 public BasicAttributeSensor(Class<T> type, String name, String description) {
 this(type, name, description, SensorPersistenceMode.REQUIRED);
    }
 
 public BasicAttributeSensor(TypeToken<T> typeToken, String name) {
 this(typeToken, name, name);
    }
 public BasicAttributeSensor(TypeToken<T> typeToken, String name, String description) {
 this(typeToken, name, description, SensorPersistenceMode.REQUIRED);
    }
 
 public BasicAttributeSensor(Class<T> type, String name, String description, SensorPersistenceMode persistence) {
 this(type, null, name, description, persistence);
    }
 public BasicAttributeSensor(TypeToken<T> typeToken, String name, String description, SensorPersistenceMode persistence) {
 this(null, typeToken, name, description, persistence);
    }
 public BasicAttributeSensor(Class<T> type, TypeToken<T> typeToken, String name, String description, SensorPersistenceMode persistence) {
 super(type, typeToken, name, description);
 this.persistence = checkNotNull(persistence, "persistence");
    }


 @Override
 public SensorPersistenceMode getPersistenceMode() {
 // persistence could be null if deserializing state written by an old version; in which case default to 'required'
 return (persistence != null) ? persistence : SensorPersistenceMode.REQUIRED;
    }
}