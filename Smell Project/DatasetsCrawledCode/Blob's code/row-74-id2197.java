public class PlanModifierUtil {


 private static final Logger LOG = LoggerFactory.getLogger(PlanModifierUtil.class);




 protected static void fixTopOBSchema(final RelNode rootRel,
 Pair<RelNode, RelNode> topSelparentPair, List<FieldSchema> resultSchema,
 boolean replaceProject) throws CalciteSemanticException {
 if (!(topSelparentPair.getKey() instanceof Sort)
        || !HiveCalciteUtil.orderRelNode(topSelparentPair.getKey())) {
 return;
    }
 HiveSortLimit obRel = (HiveSortLimit) topSelparentPair.getKey();
 Project obChild = (Project) topSelparentPair.getValue();
 if (obChild.getRowType().getFieldCount() <= resultSchema.size()) {
 return;
    }


 RelDataType rt = obChild.getRowType();
 @SuppressWarnings({ "unchecked", "rawtypes" })
 Set<Integer> collationInputRefs = new HashSet(
 RelCollations.ordinals(obRel.getCollation()));
 ImmutableMap.Builder<Integer, RexNode> inputRefToCallMapBldr = ImmutableMap.builder();
 for (int i = resultSchema.size(); i < rt.getFieldCount(); i++) {
 if (collationInputRefs.contains(i)) {
 RexNode obyExpr = obChild.getChildExps().get(i);
 if (obyExpr instanceof RexCall) {
 LOG.debug("Old RexCall : " + obyExpr);
 obyExpr = adjustOBSchema((RexCall) obyExpr, obChild, resultSchema);
 LOG.debug("New RexCall : " + obyExpr);
        }
 inputRefToCallMapBldr.put(i, obyExpr);
      }
    }
 ImmutableMap<Integer, RexNode> inputRefToCallMap = inputRefToCallMapBldr.build();


 if ((obChild.getRowType().getFieldCount() - inputRefToCallMap.size()) != resultSchema.size()) {
 LOG.error(generateInvalidSchemaMessage(obChild, resultSchema, inputRefToCallMap.size()));
 throw new CalciteSemanticException("Result Schema didn't match Optimized Op Tree Schema");
    }


 if (replaceProject) {
 // This removes order-by only expressions from the projections.
 HiveProject replacementProjectRel = HiveProject.create(obChild.getInput(), obChild
          .getChildExps().subList(0, resultSchema.size()), obChild.getRowType().getFieldNames()
          .subList(0, resultSchema.size()));
 obRel.replaceInput(0, replacementProjectRel);
    }
 obRel.setInputRefToCallMap(inputRefToCallMap);
  }


 private static RexCall adjustOBSchema(RexCall obyExpr, Project obChild,
 List<FieldSchema> resultSchema) {
 int a = -1;
 List<RexNode> operands = new ArrayList<>();
 for (int k = 0; k < obyExpr.operands.size(); k++) {
 RexNode rn = obyExpr.operands.get(k);
 for (int j = 0; j < resultSchema.size(); j++) {
 if( obChild.getChildExps().get(j).toString().equals(rn.toString())) {
 a = j;
 break;
        }
      }
 if (a != -1) {
 operands.add(new RexInputRef(a, rn.getType()));
      } else {
 if (rn instanceof RexCall) {
 operands.add(adjustOBSchema((RexCall)rn, obChild, resultSchema));
        } else {
 operands.add(rn);
        }
      }
 a = -1;
    }
 return (RexCall) obChild.getCluster().getRexBuilder().makeCall(
 obyExpr.getType(), obyExpr.getOperator(), operands);
  }


 protected static String generateInvalidSchemaMessage(Project topLevelProj,
 List<FieldSchema> resultSchema, int fieldsForOB) {
 String errorDesc = "Result Schema didn't match Calcite Optimized Op Tree; schema: ";
 for (FieldSchema fs : resultSchema) {
 errorDesc += "[" + fs.getName() + ":" + fs.getType() + "], ";
    }
 errorDesc += " projection fields: ";
 for (RexNode exp : topLevelProj.getChildExps()) {
 errorDesc += "[" + exp.toString() + ":" + exp.getType() + "], ";
    }
 if (fieldsForOB != 0) {
 errorDesc += fieldsForOB + " fields removed due to ORDER BY  ";
    }
 return errorDesc.substring(0, errorDesc.length() - 2);
  }


}