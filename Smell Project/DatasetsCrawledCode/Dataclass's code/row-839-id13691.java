 public class AnElementElements extends AbstractParserRuleElementFinder {
 private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.xtext.ui.tests.editor.contentassist.TwoContextsTestLanguage.AnElement");
 private final Group cGroup = (Group)rule.eContents().get(1);
 private final Assignment cNameAssignment_0 = (Assignment)cGroup.eContents().get(0);
 private final RuleCall cNameIDTerminalRuleCall_0_0 = (RuleCall)cNameAssignment_0.eContents().get(0);
 private final Group cGroup_1 = (Group)cGroup.eContents().get(1);
 private final Keyword cRefersToKeyword_1_0 = (Keyword)cGroup_1.eContents().get(0);
 private final Assignment cReferredAssignment_1_1 = (Assignment)cGroup_1.eContents().get(1);
 private final CrossReference cReferredAnElementCrossReference_1_1_0 = (CrossReference)cReferredAssignment_1_1.eContents().get(0);
 private final RuleCall cReferredAnElementIDTerminalRuleCall_1_1_0_1 = (RuleCall)cReferredAnElementCrossReference_1_1_0.eContents().get(1);
 private final Keyword cSemicolonKeyword_2 = (Keyword)cGroup.eContents().get(2);
 
 //AnElement:
 //	name=ID ('refersTo' referred=[AnElement])? ';';
 @Override public ParserRule getRule() { return rule; }
 
 //name=ID ('refersTo' referred=[AnElement])? ';'
 public Group getGroup() { return cGroup; }
 
 //name=ID
 public Assignment getNameAssignment_0() { return cNameAssignment_0; }
 
 //ID
 public RuleCall getNameIDTerminalRuleCall_0_0() { return cNameIDTerminalRuleCall_0_0; }
 
 //('refersTo' referred=[AnElement])?
 public Group getGroup_1() { return cGroup_1; }
 
 //'refersTo'
 public Keyword getRefersToKeyword_1_0() { return cRefersToKeyword_1_0; }
 
 //referred=[AnElement]
 public Assignment getReferredAssignment_1_1() { return cReferredAssignment_1_1; }
 
 //[AnElement]
 public CrossReference getReferredAnElementCrossReference_1_1_0() { return cReferredAnElementCrossReference_1_1_0; }
 
 //ID
 public RuleCall getReferredAnElementIDTerminalRuleCall_1_1_0_1() { return cReferredAnElementIDTerminalRuleCall_1_1_0_1; }
 
 //';'
 public Keyword getSemicolonKeyword_2() { return cSemicolonKeyword_2; }
	}