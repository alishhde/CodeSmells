@APICommand(name = RevokeCertificateCmd.APINAME,
 description = "Revokes certificate using configured CA plugin",
 responseObject = SuccessResponse.class,
 requestHasSensitiveInfo = true,
 responseHasSensitiveInfo = false,
 since = "4.11.0",
 authorized = {RoleType.Admin})
public class RevokeCertificateCmd extends BaseAsyncCmd {


 public static final String APINAME = "revokeCertificate";


 @Inject
 private CAManager caManager;


 /////////////////////////////////////////////////////
 //////////////// API parameters /////////////////////
 /////////////////////////////////////////////////////


 @Parameter(name = ApiConstants.SERIAL, type = BaseCmd.CommandType.STRING, required = true, description = "The certificate serial number, as a hex value")
 private String serial;


 @Parameter(name = ApiConstants.CN, type = BaseCmd.CommandType.STRING, description = "The certificate CN")
 private String cn;


 @Parameter(name = ApiConstants.PROVIDER, type = BaseCmd.CommandType.STRING, description = "Name of the CA service provider, otherwise the default configured provider plugin will be used")
 private String provider;


 /////////////////////////////////////////////////////
 /////////////////// Accessors ///////////////////////
 /////////////////////////////////////////////////////


 public BigInteger getSerialBigInteger() {
 if (Strings.isNullOrEmpty(serial)) {
 throw new ServerApiException(ApiErrorCode.PARAM_ERROR, "Certificate serial cannot be empty");
        }
 return new BigInteger(serial, 16);
    }


 public String getCn() {
 return cn;
    }


 public String getProvider() {
 return provider;
    }


 /////////////////////////////////////////////////////
 /////////////// API Implementation///////////////////
 /////////////////////////////////////////////////////


 @Override
 public void execute() {
 boolean result = caManager.revokeCertificate(getSerialBigInteger(), getCn(), getProvider());
 SuccessResponse response = new SuccessResponse(getCommandName());
 response.setSuccess(result);
 setResponseObject(response);
    }


 @Override
 public String getCommandName() {
 return APINAME.toLowerCase() + BaseCmd.RESPONSE_SUFFIX;
    }


 @Override
 public long getEntityOwnerId() {
 return CallContext.current().getCallingAccount().getId();
    }


 @Override
 public String getEventType() {
 return EventTypes.EVENT_CA_CERTIFICATE_REVOKE;
    }


 @Override
 public String getEventDescription() {
 return "revoking certificate with serial id=" + serial + ", cn=" + cn;
    }
}