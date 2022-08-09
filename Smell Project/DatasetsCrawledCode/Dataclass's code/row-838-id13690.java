public class ThymeleafAnnotationValues extends AbstractAnnotationValues {


 @AutoPopulate
 private String[] excludeMethods;


 @AutoPopulate
 private String[] excludeViews;


 /**
   * Constructor
   *
   * @param governorPhysicalTypeMetadata
   */
 public ThymeleafAnnotationValues(final PhysicalTypeMetadata governorPhysicalTypeMetadata) {
 super(governorPhysicalTypeMetadata, ROO_THYMELEAF);
 AutoPopulationUtils.populate(this, annotationMetadata);
  }




 public String[] getExcludeMethods() {
 return excludeMethods;
  }


 public String[] getExcludeViews() {
 return excludeViews;
  }


}