 public void startElement(String uri, String localName, String qName, Attributes attributes)
 throws SAXException {
 if (delegate != null) {
 delegateStack.push(qName);
 delegate.startElement(uri, localName, qName, attributes);
            } else if (domImplementation != null) {
 //domImplementation is set so we need to start a new DOM building sub-process
 TransformerHandler handler;
 try {
 handler = tFactory.newTransformerHandler();
                } catch (TransformerConfigurationException e) {
 throw new SAXException("Error creating a new TransformerHandler", e);
                }
 Document doc = domImplementation.createDocument(uri, qName, null);
 //It's easier to work with an empty document, so remove the root element
 doc.removeChild(doc.getDocumentElement());
 handler.setResult(new DOMResult(doc));
 Area parent = (Area)areaStack.peek();
                ((ForeignObject)parent).setDocument(doc);


 //activate delegate for nested foreign document
 domImplementation = null; //Not needed anymore now
 this.delegate = handler;
 delegateStack.push(qName);
 delegate.startDocument();
 delegate.startElement(uri, localName, qName, attributes);
            } else {
 boolean handled = true;
 if ("".equals(uri)) {
 if (localName.equals("structureTree")) {


 /* The area tree parser no longer supports the structure tree. */
 delegate = new DefaultHandler();


 delegateStack.push(qName);
 delegate.startDocument();
 delegate.startElement(uri, localName, qName, attributes);
                    } else {
 handled = startAreaTreeElement(localName, attributes);
                    }
                } else {
 ContentHandlerFactoryRegistry registry
                            = userAgent.getContentHandlerFactoryRegistry();
 ContentHandlerFactory factory = registry.getFactory(uri);
 if (factory != null) {
 delegate = factory.createContentHandler();
 delegateStack.push(qName);
 delegate.startDocument();
 delegate.startElement(uri, localName, qName, attributes);
                    } else {
 handled = false;
                    }
                }
 if (!handled) {
 if (uri == null || uri.length() == 0) {
 throw new SAXException("Unhandled element " + localName
                                + " in namespace: " + uri);
                    } else {
 log.warn("Unhandled element " + localName
                                + " in namespace: " + uri);
                    }
                }
            }
        }