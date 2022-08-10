 protected boolean doAction( ) throws Exception
	{
 if ( Policy.TRACING_ACTIONS )
		{
 System.out.println( "Edit data source action >> Runs ..." ); //$NON-NLS-1$
		}
 DataSourceHandle handle = (DataSourceHandle) getSelection( );
 DataSourceEditor dialog = new AdvancedDataSourceEditor( PlatformUI
				.getWorkbench( ).getDisplay( ).getActiveShell( ), handle );


 return ( dialog.open( ) == IDialogConstants.OK_ID );
	}