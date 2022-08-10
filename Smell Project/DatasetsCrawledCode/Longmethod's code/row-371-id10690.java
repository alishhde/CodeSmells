 public final EObject ruleXOtherOperatorExpression() throws RecognitionException {
 EObject current = null;


 EObject this_XAdditiveExpression_0 = null;


 EObject lv_rightOperand_3_0 = null;






 enterRule();


 try {
 // InternalXbase.g:873:2: ( (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* ) )
 // InternalXbase.g:874:2: (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* )
            {
 // InternalXbase.g:874:2: (this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )* )
 // InternalXbase.g:875:3: this_XAdditiveExpression_0= ruleXAdditiveExpression ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )*
            {
 if ( state.backtracking==0 ) {


 newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getXAdditiveExpressionParserRuleCall_0());
 
            }
 pushFollow(FOLLOW_14);
 this_XAdditiveExpression_0=ruleXAdditiveExpression();


 state._fsp--;
 if (state.failed) return current;
 if ( state.backtracking==0 ) {


 current = this_XAdditiveExpression_0;
 afterParserOrEnumRuleCall();
 
            }
 // InternalXbase.g:883:3: ( ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) ) )*
 loop11:
 do {
 int alt11=2;
 alt11 = dfa11.predict(input);
 switch (alt11) {
 case 1 :
 // InternalXbase.g:884:4: ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) ) ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) )
            	    {
 // InternalXbase.g:884:4: ( ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) ) )
 // InternalXbase.g:885:5: ( ( () ( ( ruleOpOther ) ) ) )=> ( () ( ( ruleOpOther ) ) )
            	    {
 // InternalXbase.g:895:5: ( () ( ( ruleOpOther ) ) )
 // InternalXbase.g:896:6: () ( ( ruleOpOther ) )
            	    {
 // InternalXbase.g:896:6: ()
 // InternalXbase.g:897:7: 
            	    {
 if ( state.backtracking==0 ) {


 current = forceCreateModelElementAndSet(
 grammarAccess.getXOtherOperatorExpressionAccess().getXBinaryOperationLeftOperandAction_1_0_0_0(),
 current);
 
            	    }


            	    }


 // InternalXbase.g:903:6: ( ( ruleOpOther ) )
 // InternalXbase.g:904:7: ( ruleOpOther )
            	    {
 // InternalXbase.g:904:7: ( ruleOpOther )
 // InternalXbase.g:905:8: ruleOpOther
            	    {
 if ( state.backtracking==0 ) {


 if (current==null) {
 current = createModelElement(grammarAccess.getXOtherOperatorExpressionRule());
            	      								}
 
            	    }
 if ( state.backtracking==0 ) {


 newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getFeatureJvmIdentifiableElementCrossReference_1_0_0_1_0());
 
            	    }
 pushFollow(FOLLOW_4);
 ruleOpOther();


 state._fsp--;
 if (state.failed) return current;
 if ( state.backtracking==0 ) {


 afterParserOrEnumRuleCall();
 
            	    }


            	    }




            	    }




            	    }




            	    }


 // InternalXbase.g:921:4: ( (lv_rightOperand_3_0= ruleXAdditiveExpression ) )
 // InternalXbase.g:922:5: (lv_rightOperand_3_0= ruleXAdditiveExpression )
            	    {
 // InternalXbase.g:922:5: (lv_rightOperand_3_0= ruleXAdditiveExpression )
 // InternalXbase.g:923:6: lv_rightOperand_3_0= ruleXAdditiveExpression
            	    {
 if ( state.backtracking==0 ) {


 newCompositeNode(grammarAccess.getXOtherOperatorExpressionAccess().getRightOperandXAdditiveExpressionParserRuleCall_1_1_0());
 
            	    }
 pushFollow(FOLLOW_14);
 lv_rightOperand_3_0=ruleXAdditiveExpression();


 state._fsp--;
 if (state.failed) return current;
 if ( state.backtracking==0 ) {


 if (current==null) {
 current = createModelElementForParent(grammarAccess.getXOtherOperatorExpressionRule());
            	      						}
 set(
 current,
 "rightOperand",
 lv_rightOperand_3_0,
 "org.eclipse.xtext.xbase.Xbase.XAdditiveExpression");
 afterParserOrEnumRuleCall();
 
            	    }


            	    }




            	    }




            	    }
 break;


 default :
 break loop11;
                }
            } while (true);




            }




            }


 if ( state.backtracking==0 ) {


 leaveRule();


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