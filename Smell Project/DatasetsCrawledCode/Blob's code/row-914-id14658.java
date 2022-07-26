public abstract class AbstractClientProvider {


 public AbstractClientProvider() {
  }


 /**
   * Generates a fixed format of application tags given one or more of
   * application name, version and description. This allows subsequent query for
   * an application with a name only, version only or description only or any
   * combination of those as filters.
   *
   * @param appName name of the application
   * @param appVersion version of the application
   * @param appDescription brief description of the application
   * @return
   */
 public static final Set<String> createApplicationTags(String appName,
 String appVersion, String appDescription) {
 Set<String> tags = new HashSet<>();
 tags.add(ServiceUtils.createNameTag(appName));
 if (appVersion != null) {
 tags.add(ServiceUtils.createVersionTag(appVersion));
    }
 if (appDescription != null) {
 tags.add(ServiceUtils.createDescriptionTag(appDescription));
    }
 return tags;
  }


 /**
   * Validate the artifact.
   * @param artifact
   */
 public abstract void validateArtifact(Artifact artifact, String compName,
 FileSystem fileSystem) throws IOException;


 protected abstract void validateConfigFile(ConfigFile configFile,
 String compName, FileSystem fileSystem) throws IOException;


 /**
   * Validate the config files.
   * @param configFiles config file list
   * @param fs file system
   */
 public void validateConfigFiles(List<ConfigFile> configFiles, String compName,
 FileSystem fs) throws IOException {
 Set<String> destFileSet = new HashSet<>();


 for (ConfigFile file : configFiles) {
 if (file.getType() == null) {
 throw new IllegalArgumentException("File type is empty");
      }
 ConfigFile.TypeEnum fileType = file.getType();


 if (fileType.equals(ConfigFile.TypeEnum.TEMPLATE)) {
 if (StringUtils.isEmpty(file.getSrcFile()) &&
            !file.getProperties().containsKey(CONTENT)) {
 throw new IllegalArgumentException(MessageFormat.format("For {0} " +
 "format, either src_file must be specified in ConfigFile," +
 " or the \"{1}\" key must be specified in " +
 "the 'properties' field of ConfigFile. ",
 ConfigFile.TypeEnum.TEMPLATE, CONTENT));
        }
      } else if (fileType.equals(ConfigFile.TypeEnum.STATIC) || fileType.equals(
 ConfigFile.TypeEnum.ARCHIVE)) {
 if (!file.getProperties().isEmpty()) {
 throw new IllegalArgumentException(String
              .format("For %s format, should not specify any 'properties.'",
 fileType));
        }


 String srcFile = file.getSrcFile();
 if (srcFile == null || srcFile.isEmpty()) {
 throw new IllegalArgumentException(String.format(
 "For %s format, should make sure that srcFile is specified",
 fileType));
        }
 FileStatus fileStatus = fs.getFileStatus(new Path(srcFile));
 if (fileStatus != null && fileStatus.isDirectory()) {
 throw new IllegalArgumentException("srcFile=" + srcFile +
 " is a directory, which is not supported.");
        }
      }
 if (!StringUtils.isEmpty(file.getSrcFile())) {
 Path p = new Path(file.getSrcFile());
 if (!fs.exists(p)) {
 throw new IllegalArgumentException(
 "Specified src_file does not exist on " + fs.getScheme() + ": "
                  + file.getSrcFile());
        }
      }


 if (StringUtils.isEmpty(file.getDestFile())) {
 throw new IllegalArgumentException("dest_file is empty.");
      }


 if (destFileSet.contains(file.getDestFile())) {
 throw new IllegalArgumentException(
 "Duplicated ConfigFile exists: " + file.getDestFile());
      }
 destFileSet.add(file.getDestFile());


 java.nio.file.Path destPath = Paths.get(file.getDestFile());
 if (!destPath.isAbsolute() && destPath.getNameCount() > 1) {
 throw new IllegalArgumentException("Non-absolute dest_file has more " +
 "than one path element");
      }


 // provider-specific validation
 validateConfigFile(file, compName, fs);
    }
  }
}