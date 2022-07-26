public class GroomServerStatus implements Writable {
 public static final Log LOG = LogFactory.getLog(GroomServerStatus.class);


 static {
 WritableFactories.setFactory(GroomServerStatus.class,
 new WritableFactory() {
 @Override
 public Writable newInstance() {
 return new GroomServerStatus();
          }
        });
  }


 String groomName;
 String rpcServer;
 String hostName;
 int failures;
 List<TaskStatus> taskReports;


 volatile long lastSeen;
 private int maxTasks;


 public GroomServerStatus() {
 // taskReports = new ArrayList<TaskStatus>();
 taskReports = new CopyOnWriteArrayList<TaskStatus>();
  }


 public GroomServerStatus(String groomName, List<TaskStatus> taskReports,
 int failures, int maxTasks) {
 this(groomName, taskReports, failures, maxTasks, "", "");
  }


 public GroomServerStatus(String groomName, List<TaskStatus> taskReports,
 int failures, int maxTasks, String rpc, String hostName) {
 this.groomName = groomName;
 this.taskReports = new ArrayList<TaskStatus>(taskReports);
 this.failures = failures;
 this.maxTasks = maxTasks;
 this.rpcServer = rpc;
 this.hostName = hostName;
  }


 public String getGroomName() {
 return groomName;
  }


 public String getGroomHostName() {
 return hostName;
  }


 public String getRpcServer() {
 return rpcServer;
  }


 /**
   * Get the current tasks at the GroomServer. Tasks are tracked by a
   * {@link TaskStatus} object.
   * 
   * @return a list of {@link TaskStatus} representing the current tasks at the
   *         GroomServer.
   */
 public List<TaskStatus> getTaskReports() {
 return taskReports;
  }


 public int getFailures() {
 return failures;
  }


 public long getLastSeen() {
 return lastSeen;
  }


 public void setLastSeen(long lastSeen) {
 this.lastSeen = lastSeen;
  }


 public int getMaxTasks() {
 return maxTasks;
  }


 /**
   * Return the current BSP Task count
   */
 public int countTasks() {
 int taskCount = 0;
 for (TaskStatus ts : taskReports) {
 TaskStatus.State state = ts.getRunState();
 if (state == TaskStatus.State.RUNNING
          || state == TaskStatus.State.UNASSIGNED) {
 taskCount++;
      }
    }


 return taskCount;
  }


 /**
   * For BSPMaster to distinguish between different GroomServers, because
   * BSPMaster stores using GroomServerStatus as key.
   */
 @Override
 public int hashCode() {
 int result = 17;
 result = 37 * result + groomName.hashCode();
 result = 37 * result + rpcServer.hashCode();
 result = 37 * result + hostName.hashCode();
 /*
     * result = 37*result + (int)failures; result = 37*result +
     * taskReports.hashCode(); result = 37*result +
     * (int)(lastSeen^(lastSeen>>>32)); result = 37*result + (int)maxTasks;
     */
 return result;
  }


 @Override
 public boolean equals(Object o) {
 if (o == this)
 return true;
 if (null == o)
 return false;
 if (getClass() != o.getClass())
 return false;


 GroomServerStatus s = (GroomServerStatus) o;
 if (!s.groomName.equals(groomName))
 return false;
 if (!s.rpcServer.equals(rpcServer))
 return false;
 /*
     * if(s.failures != failures) return false; if(null == s.taskReports){
     * if(null != s.taskReports) return false; }else
     * if(!s.taskReports.equals(taskReports)){ return false; } if(s.lastSeen !=
     * lastSeen) return false; if(s.maxTasks != maxTasks) return false;
     */
 return true;
  }


 /*
   * (non-Javadoc)
   * @see org.apache.hadoop.io.Writable#readFields(java.io.DataInput)
   */
 @Override
 public void readFields(DataInput in) throws IOException {
 this.groomName = Text.readString(in);
 this.rpcServer = Text.readString(in);
 this.hostName = Text.readString(in);


 this.failures = in.readInt();
 this.maxTasks = in.readInt();
 taskReports.clear();
 int numTasks = in.readInt();


 TaskStatus status;
 for (int i = 0; i < numTasks; i++) {
 status = new TaskStatus();
 status.readFields(in);
 taskReports.add(status);
    }
  }


 /*
   * (non-Javadoc)
   * @see org.apache.hadoop.io.Writable#write(java.io.DataOutput)
   */
 @Override
 public void write(DataOutput out) throws IOException {
 Text.writeString(out, groomName);
 Text.writeString(out, rpcServer);
 Text.writeString(out, hostName);


 out.writeInt(failures);
 out.writeInt(maxTasks);
 out.writeInt(taskReports.size());
 for (TaskStatus taskStatus : taskReports) {
 taskStatus.write(out);
    }
  }


 public Iterator<TaskStatus> taskReports() {
 return taskReports.iterator();
  }
}