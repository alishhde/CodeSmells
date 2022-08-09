 private static class FieldMetaData {
 public final FieldInfo fieldInfo;
 public final long numTerms;
 public final long sumTotalTermFreq;
 public final long sumDocFreq;
 public final int docCount;
 public final int longsSize;
 public final FST<FSTTermOutputs.TermData> dict;


 public FieldMetaData(FieldInfo fieldInfo, long numTerms, long sumTotalTermFreq, long sumDocFreq, int docCount, int longsSize, FST<FSTTermOutputs.TermData> fst) {
 this.fieldInfo = fieldInfo;
 this.numTerms = numTerms;
 this.sumTotalTermFreq = sumTotalTermFreq;
 this.sumDocFreq = sumDocFreq;
 this.docCount = docCount;
 this.longsSize = longsSize;
 this.dict = fst;
    }
  }