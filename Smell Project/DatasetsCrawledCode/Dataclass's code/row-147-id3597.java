public interface ClusterServiceArtifactResponse {


 @ApiModelProperty(name = ArtifactResourceProvider.RESPONSE_KEY)
 @SuppressWarnings("unused")
 ClusterServiceArtifactResponseInfo getClusterServiceArtifactResponseInfo();


 @ApiModelProperty(name = ArtifactResourceProvider.ARTIFACT_DATA_PROPERTY)
 Map<String, Object> getArtifactData();


 interface ClusterServiceArtifactResponseInfo {
 @ApiModelProperty(name = ArtifactResourceProvider.ARTIFACT_NAME)
 String getArtifactName();


 @ApiModelProperty(name = ArtifactResourceProvider.CLUSTER_NAME)
 String getClusterName();


 @ApiModelProperty(name = ArtifactResourceProvider.SERVICE_NAME)
 String getServiceName();
  }


}