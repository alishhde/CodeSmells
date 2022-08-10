 final protected Node internalRemoveNamedItemNS(String namespaceURI,
 String name,
 boolean raiseEx) {


 CoreDocumentImpl ownerDocument = ownerNode.ownerDocument();
 if (ownerDocument.errorChecking && isReadOnly()) {
 String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NO_MODIFICATION_ALLOWED_ERR", null);
 throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, msg);
        }
 int i = findNamePoint(namespaceURI, name);
 if (i < 0) {
 if (raiseEx) {
 String msg = DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_FOUND_ERR", null);
 throw new DOMException(DOMException.NOT_FOUND_ERR, msg);
            } else {
 return null;
            }
        }


 AttrImpl n = (AttrImpl)nodes.get(i);


 if (n.isIdAttribute()) {
 ownerDocument.removeIdentifier(n.getValue());
        }
 // If there's a default, add it instead
 String nodeName = n.getNodeName();
 if (hasDefaults()) {
 NamedNodeMapImpl defaults = ((ElementImpl) ownerNode).getDefaultAttributes();
 Node d;
 if (defaults != null
                    && (d = defaults.getNamedItem(nodeName)) != null)
            {
 int j = findNamePoint(nodeName,0);
 if (j>=0 && findNamePoint(nodeName, j+1) < 0) {
 NodeImpl clone = (NodeImpl)d.cloneNode(true);
 clone.ownerNode = ownerNode;
 if (d.getLocalName() != null) {
 // we must rely on the name to find a default attribute
 // ("test:attr"), but while copying it from the DOCTYPE
 // we should not loose namespace URI that was assigned
 // to the attribute in the instance document.
                        ((AttrNSImpl)clone).namespaceURI = namespaceURI;
                    }
 clone.isOwned(true);
 clone.isSpecified(false);
 nodes.set(i, clone);
 if (clone.isIdAttribute()) {
 ownerDocument.putIdentifier(clone.getNodeValue(),
                                (ElementImpl)ownerNode);
                    }
                } else {
 nodes.remove(i);
                }
            } else {
 nodes.remove(i);
            }
        } else {
 nodes.remove(i);
        }


 //        changed(true);


 // remove reference to owner
 n.ownerNode = ownerDocument;
 n.isOwned(false);
 // make sure it won't be mistaken with defaults in case it's
 // reused
 n.isSpecified(true);
 // update id table if needed
 n.isIdAttribute(false);


 // notify document
 ownerDocument.removedAttrNode(n, ownerNode, name);


 return n;


    } // internalRemoveNamedItemNS(String,String,boolean):Node