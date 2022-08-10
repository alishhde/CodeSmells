 @Override
 public ILSMIndex createInstance(INCServiceContext serviceCtx) throws HyracksDataException {
 IIOManager ioManager = serviceCtx.getIoManager();
 FileReference file = ioManager.resolve(path);
 List<IVirtualBufferCache> virtualBufferCaches = vbcProvider.getVirtualBufferCaches(serviceCtx, file);
 ioOpCallbackFactory.initialize(serviceCtx, this);
 return LSMRTreeUtils.createLSMTreeWithAntiMatterTuples(ioManager, virtualBufferCaches, file,
 storageManager.getBufferCache(serviceCtx), typeTraits, cmpFactories, btreeCmpFactories,
 valueProviderFactories, rtreePolicyType,
 mergePolicyFactory.createMergePolicy(mergePolicyProperties, serviceCtx),
 opTrackerProvider.getOperationTracker(serviceCtx, this), ioSchedulerProvider.getIoScheduler(serviceCtx),
 ioOpCallbackFactory, linearizeCmpFactory, rtreeFields, filterTypeTraits, filterCmpFactories,
 filterFields, durable, isPointMBR, metadataPageManagerFactory);
    }