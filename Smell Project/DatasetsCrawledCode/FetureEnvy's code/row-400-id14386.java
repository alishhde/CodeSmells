 private static void countCompiledInstructions(ProgramBlock pb, ExplainCounts counts, boolean MR, boolean CP, boolean SP) 
	{
 if (pb instanceof WhileProgramBlock)
		{
 WhileProgramBlock tmp = (WhileProgramBlock)pb;
 countCompiledInstructions(tmp.getPredicate(), counts, MR, CP, SP);
 for (ProgramBlock pb2 : tmp.getChildBlocks())
 countCompiledInstructions(pb2, counts, MR, CP, SP);
		}
 else if (pb instanceof IfProgramBlock)
		{
 IfProgramBlock tmp = (IfProgramBlock)pb;	
 countCompiledInstructions(tmp.getPredicate(), counts, MR, CP, SP);
 for( ProgramBlock pb2 : tmp.getChildBlocksIfBody() )
 countCompiledInstructions(pb2, counts, MR, CP, SP);
 for( ProgramBlock pb2 : tmp.getChildBlocksElseBody() )
 countCompiledInstructions(pb2, counts, MR, CP, SP);
		}
 else if (pb instanceof ForProgramBlock) //includes ParFORProgramBlock
		{ 
 ForProgramBlock tmp = (ForProgramBlock)pb;	
 countCompiledInstructions(tmp.getFromInstructions(), counts, MR, CP, SP);
 countCompiledInstructions(tmp.getToInstructions(), counts, MR, CP, SP);
 countCompiledInstructions(tmp.getIncrementInstructions(), counts, MR, CP, SP);
 for( ProgramBlock pb2 : tmp.getChildBlocks() )
 countCompiledInstructions(pb2, counts, MR, CP, SP);
 //additional parfor jobs counted during runtime
		}		
 else if (  pb instanceof FunctionProgramBlock ) //includes ExternalFunctionProgramBlock and ExternalFunctionProgramBlockCP
		{
 FunctionProgramBlock fpb = (FunctionProgramBlock)pb;
 for( ProgramBlock pb2 : fpb.getChildBlocks() )
 countCompiledInstructions(pb2, counts, MR, CP, SP);
		}
 else 
		{
 countCompiledInstructions(pb.getInstructions(), counts, MR, CP, SP);
		}
	}