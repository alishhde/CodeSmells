 public void widgetSelected( SelectionEvent e )
	{
 Object widget = e.widget;
 if ( widget == btnVisible )
		{
 // Notify Listeners that a change has occurred in the value
 fireValueChangedEvent( GanttLineAttributesComposite.VISIBILITY_CHANGED_EVENT,
 Boolean.valueOf( btnVisible.getSelectionState( ) == ChartCheckbox.STATE_SELECTED ),
					( btnVisible.getSelectionState( ) == ChartCheckbox.STATE_GRAYED ) ? ChartUIExtensionUtil.PROPERTY_UNSET
							: ChartUIExtensionUtil.PROPERTY_UPDATE );
 // Notification may cause this class disposed
 if ( isDisposed( ) )
			{
 return;
			}
 // Enable/Disable UI Elements
 boolean bEnableUI = context.getUIFactory( ).canEnableUI( btnVisible );
 if ( bEnableStyles )
			{
 lblStyle.setEnabled( bEnableUI );
 cmbStyle.setEnabled( bEnableUI );
			}
 if ( bEnableWidths )
			{
 lblWidth.setEnabled( bEnableUI  );
 iscWidth.setEnabled( bEnableUI  );
			}
 if ( bEnableColor )
			{
 lblColor.setEnabled( bEnableUI );
 cmbColor.setEnabled( bEnableUI );
			}
		}
	}