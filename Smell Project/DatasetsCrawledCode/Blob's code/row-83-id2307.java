public final class WhileNode extends AbstractLoopNode implements RSyntaxNode, RSyntaxCall {


 @Child private LoopNode loop;
 @Child private SetVisibilityNode visibility = SetVisibilityNode.create();


 public WhileNode(SourceSection src, RSyntaxLookup operator, RSyntaxNode condition, RSyntaxNode body) {
 super(src, operator);
 this.loop = Truffle.getRuntime().createLoopNode(new WhileRepeatingNode(this, ConvertBooleanNode.create(condition), body.asRNode()));
    }


 @Override
 public Object execute(VirtualFrame frame) {
 loop.executeLoop(frame);
 visibility.execute(frame, false);
 return RNull.instance;
    }


 private static final class WhileRepeatingNode extends AbstractRepeatingNode {


 @Child private ConvertBooleanNode condition;


 private final ConditionProfile conditionProfile = ConditionProfile.createCountingProfile();
 private final BranchProfile normalBlock = BranchProfile.create();
 private final BranchProfile breakBlock = BranchProfile.create();
 private final BranchProfile nextBlock = BranchProfile.create();


 // only used for toString
 private final WhileNode whileNode;


 WhileRepeatingNode(WhileNode whileNode, ConvertBooleanNode condition, RNode body) {
 super(body);
 this.whileNode = whileNode;
 this.condition = condition;
 // pre-initialize the profile so that loop exits to not deoptimize
 conditionProfile.profile(false);
        }


 @Override
 public boolean executeRepeating(VirtualFrame frame) {
 try {
 if (conditionProfile.profile(condition.executeByte(frame) == RRuntime.LOGICAL_TRUE)) {
 body.voidExecute(frame);
 normalBlock.enter();
 return true;
                } else {
 return false;
                }
            } catch (BreakException e) {
 breakBlock.enter();
 return false;
            } catch (NextException e) {
 nextBlock.enter();
 return true;
            }
        }


 @Override
 public String toString() {
 return whileNode.toString();
        }
    }


 @Override
 public RSyntaxElement[] getSyntaxArguments() {
 WhileRepeatingNode repeatingNode = (WhileRepeatingNode) loop.getRepeatingNode();
 return new RSyntaxElement[]{repeatingNode.condition.asRSyntaxNode(), repeatingNode.body.asRSyntaxNode()};
    }


 @Override
 public ArgumentsSignature getSyntaxSignature() {
 return ArgumentsSignature.empty(2);
    }
}