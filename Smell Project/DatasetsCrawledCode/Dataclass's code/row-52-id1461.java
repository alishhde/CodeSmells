public abstract class BaseObject {


 /** Type identifier of the object */
 public String type;


 /**
	 * Constructs an empty object
	 */
 public BaseObject() {
 type = this.getClass().getCanonicalName();
	}


 /**
	 * Constructs object with a given type
	 * @param type the type identifier
	 */
 public BaseObject(String type) {
 this.type = type;
	}


 /**
	 * Get type of this object.
	 * @return type of the object
	 */
 public String getType() {
 return type;
	}


}