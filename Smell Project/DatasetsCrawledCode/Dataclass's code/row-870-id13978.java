public class TagTag extends DefineTagSupport {


 private String name;


 public TagTag() {
    }


 // Tag interface
 //-------------------------------------------------------------------------
 public void doTag(XMLOutput output) throws JellyTagException {
 getTagLibrary().registerDynamicTag( getName(), getBody() );
    }


 // Properties
 //-------------------------------------------------------------------------


 /** @return the name of the tag to create */
 public String getName() {
 return name;
    }


 /** Sets the name of the tag to create */
 public void setName(String name) {
 this.name = name;
    }
}