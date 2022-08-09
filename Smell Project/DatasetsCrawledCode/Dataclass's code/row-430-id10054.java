public class JdbVariable implements Variable {
 private final LocalVariable jdiVariable;
 private final SimpleValue value;


 public JdbVariable(StackFrame jdiStackFrame, LocalVariable jdiVariable) {
 Value jdiValue = jdiStackFrame.getValue(jdiVariable);


 this.jdiVariable = jdiVariable;
 this.value = jdiValue == null ? new JdbNullValue() : new JdbValue(jdiValue, getVariablePath());
  }


 public JdbVariable(SimpleValue value, LocalVariable jdiVariable) {
 this.jdiVariable = jdiVariable;
 this.value = value;
  }


 @Override
 public String getName() {
 return jdiVariable.name();
  }


 @Override
 public boolean isPrimitive() {
 return JdbType.isPrimitive(jdiVariable.signature());
  }


 @Override
 public SimpleValue getValue() {
 return value;
  }


 @Override
 public String getType() {
 return jdiVariable.typeName();
  }


 @Override
 public VariablePath getVariablePath() {
 return new VariablePathImpl(getName());
  }
}