 protected JvmField createField(Field field) {
 JvmField result;
 int modifiers = field.getModifiers();
 if (!field.isEnumConstant()) {
 result = TypesFactory.eINSTANCE.createJvmField();
		} else
 result = TypesFactory.eINSTANCE.createJvmEnumerationLiteral();
 String fieldName = field.getName();
 result.internalSetIdentifier(field.getDeclaringClass().getName() + "." + fieldName);
 result.setSimpleName(fieldName);
 result.setFinal(Modifier.isFinal(modifiers));
 result.setStatic(Modifier.isStatic(modifiers));
 result.setTransient(Modifier.isTransient(modifiers));
 result.setVolatile(Modifier.isVolatile(modifiers));
 setVisibility(result, modifiers);
 Type fieldType = null;
 try {
 fieldType = field.getGenericType();
		} catch (GenericSignatureFormatError error) {
 logSignatureFormatError(field.getDeclaringClass());
 fieldType = field.getType();
		} catch (MalformedParameterizedTypeException error) {
 logSignatureFormatError(field.getDeclaringClass());
 fieldType = field.getType();
		}
 result.setType(createTypeReference(fieldType));
 createAnnotationValues(field, result);
 return result;
	}