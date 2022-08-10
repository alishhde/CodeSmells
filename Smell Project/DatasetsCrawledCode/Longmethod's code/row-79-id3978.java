 public final EObject entryRuleXMultiplicativeExpression() throws RecognitionException {
 EObject current = null;


 EObject iv_ruleXMultiplicativeExpression = null;




 try {
 // InternalEntities.g:1696:66: (iv_ruleXMultiplicativeExpression= ruleXMultiplicativeExpression EOF )
 // InternalEntities.g:1697:2: iv_ruleXMultiplicativeExpression= ruleXMultiplicativeExpression EOF
            {
 if ( state.backtracking==0 ) {
 newCompositeNode(grammarAccess.getXMultiplicativeExpressionRule()); 
            }
 pushFollow(FOLLOW_1);
 iv_ruleXMultiplicativeExpression=ruleXMultiplicativeExpression();


 state._fsp--;
 if (state.failed) return current;
 if ( state.backtracking==0 ) {
 current =iv_ruleXMultiplicativeExpression; 
            }
 match(input,EOF,FOLLOW_2); if (state.failed) return current;


            }


        }


 catch (RecognitionException re) {
 recover(input,re);
 appendSkippedTokens();
            }
 finally {
        }
 return current;
    }