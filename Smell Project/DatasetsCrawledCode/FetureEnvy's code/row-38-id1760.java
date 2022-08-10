 public Referenced_Template(final CompilationTimeStamp timestamp, final SpecificValue_Template original) {
 copyGeneralProperties(original);
 final IValue value = original.getSpecificValue();
 switch (value.getValuetype()) {
 case REFERENCED_VALUE:
 reference = ((Referenced_Value) value).getReference();
 break;
 case UNDEFINED_LOWERIDENTIFIER_VALUE:
 final Identifier identifier = ((Undefined_LowerIdentifier_Value) value).getIdentifier();
 final FieldSubReference subReference = new FieldSubReference(identifier);
 subReference.setLocation(value.getLocation());
 reference = new Reference(null);
 reference.addSubReference(subReference);
 reference.setLocation(value.getLocation());
 reference.setFullNameParent(this);
 reference.setMyScope(value.getMyScope());
 break;
 default:
 reference = null;
 break;
		}
	}