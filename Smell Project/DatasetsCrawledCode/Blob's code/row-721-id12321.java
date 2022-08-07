 public class BitwiseORExpressionElements extends AbstractParserRuleElementFinder {
 private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.N4JS.BitwiseORExpression");
 private final Group cGroup = (Group)rule.eContents().get(1);
 private final RuleCall cBitwiseXORExpressionParserRuleCall_0 = (RuleCall)cGroup.eContents().get(0);
 private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
 private final Group cGroup_1_0 = (Group)cGroup_1.eContents().get(0);
 private final Group cGroup_1_0_0 = (Group)cGroup_1_0.eContents().get(0);
 private final Action cBinaryBitwiseExpressionLhsAction_1_0_0_0 = (Action)cGroup_1_0_0.eContents().get(0);
 private final Assignment cOpAssignment_1_0_0_1 = (Assignment)cGroup_1_0_0.eContents().get(1);
 private final RuleCall cOpBitwiseOROperatorParserRuleCall_1_0_0_1_0 = (RuleCall)cOpAssignment_1_0_0_1.eContents().get(0);
 private final Assignment cRhsAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
 private final RuleCall cRhsBitwiseXORExpressionParserRuleCall_1_1_0 = (RuleCall)cRhsAssignment_1_1.eContents().get(0);
 
 //BitwiseORExpression <In, Yield Expression:
 //	BitwiseXORExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator)
 //	rhs=BitwiseXORExpression<In,Yield>)*;
 @Override public ParserRule getRule() { return rule; }
 
 //BitwiseXORExpression<In,Yield> (=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator)
 //rhs=BitwiseXORExpression<In,Yield>)*
 public Group getGroup() { return cGroup; }
 
 //BitwiseXORExpression<In,Yield>
 public RuleCall getBitwiseXORExpressionParserRuleCall_0() { return cBitwiseXORExpressionParserRuleCall_0; }
 
 //(=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator) rhs=BitwiseXORExpression<In,Yield>)*
 public Group getGroup_1() { return cGroup_1; }
 
 //=> ({BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator)
 public Group getGroup_1_0() { return cGroup_1_0; }
 
 //{BinaryBitwiseExpression.lhs=current} op=BitwiseOROperator
 public Group getGroup_1_0_0() { return cGroup_1_0_0; }
 
 //{BinaryBitwiseExpression.lhs=current}
 public Action getBinaryBitwiseExpressionLhsAction_1_0_0_0() { return cBinaryBitwiseExpressionLhsAction_1_0_0_0; }
 
 //op=BitwiseOROperator
 public Assignment getOpAssignment_1_0_0_1() { return cOpAssignment_1_0_0_1; }
 
 //BitwiseOROperator
 public RuleCall getOpBitwiseOROperatorParserRuleCall_1_0_0_1_0() { return cOpBitwiseOROperatorParserRuleCall_1_0_0_1_0; }
 
 //rhs=BitwiseXORExpression<In,Yield>
 public Assignment getRhsAssignment_1_1() { return cRhsAssignment_1_1; }
 
 //BitwiseXORExpression<In,Yield>
 public RuleCall getRhsBitwiseXORExpressionParserRuleCall_1_1_0() { return cRhsBitwiseXORExpressionParserRuleCall_1_1_0; }
	}