public final class FunctionExpressionNode extends RSourceSectionNode implements RSyntaxNode, RSyntaxFunction {


 public static FunctionExpressionNode create(SourceSection src, RootCallTarget callTarget) {
 return new FunctionExpressionNode(src, callTarget);
    }


 @Child private SetVisibilityNode visibility = SetVisibilityNode.create();


 @CompilationFinal private RootCallTarget callTarget;
 private final PromiseDeoptimizeFrameNode deoptFrameNode;


 @CompilationFinal private boolean initialized = false;


 private FunctionExpressionNode(SourceSection src, RootCallTarget callTarget) {
 super(src);
 this.callTarget = callTarget;
 this.deoptFrameNode = EagerEvalHelper.optExprs() || EagerEvalHelper.optVars() || EagerEvalHelper.optDefault() ? new PromiseDeoptimizeFrameNode() : null;
    }


 @Override
 public RFunction execute(VirtualFrame frame) {
 visibility.execute(frame, true);
 MaterializedFrame matFrame = frame.materialize();
 if (deoptFrameNode != null) {
 // Deoptimize every promise which is now in this frame, as it might leave it's stack
 deoptFrameNode.deoptimizeFrame(RArguments.getArguments(matFrame));
        }
 if (!initialized) {
 CompilerDirectives.transferToInterpreterAndInvalidate();
 if (!FrameSlotChangeMonitor.isEnclosingFrameDescriptor(callTarget.getRootNode().getFrameDescriptor(), frame)) {
 if (!FrameSlotChangeMonitor.isEnclosingFrameDescriptor(callTarget.getRootNode().getFrameDescriptor(), null)) {
 RRootNode root = (RRootNode) callTarget.getRootNode();
 callTarget = root.duplicateWithNewFrameDescriptor();
                }
 FrameSlotChangeMonitor.initializeEnclosingFrame(callTarget.getRootNode().getFrameDescriptor(), frame);
            }
 initialized = true;
        }
 return RDataFactory.createFunction(RFunction.NO_NAME, RFunction.NO_NAME, callTarget, null, matFrame);
    }


 public RootCallTarget getCallTarget() {
 return callTarget;
    }


 @Override
 public RSyntaxElement[] getSyntaxArgumentDefaults() {
 return RASTUtils.asSyntaxNodes(((FunctionDefinitionNode) callTarget.getRootNode()).getFormalArguments().getArguments());
    }


 @Override
 public RSyntaxElement getSyntaxBody() {
 return ((FunctionDefinitionNode) callTarget.getRootNode()).getBody();
    }


 @Override
 public ArgumentsSignature getSyntaxSignature() {
 return ((FunctionDefinitionNode) callTarget.getRootNode()).getFormalArguments().getSignature();
    }


 @Override
 public String getSyntaxDebugName() {
 return ((RRootNode) callTarget.getRootNode()).getName();
    }
}