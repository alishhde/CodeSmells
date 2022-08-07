 private static class DoubleTreeWriter extends TreeWriter {
 private final PositionedOutputStream stream;
 private final SerializationUtils utils;


 DoubleTreeWriter(int columnId,
 TypeDescription schema,
 StreamFactory writer,
 boolean nullable) throws IOException {
 super(columnId, schema, writer, nullable);
 this.stream = writer.createStream(id,
 OrcProto.Stream.Kind.DATA);
 this.utils = new SerializationUtils();
 recordPosition(rowIndexPosition);
    }


 @Override
 void write(Datum datum) throws IOException {
 super.write(datum);
 if (datum != null && datum.isNotNull()) {
 double val = datum.asFloat8();
 indexStatistics.updateDouble(val);
 if (createBloomFilter) {
 bloomFilter.addDouble(val);
        }
 utils.writeDouble(stream, val);
      }
    }


 @Override
 void writeStripe(OrcProto.StripeFooter.Builder builder,
 int requiredIndexEntries) throws IOException {
 super.writeStripe(builder, requiredIndexEntries);
 stream.flush();
 recordPosition(rowIndexPosition);
    }


 @Override
 void recordPosition(PositionRecorder recorder) throws IOException {
 super.recordPosition(recorder);
 stream.getPosition(recorder);
    }
  }