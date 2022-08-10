 public void bind(PropertyList pList) throws FOPException {
 super.bind(pList);
 alignmentAdjust = pList.get(PR_ALIGNMENT_ADJUST).getLength();
 alignmentBaseline = pList.get(PR_ALIGNMENT_BASELINE).getEnum();
 baselineShift = pList.get(PR_BASELINE_SHIFT).getLength();
 dominantBaseline = pList.get(PR_DOMINANT_BASELINE).getEnum();
 leaderAlignment = pList.get(PR_LEADER_ALIGNMENT).getEnum();
 leaderLength = pList.get(PR_LEADER_LENGTH).getLengthRange();
 leaderPattern = pList.get(PR_LEADER_PATTERN).getEnum();
 leaderPatternWidth = pList.get(PR_LEADER_PATTERN_WIDTH).getLength();
 // use default rule thickness as a default
 ruleThickness = getPropertyMakerFor(PR_RULE_THICKNESS).make(pList).getLength();
 switch(leaderPattern) {
 case EN_SPACE:
 // use Space
 break;
 case EN_RULE:
 // the following properties only apply
 // for leader-pattern = "rule"
 ruleStyle = pList.get(PR_RULE_STYLE).getEnum();
 // use specified rule thickness to override default (established above)
 ruleThickness = pList.get(PR_RULE_THICKNESS).getLength();
 break;
 case EN_DOTS:
 break;
 case EN_USECONTENT:
 // use inline layout manager to create inline areas
 // add the inline parent multiple times until leader full
 break;
 default:
 throw new RuntimeException("Invalid leader pattern: " + leaderPattern);
        }
 // letterSpacing = pList.get(PR_LETTER_SPACING);
 // textShadow = pList.get(PR_TEXT_SHADOW);
    }