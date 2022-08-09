@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchedulerInfo {
 private String type;
 private double capacity;
 private double usedCapacity;
 private double maxCapacity;
 private String queueName;
 private Queues queues;


 public Queues getQueues() {
 return queues;
    }


 public void setQueues(Queues queues) {
 this.queues = queues;
    }




 public double getUsedCapacity() {
 return usedCapacity;
    }


 public void setUsedCapacity(double usedCapacity) {
 this.usedCapacity = usedCapacity;
    }


 public String getType() {
 return type;
    }


 public void setType(String type) {
 this.type = type;
    }


 public double getCapacity() {
 return capacity;
    }


 public void setCapacity(double capacity) {
 this.capacity = capacity;
    }


 public double getMaxCapacity() {
 return maxCapacity;
    }


 public void setMaxCapacity(double maxCapacity) {
 this.maxCapacity = maxCapacity;
    }


 public String getQueueName() {
 return queueName;
    }


 public void setQueueName(String queueName) {
 this.queueName = queueName;
    }


}