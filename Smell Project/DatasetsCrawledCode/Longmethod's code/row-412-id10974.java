 BundleArchiveRevision(String location, File revisionDir, File file) throws IOException{
 this.revisionDir = revisionDir;
 this.location = location;
 if (!this.revisionDir.exists()) {
 this.revisionDir.mkdirs();
        }
 if(revisionDir.getAbsolutePath().startsWith(RuntimeVariables.androidApplication.getFilesDir().getAbsolutePath())){
 externalStorage = false;
        }else{
 externalStorage = true;
        }
 if(shouldCopyInstallFile(file)){
 if (isSameDriver(revisionDir, file)) {
 this.revisionLocation = FILE_PROTOCOL;
 this.bundleFile = new File(revisionDir, BUNDLE_FILE_NAME);
 boolean result = file.renameTo(bundleFile);
 if(!result){
 ApkUtils.copyInputStreamToFile(new FileInputStream(file), bundleFile);
                }
            } else {
 this.revisionLocation = FILE_PROTOCOL;
 this.bundleFile = new File(revisionDir, BUNDLE_FILE_NAME);
 ApkUtils.copyInputStreamToFile(new FileInputStream(file), bundleFile);
            }
 installSoLib(bundleFile);
        }else{
 this.revisionLocation = REFERENCE_PROTOCOL + file.getAbsolutePath();
 this.bundleFile = file;
 installSoLib(file);
        }
 updateMetadata();
    }