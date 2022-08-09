 public class GroupMultiplicitiesElements extends AbstractParserRuleElementFinder {
 private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.xtext.validation.ConcreteSyntaxValidationTestLanguage.GroupMultiplicities");
 private final Group cGroup = (Group)rule.eContents().get(1);
 private final Keyword cNumberSignDigitFourKeyword_0 = (Keyword)cGroup.eContents().get(0);
 private final Assignment cVal1Assignment_1 = (Assignment)cGroup.eContents().get(1);
 private final RuleCall cVal1IDTerminalRuleCall_1_0 = (RuleCall)cVal1Assignment_1.eContents().get(0);
 private final Keyword cKw1Keyword_2 = (Keyword)cGroup.eContents().get(2);
 private final Group cGroup_3 = (Group)cGroup.eContents().get(3);
 private final Assignment cVal2Assignment_3_0 = (Assignment)cGroup_3.eContents().get(0);
 private final RuleCall cVal2IDTerminalRuleCall_3_0_0 = (RuleCall)cVal2Assignment_3_0.eContents().get(0);
 private final Assignment cVal3Assignment_3_1 = (Assignment)cGroup_3.eContents().get(1);
 private final RuleCall cVal3IDTerminalRuleCall_3_1_0 = (RuleCall)cVal3Assignment_3_1.eContents().get(0);
 private final Keyword cKw2Keyword_4 = (Keyword)cGroup.eContents().get(4);
 private final Group cGroup_5 = (Group)cGroup.eContents().get(5);
 private final Assignment cVal4Assignment_5_0 = (Assignment)cGroup_5.eContents().get(0);
 private final RuleCall cVal4IDTerminalRuleCall_5_0_0 = (RuleCall)cVal4Assignment_5_0.eContents().get(0);
 private final Assignment cVal5Assignment_5_1 = (Assignment)cGroup_5.eContents().get(1);
 private final RuleCall cVal5IDTerminalRuleCall_5_1_0 = (RuleCall)cVal5Assignment_5_1.eContents().get(0);
 private final Keyword cKw3Keyword_6 = (Keyword)cGroup.eContents().get(6);
 private final Group cGroup_7 = (Group)cGroup.eContents().get(7);
 private final Assignment cVal6Assignment_7_0 = (Assignment)cGroup_7.eContents().get(0);
 private final RuleCall cVal6IDTerminalRuleCall_7_0_0 = (RuleCall)cVal6Assignment_7_0.eContents().get(0);
 private final Assignment cVal7Assignment_7_1 = (Assignment)cGroup_7.eContents().get(1);
 private final RuleCall cVal7IDTerminalRuleCall_7_1_0 = (RuleCall)cVal7Assignment_7_1.eContents().get(0);
 
 //GroupMultiplicities:
 //	"#4" val1=ID "kw1" (val2=ID val3=ID)? "kw2" (val4+=ID val5+=ID)+ "kw3" (val6+=ID val7+=ID)*;
 @Override public ParserRule getRule() { return rule; }
 
 //"#4" val1=ID "kw1" (val2=ID val3=ID)? "kw2" (val4+=ID val5+=ID)+ "kw3" (val6+=ID val7+=ID)*
 public Group getGroup() { return cGroup; }
 
 //"#4"
 public Keyword getNumberSignDigitFourKeyword_0() { return cNumberSignDigitFourKeyword_0; }
 
 //val1=ID
 public Assignment getVal1Assignment_1() { return cVal1Assignment_1; }
 
 //ID
 public RuleCall getVal1IDTerminalRuleCall_1_0() { return cVal1IDTerminalRuleCall_1_0; }
 
 //"kw1"
 public Keyword getKw1Keyword_2() { return cKw1Keyword_2; }
 
 //(val2=ID val3=ID)?
 public Group getGroup_3() { return cGroup_3; }
 
 //val2=ID
 public Assignment getVal2Assignment_3_0() { return cVal2Assignment_3_0; }
 
 //ID
 public RuleCall getVal2IDTerminalRuleCall_3_0_0() { return cVal2IDTerminalRuleCall_3_0_0; }
 
 //val3=ID
 public Assignment getVal3Assignment_3_1() { return cVal3Assignment_3_1; }
 
 //ID
 public RuleCall getVal3IDTerminalRuleCall_3_1_0() { return cVal3IDTerminalRuleCall_3_1_0; }
 
 //"kw2"
 public Keyword getKw2Keyword_4() { return cKw2Keyword_4; }
 
 //(val4+=ID val5+=ID)+
 public Group getGroup_5() { return cGroup_5; }
 
 //val4+=ID
 public Assignment getVal4Assignment_5_0() { return cVal4Assignment_5_0; }
 
 //ID
 public RuleCall getVal4IDTerminalRuleCall_5_0_0() { return cVal4IDTerminalRuleCall_5_0_0; }
 
 //val5+=ID
 public Assignment getVal5Assignment_5_1() { return cVal5Assignment_5_1; }
 
 //ID
 public RuleCall getVal5IDTerminalRuleCall_5_1_0() { return cVal5IDTerminalRuleCall_5_1_0; }
 
 //"kw3"
 public Keyword getKw3Keyword_6() { return cKw3Keyword_6; }
 
 //(val6+=ID val7+=ID)*
 public Group getGroup_7() { return cGroup_7; }
 
 //val6+=ID
 public Assignment getVal6Assignment_7_0() { return cVal6Assignment_7_0; }
 
 //ID
 public RuleCall getVal6IDTerminalRuleCall_7_0_0() { return cVal6IDTerminalRuleCall_7_0_0; }
 
 //val7+=ID
 public Assignment getVal7Assignment_7_1() { return cVal7Assignment_7_1; }
 
 //ID
 public RuleCall getVal7IDTerminalRuleCall_7_1_0() { return cVal7IDTerminalRuleCall_7_1_0; }
	}