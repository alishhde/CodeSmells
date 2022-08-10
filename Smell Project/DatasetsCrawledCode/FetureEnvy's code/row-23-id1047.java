 @Override
 public State computeBreaks(CommentsHelper commentsHelper, int maxWidth, State state) {
 text = commentsHelper.rewrite(tok, maxWidth, state.column);
 int firstLineLength = text.length() - Iterators.getLast(Newlines.lineOffsetIterator(text));
 return state.withColumn(state.column + firstLineLength);
    }