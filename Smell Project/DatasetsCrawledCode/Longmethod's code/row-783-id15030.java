 private static List<NameValuePair> getUserDetails(String query) {
 List<NameValuePair> details = new ArrayList<NameValuePair>();
 if (query != null && !query.isEmpty()) {
 StringTokenizer allParams = new StringTokenizer(query, "&");
 while (allParams.hasMoreTokens()) {
 String param = allParams.nextToken();
 details.add(new BasicNameValuePair(param.substring(0, param.indexOf("=")),
 param.substring(param.indexOf("=") + 1)));
            }
        }


 return details;
    }