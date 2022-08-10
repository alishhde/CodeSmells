 private void verifyRepository(RepositoryRequest request) throws AmbariException {
 URLStreamProvider usp = new URLStreamProvider(REPO_URL_CONNECT_TIMEOUT, REPO_URL_READ_TIMEOUT, null, null, null);
 usp.setSetupTruststoreForHttps(false);


 String repoName = request.getRepoName();
 if (StringUtils.isEmpty(repoName)) {
 throw new IllegalArgumentException("repo_name is required to verify repository");
    }


 String errorMessage = null;
 Exception e = null;


 String[] suffixes = configs.getRepoValidationSuffixes(request.getOsType());
 for (String suffix : suffixes) {
 String formatted_suffix = String.format(suffix, repoName);
 String spec = request.getBaseUrl().trim();


 // This logic is to identify if the end of baseurl has a slash ('/') and/or the beginning of suffix String (e.g. "/repodata/repomd.xml")
 // has a slash and they can form a good url.
 // e.g. "http://baseurl.com/" + "/repodata/repomd.xml" becomes "http://baseurl.com/repodata/repomd.xml" but not "http://baseurl.com//repodata/repomd.xml"
 if (spec.charAt(spec.length() - 1) != '/' && formatted_suffix.charAt(0) != '/') {
 spec = spec + "/" + formatted_suffix;
      } else if (spec.charAt(spec.length() - 1) == '/' && formatted_suffix.charAt(0) == '/') {
 spec = spec + formatted_suffix.substring(1);
      } else {
 spec = spec + formatted_suffix;
      }


 // if spec contains "file://" then check local file system.
 final String FILE_SCHEME = "file://";
 if(spec.toLowerCase().startsWith(FILE_SCHEME)){
 String filePath = spec.substring(FILE_SCHEME.length());
 File f = new File(filePath);
 if(!f.exists()){
 errorMessage = "Could not access base url . " + spec + " . ";
 e = new FileNotFoundException(errorMessage);
 break;
        }


      }else{
 try {
 IOUtils.readLines(usp.readFrom(spec));
        } catch (IOException ioe) {
 e = ioe;
 errorMessage = "Could not access base url . " + request.getBaseUrl() + " . ";
 if (LOG.isDebugEnabled()) {
 errorMessage += ioe;
          } else {
 errorMessage += ioe.getMessage();
          }
 break;
        }
      }
    }


 if (e != null) {
 LOG.error(errorMessage);
 throw new IllegalArgumentException(errorMessage, e);
    }
  }