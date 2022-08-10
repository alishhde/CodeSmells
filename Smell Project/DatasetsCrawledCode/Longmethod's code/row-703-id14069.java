 private boolean optimizeForGoal(ClusterModel clusterModel,
 Goal goal,
 GoalViolations goalViolations,
 Set<Integer> excludedBrokersForLeadership,
 Set<Integer> excludedBrokersForReplicaMove)
 throws KafkaCruiseControlException {
 if (clusterModel.topics().isEmpty()) {
 LOG.info("Skipping goal violation detection because the cluster model does not have any topic.");
 return false;
    }
 Map<TopicPartition, List<Integer>> initReplicaDistribution = clusterModel.getReplicaDistribution();
 Map<TopicPartition, Integer> initLeaderDistribution = clusterModel.getLeaderDistribution();
 try {
 goal.optimize(clusterModel, new HashSet<>(), new OptimizationOptions(excludedTopics(clusterModel),
 excludedBrokersForLeadership,
 excludedBrokersForReplicaMove));
    } catch (OptimizationFailureException ofe) {
 // An OptimizationFailureException indicates (1) a hard goal violation that cannot be fixed typically due to
 // lack of physical hardware (e.g. insufficient number of racks to satisfy rack awareness, insufficient number
 // of brokers to satisfy Replica Capacity Goal, or insufficient number of resources to satisfy resource
 // capacity goals), or (2) a failure to move offline replicas away from dead brokers/disks.
 goalViolations.addViolation(goal.name(), false);
 return true;
    }
 Set<ExecutionProposal> proposals = AnalyzerUtils.getDiff(initReplicaDistribution, initLeaderDistribution, clusterModel);
 LOG.trace("{} generated {} proposals", goal.name(), proposals.size());
 if (!proposals.isEmpty()) {
 // A goal violation that can be optimized by applying the generated proposals.
 goalViolations.addViolation(goal.name(), true);
 return true;
    } else {
 // The goal is already satisfied.
 return false;
    }
  }