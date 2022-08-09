 @AutoValue.Builder
 public abstract static class Builder {
 public abstract Builder setCompileTimeConstant(boolean compileTimeConstant);


 public abstract Builder setStatic(boolean isStatic);


 public abstract Builder setFinal(boolean isFinal);


 public abstract Builder setVariableCapture(boolean isVariableCapture);


 public abstract Builder setEnclosingInstanceCapture(boolean isEnclosingInstanceCapture);


 public abstract Builder setEnclosingTypeDescriptor(
 DeclaredTypeDescriptor enclosingTypeDescriptor);


 public abstract Builder setName(String name);


 public abstract Builder setEnumConstant(boolean isEnumConstant);


 public abstract Builder setSynthetic(boolean isSynthetic);


 public abstract Builder setTypeDescriptor(TypeDescriptor typeDescriptor);


 public abstract Builder setVisibility(Visibility visibility);


 public abstract Builder setJsInfo(JsInfo jsInfo);


 public abstract Builder setUnusableByJsSuppressed(boolean isUnusableByJsSuppressed);


 public abstract Builder setDeprecated(boolean isDeprecated);


 public abstract Builder setOrigin(FieldOrigin fieldOrigin);


 public Builder setDeclarationFieldDescriptor(FieldDescriptor declarationFieldDescriptor) {
 return setDeclarationFieldDescriptorOrNullIfSelf(declarationFieldDescriptor);
    }


 // Accessors to support validation, default construction and custom setters.
 abstract Builder setDeclarationFieldDescriptorOrNullIfSelf(
 FieldDescriptor declarationFieldDescriptor);


 abstract Optional<String> getName();


 abstract FieldDescriptor autoBuild();


 public FieldDescriptor build() {
 checkState(getName().isPresent());
 FieldDescriptor fieldDescriptor = autoBuild();


 checkState(
          !fieldDescriptor.isVariableCapture() || !fieldDescriptor.isEnclosingInstanceCapture());


 return interner.intern(fieldDescriptor);
    }


 public static Builder from(FieldDescriptor fieldDescriptor) {
 return fieldDescriptor.toBuilder();
    }


 private static final ThreadLocalInterner<FieldDescriptor> interner =
 new ThreadLocalInterner<>();
  }