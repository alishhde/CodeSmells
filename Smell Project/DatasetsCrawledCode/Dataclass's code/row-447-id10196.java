 @Immutable
 private static final class NumberLiteralNode extends PrimitiveLiteralNode<Number> {
 private static final long serialVersionUID = 1L;


 private final Type type = numberGetType(value);


 private NumberLiteralNode(final long token, final int finish, final Number value) {
 super(Token.recast(token, TokenType.DECIMAL), finish, value);
        }


 private NumberLiteralNode(final NumberLiteralNode literalNode) {
 super(literalNode);
        }


 private static Type numberGetType(final Number number) {
 if (number instanceof Integer) {
 return Type.INT;
            } else if (number instanceof Double) {
 return Type.NUMBER;
            } else {
 assert false;
            }


 return null;
        }


 @Override
 public Type getType() {
 return type;
        }


 @Override
 public Type getWidestOperationType() {
 return getType();
        }


    }