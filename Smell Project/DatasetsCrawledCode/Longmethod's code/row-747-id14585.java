 @Override
 public String getVMPassword(final GetVMPasswordCmd cmd) {
 final Account caller = getCaller();


 final UserVmVO vm = _userVmDao.findById(cmd.getId());
 if (vm == null) {
 final InvalidParameterValueException ex = new InvalidParameterValueException("No VM with specified id found.");
 ex.addProxyObject(cmd.getId().toString(), "vmId");
 throw ex;
        }


 // make permission check
 _accountMgr.checkAccess(caller, null, true, vm);


 _userVmDao.loadDetails(vm);
 final String password = vm.getDetail("Encrypted.Password");
 if (password == null || password.equals("")) {
 final InvalidParameterValueException ex = new InvalidParameterValueException(
 "No password for VM with specified id found. " + "If VM is created from password enabled template and SSH keypair is assigned to VM then only password can be retrieved.");
 ex.addProxyObject(vm.getUuid(), "vmId");
 throw ex;
        }


 return password;
    }