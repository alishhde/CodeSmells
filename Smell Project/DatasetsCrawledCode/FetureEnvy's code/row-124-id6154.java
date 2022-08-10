 @Override
 public void serialize(AGeometry instance, DataOutput out) throws HyracksDataException {
 try {
 OGCGeometry geometry = instance.getGeometry();
 byte[] buffer = geometry.asBinary().array();
 // For efficiency, we store the size of the geometry in bytes in the first 32 bits
 // This allows AsterixDB to skip over this attribute if needed.
 out.writeInt(buffer.length);
 out.write(buffer);
        } catch (IOException e) {
 throw HyracksDataException.create(e);
        }
    }