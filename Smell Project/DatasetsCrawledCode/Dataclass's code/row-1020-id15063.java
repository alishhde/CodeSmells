public class ItemContent
{


 private String defaultName = ""; //$NON-NLS-1$
 private String displayName = ""; //$NON-NLS-1$
 private String customName = ""; //$NON-NLS-1$
 private String description = ""; //$NON-NLS-1$


 public ItemContent( String string )
	{
 super( );
 this.setCustomName( string );
	}


 /**
	 * 
	 * @return Return the default name of ItemContent
	 */
 public String getDefaultName( )
	{
 return defaultName;
	}


 /**
	 * 
	 * @return Returns the display name of the ItemContent
	 */
 public String getDisplayName( )
	{
 return displayName;
	}


 /**
	 * Set default name for ItemContent
	 * 
	 * @param string
	 */
 public void setDefaultName( String string )
	{
 defaultName = string.trim( );
	}


 /**
	 * Sets the display name for the ItemContent
	 * 
	 * @param string
	 */
 public void setDisplayName( String string )
	{
 displayName = string.trim( );
	}


 /**
	 * 
	 * @return custom name of ItemContent
	 */
 public String getCustomName( )
	{
 return customName;
	}


 /**
	 * Set custom name for ItemContent
	 * 
	 * @param string
	 */
 public void setCustomName( String string )
	{
 customName = string.trim( );
	}


 /**
	 * 
	 * @return the description of ItemContent
	 */
 public String getDescription( )
	{
 return description;
	}


 /**
	 * Set the description for ItemContent
	 * 
	 * @param string
	 */
 public void setDescription( String string )
	{
 description = string.trim( );
	}


}