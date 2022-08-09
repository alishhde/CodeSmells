 private Object getApplicationToRun(String[] args) throws CoreException {
 String configuredApplication = getConfiguredApplication(args);
 if (configuredApplication == null) {
 configuredApplication = DEFAULT_APP_3_0;
        } else {
 System.out.println("Launching application " + configuredApplication + "...");
        }


 // Assume we are in 3.0 mode.
 // Find the name of the application as specified by the PDE JUnit launcher.
 // If no application is specified, the 3.0 default workbench application
 // is returned.
 IExtension extension = Platform.getExtensionRegistry().getExtension(Platform.PI_RUNTIME,
 Platform.PT_APPLICATIONS, configuredApplication);


 // If no 3.0 extension can be found, search the registry
 // for the pre-3.0 default workbench application, i.e. org.eclipse ui.workbench
 // Set the deprecated flag to true
 if (extension == null) {
 return null;
        }


 // If the extension does not have the correct grammar, return null.
 // Otherwise, return the application object.
 IConfigurationElement[] elements = extension.getConfigurationElements();
 if (elements.length > 0) {
 IConfigurationElement[] runs = elements[0].getChildren("run"); //$NON-NLS-1$
 if (runs.length > 0) {
 return runs[0].createExecutableExtension("class"); //$NON-NLS-1$
            }
        }
 return null;
    }