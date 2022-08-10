 @Override public Iterator<Row> getRows(Session ses, SearchRow first, SearchRow last) {
 List<Row> rows = new ArrayList<>();


 Collection<ClusterNode> nodes;


 SqlSystemViewColumnCondition idCond = conditionForColumn("NODE_ID", first, last);


 if (idCond.isEquality()) {
 try {
 UUID nodeId = uuidFromValue(idCond.valueForEquality());


 ClusterNode node = nodeId == null ? null : ctx.discovery().node(nodeId);


 if (node != null)
 nodes = Collections.singleton(node);
 else
 nodes = Collections.emptySet();
            }
 catch (Exception e) {
 nodes = Collections.emptySet();
            }
        }
 else
 nodes = F.concat(false, ctx.discovery().allNodes(), ctx.discovery().daemonNodes());


 for (ClusterNode node : nodes) {
 if (node != null) {
 ClusterMetrics metrics = node.metrics();


 rows.add(
 createRow(
 ses,
 node.id(),
 valueTimestampFromMillis(metrics.getLastUpdateTime()),
 metrics.getMaximumActiveJobs(),
 metrics.getCurrentActiveJobs(),
 metrics.getAverageActiveJobs(),
 metrics.getMaximumWaitingJobs(),
 metrics.getCurrentWaitingJobs(),
 metrics.getAverageWaitingJobs(),
 metrics.getMaximumRejectedJobs(),
 metrics.getCurrentRejectedJobs(),
 metrics.getAverageRejectedJobs(),
 metrics.getTotalRejectedJobs(),
 metrics.getMaximumCancelledJobs(),
 metrics.getCurrentCancelledJobs(),
 metrics.getAverageCancelledJobs(),
 metrics.getTotalCancelledJobs(),
 metrics.getMaximumJobWaitTime(),
 metrics.getCurrentJobWaitTime(),
                        (long)metrics.getAverageJobWaitTime(),
 metrics.getMaximumJobExecuteTime(),
 metrics.getCurrentJobExecuteTime(),
                        (long)metrics.getAverageJobExecuteTime(),
 metrics.getTotalJobsExecutionTime(),
 metrics.getTotalExecutedJobs(),
 metrics.getTotalExecutedTasks(),
 metrics.getTotalBusyTime(),
 metrics.getTotalIdleTime(),
 metrics.getCurrentIdleTime(),
 metrics.getBusyTimePercentage(),
 metrics.getIdleTimePercentage(),
 metrics.getTotalCpus(),
 metrics.getCurrentCpuLoad(),
 metrics.getAverageCpuLoad(),
 metrics.getCurrentGcCpuLoad(),
 metrics.getHeapMemoryInitialized(),
 metrics.getHeapMemoryUsed(),
 metrics.getHeapMemoryCommitted(),
 metrics.getHeapMemoryMaximum(),
 metrics.getHeapMemoryTotal(),
 metrics.getNonHeapMemoryInitialized(),
 metrics.getNonHeapMemoryUsed(),
 metrics.getNonHeapMemoryCommitted(),
 metrics.getNonHeapMemoryMaximum(),
 metrics.getNonHeapMemoryTotal(),
 metrics.getUpTime(),
 valueTimestampFromMillis(metrics.getStartTime()),
 valueTimestampFromMillis(metrics.getNodeStartTime()),
 metrics.getLastDataVersion(),
 metrics.getCurrentThreadCount(),
 metrics.getMaximumThreadCount(),
 metrics.getTotalStartedThreadCount(),
 metrics.getCurrentDaemonThreadCount(),
 metrics.getSentMessagesCount(),
 metrics.getSentBytesCount(),
 metrics.getReceivedMessagesCount(),
 metrics.getReceivedBytesCount(),
 metrics.getOutboundMessagesQueueSize()
                    )
                );
            }
        }


 return rows.iterator();
    }