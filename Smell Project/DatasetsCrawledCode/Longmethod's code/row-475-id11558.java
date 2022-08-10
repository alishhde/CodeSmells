 public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
 TokenStream input = (TokenStream)_input;
 int _s = s;
 switch ( s ) {
 case 0 : 
 int LA60_0 = input.LA(1);


 
 int index60_0 = input.index();
 input.rewind();
 s = -1;
 if ( (LA60_0==RULE_ID) ) {s = 1;}


 else if ( (LA60_0==14) ) {s = 2;}


 else if ( (LA60_0==39) && (synpred33_InternalXbaseWithAnnotations())) {s = 3;}


 else if ( ((LA60_0>=RULE_STRING && LA60_0<=RULE_DECIMAL)||(LA60_0>=18 && LA60_0<=19)||LA60_0==26||(LA60_0>=42 && LA60_0<=43)||LA60_0==48||LA60_0==55||LA60_0==59||LA60_0==61||(LA60_0>=65 && LA60_0<=67)||(LA60_0>=70 && LA60_0<=82)||LA60_0==84) ) {s = 4;}


 
 input.seek(index60_0);
 if ( s>=0 ) return s;
 break;
 case 1 : 
 int LA60_1 = input.LA(1);


 
 int index60_1 = input.index();
 input.rewind();
 s = -1;
 if ( (synpred33_InternalXbaseWithAnnotations()) ) {s = 3;}


 else if ( (true) ) {s = 4;}


 
 input.seek(index60_1);
 if ( s>=0 ) return s;
 break;
 case 2 : 
 int LA60_2 = input.LA(1);


 
 int index60_2 = input.index();
 input.rewind();
 s = -1;
 if ( (synpred33_InternalXbaseWithAnnotations()) ) {s = 3;}


 else if ( (true) ) {s = 4;}


 
 input.seek(index60_2);
 if ( s>=0 ) return s;
 break;
            }
 if (state.backtracking>0) {state.failed=true; return -1;}
 NoViableAltException nvae =
 new NoViableAltException(getDescription(), 60, _s, input);
 error(nvae);
 throw nvae;
        }