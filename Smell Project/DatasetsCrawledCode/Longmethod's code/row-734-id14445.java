 public void run( IAction action )
	{
 if ( !preGenerate( ) )
		{
 return;
		}


 IFile file = getSelectedFile( );
 if ( file != null )
		{
 String url = file.getLocation( ).toOSString( );


 Map options = new HashMap( );
 options.put( WebViewer.RESOURCE_FOLDER_KEY,
 ReportPlugin.getDefault( )
							.getResourceFolder( file.getProject( ) ) );
 options.put( WebViewer.SERVLET_NAME_KEY, WebViewer.VIEWER_DOCUMENT );


 Object adapter = ElementAdapterManager.getAdapter( action,
 IPreviewAction.class );


 if ( adapter instanceof IPreviewAction )
			{
 IPreviewAction delegate = (IPreviewAction) adapter;


 delegate.setProperty( IPreviewConstants.REPORT_PREVIEW_OPTIONS,
 options );
 delegate.setProperty( IPreviewConstants.REPORT_FILE_PATH, url );


 delegate.run( );


 return;
			}


 try
			{
 WebViewer.display( url, options );
			}
 catch ( Exception e )
			{
 ExceptionUtil.handle( e );
 return;
			}
		}
 else
		{
 action.setEnabled( false );
		}
	}