public class TemporalIntervalStartDatetimeAccessor extends AbstractScalarFunctionDynamicDescriptor {
 private static final long serialVersionUID = 1L;
 private static final FunctionIdentifier FID = BuiltinFunctions.ACCESSOR_TEMPORAL_INTERVAL_START_DATETIME;
 public static final IFunctionDescriptorFactory FACTORY = new IFunctionDescriptorFactory() {


 @Override
 public IFunctionDescriptor createFunctionDescriptor() {
 return new TemporalIntervalStartDatetimeAccessor();
        }
    };


 @Override
 public IScalarEvaluatorFactory createEvaluatorFactory(final IScalarEvaluatorFactory[] args) {
 return new IScalarEvaluatorFactory() {
 private static final long serialVersionUID = 1L;


 @Override
 public IScalarEvaluator createScalarEvaluator(IHyracksTaskContext ctx) throws HyracksDataException {
 return new IScalarEvaluator() {
 private final ArrayBackedValueStorage resultStorage = new ArrayBackedValueStorage();
 private final DataOutput out = resultStorage.getDataOutput();
 private final IPointable argPtr = new VoidPointable();
 private final IScalarEvaluator eval = args[0].createScalarEvaluator(ctx);


 // possible output
 @SuppressWarnings("unchecked")
 private final ISerializerDeserializer<ADateTime> datetimeSerde =
 SerializerDeserializerProvider.INSTANCE.getSerializerDeserializer(BuiltinType.ADATETIME);
 private final AMutableDateTime aDateTime = new AMutableDateTime(0);


 @Override
 public void evaluate(IFrameTupleReference tuple, IPointable result) throws HyracksDataException {
 eval.evaluate(tuple, argPtr);
 byte[] bytes = argPtr.getByteArray();
 int startOffset = argPtr.getStartOffset();


 resultStorage.reset();
 try {
 if (bytes[startOffset] == ATypeTag.SERIALIZED_INTERVAL_TYPE_TAG) {
 byte timeType =
 AIntervalSerializerDeserializer.getIntervalTimeType(bytes, startOffset + 1);
 long startTime =
 AIntervalSerializerDeserializer.getIntervalStart(bytes, startOffset + 1);
 if (timeType == ATypeTag.SERIALIZED_DATETIME_TYPE_TAG) {
 aDateTime.setValue(startTime);
 datetimeSerde.serialize(aDateTime, out);
                                } else {
 throw new InvalidDataFormatException(sourceLoc, getIdentifier(),
 ATypeTag.SERIALIZED_INTERVAL_TYPE_TAG);
                                }
                            } else {
 throw new TypeMismatchException(sourceLoc, getIdentifier(), 0, bytes[startOffset],
 ATypeTag.SERIALIZED_INTERVAL_TYPE_TAG);
                            }
                        } catch (IOException e) {
 throw HyracksDataException.create(e);
                        }
 result.set(resultStorage);
                    }
                };
            }
        };
    }


 /* (non-Javadoc)
     * @see org.apache.asterix.om.functions.AbstractFunctionDescriptor#getIdentifier()
     */
 @Override
 public FunctionIdentifier getIdentifier() {
 return FID;
    }


}