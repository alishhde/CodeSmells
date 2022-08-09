 private static class GridMBeanServerData {
 /** Set of grid names for selected MBeanServer. */
 private Collection<String> igniteInstanceNames = new HashSet<>();


 /** */
 private ObjectName mbean;


 /** Count of grid instances. */
 private int cnt;


 /**
             * Create data container.
             *
             * @param mbean Object name of MBean.
             */
 GridMBeanServerData(ObjectName mbean) {
 assert mbean != null;


 this.mbean = mbean;
            }


 /**
             * Add Ignite instance name.
             *
             * @param igniteInstanceName Ignite instance name.
             */
 public void addIgniteInstance(String igniteInstanceName) {
 igniteInstanceNames.add(igniteInstanceName);
            }


 /**
             * Remove Ignite instance name.
             *
             * @param igniteInstanceName Ignite instance name.
             */
 public void removeIgniteInstance(String igniteInstanceName) {
 igniteInstanceNames.remove(igniteInstanceName);
            }


 /**
             * Returns {@code true} if data contains the specified
             * Ignite instance name.
             *
             * @param igniteInstanceName Ignite instance name.
             * @return {@code true} if data contains the specified Ignite instance name.
             */
 public boolean containsIgniteInstance(String igniteInstanceName) {
 return igniteInstanceNames.contains(igniteInstanceName);
            }


 /**
             * Gets name used in MBean server.
             *
             * @return Object name of MBean.
             */
 public ObjectName getMbean() {
 return mbean;
            }


 /**
             * Gets number of grid instances working with MBeanServer.
             *
             * @return Number of grid instances.
             */
 public int getCounter() {
 return cnt;
            }


 /**
             * Sets number of grid instances working with MBeanServer.
             *
             * @param cnt Number of grid instances.
             */
 public void setCounter(int cnt) {
 this.cnt = cnt;
            }
        }