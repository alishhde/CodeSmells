 public NestedLoopJoin(IHyracksTaskContext ctx, FrameTupleAccessor accessorOuter, FrameTupleAccessor accessorInner,
 ITuplePairComparator comparatorsOuter2Inner, int memSize, IPredicateEvaluator predEval, boolean isLeftOuter,
 IMissingWriter[] missingWriters) throws HyracksDataException {
 this.accessorInner = accessorInner;
 this.accessorOuter = accessorOuter;
 this.appender = new FrameTupleAppender();
 this.tpComparator = comparatorsOuter2Inner;
 this.outBuffer = new VSizeFrame(ctx);
 this.innerBuffer = new VSizeFrame(ctx);
 this.appender.reset(outBuffer, true);
 if (memSize < 3) {
 throw new HyracksDataException("Not enough memory is available for Nested Loop Join");
        }
 this.outerBufferMngr =
 new VariableFrameMemoryManager(new VariableFramePool(ctx, ctx.getInitialFrameSize() * (memSize - 2)),
 FrameFreeSlotPolicyFactory.createFreeSlotPolicy(EnumFreeSlotPolicy.LAST_FIT, memSize - 2));


 this.predEvaluator = predEval;
 this.isReversed = false;


 this.isLeftOuter = isLeftOuter;
 if (isLeftOuter) {
 int innerFieldCount = this.accessorInner.getFieldCount();
 missingTupleBuilder = new ArrayTupleBuilder(innerFieldCount);
 DataOutput out = missingTupleBuilder.getDataOutput();
 for (int i = 0; i < innerFieldCount; i++) {
 missingWriters[i].writeMissing(out);
 missingTupleBuilder.addFieldEndOffset();
            }
        } else {
 missingTupleBuilder = null;
        }


 FileReference file =
 ctx.getJobletContext().createManagedWorkspaceFile(this.getClass().getSimpleName() + this.toString());
 runFileWriter = new RunFileWriter(file, ctx.getIoManager());
 runFileWriter.open();
    }