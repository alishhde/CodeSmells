public class TransportConnectionState extends org.apache.activemq.state.ConnectionState {


 private ConnectionContext context;
 private TransportConnection connection;
 private AtomicInteger referenceCounter = new AtomicInteger();
 private final Object connectionMutex = new Object();


 public TransportConnectionState(ConnectionInfo info, TransportConnection transportConnection) {
 super(info);
 connection = transportConnection;
    }


 public ConnectionContext getContext() {
 return context;
    }


 public TransportConnection getConnection() {
 return connection;
    }


 public void setContext(ConnectionContext context) {
 this.context = context;
    }


 public void setConnection(TransportConnection connection) {
 this.connection = connection;
    }


 public int incrementReference() {
 return referenceCounter.incrementAndGet();
    }


 public int decrementReference() {
 return referenceCounter.decrementAndGet();
    }


 public AtomicInteger getReferenceCounter() {
 return referenceCounter;
	}


 public void setReferenceCounter(AtomicInteger referenceCounter) {
 this.referenceCounter = referenceCounter;
	}


 public Object getConnectionMutex() {
 return connectionMutex;
	}
}