 public static class NewItemFinishEvent<T extends Serializable> extends NewItemEvent<T> {


 private static final String EVENT_DESCRIPTION = "finish";


 private Serializable result;


 public NewItemFinishEvent(final T item, final AjaxRequestTarget target) {
 super(item, target);
        }


 @Override
 public String getEventDescription() {
 return NewItemFinishEvent.EVENT_DESCRIPTION;
        }


 public NewItemFinishEvent<T> setResult(final Serializable result) {
 this.result = result;
 return this;
        }


 public Serializable getResult() {
 return result;
        }
    }