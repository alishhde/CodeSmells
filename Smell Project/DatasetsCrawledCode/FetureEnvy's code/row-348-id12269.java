 private void createColumns(final Composite parent) {


 final String[] titles = { "Passing type", "Typename", "Name" };
 final int[] bounds = { 100, 200, 280 };


 // pass type
 TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0]);
 col.setLabelProvider(new ColumnLabelProvider() {
 @Override
 public String getText(final Object element) {
 final ParamTableItem p = (ParamTableItem) element;
 return p.getPassType();
			}
		});


 // type name
 col = createTableViewerColumn(titles[1], bounds[1]);
 col.setLabelProvider(new ColumnLabelProvider() {


 @Override
 public String getText(final Object element) {
 final ParamTableItem p = (ParamTableItem) element;
 return p.getType();
			}
		});


 // name
 col = createTableViewerColumn(titles[2], bounds[2]);
 col.setEditingSupport(new NameEditingSupport(col.getViewer()));
 col.setLabelProvider(new ColumnLabelProvider() {
 @Override
 public String getText(final Object element) {
 final ParamTableItem p = (ParamTableItem) element;
 return p.getName();
			}
		});
	}