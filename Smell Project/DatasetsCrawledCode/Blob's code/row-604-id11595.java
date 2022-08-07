 public static class Builder {


 final SystemModuleFinder systemModulePath;
 final Set<String> rootModules = new HashSet<>();
 final List<Archive> initialArchives = new ArrayList<>();
 final List<Path> paths = new ArrayList<>();
 final List<Path> classPaths = new ArrayList<>();


 ModuleFinder upgradeModulePath;
 ModuleFinder appModulePath;
 boolean addAllApplicationModules;
 boolean addAllDefaultModules;
 boolean addAllSystemModules;
 boolean allModules;
 Runtime.Version version;


 public Builder() {
 this.systemModulePath = new SystemModuleFinder();
        }


 public Builder(String javaHome) throws IOException {
 this.systemModulePath = SystemModuleFinder.JAVA_HOME.equals(javaHome)
                ? new SystemModuleFinder()
                : new SystemModuleFinder(javaHome);
        }


 public Builder upgradeModulePath(String upgradeModulePath) {
 this.upgradeModulePath = createModulePathFinder(upgradeModulePath);
 return this;
        }


 public Builder appModulePath(String modulePath) {
 this.appModulePath = createModulePathFinder(modulePath);
 return this;
        }


 public Builder addmods(Set<String> addmods) {
 for (String mn : addmods) {
 switch (mn) {
 case ALL_MODULE_PATH:
 this.addAllApplicationModules = true;
 break;
 case ALL_DEFAULT:
 this.addAllDefaultModules = true;
 break;
 case ALL_SYSTEM:
 this.addAllSystemModules = true;
 break;
 default:
 this.rootModules.add(mn);
                }
            }
 return this;
        }


 /*
         * This method is for --check option to find all target modules specified
         * in qualified exports.
         *
         * Include all system modules and modules found on modulepath
         */
 public Builder allModules() {
 this.allModules = true;
 return this;
        }


 public Builder multiRelease(Runtime.Version version) {
 this.version = version;
 return this;
        }


 public Builder addRoot(Path path) {
 Archive archive = Archive.getInstance(path, version);
 if (archive.contains(MODULE_INFO)) {
 paths.add(path);
            } else {
 initialArchives.add(archive);
            }
 return this;
        }


 public Builder addClassPath(String classPath) {
 this.classPaths.addAll(getClassPaths(classPath));
 return this;
        }


 public JdepsConfiguration build() throws IOException {
 ModuleFinder finder = systemModulePath;
 if (upgradeModulePath != null) {
 finder = ModuleFinder.compose(upgradeModulePath, systemModulePath);
            }
 if (appModulePath != null) {
 finder = ModuleFinder.compose(finder, appModulePath);
            }
 if (!paths.isEmpty()) {
 ModuleFinder otherModulePath = ModuleFinder.of(paths.toArray(new Path[0]));


 finder = ModuleFinder.compose(finder, otherModulePath);
 // add modules specified on command-line (convenience) as root set
 otherModulePath.findAll().stream()
                        .map(mref -> mref.descriptor().name())
                        .forEach(rootModules::add);
            }


 if ((addAllApplicationModules || allModules) && appModulePath != null) {
 appModulePath.findAll().stream()
                    .map(mref -> mref.descriptor().name())
                    .forEach(rootModules::add);
            }


 // no archive is specified for analysis
 // add all system modules as root if --add-modules ALL-SYSTEM is specified
 if (addAllSystemModules && rootModules.isEmpty() &&
 initialArchives.isEmpty() && classPaths.isEmpty()) {
 systemModulePath.findAll()
                    .stream()
                    .map(mref -> mref.descriptor().name())
                    .forEach(rootModules::add);
            }


 return new JdepsConfiguration(systemModulePath,
 finder,
 rootModules,
 classPaths,
 initialArchives,
 addAllDefaultModules,
 allModules,
 version);
        }


 private static ModuleFinder createModulePathFinder(String mpaths) {
 if (mpaths == null) {
 return null;
            } else {
 String[] dirs = mpaths.split(File.pathSeparator);
 Path[] paths = new Path[dirs.length];
 int i = 0;
 for (String dir : dirs) {
 paths[i++] = Paths.get(dir);
                }
 return ModuleFinder.of(paths);
            }
        }


 /*
         * Returns the list of Archive specified in cpaths and not included
         * initialArchives
         */
 private List<Path> getClassPaths(String cpaths) {
 if (cpaths.isEmpty()) {
 return Collections.emptyList();
            }
 List<Path> paths = new ArrayList<>();
 for (String p : cpaths.split(File.pathSeparator)) {
 if (p.length() > 0) {
 // wildcard to parse all JAR files e.g. -classpath dir/*
 int i = p.lastIndexOf(".*");
 if (i > 0) {
 Path dir = Paths.get(p.substring(0, i));
 try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.jar")) {
 for (Path entry : stream) {
 paths.add(entry);
                            }
                        } catch (IOException e) {
 throw new UncheckedIOException(e);
                        }
                    } else {
 paths.add(Paths.get(p));
                    }
                }
            }
 return paths;
        }
    }