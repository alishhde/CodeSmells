 @Override
 public String getLoggerLevel(String loggerName) {
 String result = null;


/*[IF Sidecar19-SE]*/
 try {
 Object logger = getLoggerFromName(loggerName);
/*[ELSE]	
			Logger logger = LogManager.getLogManager().getLogger(loggerName);
/*[ENDIF]*/ 
 
 if (logger != null) {
 // The named Logger exists. Now attempt to obtain its log level.
/*[IF Sidecar19-SE]*/
 Object level = logger_getLevel.invoke(logger);
/*[ELSE]					
				Level level = logger.getLevel();
/*[ENDIF]*/ 
 if (level != null) {
/*[IF Sidecar19-SE]*/
 result = (String)level_getName.invoke(level);
/*[ELSE]	
					result = level.getName();
/*[ENDIF]*/ 
				} else {
 // A null return from getLevel() means that the Logger
 // is inheriting its log level from an ancestor. Return an
 // empty string to the caller.
 result = ""; //$NON-NLS-1$
				}
			}
/*[IF Sidecar19-SE]*/
		} catch (Exception e) {
 throw handleError(e);
		}
/*[ENDIF]*/
 
 return result;
	}