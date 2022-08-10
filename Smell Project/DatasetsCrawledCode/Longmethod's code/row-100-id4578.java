 @Override
 public boolean supportsParameter(MethodParameter parameter) {


 if (!super.supportsParameter(parameter)) {
 return false;
		}


 Class<?> type = parameter.getParameterType();


 if (!type.isInterface()) {
 return false;
		}


 // Annotated parameter
 if (parameter.getParameterAnnotation(ProjectedPayload.class) != null) {
 return true;
		}


 // Annotated type
 if (AnnotatedElementUtils.findMergedAnnotation(type, ProjectedPayload.class) != null) {
 return true;
		}


 // Fallback for only user defined interfaces
 String packageName = ClassUtils.getPackageName(type);


 return !IGNORED_PACKAGES.stream().anyMatch(it -> packageName.startsWith(it));
	}