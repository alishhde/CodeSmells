public class DeleteIndexAction implements ExpirationAction {


 private static final Logger logger = LoggerFactory.getLogger(DeleteIndexAction.class);
 private final PersistentProvenanceRepository repository;
 private final IndexConfiguration indexConfiguration;
 private final IndexManager indexManager;


 public DeleteIndexAction(final PersistentProvenanceRepository repo, final IndexConfiguration indexConfiguration, final IndexManager indexManager) {
 this.repository = repo;
 this.indexConfiguration = indexConfiguration;
 this.indexManager = indexManager;
    }


 @Override
 public File execute(final File expiredFile) throws IOException {
 // count the number of records and determine the max event id that we are deleting.
 final long numDeleted = 0;
 long maxEventId = -1L;
 try (final RecordReader reader = RecordReaders.newRecordReader(expiredFile, repository.getAllLogFiles(), Integer.MAX_VALUE)) {
 maxEventId = reader.getMaxEventId();
        } catch (final IOException ioe) {
 logger.warn("Failed to obtain max ID present in journal file {}", expiredFile.getAbsolutePath());
        }


 // remove the records from the index
 final List<File> indexDirs = indexConfiguration.getIndexDirectories(expiredFile);
 for (final File indexingDirectory : indexDirs) {
 final Term term = new Term(FieldNames.STORAGE_FILENAME, LuceneUtil.substringBefore(expiredFile.getName(), "."));


 boolean deleteDir = false;
 final EventIndexWriter writer = indexManager.borrowIndexWriter(indexingDirectory);
 try {
 final IndexWriter indexWriter = writer.getIndexWriter();
 indexWriter.deleteDocuments(term);
 indexWriter.commit();
 final int docsLeft = indexWriter.numDocs();
 deleteDir = docsLeft <= 0;
 logger.debug("After expiring {}, there are {} docs left for index {}", expiredFile, docsLeft, indexingDirectory);
            } finally {
 indexManager.returnIndexWriter(writer);
            }


 // we've confirmed that all documents have been removed. Delete the index directory.
 if (deleteDir) {
 indexManager.removeIndex(indexingDirectory);
 indexConfiguration.removeIndexDirectory(indexingDirectory);


 deleteDirectory(indexingDirectory);
 logger.info("Removed empty index directory {}", indexingDirectory);
            }
        }


 // Update the minimum index to 1 more than the max Event ID in this file.
 if (maxEventId > -1L) {
 indexConfiguration.setMinIdIndexed(maxEventId + 1L);
        }


 logger.info("Deleted Indices for Expired Provenance File {} from {} index files; {} documents removed", expiredFile, indexDirs.size(), numDeleted);
 return expiredFile;
    }


 private void deleteDirectory(final File dir) {
 if (dir == null || !dir.exists()) {
 return;
        }


 final File[] children = dir.listFiles();
 if (children == null) {
 return;
        }


 for (final File child : children) {
 if (child.isDirectory()) {
 deleteDirectory(child);
            } else if (!child.delete()) {
 logger.warn("Unable to remove index directory {}; this directory should be cleaned up manually", child.getAbsolutePath());
            }
        }


 if (!dir.delete()) {
 logger.warn("Unable to remove index directory {}; this directory should be cleaned up manually", dir);
        }
    }


 @Override
 public boolean hasBeenPerformed(final File expiredFile) throws IOException {
 return false;
    }
}