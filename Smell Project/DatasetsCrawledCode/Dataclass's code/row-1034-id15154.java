public class TemporaryFolderExtension implements ParameterResolver, BeforeEachCallback, AfterEachCallback {


 private TemporaryFolder temporaryFolder;


 @Override
 public void beforeEach(ExtensionContext context) throws Exception {
 temporaryFolder = new TemporaryFolder(Files.createTempDir());
    }


 @Override
 public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
 return (parameterContext.getParameter().getType() == TemporaryFolder.class);
    }


 @Override
 public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
 return temporaryFolder;
    }


 @Override
 public void afterEach(ExtensionContext context) throws Exception {
 FileUtils.deleteDirectory(temporaryFolder.getTempDir());
    }


 public TemporaryFolder getTemporaryFolder() {
 return temporaryFolder;
    }


 public static class TemporaryFolder {
 private final File tempDir;
 private final String folderPath;


 public TemporaryFolder(File tempDir) {
 this.tempDir = tempDir;
 this.folderPath = tempDir.getPath() + "/";
        }


 public File getTempDir() {
 return tempDir;
        }


 public String getFolderPath() {
 return folderPath;
        }
    }
}