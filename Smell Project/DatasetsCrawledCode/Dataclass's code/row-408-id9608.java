public class EnsemblExonVO extends EnsemblDBBaseVO {


 @JsonProperty(value = "version")
 private Integer version;


 @JsonProperty(value = "assembly_name")
 private String assemblyName;


 @JsonProperty(value = "seq_region_name")
 private String seqRegionName;


 @JsonProperty(value = "strand")
 private String strand;


 public Integer getVersion() {
 return version;
    }


 public void setVersion(Integer version) {
 this.version = version;
    }


 public String getAssemblyName() {
 return assemblyName;
    }


 public void setAssemblyName(String assemblyName) {
 this.assemblyName = assemblyName;
    }


 public String getSeqRegionName() {
 return seqRegionName;
    }


 public void setSeqRegionName(String seqRegionName) {
 this.seqRegionName = seqRegionName;
    }


 public String getStrand() {
 return strand;
    }


 public void setStrand(String strand) {
 this.strand = strand;
    }
}