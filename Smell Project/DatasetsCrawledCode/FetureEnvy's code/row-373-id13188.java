 private static long openProcessToken(int access) {
 try {
 return OpenProcessToken(GetCurrentProcess(), access);
        } catch (WindowsException x) {
 return 0L;
        }
    }