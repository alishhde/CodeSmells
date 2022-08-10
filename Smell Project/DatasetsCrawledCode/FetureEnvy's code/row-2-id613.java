 protected boolean validateToken(String token) {
 try {
 SignedJWT signed = SignedJWT.parse(token);
 boolean sigValid = validateSignature(signed);
 if (!sigValid) {
 LOGGER.warn("Signature of JWT token could not be verified. Please check the public key");
 return false;
      }
 boolean expValid = validateExpiration(signed);
 if (!expValid) {
 LOGGER.warn("Expiration time validation of JWT token failed.");
 return false;
      }
 String currentUser = (String) org.apache.shiro.SecurityUtils.getSubject().getPrincipal();
 if (currentUser == null) {
 return true;
      }
 String cookieUser = signed.getJWTClaimsSet().getSubject();
 if (!cookieUser.equals(currentUser)) {
 return false;
      }
 return true;
    } catch (ParseException ex) {
 LOGGER.info("ParseException in validateToken", ex);
 return false;
    }
  }