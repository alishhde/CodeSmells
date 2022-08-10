 public static String executeUrl(String httpMethod, String url, Properties httpHeaders, InputStream content,
 String contentType, int timeout) throws IOException {
 final ProxyParams proxyParams = prepareProxyParams();


 return executeUrl(httpMethod, url, httpHeaders, content, contentType, timeout, proxyParams.proxyHost,
 proxyParams.proxyPort, proxyParams.proxyUser, proxyParams.proxyPassword, proxyParams.nonProxyHosts);
    }