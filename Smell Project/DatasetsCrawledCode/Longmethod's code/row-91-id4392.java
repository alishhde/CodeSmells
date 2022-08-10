 @SuppressWarnings("unchecked")
 protected Map<byte[], List<Path>>[] handleBulkLoad(List<TableName> sTableList)
 throws IOException {
 Map<byte[], List<Path>>[] mapForSrc = new Map[sTableList.size()];
 List<String> activeFiles = new ArrayList<>();
 List<String> archiveFiles = new ArrayList<>();
 Pair<Map<TableName, Map<String, Map<String, List<Pair<String, Boolean>>>>>, List<byte[]>> pair =
 backupManager.readBulkloadRows(sTableList);
 Map<TableName, Map<String, Map<String, List<Pair<String, Boolean>>>>> map = pair.getFirst();
 FileSystem tgtFs;
 try {
 tgtFs = FileSystem.get(new URI(backupInfo.getBackupRootDir()), conf);
    } catch (URISyntaxException use) {
 throw new IOException("Unable to get FileSystem", use);
    }
 Path rootdir = FSUtils.getRootDir(conf);
 Path tgtRoot = new Path(new Path(backupInfo.getBackupRootDir()), backupId);


 for (Map.Entry<TableName, Map<String, Map<String, List<Pair<String, Boolean>>>>> tblEntry :
 map.entrySet()) {
 TableName srcTable = tblEntry.getKey();


 int srcIdx = getIndex(srcTable, sTableList);
 if (srcIdx < 0) {
 LOG.warn("Couldn't find " + srcTable + " in source table List");
 continue;
      }
 if (mapForSrc[srcIdx] == null) {
 mapForSrc[srcIdx] = new TreeMap<>(Bytes.BYTES_COMPARATOR);
      }
 Path tblDir = FSUtils.getTableDir(rootdir, srcTable);
 Path tgtTable = new Path(new Path(tgtRoot, srcTable.getNamespaceAsString()),
 srcTable.getQualifierAsString());
 for (Map.Entry<String,Map<String,List<Pair<String, Boolean>>>> regionEntry :
 tblEntry.getValue().entrySet()){
 String regionName = regionEntry.getKey();
 Path regionDir = new Path(tblDir, regionName);
 // map from family to List of hfiles
 for (Map.Entry<String,List<Pair<String, Boolean>>> famEntry :
 regionEntry.getValue().entrySet()) {
 String fam = famEntry.getKey();
 Path famDir = new Path(regionDir, fam);
 List<Path> files;
 if (!mapForSrc[srcIdx].containsKey(Bytes.toBytes(fam))) {
 files = new ArrayList<>();
 mapForSrc[srcIdx].put(Bytes.toBytes(fam), files);
          } else {
 files = mapForSrc[srcIdx].get(Bytes.toBytes(fam));
          }
 Path archiveDir = HFileArchiveUtil.getStoreArchivePath(conf, srcTable, regionName, fam);
 String tblName = srcTable.getQualifierAsString();
 Path tgtFam = new Path(new Path(tgtTable, regionName), fam);
 if (!tgtFs.mkdirs(tgtFam)) {
 throw new IOException("couldn't create " + tgtFam);
          }
 for (Pair<String, Boolean> fileWithState : famEntry.getValue()) {
 String file = fileWithState.getFirst();
 int idx = file.lastIndexOf("/");
 String filename = file;
 if (idx > 0) {
 filename = file.substring(idx+1);
            }
 Path p = new Path(famDir, filename);
 Path tgt = new Path(tgtFam, filename);
 Path archive = new Path(archiveDir, filename);
 if (fs.exists(p)) {
 if (LOG.isTraceEnabled()) {
 LOG.trace("found bulk hfile " + file + " in " + famDir + " for " + tblName);
              }
 if (LOG.isTraceEnabled()) {
 LOG.trace("copying " + p + " to " + tgt);
              }
 activeFiles.add(p.toString());
            } else if (fs.exists(archive)){
 LOG.debug("copying archive " + archive + " to " + tgt);
 archiveFiles.add(archive.toString());
            }
 files.add(tgt);
          }
        }
      }
    }


 copyBulkLoadedFiles(activeFiles, archiveFiles);
 backupManager.deleteBulkLoadedRows(pair.getSecond());
 return mapForSrc;
  }