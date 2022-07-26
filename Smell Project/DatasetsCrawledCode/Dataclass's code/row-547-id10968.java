public class _ReportingService2005Soap_ValidateExtensionSettings
 implements ElementSerializable
{
 // No attributes    


 // Elements
 protected String extension;
 protected _ParameterValueOrFieldReference[] parameterValues;


 public _ReportingService2005Soap_ValidateExtensionSettings()
    {
 super();
    }


 public _ReportingService2005Soap_ValidateExtensionSettings(
 final String extension,
 final _ParameterValueOrFieldReference[] parameterValues)
    {
 // TODO : Call super() instead of setting all fields directly?
 setExtension(extension);
 setParameterValues(parameterValues);
    }


 public String getExtension()
    {
 return this.extension;
    }


 public void setExtension(String value)
    {
 this.extension = value;
    }


 public _ParameterValueOrFieldReference[] getParameterValues()
    {
 return this.parameterValues;
    }


 public void setParameterValues(_ParameterValueOrFieldReference[] value)
    {
 this.parameterValues = value;
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
 "Extension",
 this.extension);


 if (this.parameterValues != null)
        {
 /*
             * The element type is an array.
             */
 writer.writeStartElement("ParameterValues");


 for (int iterator0 = 0; iterator0 < this.parameterValues.length; iterator0++)
            {
 this.parameterValues[iterator0].writeAsElement(
 writer,
 "ParameterValueOrFieldReference");
            }


 writer.writeEndElement();
        }


 writer.writeEndElement();
    }
}