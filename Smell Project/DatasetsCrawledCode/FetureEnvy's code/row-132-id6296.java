 private boolean isValid(Document xml) throws SAXException{
 try{
 String language = "http://www.w3.org/2001/XMLSchema";
 SchemaFactory factory = SchemaFactory.newInstance(language);


 Source source = new DOMSource(map.getSchema());
 Schema schema = factory.newSchema(source);
 Validator validator = schema.newValidator();
 validator.validate(new DOMSource(xml));
 
 //if no exceptions where raised, the document is valid
 return true;
        } catch(IOException e) {
 LOG.log(POILogger.ERROR, "document is not valid", e);
        }


 return false;
    }