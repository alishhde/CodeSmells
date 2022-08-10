 public String command() {
 String flags;
 if (add) {
 flags = " +FLAGS ";
            } else if (subtract) {
 flags = " -FLAGS ";
            } else {
 flags = " FLAGS ";
            }
 if (silent) {
 flags = flags + ".SILENT";
            }
 return "STORE " + msn + flags + this.flags + ")";
        }