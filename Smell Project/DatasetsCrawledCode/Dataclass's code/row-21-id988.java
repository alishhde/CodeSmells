@AutoValue
abstract class UOfKind extends UExpression {
 public static UOfKind create(UExpression expression, Set<Kind> allowed) {
 return new AutoValue_UOfKind(expression, allowed);
  }


 abstract UExpression expression();


 abstract Set<Kind> allowed();


 @Override
 public JCExpression inline(Inliner inliner) throws CouldNotResolveImportException {
 return expression().inline(inliner);
  }


 @Override
 public <R, D> R accept(TreeVisitor<R, D> visitor, D data) {
 return expression().accept(visitor, data);
  }


 @Override
 public Kind getKind() {
 return expression().getKind();
  }


 @Override
 @Nullable
 protected Choice<Unifier> defaultAction(Tree tree, @Nullable Unifier unifier) {
 return Choice.condition(allowed().contains(tree.getKind()), unifier)
        .thenChoose(unifications(expression(), tree));
  }
}