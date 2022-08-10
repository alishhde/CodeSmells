 private void processInfoOutput(final BufferedReader stdout) {
 Matcher matcher;


 final MainControllerElement tempRoot = new MainControllerElement("Temporal root", this);
 readFullLineOnly(stdout);
 if (fastLine == null) {
 return;
		}
 matcher = MC_STATE_PATTERN.matcher(fastLine);
 if (matcher.matches()) {
 final String mcStateName = matcher.group(1);
 tempRoot.setStateInfo(new InformationElement("State: " + mcStateName));
 readFullLineOnly(stdout);


 suspectedLastState = getMCStateFromName(mcStateName);
		} else {
 fastLine = null;
 return;
		}
 if (fastLine != null && " host information:".equals(fastLine)) {
 readFullLineOnly(stdout);
		} else {
 fastLine = null;
 return;
		}
 if (fastLine != null) {
 if (fastLine.startsWith("  -")) {
 // host list
 while (fastLine != null && fastLine.startsWith("  -")) {
 processInfoOutputHC(stdout, tempRoot);
				}
			} else if ("  no HCs are connected".equals(fastLine)) {
 readFullLineOnly(stdout);
			}
		} else {
 fastLine = null;
 return;
		}


 if (fastLine != null && PAUSE_PATTERN.matcher(fastLine).matches()) {
 tempRoot.setPauseInfo(new InformationElement(fastLine.trim()));
 readFullLineOnly(stdout);
		} else {
 fastLine = null;
 return;
		}
 if (fastLine != null && CONSOLE_LOGGING_PATTERN.matcher(fastLine).matches()) {
 tempRoot.setConsoleLoggingInfo(new InformationElement(fastLine.trim()));
		} else {
 fastLine = null;
 return;
		}
 if (mainControllerRoot != null) {
 mainControllerRoot.children().clear();
 mainControllerRoot.transferData(tempRoot);
		}


	}