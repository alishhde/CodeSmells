 public static Control createCustomAreaWithLink(final Composite parent, final Dialog dialog, final Binary binary) {
 final String binaryLabel = binary.getLabel();
 final String prefix = "The requested operation cannot be performed due to invalid '" + binaryLabel
				+ "' settings. Check your '" + binaryLabel
				+ "' configuration and preferences under the corresponding ";
 final String link = "preference page";
 final String suffix = ".";
 final String text = prefix + link + suffix;


 final Composite control = new Composite(parent, NONE);
 control.setLayout(GridLayoutFactory.fillDefaults().create());
 final GridData gridData = GridDataFactory.fillDefaults().align(LEFT, TOP).grab(true, true).create();
 control.setLayoutData(gridData);


 final StyleRange style = new StyleRange();
 style.underline = true;
 style.underlineStyle = UNDERLINE_LINK;


 final StyledText styledText = new StyledText(control, MULTI | READ_ONLY | WRAP);
 styledText.setWordWrap(true);
 styledText.setJustify(true);
 styledText.setText(text);
 final GridData textGridData = GridDataFactory.fillDefaults().align(FILL, FILL).grab(true, true).create();
 textGridData.widthHint = TEXT_WIDTH_HINT;
 textGridData.heightHint = TEXT_HEIGHT_HINT;
 styledText.setLayoutData(textGridData);


 styledText.setEditable(false);
 styledText.setBackground(UIUtils.getSystemColor(COLOR_WIDGET_BACKGROUND));
 final int[] ranges = { text.indexOf(link), link.length() };
 final StyleRange[] styles = { style };
 styledText.setStyleRanges(ranges, styles);


 styledText.addMouseListener(new MouseAdapter() {


 @Override
 public void mouseDown(final MouseEvent event) {
 try {
 final int offset = styledText.getOffsetAtPoint(new Point(event.x, event.y));
 final StyleRange actualStyle = offset >= 0 ? styledText.getStyleRangeAtOffset(offset) : null;
 if (null != actualStyle && actualStyle.underline
							&& UNDERLINE_LINK == actualStyle.underlineStyle) {


 dialog.close();
 final PreferenceDialog preferenceDialog = createPreferenceDialogOn(
 UIUtils.getShell(),
 BinariesPreferencePage.ID,
 FILTER_IDS,
 null);


 if (null != preferenceDialog) {
 preferenceDialog.open();
						}


					}
				} catch (final IllegalArgumentException e) {
 // We are not over the actual text.
				}
			}


		});


 return control;
	}