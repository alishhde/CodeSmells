@XmlRootElement( name = "artifactTransferRequest" )
public class ArtifactTransferRequest
 extends Artifact
 implements Serializable
{
 private String targetRepositoryId;


 public String getTargetRepositoryId()
    {
 return targetRepositoryId;
    }


 public void setTargetRepositoryId( String targetRepositoryId )
    {
 this.targetRepositoryId = targetRepositoryId;
    }
}