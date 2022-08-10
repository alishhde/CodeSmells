 protected boolean downloadLog(HttpServletRequest request,
 HttpServletResponse response, ILogService logService,
 String appenderName) throws ServletException {


 FileAppender<ILoggingEvent> appender = logService
				.getFileAppender(appenderName);
 if (appender == null) {
 String msg = NLS.bind("Appender not found: {0}", appenderName);
 final ServerStatus error = new ServerStatus(IStatus.ERROR,
 HttpServletResponse.SC_NOT_FOUND, msg, null);
 return statusHandler.handleRequest(request, response, error);
		}


 File logFile = new File(appender.getFile());
 try {
 LogUtils.provideLogFile(logFile, response);
		} catch (Exception ex) {
 String msg = NLS.bind("An error occured when looking for log {0}.",
 logFile.getName());
 final ServerStatus error = new ServerStatus(IStatus.ERROR,
 HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msg, ex);


 LogHelper.log(error);
 return statusHandler.handleRequest(request, response, error);
		}


 return true;
	}