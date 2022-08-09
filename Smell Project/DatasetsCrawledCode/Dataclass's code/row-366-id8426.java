public class AnnotationSubstitutionType extends CustomSubstitutionType<AnnotationSubstitutionField, AnnotationSubstitutionMethod> {


 private final String name;


 public AnnotationSubstitutionType(MetaAccessProvider metaAccess, ResolvedJavaType original) {
 super(original);


 assert original.getSuperclass().equals(metaAccess.lookupJavaType(Proxy.class));
 assert metaAccess.lookupJavaType(Annotation.class).isAssignableFrom(original);


 ResolvedJavaType annotationInterfaceType = AnnotationSupport.findAnnotationInterfaceType(original);
 assert annotationInterfaceType.isAssignableFrom(original);
 assert metaAccess.lookupJavaType(Annotation.class).isAssignableFrom(annotationInterfaceType);


 String n = annotationInterfaceType.getName();
 assert n.endsWith(";");
 name = n.substring(0, n.length() - 1) + "$$ProxyImpl;";
    }


 @Override
 public String getName() {
 return name;
    }


 @Override
 public String toString() {
 return "AnnotationType<" + toJavaName(true) + " -> " + original + ">";
    }
}