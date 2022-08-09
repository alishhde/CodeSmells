public class MachineStoppedEvent extends GwtEvent<MachineStoppedEvent.Handler> {


 public static final Type<MachineStoppedEvent.Handler> TYPE = new Type<>();


 private final MachineImpl machine;


 public MachineStoppedEvent(MachineImpl machine) {
 this.machine = machine;
  }


 /** Returns the stopped machine. */
 public MachineImpl getMachine() {
 return machine;
  }


 @Override
 public Type<Handler> getAssociatedType() {
 return TYPE;
  }


 @Override
 protected void dispatch(Handler handler) {
 handler.onMachineStopped(this);
  }


 public interface Handler extends EventHandler {
 void onMachineStopped(MachineStoppedEvent event);
  }
}