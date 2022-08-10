 NativeKeyHolder(P11Key p11Key, long keyID, Session keySession,
 boolean extractKeyInfo, boolean isTokenObject) {
 this.p11Key = p11Key;
 this.keyID = keyID;
 this.refCount = -1;
 byte[] ki = null;
 if (isTokenObject) {
 this.ref = null;
        } else {
 this.ref = new SessionKeyRef(p11Key, keyID, keySession);


 // Try extracting key info, if any error, disable it
 Token token = p11Key.token;
 if (extractKeyInfo) {
 try {
 if (p11Key.sensitive && nativeKeyWrapperKeyID == 0) {
 synchronized(NativeKeyHolder.class) {
 // Create a global wrapping/unwrapping key
 CK_ATTRIBUTE[] wrappingAttributes = token.getAttributes
                                (O_GENERATE, CKO_SECRET_KEY, CKK_AES, new CK_ATTRIBUTE[] {
 new CK_ATTRIBUTE(CKA_CLASS, CKO_SECRET_KEY),
 new CK_ATTRIBUTE(CKA_VALUE_LEN, 256 >> 3),
                                });
 Session wrappingSession = null;
 try {
 wrappingSession = token.getObjSession();
 nativeKeyWrapperKeyID = token.p11.C_GenerateKey
                                    (wrappingSession.id(),
 new CK_MECHANISM(CKM_AES_KEY_GEN),
 wrappingAttributes);
 byte[] iv = new byte[16];
 JCAUtil.getSecureRandom().nextBytes(iv);
 nativeKeyWrapperMechanism = new CK_MECHANISM
                                    (CKM_AES_CBC_PAD, iv);
                            } catch (PKCS11Exception e) {
 // best effort
                            } finally {
 token.releaseSession(wrappingSession);
                            }
                        }
                    }
 Session opSession = null;
 try {
 opSession = token.getOpSession();
 ki = p11Key.token.p11.getNativeKeyInfo(opSession.id(),
 keyID, nativeKeyWrapperKeyID, nativeKeyWrapperMechanism);
                    } catch (PKCS11Exception e) {
 // best effort
                    } finally {
 token.releaseSession(opSession);
                    }
                } catch (PKCS11Exception e) {
 // best effort
                }
            }
        }
 this.nativeKeyInfo = ((ki == null || ki.length == 0)? null : ki);
    }