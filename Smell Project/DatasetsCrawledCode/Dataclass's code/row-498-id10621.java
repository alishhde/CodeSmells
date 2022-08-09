 class ColumnFamilyRegionInfo {
 private RegionInfo regionInfo;
 private Set<byte[]> familySet;


 public ColumnFamilyRegionInfo(RegionInfo regionInfo, Set<byte[]> familySet) {
 this.regionInfo = regionInfo;
 this.familySet = familySet;
        }


 public RegionInfo getRegionInfo() {
 return regionInfo;
        }


 public Set<byte[]> getFamilySet() {
 return familySet;
        }


 @Override
 public boolean equals(Object obj) {
 if (obj == this) { return true; }
 if (!(obj instanceof ColumnFamilyRegionInfo)) { return false; }


 ColumnFamilyRegionInfo c = (ColumnFamilyRegionInfo)obj;
 return c.getRegionInfo().equals(this.regionInfo) && ByteUtil.match(this.familySet, c.getFamilySet());
        }


 @Override
 public int hashCode() {
 return this.getRegionInfo().hashCode();
        }
    }