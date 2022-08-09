public class DefaultJobMasterServiceFactory implements JobMasterServiceFactory {


 private final JobMasterConfiguration jobMasterConfiguration;


 private final SlotPoolFactory slotPoolFactory;


 private final SchedulerFactory schedulerFactory;


 private final RpcService rpcService;


 private final HighAvailabilityServices haServices;


 private final JobManagerSharedServices jobManagerSharedServices;


 private final HeartbeatServices heartbeatServices;


 private final JobManagerJobMetricGroupFactory jobManagerJobMetricGroupFactory;


 private final FatalErrorHandler fatalErrorHandler;


 public DefaultJobMasterServiceFactory(
 JobMasterConfiguration jobMasterConfiguration,
 SlotPoolFactory slotPoolFactory,
 SchedulerFactory schedulerFactory,
 RpcService rpcService,
 HighAvailabilityServices haServices,
 JobManagerSharedServices jobManagerSharedServices,
 HeartbeatServices heartbeatServices,
 JobManagerJobMetricGroupFactory jobManagerJobMetricGroupFactory,
 FatalErrorHandler fatalErrorHandler) {
 this.jobMasterConfiguration = jobMasterConfiguration;
 this.slotPoolFactory = slotPoolFactory;
 this.schedulerFactory = schedulerFactory;
 this.rpcService = rpcService;
 this.haServices = haServices;
 this.jobManagerSharedServices = jobManagerSharedServices;
 this.heartbeatServices = heartbeatServices;
 this.jobManagerJobMetricGroupFactory = jobManagerJobMetricGroupFactory;
 this.fatalErrorHandler = fatalErrorHandler;
	}


 @Override
 public JobMaster createJobMasterService(JobGraph jobGraph, OnCompletionActions jobCompletionActions, ClassLoader userCodeClassloader) throws Exception {
 return new JobMaster(
 rpcService,
 jobMasterConfiguration,
 ResourceID.generate(),
 jobGraph,
 haServices,
 slotPoolFactory,
 schedulerFactory,
 jobManagerSharedServices,
 heartbeatServices,
 jobManagerJobMetricGroupFactory,
 jobCompletionActions,
 fatalErrorHandler,
 userCodeClassloader);
	}
}