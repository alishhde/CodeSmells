 public void write(String baseDir) throws IOException {
 String filename = baseDir + File.separator +
 CharacterDefinition.class.getName().replace('.', File.separatorChar) + CharacterDefinition.FILENAME_SUFFIX;
 new File(filename).getParentFile().mkdirs();
 OutputStream os = new FileOutputStream(filename);
 try {
 os = new BufferedOutputStream(os);
 final DataOutput out = new OutputStreamDataOutput(os);
 CodecUtil.writeHeader(out, CharacterDefinition.HEADER, CharacterDefinition.VERSION);
 out.writeBytes(characterCategoryMap, 0, characterCategoryMap.length);
 for (int i = 0; i < CharacterDefinition.CLASS_COUNT; i++) {
 final byte b = (byte) (
          (invokeMap[i] ? 0x01 : 0x00) | 
          (groupMap[i] ? 0x02 : 0x00)
        );
 out.writeByte(b);
      }
    } finally {
 os.close();
    }
  }