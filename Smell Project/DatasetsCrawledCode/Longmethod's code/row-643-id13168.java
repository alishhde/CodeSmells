 OrdsSegmentTermsEnumFrame pushFrame(FST.Arc<Output> arc, long fp, int length, long termOrd) throws IOException {
 final OrdsSegmentTermsEnumFrame f = getFrame(1+currentFrame.ord);
 f.arc = arc;
 // System.out.println("pushFrame termOrd= " + termOrd + " fpOrig=" + f.fpOrig + " fp=" + fp + " nextEnt=" + f.nextEnt);
 if (f.fpOrig == fp && f.nextEnt != -1) {
 //if (DEBUG) System.out.println("      push reused frame ord=" + f.ord + " fp=" + f.fp + " isFloor?=" + f.isFloor + " hasTerms=" + f.hasTerms + " pref=" + term + " nextEnt=" + f.nextEnt + " targetBeforeCurrentLength=" + targetBeforeCurrentLength + " term.length=" + term.length + " vs prefix=" + f.prefix);
 if (f.prefix > targetBeforeCurrentLength) {
 // System.out.println("        do rewind!");
 f.rewind();
      } else {
 // if (DEBUG) {
 // System.out.println("        skip rewind!");
 // }
      }
 assert length == f.prefix;
 assert termOrd == f.termOrdOrig;
    } else {
 f.nextEnt = -1;
 f.prefix = length;
 f.state.termBlockOrd = 0;
 f.termOrdOrig = termOrd;
 // System.out.println("set termOrdOrig=" + termOrd);
 f.termOrd = termOrd;
 f.fpOrig = f.fp = fp;
 f.lastSubFP = -1;
 // if (DEBUG) {
 //   final int sav = term.length;
 //   term.length = length;
 //   System.out.println("      push new frame ord=" + f.ord + " fp=" + f.fp + " hasTerms=" + f.hasTerms + " isFloor=" + f.isFloor + " pref=" + brToString(term));
 //   term.length = sav;
 // }
    }


 return f;
  }