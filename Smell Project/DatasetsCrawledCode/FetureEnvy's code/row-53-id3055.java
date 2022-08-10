 @Override
 protected void securityConfEdited() {
 // Need to call explicitly since we will not get notified of changes to local security.json
 cores.securityNodeChanged();
  }