public class DefaultHotSpotLoweringProvider extends DefaultJavaLoweringProvider implements HotSpotLoweringProvider {


 protected final HotSpotGraalRuntimeProvider runtime;
 protected final HotSpotRegistersProvider registers;
 protected final HotSpotConstantReflectionProvider constantReflection;


 protected InstanceOfSnippets.Templates instanceofSnippets;
 protected NewObjectSnippets.Templates newObjectSnippets;
 protected MonitorSnippets.Templates monitorSnippets;
 protected WriteBarrierSnippets.Templates writeBarrierSnippets;
 protected LoadExceptionObjectSnippets.Templates exceptionObjectSnippets;
 protected UnsafeLoadSnippets.Templates unsafeLoadSnippets;
 protected AssertionSnippets.Templates assertionSnippets;
 protected ArrayCopySnippets.Templates arraycopySnippets;
 protected StringToBytesSnippets.Templates stringToBytesSnippets;
 protected HashCodeSnippets.Templates hashCodeSnippets;
 protected ResolveConstantSnippets.Templates resolveConstantSnippets;
 protected ProfileSnippets.Templates profileSnippets;


 protected ObjectCloneSnippets.Templates objectCloneSnippets;
 protected ForeignCallSnippets.Templates foreignCallSnippets;


 public DefaultHotSpotLoweringProvider(HotSpotGraalRuntimeProvider runtime, MetaAccessProvider metaAccess, ForeignCallsProvider foreignCalls, HotSpotRegistersProvider registers,
 HotSpotConstantReflectionProvider constantReflection, TargetDescription target) {
 super(metaAccess, foreignCalls, target, runtime.getVMConfig().useCompressedOops);
 this.runtime = runtime;
 this.registers = registers;
 this.constantReflection = constantReflection;
    }


 @Override
 public void initialize(OptionValues options, Iterable<DebugHandlersFactory> factories, HotSpotProviders providers, GraalHotSpotVMConfig config) {
 super.initialize(options, factories, runtime, providers, providers.getSnippetReflection());


 assert target == providers.getCodeCache().getTarget();
 instanceofSnippets = new InstanceOfSnippets.Templates(options, factories, runtime, providers, target);
 newObjectSnippets = new NewObjectSnippets.Templates(options, factories, runtime, providers, target, config);
 monitorSnippets = new MonitorSnippets.Templates(options, factories, runtime, providers, target, config.useFastLocking);
 writeBarrierSnippets = new WriteBarrierSnippets.Templates(options, factories, runtime, providers, target, config);
 exceptionObjectSnippets = new LoadExceptionObjectSnippets.Templates(options, factories, providers, target);
 unsafeLoadSnippets = new UnsafeLoadSnippets.Templates(options, factories, providers, target);
 assertionSnippets = new AssertionSnippets.Templates(options, factories, providers, target);
 arraycopySnippets = new ArrayCopySnippets.Templates(new HotSpotArraycopySnippets(), options, factories, runtime, providers, providers.getSnippetReflection(), target);
 stringToBytesSnippets = new StringToBytesSnippets.Templates(options, factories, providers, target);
 hashCodeSnippets = new HashCodeSnippets.Templates(options, factories, providers, target);
 resolveConstantSnippets = new ResolveConstantSnippets.Templates(options, factories, providers, target);
 if (!JavaVersionUtil.Java8OrEarlier) {
 profileSnippets = new ProfileSnippets.Templates(options, factories, providers, target);
        }
 objectCloneSnippets = new ObjectCloneSnippets.Templates(options, factories, providers, target);
 foreignCallSnippets = new ForeignCallSnippets.Templates(options, factories, providers, target);
    }


 public MonitorSnippets.Templates getMonitorSnippets() {
 return monitorSnippets;
    }


 @Override
 @SuppressWarnings("try")
 public void lower(Node n, LoweringTool tool) {
 StructuredGraph graph = (StructuredGraph) n.graph();
 try (DebugCloseable context = n.withNodeSourcePosition()) {
 if (n instanceof Invoke) {
 lowerInvoke((Invoke) n, tool, graph);
            } else if (n instanceof LoadMethodNode) {
 lowerLoadMethodNode((LoadMethodNode) n);
            } else if (n instanceof GetClassNode) {
 lowerGetClassNode((GetClassNode) n, tool, graph);
            } else if (n instanceof StoreHubNode) {
 lowerStoreHubNode((StoreHubNode) n, graph);
            } else if (n instanceof OSRStartNode) {
 lowerOSRStartNode((OSRStartNode) n);
            } else if (n instanceof BytecodeExceptionNode) {
 lowerBytecodeExceptionNode((BytecodeExceptionNode) n);
            } else if (n instanceof InstanceOfNode) {
 InstanceOfNode instanceOfNode = (InstanceOfNode) n;
 if (graph.getGuardsStage().areDeoptsFixed()) {
 instanceofSnippets.lower(instanceOfNode, tool);
                } else {
 if (instanceOfNode.allowsNull()) {
 ValueNode object = instanceOfNode.getValue();
 LogicNode newTypeCheck = graph.addOrUniqueWithInputs(InstanceOfNode.create(instanceOfNode.type(), object, instanceOfNode.profile(), instanceOfNode.getAnchor()));
 LogicNode newNode = LogicNode.or(graph.unique(IsNullNode.create(object)), newTypeCheck, GraalDirectives.UNLIKELY_PROBABILITY);
 instanceOfNode.replaceAndDelete(newNode);
                    }
                }
            } else if (n instanceof InstanceOfDynamicNode) {
 InstanceOfDynamicNode instanceOfDynamicNode = (InstanceOfDynamicNode) n;
 if (graph.getGuardsStage().areDeoptsFixed()) {
 instanceofSnippets.lower(instanceOfDynamicNode, tool);
                } else {
 ValueNode mirror = instanceOfDynamicNode.getMirrorOrHub();
 if (mirror.stamp(NodeView.DEFAULT).getStackKind() == JavaKind.Object) {
 ClassGetHubNode classGetHub = graph.unique(new ClassGetHubNode(mirror));
 instanceOfDynamicNode.setMirror(classGetHub);
                    }


 if (instanceOfDynamicNode.allowsNull()) {
 ValueNode object = instanceOfDynamicNode.getObject();
 LogicNode newTypeCheck = graph.addOrUniqueWithInputs(
 InstanceOfDynamicNode.create(graph.getAssumptions(), tool.getConstantReflection(), instanceOfDynamicNode.getMirrorOrHub(), object, false));
 LogicNode newNode = LogicNode.or(graph.unique(IsNullNode.create(object)), newTypeCheck, GraalDirectives.UNLIKELY_PROBABILITY);
 instanceOfDynamicNode.replaceAndDelete(newNode);
                    }
                }
            } else if (n instanceof ClassIsAssignableFromNode) {
 if (graph.getGuardsStage().areDeoptsFixed()) {
 instanceofSnippets.lower((ClassIsAssignableFromNode) n, tool);
                }
            } else if (n instanceof NewInstanceNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 newObjectSnippets.lower((NewInstanceNode) n, registers, tool);
                }
            } else if (n instanceof DynamicNewInstanceNode) {
 DynamicNewInstanceNode newInstanceNode = (DynamicNewInstanceNode) n;
 if (newInstanceNode.getClassClass() == null) {
 JavaConstant classClassMirror = constantReflection.asJavaClass(metaAccess.lookupJavaType(Class.class));
 ConstantNode classClass = ConstantNode.forConstant(classClassMirror, tool.getMetaAccess(), graph);
 newInstanceNode.setClassClass(classClass);
                }
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 newObjectSnippets.lower(newInstanceNode, registers, tool);
                }
            } else if (n instanceof NewArrayNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 newObjectSnippets.lower((NewArrayNode) n, registers, tool);
                }
            } else if (n instanceof DynamicNewArrayNode) {
 DynamicNewArrayNode dynamicNewArrayNode = (DynamicNewArrayNode) n;
 if (dynamicNewArrayNode.getVoidClass() == null) {
 JavaConstant voidClassMirror = constantReflection.asJavaClass(metaAccess.lookupJavaType(void.class));
 ConstantNode voidClass = ConstantNode.forConstant(voidClassMirror, tool.getMetaAccess(), graph);
 dynamicNewArrayNode.setVoidClass(voidClass);
                }
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 newObjectSnippets.lower(dynamicNewArrayNode, registers, tool);
                }
            } else if (n instanceof VerifyHeapNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 newObjectSnippets.lower((VerifyHeapNode) n, registers, tool);
                }
            } else if (n instanceof RawMonitorEnterNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 monitorSnippets.lower((RawMonitorEnterNode) n, registers, tool);
                }
            } else if (n instanceof MonitorExitNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 monitorSnippets.lower((MonitorExitNode) n, registers, tool);
                }
            } else if (n instanceof ArrayCopyNode) {
 arraycopySnippets.lower((ArrayCopyNode) n, tool);
            } else if (n instanceof ArrayCopyWithSlowPathNode) {
 arraycopySnippets.lower((ArrayCopyWithSlowPathNode) n, tool);
            } else if (n instanceof G1PreWriteBarrier) {
 writeBarrierSnippets.lower((G1PreWriteBarrier) n, registers, tool);
            } else if (n instanceof G1PostWriteBarrier) {
 writeBarrierSnippets.lower((G1PostWriteBarrier) n, registers, tool);
            } else if (n instanceof G1ReferentFieldReadBarrier) {
 writeBarrierSnippets.lower((G1ReferentFieldReadBarrier) n, registers, tool);
            } else if (n instanceof SerialWriteBarrier) {
 writeBarrierSnippets.lower((SerialWriteBarrier) n, tool);
            } else if (n instanceof SerialArrayRangeWriteBarrier) {
 writeBarrierSnippets.lower((SerialArrayRangeWriteBarrier) n, tool);
            } else if (n instanceof G1ArrayRangePreWriteBarrier) {
 writeBarrierSnippets.lower((G1ArrayRangePreWriteBarrier) n, registers, tool);
            } else if (n instanceof G1ArrayRangePostWriteBarrier) {
 writeBarrierSnippets.lower((G1ArrayRangePostWriteBarrier) n, registers, tool);
            } else if (n instanceof NewMultiArrayNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 newObjectSnippets.lower((NewMultiArrayNode) n, tool);
                }
            } else if (n instanceof LoadExceptionObjectNode) {
 exceptionObjectSnippets.lower((LoadExceptionObjectNode) n, registers, tool);
            } else if (n instanceof AssertionNode) {
 assertionSnippets.lower((AssertionNode) n, tool);
            } else if (n instanceof StringToBytesNode) {
 if (graph.getGuardsStage().areDeoptsFixed()) {
 stringToBytesSnippets.lower((StringToBytesNode) n, tool);
                }
            } else if (n instanceof IntegerDivRemNode) {
 // Nothing to do for division nodes. The HotSpot signal handler catches divisions by
 // zero and the MIN_VALUE / -1 cases.
            } else if (n instanceof AbstractDeoptimizeNode || n instanceof UnwindNode || n instanceof RemNode || n instanceof SafepointNode) {
 /* No lowering, we generate LIR directly for these nodes. */
            } else if (n instanceof ClassGetHubNode) {
 lowerClassGetHubNode((ClassGetHubNode) n, tool);
            } else if (n instanceof HubGetClassNode) {
 lowerHubGetClassNode((HubGetClassNode) n, tool);
            } else if (n instanceof KlassLayoutHelperNode) {
 lowerKlassLayoutHelperNode((KlassLayoutHelperNode) n, tool);
            } else if (n instanceof ComputeObjectAddressNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 lowerComputeObjectAddressNode((ComputeObjectAddressNode) n);
                }
            } else if (n instanceof IdentityHashCodeNode) {
 hashCodeSnippets.lower((IdentityHashCodeNode) n, tool);
            } else if (n instanceof ResolveDynamicConstantNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 resolveConstantSnippets.lower((ResolveDynamicConstantNode) n, tool);
                }
            } else if (n instanceof ResolveConstantNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 resolveConstantSnippets.lower((ResolveConstantNode) n, tool);
                }
            } else if (n instanceof ResolveMethodAndLoadCountersNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 resolveConstantSnippets.lower((ResolveMethodAndLoadCountersNode) n, tool);
                }
            } else if (n instanceof InitializeKlassNode) {
 if (graph.getGuardsStage().areFrameStatesAtDeopts()) {
 resolveConstantSnippets.lower((InitializeKlassNode) n, tool);
                }
            } else if (n instanceof ProfileNode) {
 profileSnippets.lower((ProfileNode) n, tool);
            } else {
 super.lower(n, tool);
            }
        }
    }


 private static void lowerComputeObjectAddressNode(ComputeObjectAddressNode n) {
 /*
         * Lower the node into a ComputeObjectAddress node and an Add but ensure that it's below any
         * potential safepoints and above it's uses.
         */
 for (Node use : n.usages().snapshot()) {
 if (use instanceof FixedNode) {
 FixedNode fixed = (FixedNode) use;
 StructuredGraph graph = n.graph();
 GetObjectAddressNode address = graph.add(new GetObjectAddressNode(n.getObject()));
 graph.addBeforeFixed(fixed, address);
 AddNode add = graph.addOrUnique(new AddNode(address, n.getOffset()));
 use.replaceFirstInput(n, add);
            } else {
 throw GraalError.shouldNotReachHere("Unexpected floating use of ComputeObjectAddressNode " + n);
            }
        }
 GraphUtil.unlinkFixedNode(n);
 n.safeDelete();
    }


 private void lowerKlassLayoutHelperNode(KlassLayoutHelperNode n, LoweringTool tool) {
 if (tool.getLoweringStage() == LoweringTool.StandardLoweringStage.HIGH_TIER) {
 return;
        }
 StructuredGraph graph = n.graph();
 assert !n.getHub().isConstant();
 AddressNode address = createOffsetAddress(graph, n.getHub(), runtime.getVMConfig().klassLayoutHelperOffset);
 n.replaceAtUsagesAndDelete(graph.unique(new FloatingReadNode(address, KLASS_LAYOUT_HELPER_LOCATION, null, n.stamp(NodeView.DEFAULT), null, BarrierType.NONE)));
    }


 private void lowerHubGetClassNode(HubGetClassNode n, LoweringTool tool) {
 if (tool.getLoweringStage() == LoweringTool.StandardLoweringStage.HIGH_TIER) {
 return;
        }


 ValueNode hub = n.getHub();
 GraalHotSpotVMConfig vmConfig = runtime.getVMConfig();
 StructuredGraph graph = n.graph();
 assert !hub.isConstant() || GraalOptions.ImmutableCode.getValue(graph.getOptions());
 AddressNode mirrorAddress = createOffsetAddress(graph, hub, vmConfig.classMirrorOffset);
 FloatingReadNode read = graph.unique(
 new FloatingReadNode(mirrorAddress, CLASS_MIRROR_LOCATION, null, vmConfig.classMirrorIsHandle ? StampFactory.forKind(target.wordJavaKind) : n.stamp(NodeView.DEFAULT),
 null, BarrierType.NONE));
 if (vmConfig.classMirrorIsHandle) {
 AddressNode address = createOffsetAddress(graph, read, 0);
 read = graph.unique(new FloatingReadNode(address, CLASS_MIRROR_HANDLE_LOCATION, null, n.stamp(NodeView.DEFAULT), null, BarrierType.NONE));
        }
 n.replaceAtUsagesAndDelete(read);
    }


 private void lowerClassGetHubNode(ClassGetHubNode n, LoweringTool tool) {
 if (tool.getLoweringStage() == LoweringTool.StandardLoweringStage.HIGH_TIER) {
 return;
        }


 StructuredGraph graph = n.graph();
 assert !n.getValue().isConstant();
 AddressNode address = createOffsetAddress(graph, n.getValue(), runtime.getVMConfig().klassOffset);
 FloatingReadNode read = graph.unique(new FloatingReadNode(address, CLASS_KLASS_LOCATION, null, n.stamp(NodeView.DEFAULT), null, BarrierType.NONE));
 n.replaceAtUsagesAndDelete(read);
    }


 private void lowerInvoke(Invoke invoke, LoweringTool tool, StructuredGraph graph) {
 if (invoke.callTarget() instanceof MethodCallTargetNode) {
 MethodCallTargetNode callTarget = (MethodCallTargetNode) invoke.callTarget();
 NodeInputList<ValueNode> parameters = callTarget.arguments();
 ValueNode receiver = parameters.size() <= 0 ? null : parameters.get(0);
 if (!callTarget.isStatic() && receiver.stamp(NodeView.DEFAULT) instanceof ObjectStamp && !StampTool.isPointerNonNull(receiver)) {
 ValueNode nonNullReceiver = createNullCheckedValue(receiver, invoke.asNode(), tool);
 parameters.set(0, nonNullReceiver);
 receiver = nonNullReceiver;
            }
 JavaType[] signature = callTarget.targetMethod().getSignature().toParameterTypes(callTarget.isStatic() ? null : callTarget.targetMethod().getDeclaringClass());


 LoweredCallTargetNode loweredCallTarget = null;
 OptionValues options = graph.getOptions();
 if (InlineVTableStubs.getValue(options) && callTarget.invokeKind().isIndirect() && (AlwaysInlineVTableStubs.getValue(options) || invoke.isPolymorphic())) {
 HotSpotResolvedJavaMethod hsMethod = (HotSpotResolvedJavaMethod) callTarget.targetMethod();
 ResolvedJavaType receiverType = invoke.getReceiverType();
 if (hsMethod.isInVirtualMethodTable(receiverType)) {
 JavaKind wordKind = runtime.getTarget().wordJavaKind;
 ValueNode hub = createReadHub(graph, receiver, tool);


 ReadNode metaspaceMethod = createReadVirtualMethod(graph, hub, hsMethod, receiverType);
 // We use LocationNode.ANY_LOCATION for the reads that access the
 // compiled code entry as HotSpot does not guarantee they are final
 // values.
 int methodCompiledEntryOffset = runtime.getVMConfig().methodCompiledEntryOffset;
 AddressNode address = createOffsetAddress(graph, metaspaceMethod, methodCompiledEntryOffset);
 ReadNode compiledEntry = graph.add(new ReadNode(address, any(), StampFactory.forKind(wordKind), BarrierType.NONE));


 loweredCallTarget = graph.add(new HotSpotIndirectCallTargetNode(metaspaceMethod, compiledEntry, parameters.toArray(new ValueNode[parameters.size()]), callTarget.returnStamp(),
 signature, callTarget.targetMethod(),
 HotSpotCallingConventionType.JavaCall, callTarget.invokeKind()));


 graph.addBeforeFixed(invoke.asNode(), metaspaceMethod);
 graph.addAfterFixed(metaspaceMethod, compiledEntry);
                }
            }


 if (loweredCallTarget == null) {
 loweredCallTarget = graph.add(new HotSpotDirectCallTargetNode(parameters.toArray(new ValueNode[parameters.size()]), callTarget.returnStamp(),
 signature, callTarget.targetMethod(),
 HotSpotCallingConventionType.JavaCall,
 callTarget.invokeKind()));
            }
 callTarget.replaceAndDelete(loweredCallTarget);
        }
    }


 private CompressEncoding getOopEncoding() {
 return runtime.getVMConfig().getOopEncoding();
    }


 @Override
 protected Stamp loadCompressedStamp(ObjectStamp stamp) {
 return HotSpotNarrowOopStamp.compressed(stamp, getOopEncoding());
    }


 @Override
 protected ValueNode newCompressionNode(CompressionOp op, ValueNode value) {
 return new HotSpotCompressionNode(op, value, getOopEncoding());
    }


 @Override
 public ValueNode staticFieldBase(StructuredGraph graph, ResolvedJavaField f) {
 HotSpotResolvedJavaField field = (HotSpotResolvedJavaField) f;
 JavaConstant base = constantReflection.asJavaClass(field.getDeclaringClass());
 return ConstantNode.forConstant(base, metaAccess, graph);
    }


 @Override
 protected ValueNode createReadArrayComponentHub(StructuredGraph graph, ValueNode arrayHub, FixedNode anchor) {
 /*
         * Anchor the read of the element klass to the cfg, because it is only valid when arrayClass
         * is an object class, which might not be the case in other parts of the compiled method.
         */
 AddressNode address = createOffsetAddress(graph, arrayHub, runtime.getVMConfig().arrayClassElementOffset);
 return graph.unique(new FloatingReadNode(address, OBJ_ARRAY_KLASS_ELEMENT_KLASS_LOCATION, null, KlassPointerStamp.klassNonNull(), AbstractBeginNode.prevBegin(anchor)));
    }


 @Override
 protected void lowerUnsafeLoadNode(RawLoadNode load, LoweringTool tool) {
 StructuredGraph graph = load.graph();
 if (!(load instanceof GuardedUnsafeLoadNode) && !graph.getGuardsStage().allowsFloatingGuards() && addReadBarrier(load)) {
 unsafeLoadSnippets.lower(load, tool);
        } else {
 super.lowerUnsafeLoadNode(load, tool);
        }
    }


 private void lowerLoadMethodNode(LoadMethodNode loadMethodNode) {
 StructuredGraph graph = loadMethodNode.graph();
 HotSpotResolvedJavaMethod method = (HotSpotResolvedJavaMethod) loadMethodNode.getMethod();
 ReadNode metaspaceMethod = createReadVirtualMethod(graph, loadMethodNode.getHub(), method, loadMethodNode.getReceiverType());
 graph.replaceFixed(loadMethodNode, metaspaceMethod);
    }


 private static void lowerGetClassNode(GetClassNode getClass, LoweringTool tool, StructuredGraph graph) {
 StampProvider stampProvider = tool.getStampProvider();
 LoadHubNode hub = graph.unique(new LoadHubNode(stampProvider, getClass.getObject()));
 HubGetClassNode hubGetClass = graph.unique(new HubGetClassNode(tool.getMetaAccess(), hub));
 getClass.replaceAtUsagesAndDelete(hubGetClass);
 hub.lower(tool);
 hubGetClass.lower(tool);
    }


 private void lowerStoreHubNode(StoreHubNode storeHub, StructuredGraph graph) {
 WriteNode hub = createWriteHub(graph, storeHub.getObject(), storeHub.getValue());
 graph.replaceFixed(storeHub, hub);
    }


 @Override
 public BarrierType fieldInitializationBarrier(JavaKind entryKind) {
 return (entryKind == JavaKind.Object && !runtime.getVMConfig().useDeferredInitBarriers) ? BarrierType.IMPRECISE : BarrierType.NONE;
    }


 @Override
 public BarrierType arrayInitializationBarrier(JavaKind entryKind) {
 return (entryKind == JavaKind.Object && !runtime.getVMConfig().useDeferredInitBarriers) ? BarrierType.PRECISE : BarrierType.NONE;
    }


 private void lowerOSRStartNode(OSRStartNode osrStart) {
 StructuredGraph graph = osrStart.graph();
 if (graph.getGuardsStage() == StructuredGraph.GuardsStage.FIXED_DEOPTS) {
 StartNode newStart = graph.add(new StartNode());
 ParameterNode buffer = graph.addWithoutUnique(new ParameterNode(0, StampPair.createSingle(StampFactory.forKind(runtime.getTarget().wordJavaKind))));
 ForeignCallNode migrationEnd = graph.add(new ForeignCallNode(foreignCalls, OSR_MIGRATION_END, buffer));
 migrationEnd.setStateAfter(osrStart.stateAfter());
 newStart.setNext(migrationEnd);
 FixedNode next = osrStart.next();
 osrStart.setNext(null);
 migrationEnd.setNext(next);
 graph.setStart(newStart);


 final int wordSize = target.wordSize;


 // @formatter:off
 // taken from c2 locals_addr = osr_buf + (max_locals-1)*wordSize)
 // @formatter:on
 int localsOffset = (graph.method().getMaxLocals() - 1) * wordSize;
 for (OSRLocalNode osrLocal : graph.getNodes(OSRLocalNode.TYPE)) {
 int size = osrLocal.getStackKind().getSlotCount();
 int offset = localsOffset - (osrLocal.index() + size - 1) * wordSize;
 AddressNode address = createOffsetAddress(graph, buffer, offset);
 ReadNode load = graph.add(new ReadNode(address, any(), osrLocal.stamp(NodeView.DEFAULT), BarrierType.NONE));
 osrLocal.replaceAndDelete(load);
 graph.addBeforeFixed(migrationEnd, load);
            }


 // @formatter:off
 // taken from c2 monitors_addr = osr_buf + (max_locals+mcnt*2-1)*wordSize);
 // @formatter:on
 final int lockCount = osrStart.stateAfter().locksSize();
 final int locksOffset = (graph.method().getMaxLocals() + lockCount * 2 - 1) * wordSize;


 // first initialize the lock slots for all enters with the displaced marks read from the
 // buffer
 for (OSRMonitorEnterNode osrMonitorEnter : graph.getNodes(OSRMonitorEnterNode.TYPE)) {
 MonitorIdNode monitorID = osrMonitorEnter.getMonitorId();
 OSRLockNode lock = (OSRLockNode) osrMonitorEnter.object();
 final int index = lock.index();


 final int offsetDisplacedHeader = locksOffset - ((index * 2) + 1) * wordSize;
 final int offsetLockObject = locksOffset - index * 2 * wordSize;


 // load the displaced mark from the osr buffer
 AddressNode addressDisplacedHeader = createOffsetAddress(graph, buffer, offsetDisplacedHeader);
 ReadNode loadDisplacedHeader = graph.add(new ReadNode(addressDisplacedHeader, any(), lock.stamp(NodeView.DEFAULT), BarrierType.NONE));
 graph.addBeforeFixed(migrationEnd, loadDisplacedHeader);


 // we need to initialize the stack slot for the lock
 BeginLockScopeNode beginLockScope = graph.add(new BeginLockScopeNode(lock.getStackKind(), monitorID.getLockDepth()));
 graph.addBeforeFixed(migrationEnd, beginLockScope);


 // write the displaced mark to the correct stack slot
 AddressNode addressDisplacedMark = createOffsetAddress(graph, beginLockScope, runtime.getVMConfig().basicLockDisplacedHeaderOffset);
 WriteNode writeStackSlot = graph.add(new WriteNode(addressDisplacedMark, DISPLACED_MARK_WORD_LOCATION, loadDisplacedHeader, BarrierType.NONE));
 graph.addBeforeFixed(migrationEnd, writeStackSlot);


 // load the lock object from the osr buffer
 AddressNode addressLockObject = createOffsetAddress(graph, buffer, offsetLockObject);
 ReadNode loadObject = graph.add(new ReadNode(addressLockObject, any(), lock.stamp(NodeView.DEFAULT), BarrierType.NONE));
 lock.replaceAndDelete(loadObject);
 graph.addBeforeFixed(migrationEnd, loadObject);
            }


 osrStart.replaceAtUsagesAndDelete(newStart);
        }
    }


 static final class Exceptions {
 protected static final EnumMap<BytecodeExceptionKind, RuntimeException> cachedExceptions;


 static {
 cachedExceptions = new EnumMap<>(BytecodeExceptionKind.class);
 cachedExceptions.put(BytecodeExceptionKind.NULL_POINTER, clearStackTrace(new NullPointerException()));
 cachedExceptions.put(BytecodeExceptionKind.OUT_OF_BOUNDS, clearStackTrace(new ArrayIndexOutOfBoundsException()));
 cachedExceptions.put(BytecodeExceptionKind.CLASS_CAST, clearStackTrace(new ClassCastException()));
 cachedExceptions.put(BytecodeExceptionKind.ARRAY_STORE, clearStackTrace(new ArrayStoreException()));
 cachedExceptions.put(BytecodeExceptionKind.DIVISION_BY_ZERO, clearStackTrace(new ArithmeticException()));
        }


 private static RuntimeException clearStackTrace(RuntimeException ex) {
 ex.setStackTrace(new StackTraceElement[0]);
 return ex;
        }
    }


 public static final class RuntimeCalls {
 public static final EnumMap<BytecodeExceptionKind, ForeignCallDescriptor> runtimeCalls;


 static {
 runtimeCalls = new EnumMap<>(BytecodeExceptionKind.class);
 runtimeCalls.put(BytecodeExceptionKind.ARRAY_STORE, new ForeignCallDescriptor("createArrayStoreException", ArrayStoreException.class, Object.class));
 runtimeCalls.put(BytecodeExceptionKind.CLASS_CAST, new ForeignCallDescriptor("createClassCastException", ClassCastException.class, Object.class, KlassPointer.class));
 runtimeCalls.put(BytecodeExceptionKind.NULL_POINTER, new ForeignCallDescriptor("createNullPointerException", NullPointerException.class));
 runtimeCalls.put(BytecodeExceptionKind.OUT_OF_BOUNDS, new ForeignCallDescriptor("createOutOfBoundsException", ArrayIndexOutOfBoundsException.class, int.class, int.class));
 runtimeCalls.put(BytecodeExceptionKind.DIVISION_BY_ZERO, new ForeignCallDescriptor("createDivisionByZeroException", ArithmeticException.class));
 runtimeCalls.put(BytecodeExceptionKind.INTEGER_EXACT_OVERFLOW, new ForeignCallDescriptor("createIntegerExactOverflowException", ArithmeticException.class));
 runtimeCalls.put(BytecodeExceptionKind.LONG_EXACT_OVERFLOW, new ForeignCallDescriptor("createLongExactOverflowException", ArithmeticException.class));
        }
    }


 private void throwCachedException(BytecodeExceptionNode node) {
 if (IS_IN_NATIVE_IMAGE) {
 throw new InternalError("Can't throw exception from SVM object");
        }
 Throwable exception = Exceptions.cachedExceptions.get(node.getExceptionKind());
 assert exception != null;


 StructuredGraph graph = node.graph();
 FloatingNode exceptionNode = ConstantNode.forConstant(constantReflection.forObject(exception), metaAccess, graph);
 graph.replaceFixedWithFloating(node, exceptionNode);
    }


 private void lowerBytecodeExceptionNode(BytecodeExceptionNode node) {
 if (OmitHotExceptionStacktrace.getValue(node.getOptions())) {
 throwCachedException(node);
 return;
        }


 ForeignCallDescriptor descriptor = RuntimeCalls.runtimeCalls.get(node.getExceptionKind());
 assert descriptor != null;


 StructuredGraph graph = node.graph();
 ForeignCallNode foreignCallNode = graph.add(new ForeignCallNode(foreignCalls, descriptor, node.stamp(NodeView.DEFAULT), node.getArguments()));
 graph.replaceFixedWithFixed(node, foreignCallNode);
    }


 private boolean addReadBarrier(RawLoadNode load) {
 if (runtime.getVMConfig().useG1GC && load.graph().getGuardsStage() == StructuredGraph.GuardsStage.FIXED_DEOPTS && load.object().getStackKind() == JavaKind.Object &&
 load.accessKind() == JavaKind.Object && !StampTool.isPointerAlwaysNull(load.object())) {
 ResolvedJavaType type = StampTool.typeOrNull(load.object());
 if (type != null && !type.isArray()) {
 return true;
            }
        }
 return false;
    }


 private ReadNode createReadVirtualMethod(StructuredGraph graph, ValueNode hub, HotSpotResolvedJavaMethod method, ResolvedJavaType receiverType) {
 return createReadVirtualMethod(graph, hub, method.vtableEntryOffset(receiverType));
    }


 private ReadNode createReadVirtualMethod(StructuredGraph graph, ValueNode hub, int vtableEntryOffset) {
 assert vtableEntryOffset > 0;
 // We use LocationNode.ANY_LOCATION for the reads that access the vtable
 // entry as HotSpot does not guarantee that this is a final value.
 Stamp methodStamp = MethodPointerStamp.methodNonNull();
 AddressNode address = createOffsetAddress(graph, hub, vtableEntryOffset);
 ReadNode metaspaceMethod = graph.add(new ReadNode(address, any(), methodStamp, BarrierType.NONE));
 return metaspaceMethod;
    }


 @Override
 protected ValueNode createReadHub(StructuredGraph graph, ValueNode object, LoweringTool tool) {
 if (tool.getLoweringStage() != LoweringTool.StandardLoweringStage.LOW_TIER) {
 return graph.unique(new LoadHubNode(tool.getStampProvider(), object));
        }
 assert !object.isConstant() || object.isNullConstant();


 KlassPointerStamp hubStamp = KlassPointerStamp.klassNonNull();
 if (runtime.getVMConfig().useCompressedClassPointers) {
 hubStamp = hubStamp.compressed(runtime.getVMConfig().getKlassEncoding());
        }


 AddressNode address = createOffsetAddress(graph, object, runtime.getVMConfig().hubOffset);
 LocationIdentity hubLocation = runtime.getVMConfig().useCompressedClassPointers ? COMPRESSED_HUB_LOCATION : HUB_LOCATION;
 FloatingReadNode memoryRead = graph.unique(new FloatingReadNode(address, hubLocation, null, hubStamp, null, BarrierType.NONE));
 if (runtime.getVMConfig().useCompressedClassPointers) {
 return HotSpotCompressionNode.uncompress(memoryRead, runtime.getVMConfig().getKlassEncoding());
        } else {
 return memoryRead;
        }
    }


 private WriteNode createWriteHub(StructuredGraph graph, ValueNode object, ValueNode value) {
 assert !object.isConstant() || object.asConstant().isDefaultForKind();


 ValueNode writeValue = value;
 if (runtime.getVMConfig().useCompressedClassPointers) {
 writeValue = HotSpotCompressionNode.compress(value, runtime.getVMConfig().getKlassEncoding());
        }


 AddressNode address = createOffsetAddress(graph, object, runtime.getVMConfig().hubOffset);
 return graph.add(new WriteNode(address, HUB_WRITE_LOCATION, writeValue, BarrierType.NONE));
    }


 @Override
 protected BarrierType fieldLoadBarrierType(ResolvedJavaField f) {
 HotSpotResolvedJavaField loadField = (HotSpotResolvedJavaField) f;
 BarrierType barrierType = BarrierType.NONE;
 if (runtime.getVMConfig().useG1GC && loadField.getJavaKind() == JavaKind.Object && metaAccess.lookupJavaType(Reference.class).equals(loadField.getDeclaringClass()) &&
 loadField.getName().equals("referent")) {
 barrierType = BarrierType.PRECISE;
        }
 return barrierType;
    }


 @Override
 public int fieldOffset(ResolvedJavaField f) {
 return f.getOffset();
    }


 @Override
 public int arrayLengthOffset() {
 return runtime.getVMConfig().arrayOopDescLengthOffset();
    }


 @Override
 protected final JavaKind getStorageKind(ResolvedJavaField field) {
 return field.getJavaKind();
    }


 @Override
 public ObjectCloneSnippets.Templates getObjectCloneSnippets() {
 return objectCloneSnippets;
    }


 @Override
 public ForeignCallSnippets.Templates getForeignCallSnippets() {
 return foreignCallSnippets;
    }
}