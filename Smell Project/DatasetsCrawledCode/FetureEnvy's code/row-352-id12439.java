 @Override
 public short syncAll(short syncMode) {
 short syncState = SYNC_STATE_IN_SYNC;


 /* vnc classes need to be synchronized with cloudstack */
 s_logger.debug("syncing cloudstack db with vnc");
 try {
 for (Class<?> cls : _vncClasses) {


 /* lock the sync mode*/
 _lockSyncMode.lock();
 _rwMode = syncMode == DBSyncGeneric.SYNC_MODE_UPDATE;
 _dbSync.setSyncMode(syncMode);


 if (_dbSync.getSyncMode() == DBSyncGeneric.SYNC_MODE_CHECK) {
 s_logger.debug("sync check start: " + DBSyncGeneric.getClassName(cls));
                } else {
 s_logger.debug("sync start: " + DBSyncGeneric.getClassName(cls));
                }


 if (_dbSync.sync(cls) == false) {
 if (_dbSync.getSyncMode() == DBSyncGeneric.SYNC_MODE_CHECK) {
 s_logger.info("out of sync detected: " + DBSyncGeneric.getClassName(cls));
                    } else {
 s_logger.info("out of sync detected and re-synced: " + DBSyncGeneric.getClassName(cls));
                    }
 syncState = SYNC_STATE_OUT_OF_SYNC;
                }
 if (_dbSync.getSyncMode() == DBSyncGeneric.SYNC_MODE_CHECK) {
 s_logger.debug("sync check finish: " + DBSyncGeneric.getClassName(cls));
                } else {
 s_logger.debug("sync finish: " + DBSyncGeneric.getClassName(cls));
                }
 /* unlock the sync mode */
 _lockSyncMode.unlock();
            }
        } catch (Exception ex) {
 s_logger.warn("DB Synchronization", ex);
 syncState = SYNC_STATE_UNKNOWN;
 if (_lockSyncMode.isLocked()) {
 _lockSyncMode.unlock();
            }
        }


 return syncState;
    }