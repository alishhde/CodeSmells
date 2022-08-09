public class RewriteLoadBalancerClient implements LoadBalancerClient
{
 private static final Logger _log = LoggerFactory.getLogger(TrackerClient.class);


 private final String _serviceName;
 private final URI _uri;
 private final RewriteClient _client;


 public RewriteLoadBalancerClient(String serviceName, URI uri, TransportClient client)
  {
 _serviceName = serviceName;
 _uri = uri;
 _client = new RewriteClient(client, new D2URIRewriter(uri));
 debug(_log, "created rewrite client: ", this);
  }


 @Override
 public void restRequest(RestRequest request,
 RequestContext requestContext,
 Map<String, String> wireAttrs,
 TransportCallback<RestResponse> callback)
  {
 assert _serviceName.equals(LoadBalancerUtil.getServiceNameFromUri(request.getURI()));
 _client.restRequest(request, requestContext, wireAttrs, callback);
  }


 @Override
 public void streamRequest(StreamRequest request,
 RequestContext requestContext,
 Map<String, String> wireAttrs,
 TransportCallback<StreamResponse> callback)
  {
 assert _serviceName.equals(LoadBalancerUtil.getServiceNameFromUri(request.getURI()));
 _client.streamRequest(request, requestContext, wireAttrs, callback);
  }


 @Override
 public void shutdown(Callback<None> callback)
  {
 _client.shutdown(callback);
  }


 @Deprecated
 public TransportClient getWrappedClient()
  {
 return _client;
  }


 public TransportClient getDecoratedClient()
  {
 return _client;
  }


 @Override
 public URI getUri()
  {
 return _uri;
  }


 public String getServiceName()
  {
 return _serviceName;
  }


 @Override
 public String toString()
  {
 return "RewriteLoadBalancerClient [_serviceName=" + _serviceName + ", _uri=" + _uri
        + ", _wrappedClient=" + _client + "]";
  }
}