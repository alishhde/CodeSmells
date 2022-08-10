 protected void writeVersion(final JsonWriter out, final Integer value) throws IOException {
 if ((value == null)) {
 final boolean previousSerializeNulls = out.getSerializeNulls();
 out.setSerializeNulls(true);
 out.nullValue();
 out.setSerializeNulls(previousSerializeNulls);
    } else {
 out.value(value);
    }
  }