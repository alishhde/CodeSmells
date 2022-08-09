 static class Solaris extends OperatingSystem.Unix {
 @Override
 public String getFamilyName() {
 return "solaris";
        }


 @Override
 protected String getOsPrefix() {
 return "sunos";
        }


 @Override
 protected String getArch() {
 String arch = System.getProperty("os.arch");
 if (arch.equals("i386") || arch.equals("x86")) {
 return "x86";
            }
 return super.getArch();
        }
    }