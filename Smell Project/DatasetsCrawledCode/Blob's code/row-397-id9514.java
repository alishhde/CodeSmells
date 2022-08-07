 static class HttpsTokenInInterceptor extends AbstractPhaseInterceptor<Message> {
 HttpsTokenInInterceptor() {
 super(Phase.PRE_STREAM);
 addBefore(WSS4JStaxInInterceptor.class.getName());
        }


 public void handleMessage(Message message) throws Fault {
 AssertionInfoMap aim = message.get(AssertionInfoMap.class);
 // extract Assertion information
 if (aim != null) {
 Collection<AssertionInfo> ais =
 PolicyUtils.getAllAssertionsByLocalname(aim, SPConstants.HTTPS_TOKEN);
 boolean requestor = isRequestor(message);
 if (ais.isEmpty()) {
 if (!requestor) {
 try {
 assertNonHttpsTransportToken(message);
                        } catch (XMLSecurityException e) {
 LOG.fine(e.getMessage());
                        }
                    }
 return;
                }
 if (!requestor) {
 try {
 assertHttps(aim, ais, message);
                    } catch (XMLSecurityException e) {
 LOG.fine(e.getMessage());
                    }
 // Store the TLS principal on the message context
 SecurityContext sc = message.get(SecurityContext.class);
 if (sc == null || sc.getUserPrincipal() == null) {
 TLSSessionInfo tlsInfo = message.get(TLSSessionInfo.class);
 if (tlsInfo != null && tlsInfo.getPeerCertificates() != null
                                && tlsInfo.getPeerCertificates().length > 0
                                && (tlsInfo.getPeerCertificates()[0] instanceof X509Certificate)
                        ) {
 X509Certificate cert = (X509Certificate)tlsInfo.getPeerCertificates()[0];
 message.put(
 SecurityContext.class, createSecurityContext(cert.getSubjectX500Principal())
                            );
                        }
                    }


                } else {
 //client side should be checked on the way out
 for (AssertionInfo ai : ais) {
 ai.setAsserted(true);
                    }


 PolicyUtils.assertPolicy(aim, SPConstants.HTTP_DIGEST_AUTHENTICATION);
 PolicyUtils.assertPolicy(aim, SPConstants.HTTP_BASIC_AUTHENTICATION);
 PolicyUtils.assertPolicy(aim, SPConstants.REQUIRE_CLIENT_CERTIFICATE);
                }
            }
        }


 private void assertHttps(
 AssertionInfoMap aim,
 Collection<AssertionInfo> ais,
 Message message
        ) throws XMLSecurityException {
 List<SecurityEvent> securityEvents = getSecurityEventList(message);
 AuthorizationPolicy policy = message.get(AuthorizationPolicy.class);


 for (AssertionInfo ai : ais) {
 boolean asserted = true;
 HttpsToken token = (HttpsToken)ai.getAssertion();


 HttpsTokenSecurityEvent httpsTokenSecurityEvent = new HttpsTokenSecurityEvent();


 Map<String, List<String>> headers = getProtocolHeaders(message);
 if (token.getAuthenticationType() == HttpsToken.AuthenticationType.HttpBasicAuthentication) {
 List<String> auth = headers.get("Authorization");
 if (auth == null || auth.isEmpty()
                        || !auth.get(0).startsWith("Basic")) {
 asserted = false;
                    } else {
 httpsTokenSecurityEvent.setAuthenticationType(
 HttpsTokenSecurityEvent.AuthenticationType.HttpBasicAuthentication
                        );
 HttpsSecurityTokenImpl httpsSecurityToken =
 new HttpsSecurityTokenImpl(true, policy.getUserName());
 httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
 httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
 PolicyUtils.assertPolicy(aim,
 new QName(token.getName().getNamespaceURI(),
 SPConstants.HTTP_BASIC_AUTHENTICATION));
                    }
                }
 if (token.getAuthenticationType() == HttpsToken.AuthenticationType.HttpDigestAuthentication) {
 List<String> auth = headers.get("Authorization");
 if (auth == null || auth.isEmpty()
                        || !auth.get(0).startsWith("Digest")) {
 asserted = false;
                    } else {
 httpsTokenSecurityEvent.setAuthenticationType(
 HttpsTokenSecurityEvent.AuthenticationType.HttpDigestAuthentication
                        );
 HttpsSecurityTokenImpl httpsSecurityToken =
 new HttpsSecurityTokenImpl(false, policy.getUserName());
 httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
 httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
 PolicyUtils.assertPolicy(aim,
 new QName(token.getName().getNamespaceURI(),
 SPConstants.HTTP_DIGEST_AUTHENTICATION));
                    }
                }


 TLSSessionInfo tlsInfo = message.get(TLSSessionInfo.class);
 if (tlsInfo != null) {
 if (token.getAuthenticationType()
                        == HttpsToken.AuthenticationType.RequireClientCertificate) {
 if (tlsInfo.getPeerCertificates() == null
                            || tlsInfo.getPeerCertificates().length == 0) {
 asserted = false;
                        } else {
 PolicyUtils.assertPolicy(aim,
 new QName(token.getName().getNamespaceURI(),
 SPConstants.REQUIRE_CLIENT_CERTIFICATE));
                        }
                    }


 if (tlsInfo.getPeerCertificates() != null && tlsInfo.getPeerCertificates().length > 0) {
 httpsTokenSecurityEvent.setAuthenticationType(
 HttpsTokenSecurityEvent.AuthenticationType.HttpsClientCertificateAuthentication
                        );
 HttpsSecurityTokenImpl httpsSecurityToken =
 new HttpsSecurityTokenImpl((X509Certificate)tlsInfo.getPeerCertificates()[0]);
 httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
 httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
                    } else if (httpsTokenSecurityEvent.getAuthenticationType() == null) {
 httpsTokenSecurityEvent.setAuthenticationType(
 HttpsTokenSecurityEvent.AuthenticationType.HttpsNoAuthentication
                        );
 HttpsSecurityTokenImpl httpsSecurityToken = new HttpsSecurityTokenImpl();
 httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
 httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
                    }
                } else {
 asserted = false;
                }


 ai.setAsserted(asserted);


 if (asserted) {
 securityEvents.add(httpsTokenSecurityEvent);
                }
            }
        }


 // We might have an IssuedToken TransportToken
 private void assertNonHttpsTransportToken(Message message) throws XMLSecurityException {
 TLSSessionInfo tlsInfo = message.get(TLSSessionInfo.class);
 if (tlsInfo != null) {
 HttpsTokenSecurityEvent httpsTokenSecurityEvent = new HttpsTokenSecurityEvent();
 if (tlsInfo.getPeerCertificates() != null && tlsInfo.getPeerCertificates().length > 0) {
 httpsTokenSecurityEvent.setAuthenticationType(
 HttpsTokenSecurityEvent.AuthenticationType.HttpsClientCertificateAuthentication
                    );
 HttpsSecurityTokenImpl httpsSecurityToken =
 new HttpsSecurityTokenImpl((X509Certificate)tlsInfo.getPeerCertificates()[0]);
 httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
 httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
                } else if (httpsTokenSecurityEvent.getAuthenticationType() == null) {
 httpsTokenSecurityEvent.setAuthenticationType(
 HttpsTokenSecurityEvent.AuthenticationType.HttpsNoAuthentication
                    );
 HttpsSecurityTokenImpl httpsSecurityToken = new HttpsSecurityTokenImpl();
 httpsSecurityToken.addTokenUsage(WSSecurityTokenConstants.TOKENUSAGE_MAIN_SIGNATURE);
 httpsTokenSecurityEvent.setSecurityToken(httpsSecurityToken);
                }
 List<SecurityEvent> securityEvents = getSecurityEventList(message);
 securityEvents.add(httpsTokenSecurityEvent);
            }
        }


 private List<SecurityEvent> getSecurityEventList(Message message) {
 @SuppressWarnings("unchecked")
 List<SecurityEvent> securityEvents =
                (List<SecurityEvent>) message.getExchange().get(SecurityEvent.class.getName() + ".out");
 if (securityEvents == null) {
 securityEvents = new ArrayList<>();
 message.getExchange().put(SecurityEvent.class.getName() + ".out", securityEvents);
            }


 return securityEvents;
        }


 private SecurityContext createSecurityContext(final Principal p) {
 return new SecurityContext() {
 public Principal getUserPrincipal() {
 return p;
                }
 public boolean isUserInRole(String role) {
 return false;
                }
            };
        }
    }