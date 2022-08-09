public class AssemblerConfig {


 // Private Member Variables ------------------------------------------------


 /** The portlet app descriptor, which is usually WEB-INF/portlet.xml. */
 private File portletDescriptor;


 /** The webapp descriptor to assemble, which is usually WEB-INF/web.xml. */
 private File webappDescriptor;


 /** The assemble destination, which points to the assembled WAR file. */
 private File destination;


 /** The class of the servlet that will handle portlet requests */
 private String dispatchServletClass;


 /** The source archive to assemble */
 private File source;
 
 /** Assembler sink buffer size.  Defaults to 4096 bytes. */
 private int assemblerSinkBuflen = 1024 * 4; // 4kb


 // Public Methods ----------------------------------------------------------


 public File getPortletDescriptor() {
 return portletDescriptor;
    }


 public void setPortletDescriptor(File portletDescriptor) {
 this.portletDescriptor = portletDescriptor;
    }


 public File getWebappDescriptor() {
 return webappDescriptor;
    }


 public void setWebappDescriptor(File webappDescriptor) {
 this.webappDescriptor = webappDescriptor;
    }


 public File getDestination() {
 return destination;
    }


 public void setDestination(File destination) {
 this.destination = destination;
    }


 public String getDispatchServletClass() {
 return dispatchServletClass;
    }


 public void setDispatchServletClass(String dispatchServletClass) {
 this.dispatchServletClass = dispatchServletClass;
    }


 /**
     * @deprecated use <code>setSource(File)</code> instead.
     */
 public void setWarSource(File source) {
 this.source = source;
    }
 
 public void setSource(File source) {
 this.source = source;
    }
 
 /**
     * @deprecated use <code>getSource()</code> instead.
     */
 public File getWarSource() {
 return source;
    }
 
 public File getSource() {
 return source;
    }
 
 public int getAssemblerSinkBuflen() {
 return assemblerSinkBuflen;
    }
 
 public void setAssemblerSinkBuflen(int buflen) {
 this.assemblerSinkBuflen = buflen;
    }
}