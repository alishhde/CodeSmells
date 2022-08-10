 private boolean deriveTypeHierarchyFromOverridden(ParserRule rule, Grammar grammar) throws TransformationException {
 AbstractRule parentRule = GrammarUtil.findRuleForName(grammar, rule.getName());
 if (parentRule != null) {
 if (parentRule != rule && parentRule instanceof ParserRule) {
 ParserRule casted = (ParserRule) parentRule;
 if (casted.isFragment() != rule.isFragment()) {
 if (rule.isFragment()) {
 throw new TransformationException(TransformationErrorCode.InvalidFragmentOverride,
 "A fragment rule cannot override a production rule.", rule);
					} else {
 throw new TransformationException(TransformationErrorCode.InvalidFragmentOverride,
 "Only fragment rule can override other fragment rules.", rule);
					}
				}
 if (casted.isWildcard() != rule.isWildcard()) {
 if (rule.isWildcard()) {
 throw new TransformationException(TransformationErrorCode.InvalidFragmentOverride,
 "A wildcard fragment rule cannot override a typed fragment rule.", rule);
					} else {
 throw new TransformationException(TransformationErrorCode.InvalidFragmentOverride,
 "Only wildcard fragment rules can override other wildcard fragments.", rule);
					}
				}
 if (rule.isFragment() && !rule.isWildcard() && parentRule.getType() != null) {
 if (rule.getType().getClassifier() != parentRule.getType().getClassifier()) {
 throw new TransformationException(TransformationErrorCode.InvalidFragmentOverride,
 "Overriding fragment rules cannot redeclare their type.", rule.getType());
					}
				}
 checkParameterLists(rule, casted);
			}
 if (parentRule.getType() != null && parentRule != rule) {			
 if (parentRule.getType().getClassifier() instanceof EDataType)
 throw new TransformationException(TransformationErrorCode.InvalidSupertype,
 "Cannot inherit from datatype rule and return another type.", rule.getType());
 EClassifierInfo parentTypeInfo = eClassifierInfos.getInfoOrNull(parentRule.getType());
 if (parentTypeInfo == null)
 throw new TransformationException(TransformationErrorCode.InvalidSupertype,
 "Cannot determine return type of overridden rule.", rule.getType());
 addSuperType(rule, rule.getType(), parentTypeInfo);
 return true;
			}
		}
 return false;
	}