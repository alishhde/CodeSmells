public class DefaultResourceService implements ResourceService {


 private String servletPath = "";
 
 /**
	 * @param servletPath the servletPath to set
	 */
 public void setServletPath(String servletPath) {
 this.servletPath = servletPath;
	}


 public String getServletPath() {
 return servletPath;
	}


}