 private NameRegion[] findLinkComponentsInClosure(ClosureExpression firstArg,
 int offset) {
 if (! (firstArg.getCode() instanceof BlockStatement)) {
 return null;
        }
 
 BlockStatement code = (BlockStatement) firstArg.getCode();
 if (code.getStatements() == null) {
 return null;
        }
 NameRegion controllerName = null;
 NameRegion actionName = null;
 NameRegion viewName = null;


 for (Statement state : code.getStatements()) {
 if (state instanceof ExpressionStatement) {
 if (((ExpressionStatement) state).getExpression() instanceof BinaryExpression) {
 BinaryExpression bexpr = (BinaryExpression) ((ExpressionStatement) state).getExpression();
 Expression left = bexpr.getLeftExpression();
 if (bexpr.getOperation().getText().equals("=") && left instanceof VariableExpression) {
 Expression right = bexpr.getRightExpression();
 Region region;
 if (right.getStart() <= offset && right.getEnd() >= offset) {
 region = new Region(right.getStart(), right.getLength());
                        } else {
 region = null;
                        }


 String name = left.getText();
 if (name.equals("controller")) {
 controllerName = new NameRegion(right.getText(), region);
                        } else if (name.equals("action")) {
 actionName = new NameRegion(right.getText(), region);
                        } else if (name.equals("view")) {
 viewName = new NameRegion(right.getText(), region);
                        }
                    }
                }
            }
        }
 return new NameRegion[] { controllerName, actionName, viewName };
    }