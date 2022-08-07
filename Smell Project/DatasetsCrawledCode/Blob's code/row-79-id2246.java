public class IgnoreCommitOptimizeUpdateProcessorFactory extends UpdateRequestProcessorFactory {
 private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


 private static final String DEFAULT_RESPONSE_MSG = "Explicit commit/optimize requests are forbidden!";
 
 protected ErrorCode errorCode;
 protected String responseMsg;
 protected boolean ignoreOptimizeOnly = false; // default behavior is to ignore commits and optimize


 @Override
 public void init(final NamedList args) {
 SolrParams params = (args != null) ? args.toSolrParams() : null;
 if (params == null) {
 errorCode = ErrorCode.FORBIDDEN; // default is 403 error
 responseMsg = DEFAULT_RESPONSE_MSG;
 ignoreOptimizeOnly = false;
 return;
    }


 ignoreOptimizeOnly = params.getBool("ignoreOptimizeOnly", false);


 int statusCode = params.getInt("statusCode", ErrorCode.FORBIDDEN.code);
 if (statusCode == 200) {
 errorCode = null; // not needed but makes the logic clearer
 responseMsg = params.get("responseMessage"); // OK to be null for 200's
    } else {
 errorCode = ErrorCode.getErrorCode(statusCode);
 if (errorCode == ErrorCode.UNKNOWN) {
 // only allow the error codes supported by the SolrException.ErrorCode class
 StringBuilder validCodes = new StringBuilder();
 int appended = 0;
 for (ErrorCode code : ErrorCode.values()) {
 if (code != ErrorCode.UNKNOWN) {
 if (appended++ > 0) validCodes.append(", ");
 validCodes.append(code.code);
          }
        }
 throw new IllegalArgumentException("Configured status code " + statusCode +
 " not supported! Please choose one of: " + validCodes.toString());
      }


 // must always have a response message if sending an error code
 responseMsg = params.get("responseMessage", DEFAULT_RESPONSE_MSG);
    }
  }


 @Override
 public UpdateRequestProcessor getInstance(SolrQueryRequest req, SolrQueryResponse rsp, UpdateRequestProcessor next) {
 return new IgnoreCommitOptimizeUpdateProcessor(rsp, this, next);
  }
 
 static class IgnoreCommitOptimizeUpdateProcessor extends UpdateRequestProcessor {


 private final SolrQueryResponse rsp;
 private final ErrorCode errorCode;
 private final String responseMsg;
 private final boolean ignoreOptimizeOnly;


 IgnoreCommitOptimizeUpdateProcessor(SolrQueryResponse rsp,
 IgnoreCommitOptimizeUpdateProcessorFactory factory,
 UpdateRequestProcessor next)
    {
 super(next);
 this.rsp = rsp;
 this.errorCode = factory.errorCode;
 this.responseMsg = factory.responseMsg;
 this.ignoreOptimizeOnly = factory.ignoreOptimizeOnly;
    }


 @Override
 public void processCommit(CommitUpdateCommand cmd) throws IOException {


 if (ignoreOptimizeOnly && !cmd.optimize) {
 // we're setup to only ignore optimize requests so it's OK to pass this commit on down the line
 if (next != null) next.processCommit(cmd);
 return;
      }


 if (cmd.getReq().getParams().getBool(DistributedUpdateProcessor.COMMIT_END_POINT, false)) {
 // this is a targeted commit from replica to leader needed for recovery, so can't be ignored
 if (next != null) next.processCommit(cmd);
 return;
      }


 final String cmdType = cmd.optimize ? "optimize" : "commit";
 if (errorCode != null) {
 IgnoreCommitOptimizeUpdateProcessorFactory.log.info(
 "{} from client application ignored with error code: {}", cmdType, errorCode.code);
 rsp.setException(new SolrException(errorCode, responseMsg));
      } else {
 // errorcode is null, treat as a success with an optional message warning the commit request was ignored
 IgnoreCommitOptimizeUpdateProcessorFactory.log.info(
 "{} from client application ignored with status code: 200", cmdType);
 if (responseMsg != null) {
 NamedList<Object> responseHeader = rsp.getResponseHeader();
 if (responseHeader != null) {
 responseHeader.add("msg", responseMsg);
          } else {
 responseHeader = new SimpleOrderedMap<Object>();
 responseHeader.add("msg", responseMsg);
 rsp.addResponseHeader(responseHeader);
          }
        }
      }
    }
  }
}