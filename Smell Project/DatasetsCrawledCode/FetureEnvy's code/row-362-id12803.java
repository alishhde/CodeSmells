 @Override
 public void sequence(ISerializationContext context, EObject semanticObject) {
 EPackage epackage = semanticObject.eClass().getEPackage();
 ParserRule rule = context.getParserRule();
 Action action = context.getAssignedAction();
 Set<Parameter> parameters = context.getEnabledBooleanParameters();
 if (epackage == Bug250313Package.eINSTANCE)
 switch (semanticObject.eClass().getClassifierID()) {
 case Bug250313Package.CHILD1:
 sequence_Child1(context, (Child1) semanticObject); 
 return; 
 case Bug250313Package.CHILD2:
 sequence_Child2(context, (Child2) semanticObject); 
 return; 
 case Bug250313Package.MODEL:
 sequence_Model(context, (Model) semanticObject); 
 return; 
			}
 if (errorAcceptor != null)
 errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}