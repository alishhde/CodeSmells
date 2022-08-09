 public class IteratorVariableElements extends AbstractParserRuleElementFinder {
 private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.xtext.ui.tests.editor.contentassist.BacktrackingContentAssistTestLanguage.iteratorVariable");
 private final Group cGroup = (Group)rule.eContents().get(1);
 private final Assignment cNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
 private final RuleCall cNameIdentifierParserRuleCall_0_0 = (RuleCall)cNameAssignment_0.eContents().get(0);
 private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
 private final Keyword cColonKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
 private final Assignment cTypeAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
 private final RuleCall cTypeTypeExpParserRuleCall_1_1_0 = (RuleCall)cTypeAssignment_1_1.eContents().get(0);
 
 //iteratorVariable:
 //	name=Identifier (':' type=TypeExp)?;
 @Override public ParserRule getRule() { return rule; }
 
 //name=Identifier (':' type=TypeExp)?
 public Group getGroup() { return cGroup; }
 
 //name=Identifier
 public Assignment getNameAssignment_0() { return cNameAssignment_0; }
 
 //Identifier
 public RuleCall getNameIdentifierParserRuleCall_0_0() { return cNameIdentifierParserRuleCall_0_0; }
 
 //(':' type=TypeExp)?
 public Group getGroup_1() { return cGroup_1; }
 
 //':'
 public Keyword getColonKeyword_1_0() { return cColonKeyword_1_0; }
 
 //type=TypeExp
 public Assignment getTypeAssignment_1_1() { return cTypeAssignment_1_1; }
 
 //TypeExp
 public RuleCall getTypeTypeExpParserRuleCall_1_1_0() { return cTypeTypeExpParserRuleCall_1_1_0; }
	}