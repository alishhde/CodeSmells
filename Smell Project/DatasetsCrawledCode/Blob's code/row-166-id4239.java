public class BeamIntersectRel extends Intersect implements BeamRelNode {
 public BeamIntersectRel(
 RelOptCluster cluster, RelTraitSet traits, List<RelNode> inputs, boolean all) {
 super(cluster, traits, inputs, all);
  }


 @Override
 public SetOp copy(RelTraitSet traitSet, List<RelNode> inputs, boolean all) {
 return new BeamIntersectRel(getCluster(), traitSet, inputs, all);
  }


 @Override
 public PTransform<PCollectionList<Row>, PCollection<Row>> buildPTransform() {
 return new BeamSetOperatorRelBase(this, BeamSetOperatorRelBase.OpType.INTERSECT, all);
  }
}