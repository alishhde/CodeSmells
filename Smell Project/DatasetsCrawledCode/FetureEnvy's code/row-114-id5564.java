 public static JsonWebKey fromRSAPrivateKey(RSAPrivateKey pk, String algo, String kid) {
 JsonWebKey jwk = prepareRSAJwk(pk.getModulus(), algo, kid);
 String encodedPrivateExponent = Base64UrlUtility.encode(pk.getPrivateExponent().toByteArray());
 jwk.setProperty(JsonWebKey.RSA_PRIVATE_EXP, encodedPrivateExponent);
 if (pk instanceof RSAPrivateCrtKey) {
 RSAPrivateCrtKey pkCrt = (RSAPrivateCrtKey)pk;
 jwk.setProperty(JsonWebKey.RSA_PUBLIC_EXP,
 Base64UrlUtility.encode(pkCrt.getPublicExponent().toByteArray()));
 jwk.setProperty(JsonWebKey.RSA_FIRST_PRIME_FACTOR,
 Base64UrlUtility.encode(pkCrt.getPrimeP().toByteArray()));
 jwk.setProperty(JsonWebKey.RSA_SECOND_PRIME_FACTOR,
 Base64UrlUtility.encode(pkCrt.getPrimeQ().toByteArray()));
 jwk.setProperty(JsonWebKey.RSA_FIRST_PRIME_CRT,
 Base64UrlUtility.encode(pkCrt.getPrimeExponentP().toByteArray()));
 jwk.setProperty(JsonWebKey.RSA_SECOND_PRIME_CRT,
 Base64UrlUtility.encode(pkCrt.getPrimeExponentQ().toByteArray()));
 jwk.setProperty(JsonWebKey.RSA_FIRST_CRT_COEFFICIENT,
 Base64UrlUtility.encode(pkCrt.getCrtCoefficient().toByteArray()));
        }
 // "oth" can be populated too if needed
 return jwk;
    }