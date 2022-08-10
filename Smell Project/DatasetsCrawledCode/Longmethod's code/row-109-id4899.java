 @Override
 protected void initializeOp(Configuration hconf) throws HiveException {


 // If there is a sort-merge join followed by a regular join, the SMBJoinOperator may not
 // get initialized at all. Consider the following query:
 // A SMB B JOIN C
 // For the mapper processing C, The SMJ is not initialized, no need to close it either.
 initDone = true;


 super.initializeOp(hconf);


 closeCalled = false;


 this.firstFetchHappened = false;
 this.inputFileChanged = false;


 // get the largest table alias from order
 int maxAlias = 0;
 for (byte pos = 0; pos < order.length; pos++) {
 if (pos > maxAlias) {
 maxAlias = pos;
      }
    }
 maxAlias += 1;


 nextGroupStorage = new RowContainer[maxAlias];
 candidateStorage = new RowContainer[maxAlias];
 keyWritables = new ArrayList[maxAlias];
 nextKeyWritables = new ArrayList[maxAlias];
 fetchDone = new boolean[maxAlias];
 foundNextKeyGroup = new boolean[maxAlias];


 int bucketSize;


 // For backwards compatibility reasons we honor the older
 // HIVEMAPJOINBUCKETCACHESIZE if set different from default.
 // By hive 0.13 we should remove this code.
 int oldVar = HiveConf.getIntVar(hconf, HiveConf.ConfVars.HIVEMAPJOINBUCKETCACHESIZE);
 if (oldVar != 100) {
 bucketSize = oldVar;
    } else {
 bucketSize = HiveConf.getIntVar(hconf, HiveConf.ConfVars.HIVESMBJOINCACHEROWS);
    }


 for (byte pos = 0; pos < order.length; pos++) {
 RowContainer<List<Object>> rc = JoinUtil.getRowContainer(hconf,
 rowContainerStandardObjectInspectors[pos],
 pos, bucketSize,spillTableDesc, conf, !hasFilter(pos),
 reporter);
 nextGroupStorage[pos] = rc;
 RowContainer<List<Object>> candidateRC = JoinUtil.getRowContainer(hconf,
 rowContainerStandardObjectInspectors[pos],
 pos, bucketSize,spillTableDesc, conf, !hasFilter(pos),
 reporter);
 candidateStorage[pos] = candidateRC;
    }
 tagToAlias = conf.convertToArray(conf.getTagToAlias(), String.class);


 for (byte pos = 0; pos < order.length; pos++) {
 if (pos != posBigTable) {
 fetchDone[pos] = false;
      }
 foundNextKeyGroup[pos] = false;
    }
  }