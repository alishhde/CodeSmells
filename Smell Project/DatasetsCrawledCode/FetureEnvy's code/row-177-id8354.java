 public String[] getPath(final TreeItem swtTreeItem) {
 return Display.syncExec(new ResultRunnable<String[]>() {
 @Override
 public String[] run() {
 org.eclipse.swt.widgets.TreeItem swttiDummy = swtTreeItem;
 LinkedList<String> items = new LinkedList<String>();
 while (swttiDummy != null) {
 items.addFirst(swttiDummy.getText());
 swttiDummy = swttiDummy.getParentItem();
				}
 return items.toArray(new String[0]);
			}
		});
	}