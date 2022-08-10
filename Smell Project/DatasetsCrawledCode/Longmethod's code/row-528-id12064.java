 @Override
 public java.util.concurrent.Future<UpdateStackResponse> updateStack(
 final UpdateStackRequest request,
 final com.oracle.bmc.responses.AsyncHandler<UpdateStackRequest, UpdateStackResponse>
 handler) {
 LOG.trace("Called async updateStack");
 final UpdateStackRequest interceptedRequest =
 UpdateStackConverter.interceptRequest(request);
 final com.oracle.bmc.http.internal.WrappedInvocationBuilder ib =
 UpdateStackConverter.fromRequest(client, interceptedRequest);
 final com.google.common.base.Function<javax.ws.rs.core.Response, UpdateStackResponse>
 transformer = UpdateStackConverter.fromResponse();


 com.oracle.bmc.responses.AsyncHandler<UpdateStackRequest, UpdateStackResponse>
 handlerToUse = handler;
 if (handler != null
                && this.authenticationDetailsProvider
 instanceof com.oracle.bmc.auth.RefreshableOnNotAuthenticatedProvider) {
 handlerToUse =
 new com.oracle.bmc.util.internal.RefreshAuthTokenWrappingAsyncHandler<
 UpdateStackRequest, UpdateStackResponse>(
                            (com.oracle.bmc.auth.RefreshableOnNotAuthenticatedProvider)
 this.authenticationDetailsProvider,
 handler) {
 @Override
 public void retryCall() {
 final com.oracle.bmc.util.internal.Consumer<javax.ws.rs.core.Response>
 onSuccess =
 new com.oracle.bmc.http.internal.SuccessConsumer<>(
 this, transformer, interceptedRequest);
 final com.oracle.bmc.util.internal.Consumer<Throwable> onError =
 new com.oracle.bmc.http.internal.ErrorConsumer<>(
 this, interceptedRequest);
 client.put(
 ib,
 interceptedRequest.getUpdateStackDetails(),
 interceptedRequest,
 onSuccess,
 onError);
                        }
                    };
        }


 final com.oracle.bmc.util.internal.Consumer<javax.ws.rs.core.Response> onSuccess =
                (handler == null)
                        ? null
                        : new com.oracle.bmc.http.internal.SuccessConsumer<>(
 handlerToUse, transformer, interceptedRequest);
 final com.oracle.bmc.util.internal.Consumer<Throwable> onError =
                (handler == null)
                        ? null
                        : new com.oracle.bmc.http.internal.ErrorConsumer<>(
 handlerToUse, interceptedRequest);


 java.util.concurrent.Future<javax.ws.rs.core.Response> responseFuture =
 client.put(
 ib,
 interceptedRequest.getUpdateStackDetails(),
 interceptedRequest,
 onSuccess,
 onError);


 if (this.authenticationDetailsProvider
 instanceof com.oracle.bmc.auth.RefreshableOnNotAuthenticatedProvider) {
 return new com.oracle.bmc.util.internal.RefreshAuthTokenTransformingFuture<
 javax.ws.rs.core.Response, UpdateStackResponse>(
 responseFuture,
 transformer,
                    (com.oracle.bmc.auth.RefreshableOnNotAuthenticatedProvider)
 this.authenticationDetailsProvider,
 new com.google.common.base.Supplier<
 java.util.concurrent.Future<javax.ws.rs.core.Response>>() {
 @Override
 public java.util.concurrent.Future<javax.ws.rs.core.Response> get() {
 return client.put(
 ib,
 interceptedRequest.getUpdateStackDetails(),
 interceptedRequest,
 onSuccess,
 onError);
                        }
                    });
        } else {
 return new com.oracle.bmc.util.internal.TransformingFuture<>(
 responseFuture, transformer);
        }
    }