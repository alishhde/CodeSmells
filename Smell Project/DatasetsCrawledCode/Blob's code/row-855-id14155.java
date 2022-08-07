@SuppressWarnings("deprecation")
public class MRBaseForCommonInstructions extends MapReduceBase
{
 
 //indicate whether the matrix value in this mapper is a matrix cell or a matrix block
 protected Class<? extends MatrixValue> valueClass;
 
 //a cache to hold the intermediate results
 protected CachedValueMap cachedValues=new CachedValueMap();
 
 //distributed cache data handling
 public static boolean isJobLocal = false; //set from MapperBase
 public static HashMap<Byte, DistributedCacheInput> dcValues = new HashMap<>();
 
 protected HashMap<Byte, MatrixCharacteristics> dimensions=new HashMap<>();
 
 //temporary variables
 protected IndexedMatrixValue tempValue=null;
 protected IndexedMatrixValue zeroInput=null;	


 @Override
 public void configure(JobConf job)
	{	
 //whether to use the cell representation or the block representation
 valueClass=MRJobConfiguration.getMatrixValueClass(job);
 //allocate space for temporary variables
 tempValue=new IndexedMatrixValue(valueClass);
 zeroInput=new IndexedMatrixValue(valueClass);
 
 //matrix characteristics inputs/outputs
 byte[] inputIX = MRJobConfiguration.getInputIndexesInMapper(job);
 for( byte ix : inputIX )
 dimensions.put(ix, MRJobConfiguration.getMatrixCharacteristicsForInput(job, ix));	
 
 byte[] mapOutputIX = MRJobConfiguration.getOutputIndexesInMapper(job);
 for(byte ix : mapOutputIX)
 dimensions.put(ix, MRJobConfiguration.getMatrixCharacteristicsForMapOutput(job, ix));
 
 byte[] outputIX = MRJobConfiguration.getResultIndexes(job);
 for( byte ix : outputIX )
 dimensions.put(ix, MRJobConfiguration.getMatrixCharacteristicsForOutput(job, ix));	
 
 //matrix characteristics intermediates
 byte[] immediateIndexes=MRJobConfiguration.getIntermediateMatrixIndexes(job);
 if(immediateIndexes!=null)
		{
 for(byte index: immediateIndexes)
 dimensions.put(index, MRJobConfiguration.getIntermediateMatrixCharactristics(job, index));			
		}
	}


 protected void collectOutput_N_Increase_Counter(MatrixIndexes indexes, MatrixValue value, 
 int i, Reporter reporter, CollectMultipleConvertedOutputs collectFinalMultipleOutputs, 
 byte[] resultDimsUnknown, long[] resultsNonZeros, long[] resultsMaxRowDims, 
 long[] resultsMaxColDims) throws IOException
	{
 collectFinalMultipleOutputs.collectOutput(indexes, value, i, reporter);
 resultsNonZeros[i]+=value.getNonZeros();
 
 if ( resultDimsUnknown[i] == (byte) 1 ) 
		{
 // compute dimensions for the resulting matrix
 
 // find the maximum row index and column index encountered in current output block/cell 
 long maxrow = getMaxDimension(indexes, value, true);
 long maxcol = getMaxDimension(indexes, value, false);
 
 if ( maxrow > resultsMaxRowDims[i] )
 resultsMaxRowDims[i] = maxrow;
 
 if ( maxcol > resultsMaxColDims[i] )
 resultsMaxColDims[i] = maxcol;
		}
 else if(resultDimsUnknown[i] == (byte) 2)
		{
 if ( indexes.getRowIndex() > resultsMaxRowDims[i] )
 resultsMaxRowDims[i] = indexes.getRowIndex();
 
 if ( indexes.getColumnIndex() > resultsMaxColDims[i] )
 resultsMaxColDims[i] = indexes.getColumnIndex();
		}
	}


 protected void processMixedInstructions(ArrayList<MRInstruction> mixed_instructions) {
 if( mixed_instructions != null )
 for( MRInstruction ins : mixed_instructions )
 processOneInstruction(ins, valueClass, cachedValues, tempValue, zeroInput);
	}


 protected void processOneInstruction(MRInstruction ins, Class<? extends MatrixValue> valueClass,
 CachedValueMap cachedValues, IndexedMatrixValue tempValue, IndexedMatrixValue zeroInput) {
 //Timing time = new Timing(true);
 
 if ( ins instanceof AggregateBinaryInstruction ) {
 byte input = ((AggregateBinaryInstruction)ins).input1;
 MatrixCharacteristics dim=dimensions.get(input);
 if(dim==null)
 throw new DMLRuntimeException("dimension for instruction "+ins+"  is unset!!!");
 ins.processInstruction(valueClass, cachedValues, tempValue, zeroInput, dim.getRowsPerBlock(), dim.getColsPerBlock());
		}
 else if(ins instanceof ZeroOutInstruction || ins instanceof AggregateUnaryInstruction 
				|| ins instanceof RangeBasedReIndexInstruction || ins instanceof CumulativeSplitInstruction)
		{
 byte input=((UnaryMRInstructionBase) ins).input;
 MatrixCharacteristics dim=dimensions.get(input);
 if(dim==null)
 throw new DMLRuntimeException("dimension for instruction "+ins+"  is unset!!!");
 if( ins instanceof CumulativeAggregateInstruction )
				((CumulativeAggregateInstruction)ins).setMatrixCharacteristics(dim);
 if( ins instanceof CumulativeSplitInstruction )
				((CumulativeSplitInstruction)ins).setMatrixCharacteristics(dim);
 ins.processInstruction(valueClass, cachedValues, tempValue, zeroInput, dim.getRowsPerBlock(), dim.getColsPerBlock());
		}
 else if( ins instanceof ReorgInstruction )
		{
 ReorgInstruction rinst = (ReorgInstruction) ins;
 byte input = rinst.input;
 MatrixCharacteristics dim = dimensions.get(input);
 if(dim==null)
 throw new DMLRuntimeException("dimension for instruction "+ins+"  is unset!!!");
 rinst.setInputMatrixCharacteristics(dim);
 rinst.setOutputEmptyBlocks(!(this instanceof MMCJMRMapper)); //MMCJMRMapper does not output empty blocks, no need to generate
 ins.processInstruction(valueClass, cachedValues, tempValue, zeroInput, dim.getRowsPerBlock(), dim.getColsPerBlock());
		}
 else if( ins instanceof MatrixReshapeMRInstruction )
		{
 MatrixReshapeMRInstruction mrins = (MatrixReshapeMRInstruction) ins;
 byte input = mrins.input;
 byte output = mrins.output; 
 MatrixCharacteristics dimIn=dimensions.get(input);
 MatrixCharacteristics dimOut=dimensions.get(output);
 if(dimIn==null || dimOut==null)
 throw new DMLRuntimeException("dimension for instruction "+ins+"  is unset!!!");
 mrins.setMatrixCharacteristics(dimIn, dimOut);
 mrins.processInstruction(valueClass, cachedValues, tempValue, zeroInput, dimIn.getRowsPerBlock(), dimIn.getColsPerBlock());
		}
 else if(ins instanceof AppendMInstruction)
		{
 byte input=((AppendMInstruction) ins).input1;
 MatrixCharacteristics dim=dimensions.get(input);
 if(dim==null)
 throw new DMLRuntimeException("dimension for instruction "+ins+"  is unset!!!");
 ins.processInstruction(valueClass, cachedValues, tempValue, zeroInput, dim.getRowsPerBlock(), dim.getColsPerBlock());
		}
 else if(ins instanceof BinaryMInstruction || ins instanceof RemoveEmptyMRInstruction )
		{
 byte input=((BinaryMRInstructionBase) ins).input1;
 MatrixCharacteristics dim=dimensions.get(input);
 if(dim==null)
 throw new DMLRuntimeException("dimension for instruction "+ins+"  is unset!!!");
 ins.processInstruction(valueClass, cachedValues, tempValue, zeroInput, dim.getRowsPerBlock(), dim.getColsPerBlock());
		}
 else if(ins instanceof AppendGInstruction)
		{
 AppendGInstruction arinst = ((AppendGInstruction) ins);
 byte input = arinst.input1;
 MatrixCharacteristics dimIn=dimensions.get(input);
 if( dimIn==null )
 throw new DMLRuntimeException("Dimensions for instruction "+arinst+"  is unset!!!");
 arinst.processInstruction(valueClass, cachedValues, tempValue, zeroInput, dimIn.getRowsPerBlock(), dimIn.getColsPerBlock());
		}
 else if(ins instanceof UnaryMRInstructionBase)
		{
 UnaryMRInstructionBase rinst = (UnaryMRInstructionBase) ins;
 MatrixCharacteristics dimIn=dimensions.get(rinst.input);
 if( dimIn==null )
 throw new DMLRuntimeException("Dimensions for instruction "+rinst+"  is unset!!!");
 rinst.processInstruction(valueClass, cachedValues, tempValue, zeroInput, dimIn.getRowsPerBlock(), dimIn.getColsPerBlock());
		}
 else if(ins instanceof BinaryMRInstructionBase)
		{
 BinaryMRInstructionBase rinst = (BinaryMRInstructionBase) ins;
 MatrixCharacteristics dimIn=dimensions.get(rinst.input1);
 if( dimIn!=null ) //not set for all
 rinst.processInstruction(valueClass, cachedValues, tempValue, zeroInput, dimIn.getRowsPerBlock(), dimIn.getColsPerBlock());
 else
 ins.processInstruction(valueClass, cachedValues, tempValue, zeroInput, -1, -1);
		}
 else
 ins.processInstruction(valueClass, cachedValues, tempValue, zeroInput, -1, -1);
 
 //System.out.println(ins.getMRInstructionType()+" in "+time.stop());
	}
 
 /**
	 * Reset in-memory state from distributed cache (required only for
	 * local job runner) 
	 */
 public static void resetDistCache()
	{
 for(DistributedCacheInput dcInput : dcValues.values() ) 
 dcInput.reset();
 dcValues.clear();
	}


 protected void setupDistCacheFiles(JobConf job) 
 throws IOException 
	{
 
 if ( MRJobConfiguration.getDistCacheInputIndices(job) == null )
 return;
 
 //boolean isJobLocal = false;
 isJobLocal = InfrastructureAnalyzer.isLocalMode(job);
 
 String[] inputIndices = MRJobConfiguration.getInputPaths(job);
 String[] dcIndices = MRJobConfiguration.getDistCacheInputIndices(job).split(Instruction.INSTRUCTION_DELIM);
 Path[] dcFiles = DistributedCache.getLocalCacheFiles(job);
 PDataPartitionFormat[] inputPartitionFormats = MRJobConfiguration.getInputPartitionFormats(job);
 
 DistributedCacheInput[] dcInputs = new DistributedCacheInput[dcIndices.length];
 for(int i=0; i < dcIndices.length; i++) {
 byte inputIndex = Byte.parseByte(dcIndices[i]);
 
 //load if not already present (jvm reuse)
 if( !dcValues.containsKey(inputIndex) )
        	{
 // When the job is in local mode, files can be read from HDFS directly -- use 
 // input paths as opposed to "local" paths prepared by DistributedCache. 
 Path p = null;
 if(isJobLocal)
 p = new Path(inputIndices[ Byte.parseByte(dcIndices[i]) ]);
 else
 p = dcFiles[i];
 
 dcInputs[i] = new DistributedCacheInput(
 p, 
 MRJobConfiguration.getNumRows(job, inputIndex), //rlens[inputIndex],
 MRJobConfiguration.getNumColumns(job, inputIndex), //clens[inputIndex],
 MRJobConfiguration.getNumRowsPerBlock(job, inputIndex), //brlens[inputIndex],
 MRJobConfiguration.getNumColumnsPerBlock(job, inputIndex), //bclens[inputIndex],
 inputPartitionFormats[inputIndex]
								);
 dcValues.put(inputIndex, dcInputs[i]);
        	}
		}	
	}
 
 /**
	 * Returns the maximum row or column dimension of the given key and value pair. 
	 * 
	 * @param key matrix indexes
	 * @param value MatrixValue of either type MatrixCell or MatrixBlock
	 * @param row if true return row dimension, else return column dimension
	 * @return maximum row or column dimension, or 0 if MatrixValue not MatrixCell or MatrixBlock
	 */
 private static long getMaxDimension( MatrixIndexes key, MatrixValue value, boolean row ) {
 if( value instanceof MatrixCell )
 return row ? key.getRowIndex() : key.getColumnIndex();
 else if( value instanceof MatrixBlock )
 return row ? value.getNumRows() : value.getNumColumns();
 return 0;
	}
}