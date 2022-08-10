 @Override
 public String sendMessage(final Map<String, String> headers,
 final int type,
 final String body,
 boolean durable,
 final String user,
 final String password) throws Exception {
 if (AuditLogger.isEnabled()) {
 AuditLogger.sendMessage(this, null, headers, type, body, durable, user, "****");
      }
 try {
 return sendMessage(addressInfo.getName(), server, headers, type, body, durable, user, password);
      } catch (Exception e) {
 e.printStackTrace();
 throw new IllegalStateException(e.getMessage());
      }
   }