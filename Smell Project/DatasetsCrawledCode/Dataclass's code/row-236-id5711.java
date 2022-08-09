public abstract class DrillScanRelBase extends TableScan implements DrillRelNode {
 protected GroupScan groupScan;
 protected final DrillTable drillTable;


 public DrillScanRelBase(RelOptCluster cluster,
 RelTraitSet traits,
 RelOptTable table,
 final List<SchemaPath> columns) {
 super(cluster, traits, table);
 this.drillTable = Utilities.getDrillTable(table);
 assert drillTable != null;
 try {
 this.groupScan = drillTable.getGroupScan().clone(columns);
    } catch (final IOException e) {
 throw new DrillRuntimeException("Failure creating scan.", e);
    }
  }


 public DrillScanRelBase(RelOptCluster cluster,
 RelTraitSet traits,
 GroupScan grpScan,
 RelOptTable table) {
 super(cluster, traits, table);
 DrillTable unwrap = table.unwrap(DrillTable.class);
 if (unwrap == null) {
 unwrap = table.unwrap(DrillTranslatableTable.class).getDrillTable();
    }
 this.drillTable = unwrap;
 assert drillTable != null;
 this.groupScan = grpScan;
  }


 public DrillTable getDrillTable() {
 return drillTable;
  }


 public GroupScan getGroupScan() {
 return groupScan;
  }


 @Override public double estimateRowCount(RelMetadataQuery mq) {
 return mq.getRowCount(this);
  }


 @Override public RelOptCost computeSelfCost(RelOptPlanner planner, RelMetadataQuery mq) {
 double dRows = estimateRowCount(mq);
 double dCpu = dRows + 1; // ensure non-zero cost
 double dIo = 0;
 return planner.getCostFactory().makeCost(dRows, dCpu, dIo);
  }
}