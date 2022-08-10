 private void createServerIdEditGroup( Composite parent )
    {
 // ServerID Group
 Group serverIdGroup = BaseWidgetUtils.createGroup( parent, "ServerID input", 1 );
 GridLayout serverIdGroupGridLayout = new GridLayout( 2, false );
 serverIdGroup.setLayout( serverIdGroupGridLayout );
 serverIdGroup.setLayoutData( new GridData( SWT.FILL, SWT.NONE, true, false ) );


 // ServerID Text
 BaseWidgetUtils.createLabel( serverIdGroup, "ID:", 1 );
 idText = BaseWidgetUtils.createText( serverIdGroup, "", 1 );
 idText.setLayoutData( new GridData( SWT.FILL, SWT.NONE, true, false ) );


 // URL Text
 BaseWidgetUtils.createLabel( serverIdGroup, "URL:", 1 );
 urlText = BaseWidgetUtils.createText( serverIdGroup, "", 1 );
 urlText.setLayoutData( new GridData( SWT.FILL, SWT.NONE, true, false ) );
    }