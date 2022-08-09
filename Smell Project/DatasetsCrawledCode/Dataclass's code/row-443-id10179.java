public class _AdministrationWebServiceSoap_QueryBuildAgentsByUri
 implements ElementSerializable
{
 // No attributes    


 // Elements
 protected String[] agentUris;


 public _AdministrationWebServiceSoap_QueryBuildAgentsByUri()
    {
 super();
    }


 public _AdministrationWebServiceSoap_QueryBuildAgentsByUri(final String[] agentUris)
    {
 // TODO : Call super() instead of setting all fields directly?
 setAgentUris(agentUris);
    }


 public String[] getAgentUris()
    {
 return this.agentUris;
    }


 public void setAgentUris(String[] value)
    {
 this.agentUris = value;
    }


 public void writeAsElement(
 final XMLStreamWriter writer,
 final String name)
 throws XMLStreamException
    {
 writer.writeStartElement(name);


 // Elements
 if (this.agentUris != null)
        {
 /*
             * The element type is an array.
             */
 writer.writeStartElement("agentUris");


 for (int iterator0 = 0; iterator0 < this.agentUris.length; iterator0++)
            {
 XMLStreamWriterHelper.writeElement(
 writer,
 "string",
 this.agentUris[iterator0]);
            }


 writer.writeEndElement();
        }


 writer.writeEndElement();
    }
}