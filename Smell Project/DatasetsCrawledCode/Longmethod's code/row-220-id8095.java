 protected void createButtonGroup( Group grpTop )
	{
 btnAdd = new Button( grpTop, SWT.NONE );
		{
 btnAdd.setText( Messages.getString( "LineSeriesMarkerSheet.Label.Add" ) ); //$NON-NLS-1$
 btnAdd.addSelectionListener( this );
		}


 newMarkerEditor = new MarkerEditorComposite( grpTop,
 createMarker( ),
 getContext( ),
 getDefaultMarker( ) );
 if ( markerTypeSet != null )
		{
 newMarkerEditor.setSupportedMarkerTypes( markerTypeSet );
		}
 if ( outlineText != null )
		{
 newMarkerEditor.setOutlineText( outlineText );
		}
 
 btnRemove = new Button( grpTop, SWT.NONE );
		{
 btnRemove.setText( Messages.getString( "LineSeriesMarkerSheet.Label.Remove" ) ); //$NON-NLS-1$
 btnRemove.addSelectionListener( this );
		}


 btnUp = new Button( grpTop, SWT.ARROW | SWT.UP );
		{
 btnUp.setToolTipText( Messages.getString( "PaletteEditorComposite.Lbl.Up" ) ); //$NON-NLS-1$
 btnUp.addSelectionListener( this );
		}


 btnDown = new Button( grpTop, SWT.ARROW | SWT.DOWN );
		{
 btnDown.setToolTipText( Messages.getString( "PaletteEditorComposite.Lbl.Down" ) ); //$NON-NLS-1$
 btnDown.addSelectionListener( this );
		}
	}