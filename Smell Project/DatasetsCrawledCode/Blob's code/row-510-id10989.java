public class _ParameterFieldReference
 extends _ParameterValueOrFieldReference
 implements ElementSerializable, ElementDeserializable
{
 // No attributes    


 // Elements
 protected String parameterName;
 protected String fieldAlias;


 public _ParameterFieldReference()
    {
 super();
    }


 public _ParameterFieldReference(
 final String parameterName,
 final String fieldAlias)
    {
 // TODO : Call super() instead of setting all fields directly?
 setParameterName(parameterName);
 setFieldAlias(fieldAlias);
    }


 public String getParameterName()
    {
 return this.parameterName;
    }


 public void setParameterName(String value)
    {
 this.parameterName = value;
    }


 public String getFieldAlias()
    {
 return this.fieldAlias;
    }


 public void setFieldAlias(String value)
    {
 this.fieldAlias = value;
    }


 public void writeAsElement(
 final XMLStreamWriter writer,
 final String name)
 throws XMLStreamException
    {
 writer.writeStartElement(name);


 // Declare our type
 writer.writeAttribute(
 "xsi",
 "http://www.w3.org/2001/XMLSchema-instance",
 "type",
 "ParameterFieldReference");


 // Elements
 XMLStreamWriterHelper.writeElement(
 writer,
 "ParameterName",
 this.parameterName);
 XMLStreamWriterHelper.writeElement(
 writer,
 "FieldAlias",
 this.fieldAlias);


 writer.writeEndElement();
    }


 public void readFromElement(final XMLStreamReader reader)
 throws XMLStreamException
    {
 String localName;


 // This object uses no attributes


 // Elements
 int event;


 do
        {
 event = reader.next();


 if (event == XMLStreamConstants.START_ELEMENT)
            {
 localName = reader.getLocalName();


 if (localName.equalsIgnoreCase("ParameterName"))
                {
 this.parameterName = reader.getElementText();
                }
 else if (localName.equalsIgnoreCase("FieldAlias"))
                {
 this.fieldAlias = reader.getElementText();
                }
 else
                {
 // Read the unknown child element until its end
 XMLStreamReaderHelper.readUntilElementEnd(reader);
                }
            }
        }
 while (event != XMLStreamConstants.END_ELEMENT);
    }
}