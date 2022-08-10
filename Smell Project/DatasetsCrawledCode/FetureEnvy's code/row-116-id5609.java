 private static Optional<Schema> removeUncomparableFieldsFromRecord(Schema record, Set<Schema> processed) {
 Preconditions.checkArgument(record.getType() == Schema.Type.RECORD);


 if (processed.contains(record)) {
 return Optional.absent();
    }
 processed.add(record);


 List<Field> fields = Lists.newArrayList();
 for (Field field : record.getFields()) {
 Optional<Schema> newFieldSchema = removeUncomparableFields(field.schema(), processed);
 if (newFieldSchema.isPresent()) {
 fields.add(new Field(field.name(), newFieldSchema.get(), field.doc(), field.defaultValue()));
      }
    }


 Schema newSchema = Schema.createRecord(record.getName(), record.getDoc(), record.getNamespace(), false);
 newSchema.setFields(fields);
 return Optional.of(newSchema);
  }