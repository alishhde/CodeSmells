public abstract class AbstractBraveClientProvider extends AbstractTracingProvider {
 protected static final Logger LOG = LogUtils.getL7dLogger(AbstractBraveClientProvider.class);
 protected static final String TRACE_SPAN = "org.apache.cxf.tracing.client.brave.span";


 private final HttpTracing brave;


 public AbstractBraveClientProvider(final HttpTracing brave) {
 this.brave = brave;
    }


 protected TraceScopeHolder<TraceScope> startTraceSpan(final Map<String, List<String>> requestHeaders,
 URI uri, String method) {


 final Request request = HttpAdapterFactory.request(requestHeaders, uri, method);
 final HttpClientAdapter<Request, ?> adapter = HttpClientAdapterFactory.create(request);
 
 final HttpClientHandler<Request, ?> handler = HttpClientHandler.create(brave, adapter);
 final Span span = handler.handleSend(
 brave
                .tracing()
                .propagation()
                .injector(inject(requestHeaders)), 
 request);


 // In case of asynchronous client invocation, the span should be detached as JAX-RS
 // client request / response filters are going to be executed in different threads.
 SpanInScope scope = null;
 if (!isAsyncInvocation() && span != null) {
 scope = brave.tracing().tracer().withSpanInScope(span);
        }


 return new TraceScopeHolder<TraceScope>(new TraceScope(span, scope), scope == null /* detached */);
    }
 
 private <C> Setter<C, String> inject(final Map<String, List<String>> requestHeaders) {
 return (carrier, key, value) -> {
 if (!requestHeaders.containsKey(key)) {
 requestHeaders.put(key, Collections.singletonList(value));
            }
        };
    }


 private boolean isAsyncInvocation() {
 return !PhaseInterceptorChain.getCurrentMessage().getExchange().isSynchronous();
    }


 protected void stopTraceSpan(final TraceScopeHolder<TraceScope> holder, final int responseStatus) {
 if (holder == null) {
 return;
        }


 final TraceScope scope = holder.getScope();
 if (scope != null) {
 try {
 // If the client invocation was asynchronous , the trace span has been created
 // in another thread and should be re-attached to the current one.
 if (holder.isDetached()) {
 brave.tracing().tracer().joinSpan(scope.getSpan().context());
                }
 
 final Response response = HttpAdapterFactory.response(responseStatus);
 final HttpClientAdapter<?, Response> adapter = HttpClientAdapterFactory.create(response);
 
 final HttpClientHandler<?, Response> handler = HttpClientHandler.create(brave, adapter);
 handler.handleReceive(response, null, scope.getSpan());
            } finally {
 scope.close();
            }
        }
    }
}