public class _LocationWebServiceSoap_Connect
 implements ElementSerializable
{
 // No attributes    


 // Elements
 protected int connectOptions;
 protected int lastChangeId;
 protected int features;


 public _LocationWebServiceSoap_Connect()
    {
 super();
    }


 public _LocationWebServiceSoap_Connect(
 final int connectOptions,
 final int lastChangeId,
 final int features)
    {
 // TODO : Call super() instead of setting all fields directly?
 setConnectOptions(connectOptions);
 setLastChangeId(lastChangeId);
 setFeatures(features);
    }


 public int getConnectOptions()
    {
 return this.connectOptions;
    }


 public void setConnectOptions(int value)
    {
 this.connectOptions = value;
    }


 public int getLastChangeId()
    {
 return this.lastChangeId;
    }


 public void setLastChangeId(int value)
    {
 this.lastChangeId = value;
    }


 public int getFeatures()
    {
 return this.features;
    }


 public void setFeatures(int value)
    {
 this.features = value;
    }


 public void writeAsElement(
 final XMLStreamWriter writer,
 final String name)
 throws XMLStreamException
    {
 writer.writeStartElement(name);


 // Elements
 XMLStreamWriterHelper.writeElement(
 writer,
 "connectOptions",
 this.connectOptions);
 XMLStreamWriterHelper.writeElement(
 writer,
 "lastChangeId",
 this.lastChangeId);
 XMLStreamWriterHelper.writeElement(
 writer,
 "features",
 this.features);


 writer.writeEndElement();
    }
}