final class TraceableHttpServletResponse implements TraceableResponse {


 private final HttpServletResponse delegate;


 TraceableHttpServletResponse(HttpServletResponse response) {
 this.delegate = response;
	}


 @Override
 public int getStatus() {
 return this.delegate.getStatus();
	}


 @Override
 public Map<String, List<String>> getHeaders() {
 return extractHeaders();
	}


 private Map<String, List<String>> extractHeaders() {
 Map<String, List<String>> headers = new LinkedHashMap<>();
 for (String name : this.delegate.getHeaderNames()) {
 headers.put(name, new ArrayList<>(this.delegate.getHeaders(name)));
		}
 return headers;
	}


}