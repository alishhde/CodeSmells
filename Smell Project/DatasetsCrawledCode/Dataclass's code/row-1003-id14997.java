class FloatFilterFunction extends AbstractFloatValue {
 private final FloatValue baseExpr;
 private final BooleanValue filterExpr;
 public static final String name = FilterFunction.name;
 private final String exprStr;
 private final ExpressionType funcType;
 
 public FloatFilterFunction(FloatValue baseExpr, BooleanValue filterExpr) throws SolrException {
 this.baseExpr = baseExpr;
 this.filterExpr = filterExpr;
 this.exprStr = AnalyticsValueStream.createExpressionString(name,baseExpr,filterExpr);
 this.funcType = AnalyticsValueStream.determineMappingPhase(exprStr,baseExpr,filterExpr);
  }
 
 boolean exists = false;


 @Override
 public float getFloat() {
 float value = baseExpr.getFloat();
 exists = baseExpr.exists() && filterExpr.getBoolean() && filterExpr.exists();
 return value;
  }
 @Override
 public boolean exists() {
 return exists;
  }
 
 @Override
 public String getName() {
 return name;
  }
 @Override
 public String getExpressionStr() {
 return exprStr;
  }
 @Override
 public ExpressionType getExpressionType() {
 return funcType;
  }
}