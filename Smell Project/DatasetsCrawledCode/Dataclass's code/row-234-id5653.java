public class FakeErrorBoxWidget implements IErrorBox {
 /** 
	 * <p>Boolean to signify if a listener was registered.</p>
	 */
 private boolean observed;
 /** 
	 * <p>Boolean to store the display state.</p>
	 */
 private boolean displayed;


 /** 
	 * <p>The error message.</p>
	 */
 private String errorMsg = null;


 /** 
	 * <p>This operation returns true if the display operation is called for the FakeErrorBoxWidget.</p>
	 * @return <p>True if the widget was displayed, false if not.</p>
	 */
 public boolean widgetDisplayed() {
 return this.displayed;
	}


 /** 
	 * <p>This operation implements display() from UIWidget with a simple pass through that makes whether or not the method was called. Nothing is drawn on the screen.</p>
	 */
 @Override
 public void display() {


 this.displayed = true;


 return;


	}


 /** 
	 * (non-Javadoc)
	 * @see IErrorBox#setErrorString(String error)
	 */
 @Override
 public void setErrorString(String error) {


 // Set the error message
 errorMsg = error;


 return;


	}


 /** 
	 * (non-Javadoc)
	 * @see IErrorBox#getErrorString()
	 */
 @Override
 public String getErrorString() {


 // Return the error message
 return errorMsg;


	}


}