 public void crawl(File dirRoot) {
 LOG.info(String.format("Start crawling dir: %s", dirRoot));


 // Reset ingest status.a


 ingestStatus.clear();


 // Load actions.
 loadAndValidateActions();


 // Create Ingester.
 setupIngester();


 // Verify valid crawl directory.
 if (dirRoot == null || !dirRoot.exists()) {
 throw new IllegalArgumentException("dir root is null or non existant!");
      }


 // Start crawling.
 Stack<File> stack = new Stack<File>();
 stack.push(dirRoot.isDirectory() ? dirRoot : dirRoot.getParentFile());
 while (!stack.isEmpty()) {
 File dir = (File) stack.pop();
 LOG.log(Level.INFO, "Crawling " + dir);


 File[] productFiles;
 productFiles = isCrawlForDirs() ? dir.listFiles(DIR_FILTER) : dir.listFiles(FILE_FILTER);


 if(productFiles!=null) {
 for (File productFile : productFiles) {
 ingestStatus.add(handleFile(productFile));
            }
         }


 if (!isNoRecur()) {
 File[] subdirs = dir.listFiles(DIR_FILTER);
 if (subdirs != null) {
 for (File subdir : subdirs) {
 stack.push(subdir);
               }
            }
         }
      }


 LOG.info(String.format("Finished crawling dir: %s", dirRoot));
   }