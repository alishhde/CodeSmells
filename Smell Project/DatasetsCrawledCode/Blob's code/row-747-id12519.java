public class ObserveUtil {


 /* keys used to populate the request context */
 public static final String CTX_ENDPOINT = "leshan-endpoint";
 public static final String CTX_REGID = "leshan-regId";
 public static final String CTX_LWM2M_PATH = "leshan-path";


 /**
     * Create a LWM2M observation from a CoAP request.
     */
 public static Observation createLwM2mObservation(Request request) {
 String regId = null;
 String lwm2mPath = null;
 Map<String, String> context = null;


 for (Entry<String, String> ctx : request.getUserContext().entrySet()) {
 switch (ctx.getKey()) {
 case CTX_REGID:
 regId = ctx.getValue();
 break;
 case CTX_LWM2M_PATH:
 lwm2mPath = ctx.getValue();
 break;
 case CTX_ENDPOINT:
 break;
 default:
 if (context == null) {
 context = new HashMap<>();
                }
 context.put(ctx.getKey(), ctx.getValue());
            }
        }


 ContentFormat contentFormat = null;
 if (request.getOptions().hasAccept()) {
 contentFormat = ContentFormat.fromCode(request.getOptions().getAccept());
        }
 return new Observation(request.getToken().getBytes(), regId, new LwM2mPath(lwm2mPath), contentFormat, context);
    }


 /**
     * Create a CoAP observe request context with specific keys needed for internal Leshan working.
     */
 public static Map<String, String> createCoapObserveRequestContext(String endpoint, String registrationId,
 ObserveRequest request) {
 Map<String, String> context = new HashMap<>();
 context.put(CTX_ENDPOINT, endpoint);
 context.put(CTX_REGID, registrationId);
 context.put(CTX_LWM2M_PATH, request.getPath().toString());
 for (Entry<String, String> ctx : request.getContext().entrySet()) {
 context.put(ctx.getKey(), ctx.getValue());
        }
 return context;
    }


 public static String extractRegistrationId(org.eclipse.californium.core.observe.Observation observation) {
 return observation.getRequest().getUserContext().get(CTX_REGID);
    }


 public static String extractLwm2mPath(org.eclipse.californium.core.observe.Observation observation) {
 return observation.getRequest().getUserContext().get(CTX_LWM2M_PATH);
    }


 public static String extractEndpoint(org.eclipse.californium.core.observe.Observation observation) {
 return observation.getRequest().getUserContext().get(CTX_ENDPOINT);
    }


 /**
     * Validate the Californium observation. It is valid if it contains all necessary context for Leshan.
     */
 public static String validateCoapObservation(org.eclipse.californium.core.observe.Observation observation) {
 if (!observation.getRequest().getUserContext().containsKey(CTX_REGID))
 throw new IllegalStateException("missing registrationId info in the request context");
 if (!observation.getRequest().getUserContext().containsKey(CTX_LWM2M_PATH))
 throw new IllegalStateException("missing lwm2m path info in the request context");


 String endpoint = observation.getRequest().getUserContext().get(CTX_ENDPOINT);
 if (endpoint == null)
 throw new IllegalStateException("missing endpoint info in the request context");


 return endpoint;
    }
}