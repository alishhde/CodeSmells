 @Override
 public final Element getDocumentElement()
  {
 int dochandle=dtm.getDocument();
 int elementhandle=DTM.NULL;
 for(int kidhandle=dtm.getFirstChild(dochandle);
 kidhandle!=DTM.NULL;
 kidhandle=dtm.getNextSibling(kidhandle))
                {
 switch(dtm.getNodeType(kidhandle))
                        {
 case Node.ELEMENT_NODE:
 if(elementhandle!=DTM.NULL)
                                {
 elementhandle=DTM.NULL; // More than one; ill-formed.
 kidhandle=dtm.getLastChild(dochandle); // End loop
                                }
 else
 elementhandle=kidhandle;
 break;


 // These are harmless; document is still wellformed
 case Node.COMMENT_NODE:
 case Node.PROCESSING_INSTRUCTION_NODE:
 case Node.DOCUMENT_TYPE_NODE:
 break;


 default:
 elementhandle=DTM.NULL; // ill-formed
 kidhandle=dtm.getLastChild(dochandle); // End loop
 break;
                        }
                }
 if(elementhandle==DTM.NULL)
 throw new DTMDOMException(DOMException.NOT_SUPPORTED_ERR);
 else
 return (Element)(dtm.getNode(elementhandle));
  }