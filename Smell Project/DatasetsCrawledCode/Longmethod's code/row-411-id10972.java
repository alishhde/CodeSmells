 @Override
 public void setVersions(final VersionTag versionTag) {
 this.memberId = versionTag.getMemberID();
 int eVersion = versionTag.getEntryVersion();
 this.entryVersionLowBytes = (short) (eVersion & 0xffff);
 this.entryVersionHighByte = (byte) ((eVersion & 0xff0000) >> 16);
 this.regionVersionHighBytes = versionTag.getRegionVersionHighBytes();
 this.regionVersionLowBytes = versionTag.getRegionVersionLowBytes();
 if (!versionTag.isGatewayTag()
        && this.distributedSystemId == versionTag.getDistributedSystemId()) {
 if (getVersionTimeStamp() <= versionTag.getVersionTimeStamp()) {
 setVersionTimeStamp(versionTag.getVersionTimeStamp());
      } else {
 versionTag.setVersionTimeStamp(getVersionTimeStamp());
      }
    } else {
 setVersionTimeStamp(versionTag.getVersionTimeStamp());
    }
 this.distributedSystemId = (byte) (versionTag.getDistributedSystemId() & 0xff);
  }