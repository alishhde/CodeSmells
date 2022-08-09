public abstract class ContextMenuProvider extends MenuManager implements
 IMenuListener {


 private EditPartViewer viewer;


 /**
	 * Constructs a context menu for the specified EditPartViewer.
	 * 
	 * @param viewer
	 *            the editpart viewer
	 */
 public ContextMenuProvider(EditPartViewer viewer) {
 setViewer(viewer);
 addMenuListener(this);
 setRemoveAllWhenShown(true);
	}


 /**
	 * Called when the menu is about to show. Subclasses must implement this
	 * method to populate the menu each time it is shown.
	 * 
	 * @param menu
	 *            this parameter is actually <code>this</code> object
	 */
 public abstract void buildContextMenu(IMenuManager menu);


 /**
	 * Returns the EditPartViewer
	 * 
	 * @return the viewer
	 */
 protected EditPartViewer getViewer() {
 return viewer;
	}


 /**
	 * @see IMenuListener#menuAboutToShow(IMenuManager)
	 */
 public void menuAboutToShow(IMenuManager menu) {
 buildContextMenu(menu);
	}


 /**
	 * Sets the editpart viewer. Called during construction.
	 * 
	 * @param viewer
	 *            the viewer
	 */
 protected void setViewer(EditPartViewer viewer) {
 this.viewer = viewer;
	}


}