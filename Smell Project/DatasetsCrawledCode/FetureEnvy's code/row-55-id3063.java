 public static final <S, E> Iterator<E> applyAll(final S start, final Traversal.Admin<S, E> traversal) {
 traversal.reset();
 traversal.addStart(traversal.getTraverserGenerator().generate(start, traversal.getStartStep(), 1l));
 return traversal; // flatMap
    }