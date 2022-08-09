public class _BuildWebServiceSoap_UpdateBuildDefinitions
 implements ElementSerializable
{
 // No attributes    


 // Elements
 protected _BuildDefinition[] updates;


 public _BuildWebServiceSoap_UpdateBuildDefinitions()
    {
 super();
    }


 public _BuildWebServiceSoap_UpdateBuildDefinitions(final _BuildDefinition[] updates)
    {
 // TODO : Call super() instead of setting all fields directly?
 setUpdates(updates);
    }


 public _BuildDefinition[] getUpdates()
    {
 return this.updates;
    }


 public void setUpdates(_BuildDefinition[] value)
    {
 this.updates = value;
    }


 public void writeAsElement(
 final XMLStreamWriter writer,
 final String name)
 throws XMLStreamException
    {
 writer.writeStartElement(name);


 // Elements
 if (this.updates != null)
        {
 /*
             * The element type is an array.
             */
 writer.writeStartElement("updates");


 for (int iterator0 = 0; iterator0 < this.updates.length; iterator0++)
            {
 this.updates[iterator0].writeAsElement(
 writer,
 "BuildDefinition");
            }


 writer.writeEndElement();
        }


 writer.writeEndElement();
    }
}