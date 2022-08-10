 public IAnnulusWedgeCriteria create() {
 switch (this) {
 case LEAF_NUMBER:
 return new AnnulusWedgeByLeafs();
 case NODE_SIZE:
 return new AnnulusWedgeByNodeSpace();
 default:
 throw new IllegalArgumentException(
 "No implementation is available for the layout option " + this.toString());
        }
    }