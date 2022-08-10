 private void formatElement(IProgressMonitor monitor) {
 FormatProcessorXML formatProcessor = new FormatProcessorXML();
 formatProcessor.setProgressMonitor(monitor);
 formatProcessor.getFormatPreferences().setClearAllBlankLines(true);
 formatProcessor.formatModel(model);


 CleanupProcessorXML bla = new CleanupProcessorXML();
 bla.getCleanupPreferences().setCompressEmptyElementTags(true);
 bla.cleanupModel(model);
	}