 public void resetApplications() {
 String jobPath = zkRoot + "/jobs";
 InterProcessLock lock = new InterProcessReadWriteLock(curator,jobPath).writeLock();
 try {
 lock.acquire();
            (curator.getChildren().forPath(jobPath)).forEach(appId -> {
 String path = jobPath + "/" + appId;
 try {
 if (curator.checkExists().forPath(path) != null) {
 String status = new String(curator.getData().forPath(path));
 if (!ZKStateConstant.AppStatus.INIT.toString().equals(status)) {
 curator.setData().forPath(path, ZKStateConstant.AppStatus.INIT.toString().getBytes("UTF-8"));
                        }
                    }
                } catch (Exception e) {
 LOG.error("fail to read unprocessed job", e);
 throw new RuntimeException(e);
                }
            });


        } catch (Exception e) {
 LOG.error("fail to read unprocessed jobs", e);
 throw new RuntimeException(e);
        } finally {
 try {
 lock.release();
            } catch (Exception e) {
 LOG.error("fail to release lock", e);
            }
        }
    }