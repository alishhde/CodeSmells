 private String formatQueryString(final String projectUri, final String[] args) {
 final StringBuffer result = new StringBuffer();


 if (projectUri != null) {
 if (isCompatibleMode) {
 result.append("puri="); //$NON-NLS-1$
 result.append(URLEncode.encode(projectUri.toString()));
            } else {
 final ArtifactID artifactID = new ArtifactID(projectUri);
 result.append("pguid="); //$NON-NLS-1$
 result.append(URLEncode.encode(artifactID.getToolSpecificID()));
            }
        } else if (!isCompatibleMode) {
 result.append("pcguid="); //$NON-NLS-1$
 result.append(URLEncode.encode(collectionId.toString()));
        }


 for (int i = 0; i < args.length - 1; i += 2) {
 final String name = args[i];
 final String value = args[i + 1];


 if (name != null) {
 if (result.length() > 0) {
 result.append('&');
                }


 result.append(URLEncode.encode(name));
            }


 if (value != null) {
 if (name != null) {
 result.append('=');
                } else if (result.length() > 0) {
 result.append('&');
                }


 result.append(URLEncode.encode(value));
            }
        }


 return result.toString();
    }