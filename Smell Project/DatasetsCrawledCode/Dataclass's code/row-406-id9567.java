@SuppressWarnings("serial")
public class ObjectRetrievalFailureException extends DataRetrievalFailureException {


 @Nullable
 private final Object persistentClass;


 @Nullable
 private final Object identifier;




 /**
	 * Create a general ObjectRetrievalFailureException with the given message,
	 * without any information on the affected object.
	 * @param msg the detail message
	 * @param cause the source exception
	 */
 public ObjectRetrievalFailureException(String msg, Throwable cause) {
 super(msg, cause);
 this.persistentClass = null;
 this.identifier = null;
	}


 /**
	 * Create a new ObjectRetrievalFailureException for the given object,
	 * with the default "not found" message.
	 * @param persistentClass the persistent class
	 * @param identifier the ID of the object that should have been retrieved
	 */
 public ObjectRetrievalFailureException(Class<?> persistentClass, Object identifier) {
 this(persistentClass, identifier,
 "Object of class [" + persistentClass.getName() + "] with identifier [" + identifier + "]: not found",
 null);
	}


 /**
	 * Create a new ObjectRetrievalFailureException for the given object,
	 * with the given explicit message and exception.
	 * @param persistentClass the persistent class
	 * @param identifier the ID of the object that should have been retrieved
	 * @param msg the detail message
	 * @param cause the source exception
	 */
 public ObjectRetrievalFailureException(
 Class<?> persistentClass, Object identifier, String msg, @Nullable Throwable cause) {


 super(msg, cause);
 this.persistentClass = persistentClass;
 this.identifier = identifier;
	}


 /**
	 * Create a new ObjectRetrievalFailureException for the given object,
	 * with the default "not found" message.
	 * @param persistentClassName the name of the persistent class
	 * @param identifier the ID of the object that should have been retrieved
	 */
 public ObjectRetrievalFailureException(String persistentClassName, Object identifier) {
 this(persistentClassName, identifier,
 "Object of class [" + persistentClassName + "] with identifier [" + identifier + "]: not found",
 null);
	}


 /**
	 * Create a new ObjectRetrievalFailureException for the given object,
	 * with the given explicit message and exception.
	 * @param persistentClassName the name of the persistent class
	 * @param identifier the ID of the object that should have been retrieved
	 * @param msg the detail message
	 * @param cause the source exception
	 */
 public ObjectRetrievalFailureException(
 String persistentClassName, Object identifier, String msg, @Nullable Throwable cause) {


 super(msg, cause);
 this.persistentClass = persistentClassName;
 this.identifier = identifier;
	}




 /**
	 * Return the persistent class of the object that was not found.
	 * If no Class was specified, this method returns null.
	 */
 @Nullable
 public Class<?> getPersistentClass() {
 return (this.persistentClass instanceof Class ? (Class<?>) this.persistentClass : null);
	}


 /**
	 * Return the name of the persistent class of the object that was not found.
	 * Will work for both Class objects and String names.
	 */
 @Nullable
 public String getPersistentClassName() {
 if (this.persistentClass instanceof Class) {
 return ((Class<?>) this.persistentClass).getName();
		}
 return (this.persistentClass != null ? this.persistentClass.toString() : null);
	}


 /**
	 * Return the identifier of the object that was not found.
	 */
 @Nullable
 public Object getIdentifier() {
 return this.identifier;
	}


}