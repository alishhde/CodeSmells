 public final void mRULE_STRING() throws RecognitionException {
 try {
 int _type = RULE_STRING;
 int _channel = DEFAULT_TOKEN_CHANNEL;
 // InternalXImportSectionTestLang.g:6435:13: ( ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* ( '\"' )? | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* ( '\\'' )? ) )
 // InternalXImportSectionTestLang.g:6435:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* ( '\"' )? | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* ( '\\'' )? )
            {
 // InternalXImportSectionTestLang.g:6435:15: ( '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* ( '\"' )? | '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* ( '\\'' )? )
 int alt15=2;
 int LA15_0 = input.LA(1);


 if ( (LA15_0=='\"') ) {
 alt15=1;
            }
 else if ( (LA15_0=='\'') ) {
 alt15=2;
            }
 else {
 NoViableAltException nvae =
 new NoViableAltException("", 15, 0, input);


 throw nvae;
            }
 switch (alt15) {
 case 1 :
 // InternalXImportSectionTestLang.g:6435:16: '\"' ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )* ( '\"' )?
                    {
 match('\"'); 
 // InternalXImportSectionTestLang.g:6435:20: ( '\\\\' . | ~ ( ( '\\\\' | '\"' ) ) )*
 loop11:
 do {
 int alt11=3;
 int LA11_0 = input.LA(1);


 if ( (LA11_0=='\\') ) {
 alt11=1;
                        }
 else if ( ((LA11_0>='\u0000' && LA11_0<='!')||(LA11_0>='#' && LA11_0<='[')||(LA11_0>=']' && LA11_0<='\uFFFF')) ) {
 alt11=2;
                        }




 switch (alt11) {
 case 1 :
 // InternalXImportSectionTestLang.g:6435:21: '\\\\' .
                    	    {
 match('\\'); 
 matchAny(); 


                    	    }
 break;
 case 2 :
 // InternalXImportSectionTestLang.g:6435:28: ~ ( ( '\\\\' | '\"' ) )
                    	    {
 if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
 input.consume();


                    	    }
 else {
 MismatchedSetException mse = new MismatchedSetException(null,input);
 recover(mse);
 throw mse;}




                    	    }
 break;


 default :
 break loop11;
                        }
                    } while (true);


 // InternalXImportSectionTestLang.g:6435:44: ( '\"' )?
 int alt12=2;
 int LA12_0 = input.LA(1);


 if ( (LA12_0=='\"') ) {
 alt12=1;
                    }
 switch (alt12) {
 case 1 :
 // InternalXImportSectionTestLang.g:6435:44: '\"'
                            {
 match('\"'); 


                            }
 break;


                    }




                    }
 break;
 case 2 :
 // InternalXImportSectionTestLang.g:6435:49: '\\'' ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )* ( '\\'' )?
                    {
 match('\''); 
 // InternalXImportSectionTestLang.g:6435:54: ( '\\\\' . | ~ ( ( '\\\\' | '\\'' ) ) )*
 loop13:
 do {
 int alt13=3;
 int LA13_0 = input.LA(1);


 if ( (LA13_0=='\\') ) {
 alt13=1;
                        }
 else if ( ((LA13_0>='\u0000' && LA13_0<='&')||(LA13_0>='(' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='\uFFFF')) ) {
 alt13=2;
                        }




 switch (alt13) {
 case 1 :
 // InternalXImportSectionTestLang.g:6435:55: '\\\\' .
                    	    {
 match('\\'); 
 matchAny(); 


                    	    }
 break;
 case 2 :
 // InternalXImportSectionTestLang.g:6435:62: ~ ( ( '\\\\' | '\\'' ) )
                    	    {
 if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
 input.consume();


                    	    }
 else {
 MismatchedSetException mse = new MismatchedSetException(null,input);
 recover(mse);
 throw mse;}




                    	    }
 break;


 default :
 break loop13;
                        }
                    } while (true);


 // InternalXImportSectionTestLang.g:6435:79: ( '\\'' )?
 int alt14=2;
 int LA14_0 = input.LA(1);


 if ( (LA14_0=='\'') ) {
 alt14=1;
                    }
 switch (alt14) {
 case 1 :
 // InternalXImportSectionTestLang.g:6435:79: '\\''
                            {
 match('\''); 


                            }
 break;


                    }




                    }
 break;


            }




            }


 state.type = _type;
 state.channel = _channel;
        }
 finally {
        }
    }