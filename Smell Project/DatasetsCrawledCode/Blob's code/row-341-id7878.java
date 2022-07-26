public final class IntermediateModel {
 private final Metadata metadata;


 private final Map<String, OperationModel> operations;


 private final Map<String, ShapeModel> shapes;


 private final CustomizationConfig customizationConfig;


 private final ServiceExamples examples;


 private final Map<String, AuthorizerModel> customAuthorizers;


 @JsonIgnore
 private final Optional<OperationModel> endpointOperation;


 @JsonIgnore
 private final Map<String, PaginatorDefinition> paginators;


 @JsonIgnore
 private final NamingStrategy namingStrategy;


 @JsonCreator
 public IntermediateModel(
 @JsonProperty("metadata") Metadata metadata,
 @JsonProperty("operations") Map<String, OperationModel> operations,
 @JsonProperty("shapes") Map<String, ShapeModel> shapes,
 @JsonProperty("customizationConfig") CustomizationConfig customizationConfig,
 @JsonProperty("serviceExamples") ServiceExamples examples) {


 this(metadata, operations, shapes, customizationConfig, examples, null,
 Collections.emptyMap(), Collections.emptyMap(), null);
    }


 public IntermediateModel(
 Metadata metadata,
 Map<String, OperationModel> operations,
 Map<String, ShapeModel> shapes,
 CustomizationConfig customizationConfig,
 ServiceExamples examples,
 OperationModel endpointOperation,
 Map<String, AuthorizerModel> customAuthorizers,
 Map<String, PaginatorDefinition> paginators,
 NamingStrategy namingStrategy) {
 this.metadata = metadata;
 this.operations = operations;
 this.shapes = shapes;
 this.customizationConfig = customizationConfig;
 this.examples = examples;
 this.endpointOperation = Optional.ofNullable(endpointOperation);
 this.customAuthorizers = customAuthorizers;
 this.paginators = paginators;
 this.namingStrategy = namingStrategy;
    }


 public Metadata getMetadata() {
 return metadata;
    }


 public Map<String, OperationModel> getOperations() {
 return operations;
    }


 public OperationModel getOperation(String operationName) {
 return getOperations().get(operationName);
    }


 public Map<String, ShapeModel> getShapes() {
 return shapes;
    }


 public ShapeModel getShapeByC2jName(String c2jName) {
 return Utils.findShapeModelByC2jName(this, c2jName);
    }


 public CustomizationConfig getCustomizationConfig() {
 return customizationConfig;
    }


 public ServiceExamples getExamples() {
 return examples;
    }


 public Map<String, PaginatorDefinition> getPaginators() {
 return paginators;
    }


 public NamingStrategy getNamingStrategy() {
 return namingStrategy;
    }


 public String getCustomRetryPolicy() {
 return customizationConfig.getCustomRetryPolicy();
    }


 public String getSdkModeledExceptionBaseFqcn() {
 return String.format("%s.%s",
 metadata.getFullModelPackageName(),
 getSdkModeledExceptionBaseClassName());
    }


 public String getSdkModeledExceptionBaseClassName() {
 if (customizationConfig.getSdkModeledExceptionBaseClassName() != null) {
 return customizationConfig.getSdkModeledExceptionBaseClassName();
        } else {
 return metadata.getBaseExceptionName();
        }
    }


 public String getSdkRequestBaseClassName() {
 if (customizationConfig.getSdkRequestBaseClassName() != null) {
 return customizationConfig.getSdkRequestBaseClassName();
        } else {
 return metadata.getBaseRequestName();
        }
    }


 public String getSdkResponseBaseClassName() {
 if (customizationConfig.getSdkResponseBaseClassName() != null) {
 return customizationConfig.getSdkResponseBaseClassName();
        } else {
 return metadata.getBaseResponseName();
        }
    }


 public String getFileHeader() throws IOException {
 return loadDefaultFileHeader();
    }


 private String loadDefaultFileHeader() throws IOException {
 try (InputStream inputStream = getClass()
            .getResourceAsStream("/software/amazon/awssdk/codegen/DefaultFileHeader.txt")) {
 return IoUtils.toUtf8String(inputStream)
                          .replaceFirst("%COPYRIGHT_DATE_RANGE%", getCopyrightDateRange());
        }
    }


 private String getCopyrightDateRange() {
 int currentYear = ZonedDateTime.now().getYear();
 int copyrightStartYear = currentYear - 5;
 return String.format("%d-%d", copyrightStartYear, currentYear);
    }


 public String getSdkBaseResponseFqcn() {
 if (metadata.getProtocol() == Protocol.API_GATEWAY) {
 return "software.amazon.awssdk.opensdk.BaseResult";
        } else {
 return String.format("%s<%s>",
 AwsResponse.class.getName(),
 getResponseMetadataClassName());
        }
    }


 private String getResponseMetadataClassName() {
 return AwsResponseMetadata.class.getName();
    }


 @JsonIgnore
 public List<OperationModel> simpleMethodsRequiringTesting() {
 return getOperations().values().stream()
                              .filter(v -> v.getInputShape().isSimpleMethod())
                              .collect(Collectors.toList());
    }


 public Map<String, AuthorizerModel> getCustomAuthorizers() {
 return customAuthorizers;
    }


 public Optional<OperationModel> getEndpointOperation() {
 return endpointOperation;
    }


 public boolean hasPaginators() {
 return paginators.size() > 0;
    }


 public boolean containsRequestSigners() {
 return getShapes().values().stream()
                          .filter(ShapeModel::isRequestSignerAware)
                          .findAny()
                          .isPresent();
    }


 public boolean containsRequestEventStreams() {
 return getOperations().values().stream()
                              .filter(opModel -> opModel.hasEventStreamInput())
                              .findAny()
                              .isPresent();
    }
}