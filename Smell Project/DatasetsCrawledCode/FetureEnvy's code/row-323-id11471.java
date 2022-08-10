 protected void sendMessage(Connection cnx) throws Exception {
 if (cnx.getServer().getRequiresCredentials()) {
 // Security is enabled on client as well as on server
 getMessage().setMessageHasSecurePartFlag();
 long userId = -1;


 if (UserAttributes.userAttributes.get() == null) { // single user mode
 userId = cnx.getServer().getUserId();
      } else { // multi user mode
 Object id = UserAttributes.userAttributes.get().getServerToId().get(cnx.getServer());
 if (id == null) {
 // This will ensure that this op is retried on another server, unless
 // the retryCount is exhausted. Fix for Bug 41501
 throw new ServerConnectivityException("Connection error while authenticating user");
        }
 userId = (Long) id;
      }
 HeapDataOutputStream hdos = new HeapDataOutputStream(Version.CURRENT);
 try {
 hdos.writeLong(cnx.getConnectionID());
 hdos.writeLong(userId);
 getMessage().setSecurePart(((ConnectionImpl) cnx).encryptBytes(hdos.toByteArray()));
      } finally {
 hdos.close();
      }
    }
 getMessage().send(false);
  }