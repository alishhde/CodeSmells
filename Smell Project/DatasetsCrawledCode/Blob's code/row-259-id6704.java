public class N4JSNewProjectWizard extends XtextNewProjectWizard {


 private static final ImageDescriptor NEW_PROJECT_WIZBAN_DESC = ImageRef.NEW_PROJECT_WIZBAN.asImageDescriptor()
			.orNull();


 private static final String DIALOG_SETTINGS_SECTION_KEY = "org.eclipse.n4js.ui.wizard.project.N4JSNewProjectWizard.dialogSettings";
 private static final String CREATE_GREETER_SETTINGS_KEY = "createGreeterFile";
 private static final String VENDOR_ID_SETTINGS_KEY = "vendorId";


 private final N4JSProjectInfo projectInfo;


 @Inject
 private IResourceDescriptions resourceDescriptions;


 private N4JSNewProjectWizardCreationPage newProjectWizardCreationPage;


 /**
	 * Creates a new wizard container for creating and initializing a new N4JS project into the workspace.
	 *
	 * @param projectCreator
	 *            the project creation logic to be triggered when finishing this wizard.
	 */
 @Inject
 public N4JSNewProjectWizard(final IProjectCreator projectCreator) {
 super(projectCreator);
 setWindowTitle("New N4JS Project");
 setNeedsProgressMonitor(true);
 setDefaultPageImageDescriptor(NEW_PROJECT_WIZBAN_DESC);
 projectInfo = new N4JSProjectInfo();


 // Setup the dialog settings
 IDialogSettings workbenchDialogSettings = N4JSActivator.getInstance().getDialogSettings();


 IDialogSettings projectWizardSettings = workbenchDialogSettings.getSection(DIALOG_SETTINGS_SECTION_KEY);
 if (null == projectWizardSettings) {
 projectWizardSettings = workbenchDialogSettings.addNewSection(DIALOG_SETTINGS_SECTION_KEY);
		}
 setDialogSettings(projectWizardSettings);
	}


 @Override
 public void createPageControls(Composite pageContainer) {
 super.createPageControls(pageContainer);


 IDialogSettings dialogSettings = this.getDialogSettings();


 if (null != dialogSettings.get(CREATE_GREETER_SETTINGS_KEY)) {
 projectInfo.setCreateGreeterFile(dialogSettings.getBoolean(CREATE_GREETER_SETTINGS_KEY));
		}
 if (null != dialogSettings.get(VENDOR_ID_SETTINGS_KEY)) {
 projectInfo.setVendorId(dialogSettings.get(VENDOR_ID_SETTINGS_KEY));
		}
	}


 @Override
 public void addPages() {
 newProjectWizardCreationPage = new N4JSNewProjectWizardCreationPage(projectInfo);
 addPage(newProjectWizardCreationPage);
 addPage(new N4JSTestedProjectWizardPage(projectInfo, resourceDescriptions));
	}


 @Override
 public boolean performFinish() {
 // Save the value for the create greeter file checkbox and the vendor id
 this.getDialogSettings().put(CREATE_GREETER_SETTINGS_KEY, projectInfo.getCreateGreeterFile());
 this.getDialogSettings().put(VENDOR_ID_SETTINGS_KEY, projectInfo.getVendorId());
 return super.performFinish();
	}


 @Override
 public boolean canFinish() {
 /*
		 * Can finish after first page for non-test projects or like normally if all pages are complete for test
		 * projects
		 *
		 * This means that even for test projects the whole second page can be completely skipped.
		 */
 return (!ProjectType.TEST.equals(projectInfo.getProjectType()) &&
 newProjectWizardCreationPage.isPageComplete()) || super.canFinish();
	}


 @Override
 protected IProjectInfo getProjectInfo() {
 // update workingsets:
 newProjectWizardCreationPage.updateSelectedWorkingSets();


 return projectInfo;
	}
}