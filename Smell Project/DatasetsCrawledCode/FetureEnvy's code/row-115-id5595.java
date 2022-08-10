 private void asJson(OutputStream out, Map<String, Object> data)
 throws HiveException
      {
 try {
 new ObjectMapper().writeValue(out, data);
    } catch (IOException e) {
 throw new HiveException("Unable to convert to json", e);
    }
      }