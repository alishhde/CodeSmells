 class TaskRemoval implements WorkerHistoryItem
  {
 private final String taskId;


 @JsonCreator
 public TaskRemoval(
 @JsonProperty("taskId") String taskId
    )
    {
 this.taskId = taskId;
    }


 @JsonProperty
 public String getTaskId()
    {
 return taskId;
    }


 @Override
 public String toString()
    {
 return "TaskRemoval{" +
 "taskId='" + taskId + '\'' +
 '}';
    }
  }