 public String getColumnText(Object element, int columnIndex) {
 if (element instanceof HadoopServer) {
 HadoopServer server = (HadoopServer) element;


 switch (columnIndex) {
 case 0:
 return server.getLocationName();
 case 1:
 return server.getMasterHostName().toString();
 case 2:
 return server.getState();
 case 3:
 return "";
      }
    } else if (element instanceof HadoopJob) {
 HadoopJob job = (HadoopJob) element;


 switch (columnIndex) {
 case 0:
 return job.getJobID().toString();
 case 1:
 return "";
 case 2:
 return job.getState().toString();
 case 3:
 return job.getStatus();
      }
    } else if (element instanceof JarModule) {
 JarModule jar = (JarModule) element;


 switch (columnIndex) {
 case 0:
 return jar.toString();
 case 1:
 return "Publishing jar to server..";
 case 2:
 return "";
      }
    }


 return null;
  }