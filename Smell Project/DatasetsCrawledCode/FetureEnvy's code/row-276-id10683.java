 @Override
 protected String getGatewayClassName(Element element) {
 return ((StringUtils.hasText(element.getAttribute("marshaller"))) ?
 MarshallingWebServiceOutboundGateway.class : SimpleWebServiceOutboundGateway.class).getName();
	}