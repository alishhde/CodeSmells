class WSDLReaderImpl extends com.ibm.wsdl.xml.WSDLReaderImpl {


 private WSDLFactory _localFactory;


 WSDLReaderImpl(WSDLFactory factory) {
 _localFactory = factory;
    }


 @Override
 protected WSDLFactory getWSDLFactory() throws WSDLException {
 return _localFactory;
    }


 @Override
 public Binding parseBinding(Element bindingEl, Definition def) throws WSDLException {
 Binding binding = super.parseBinding(bindingEl, def);
 binding.setDocumentationElement(null);
 return binding;
    }


 @Override
 public BindingFault parseBindingFault(Element bindingFaultEl, Definition def) throws WSDLException {
 BindingFault bindingFault = super.parseBindingFault(bindingFaultEl, def);
 bindingFault.setDocumentationElement(null);
 return bindingFault;
    }


 @Override
 public BindingInput parseBindingInput(Element bindingInputEl, Definition def) throws WSDLException {
 BindingInput bindingInput = super.parseBindingInput(bindingInputEl, def);
 bindingInput.setDocumentationElement(null);
 return bindingInput;
    }


 @Override
 public BindingOperation parseBindingOperation(Element bindingOperationEl, PortType portType, Definition def) throws WSDLException {
 BindingOperation bindingOperation = super.parseBindingOperation(bindingOperationEl, portType, def);
 bindingOperation.setDocumentationElement(null);
 return bindingOperation;
    }


 @Override
 public BindingOutput parseBindingOutput(Element bindingOutputEl, Definition def) throws WSDLException {
 BindingOutput BindingOutput = super.parseBindingOutput(bindingOutputEl, def);
 BindingOutput.setDocumentationElement(null);
 return BindingOutput;
    }


 @SuppressWarnings("rawtypes")
 @Override
 public Definition parseDefinitions(String documentBaseURI, Element defEl, Map importedDefs) throws WSDLException {
 Definition definition = super.parseDefinitions(documentBaseURI, defEl, importedDefs);
 definition.setDocumentationElement(null);
 return definition;
    }


 @Override
 public Fault parseFault(Element faultEl, Definition def) throws WSDLException {
 Fault fault = super.parseFault(faultEl, def);
 fault.setDocumentationElement(null);
 return fault;
    }


 @Override
 public Input parseInput(Element inputEl, Definition def) throws WSDLException {
 Input input = super.parseInput(inputEl, def);
 input.setDocumentationElement(null);
 return input;
    }


 @Override
 public Message parseMessage(Element msgEl, Definition def) throws WSDLException {
 Message message = super.parseMessage(msgEl, def);
 message.setDocumentationElement(null);
 return message;
    }


 @Override
 public Operation parseOperation(Element opEl, PortType portType, Definition def) throws WSDLException {
 Operation operation = super.parseOperation(opEl, portType, def);
 operation.setDocumentationElement(null);
 return operation;
    }


 @Override
 public Output parseOutput(Element outputEl, Definition def) throws WSDLException {
 Output output = super.parseOutput(outputEl, def);
 output.setDocumentationElement(null);
 return output;
    }


 @Override
 public Part parsePart(Element partEl, Definition def) throws WSDLException {
 Part part = super.parsePart(partEl, def);
 part.setDocumentationElement(null);
 return part;
    }


 @Override
 public Port parsePort(Element portEl, Definition def) throws WSDLException {
 Port Port = super.parsePort(portEl, def);
 Port.setDocumentationElement(null);
 return Port;
    }


 @Override
 public PortType parsePortType(Element portTypeEl, Definition def) throws WSDLException {
 PortType portType = super.parsePortType(portTypeEl, def);
 portType.setDocumentationElement(null);
 return portType;
    }


 @Override
 public Service parseService(Element serviceEl, Definition def) throws WSDLException {
 Service service = super.parseService(serviceEl, def);
 service.setDocumentationElement(null);
 return service;
    }


 @Override
 public Types parseTypes(Element typesEl, Definition def) throws WSDLException {
 Types types = super.parseTypes(typesEl, def);
 types.setDocumentationElement(null);
 return types;
    }
}