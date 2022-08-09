 protected class UnifyRuleCall {
 protected final UnifyRule rule;
 public final MutableRel query;
 public final MutableRel target;
 protected final ImmutableList<MutableRel> slots;


 public UnifyRuleCall(UnifyRule rule, MutableRel query, MutableRel target,
 ImmutableList<MutableRel> slots) {
 this.rule = Objects.requireNonNull(rule);
 this.query = Objects.requireNonNull(query);
 this.target = Objects.requireNonNull(target);
 this.slots = Objects.requireNonNull(slots);
    }


 public UnifyResult result(MutableRel result) {
 assert MutableRels.contains(result, target);
 assert equalType("result", result, "query", query,
 Litmus.THROW);
 MutableRel replace = replacementMap.get(target);
 if (replace != null) {
 assert false; // replacementMap is always empty
 // result =
 replace(result, target, replace);
      }
 register(result, query);
 return new UnifyResult(this, result);
    }


 /**
     * Creates a {@link UnifyRuleCall} based on the parent of {@code query}.
     */
 public UnifyRuleCall create(MutableRel query) {
 return new UnifyRuleCall(rule, query, target, slots);
    }


 public RelOptCluster getCluster() {
 return cluster;
    }


 public RexSimplify getSimplify() {
 return simplify;
    }
  }