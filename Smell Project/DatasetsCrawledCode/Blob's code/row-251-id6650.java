public class GrpcXceiverService extends
 XceiverClientProtocolServiceGrpc.XceiverClientProtocolServiceImplBase {
 public static final Logger
 LOG = LoggerFactory.getLogger(GrpcXceiverService.class);


 private final ContainerDispatcher dispatcher;
 private final boolean isGrpcTokenEnabled;
 private final TokenVerifier tokenVerifier;


 public GrpcXceiverService(ContainerDispatcher dispatcher) {
 this(dispatcher, false, null);
  }


 public GrpcXceiverService(ContainerDispatcher dispatcher,
 boolean grpcTokenEnabled, TokenVerifier tokenVerifier) {
 this.dispatcher = dispatcher;
 this.isGrpcTokenEnabled = grpcTokenEnabled;
 this.tokenVerifier = tokenVerifier;
  }


 @Override
 public StreamObserver<ContainerCommandRequestProto> send(
 StreamObserver<ContainerCommandResponseProto> responseObserver) {
 return new StreamObserver<ContainerCommandRequestProto>() {
 private final AtomicBoolean isClosed = new AtomicBoolean(false);


 @Override
 public void onNext(ContainerCommandRequestProto request) {
 try {
 if(isGrpcTokenEnabled) {
 // ServerInterceptors intercepts incoming request and creates ugi.
 tokenVerifier.verify(UserGroupInformation.getCurrentUser()
                .getShortUserName(), request.getEncodedToken());
          }
 ContainerCommandResponseProto resp =
 dispatcher.dispatch(request, null);
 responseObserver.onNext(resp);
        } catch (Throwable e) {
 LOG.error("{} got exception when processing"
                    + " ContainerCommandRequestProto {}: {}", request, e);
 responseObserver.onError(e);
        }
      }


 @Override
 public void onError(Throwable t) {
 // for now we just log a msg
 LOG.error("{}: ContainerCommand send on error. Exception: {}", t);
      }


 @Override
 public void onCompleted() {
 if (isClosed.compareAndSet(false, true)) {
 LOG.debug("{}: ContainerCommand send completed");
 responseObserver.onCompleted();
        }
      }
    };
  }
}