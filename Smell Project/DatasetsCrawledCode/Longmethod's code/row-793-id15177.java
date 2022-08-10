 @Override
 public RelOptCost getCost(HiveJoin join) {
 final RelMetadataQuery mq = join.getCluster().getMetadataQuery();
 // 1. Sum of input cardinalities
 final Double leftRCount = mq.getRowCount(join.getLeft());
 final Double rightRCount = mq.getRowCount(join.getRight());
 if (leftRCount == null || rightRCount == null) {
 return null;
      }
 final double rCount = leftRCount + rightRCount;
 // 2. CPU cost = HashTable  construction  cost  +
 //               join cost
 ImmutableList<Double> cardinalities = new ImmutableList.Builder<Double>().
 add(leftRCount).
 add(rightRCount).
 build();
 ImmutableBitSet.Builder streamingBuilder = ImmutableBitSet.builder();
 switch (join.getStreamingSide()) {
 case LEFT_RELATION:
 streamingBuilder.set(0);
 break;
 case RIGHT_RELATION:
 streamingBuilder.set(1);
 break;
 default:
 return null;
      }
 ImmutableBitSet streaming = streamingBuilder.build();
 final double cpuCost = algoUtils.computeBucketMapJoinCPUCost(cardinalities, streaming);
 // 3. IO cost = cost of transferring small tables to join node *
 //              degree of parallelism
 final Double leftRAverageSize = mq.getAverageRowSize(join.getLeft());
 final Double rightRAverageSize = mq.getAverageRowSize(join.getRight());
 if (leftRAverageSize == null || rightRAverageSize == null) {
 return null;
      }
 ImmutableList<Pair<Double,Double>> relationInfos = new ImmutableList.Builder<Pair<Double,Double>>().
 add(new Pair<Double,Double>(leftRCount,leftRAverageSize)).
 add(new Pair<Double,Double>(rightRCount,rightRAverageSize)).
 build();
 //TODO: No Of buckets is not same as no of splits
 JoinAlgorithm oldAlgo = join.getJoinAlgorithm();
 join.setJoinAlgorithm(TezBucketJoinAlgorithm.INSTANCE);
 final int parallelism = mq.splitCount(join) == null
              ? 1 : mq.splitCount(join);
 join.setJoinAlgorithm(oldAlgo);


 final double ioCost = algoUtils.computeBucketMapJoinIOCost(relationInfos, streaming, parallelism);
 // 4. Result
 return HiveCost.FACTORY.makeCost(rCount, cpuCost, ioCost);
    }