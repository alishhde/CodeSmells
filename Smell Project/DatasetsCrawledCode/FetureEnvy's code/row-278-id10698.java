 long recoverDrf(OplogEntryIdSet deletedIds, boolean alreadyRecoveredOnce, boolean latestOplog) {
 File drfFile = this.drf.f;
 if (drfFile == null) {
 this.haveRecoveredDrf = true;
 return 0L;
    }
 lockCompactor();
 try {
 if (this.haveRecoveredDrf && !getHasDeletes())
 return 0L; // do this while holding lock
 if (!this.haveRecoveredDrf) {
 this.haveRecoveredDrf = true;
      }
 logger.info("Recovering {} {} for disk store {}.",
 new Object[] {toString(), drfFile.getAbsolutePath(), getParent().getName()});
 this.recoverDelEntryId = DiskStoreImpl.INVALID_ID;
 boolean readLastRecord = true;
 CountingDataInputStream dis = null;
 try {
 int recordCount = 0;
 boolean foundDiskStoreRecord = false;
 FileInputStream fis = null;
 try {
 fis = new FileInputStream(drfFile);
 dis = new CountingDataInputStream(new BufferedInputStream(fis, 32 * 1024),
 drfFile.length());
 boolean endOfLog = false;
 while (!endOfLog) {
 if (dis.atEndOfFile()) {
 endOfLog = true;
 break;
            }
 readLastRecord = false;
 byte opCode = dis.readByte();
 if (logger.isTraceEnabled(LogMarker.PERSIST_RECOVERY_VERBOSE)) {
 logger.trace(LogMarker.PERSIST_RECOVERY_VERBOSE, "drf byte={} location={}", opCode,
 Long.toHexString(dis.getCount()));
            }
 switch (opCode) {
 case OPLOG_EOF_ID:
 // we are at the end of the oplog. So we need to back up one byte
 dis.decrementCount();
 endOfLog = true;
 break;
 case OPLOG_DEL_ENTRY_1ID:
 case OPLOG_DEL_ENTRY_2ID:
 case OPLOG_DEL_ENTRY_3ID:
 case OPLOG_DEL_ENTRY_4ID:
 case OPLOG_DEL_ENTRY_5ID:
 case OPLOG_DEL_ENTRY_6ID:
 case OPLOG_DEL_ENTRY_7ID:
 case OPLOG_DEL_ENTRY_8ID:
 readDelEntry(dis, opCode, deletedIds, parent);
 recordCount++;
 break;
 case OPLOG_DISK_STORE_ID:
 readDiskStoreRecord(dis, this.drf.f);
 foundDiskStoreRecord = true;
 recordCount++;
 break;
 case OPLOG_MAGIC_SEQ_ID:
 readOplogMagicSeqRecord(dis, this.drf.f, OPLOG_TYPE.DRF);
 break;
 case OPLOG_GEMFIRE_VERSION:
 readGemfireVersionRecord(dis, this.drf.f);
 recordCount++;
 break;


 case OPLOG_RVV:
 long idx = dis.getCount();
 readRVVRecord(dis, this.drf.f, true, latestOplog);
 recordCount++;
 break;


 default:
 throw new DiskAccessException(
 String.format("Unknown opCode %s found in disk operation log.",
 opCode),
 getParent());
            }
 readLastRecord = true;
 // @todo
 // if (rgn.isDestroyed()) {
 // break;
 // }
          } // while
        } finally {
 if (dis != null) {
 dis.close();
          }
 if (fis != null) {
 fis.close();
          }
        }
 if (!foundDiskStoreRecord && recordCount > 0) {
 throw new DiskAccessException(
 "The oplog file \"" + this.drf.f + "\" does not belong to the init file \""
                  + getParent().getInitFile() + "\". Drf did not contain a disk store id.",
 getParent());
        }
      } catch (EOFException ignore) {
 // ignore since a partial record write can be caused by a crash
      } catch (IOException ex) {
 getParent().getCancelCriterion().checkCancelInProgress(ex);
 throw new DiskAccessException(
 String.format("Failed to read file during recovery from %s",
 drfFile.getPath()),
 ex, getParent());
      } catch (CancelException e) {
 if (logger.isDebugEnabled()) {
 logger.debug("Oplog::readOplog:Error in recovery as Cache was closed", e);
        }
      } catch (RegionDestroyedException e) {
 if (logger.isDebugEnabled()) {
 logger.debug("Oplog::readOplog:Error in recovery as Region was destroyed", e);
        }
      }
 // Add the Oplog size to the Directory Holder which owns this oplog,
 // so that available space is correctly calculated & stats updated.
 long byteCount = 0;
 if (!readLastRecord) {
 // this means that there was a crash
 // and hence we should not continue to read
 // the next oplog
 this.crashed = true;
 if (dis != null) {
 byteCount = dis.getFileLength();
        }
      } else {
 if (dis != null) {
 byteCount = dis.getCount();
        }
      }
 if (!alreadyRecoveredOnce) {
 setRecoveredDrfSize(byteCount);
 this.dirHolder.incrementTotalOplogSize(byteCount);
      }
 return byteCount;
    } finally {
 unlockCompactor();
    }
  }