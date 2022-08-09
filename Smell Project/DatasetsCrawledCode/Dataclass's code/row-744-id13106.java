public class _RepositorySoap_LabelItem
 implements ElementSerializable
{
 // No attributes    


 // Elements
 protected String workspaceName;
 protected String workspaceOwner;
 protected _VersionControlLabel label;
 protected _LabelItemSpec[] labelSpecs;
 protected _LabelChildOption children;


 public _RepositorySoap_LabelItem()
    {
 super();
    }


 public _RepositorySoap_LabelItem(
 final String workspaceName,
 final String workspaceOwner,
 final _VersionControlLabel label,
 final _LabelItemSpec[] labelSpecs,
 final _LabelChildOption children)
    {
 // TODO : Call super() instead of setting all fields directly?
 setWorkspaceName(workspaceName);
 setWorkspaceOwner(workspaceOwner);
 setLabel(label);
 setLabelSpecs(labelSpecs);
 setChildren(children);
    }


 public String getWorkspaceName()
    {
 return this.workspaceName;
    }


 public void setWorkspaceName(String value)
    {
 this.workspaceName = value;
    }


 public String getWorkspaceOwner()
    {
 return this.workspaceOwner;
    }


 public void setWorkspaceOwner(String value)
    {
 this.workspaceOwner = value;
    }


 public _VersionControlLabel getLabel()
    {
 return this.label;
    }


 public void setLabel(_VersionControlLabel value)
    {
 this.label = value;
    }


 public _LabelItemSpec[] getLabelSpecs()
    {
 return this.labelSpecs;
    }


 public void setLabelSpecs(_LabelItemSpec[] value)
    {
 this.labelSpecs = value;
    }


 public _LabelChildOption getChildren()
    {
 return this.children;
    }


 public void setChildren(_LabelChildOption value)
    {
 if (value == null)
        {
 throw new IllegalArgumentException("'children' is a required element, its value cannot be null");
        }


 this.children = value;
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
 "workspaceName",
 this.workspaceName);
 XMLStreamWriterHelper.writeElement(
 writer,
 "workspaceOwner",
 this.workspaceOwner);


 if (this.label != null)
        {
 this.label.writeAsElement(
 writer,
 "label");
        }


 if (this.labelSpecs != null)
        {
 /*
             * The element type is an array.
             */
 writer.writeStartElement("labelSpecs");


 for (int iterator0 = 0; iterator0 < this.labelSpecs.length; iterator0++)
            {
 this.labelSpecs[iterator0].writeAsElement(
 writer,
 "LabelItemSpec");
            }


 writer.writeEndElement();
        }


 this.children.writeAsElement(
 writer,
 "children");


 writer.writeEndElement();
    }
}