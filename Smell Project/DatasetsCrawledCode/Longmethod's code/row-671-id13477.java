 @Override
 public boolean incrementToken() throws IOException {
 for(;;) {


 if (!remainingTokens.isEmpty()) {
 // clearAttributes();  // not currently necessary
 restoreState(remainingTokens.removeFirst());
 return true;
      }


 if (!input.incrementToken()) return false;


 int len = termAtt.length();
 if (len==0) return true; // pass through zero length terms
 
 int firstAlternativeIncrement = inject ? 0 : posAtt.getPositionIncrement();


 String v = termAtt.toString();
 String primaryPhoneticValue = encoder.doubleMetaphone(v);
 String alternatePhoneticValue = encoder.doubleMetaphone(v, true);


 // a flag to lazily save state if needed... this avoids a save/restore when only
 // one token will be generated.
 boolean saveState=inject;


 if (primaryPhoneticValue!=null && primaryPhoneticValue.length() > 0 && !primaryPhoneticValue.equals(v)) {
 if (saveState) {
 remainingTokens.addLast(captureState());
        }
 posAtt.setPositionIncrement( firstAlternativeIncrement );
 firstAlternativeIncrement = 0;
 termAtt.setEmpty().append(primaryPhoneticValue);
 saveState = true;
      }


 if (alternatePhoneticValue!=null && alternatePhoneticValue.length() > 0
              && !alternatePhoneticValue.equals(primaryPhoneticValue)
              && !primaryPhoneticValue.equals(v)) {
 if (saveState) {
 remainingTokens.addLast(captureState());
 saveState = false;
        }
 posAtt.setPositionIncrement( firstAlternativeIncrement );
 termAtt.setEmpty().append(alternatePhoneticValue);
 saveState = true;
      }


 // Just one token to return, so no need to capture/restore
 // any state, simply return it.
 if (remainingTokens.isEmpty()) {
 return true;
      }


 if (saveState) {
 remainingTokens.addLast(captureState());
      }
    }
  }