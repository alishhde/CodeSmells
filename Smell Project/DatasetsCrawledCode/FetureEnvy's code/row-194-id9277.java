 private void finishRestore(final Timer.Context context, Map<String, List<HoodieRollbackStat>> commitToStats,
 List<String> commitsToRollback, final String startRestoreTime, final String restoreToInstant) throws IOException {
 HoodieTable<T> table = HoodieTable.getHoodieTable(
 new HoodieTableMetaClient(jsc.hadoopConfiguration(), config.getBasePath(), true), config, jsc);
 Optional<Long> durationInMs = Optional.empty();
 Long numFilesDeleted = 0L;
 for (Map.Entry<String, List<HoodieRollbackStat>> commitToStat : commitToStats.entrySet()) {
 List<HoodieRollbackStat> stats = commitToStat.getValue();
 numFilesDeleted = stats.stream().mapToLong(stat -> stat.getSuccessDeleteFiles().size())
          .sum();
    }
 if (context != null) {
 durationInMs = Optional.of(metrics.getDurationInMs(context.stop()));
 metrics.updateRollbackMetrics(durationInMs.get(), numFilesDeleted);
    }
 HoodieRestoreMetadata restoreMetadata = AvroUtils
        .convertRestoreMetadata(startRestoreTime, durationInMs, commitsToRollback, commitToStats);
 table.getActiveTimeline().saveAsComplete(
 new HoodieInstant(true, HoodieTimeline.RESTORE_ACTION, startRestoreTime),
 AvroUtils.serializeRestoreMetadata(restoreMetadata));
 logger.info("Commits " + commitsToRollback + " rollback is complete. Restored dataset to " + restoreToInstant);


 if (!table.getActiveTimeline().getCleanerTimeline().empty()) {
 logger.info("Cleaning up older restore meta files");
 // Cleanup of older cleaner meta files
 // TODO - make the commit archival generic and archive rollback metadata
 FSUtils.deleteOlderRollbackMetaFiles(fs, table.getMetaClient().getMetaPath(),
 table.getActiveTimeline().getRestoreTimeline().getInstants());
    }
  }