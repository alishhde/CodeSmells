 @Override
 public CreatePreauthenticatedRequestResponse createPreauthenticatedRequest(
 CreatePreauthenticatedRequestRequest request) {
 LOG.trace("Called createPreauthenticatedRequest");
 request = CreatePreauthenticatedRequestConverter.interceptRequest(request);
 com.oracle.bmc.http.internal.WrappedInvocationBuilder ib =
 CreatePreauthenticatedRequestConverter.fromRequest(client, request);
 com.google.common.base.Function<
 javax.ws.rs.core.Response, CreatePreauthenticatedRequestResponse>
 transformer = CreatePreauthenticatedRequestConverter.fromResponse();


 int attempts = 0;
 while (true) {
 try {
 javax.ws.rs.core.Response response =
 client.post(ib, request.getCreatePreauthenticatedRequestDetails(), request);
 return transformer.apply(response);
            } catch (com.oracle.bmc.model.BmcException e) {
 if (++attempts < MAX_IMMEDIATE_RETRIES_IF_USING_INSTANCE_PRINCIPALS
                        && canRetryRequestIfRefreshableAuthTokenUsed(e)) {
 continue;
                } else {
 throw e;
                }
            }
        }
    }