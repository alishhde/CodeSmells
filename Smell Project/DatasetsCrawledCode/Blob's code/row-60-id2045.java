@APICommand(name = "extractTemplate", description = "Extracts a template", responseObject = ExtractResponse.class,
 requestHasSensitiveInfo = false, responseHasSensitiveInfo = false)
public class ExtractTemplateCmd extends BaseAsyncCmd {
 public static final Logger s_logger = Logger.getLogger(ExtractTemplateCmd.class.getName());


 private static final String s_name = "extracttemplateresponse";


 /////////////////////////////////////////////////////
 //////////////// API parameters /////////////////////
 /////////////////////////////////////////////////////


 @Parameter(name = ApiConstants.ID, type = CommandType.UUID, entityType = TemplateResponse.class, required = true, description = "the ID of the template")
 private Long id;


 @Parameter(name = ApiConstants.URL, type = CommandType.STRING, required = false, length = 2048, description = "the url to which the ISO would be extracted")
 private String url;


 @Parameter(name = ApiConstants.ZONE_ID,
 type = CommandType.UUID,
 entityType = ZoneResponse.class,
 required = false,
 description = "the ID of the zone where the ISO is originally located")
 private Long zoneId;


 @Parameter(name = ApiConstants.MODE, type = CommandType.STRING, required = true, description = "the mode of extraction - HTTP_DOWNLOAD or FTP_UPLOAD")
 private String mode;


 /////////////////////////////////////////////////////
 /////////////////// Accessors ///////////////////////
 /////////////////////////////////////////////////////


 public Long getId() {
 return id;
    }


 public String getUrl() {
 return url;
    }


 public Long getZoneId() {
 return zoneId;
    }


 public String getMode() {
 return mode;
    }


 /////////////////////////////////////////////////////
 /////////////// API Implementation///////////////////
 /////////////////////////////////////////////////////


 @Override
 public String getCommandName() {
 return s_name;
    }


 public static String getStaticName() {
 return s_name;
    }


 @Override
 public long getEntityOwnerId() {
 VirtualMachineTemplate template = _entityMgr.findById(VirtualMachineTemplate.class, getId());
 if (template != null) {
 return template.getAccountId();
        }


 // invalid id, parent this command to SYSTEM so ERROR events are tracked
 return Account.ACCOUNT_ID_SYSTEM;
    }


 @Override
 public String getEventType() {
 return EventTypes.EVENT_TEMPLATE_EXTRACT;
    }


 @Override
 public String getEventDescription() {
 return "extracting template: " + this._uuidMgr.getUuid(VirtualMachineTemplate.class, getId()) + ((getZoneId() != null) ? " from zone: " + this._uuidMgr.getUuid(DataCenter.class, getZoneId()) : "");
    }


 @Override
 public ApiCommandJobType getInstanceType() {
 return ApiCommandJobType.Template;
    }


 @Override
 public Long getInstanceId() {
 return getId();
    }


 @Override
 public void execute() {
 try {
 CallContext.current().setEventDetails(getEventDescription());
 String uploadUrl = _templateService.extract(this);
 if (uploadUrl != null) {
 ExtractResponse response = _responseGenerator.createExtractResponse(id, zoneId, getEntityOwnerId(), mode, uploadUrl);
 response.setResponseName(getCommandName());
 this.setResponseObject(response);
            } else {
 throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to extract template");
            }
        } catch (InternalErrorException ex) {
 s_logger.warn("Exception: ", ex);
 throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        }
    }
}