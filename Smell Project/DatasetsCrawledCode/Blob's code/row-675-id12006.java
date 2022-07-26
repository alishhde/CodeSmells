@Component("org.apache.usergrid.rest.management.organizations.applications.ApplicationResource")
@Scope("prototype")
@Produces({
 MediaType.APPLICATION_JSON,
 "application/javascript",
 "application/x-javascript",
 "text/ecmascript",
 "application/ecmascript",
 "text/jscript"
})
public class ApplicationResource extends AbstractContextResource {


 private static final Logger logger = LoggerFactory.getLogger(ApplicationResource.class);


 public static final String CONFIRM_APPLICATION_IDENTIFIER = "confirm_application_identifier";
 public static final String RESTORE_PASSWORD = "restore_password";


 //@Autowired
 //protected ExportService exportService;


 OrganizationInfo organization;
 UUID applicationId;
 ApplicationInfo application;


 @Autowired
 private SignInProviderFactory signInProviderFactory;




 public ApplicationResource() {
    }




 public ApplicationResource init( OrganizationInfo organization, UUID applicationId ) {
 this.organization = organization;
 this.applicationId = applicationId;
 return this;
    }




 public ApplicationResource init( OrganizationInfo organization, ApplicationInfo application ) {
 this.organization = organization;
 applicationId = application.getId();
 this.application = application;
 return this;
    }






 @RequireOrganizationAccess
 @GET
 @JSONP
 @Produces({MediaType.APPLICATION_JSON, "application/javascript"})
 public ApiResponse getApplication(
 @Context UriInfo ui, @QueryParam("callback") @DefaultValue("callback") String callback )
 throws Exception {


 ApiResponse response = createApiResponse();
 ServiceManager sm = smf.getServiceManager( applicationId );
 response.setAction( "get" );
 response.setApplication( sm.getApplication() );
 response.setParams( ui.getQueryParameters() );
 response.setResults( management.getApplicationMetadata( applicationId ) );
 return response;
    }




 @RequireOrganizationAccess
 @GET
 @Path("credentials")
 @JSONP
 @Produces({MediaType.APPLICATION_JSON, "application/javascript"})
 public ApiResponse getCredentials(
 @Context UriInfo ui, @QueryParam("callback") @DefaultValue("callback") String callback )
 throws Exception {


 ApiResponse response = createApiResponse();
 response.setAction("get application client credentials");


 ClientCredentialsInfo credentials =
 new ClientCredentialsInfo( management.getClientIdForApplication( applicationId ),
 management.getClientSecretForApplication( applicationId ) );


 response.setCredentials( credentials );
 return response;
    }




 @RequireOrganizationAccess
 @POST
 @Path("credentials")
 @JSONP
 @Produces({MediaType.APPLICATION_JSON, "application/javascript"})
 public ApiResponse generateCredentials( @Context UriInfo ui,
 @QueryParam("callback") @DefaultValue("callback") String callback )
 throws Exception {


 ApiResponse response = createApiResponse();
 response.setAction( "generate application client credentials" );


 ClientCredentialsInfo credentials =
 new ClientCredentialsInfo( management.getClientIdForApplication( applicationId ),
 management.newClientSecretForApplication(applicationId) );


 response.setCredentials( credentials );
 return response;
    }


 @RequireOrganizationAccess
 @GET
 @JSONP
 @Path("_size")
 public ApiResponse getApplicationSize(
 @Context UriInfo ui, @QueryParam("callback") @DefaultValue("callback") String callback )
 throws Exception {


 ApiResponse response = createApiResponse();
 response.setAction( "get application size for all entities" );
 long size = management.getApplicationSize(this.applicationId);
 Map<String,Object> map = new HashMap<>();
 Map<String,Object> innerMap = new HashMap<>();
 Map<String,Object> sumMap = new HashMap<>();
 innerMap.put("application",size);
 sumMap.put("size",innerMap);
 map.put("aggregation", sumMap);
 response.setMetadata(map);
 return response;
    }


 @RequireOrganizationAccess
 @GET
 @JSONP
 @Path("{collection_name}/_size")
 public ApiResponse getCollectionSize(
 @Context UriInfo ui,
 @PathParam( "collection_name" ) String collection_name,
 @QueryParam("callback") @DefaultValue("callback") String callback )
 throws Exception {
 ApiResponse response = createApiResponse();
 response.setAction("get collection size for all entities");
 long size = management.getCollectionSize(this.applicationId, collection_name);
 Map<String,Object> map = new HashMap<>();
 Map<String,Object> sumMap = new HashMap<>();
 Map<String,Object> innerMap = new HashMap<>();
 innerMap.put(collection_name,size);
 sumMap.put("size",innerMap);
 map.put("aggregation",sumMap);
 response.setMetadata(map);
 return response;
    }


 @RequireOrganizationAccess
 @GET
 @JSONP
 @Path("collections/_size")
 public ApiResponse getEachCollectionSize(
 @Context UriInfo ui,
 @QueryParam("callback") @DefaultValue("callback") String callback )
 throws Exception {
 ApiResponse response = createApiResponse();
 response.setAction("get collection size for all entities");
 Map<String,Long> sizes = management.getEachCollectionSize(this.applicationId);
 Map<String,Object> map = new HashMap<>();
 Map<String,Object> sumMap = new HashMap<>();
 sumMap.put("size",sizes);
 map.put("aggregation",sumMap);
 response.setMetadata(map);
 return response;
    }


 @POST
 @Path("sia-provider")
 @Consumes(APPLICATION_JSON)
 @RequireOrganizationAccess
 @JSONP
 @Produces({MediaType.APPLICATION_JSON, "application/javascript"})
 public ApiResponse configureProvider(
 @Context UriInfo ui,
 @QueryParam("provider_key") String siaProvider,
 Map<String, Object> json,
 @QueryParam("callback")
 @DefaultValue("") String callback )
 throws Exception {


 ApiResponse response = createApiResponse();
 response.setAction( "post signin provider configuration" );


 Preconditions.checkArgument( siaProvider != null, "Sign in provider required" );


 SignInAsProvider signInAsProvider = null;
 if ( StringUtils.equalsIgnoreCase( siaProvider, "facebook" ) ) {
 signInAsProvider = signInProviderFactory.facebook(
 smf.getServiceManager( applicationId ).getApplication() );
        }
 else if ( StringUtils.equalsIgnoreCase( siaProvider, "pingident" ) ) {
 signInAsProvider = signInProviderFactory.pingident(
 smf.getServiceManager( applicationId ).getApplication() );
        }
 else if ( StringUtils.equalsIgnoreCase( siaProvider, "foursquare" ) ) {
 signInAsProvider = signInProviderFactory.foursquare(
 smf.getServiceManager( applicationId ).getApplication() );
        }


 Preconditions.checkArgument( signInAsProvider != null,
 "No signin provider found by that name: " + siaProvider );


 signInAsProvider.saveToConfiguration( json );


 return response;
    }


//    @POST
//    @Path("export")
//    @Consumes(APPLICATION_JSON)
//    @RequireOrganizationAccess
//    public Response exportPostJson( @Context UriInfo ui,Map<String, Object> json,
//                                    @QueryParam("callback") @DefaultValue("") String callback )
//            throws OAuthSystemException {
//
//        UsergridAwsCredentials uac = new UsergridAwsCredentials();
//
//        UUID jobUUID = null;
//        Map<String, String> uuidRet = new HashMap<String, String>();
//
//        Map<String,Object> properties;
//        Map<String, Object> storage_info;
//
//        try {
//            if((properties = ( Map<String, Object> )  json.get( "properties" )) == null){
//                throw new NullArgumentException("Could not find 'properties'");
//            }
//            storage_info = ( Map<String, Object> ) properties.get( "storage_info" );
//            String storage_provider = ( String ) properties.get( "storage_provider" );
//            if(storage_provider == null) {
//                throw new NullArgumentException( "Could not find field 'storage_provider'" );
//            }
//            if(storage_info == null) {
//                throw new NullArgumentException( "Could not find field 'storage_info'" );
//            }
//
//
//            String bucketName = ( String ) storage_info.get( "bucket_location" );
//            String accessId = ( String ) storage_info.get( "s3_access_id" );
//            String secretKey = ( String ) storage_info.get( "s3_key" );
//
//            if ( bucketName == null ) {
//                throw new NullArgumentException( "Could not find field 'bucketName'" );
//            }
//            if ( accessId == null ) {
//                throw new NullArgumentException( "Could not find field 's3_access_id'" );
//            }
//            if ( secretKey == null ) {
//
//                throw new NullArgumentException( "Could not find field 's3_key'" );
//            }
//
//            json.put("organizationId", organization.getUuid());
//            json.put( "applicationId",applicationId);
//
//            jobUUID = exportService.schedule( json );
//            uuidRet.put( "Export Entity", jobUUID.toString() );
//        }
//        catch ( NullArgumentException e ) {
//            return Response.status( SC_BAD_REQUEST )
//                .type( JSONPUtils.jsonMediaType( callback ) )
//                .entity( ServiceResource.wrapWithCallback( e.getMessage(), callback ) ).build();
//        }
//        catch ( Exception e ) {
//            // TODO: throw descriptive error message and or include on in the response
//            // TODO: fix below, it doesn't work if there is an exception.
//            // Make it look like the OauthResponse.
//            return Response.status( SC_INTERNAL_SERVER_ERROR )
//                .type( JSONPUtils.jsonMediaType( callback ) )
//                .entity( ServiceResource.wrapWithCallback( e.getMessage(), callback ) ).build();
//        }
//
//        return Response.status( SC_ACCEPTED ).entity( uuidRet ).build();
//    }
//
//    @POST
//    @Path("collection/{collection_name}/export")
//    @Consumes(APPLICATION_JSON)
//    @RequireOrganizationAccess
//    public Response exportPostJson( @Context UriInfo ui,
//            @PathParam( "collection_name" ) String collection_name ,Map<String, Object> json,
//            @QueryParam("callback") @DefaultValue("") String callback )
//            throws OAuthSystemException {
//
//        UsergridAwsCredentials uac = new UsergridAwsCredentials();
//        UUID jobUUID = null;
//        String colExport = collection_name;
//        Map<String, String> uuidRet = new HashMap<String, String>();
//
//        Map<String,Object> properties;
//        Map<String, Object> storage_info;
//
//        try {
//            //checkJsonExportProperties(json);
//            if((properties = ( Map<String, Object> )  json.get( "properties" )) == null){
//                throw new NullArgumentException("Could not find 'properties'");
//            }
//            storage_info = ( Map<String, Object> ) properties.get( "storage_info" );
//            String storage_provider = ( String ) properties.get( "storage_provider" );
//            if(storage_provider == null) {
//                throw new NullArgumentException( "Could not find field 'storage_provider'" );
//            }
//            if(storage_info == null) {
//                throw new NullArgumentException( "Could not find field 'storage_info'" );
//            }
//
//            String bucketName = ( String ) storage_info.get( "bucket_location" );
//            String accessId = ( String ) storage_info.get( "s3_access_id" );
//            String secretKey = ( String ) storage_info.get( "s3_key" );
//
//            if ( accessId == null ) {
//                throw new NullArgumentException( "Could not find field 's3_access_id'" );
//            }
//            if ( secretKey == null ) {
//                throw new NullArgumentException( "Could not find field 's3_key'" );
//            }
//
//            if(bucketName == null) {
//                throw new NullArgumentException( "Could not find field 'bucketName'" );
//            }
//
//            json.put( "organizationId",organization.getUuid() );
//            json.put( "applicationId", applicationId);
//            json.put( "collectionName", colExport);
//
//            jobUUID = exportService.schedule( json );
//            uuidRet.put( "Export Entity", jobUUID.toString() );
//        }
//        catch ( NullArgumentException e ) {
//            return Response.status( SC_BAD_REQUEST )
//                .type( JSONPUtils.jsonMediaType( callback ) )
//                .entity( ServiceResource.wrapWithCallback( e.getMessage(), callback ) )
//                .build();
//        }
//        catch ( Exception e ) {
//
//            // TODO: throw descriptive error message and or include on in the response
//            // TODO: fix below, it doesn't work if there is an exception.
//            // Make it look like the OauthResponse.
//
//            OAuthResponse errorMsg = OAuthResponse.errorResponse( SC_INTERNAL_SERVER_ERROR )
//                .setErrorDescription( e.getMessage() )
//                .buildJSONMessage();
//
//            return Response.status( errorMsg.getResponseStatus() )
//                .type( JSONPUtils.jsonMediaType( callback ) )
//                .entity( ServiceResource.wrapWithCallback( errorMsg.getBody(), callback ) )
//                .build();
//        }
//
//        return Response.status( SC_ACCEPTED ).entity( uuidRet ).build();
//    }
//
//
//    @Path( "imports" )
//    public ImportsResource importGetJson( @Context UriInfo ui,
//                                          @QueryParam( "callback" ) @DefaultValue( "" ) String callback )
//        throws Exception {
//
//
//        return getSubResource( ImportsResource.class ).init( organization, application );
//    }


 @GET
 @Path("/status")
 public Response getStatus() {


 Map<String, Object> statusMap = new HashMap<String, Object>();


 EntityManager em = emf.getEntityManager( applicationId );
 if ( !emf.getIndexHealth().equals( Health.RED ) ) {
 statusMap.put("message", "Index Health Status RED for application " + applicationId );
 return Response.status( SC_INTERNAL_SERVER_ERROR ).entity( statusMap ).build();
        }


 try {
 if ( em.getApplication() == null ) {
 statusMap.put("message", "Application " + applicationId + " not found");
 return Response.status( SC_NOT_FOUND ).entity( statusMap ).build();
            }


        } catch (Exception ex) {
 statusMap.put("message", "Error looking up application " + applicationId );
 return Response.status( SC_INTERNAL_SERVER_ERROR ).entity( statusMap ).build();
        }


 return Response.status( SC_OK ).entity( null ).build();
    }






 /**
     * Put on application URL will restore application if it was deleted.
     */
 @PUT
 @RequireOrganizationAccess
 @JSONP
 @Produces({MediaType.APPLICATION_JSON, "application/javascript"})
 public ApiResponse executePut(  @Context UriInfo ui, String body,
 @QueryParam("callback") @DefaultValue("callback") String callback ) throws Exception {


 if ( applicationId == null ) {
 throw new IllegalArgumentException("Application ID not specified in request");
        }


 ApplicationRestorePasswordService restorePasswordService = getApplicationRestorePasswordService();
 if (!SubjectUtils.isServiceAdmin()) {
 // require password if it exists
 String storedRestorePassword = restorePasswordService.getApplicationRestorePassword(applicationId);
 if (StringUtils.isNotEmpty(storedRestorePassword)) {
 // must have matching password as query parameter
 String suppliedRestorePassword = ui.getQueryParameters().getFirst(RESTORE_PASSWORD);
 if (!storedRestorePassword.equals(suppliedRestorePassword)) {
 throw new IllegalArgumentException("Application cannot be restored without application password");
                }
            }
        }


 management.restoreApplication( applicationId );


 // not deleting password -- will be changed upon successful soft delete


 ApiResponse response = createApiResponse();
 response.setAction( "restore" );
 response.setApplication( emf.getEntityManager( applicationId ).getApplication() );
 response.setParams( ui.getQueryParameters() );


 return response;
    }




 /**
     * Caller MUST pass confirm_application_identifier that is either the UUID or the
     * name of the application to be deleted. Yes, this is redundant and intended to
     * be a protection measure to force caller to confirm that they want to do a delete.
     */
 @DELETE
 @RequireOrganizationAccess
 @JSONP
 @Produces({MediaType.APPLICATION_JSON, "application/javascript"})
 public ApiResponse executeDelete(  @Context UriInfo ui,
 @QueryParam("callback") @DefaultValue("callback") String callback,
 @QueryParam(CONFIRM_APPLICATION_IDENTIFIER) String confirmApplicationIdentifier) throws Exception {


 if ( application == null && applicationId == null ) {
 throw new IllegalArgumentException("Application ID not specified in request");
        }


 // If the path uses name then expect name, otherwise if they use uuid then expect uuid.
 if (application == null) {
 if (!applicationId.toString().equals( confirmApplicationIdentifier )) {
 throw new IllegalArgumentException(
 "Cannot delete application without supplying correct application id.");
            }


        } else if (!application.getName().split( "/" )[1].equals( confirmApplicationIdentifier ) ) {
 throw new IllegalArgumentException(
 "Cannot delete application without supplying correct application name");
        }


 String restorePassword = null;
 ApplicationRestorePasswordService restorePasswordService = getApplicationRestorePasswordService();
 if (SubjectUtils.isServiceAdmin()) {
 restorePassword = ui.getQueryParameters().getFirst(RESTORE_PASSWORD);
 if (StringUtils.isNotEmpty(restorePassword)) {
 // save password, required for future undelete if not sysadmin
 restorePasswordService.setApplicationRestorePassword(applicationId, restorePassword);
            }
        }


 management.deleteApplication( applicationId );


 if (restorePassword == null) {
 // clear restore password
 restorePasswordService.removeApplicationRestorePassword(applicationId);
        }


 if (logger.isTraceEnabled()) {
 logger.trace("ApplicationResource.delete() deleted appId = {}", applicationId);
        }


 ApiResponse response = createApiResponse();
 response.setAction( "delete" );
 response.setApplication(emf.getEntityManager( applicationId ).getApplication());
 response.setParams(ui.getQueryParameters());


 if (logger.isTraceEnabled()) {
 logger.trace("ApplicationResource.delete() sending response ");
        }


 return response;
    }


 private ApplicationRestorePasswordService getApplicationRestorePasswordService() {
 return injector.getInstance(ApplicationRestorePasswordService.class);
    }


}