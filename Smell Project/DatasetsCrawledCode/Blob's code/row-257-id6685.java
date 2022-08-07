 static class DFA12 extends DFA {


 public DFA12(BaseRecognizer recognizer) {
 this.recognizer = recognizer;
 this.decisionNumber = 12;
 this.eot = DFA12_eot;
 this.eof = DFA12_eof;
 this.min = DFA12_min;
 this.max = DFA12_max;
 this.accept = DFA12_accept;
 this.special = DFA12_special;
 this.transition = DFA12_transition;
        }
 public String getDescription() {
 return "1:1: Tokens : ( T__11 | T__12 | T__13 | T__14 | T__15 | T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | RULE_ID | RULE_INT | RULE_STRING | RULE_ML_COMMENT | RULE_SL_COMMENT | RULE_WS | RULE_ANY_OTHER );";
        }
 public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
 IntStream input = _input;
 int _s = s;
 switch ( s ) {
 case 0 : 
 int LA12_0 = input.LA(1);


 s = -1;
 if ( (LA12_0=='s') ) {s = 1;}


 else if ( (LA12_0=='c') ) {s = 2;}


 else if ( (LA12_0=='i') ) {s = 3;}


 else if ( (LA12_0=='a') ) {s = 4;}


 else if ( (LA12_0=='d') ) {s = 5;}


 else if ( (LA12_0=='^') ) {s = 6;}


 else if ( ((LA12_0>='A' && LA12_0<='Z')||LA12_0=='_'||LA12_0=='b'||(LA12_0>='e' && LA12_0<='h')||(LA12_0>='j' && LA12_0<='r')||(LA12_0>='t' && LA12_0<='z')) ) {s = 7;}


 else if ( ((LA12_0>='0' && LA12_0<='9')) ) {s = 8;}


 else if ( (LA12_0=='\"') ) {s = 9;}


 else if ( (LA12_0=='\'') ) {s = 10;}


 else if ( (LA12_0=='/') ) {s = 11;}


 else if ( ((LA12_0>='\t' && LA12_0<='\n')||LA12_0=='\r'||LA12_0==' ') ) {s = 12;}


 else if ( ((LA12_0>='\u0000' && LA12_0<='\b')||(LA12_0>='\u000B' && LA12_0<='\f')||(LA12_0>='\u000E' && LA12_0<='\u001F')||LA12_0=='!'||(LA12_0>='#' && LA12_0<='&')||(LA12_0>='(' && LA12_0<='.')||(LA12_0>=':' && LA12_0<='@')||(LA12_0>='[' && LA12_0<=']')||LA12_0=='`'||(LA12_0>='{' && LA12_0<='\uFFFF')) ) {s = 13;}


 if ( s>=0 ) return s;
 break;
 case 1 : 
 int LA12_9 = input.LA(1);


 s = -1;
 if ( ((LA12_9>='\u0000' && LA12_9<='\uFFFF')) ) {s = 22;}


 else s = 13;


 if ( s>=0 ) return s;
 break;
 case 2 : 
 int LA12_10 = input.LA(1);


 s = -1;
 if ( ((LA12_10>='\u0000' && LA12_10<='\uFFFF')) ) {s = 22;}


 else s = 13;


 if ( s>=0 ) return s;
 break;
            }
 NoViableAltException nvae =
 new NoViableAltException(getDescription(), 12, _s, input);
 error(nvae);
 throw nvae;
        }
    }