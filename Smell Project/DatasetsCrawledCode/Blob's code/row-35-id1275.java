public class _ConfigurationSettingsServiceSoapService
 extends SOAP11Service
 implements _ConfigurationSettingsServiceSoap
{
 private static final QName PORT_QNAME = new QName(
 "http://schemas.microsoft.com/TeamFoundation/2005/06/WorkItemTracking/configurationSettingsService/03",
 "ConfigurationSettingsServiceSoapService");
 private static final String ENDPOINT_PATH = "/tfs/DefaultCollection/WorkItemTracking/v1.0/ConfigurationSettingsService.asmx";


 public _ConfigurationSettingsServiceSoapService(
 final URI endpoint,
 final QName port)
    {
 super(endpoint, port);
    }


 public _ConfigurationSettingsServiceSoapService(
 final HttpClient client,
 URI endpoint,
 QName port)
    {
 super(client, endpoint, port);
    }


 /**
     * @return the qualified name of the WSDL port this service implementation can be used with
     */
 public static QName getPortQName()
    {
 return _ConfigurationSettingsServiceSoapService.PORT_QNAME;
    }


 /**
     * @return the path part to use when constructing a URI to contact a host that provides this service
     */
 public static String getEndpointPath()
    {
 return _ConfigurationSettingsServiceSoapService.ENDPOINT_PATH;
    }


 public String getWorkitemTrackingVersion()
 throws TransportException, SOAPFault
    {
 final _ConfigurationSettingsServiceSoap_GetWorkitemTrackingVersion requestData = new _ConfigurationSettingsServiceSoap_GetWorkitemTrackingVersion();


 final SOAPRequest request = createSOAPRequest(
 "GetWorkitemTrackingVersion",
 new SOAPMethodRequestWriter()
                {
 public void writeSOAPRequest(
 final XMLStreamWriter writer,
 final OutputStream out)
 throws XMLStreamException, IOException
                    {
 requestData.writeAsElement(
 writer,
 "GetWorkitemTrackingVersion");
                    }
                });


 final _ConfigurationSettingsServiceSoap_GetWorkitemTrackingVersionResponse responseData = new _ConfigurationSettingsServiceSoap_GetWorkitemTrackingVersionResponse();


 executeSOAPRequest(
 request,
 "GetWorkitemTrackingVersionResponse",
 new SOAPMethodResponseReader()
            {
 public void readSOAPResponse(
 final XMLStreamReader reader,
 final InputStream in)
 throws XMLStreamException, IOException
                {
 responseData.readFromElement(reader);
                }
            });


 return responseData.getGetWorkitemTrackingVersionResult();
    }


 public long getMaxAttachmentSize()
 throws TransportException, SOAPFault
    {
 final _ConfigurationSettingsServiceSoap_GetMaxAttachmentSize requestData = new _ConfigurationSettingsServiceSoap_GetMaxAttachmentSize();


 final SOAPRequest request = createSOAPRequest(
 "GetMaxAttachmentSize",
 new SOAPMethodRequestWriter()
                {
 public void writeSOAPRequest(
 final XMLStreamWriter writer,
 final OutputStream out)
 throws XMLStreamException, IOException
                    {
 requestData.writeAsElement(
 writer,
 "GetMaxAttachmentSize");
                    }
                });


 final _ConfigurationSettingsServiceSoap_GetMaxAttachmentSizeResponse responseData = new _ConfigurationSettingsServiceSoap_GetMaxAttachmentSizeResponse();


 executeSOAPRequest(
 request,
 "GetMaxAttachmentSizeResponse",
 new SOAPMethodResponseReader()
            {
 public void readSOAPResponse(
 final XMLStreamReader reader,
 final InputStream in)
 throws XMLStreamException, IOException
                {
 responseData.readFromElement(reader);
                }
            });


 return responseData.getGetMaxAttachmentSizeResult();
    }


 public void setMaxAttachmentSize(final long maxSize)
 throws TransportException, SOAPFault
    {
 final _ConfigurationSettingsServiceSoap_SetMaxAttachmentSize requestData = new _ConfigurationSettingsServiceSoap_SetMaxAttachmentSize(
 maxSize);


 final SOAPRequest request = createSOAPRequest(
 "SetMaxAttachmentSize",
 new SOAPMethodRequestWriter()
                {
 public void writeSOAPRequest(
 final XMLStreamWriter writer,
 final OutputStream out)
 throws XMLStreamException, IOException
                    {
 requestData.writeAsElement(
 writer,
 "SetMaxAttachmentSize");
                    }
                });


 final _ConfigurationSettingsServiceSoap_SetMaxAttachmentSizeResponse responseData = new _ConfigurationSettingsServiceSoap_SetMaxAttachmentSizeResponse();


 executeSOAPRequest(
 request,
 "SetMaxAttachmentSizeResponse",
 new SOAPMethodResponseReader()
            {
 public void readSOAPResponse(
 final XMLStreamReader reader,
 final InputStream in)
 throws XMLStreamException, IOException
                {
 responseData.readFromElement(reader);
                }
            });
    }


 public boolean getInProcBuildCompletionNotificationAvailability()
 throws TransportException, SOAPFault
    {
 final _ConfigurationSettingsServiceSoap_GetInProcBuildCompletionNotificationAvailability requestData = new _ConfigurationSettingsServiceSoap_GetInProcBuildCompletionNotificationAvailability();


 final SOAPRequest request = createSOAPRequest(
 "GetInProcBuildCompletionNotificationAvailability",
 new SOAPMethodRequestWriter()
                {
 public void writeSOAPRequest(
 final XMLStreamWriter writer,
 final OutputStream out)
 throws XMLStreamException, IOException
                    {
 requestData.writeAsElement(
 writer,
 "GetInProcBuildCompletionNotificationAvailability");
                    }
                });


 final _ConfigurationSettingsServiceSoap_GetInProcBuildCompletionNotificationAvailabilityResponse responseData = new _ConfigurationSettingsServiceSoap_GetInProcBuildCompletionNotificationAvailabilityResponse();


 executeSOAPRequest(
 request,
 "GetInProcBuildCompletionNotificationAvailabilityResponse",
 new SOAPMethodResponseReader()
            {
 public void readSOAPResponse(
 final XMLStreamReader reader,
 final InputStream in)
 throws XMLStreamException, IOException
                {
 responseData.readFromElement(reader);
                }
            });


 return responseData.isGetInProcBuildCompletionNotificationAvailabilityResult();
    }


 public void setInProcBuildCompletionNotificationAvailability(final boolean isEnabled)
 throws TransportException, SOAPFault
    {
 final _ConfigurationSettingsServiceSoap_SetInProcBuildCompletionNotificationAvailability requestData = new _ConfigurationSettingsServiceSoap_SetInProcBuildCompletionNotificationAvailability(
 isEnabled);


 final SOAPRequest request = createSOAPRequest(
 "SetInProcBuildCompletionNotificationAvailability",
 new SOAPMethodRequestWriter()
                {
 public void writeSOAPRequest(
 final XMLStreamWriter writer,
 final OutputStream out)
 throws XMLStreamException, IOException
                    {
 requestData.writeAsElement(
 writer,
 "SetInProcBuildCompletionNotificationAvailability");
                    }
                });


 final _ConfigurationSettingsServiceSoap_SetInProcBuildCompletionNotificationAvailabilityResponse responseData = new _ConfigurationSettingsServiceSoap_SetInProcBuildCompletionNotificationAvailabilityResponse();


 executeSOAPRequest(
 request,
 "SetInProcBuildCompletionNotificationAvailabilityResponse",
 new SOAPMethodResponseReader()
            {
 public void readSOAPResponse(
 final XMLStreamReader reader,
 final InputStream in)
 throws XMLStreamException, IOException
                {
 responseData.readFromElement(reader);
                }
            });
    }


 public int getMaxBuildListSize()
 throws TransportException, SOAPFault
    {
 final _ConfigurationSettingsServiceSoap_GetMaxBuildListSize requestData = new _ConfigurationSettingsServiceSoap_GetMaxBuildListSize();


 final SOAPRequest request = createSOAPRequest(
 "GetMaxBuildListSize",
 new SOAPMethodRequestWriter()
                {
 public void writeSOAPRequest(
 final XMLStreamWriter writer,
 final OutputStream out)
 throws XMLStreamException, IOException
                    {
 requestData.writeAsElement(
 writer,
 "GetMaxBuildListSize");
                    }
                });


 final _ConfigurationSettingsServiceSoap_GetMaxBuildListSizeResponse responseData = new _ConfigurationSettingsServiceSoap_GetMaxBuildListSizeResponse();


 executeSOAPRequest(
 request,
 "GetMaxBuildListSizeResponse",
 new SOAPMethodResponseReader()
            {
 public void readSOAPResponse(
 final XMLStreamReader reader,
 final InputStream in)
 throws XMLStreamException, IOException
                {
 responseData.readFromElement(reader);
                }
            });


 return responseData.getGetMaxBuildListSizeResult();
    }


 public void setMaxBuildListSize(final int maxBuildListSize)
 throws TransportException, SOAPFault
    {
 final _ConfigurationSettingsServiceSoap_SetMaxBuildListSize requestData = new _ConfigurationSettingsServiceSoap_SetMaxBuildListSize(
 maxBuildListSize);


 final SOAPRequest request = createSOAPRequest(
 "SetMaxBuildListSize",
 new SOAPMethodRequestWriter()
                {
 public void writeSOAPRequest(
 final XMLStreamWriter writer,
 final OutputStream out)
 throws XMLStreamException, IOException
                    {
 requestData.writeAsElement(
 writer,
 "SetMaxBuildListSize");
                    }
                });


 final _ConfigurationSettingsServiceSoap_SetMaxBuildListSizeResponse responseData = new _ConfigurationSettingsServiceSoap_SetMaxBuildListSizeResponse();


 executeSOAPRequest(
 request,
 "SetMaxBuildListSizeResponse",
 new SOAPMethodResponseReader()
            {
 public void readSOAPResponse(
 final XMLStreamReader reader,
 final InputStream in)
 throws XMLStreamException, IOException
                {
 responseData.readFromElement(reader);
                }
            });
    }


 public int getWorkItemQueryTimeout()
 throws TransportException, SOAPFault
    {
 final _ConfigurationSettingsServiceSoap_GetWorkItemQueryTimeout requestData = new _ConfigurationSettingsServiceSoap_GetWorkItemQueryTimeout();


 final SOAPRequest request = createSOAPRequest(
 "GetWorkItemQueryTimeout",
 new SOAPMethodRequestWriter()
                {
 public void writeSOAPRequest(
 final XMLStreamWriter writer,
 final OutputStream out)
 throws XMLStreamException, IOException
                    {
 requestData.writeAsElement(
 writer,
 "GetWorkItemQueryTimeout");
                    }
                });


 final _ConfigurationSettingsServiceSoap_GetWorkItemQueryTimeoutResponse responseData = new _ConfigurationSettingsServiceSoap_GetWorkItemQueryTimeoutResponse();


 executeSOAPRequest(
 request,
 "GetWorkItemQueryTimeoutResponse",
 new SOAPMethodResponseReader()
            {
 public void readSOAPResponse(
 final XMLStreamReader reader,
 final InputStream in)
 throws XMLStreamException, IOException
                {
 responseData.readFromElement(reader);
                }
            });


 return responseData.getGetWorkItemQueryTimeoutResult();
    }


 public void setWorkItemQueryTimeout(final int workItemQueryTimeout)
 throws TransportException, SOAPFault
    {
 final _ConfigurationSettingsServiceSoap_SetWorkItemQueryTimeout requestData = new _ConfigurationSettingsServiceSoap_SetWorkItemQueryTimeout(
 workItemQueryTimeout);


 final SOAPRequest request = createSOAPRequest(
 "SetWorkItemQueryTimeout",
 new SOAPMethodRequestWriter()
                {
 public void writeSOAPRequest(
 final XMLStreamWriter writer,
 final OutputStream out)
 throws XMLStreamException, IOException
                    {
 requestData.writeAsElement(
 writer,
 "SetWorkItemQueryTimeout");
                    }
                });


 final _ConfigurationSettingsServiceSoap_SetWorkItemQueryTimeoutResponse responseData = new _ConfigurationSettingsServiceSoap_SetWorkItemQueryTimeoutResponse();


 executeSOAPRequest(
 request,
 "SetWorkItemQueryTimeoutResponse",
 new SOAPMethodResponseReader()
            {
 public void readSOAPResponse(
 final XMLStreamReader reader,
 final InputStream in)
 throws XMLStreamException, IOException
                {
 responseData.readFromElement(reader);
                }
            });
    }
}