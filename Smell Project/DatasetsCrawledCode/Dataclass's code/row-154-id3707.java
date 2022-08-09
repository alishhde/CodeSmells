@Listener(clustered = false, sync = false)
public class InfinispanAsyncLocalEventListener extends InfinispanSyncLocalEventListener {
 public InfinispanAsyncLocalEventListener(InfinispanConsumer consumer, Set<String> eventTypes) {
 super(consumer, eventTypes);
    }
}