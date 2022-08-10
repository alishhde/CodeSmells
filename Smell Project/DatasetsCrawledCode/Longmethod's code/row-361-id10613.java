 public static void copyDirectory(final File srcDir, final File destDir) throws IOException {
 if (srcDir == null) {
 throw new NullPointerException("Source must not be null");
        }
 if (destDir == null) {
 throw new NullPointerException("Destination must not be null");
        }
 if (!srcDir.exists()) {
 throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
        }
 if (!srcDir.isDirectory()) {
 throw new IOException("Source '" + srcDir + "' exists but is not a directory");
        }
 if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
 throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
        }


 // Cater for destination being directory within the source directory (see IO-141)
 List<String> exclusionList = null;
 if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
 final File[] srcFiles = srcDir.listFiles();
 if (srcFiles != null && srcFiles.length > 0) {
 exclusionList = new ArrayList<>(srcFiles.length);
 for (final File srcFile : srcFiles) {
 final File copiedFile = new File(destDir, srcFile.getName());
 exclusionList.add(copiedFile.getCanonicalPath());
                }
            }
        }
 doCopyDirectory(srcDir, destDir, exclusionList);
    }