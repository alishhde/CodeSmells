@Singleton
public class StandardComponentInitializer {


 public static final String NAVIGATE_TO_FILE = "navigateToFile";
 public static final String FULL_TEXT_SEARCH = "fullTextSearch";
 public static final String PREVIEW_IMAGE = "previewImage";
 public static final String FIND_ACTION = "findAction";
 public static final String FORMAT = "format";
 public static final String SAVE = "save";
 public static final String COPY = "copy";
 public static final String CUT = "cut";
 public static final String PASTE = "paste";
 public static final String UNDO = "undo";
 public static final String REDO = "redo";
 public static final String SWITCH_LEFT_TAB = "switchLeftTab";
 public static final String SWITCH_RIGHT_TAB = "switchRightTab";
 public static final String OPEN_RECENT_FILES = "openRecentFiles";
 public static final String DELETE_ITEM = "deleteItem";
 public static final String NEW_FILE = "newFile";
 public static final String CREATE_PROJECT = "createProject";
 public static final String IMPORT_PROJECT = "importProject";
 public static final String CLOSE_ACTIVE_EDITOR = "closeActiveEditor";
 public static final String SIGNATURE_HELP = "signatureHelp";
 public static final String SOFT_WRAP = "softWrap";
 public static final String RENAME = "renameResource";
 public static final String SHOW_REFERENCE = "showReference";
 public static final String SHOW_COMMANDS_PALETTE = "showCommandsPalette";
 public static final String NEW_TERMINAL = "newTerminal";
 public static final String OPEN_IN_TERMINAL = "openInTerminal";
 public static final String PROJECT_EXPLORER_DISPLAYING_MODE = "projectExplorerDisplayingMode";
 public static final String COMMAND_EXPLORER_DISPLAYING_MODE = "commandExplorerDisplayingMode";
 public static final String FIND_RESULT_DISPLAYING_MODE = "findResultDisplayingMode";
 public static final String EVENT_LOGS_DISPLAYING_MODE = "eventLogsDisplayingMode";
 public static final String EDITOR_DISPLAYING_MODE = "editorDisplayingMode";
 public static final String TERMINAL_DISPLAYING_MODE = "terminalDisplayingMode";
 public static final String REVEAL_RESOURCE = "revealResourceInProjectTree";
 public static final String COLLAPSE_ALL = "collapseAll";


 public interface ParserResource extends ClientBundle {
 @Source("org/eclipse/che/ide/blank.svg")
 SVGResource samplesCategoryBlank();
  }


 @Inject private EditorRegistry editorRegistry;


 @Inject private FileTypeRegistry fileTypeRegistry;


 @Inject private Resources resources;


 @Inject private KeyBindingAgent keyBinding;


 @Inject private ActionManager actionManager;


 @Inject private SaveAction saveAction;


 @Inject private SaveAllAction saveAllAction;


 @Inject private ShowPreferencesAction showPreferencesAction;


 @Inject private PreviewImageAction previewImageAction;


 @Inject private FindActionAction findActionAction;


 @Inject private NavigateToFileAction navigateToFileAction;


 @Inject @MainToolbar private ToolbarPresenter toolbarPresenter;


 @Inject private CutResourceAction cutResourceAction;


 @Inject private CopyResourceAction copyResourceAction;


 @Inject private PasteResourceAction pasteResourceAction;


 @Inject private DeleteResourceAction deleteResourceAction;


 @Inject private RenameItemAction renameItemAction;


 @Inject private SplitVerticallyAction splitVerticallyAction;


 @Inject private SplitHorizontallyAction splitHorizontallyAction;


 @Inject private CloseAction closeAction;


 @Inject private CloseAllAction closeAllAction;


 @Inject private CloseOtherAction closeOtherAction;


 @Inject private CloseAllExceptPinnedAction closeAllExceptPinnedAction;


 @Inject private ReopenClosedFileAction reopenClosedFileAction;


 @Inject private PinEditorTabAction pinEditorTabAction;


 @Inject private GoIntoAction goIntoAction;


 @Inject private EditFileAction editFileAction;


 @Inject private OpenFileAction openFileAction;


 @Inject private ShowHiddenFilesAction showHiddenFilesAction;


 @Inject private FormatterAction formatterAction;


 @Inject private UndoAction undoAction;


 @Inject private RedoAction redoAction;


 @Inject private UploadFileAction uploadFileAction;


 @Inject private UploadFolderAction uploadFolderAction;


 @Inject private DownloadProjectAction downloadProjectAction;


 @Inject private DownloadWsAction downloadWsAction;


 @Inject private DownloadResourceAction downloadResourceAction;


 @Inject private ImportProjectAction importProjectAction;


 @Inject private CreateProjectAction createProjectAction;


 @Inject private ConvertFolderToProjectAction convertFolderToProjectAction;


 @Inject private FullTextSearchAction fullTextSearchAction;


 @Inject private NewFolderAction newFolderAction;


 @Inject private NewFileAction newFileAction;


 @Inject private NewXmlFileAction newXmlFileAction;


 @Inject private ImageViewerProvider imageViewerProvider;


 @Inject private ProjectConfigurationAction projectConfigurationAction;


 @Inject private ExpandEditorAction expandEditorAction;


 @Inject private CompleteAction completeAction;


 @Inject private SwitchPreviousEditorAction switchPreviousEditorAction;


 @Inject private SwitchNextEditorAction switchNextEditorAction;


 @Inject private HotKeysListAction hotKeysListAction;


 @Inject private OpenRecentFilesAction openRecentFilesAction;


 @Inject private ClearRecentListAction clearRecentFilesAction;


 @Inject private CloseActiveEditorAction closeActiveEditorAction;


 @Inject private MessageLoaderResources messageLoaderResources;


 @Inject private EditorResources editorResources;


 @Inject private PopupResources popupResources;


 @Inject private ShowReferenceAction showReferenceAction;


 @Inject private RevealResourceAction revealResourceAction;


 @Inject private RefreshPathAction refreshPathAction;


 @Inject private LinkWithEditorAction linkWithEditorAction;


 @Inject private ShowToolbarAction showToolbarAction;


 @Inject private SignatureHelpAction signatureHelpAction;


 @Inject private MaximizePartAction maximizePartAction;


 @Inject private HidePartAction hidePartAction;


 @Inject private RestorePartAction restorePartAction;


 @Inject private ShowCommandsPaletteAction showCommandsPaletteAction;


 @Inject private SoftWrapAction softWrapAction;


 @Inject private StartWorkspaceAction startWorkspaceAction;


 @Inject private StopWorkspaceAction stopWorkspaceAction;


 @Inject private ShowWorkspaceStatusAction showWorkspaceStatusAction;


 @Inject private ShowRuntimeInfoAction showRuntimeInfoAction;


 @Inject private RunCommandAction runCommandAction;


 @Inject private NewTerminalAction newTerminalAction;


 @Inject private ReRunProcessAction reRunProcessAction;


 @Inject private StopProcessAction stopProcessAction;


 @Inject private CloseConsoleAction closeConsoleAction;


 @Inject private DisplayMachineOutputAction displayMachineOutputAction;


 @Inject private PreviewSSHAction previewSSHAction;


 @Inject private ShowConsoleTreeAction showConsoleTreeAction;


 @Inject private AddToFileWatcherExcludesAction addToFileWatcherExcludesAction;


 @Inject private RemoveFromFileWatcherExcludesAction removeFromFileWatcherExcludesAction;


 @Inject private DevModeSetUpAction devModeSetUpAction;


 @Inject private DevModeOffAction devModeOffAction;


 @Inject private CollapseAllAction collapseAllAction;


 @Inject private PerspectiveManager perspectiveManager;


 @Inject private CommandsExplorerDisplayingModeAction commandsExplorerDisplayingModeAction;


 @Inject private ProjectExplorerDisplayingModeAction projectExplorerDisplayingModeAction;


 @Inject private EventLogsDisplayingModeAction eventLogsDisplayingModeAction;


 @Inject private FindResultDisplayingModeAction findResultDisplayingModeAction;


 @Inject private EditorDisplayingModeAction editorDisplayingModeAction;


 @Inject private TerminalDisplayingModeAction terminalDisplayingModeAction;


 @Inject private RenameCommandAction renameCommandAction;


 @Inject private MoveCommandAction moveCommandAction;


 @Inject private OpenInTerminalAction openInTerminalAction;


 @Inject private FreeDiskSpaceStatusBarAction freeDiskSpaceStatusBarAction;


 @Inject
 @Named("XMLFileType")
 private FileType xmlFile;


 @Inject
 @Named("TXTFileType")
 private FileType txtFile;


 @Inject
 @Named("JsonFileType")
 private FileType jsonFile;


 @Inject
 @Named("MDFileType")
 private FileType mdFile;


 @Inject
 @Named("PNGFileType")
 private FileType pngFile;


 @Inject
 @Named("BMPFileType")
 private FileType bmpFile;


 @Inject
 @Named("GIFFileType")
 private FileType gifFile;


 @Inject
 @Named("ICOFileType")
 private FileType iconFile;


 @Inject
 @Named("SVGFileType")
 private FileType svgFile;


 @Inject
 @Named("JPEFileType")
 private FileType jpeFile;


 @Inject
 @Named("JPEGFileType")
 private FileType jpegFile;


 @Inject
 @Named("JPGFileType")
 private FileType jpgFile;


 @Inject private CommandEditorProvider commandEditorProvider;


 @Inject
 @Named("CommandFileType")
 private FileType commandFileType;


 @Inject private ProjectConfigSynchronized projectConfigSynchronized;


 @Inject private TreeResourceRevealer treeResourceRevealer; // just to work with it


 @Inject private TerminalInitializer terminalInitializer;


 /** Instantiates {@link StandardComponentInitializer} an creates standard content. */
 @Inject
 public StandardComponentInitializer(
 IconRegistry iconRegistry,
 MachineResources machineResources,
 StandardComponentInitializer.ParserResource parserResource) {
 iconRegistry.registerIcon(
 new Icon(BLANK_CATEGORY + ".samples.category.icon", parserResource.samplesCategoryBlank()));
 iconRegistry.registerIcon(new Icon("che.machine.icon", machineResources.devMachine()));
 machineResources.getCss().ensureInjected();
  }


 public void initialize() {
 messageLoaderResources.Css().ensureInjected();
 editorResources.editorCss().ensureInjected();
 popupResources.popupStyle().ensureInjected();


 fileTypeRegistry.registerFileType(xmlFile);


 fileTypeRegistry.registerFileType(txtFile);


 fileTypeRegistry.registerFileType(jsonFile);


 fileTypeRegistry.registerFileType(mdFile);


 fileTypeRegistry.registerFileType(pngFile);
 editorRegistry.registerDefaultEditor(pngFile, imageViewerProvider);


 fileTypeRegistry.registerFileType(bmpFile);
 editorRegistry.registerDefaultEditor(bmpFile, imageViewerProvider);


 fileTypeRegistry.registerFileType(gifFile);
 editorRegistry.registerDefaultEditor(gifFile, imageViewerProvider);


 fileTypeRegistry.registerFileType(iconFile);
 editorRegistry.registerDefaultEditor(iconFile, imageViewerProvider);


 fileTypeRegistry.registerFileType(svgFile);
 editorRegistry.registerDefaultEditor(svgFile, imageViewerProvider);


 fileTypeRegistry.registerFileType(jpeFile);
 editorRegistry.registerDefaultEditor(jpeFile, imageViewerProvider);


 fileTypeRegistry.registerFileType(jpegFile);
 editorRegistry.registerDefaultEditor(jpegFile, imageViewerProvider);


 fileTypeRegistry.registerFileType(jpgFile);
 editorRegistry.registerDefaultEditor(jpgFile, imageViewerProvider);


 fileTypeRegistry.registerFileType(commandFileType);
 editorRegistry.registerDefaultEditor(commandFileType, commandEditorProvider);


 // Workspace (New Menu)
 DefaultActionGroup workspaceGroup =
        (DefaultActionGroup) actionManager.getAction(GROUP_WORKSPACE);


 actionManager.registerAction(IMPORT_PROJECT, importProjectAction);
 workspaceGroup.add(importProjectAction);


 actionManager.registerAction(CREATE_PROJECT, createProjectAction);
 workspaceGroup.add(createProjectAction);


 actionManager.registerAction("downloadWsAsZipAction", downloadWsAction);
 workspaceGroup.add(downloadWsAction);


 workspaceGroup.addSeparator();
 workspaceGroup.add(startWorkspaceAction);
 workspaceGroup.add(stopWorkspaceAction);
 workspaceGroup.add(showWorkspaceStatusAction);


 // Project (New Menu)
 DefaultActionGroup projectGroup = (DefaultActionGroup) actionManager.getAction(GROUP_PROJECT);


 DefaultActionGroup newGroup = new DefaultActionGroup("New", true, actionManager);
 newGroup.getTemplatePresentation().setDescription("Create...");
 newGroup
        .getTemplatePresentation()
        .setImageElement(new SVGImage(resources.newResource()).getElement());
 actionManager.registerAction(GROUP_FILE_NEW, newGroup);
 projectGroup.add(newGroup);


 newGroup.addSeparator();


 actionManager.registerAction(NEW_FILE, newFileAction);
 newGroup.addAction(newFileAction, Constraints.FIRST);


 actionManager.registerAction("newFolder", newFolderAction);
 newGroup.addAction(newFolderAction, new Constraints(AFTER, NEW_FILE));


 newGroup.addSeparator();


 actionManager.registerAction("newXmlFile", newXmlFileAction);
 newXmlFileAction
        .getTemplatePresentation()
        .setImageElement(new SVGImage(xmlFile.getImage()).getElement());
 newGroup.addAction(newXmlFileAction);


 actionManager.registerAction("uploadFile", uploadFileAction);
 projectGroup.add(uploadFileAction);


 actionManager.registerAction("uploadFolder", uploadFolderAction);
 projectGroup.add(uploadFolderAction);


 actionManager.registerAction("convertFolderToProject", convertFolderToProjectAction);
 projectGroup.add(convertFolderToProjectAction);


 actionManager.registerAction("downloadAsZipAction", downloadProjectAction);
 projectGroup.add(downloadProjectAction);


 actionManager.registerAction("showHideHiddenFiles", showHiddenFilesAction);
 projectGroup.add(showHiddenFilesAction);


 projectGroup.addSeparator();


 actionManager.registerAction("projectConfiguration", projectConfigurationAction);
 projectGroup.add(projectConfigurationAction);


 DefaultActionGroup saveGroup = new DefaultActionGroup(actionManager);
 actionManager.registerAction("saveGroup", saveGroup);
 actionManager.registerAction(SAVE, saveAction);
 saveGroup.addSeparator();
 saveGroup.add(saveAction);


 // Edit (New Menu)
 DefaultActionGroup editGroup = (DefaultActionGroup) actionManager.getAction(GROUP_EDIT);
 DefaultActionGroup recentGroup = new DefaultActionGroup(RECENT_GROUP_ID, true, actionManager);
 actionManager.registerAction(GROUP_RECENT_FILES, recentGroup);
 actionManager.registerAction("clearRecentList", clearRecentFilesAction);
 recentGroup.addSeparator();
 recentGroup.add(clearRecentFilesAction, LAST);
 editGroup.add(recentGroup);
 actionManager.registerAction(OPEN_RECENT_FILES, openRecentFilesAction);
 editGroup.add(openRecentFilesAction);


 actionManager.registerAction(CLOSE_ACTIVE_EDITOR, closeActiveEditorAction);
 editGroup.add(closeActiveEditorAction);


 actionManager.registerAction(FORMAT, formatterAction);
 editGroup.add(formatterAction);


 editGroup.add(saveAction);


 actionManager.registerAction(UNDO, undoAction);
 editGroup.add(undoAction);


 actionManager.registerAction(REDO, redoAction);
 editGroup.add(redoAction);


 actionManager.registerAction(SOFT_WRAP, softWrapAction);
 editGroup.add(softWrapAction);


 actionManager.registerAction(CUT, cutResourceAction);
 editGroup.add(cutResourceAction);


 actionManager.registerAction(COPY, copyResourceAction);
 editGroup.add(copyResourceAction);


 actionManager.registerAction(PASTE, pasteResourceAction);
 editGroup.add(pasteResourceAction);


 actionManager.registerAction(RENAME, renameItemAction);
 editGroup.add(renameItemAction);


 actionManager.registerAction(DELETE_ITEM, deleteResourceAction);
 editGroup.add(deleteResourceAction);


 actionManager.registerAction(FULL_TEXT_SEARCH, fullTextSearchAction);
 editGroup.add(fullTextSearchAction);


 editGroup.addSeparator();
 editGroup.add(switchPreviousEditorAction);
 editGroup.add(switchNextEditorAction);


 // Assistant (New Menu)
 DefaultActionGroup assistantGroup =
        (DefaultActionGroup) actionManager.getAction(GROUP_ASSISTANT);


 actionManager.registerAction(PREVIEW_IMAGE, previewImageAction);
 assistantGroup.add(previewImageAction);


 actionManager.registerAction(FIND_ACTION, findActionAction);
 assistantGroup.add(findActionAction);


 actionManager.registerAction("hotKeysList", hotKeysListAction);
 assistantGroup.add(hotKeysListAction);


 assistantGroup.addSeparator();


 // Switching of parts
 DefaultActionGroup toolWindowsGroup =
 new DefaultActionGroup("Tool Windows", true, actionManager);
 actionManager.registerAction(TOOL_WINDOWS_GROUP, toolWindowsGroup);


 actionManager.registerAction(
 PROJECT_EXPLORER_DISPLAYING_MODE, projectExplorerDisplayingModeAction);
 actionManager.registerAction(FIND_RESULT_DISPLAYING_MODE, findResultDisplayingModeAction);
 actionManager.registerAction(EVENT_LOGS_DISPLAYING_MODE, eventLogsDisplayingModeAction);
 actionManager.registerAction(
 COMMAND_EXPLORER_DISPLAYING_MODE, commandsExplorerDisplayingModeAction);
 actionManager.registerAction(EDITOR_DISPLAYING_MODE, editorDisplayingModeAction);
 actionManager.registerAction(TERMINAL_DISPLAYING_MODE, terminalDisplayingModeAction);
 toolWindowsGroup.add(projectExplorerDisplayingModeAction, FIRST);
 toolWindowsGroup.add(
 eventLogsDisplayingModeAction, new Constraints(AFTER, PROJECT_EXPLORER_DISPLAYING_MODE));
 toolWindowsGroup.add(
 findResultDisplayingModeAction, new Constraints(AFTER, EVENT_LOGS_DISPLAYING_MODE));
 toolWindowsGroup.add(
 commandsExplorerDisplayingModeAction, new Constraints(AFTER, FIND_RESULT_DISPLAYING_MODE));
 toolWindowsGroup.add(editorDisplayingModeAction);
 toolWindowsGroup.add(terminalDisplayingModeAction);


 assistantGroup.add(toolWindowsGroup);
 assistantGroup.addSeparator();


 actionManager.registerAction("callCompletion", completeAction);
 assistantGroup.add(completeAction);


 actionManager.registerAction("downloadItemAction", downloadResourceAction);
 actionManager.registerAction(NAVIGATE_TO_FILE, navigateToFileAction);
 assistantGroup.add(navigateToFileAction);


 assistantGroup.addSeparator();
 actionManager.registerAction("devModeSetUpAction", devModeSetUpAction);
 actionManager.registerAction("devModeOffAction", devModeOffAction);
 assistantGroup.add(devModeSetUpAction);
 assistantGroup.add(devModeOffAction);


 // Compose Profile menu
 DefaultActionGroup profileGroup = (DefaultActionGroup) actionManager.getAction(GROUP_PROFILE);
 actionManager.registerAction("showPreferences", showPreferencesAction);


 profileGroup.add(showPreferencesAction);


 // Compose Help menu
 DefaultActionGroup helpGroup = (DefaultActionGroup) actionManager.getAction(GROUP_HELP);
 helpGroup.addSeparator();


 // Processes panel actions
 actionManager.registerAction("startWorkspace", startWorkspaceAction);
 actionManager.registerAction("stopWorkspace", stopWorkspaceAction);
 actionManager.registerAction("showWorkspaceStatus", showWorkspaceStatusAction);
 actionManager.registerAction("runCommand", runCommandAction);
 actionManager.registerAction("newTerminal", newTerminalAction);


 // Compose main context menu
 DefaultActionGroup resourceOperation = new DefaultActionGroup(actionManager);
 actionManager.registerAction("resourceOperation", resourceOperation);
 actionManager.registerAction("refreshPathAction", refreshPathAction);
 actionManager.registerAction("linkWithEditor", linkWithEditorAction);
 actionManager.registerAction("showToolbar", showToolbarAction);


 resourceOperation.addSeparator();
 resourceOperation.add(previewImageAction);
 resourceOperation.add(showReferenceAction);
 resourceOperation.add(goIntoAction);
 resourceOperation.add(editFileAction);


 resourceOperation.add(saveAction);
 resourceOperation.add(cutResourceAction);
 resourceOperation.add(copyResourceAction);
 resourceOperation.add(pasteResourceAction);
 resourceOperation.add(renameItemAction);
 resourceOperation.add(deleteResourceAction);
 resourceOperation.addSeparator();
 resourceOperation.add(downloadResourceAction);
 resourceOperation.add(refreshPathAction);
 resourceOperation.add(linkWithEditorAction);
 resourceOperation.add(collapseAllAction);
 resourceOperation.addSeparator();
 resourceOperation.add(convertFolderToProjectAction);
 resourceOperation.addSeparator();
 resourceOperation.addSeparator();
 resourceOperation.add(addToFileWatcherExcludesAction);
 resourceOperation.add(removeFromFileWatcherExcludesAction);
 resourceOperation.addSeparator();


 DefaultActionGroup mainContextMenuGroup =
        (DefaultActionGroup) actionManager.getAction(GROUP_MAIN_CONTEXT_MENU);
 mainContextMenuGroup.add(newGroup, FIRST);
 mainContextMenuGroup.addSeparator();
 mainContextMenuGroup.add(resourceOperation);
 mainContextMenuGroup.add(openInTerminalAction);
 actionManager.registerAction(OPEN_IN_TERMINAL, openInTerminalAction);


 DefaultActionGroup partMenuGroup =
        (DefaultActionGroup) actionManager.getAction(GROUP_PART_MENU);
 partMenuGroup.add(maximizePartAction);
 partMenuGroup.add(hidePartAction);
 partMenuGroup.add(restorePartAction);
 partMenuGroup.add(showConsoleTreeAction);
 partMenuGroup.add(revealResourceAction);
 partMenuGroup.add(collapseAllAction);
 partMenuGroup.add(refreshPathAction);
 partMenuGroup.add(linkWithEditorAction);


 DefaultActionGroup toolbarControllerGroup =
        (DefaultActionGroup) actionManager.getAction(GROUP_TOOLBAR_CONTROLLER);
 toolbarControllerGroup.add(showToolbarAction);


 actionManager.registerAction("expandEditor", expandEditorAction);
 DefaultActionGroup rightMenuGroup =
        (DefaultActionGroup) actionManager.getAction(GROUP_RIGHT_MAIN_MENU);
 rightMenuGroup.add(expandEditorAction, FIRST);


 // Compose main toolbar
 DefaultActionGroup changeResourceGroup = new DefaultActionGroup(actionManager);
 actionManager.registerAction("changeResourceGroup", changeResourceGroup);
 actionManager.registerAction("editFile", editFileAction);
 actionManager.registerAction("goInto", goIntoAction);
 actionManager.registerAction(SHOW_REFERENCE, showReferenceAction);


 actionManager.registerAction(REVEAL_RESOURCE, revealResourceAction);
 actionManager.registerAction(COLLAPSE_ALL, collapseAllAction);


 actionManager.registerAction("openFile", openFileAction);
 actionManager.registerAction(SWITCH_LEFT_TAB, switchPreviousEditorAction);
 actionManager.registerAction(SWITCH_RIGHT_TAB, switchNextEditorAction);


 changeResourceGroup.add(cutResourceAction);
 changeResourceGroup.add(copyResourceAction);
 changeResourceGroup.add(pasteResourceAction);
 changeResourceGroup.add(deleteResourceAction);


 DefaultActionGroup mainToolbarGroup =
        (DefaultActionGroup) actionManager.getAction(GROUP_MAIN_TOOLBAR);
 mainToolbarGroup.add(newGroup);
 mainToolbarGroup.add(saveGroup);
 mainToolbarGroup.add(changeResourceGroup);
 toolbarPresenter.bindMainGroup(mainToolbarGroup);


 DefaultActionGroup centerToolbarGroup =
        (DefaultActionGroup) actionManager.getAction(GROUP_CENTER_TOOLBAR);
 toolbarPresenter.bindCenterGroup(centerToolbarGroup);


 DefaultActionGroup rightToolbarGroup =
        (DefaultActionGroup) actionManager.getAction(GROUP_RIGHT_TOOLBAR);
 toolbarPresenter.bindRightGroup(rightToolbarGroup);


 actionManager.registerAction("showServers", showRuntimeInfoAction);


 // Consoles tree context menu group
 DefaultActionGroup consolesTreeContextMenu =
        (DefaultActionGroup) actionManager.getAction(GROUP_CONSOLES_TREE_CONTEXT_MENU);
 consolesTreeContextMenu.add(showRuntimeInfoAction);
 consolesTreeContextMenu.add(newTerminalAction);
 consolesTreeContextMenu.add(reRunProcessAction);
 consolesTreeContextMenu.add(stopProcessAction);
 consolesTreeContextMenu.add(closeConsoleAction);


 actionManager.registerAction("displayMachineOutput", displayMachineOutputAction);
 consolesTreeContextMenu.add(displayMachineOutputAction);


 actionManager.registerAction("previewSSH", previewSSHAction);
 consolesTreeContextMenu.add(previewSSHAction);


 // Editor context menu group
 DefaultActionGroup editorTabContextMenu =
        (DefaultActionGroup) actionManager.getAction(GROUP_EDITOR_TAB_CONTEXT_MENU);
 editorTabContextMenu.add(closeAction);
 actionManager.registerAction(CLOSE, closeAction);
 editorTabContextMenu.add(closeAllAction);
 actionManager.registerAction(CLOSE_ALL, closeAllAction);
 editorTabContextMenu.add(closeOtherAction);
 actionManager.registerAction(CLOSE_OTHER, closeOtherAction);
 editorTabContextMenu.add(closeAllExceptPinnedAction);
 actionManager.registerAction(CLOSE_ALL_EXCEPT_PINNED, closeAllExceptPinnedAction);
 editorTabContextMenu.addSeparator();
 editorTabContextMenu.add(reopenClosedFileAction);
 actionManager.registerAction(REOPEN_CLOSED, reopenClosedFileAction);
 editorTabContextMenu.add(pinEditorTabAction);
 actionManager.registerAction(PIN_TAB, pinEditorTabAction);
 editorTabContextMenu.addSeparator();
 actionManager.registerAction(SPLIT_HORIZONTALLY, splitHorizontallyAction);
 editorTabContextMenu.add(splitHorizontallyAction);
 actionManager.registerAction(SPLIT_VERTICALLY, splitVerticallyAction);
 editorTabContextMenu.add(splitVerticallyAction);
 actionManager.registerAction(SIGNATURE_HELP, signatureHelpAction);


 actionManager.registerAction(SHOW_COMMANDS_PALETTE, showCommandsPaletteAction);
 DefaultActionGroup runGroup =
        (DefaultActionGroup) actionManager.getAction(IdeActions.GROUP_RUN);
 runGroup.add(showCommandsPaletteAction);
 runGroup.add(newTerminalAction, FIRST);
 runGroup.addSeparator();


 DefaultActionGroup editorContextMenuGroup = new DefaultActionGroup(actionManager);
 actionManager.registerAction(GROUP_EDITOR_CONTEXT_MENU, editorContextMenuGroup);


 editorContextMenuGroup.add(saveAction);
 editorContextMenuGroup.add(undoAction);
 editorContextMenuGroup.add(redoAction);
 editorContextMenuGroup.addSeparator();
 editorContextMenuGroup.add(formatterAction);
 editorContextMenuGroup.add(softWrapAction);


 editorContextMenuGroup.addSeparator();
 editorContextMenuGroup.add(fullTextSearchAction);
 editorContextMenuGroup.add(closeActiveEditorAction);


 editorContextMenuGroup.addSeparator();
 editorContextMenuGroup.add(revealResourceAction);


 DefaultActionGroup commandExplorerMenuGroup = new DefaultActionGroup(actionManager);
 actionManager.registerAction(GROUP_COMMAND_EXPLORER_CONTEXT_MENU, commandExplorerMenuGroup);


 actionManager.registerAction("renameCommand", renameCommandAction);
 commandExplorerMenuGroup.add(renameCommandAction);
 actionManager.registerAction("moveCommand", moveCommandAction);
 commandExplorerMenuGroup.add(moveCommandAction);


 DefaultActionGroup rightStatusPanelGroup =
        (DefaultActionGroup) actionManager.getAction(GROUP_RIGHT_STATUS_PANEL);
 rightStatusPanelGroup.add(freeDiskSpaceStatusBarAction);


 // Define hot-keys
 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().action().alt().charCode('n').build(), NAVIGATE_TO_FILE);
 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().action().charCode('F').build(), FULL_TEXT_SEARCH);
 keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('A').build(), FIND_ACTION);
 keyBinding.getGlobal().addKey(new KeyBuilder().alt().charCode('L').build(), FORMAT);
 keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('c').build(), COPY);
 keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('x').build(), CUT);
 keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('v').build(), PASTE);
 keyBinding.getGlobal().addKey(new KeyBuilder().shift().charCode(KeyCodeMap.F6).build(), RENAME);
 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().shift().charCode(KeyCodeMap.F7).build(), SHOW_REFERENCE);
 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().alt().charCode(KeyCodeMap.ARROW_LEFT).build(), SWITCH_LEFT_TAB);
 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().alt().charCode(KeyCodeMap.ARROW_RIGHT).build(), SWITCH_RIGHT_TAB);
 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().action().charCode('e').build(), OPEN_RECENT_FILES);
 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().charCode(KeyCodeMap.DELETE).build(), DELETE_ITEM);
 keyBinding.getGlobal().addKey(new KeyBuilder().action().alt().charCode('w').build(), SOFT_WRAP);
 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().alt().charCode(KeyCodeMap.F12).build(), NEW_TERMINAL);
 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().alt().shift().charCode(KeyCodeMap.F12).build(), OPEN_IN_TERMINAL);


 keyBinding.getGlobal().addKey(new KeyBuilder().alt().charCode('N').build(), NEW_FILE);
 keyBinding.getGlobal().addKey(new KeyBuilder().alt().charCode('x').build(), CREATE_PROJECT);
 keyBinding.getGlobal().addKey(new KeyBuilder().alt().charCode('A').build(), IMPORT_PROJECT);


 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().shift().charCode(KeyCodeMap.F10).build(), SHOW_COMMANDS_PALETTE);


 keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('s').build(), SAVE);


 keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('z').build(), UNDO);
 keyBinding.getGlobal().addKey(new KeyBuilder().action().charCode('y').build(), REDO);


 if (UserAgent.isMac()) {
 keyBinding
          .getGlobal()
          .addKey(
 new KeyBuilder().action().control().charCode('1').build(),
 PROJECT_EXPLORER_DISPLAYING_MODE);


 keyBinding
          .getGlobal()
          .addKey(
 new KeyBuilder().action().control().charCode('2').build(),
 EVENT_LOGS_DISPLAYING_MODE);


 keyBinding
          .getGlobal()
          .addKey(
 new KeyBuilder().action().control().charCode('3').build(),
 FIND_RESULT_DISPLAYING_MODE);


 keyBinding
          .getGlobal()
          .addKey(
 new KeyBuilder().action().control().charCode('4').build(),
 COMMAND_EXPLORER_DISPLAYING_MODE);


 keyBinding
          .getGlobal()
          .addKey(new KeyBuilder().action().charCode('E').build(), EDITOR_DISPLAYING_MODE);


 keyBinding
          .getGlobal()
          .addKey(new KeyBuilder().action().charCode('T').build(), TERMINAL_DISPLAYING_MODE);
    } else {
 keyBinding
          .getGlobal()
          .addKey(
 new KeyBuilder().action().alt().charCode('1').build(),
 PROJECT_EXPLORER_DISPLAYING_MODE);


 keyBinding
          .getGlobal()
          .addKey(
 new KeyBuilder().action().alt().charCode('2').build(), EVENT_LOGS_DISPLAYING_MODE);


 keyBinding
          .getGlobal()
          .addKey(
 new KeyBuilder().action().alt().charCode('3').build(), FIND_RESULT_DISPLAYING_MODE);


 keyBinding
          .getGlobal()
          .addKey(
 new KeyBuilder().action().alt().charCode('4').build(),
 COMMAND_EXPLORER_DISPLAYING_MODE);


 keyBinding
          .getGlobal()
          .addKey(new KeyBuilder().alt().charCode('E').build(), EDITOR_DISPLAYING_MODE);


 keyBinding
          .getGlobal()
          .addKey(new KeyBuilder().alt().charCode('T').build(), TERMINAL_DISPLAYING_MODE);
    }


 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().action().charCode(ARROW_DOWN).build(), REVEAL_RESOURCE);
 keyBinding
        .getGlobal()
        .addKey(new KeyBuilder().action().charCode(ARROW_UP).build(), COLLAPSE_ALL);


 if (UserAgent.isMac()) {
 keyBinding
          .getGlobal()
          .addKey(new KeyBuilder().control().charCode('w').build(), CLOSE_ACTIVE_EDITOR);
 keyBinding
          .getGlobal()
          .addKey(new KeyBuilder().control().charCode('p').build(), SIGNATURE_HELP);
    } else {
 keyBinding
          .getGlobal()
          .addKey(new KeyBuilder().alt().charCode('w').build(), CLOSE_ACTIVE_EDITOR);
 keyBinding
          .getGlobal()
          .addKey(new KeyBuilder().action().charCode('p').build(), SIGNATURE_HELP);
    }


 final Map<String, Perspective> perspectives = perspectiveManager.getPerspectives();
 if (perspectives.size()
        > 1) { // if registered perspectives will be more then 2 Main Menu -> Window
 // will appears and contains all of them as sub-menu
 final DefaultActionGroup windowMenu = new DefaultActionGroup("Window", true, actionManager);
 actionManager.registerAction("Window", windowMenu);
 final DefaultActionGroup mainMenu =
          (DefaultActionGroup) actionManager.getAction(GROUP_MAIN_MENU);
 mainMenu.add(windowMenu);
 for (Perspective perspective : perspectives.values()) {
 final BaseAction action =
 new BaseAction(perspective.getPerspectiveName()) {
 @Override
 public void actionPerformed(ActionEvent e) {
 perspectiveManager.setPerspectiveId(perspective.getPerspectiveId());
              }
            };
 actionManager.registerAction(perspective.getPerspectiveId(), action);
 windowMenu.add(action);
      }
    }
  }
}