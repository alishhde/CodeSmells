 private class PutFileAction implements SshAction<Void> {
 // TODO support backup as a property?
 
 private SFTPClient sftp;
 private final String path;
 private final int permissionsMask;
 private final long lastModificationDate;
 private final long lastAccessDate;
 private final int uid;
 private final Supplier<InputStream> contentsSupplier;
 private final Integer length;
 
 PutFileAction(Map<String,?> props, String path, Supplier<InputStream> contentsSupplier, long length) {
 String permissions = getOptionalVal(props, PROP_PERMISSIONS);
 long lastModificationDateVal = getOptionalVal(props, PROP_LAST_MODIFICATION_DATE);
 long lastAccessDateVal = getOptionalVal(props, PROP_LAST_ACCESS_DATE);
 if (lastAccessDateVal <= 0 ^ lastModificationDateVal <= 0) {
 lastAccessDateVal = Math.max(lastAccessDateVal, lastModificationDateVal);
 lastModificationDateVal = Math.max(lastAccessDateVal, lastModificationDateVal);
            }
 this.permissionsMask = Integer.parseInt(permissions, 8);
 this.lastAccessDate = lastAccessDateVal;
 this.lastModificationDate = lastModificationDateVal;
 this.uid = getOptionalVal(props, PROP_OWNER_UID);
 this.path = checkNotNull(path, "path");
 this.contentsSupplier = checkNotNull(contentsSupplier, "contents");
 this.length = Ints.checkedCast(checkNotNull((long)length, "size"));
        }


 @Override
 public void clear() {
 closeWhispering(sftp, this);
 sftp = null;
        }


 @Override
 public Void create() throws Exception {
 final AtomicReference<InputStream> inputStreamRef = new AtomicReference<InputStream>();
 sftp = acquire(sftpConnection);
 try {
 sftp.put(new InMemorySourceFile() {
 @Override public String getName() {
 return path;
                    }
 @Override public long getLength() {
 return length;
                    }
 @Override public InputStream getInputStream() throws IOException {
 InputStream contents = contentsSupplier.get();
 inputStreamRef.set(contents);
 return contents;
                    }
                }, path);
 sftp.chmod(path, permissionsMask);
 if (uid != -1) {
 sftp.chown(path, uid);
                }
 if (lastAccessDate > 0) {
 sftp.setattr(path, new FileAttributes.Builder()
                            .withAtimeMtime(lastAccessDate, lastModificationDate)
                            .build());
                }
            } finally {
 closeWhispering(inputStreamRef.get(), this);
            }
 return null;
        }


 @Override
 public String toString() {
 return "Put(path=[" + path + " "+length+"])";
        }
    }