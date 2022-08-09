 public GSSCredentialSpi getCredentialElement(GSSNameSpi name,
 int initLifetime,
 int acceptLifetime,
 int usage)
 throws GSSException {
 if (name != null && !(name instanceof GssNameElement)) {
 name = GssNameElement.getInstance(name.toString(), name.getStringNameType());
        }


 GssCredElement credElement;


 if (usage == GSSCredential.INITIATE_ONLY) {
 credElement = GssInitCred.getInstance(caller, (GssNameElement) name, initLifetime);
        } else if (usage == GSSCredential.ACCEPT_ONLY) {
 credElement = GssAcceptCred.getInstance(caller, (GssNameElement) name, acceptLifetime);
        } else if (usage == GSSCredential.INITIATE_AND_ACCEPT) {
 throw new GSSException(GSSException.FAILURE, -1, "Unsupported usage mode: INITIATE_AND_ACCEPT");
        } else {
 throw new GSSException(GSSException.FAILURE, -1, "Unknown usage mode: " + usage);
        }


 return credElement;
    }