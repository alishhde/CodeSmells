public class LibraryBreadcrumbNodeProvider extends
 DesignerBreadcrumbNodeProvider
{


 public boolean validate( Object element )
	{
 if ( getEditPart( element ) == null
 // || getEditPart( element ) instanceof EmptyEditPart
		)
 return false;
 return true;
	}


 public EditPart getEditPart( Object element )
	{
 // EditPart editPart = super.getEditPart( element );
 // if ( editPart == null || editPart instanceof EmptyEditPart )
 // return null;
 return super.getEditPart( element );
	}


 @Override
 public Object[] getChildren( Object element )
	{
 if ( getRealModel( element ) instanceof LibraryHandle )
		{
 return ( (LibraryHandle) getRealModel( element ) ).getComponents( )
					.getContents( )
					.toArray( );
		}
 List children = new ArrayList( );
 children.addAll( Arrays.asList( super.getChildren( element ) ) );
 for ( int i = 0; i < children.size( ); i++ )
		{
 if ( children.get( i ) instanceof EmptyEditPart )
			{
 children.remove( i );
 i--;
			}
		}
 return children.toArray( );
	}


 @Override
 public String getText( Object element )
	{
 Object object = getRealModel( element );
 if ( getEditPart( object ) == null )
		{
 if ( object instanceof DesignElementHandle
					&& ( (DesignElementHandle) object ).getContainer( ) instanceof LibraryHandle )
			{
 INodeProvider provider = ProviderFactory.createProvider( object );
 if ( provider == null )
 return object.toString( );
 return provider.getNodeDisplayName( object );
			}
		}
 return super.getText( element );
	}


 @Override
 public Image getImage( Object element )
	{
 Object object = getRealModel( element );
 if ( getEditPart( object ) == null )
		{
 if ( object instanceof DesignElementHandle
					&& ( (DesignElementHandle) object ).getContainer( ) instanceof LibraryHandle )
			{
 INodeProvider provider = ProviderFactory.createProvider( object );
 if ( provider == null )
 return null;
 return provider.getNodeIcon( object );
			}
		}
 return super.getImage( element );
	}


 @Override
 public String getTooltipText( Object element )
	{
 Object object = getRealModel( element );
 if ( getEditPart( object ) == null )
		{
 if ( object instanceof DesignElementHandle
					&& ( (DesignElementHandle) object ).getContainer( ) instanceof LibraryHandle )
			{
 INodeProvider provider = ProviderFactory.createProvider( object );
 if ( provider == null )
 return object.toString( );
 return provider.getNodeTooltip( object );
			}
		}
 return super.getTooltipText( element );
	}
}