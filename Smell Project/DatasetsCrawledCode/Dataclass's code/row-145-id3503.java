 public static class NamespaceFilter extends XMLFilterImpl {
 private static final InputSource EMPTY_INPUT_SOURCE = new InputSource(new ByteArrayInputStream(new byte[0]));


 public NamespaceFilter(XMLReader xmlReader) {
 super(xmlReader);
        }


 public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
 return EMPTY_INPUT_SOURCE;
        }
    }