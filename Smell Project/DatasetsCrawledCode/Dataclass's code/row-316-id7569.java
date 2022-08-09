public class RowReadSupport extends ReadSupport<Row> {


 private TypeInformation<?> returnTypeInfo;


 @Override
 public ReadContext init(InitContext initContext) {
 checkNotNull(initContext, "initContext");
 returnTypeInfo = ParquetSchemaConverter.fromParquetType(initContext.getFileSchema());
 return new ReadContext(initContext.getFileSchema());
	}


 @Override
 public RecordMaterializer<Row> prepareForRead(
 Configuration configuration, Map<String, String> keyValueMetaData,
 MessageType fileSchema, ReadContext readContext) {
 return new RowMaterializer(readContext.getRequestedSchema(), returnTypeInfo);
	}
}