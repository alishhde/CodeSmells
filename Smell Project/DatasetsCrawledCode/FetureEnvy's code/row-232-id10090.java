 protected void transform(XtendConstructor source, JvmGenericType container) {
 JvmConstructor constructor = typesFactory.createJvmConstructor();
 container.getMembers().add(constructor);
 associator.associatePrimary(source, constructor);
 JvmVisibility visibility = source.getVisibility();
 constructor.setSimpleName(container.getSimpleName());
 constructor.setVisibility(visibility);
 for (XtendParameter parameter : source.getParameters()) {
 translateParameter(constructor, parameter);
		}
 copyAndFixTypeParameters(source.getTypeParameters(), constructor);
 for (JvmTypeReference exception : source.getExceptions()) {
 constructor.getExceptions().add(jvmTypesBuilder.cloneWithProxies(exception));
		}
 translateAnnotationsTo(source.getAnnotations(), constructor);
 setBody(constructor, source.getExpression());
 jvmTypesBuilder.copyDocumentationTo(source, constructor);
	}