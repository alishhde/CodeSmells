@Service ("knownRepositoryContentConsumer#create-archiva-metadata")
@Scope ("prototype")
public class ArchivaMetadataCreationConsumer
 extends AbstractMonitoredConsumer
 implements KnownRepositoryContentConsumer, RegistryListener
{
 private String id = "create-archiva-metadata";


 private String description = "Create basic metadata for Archiva to be able to reference the artifact";


 @Inject
 private ArchivaConfiguration configuration;


 @Inject
 private FileTypes filetypes;


 private Date whenGathered;


 private List<String> includes = new ArrayList<>( 0 );


 /**
     * FIXME: this could be multiple implementations and needs to be configured.
     */
 @Inject
 private RepositorySessionFactory repositorySessionFactory;


 /**
     * FIXME: this needs to be configurable based on storage type - and could also be instantiated per repo. Change to a
     * factory.
     */
 @Inject
 @Named (value = "repositoryStorage#maven2")
 private RepositoryStorage repositoryStorage;


 private static final Logger log = LoggerFactory.getLogger( ArchivaMetadataCreationConsumer.class );


 private String repoId;


 @Override
 public String getId()
    {
 return this.id;
    }


 @Override
 public String getDescription()
    {
 return this.description;
    }


 @Override
 public List<String> getExcludes()
    {
 return getDefaultArtifactExclusions();
    }


 @Override
 public List<String> getIncludes()
    {
 return this.includes;
    }


 @Override
 public void beginScan( ManagedRepository repo, Date whenGathered )
 throws ConsumerException
    {
 repoId = repo.getId();
 this.whenGathered = whenGathered;
    }


 @Override
 public void beginScan( ManagedRepository repository, Date whenGathered, boolean executeOnEntireRepo )
 throws ConsumerException
    {
 beginScan( repository, whenGathered );
    }


 @Override
 public void processFile( String path )
 throws ConsumerException
    {


 RepositorySession repositorySession = repositorySessionFactory.createSession();
 try
        {
 // note that we do minimal processing including checksums and POM information for performance of
 // the initial scan. Any request for this information will be intercepted and populated on-demand
 // or picked up by subsequent scans


 ArtifactMetadata artifact = repositoryStorage.readArtifactMetadataFromPath( repoId, path );


 ProjectMetadata project = new ProjectMetadata();
 project.setNamespace( artifact.getNamespace() );
 project.setId( artifact.getProject() );


 String projectVersion = VersionUtil.getBaseVersion( artifact.getVersion() );


 MetadataRepository metadataRepository = repositorySession.getRepository();


 boolean createVersionMetadata = false;


 // FIXME: maybe not too efficient since it may have already been read and stored for this artifact
 ProjectVersionMetadata versionMetadata = null;
 try
            {
 ReadMetadataRequest readMetadataRequest =
 new ReadMetadataRequest().repositoryId( repoId ).namespace( artifact.getNamespace() ).projectId(
 artifact.getProject() ).projectVersion( projectVersion );
 versionMetadata = repositoryStorage.readProjectVersionMetadata( readMetadataRequest );
 createVersionMetadata = true;
            }
 catch ( RepositoryStorageMetadataNotFoundException e )
            {
 log.warn( "Missing or invalid POM for artifact:{} (repository:{}); creating empty metadata", path,
 repoId );


 versionMetadata = new ProjectVersionMetadata();
 versionMetadata.setId( projectVersion );
 versionMetadata.setIncomplete( true );
 createVersionMetadata = true;
            }
 catch ( RepositoryStorageMetadataInvalidException e )
            {
 log.warn( "Error occurred resolving POM for artifact:{} (repository:{}); message: {}",
 new Object[]{ path, repoId, e.getMessage() } );
            }


 // read the metadata and update it if it is newer or doesn't exist
 artifact.setWhenGathered( whenGathered );
 metadataRepository.updateArtifact( repoId, project.getNamespace(), project.getId(), projectVersion,
 artifact );
 if ( createVersionMetadata )
            {
 metadataRepository.updateProjectVersion( repoId, project.getNamespace(), project.getId(),
 versionMetadata );
            }
 metadataRepository.updateProject( repoId, project );
 repositorySession.save();
        }
 catch ( MetadataRepositoryException e )
        {
 log.warn(
 "Error occurred persisting metadata for artifact:{} (repository:{}); message: {}" ,
 path, repoId, e.getMessage(), e );
 repositorySession.revert();
        }
 catch ( RepositoryStorageRuntimeException e )
        {
 log.warn(
 "Error occurred persisting metadata for artifact:{} (repository:{}); message: {}",
 path, repoId, e.getMessage(), e );
 repositorySession.revert();
        }
 finally
        {
 repositorySession.close();
        }
    }


 @Override
 public void processFile( String path, boolean executeOnEntireRepo )
 throws ConsumerException
    {
 processFile( path );
    }


 @Override
 public void completeScan()
    {
 /* do nothing */
    }


 @Override
 public void completeScan( boolean executeOnEntireRepo )
    {
 completeScan();
    }


 @Override
 public void afterConfigurationChange( Registry registry, String propertyName, Object propertyValue )
    {
 if ( ConfigurationNames.isRepositoryScanning( propertyName ) )
        {
 initIncludes();
        }
    }


 @Override
 public void beforeConfigurationChange( Registry registry, String propertyName, Object propertyValue )
    {
 /* do nothing */
    }


 private void initIncludes()
    {
 includes = new ArrayList<String>( filetypes.getFileTypePatterns( FileTypes.ARTIFACTS ) );
    }


 @PostConstruct
 public void initialize()
    {
 configuration.addChangeListener( this );


 initIncludes();
    }
}