 public class ExistingEnumElements extends AbstractEnumRuleElementFinder {
 private final EnumRule rule = (EnumRule) GrammarUtil.findRuleForName(getGrammar(), "org.eclipse.xtext.enumrules.EnumRulesTestLanguage.ExistingEnum");
 private final Alternatives cAlternatives = (Alternatives)rule.eContents().get(1);
 private final EnumLiteralDeclaration cSameNameEnumLiteralDeclaration_0 = (EnumLiteralDeclaration)cAlternatives.eContents().get(0);
 private final Keyword cSameNameSameNameKeyword_0_0 = (Keyword)cSameNameEnumLiteralDeclaration_0.eContents().get(0);
 private final EnumLiteralDeclaration cOverriddenLiteralEnumLiteralDeclaration_1 = (EnumLiteralDeclaration)cAlternatives.eContents().get(1);
 private final Keyword cOverriddenLiteralOverriddenKeyword_1_0 = (Keyword)cOverriddenLiteralEnumLiteralDeclaration_1.eContents().get(0);
 private final EnumLiteralDeclaration cDifferentNameEnumLiteralDeclaration_2 = (EnumLiteralDeclaration)cAlternatives.eContents().get(2);
 private final Keyword cDifferentNameDifferentLiteralKeyword_2_0 = (Keyword)cDifferentNameEnumLiteralDeclaration_2.eContents().get(0);
 
 //enum ExistingEnum:
 //	SameName | OverriddenLiteral="overridden" | DifferentName="DifferentLiteral";
 public EnumRule getRule() { return rule; }
 
 //SameName | OverriddenLiteral="overridden" | DifferentName="DifferentLiteral"
 public Alternatives getAlternatives() { return cAlternatives; }
 
 //SameName
 public EnumLiteralDeclaration getSameNameEnumLiteralDeclaration_0() { return cSameNameEnumLiteralDeclaration_0; }
 
 //"SameName"
 public Keyword getSameNameSameNameKeyword_0_0() { return cSameNameSameNameKeyword_0_0; }
 
 //OverriddenLiteral="overridden"
 public EnumLiteralDeclaration getOverriddenLiteralEnumLiteralDeclaration_1() { return cOverriddenLiteralEnumLiteralDeclaration_1; }
 
 //"overridden"
 public Keyword getOverriddenLiteralOverriddenKeyword_1_0() { return cOverriddenLiteralOverriddenKeyword_1_0; }
 
 //DifferentName="DifferentLiteral"
 public EnumLiteralDeclaration getDifferentNameEnumLiteralDeclaration_2() { return cDifferentNameEnumLiteralDeclaration_2; }
 
 //"DifferentLiteral"
 public Keyword getDifferentNameDifferentLiteralKeyword_2_0() { return cDifferentNameDifferentLiteralKeyword_2_0; }
	}