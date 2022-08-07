public class JmxSupport {
 private final static Logger LOGGER = Logger.getLogger(JmxSupport.class.getName());
 private static final String HOTSPOT_DIAGNOSTIC_MXBEAN_NAME =
 "com.sun.management:type=HotSpotDiagnostic";    // NOI18N
 private static final String DIAGNOSTIC_COMMAND_MXBEAN_NAME =
 "com.sun.management:type=DiagnosticCommand";    // NOI18N
 private static final String ALL_OBJECTS_OPTION = "-all";    // NOI18N
 private static final String HISTOGRAM_COMMAND = "gcClassHistogram";       // NOI18N


 private JvmMXBeans mxbeans;
 private JmxModel jmxModel;
 // HotspotDiagnostic
 private boolean hotspotDiagnosticInitialized;
 private final Object hotspotDiagnosticLock = new Object();
 private HotSpotDiagnosticMXBean hotspotDiagnosticMXBean;
 private final Object readOnlyConnectionLock = new Object();
 private Boolean readOnlyConnection;
 
 private Boolean hasDumpAllThreads;
 private final Object hasDumpAllThreadsLock = new Object();
 
 JmxSupport(JmxModel jmx) {
 jmxModel = jmx;
    }
 
 private RuntimeMXBean getRuntime() {
 JvmMXBeans jmx = getJvmMXBeans();
 if (jmx != null) {
 return jmx.getRuntimeMXBean();
        }
 return null;
    }
 
 private synchronized JvmMXBeans getJvmMXBeans() {
 if (mxbeans == null) {
 if (jmxModel.getConnectionState() == ConnectionState.CONNECTED) {
 mxbeans = JvmMXBeansFactory.getJvmMXBeans(jmxModel);
            }
        }
 return mxbeans;
    }
 
 Properties getSystemProperties() {
 try {
 RuntimeMXBean runtime = getRuntime();
 if (runtime != null) {
 Properties prop = new Properties();
 prop.putAll(runtime.getSystemProperties());
 return prop;
            }
 return null;
        } catch (Exception e) {
 LOGGER.throwing(JmxSupport.class.getName(), "getSystemProperties", e); // NOI18N
 return null;
        }
    }


 synchronized boolean isReadOnlyConnection() {
 synchronized (readOnlyConnectionLock) {
 if (readOnlyConnection == null) {
 readOnlyConnection = Boolean.FALSE;
 ThreadMXBean threads = getThreadBean();
 if (threads != null) {
 try {
 threads.getThreadInfo(1);
                    } catch (SecurityException ex) {
 readOnlyConnection = Boolean.TRUE;
                    }
                }
            }
 return readOnlyConnection.booleanValue();
        }
    }
 
 ThreadMXBean getThreadBean() {
 JvmMXBeans jmx = getJvmMXBeans();
 if (jmx != null) {
 return jmx.getThreadMXBean();
        }
 return null;
    }
 
 HotSpotDiagnosticMXBean getHotSpotDiagnostic() {
 synchronized (hotspotDiagnosticLock) {
 if (hotspotDiagnosticInitialized) {
 return hotspotDiagnosticMXBean;
            }
 JvmMXBeans jmx = getJvmMXBeans();
 if (jmx != null) {
 try {
 hotspotDiagnosticMXBean = jmx.getMXBean(
 ObjectName.getInstance(HOTSPOT_DIAGNOSTIC_MXBEAN_NAME),
 HotSpotDiagnosticMXBean.class);
                } catch (MalformedObjectNameException e) {
 ErrorManager.getDefault().log(ErrorManager.WARNING,
 "Couldn't find HotSpotDiagnosticMXBean: " + // NOI18N
 e.getLocalizedMessage());
                } catch (IllegalArgumentException ex) {
 ErrorManager.getDefault().notify(ErrorManager.INFORMATIONAL, ex);
                }
            }
 hotspotDiagnosticInitialized = true;
 return hotspotDiagnosticMXBean;
        }
    }
 
 String takeThreadDump(long[] threadIds) {
 ThreadMXBean threadMXBean = getThreadBean();
 if (threadMXBean == null) {
 return null;
        }
 ThreadInfo[] threads;
 StringBuilder sb = new StringBuilder(4096);
 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  // NOI18N
 
 if (hasDumpAllThreads()) {
 threads = threadMXBean.getThreadInfo(threadIds, true, true);
        } else {
 threads = threadMXBean.getThreadInfo(threadIds, Integer.MAX_VALUE);
        }
 sb.append(df.format(new Date()) + "\n");  // NOI18N
 printThreads(sb, threadMXBean, threads);
 return sb.toString();
    }
 
 String takeThreadDump() {
 try {
 ThreadMXBean threadMXBean = getThreadBean();
 if (threadMXBean == null) {
 return null;
            }
 ThreadInfo[] threads;
 Properties prop = getSystemProperties();
 StringBuilder sb = new StringBuilder(4096);
 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  // NOI18N
 sb.append(df.format(new Date()) + "\n");
 sb.append("Full thread dump " + prop.getProperty("java.vm.name") + // NOI18N
 " (" + prop.getProperty("java.vm.version") + " " + // NOI18N
 prop.getProperty("java.vm.info") + "):\n");  // NOI18N
 if (hasDumpAllThreads()) {
 threads = threadMXBean.dumpAllThreads(true, true);
            } else {
 long[] threadIds = threadMXBean.getAllThreadIds();
 threads = threadMXBean.getThreadInfo(threadIds, Integer.MAX_VALUE);
            }
 printThreads(sb, threadMXBean, threads);
 return sb.toString();
        } catch (Exception e) {
 LOGGER.log(Level.INFO,"takeThreadDump", e); // NOI18N
 return null;
        }
    }
 
 private void printThreads(final StringBuilder sb, final ThreadMXBean threadMXBean, ThreadInfo[] threads) {
 boolean jdk16 = hasDumpAllThreads();
 
 for (ThreadInfo thread : threads) {
 if (thread != null) {
 if (jdk16) {
 print16Thread(sb, threadMXBean, thread);
                } else {
 print15Thread(sb, thread);
                }
            }
        }
    }
 
 private void print16Thread(final StringBuilder sb, final ThreadMXBean threadMXBean, final ThreadInfo thread) {
 MonitorInfo[] monitors = null;
 if (threadMXBean.isObjectMonitorUsageSupported()) {
 monitors = thread.getLockedMonitors();
        }
 sb.append("\n\"" + thread.getThreadName() + // NOI18N
 "\" - Thread t@" + thread.getThreadId() + "\n");    // NOI18N
 sb.append("   java.lang.Thread.State: " + thread.getThreadState()); // NOI18N
 sb.append("\n");   // NOI18N
 int index = 0;
 for (StackTraceElement st : thread.getStackTrace()) {
 LockInfo lock = thread.getLockInfo();
 String lockOwner = thread.getLockOwnerName();
 
 sb.append("\tat " + st.toString() + "\n");    // NOI18N
 if (index == 0) {
 if ("java.lang.Object".equals(st.getClassName()) &&     // NOI18N
 "wait".equals(st.getMethodName())) {                // NOI18N
 if (lock != null) {
 sb.append("\t- waiting on ");    // NOI18N
 printLock(sb,lock);
 sb.append("\n");    // NOI18N
                    }
                } else if (lock != null) {
 if (lockOwner == null) {
 sb.append("\t- parking to wait for ");      // NOI18N
 printLock(sb,lock);
 sb.append("\n");            // NOI18N
                    } else {
 sb.append("\t- waiting to lock ");      // NOI18N
 printLock(sb,lock);
 sb.append(" owned by \""+lockOwner+"\" t@"+thread.getLockOwnerId()+"\n");   // NOI18N
                    }
                }
            }
 printMonitors(sb, monitors, index);
 index++;
        }
 StringBuilder jnisb = new StringBuilder();
 printMonitors(jnisb, monitors, -1);
 if (jnisb.length() > 0) {
 sb.append("   JNI locked monitors:\n");
 sb.append(jnisb);
        }
 if (threadMXBean.isSynchronizerUsageSupported()) {
 sb.append("\n   Locked ownable synchronizers:");    // NOI18N
 LockInfo[] synchronizers = thread.getLockedSynchronizers();
 if (synchronizers == null || synchronizers.length == 0) {
 sb.append("\n\t- None\n");  // NOI18N
            } else {
 for (LockInfo li : synchronizers) {
 sb.append("\n\t- locked ");         // NOI18N
 printLock(sb,li);
 sb.append("\n");  // NOI18N
                }
            }
        }
    }


 private void printMonitors(final StringBuilder sb, final MonitorInfo[] monitors, final int index) {
 if (monitors != null) {
 for (MonitorInfo mi : monitors) {
 if (mi.getLockedStackDepth() == index) {
 sb.append("\t- locked ");   // NOI18N
 printLock(sb,mi);
 sb.append("\n");    // NOI18N
                }
            }
        }
    }
 
 private void print15Thread(final StringBuilder sb, final ThreadInfo thread) {
 sb.append("\n\"" + thread.getThreadName() + // NOI18N
 "\" - Thread t@" + thread.getThreadId() + "\n");    // NOI18N
 sb.append("   java.lang.Thread.State: " + thread.getThreadState()); // NOI18N
 if (thread.getLockName() != null) {
 sb.append(" on " + thread.getLockName());   // NOI18N
 if (thread.getLockOwnerName() != null) {
 sb.append(" owned by: " + thread.getLockOwnerName());   // NOI18N
            }
        }
 sb.append("\n");    // NOI18N
 for (StackTraceElement st : thread.getStackTrace()) {
 sb.append("        at " + st.toString() + "\n");    // NOI18N
        }
    }
 
 private void printLock(StringBuilder sb,LockInfo lock) {
 String id = Integer.toHexString(lock.getIdentityHashCode());
 String className = lock.getClassName();
 
 sb.append("<"+id+"> (a "+className+")");       // NOI18N
    }
 
 boolean takeHeapDump(String fileName) {
 HotSpotDiagnosticMXBean hsDiagnostic = getHotSpotDiagnostic();
 if (hsDiagnostic != null) {
 try {
 hsDiagnostic.dumpHeap(fileName,true);
            } catch (IOException ex) {
 LOGGER.log(Level.INFO,"takeHeapDump", ex); // NOI18N
 return false;
            }
 return true;
        }
 return false;
    }
 
 String getFlagValue(String name) {
 HotSpotDiagnosticMXBean hsDiagnostic = getHotSpotDiagnostic();
 if (hsDiagnostic != null) {
 VMOption option = hsDiagnostic.getVMOption(name);
 if (option != null) {
 return option.getValue();
            }
        }
 return null;
    }


 HeapHistogram takeHeapHistogram() {
 if (jmxModel.getConnectionState() == ConnectionState.CONNECTED) {
 MBeanServerConnection conn = jmxModel.getMBeanServerConnection();
 try {
 ObjectName diagCommName = new ObjectName(DIAGNOSTIC_COMMAND_MXBEAN_NAME);


 if (conn.isRegistered(diagCommName)) {
 Object histo = conn.invoke(diagCommName,
 HISTOGRAM_COMMAND,
 new Object[] {new String[] {ALL_OBJECTS_OPTION}},
 new String[] {String[].class.getName()}
                    );
 if (histo instanceof String) {
 return new HeapHistogramImpl((String)histo);
                    }
                }
            } catch (MalformedObjectNameException ex) {
 Exceptions.printStackTrace(ex);
            } catch (IOException ex) {
 LOGGER.log(Level.INFO,"takeHeapHistogram", ex); // NOI18N
            } catch (InstanceNotFoundException ex) {
 Exceptions.printStackTrace(ex);
            } catch (MBeanException ex) {
 Exceptions.printStackTrace(ex);
            } catch (ReflectionException ex) {
 Exceptions.printStackTrace(ex);
            } 
        }
 return null;
    }
 
 void setFlagValue(String name, String value) {
 HotSpotDiagnosticMXBean hsDiagnostic = getHotSpotDiagnostic();
 if (hsDiagnostic != null) {
 hsDiagnostic.setVMOption(name,value);
        }
    }
 
 private boolean hasDumpAllThreads() {
 synchronized (hasDumpAllThreadsLock) {
 if (hasDumpAllThreads == null) {
 hasDumpAllThreads = Boolean.FALSE;
 try {
 ObjectName threadObjName = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
 MBeanInfo threadInfo = jmxModel.getMBeanServerConnection().getMBeanInfo(threadObjName);
 if (threadInfo != null) {
 for (MBeanOperationInfo op : threadInfo.getOperations()) {
 if ("dumpAllThreads".equals(op.getName())) {
 hasDumpAllThreads = Boolean.TRUE;
                            }
                        }
                    }
                } catch (Exception ex) {
 LOGGER.log(Level.INFO,"hasDumpAllThreads", ex); // NOI18N
                }
            }
 return hasDumpAllThreads.booleanValue();
        }
    }
}