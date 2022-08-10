 public final void mRULE_ML_COMMENT() throws RecognitionException {
 try {
 int _type = RULE_ML_COMMENT;
 int _channel = DEFAULT_TOKEN_CHANNEL;
 // InternalCrossReferenceProposalTestLanguage.g:169:17: ( '/*' ( options {greedy=false; } : . )* '*/' )
 // InternalCrossReferenceProposalTestLanguage.g:169:19: '/*' ( options {greedy=false; } : . )* '*/'
            {
 match("/*"); 


 // InternalCrossReferenceProposalTestLanguage.g:169:24: ( options {greedy=false; } : . )*
 loop7:
 do {
 int alt7=2;
 int LA7_0 = input.LA(1);


 if ( (LA7_0=='*') ) {
 int LA7_1 = input.LA(2);


 if ( (LA7_1=='/') ) {
 alt7=2;
                    }
 else if ( ((LA7_1>='\u0000' && LA7_1<='.')||(LA7_1>='0' && LA7_1<='\uFFFF')) ) {
 alt7=1;
                    }




                }
 else if ( ((LA7_0>='\u0000' && LA7_0<=')')||(LA7_0>='+' && LA7_0<='\uFFFF')) ) {
 alt7=1;
                }




 switch (alt7) {
 case 1 :
 // InternalCrossReferenceProposalTestLanguage.g:169:52: .
            	    {
 matchAny(); 


            	    }
 break;


 default :
 break loop7;
                }
            } while (true);


 match("*/"); 




            }


 state.type = _type;
 state.channel = _channel;
        }
 finally {
        }
    }