 @Override
 public String vmExecutablePath(Vm vm) {
 File homeDir = vmHomeDir(vm);
 for (String extension : EXECUTABLE_EXTENSIONS) {
 for (String dir : EXECUTABLE_DIRS) {
 File file = new File(homeDir, dir + vm.executable() + extension);
 if (file.isFile()) {
 return file.getAbsolutePath();
        }
      }
    }
 throw new VirtualMachineException(
 String.format(
 "VM executable %s for VM %s not found under home dir %s",
 vm.executable(), vm, homeDir));
  }