public class GetOperationCompletedEvent extends OperationCompletedEvent {
 private final GetRequest[] requests;
 private final GetStatus status;


 public GetOperationCompletedEvent(
 final EventSource source,
 final Workspace workspace,
 final GetRequest[] requests,
 final GetStatus status) {
 super(source, workspace, ProcessType.GET);


 Check.notNull(requests, "requests"); //$NON-NLS-1$


 this.requests = requests;
 this.status = status;
    }


 /**
     * @return the status object produced by the get operation that caused this
     *         event. null means the get operation did not fully complete.
     */
 public GetStatus getStatus() {
 return status;
    }


 /**
     * @return the request objects that initiated this get operation.
     */
 public GetRequest[] getRequests() {
 return requests;
    }
}