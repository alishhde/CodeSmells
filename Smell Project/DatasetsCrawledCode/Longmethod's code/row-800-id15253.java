 private void processEmail(EmailData emailData) {
 if (logger.isTraceEnabled()) {
 logger.trace("Entered MailManager:processEmail");
    }


 if (mailHost == null || mailHost.length() == 0 || emailData == null
        || mailToAddresses.length == 0) {
 logger.error("Required mail server configuration is not specfied.");
 if (logger.isDebugEnabled()) {
 logger.debug("Exited MailManager:processEmail: Not sending email as conditions not met");
      }
 return;
    }


 Session session = Session.getDefaultInstance(getMailHostConfiguration());
 MimeMessage mimeMessage = new MimeMessage(session);
 String subject = emailData.subject;
 String message = emailData.message;
 String mailToList = getMailToAddressesAsString();


 try {
 for (int i = 0; i < mailToAddresses.length; i++) {
 mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailToAddresses[i]));
      }


 if (subject == null) {
 subject = "Alert from GemFire Admin Agent";
      }
 mimeMessage.setSubject(subject);


 if (message == null) {
 message = "";
      }
 mimeMessage.setText(message);


 Transport.send(mimeMessage);
 logger.info("Email sent to {}. Subject: {}, Content: {}",
 new Object[] {mailToList, subject, message});
    } catch (VirtualMachineError err) {
 SystemFailure.initiateFailure(err);
 // If this ever returns, rethrow the error. We're poisoned
 // now, so don't let this thread continue.
 throw err;
    } catch (Throwable ex) {
 // Whenever you catch Error or Throwable, you must also
 // catch VirtualMachineError (see above). However, there is
 // _still_ a possibility that you are dealing with a cascading
 // error condition, so you also need to check to see if the JVM
 // is still usable:
 SystemFailure.checkFailure();
 StringBuilder buf = new StringBuilder();
 buf.append("An exception occurred while sending email.");
 buf.append(
 "Unable to send email. Please check your mail settings and the log file.");
 buf.append("\n\n").append(
 String.format("Exception message: %s", ex.getMessage()));
 buf.append("\n\n").append(
 "Following email was not delivered:");
 buf.append("\n\t")
          .append(String.format("Mail Host: %s", mailHost));
 buf.append("\n\t").append(String.format("From: %s", mailFrom));
 buf.append("\n\t").append(String.format("To: %s", mailToList));
 buf.append("\n\t").append(String.format("Subject: %s", subject));
 buf.append("\n\t").append(String.format("Content: %s", message));


 logger.error(buf.toString(), ex);
    }
 if (logger.isTraceEnabled()) {
 logger.trace("Exited MailManager:processEmail");
    }
  }