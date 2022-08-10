 @Override
 public Validator childNodeChanged(String name, NodeState before, NodeState after) throws CommitFailedException {
 Tree beforeTree = (parentBefore == null) ? null : parentBefore.getChild(name);
 Tree afterTree = parentAfter.getChild(name);


 if (isTokenTree(beforeTree) || isTokenTree(afterTree)) {
 validateTokenTree(afterTree);
            } else if (isTokensParent(beforeTree) || isTokensParent(afterTree)) {
 validateTokensParent(afterTree);
            }


 return new VisibleValidator(new TokenValidator(beforeTree, afterTree, commitInfo), true, true);
        }