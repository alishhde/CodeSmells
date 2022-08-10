 @Override
 public Explanation explain(LeafReaderContext context, int doc) throws IOException {
 Scorer scorer = scorer(context);
 if (scorer != null) {
 int newDoc = scorer.iterator().advance(doc);
 if (newDoc == doc) {
 final float freq;
 if (scorer instanceof BM25FScorer) {
 freq = ((BM25FScorer) scorer).freq();
          } else {
 assert scorer instanceof TermScorer;
 freq = ((TermScorer) scorer).freq();
          }
 final MultiNormsLeafSimScorer docScorer =
 new MultiNormsLeafSimScorer(simWeight, context.reader(), fieldAndWeights.values(), true);
 Explanation freqExplanation = Explanation.match(freq, "termFreq=" + freq);
 Explanation scoreExplanation = docScorer.explain(doc, freqExplanation);
 return Explanation.match(
 scoreExplanation.getValue(),
 "weight(" + getQuery() + " in " + doc + ") ["
                  + similarity.getClass().getSimpleName() + "], result of:",
 scoreExplanation);
        }
      }
 return Explanation.noMatch("no matching term");
    }