 private class ConfigFilePropertySource extends FilePropertySource {
 private IFile file;


 public ConfigFilePropertySource(IFile file) {
 super(file);
 this.file = file;
		}


 @Override
 public String toString() {
 return file.getFullPath().toString();
		}
	}