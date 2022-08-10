 @Override
 public void startElement(final String uri, final String localName, final String qname, final Attributes attributes) throws SAXException
        {
 // Verify and initialize the context stack at root element.
 if (contextStack.size() == 0)
            {
 if (!qname.equals(rootElement))
                {
 throw new SAXConfigurationException(
 new ConfigurationException.IncorrectElement(rootElement, qname, this.source, locator.getLineNumber()),
 locator);
                }                
 String all = attributes.getValue("includeAllClasses");
 if ("true".equals(all))
 allClasses = true;
 contextStack.push(qname);
 return;
            }
 else
            {
 if (qname.equals("classEntry"))
                {
 String path = attributes.getValue("path");
 includedClasses.add(path);
                }
 else if (qname.equals("namespaceManifestEntry"))
                {
 String manifest = attributes.getValue("manifest");
 String namespace = attributes.getValue("namespace");
 fbArgs.add("-namespace");
 fbArgs.add(namespace);
 String mf = contextPath + "/" + manifest;
 File f = new File(mf);
 if (!f.exists())
                    {
 mf = contextPath + "/src/" + manifest;
                    }
 fbArgs.add(mf);
 fbArgs.add("-include-namespaces");
 fbArgs.add(namespace);                    
                }
            }
        }