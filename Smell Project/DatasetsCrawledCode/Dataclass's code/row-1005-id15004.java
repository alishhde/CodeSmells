public abstract class BinaryExpression implements Expression {
 protected Expression left;
 protected Expression right;


 public BinaryExpression(Expression left, Expression right) {
 this.left = left;
 this.right = right;
    }


 public Expression getLeft() {
 return left;
    }


 public Expression getRight() {
 return right;
    }




 /**
     * @see java.lang.Object#toString()
     */
 public String toString() {
 return "(" + left.toString() + " " + getExpressionSymbol() + " " + right.toString() + ")";
    }


 /**
     * TODO: more efficient hashCode()
     *
     * @see java.lang.Object#hashCode()
     */
 public int hashCode() {
 return toString().hashCode();
    }


 /**
     * TODO: more efficient hashCode()
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
 public boolean equals(Object o) {


 if (o == null || !this.getClass().equals(o.getClass())) {
 return false;
        }
 return toString().equals(o.toString());


    }


 /**
     * Returns the symbol that represents this binary expression.  For example, addition is
     * represented by "+"
     *
     * @return
     */
 public abstract String getExpressionSymbol();


 /**
     * @param expression
     */
 public void setRight(Expression expression) {
 right = expression;
    }


 /**
     * @param expression
     */
 public void setLeft(Expression expression) {
 left = expression;
    }
 
}