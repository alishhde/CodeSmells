 @Override
 /** {@inheritDoc} */
 public IType getFieldType(final CompilationTimeStamp timestamp, final Reference reference, final int actualSubReference,
 final Expected_Value_type expectedIndex, final IReferenceChain refChain, final boolean interruptIfOptional) {
 final List<ISubReference> subreferences = reference.getSubreferences();
 if (subreferences.size() <= actualSubReference) {
 return this;
		}


 final ISubReference subreference = subreferences.get(actualSubReference);
 switch (subreference.getReferenceType()) {
 case arraySubReference:
 subreference.getLocation().reportSemanticError(MessageFormat.format(ArraySubReference.INVALIDSUBREFERENCE, getTypename()));
 return null;
 case fieldSubReference:
 subreference.getLocation().reportSemanticError(
 MessageFormat.format(FieldSubReference.INVALIDSUBREFERENCE, ((FieldSubReference) subreference).getId().getDisplayName(),
 getTypename()));
 return null;
 case parameterisedSubReference:
 subreference.getLocation().reportSemanticError(
 MessageFormat.format(FieldSubReference.INVALIDSUBREFERENCE, ((ParameterisedSubReference) subreference).getId().getDisplayName(),
 getTypename()));
 return null;
 default:
 subreference.getLocation().reportSemanticError(ISubReference.INVALIDSUBREFERENCE);
 return null;
		}
	}