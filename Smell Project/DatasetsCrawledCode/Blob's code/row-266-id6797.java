 class DFA28 extends DFA {


 public DFA28(BaseRecognizer recognizer) {
 this.recognizer = recognizer;
 this.decisionNumber = 28;
 this.eot = dfa_9;
 this.eof = dfa_21;
 this.min = dfa_10;
 this.max = dfa_11;
 this.accept = dfa_12;
 this.special = dfa_13;
 this.transition = dfa_14;
        }
 public String getDescription() {
 return "4005:2: ( rule__Object__UnorderedGroup_5__5 )?";
        }
 public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
 TokenStream input = (TokenStream)_input;
 int _s = s;
 switch ( s ) {
 case 0 : 
 int LA28_0 = input.LA(1);


 
 int index28_0 = input.index();
 input.rewind();
 s = -1;
 if ( LA28_0 == 19 && getUnorderedGroupHelper().canSelect(grammarAccess.getObjectAccess().getUnorderedGroup_5(), 0) ) {s = 1;}


 else if ( LA28_0 == 20 && getUnorderedGroupHelper().canSelect(grammarAccess.getObjectAccess().getUnorderedGroup_5(), 1) ) {s = 2;}


 else if ( LA28_0 == 21 && getUnorderedGroupHelper().canSelect(grammarAccess.getObjectAccess().getUnorderedGroup_5(), 2) ) {s = 3;}


 else if ( LA28_0 == 33 && getUnorderedGroupHelper().canSelect(grammarAccess.getObjectAccess().getUnorderedGroup_5(), 3) ) {s = 4;}


 else if ( LA28_0 == 26 && getUnorderedGroupHelper().canSelect(grammarAccess.getObjectAccess().getUnorderedGroup_5(), 4) ) {s = 5;}


 else if ( LA28_0 == 27 && getUnorderedGroupHelper().canSelect(grammarAccess.getObjectAccess().getUnorderedGroup_5(), 4) ) {s = 6;}


 else if ( LA28_0 == 22 && getUnorderedGroupHelper().canSelect(grammarAccess.getObjectAccess().getUnorderedGroup_5(), 5) ) {s = 7;}


 else if ( LA28_0 == 24 && getUnorderedGroupHelper().canSelect(grammarAccess.getObjectAccess().getUnorderedGroup_5(), 6) ) {s = 8;}


 else if ( LA28_0 == 25 && getUnorderedGroupHelper().canSelect(grammarAccess.getObjectAccess().getUnorderedGroup_5(), 7) ) {s = 9;}


 else if ( (LA28_0==EOF||LA28_0==17) ) {s = 10;}


 
 input.seek(index28_0);
 if ( s>=0 ) return s;
 break;
            }
 if (state.backtracking>0) {state.failed=true; return -1;}
 NoViableAltException nvae =
 new NoViableAltException(getDescription(), 28, _s, input);
 error(nvae);
 throw nvae;
        }
    }