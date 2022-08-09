 private static class BeanRegistration {


 private final Class<?> annotatedClass;


 @Nullable
 private final Supplier<?> supplier;


 private final Class<? extends Annotation>[] qualifiers;


 public BeanRegistration(
 Class<?> annotatedClass, @Nullable Supplier<?> supplier, Class<? extends Annotation>[] qualifiers) {
 this.annotatedClass = annotatedClass;
 this.supplier = supplier;
 this.qualifiers = qualifiers;
		}


 public Class<?> getAnnotatedClass() {
 return this.annotatedClass;
		}


 @Nullable
 @SuppressWarnings("rawtypes")
 public Supplier getSupplier() {
 return this.supplier;
		}


 public Class<? extends Annotation>[] getQualifiers() {
 return this.qualifiers;
		}


 @Override
 public String toString() {
 return this.annotatedClass.getName();
		}
	}