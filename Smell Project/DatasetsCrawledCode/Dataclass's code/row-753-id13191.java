 public class ListElement extends Canvas
	{


 private Tab tab;


 private int index;


 private boolean selected;


 private boolean hover;


 public ListElement( Composite parent, final Tab tab, int index )
		{
 super( parent, SWT.NO_FOCUS );
 this.tab = tab;
 hover = false;
 selected = false;
 this.index = index;


 addPaintListener( new PaintListener( ) {


 public void paintControl( PaintEvent e )
				{
 paint( e );
				}
			} );
 addMouseListener( new MouseAdapter( ) {


 public void mouseDown( MouseEvent e )
				{
 if ( !selected )
					{
 select( getIndex( ListElement.this ), true );
					}
 Composite tabbedPropertyComposite = getParent( );
 Control[] children = tabbedPropertyComposite.getParent( )
							.getTabList( );
 if ( children != null && children.length > 0 )
					{
 for ( int i = 0; i < children.length; i++ )
						{
 if ( children[i] == TabbedPropertyList.this )
							{
 continue;
							}
 else if ( children[i].setFocus( ) )
							{
 focus = false;
 return;
							}
						}
					}
				}
			} );
 addMouseMoveListener( new MouseMoveListener( ) {


 public void mouseMove( MouseEvent e )
				{
 if ( !hover )
					{
 hover = true;
 redraw( );
					}
				}
			} );
 addMouseTrackListener( new MouseTrackAdapter( ) {


 public void mouseExit( MouseEvent e )
				{
 hover = false;
 redraw( );
				}
			} );
		}


 public void setSelected( boolean selected )
		{
 this.selected = selected;
 redraw( );
		}


 /**
		 * Draws elements and collects element areas.
		 */
 private void paint( PaintEvent e )
		{
 /*
			 * draw the top two lines of the tab, same for selected, hover and
			 * default
			 */
 Rectangle bounds = getBounds( );
 e.gc.setForeground( widgetNormalShadow );
 e.gc.drawLine( 0, 0, bounds.width - 1, 0 );
 e.gc.setForeground( listBackground );
 e.gc.drawLine( 0, 1, bounds.width - 1, 1 );


 /* draw the fill in the tab */
 if ( selected )
			{
 e.gc.setBackground( listBackground );
 e.gc.fillRectangle( 0, 2, bounds.width, bounds.height - 1 );
			}
 else if ( hover && tab.isIndented( ) )
			{
 e.gc.setBackground( indentedHoverBackground );
 e.gc.fillRectangle( 0, 2, bounds.width - 1, bounds.height - 1 );
			}
 else if ( hover )
			{
 e.gc.setForeground( hoverGradientStart );
 e.gc.setBackground( hoverGradientEnd );
 e.gc.fillGradientRectangle( 0,
 2,
 bounds.width - 1,
 bounds.height - 1,
 true );
			}
 else if ( tab.isIndented( ) )
			{
 e.gc.setBackground( indentedDefaultBackground );
 e.gc.fillRectangle( 0, 2, bounds.width - 1, bounds.height - 1 );
			}
 else
			{
 e.gc.setForeground( defaultGradientStart );
 e.gc.setBackground( defaultGradientEnd );
 e.gc.fillGradientRectangle( 0,
 2,
 bounds.width - 1,
 bounds.height - 1,
 true );
			}


 if ( !selected )
			{
 e.gc.setForeground( widgetNormalShadow );
 e.gc.drawLine( bounds.width - 1,
 1,
 bounds.width - 1,
 bounds.height + 1 );
			}


 int textIndent = INDENT;
 FontMetrics fm = e.gc.getFontMetrics( );
 int height = fm.getHeight( );
 int textMiddle = ( bounds.height - height ) / 2;


 if ( selected
					&& tab.getImage( ) != null
					&& !tab.getImage( ).isDisposed( ) )
			{
 /* draw the icon for the selected tab */
 if ( tab.isIndented( ) )
				{
 textIndent = textIndent + INDENT;
				}
 else
				{
 textIndent = textIndent - 3;
				}
 e.gc.drawImage( tab.getImage( ), textIndent, textMiddle - 1 );
 textIndent = textIndent + 16 + 5;
			}
 else if ( tab.isIndented( ) )
			{
 textIndent = textIndent + INDENT;
			}


 /* draw the text */
 e.gc.setForeground( widgetForeground );
 if ( selected )
			{
 /* selected tab is bold font */
 e.gc.setFont( JFaceResources.getFontRegistry( )
						.getBold( JFaceResources.DEFAULT_FONT ) );
			}
 e.gc.drawText( tab.getText( ), textIndent, textMiddle, true );


 if ( ( (TabbedPropertyList) getParent( ) ).focus
					&& selected
					&& focus )
			{
 /* draw a line if the tab has focus */
 Point point = e.gc.textExtent( tab.getText( ) );
 e.gc.drawLine( textIndent, bounds.height - 4, textIndent
						+ point.x, bounds.height - 4 );
			}


 /* draw the bottom line on the tab for selected and default */
 if ( !hover )
			{
 e.gc.setForeground( listBackground );
 e.gc.drawLine( 0,
 bounds.height - 1,
 bounds.width - 2,
 bounds.height - 1 );
			}


		}


 public String getText( )
		{
 return tab.getText( );
		}


 public String toString( )
		{
 return tab.getText( );
		}
	}