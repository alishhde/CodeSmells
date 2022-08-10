 @Override
 public void setup(Http2SolrClient http2Client) {
 HttpAuthenticationStore authenticationStore = new HttpAuthenticationStore();
 authenticationStore.addAuthentication(createSPNEGOAuthentication());
 http2Client.getHttpClient().setAuthenticationStore(authenticationStore);
 http2Client.getProtocolHandlers().put(new WWWAuthenticationProtocolHandler(http2Client.getHttpClient()));
  }