@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractCompendiumHandler extends ServiceTracker implements MBeanHandler {


 protected final JMXAgentContext agentContext;
 protected StandardMBean mbean;
 protected final AtomicLong trackedId = new AtomicLong();
 
 /**
     * 
     * @param agentContext
     * @param filter
     */
 protected AbstractCompendiumHandler(JMXAgentContext agentContext, Filter filter) {
 super(agentContext.getBundleContext(), filter, null);
 this.agentContext = agentContext;
    }


 /**
     * 
     * @param agentContext
     * @param clazz
     */
 protected AbstractCompendiumHandler(JMXAgentContext agentContext, String clazz) {
 super(agentContext.getBundleContext(), clazz, null);
 this.agentContext = agentContext;
    }


 /*
     * (non-Javadoc)
     * 
     * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
     */
 public Object addingService(ServiceReference reference) {
 Logger logger = agentContext.getLogger();
 Object trackedService = null;
 long serviceId = (Long) reference.getProperty(Constants.SERVICE_ID);
 //API stipulates versions for compendium services with static ObjectName
 //This shouldn't happen but added as a consistency check
 if (trackedId.compareAndSet(0, serviceId)) {
 logger.log(LogService.LOG_INFO, "Registering MBean with ObjectName [" + getName() + "] for service with "
                    + Constants.SERVICE_ID + " [" + serviceId + "]");
 trackedService = context.getService(reference);
 mbean = constructInjectMBean(trackedService);
 agentContext.registerMBean(AbstractCompendiumHandler.this);
        } else {
 String serviceDescription = getServiceDescription(reference);
 logger.log(LogService.LOG_WARNING, "Detected secondary ServiceReference for [" + serviceDescription
                    + "] with " + Constants.SERVICE_ID + " [" + serviceId + "] Only 1 instance will be JMX managed");
        }
 return trackedService;
    }


 /*
     * (non-Javadoc)
     * 
     * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
     */
 public void removedService(ServiceReference reference, Object service) {
 Logger logger = agentContext.getLogger();
 long serviceID = (Long) reference.getProperty(Constants.SERVICE_ID);
 if (trackedId.compareAndSet(serviceID, 0)) {
 logger.log(LogService.LOG_INFO, "Unregistering MBean with ObjectName [" + getName() + "] for service with "
                    + Constants.SERVICE_ID + " [" + serviceID + "]"); 
 agentContext.unregisterMBean(AbstractCompendiumHandler.this);
 context.ungetService(reference);
        } else {
 String serviceDescription = getServiceDescription(reference);
 logger.log(LogService.LOG_WARNING, "ServiceReference for [" + serviceDescription + "] with "
                    + Constants.SERVICE_ID + " [" + serviceID + "] is not currently JMX managed");
        }
    }


 private String getServiceDescription(ServiceReference reference) {
 String serviceDescription = (String) reference.getProperty(Constants.SERVICE_DESCRIPTION);
 if (serviceDescription == null) {
 Object obj = reference.getProperty(Constants.OBJECTCLASS);
 if (obj instanceof String[]) {
 StringBuilder sb = new StringBuilder();
 for (String s : (String[]) obj) {
 if (sb.length() > 0) {
 sb.append(", ");
                    }
 sb.append(s);
                }
 serviceDescription = sb.toString();
            } else {
 serviceDescription = obj.toString();
            }
        }
 return serviceDescription;
    }


 /**
     * Gets the <code>StandardMBean</code> managed by this handler when the backing service is available or null
     * 
     * @see org.apache.aries.jmx.MBeanHandler#getMbean()
     */
 public StandardMBean getMbean() {
 return mbean;
    }


 /**
     * Implement this method to construct an appropriate {@link StandardMBean} instance which is backed by the supplied
     * service tracked by this handler
     * 
     * @param targetService
     *            the compendium service tracked by this handler
     * @return The <code>StandardMBean</code> instance whose registration lifecycle will be managed by this handler
     */
 protected abstract StandardMBean constructInjectMBean(Object targetService);


 /**
     * The base name of the MBean. Will be expanded with the framework name and the UUID.
     * @return
     */
 protected abstract String getBaseName();


 /**
     * @see org.apache.aries.jmx.MBeanHandler#getName()
     */
 public String getName() {
 return ObjectNameUtils.createFullObjectName(context, getBaseName());
    }
}