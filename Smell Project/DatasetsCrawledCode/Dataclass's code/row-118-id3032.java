public class PersonEntry extends BasePersonEntry<PersonEntry> {


 /**
   * Default mutable constructor.
   */
 public PersonEntry() {
 super();
  }


 /**
   * Constructs a new instance by doing a shallow copy of data from an existing
   * {@link BaseEntry} instance.
   *
   * @param sourceEntry source entry
   */
 public PersonEntry(BaseEntry<?> sourceEntry) {
 super(sourceEntry);
  }


 @Override
 public String toString() {
 return "{PersonEntry " + super.toString() + "}";
  }


}