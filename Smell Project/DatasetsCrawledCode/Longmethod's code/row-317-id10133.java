 protected VectorizedRowBatch setupOverflowBatch() throws HiveException {


 int initialColumnCount = vContext.firstOutputColumnIndex();
 VectorizedRowBatch overflowBatch;


 int totalNumColumns = initialColumnCount + vOutContext.getScratchColumnTypeNames().length;
 overflowBatch = new VectorizedRowBatch(totalNumColumns);


 // First, just allocate just the output columns we will be using.
 for (int i = 0; i < outputProjectionColumnMap.length; i++) {
 int outputColumn = outputProjectionColumnMap[i];
 String typeName = outputTypeInfos[i].getTypeName();
 allocateOverflowBatchColumnVector(overflowBatch, outputColumn, typeName);
    }


 // Now, add any scratch columns needed for children operators.
 int outputColumn = initialColumnCount;
 for (String typeName : vOutContext.getScratchColumnTypeNames()) {
 allocateOverflowBatchColumnVector(overflowBatch, outputColumn++, typeName);
    }


 overflowBatch.projectedColumns = outputProjectionColumnMap;
 overflowBatch.projectionSize = outputProjectionColumnMap.length;


 overflowBatch.reset();


 return overflowBatch;
  }