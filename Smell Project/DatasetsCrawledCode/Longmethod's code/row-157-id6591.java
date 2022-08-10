 public boolean executeSyncCmsId(NuageVspDeviceVO nuageVspDevice, SyncType syncType) {
 NuageVspDeviceVO matchingNuageVspDevice = findMatchingNuageVspDevice(nuageVspDevice);
 if (syncType == SyncType.REGISTER && matchingNuageVspDevice != null) {
 String cmsId = findNuageVspCmsIdForDeviceOrHost(matchingNuageVspDevice.getId(), matchingNuageVspDevice.getHostId());
 registerNewNuageVspDevice(nuageVspDevice.getHostId(), cmsId);
 return true;
        }


 String cmsId = findNuageVspCmsIdForDeviceOrHost(nuageVspDevice.getId(), nuageVspDevice.getHostId());


 SyncNuageVspCmsIdCommand syncCmd = new SyncNuageVspCmsIdCommand(syncType, cmsId);
 SyncNuageVspCmsIdAnswer answer = (SyncNuageVspCmsIdAnswer) _agentMgr.easySend(nuageVspDevice.getHostId(), syncCmd);
 if (answer != null) {
 if (answer.getSuccess()) {
 if (syncType == SyncType.REGISTER || answer.getSyncType() == SyncType.REGISTER) {
 registerNewNuageVspDevice(nuageVspDevice.getHostId(), answer.getNuageVspCmsId());
                } else if (syncType == SyncType.UNREGISTER) {
 removeLegacyNuageVspDeviceCmsId(nuageVspDevice.getId());
                }
            } else if (syncType == SyncType.AUDIT || syncType == SyncType.AUDIT_ONLY) {
 s_logger.fatal("Nuage VSP Device with ID " + nuageVspDevice.getId() + " is configured with an unknown CMS ID!");
            }
        }


 return answer != null && answer.getSuccess();
    }