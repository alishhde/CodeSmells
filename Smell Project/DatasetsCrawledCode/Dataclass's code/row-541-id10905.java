 public class TypeRefWithoutModifiersElements extends AbstractParserRuleElementFinder {
 private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.n4js.ts.TypeExpressions.TypeRefWithoutModifiers");
 private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
 private final Group cGroup_0 = (Group)cAlternatives.eContents().get(0);
 private final Alternatives cAlternatives_0_0 = (Alternatives)cGroup_0.eContents().get(0);
 private final RuleCall cParameterizedTypeRefParserRuleCall_0_0_0 = (RuleCall)cAlternatives_0_0.eContents().get(0);
 private final RuleCall cThisTypeRefParserRuleCall_0_0_1 = (RuleCall)cAlternatives_0_0.eContents().get(1);
 private final Assignment cDynamicAssignment_0_1 = (Assignment)cGroup_0.eContents().get(1);
 private final Keyword cDynamicPlusSignKeyword_0_1_0 = (Keyword)cDynamicAssignment_0_1.eContents().get(0);
 private final RuleCall cTypeTypeRefParserRuleCall_1 = (RuleCall)cAlternatives.eContents().get(1);
 private final RuleCall cFunctionTypeExpressionOLDParserRuleCall_2 = (RuleCall)cAlternatives.eContents().get(2);
 private final RuleCall cUnionTypeExpressionOLDParserRuleCall_3 = (RuleCall)cAlternatives.eContents().get(3);
 private final RuleCall cIntersectionTypeExpressionOLDParserRuleCall_4 = (RuleCall)cAlternatives.eContents().get(4);
 
 //TypeRefWithoutModifiers StaticBaseTypeRef:
 //	(ParameterizedTypeRef | ThisTypeRef) => dynamic?='+'? | TypeTypeRef
 //	| FunctionTypeExpressionOLD
 //	| UnionTypeExpressionOLD
 //	| IntersectionTypeExpressionOLD;
 @Override public ParserRule getRule() { return rule; }
 
 //(ParameterizedTypeRef | ThisTypeRef) => dynamic?='+'? | TypeTypeRef | FunctionTypeExpressionOLD | UnionTypeExpressionOLD
 //| IntersectionTypeExpressionOLD
 public Alternatives getAlternatives() { return cAlternatives; }
 
 //(ParameterizedTypeRef | ThisTypeRef) => dynamic?='+'?
 public Group getGroup_0() { return cGroup_0; }
 
 //ParameterizedTypeRef | ThisTypeRef
 public Alternatives getAlternatives_0_0() { return cAlternatives_0_0; }
 
 //ParameterizedTypeRef
 public RuleCall getParameterizedTypeRefParserRuleCall_0_0_0() { return cParameterizedTypeRefParserRuleCall_0_0_0; }
 
 //ThisTypeRef
 public RuleCall getThisTypeRefParserRuleCall_0_0_1() { return cThisTypeRefParserRuleCall_0_0_1; }
 
 //=> dynamic?='+'?
 public Assignment getDynamicAssignment_0_1() { return cDynamicAssignment_0_1; }
 
 //'+'
 public Keyword getDynamicPlusSignKeyword_0_1_0() { return cDynamicPlusSignKeyword_0_1_0; }
 
 //TypeTypeRef
 public RuleCall getTypeTypeRefParserRuleCall_1() { return cTypeTypeRefParserRuleCall_1; }
 
 //FunctionTypeExpressionOLD
 public RuleCall getFunctionTypeExpressionOLDParserRuleCall_2() { return cFunctionTypeExpressionOLDParserRuleCall_2; }
 
 //UnionTypeExpressionOLD
 public RuleCall getUnionTypeExpressionOLDParserRuleCall_3() { return cUnionTypeExpressionOLDParserRuleCall_3; }
 
 //IntersectionTypeExpressionOLD
 public RuleCall getIntersectionTypeExpressionOLDParserRuleCall_4() { return cIntersectionTypeExpressionOLDParserRuleCall_4; }
	}