public interface ExpressionNode {


 String evaluateAsString(Context context);


 Object evaluateAsObject(Context context);


 long evaluateAsLong(Context context);


 double evaluateAsDouble(Context context);


 boolean evaluateAsBoolean(Context context);


}