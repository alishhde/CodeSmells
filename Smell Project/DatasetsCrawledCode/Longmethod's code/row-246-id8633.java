 private void alterTableStatsForTruncate(RawStore ms, String catName, String dbName,
 String tableName, Table table, List<String> partNames,
 String validWriteIds, long writeId) throws Exception {
 if (partNames == null) {
 if (0 != table.getPartitionKeysSize()) {
 for (Partition partition : ms.getPartitions(catName, dbName, tableName, Integer.MAX_VALUE)) {
 alterPartitionForTruncate(ms, catName, dbName, tableName, table, partition,
 validWriteIds, writeId);
          }
        } else {
 EnvironmentContext environmentContext = new EnvironmentContext();
 updateStatsForTruncate(table.getParameters(), environmentContext);


 if (!transactionalListeners.isEmpty()) {
 MetaStoreListenerNotifier.notifyEvent(transactionalListeners,
 EventType.ALTER_TABLE,
 new AlterTableEvent(table, table, true, true,
 writeId, this));
          }


 if (!listeners.isEmpty()) {
 MetaStoreListenerNotifier.notifyEvent(listeners,
 EventType.ALTER_TABLE,
 new AlterTableEvent(table, table, true, true,
 writeId, this));
          }


 // TODO: this should actually pass thru and set writeId for txn stats.
 if (writeId > 0) {
 table.setWriteId(writeId);
          }
 alterHandler.alterTable(ms, wh, catName, dbName, tableName, table,
 environmentContext, this, validWriteIds);
        }
      } else {
 for (Partition partition : ms.getPartitionsByNames(catName, dbName, tableName, partNames)) {
 alterPartitionForTruncate(ms, catName, dbName, tableName, table, partition,
 validWriteIds, writeId);
        }
      }
 return;
    }