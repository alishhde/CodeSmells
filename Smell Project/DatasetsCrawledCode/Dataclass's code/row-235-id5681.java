public class OdaDimensionState extends ReportElementState
{


 /**
	 * The dimension being created.
	 */


 protected Dimension element = null;


 /**
	 * Constructs dimension state with the design parser handler, the container
	 * element and the container property name of the report element.
	 * 
	 * @param handler
	 *            the design file parser handler
	 * @param theContainer
	 *            the element that contains this one
	 * @param prop
	 *            the slot in which this element appears
	 */


 public OdaDimensionState( ModuleParserHandler handler,
 DesignElement theContainer, String prop )
	{
 super( handler, theContainer, prop );
	}


 /*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.parser.ReportElementState#getElement()
	 */


 public DesignElement getElement( )
	{
 return element;
	}


 /*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.birt.report.model.util.AbstractParseState#parseAttrs(org.xml.sax.Attributes)
	 */


 public void parseAttrs( Attributes attrs ) throws XMLParserException
	{
 element = new OdaDimension( );
 initElement( attrs, true );
	}
}