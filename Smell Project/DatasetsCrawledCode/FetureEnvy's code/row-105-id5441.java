 private ByteBuffer toByteBuffer(Serializable serializable) {
 try {
 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
 new ObjectOutputStream(outputStream).writeObject(serializable);
 return ByteBuffer.wrap(outputStream.toByteArray());
        } catch (IOException e) {
 throw new RuntimeException(e);
        }
    }