 @SuppressWarnings("unchecked")
 private void extractDataAndSave(IBatchDAO batchDAO) {
 if (logger.isDebugEnabled()) {
 logger.debug("Extract data and save");
        }


 long startTime = System.currentTimeMillis();
 try {
 HistogramMetric.Timer timer = prepareLatency.createTimer();


 List batchAllCollection = new LinkedList();
 try {
 List<PersistenceWorker> persistenceWorkers = new ArrayList<>();
 persistenceWorkers.addAll(IndicatorProcess.INSTANCE.getPersistentWorkers());
 persistenceWorkers.addAll(RecordProcess.INSTANCE.getPersistentWorkers());
 persistenceWorkers.addAll(TopNProcess.INSTANCE.getPersistentWorkers());


 persistenceWorkers.forEach(worker -> {
 if (logger.isDebugEnabled()) {
 logger.debug("extract {} worker data and save", worker.getClass().getName());
                    }


 if (worker.flushAndSwitch()) {
 List<?> batchCollection = worker.buildBatchCollection();


 if (logger.isDebugEnabled()) {
 logger.debug("extract {} worker data size: {}", worker.getClass().getName(), batchCollection.size());
                        }
 batchAllCollection.addAll(batchCollection);
                    }
                });


 if (debug) {
 logger.info("build batch persistence duration: {} ms", System.currentTimeMillis() - startTime);
                }
            } finally {
 timer.finish();
            }


 HistogramMetric.Timer executeLatencyTimer = executeLatency.createTimer();
 try {
 batchDAO.batchPersistence(batchAllCollection);
            } finally {
 executeLatencyTimer.finish();
            }
        } catch (Throwable e) {
 errorCounter.inc();
 logger.error(e.getMessage(), e);
        } finally {
 if (logger.isDebugEnabled()) {
 logger.debug("persistence data save finish");
            }
        }


 if (debug) {
 logger.info("batch persistence duration: {} ms", System.currentTimeMillis() - startTime);
        }
    }