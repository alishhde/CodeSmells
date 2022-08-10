 private void handleRemainder() {
 final int remainingRecordCount = incoming.getRecordCount() - remainderIndex;
 assert this.memoryManager.incomingBatch == incoming;
 final int recordsToProcess = Math.min(remainingRecordCount, memoryManager.getOutputRowCount());


 if (!doAlloc(recordsToProcess)) {
 outOfMemory = true;
 return;
    }


 logger.trace("handleRemainder: remaining RC {}, toProcess {}, remainder index {}, incoming {}, Project {}",
 remainingRecordCount, recordsToProcess, remainderIndex, incoming, this);


 long projectStartTime = System.currentTimeMillis();
 final int projRecords = projector.projectRecords(this.incoming, remainderIndex, recordsToProcess, 0);
 long projectEndTime = System.currentTimeMillis();


 logger.trace("handleRemainder: projection: records {}, time {} ms", projRecords,(projectEndTime - projectStartTime));


 if (projRecords < remainingRecordCount) {
 setValueCount(projRecords);
 this.recordCount = projRecords;
 remainderIndex += projRecords;
    } else {
 setValueCount(remainingRecordCount);
 hasRemainder = false;
 remainderIndex = 0;
 for (final VectorWrapper<?> v : incoming) {
 v.clear();
      }
 this.recordCount = remainingRecordCount;
    }
 // In case of complex writer expression, vectors would be added to batch run-time.
 // We have to re-build the schema.
 if (complexWriters != null) {
 container.buildSchema(SelectionVectorMode.NONE);
    }


 memoryManager.updateOutgoingStats(projRecords);
 RecordBatchStats.logRecordBatchStats(RecordBatchIOType.OUTPUT, this, getRecordBatchStatsContext());
  }