 public static class FieldSchemaWrapper {
 @JsonIgnore
 private FieldSchema fieldSchema;
 @JsonProperty
 public String name;
 @JsonProperty
 public String type;
 @JsonProperty
 public String comment;


 @JsonCreator
 public FieldSchemaWrapper(@JsonProperty("name") String name, @JsonProperty("type") String type, @JsonProperty("comment") String comment) {
 this.name = name;
 this.type = type;
 this.comment = comment;
 this.fieldSchema = new FieldSchema(name, type, comment);
    }


 public FieldSchemaWrapper(FieldSchema fieldSchema) {
 this.fieldSchema = fieldSchema;
 this.name = fieldSchema.getName();
 this.type = fieldSchema.getType();
 this.comment = fieldSchema.getComment();
    }


 @JsonIgnore
 public FieldSchema getFieldSchema() {
 return fieldSchema;
    }
  }