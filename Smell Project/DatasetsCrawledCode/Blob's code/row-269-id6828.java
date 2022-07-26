 public static class GenericConnectionStatusHtmlCreator {
 public static String generateConnectionViewHtml(String providerDisplayName, String providerId, UserProfile profile) {
 String escProviderDisplayName = escape(providerDisplayName);
 StringBuilder builder = new StringBuilder();
 if (profile == null) {
 builder.append("<h3>Connect to " + escProviderDisplayName + "</h3>");
 builder.append("<form action=\"/connect/");
 
 try {
 String escProviderId = UriUtils.encodePath(providerId, "UTF-8");
 builder.append(escProviderId);
				} catch (UnsupportedEncodingException e) { /* Default to UTF-8...should be fine.*/ }


 builder.append("\" method=\"POST\">");
 builder.append("<div class=\"formInfo\">");
 builder.append("<p>You aren't connected to ");
 builder.append(escProviderDisplayName);
 builder.append(" yet. Click the button to connect with your ");
 builder.append(escProviderDisplayName);
 builder.append(" account.</p>");
 builder.append("</div>");
 builder.append("<p><button type=\"submit\">Connect to ");
 builder.append(escProviderDisplayName);
 builder.append("</button></p>");
 builder.append("</form>");
			} else {
 String escProfileName = escape(profile.getName());
 builder.append("<h3>Connected to ");
 builder.append(escProviderDisplayName);
 builder.append("</h3>");
 builder.append("<p>Hello, ");
 builder.append(escProfileName);
 builder.append("!</p><p>You are now connected to ");
 builder.append(escProviderDisplayName);
 String username = profile.getUsername();
 if (username !=null) {
 builder.append(" as ");
 builder.append(escape(username));
				}
 builder.append(".</p>");
			}
 return builder.toString();
		}
 
 private static String escape(String in) {
 return htmlEscape(in).replaceAll("\\{", "&#123;").replaceAll("\\}", "&#125;");
		}
	}