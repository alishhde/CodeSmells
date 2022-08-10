 public void updateState(X509Certificate cert)
 throws CertificateException, IOException, CertPathValidatorException {


 if (cert == null) {
 return;
        }


 /* update subject DN */
 subjectDN = cert.getSubjectX500Principal();


 /* check for key needing to inherit alg parameters */
 X509CertImpl icert = X509CertImpl.toImpl(cert);
 PublicKey newKey = cert.getPublicKey();
 if (PKIX.isDSAPublicKeyWithoutParams(newKey)) {
 newKey = BasicChecker.makeInheritedParamsKey(newKey, pubKey);
        }


 /* update subject public key */
 pubKey = newKey;


 /*
         * if this is a trusted cert (init == true), then we
         * don't update any of the remaining fields
         */
 if (init) {
 init = false;
 return;
        }


 /* update subject key identifier */
 subjKeyId = icert.getSubjectKeyIdentifierExtension();


 /* update crlSign */
 crlSign = RevocationChecker.certCanSignCrl(cert);


 /* update current name constraints */
 if (nc != null) {
 nc.merge(icert.getNameConstraintsExtension());
        } else {
 nc = icert.getNameConstraintsExtension();
 if (nc != null) {
 // Make sure we do a clone here, because we're probably
 // going to modify this object later and we don't want to
 // be sharing it with a Certificate object!
 nc = (NameConstraintsExtension) nc.clone();
            }
        }


 /* update policy state variables */
 explicitPolicy =
 PolicyChecker.mergeExplicitPolicy(explicitPolicy, icert, false);
 policyMapping =
 PolicyChecker.mergePolicyMapping(policyMapping, icert);
 inhibitAnyPolicy =
 PolicyChecker.mergeInhibitAnyPolicy(inhibitAnyPolicy, icert);
 certIndex++;


 /*
         * Update remaining CA certs
         */
 remainingCACerts =
 ConstraintsChecker.mergeBasicConstraints(cert, remainingCACerts);


 init = false;
    }