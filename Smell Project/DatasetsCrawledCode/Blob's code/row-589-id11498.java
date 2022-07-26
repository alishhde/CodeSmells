public class _Repository4Soap_QueryPendingSetsWithLocalWorkspaces
 implements ElementSerializable
{
 // No attributes    


 // Elements
 protected String localWorkspaceName;
 protected String localWorkspaceOwner;
 protected String queryWorkspaceName;
 protected String ownerName;
 protected _ItemSpec[] itemSpecs;
 protected boolean generateDownloadUrls;
 protected String[] itemPropertyFilters;


 public _Repository4Soap_QueryPendingSetsWithLocalWorkspaces()
    {
 super();
    }


 public _Repository4Soap_QueryPendingSetsWithLocalWorkspaces(
 final String localWorkspaceName,
 final String localWorkspaceOwner,
 final String queryWorkspaceName,
 final String ownerName,
 final _ItemSpec[] itemSpecs,
 final boolean generateDownloadUrls,
 final String[] itemPropertyFilters)
    {
 // TODO : Call super() instead of setting all fields directly?
 setLocalWorkspaceName(localWorkspaceName);
 setLocalWorkspaceOwner(localWorkspaceOwner);
 setQueryWorkspaceName(queryWorkspaceName);
 setOwnerName(ownerName);
 setItemSpecs(itemSpecs);
 setGenerateDownloadUrls(generateDownloadUrls);
 setItemPropertyFilters(itemPropertyFilters);
    }


 public String getLocalWorkspaceName()
    {
 return this.localWorkspaceName;
    }


 public void setLocalWorkspaceName(String value)
    {
 this.localWorkspaceName = value;
    }


 public String getLocalWorkspaceOwner()
    {
 return this.localWorkspaceOwner;
    }


 public void setLocalWorkspaceOwner(String value)
    {
 this.localWorkspaceOwner = value;
    }


 public String getQueryWorkspaceName()
    {
 return this.queryWorkspaceName;
    }


 public void setQueryWorkspaceName(String value)
    {
 this.queryWorkspaceName = value;
    }


 public String getOwnerName()
    {
 return this.ownerName;
    }


 public void setOwnerName(String value)
    {
 this.ownerName = value;
    }


 public _ItemSpec[] getItemSpecs()
    {
 return this.itemSpecs;
    }


 public void setItemSpecs(_ItemSpec[] value)
    {
 this.itemSpecs = value;
    }


 public boolean isGenerateDownloadUrls()
    {
 return this.generateDownloadUrls;
    }


 public void setGenerateDownloadUrls(boolean value)
    {
 this.generateDownloadUrls = value;
    }


 public String[] getItemPropertyFilters()
    {
 return this.itemPropertyFilters;
    }


 public void setItemPropertyFilters(String[] value)
    {
 this.itemPropertyFilters = value;
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
 "localWorkspaceName",
 this.localWorkspaceName);
 XMLStreamWriterHelper.writeElement(
 writer,
 "localWorkspaceOwner",
 this.localWorkspaceOwner);
 XMLStreamWriterHelper.writeElement(
 writer,
 "queryWorkspaceName",
 this.queryWorkspaceName);
 XMLStreamWriterHelper.writeElement(
 writer,
 "ownerName",
 this.ownerName);


 if (this.itemSpecs != null)
        {
 /*
             * The element type is an array.
             */
 writer.writeStartElement("itemSpecs");


 for (int iterator0 = 0; iterator0 < this.itemSpecs.length; iterator0++)
            {
 this.itemSpecs[iterator0].writeAsElement(
 writer,
 "ItemSpec");
            }


 writer.writeEndElement();
        }


 XMLStreamWriterHelper.writeElement(
 writer,
 "generateDownloadUrls",
 this.generateDownloadUrls);


 if (this.itemPropertyFilters != null)
        {
 /*
             * The element type is an array.
             */
 writer.writeStartElement("itemPropertyFilters");


 for (int iterator0 = 0; iterator0 < this.itemPropertyFilters.length; iterator0++)
            {
 XMLStreamWriterHelper.writeElement(
 writer,
 "string",
 this.itemPropertyFilters[iterator0]);
            }


 writer.writeEndElement();
        }


 writer.writeEndElement();
    }
}