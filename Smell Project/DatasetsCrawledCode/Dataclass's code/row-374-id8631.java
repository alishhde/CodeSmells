 public static final class SecretKeyEntry implements Entry {


 private final SecretKey sKey;


 /**
         * Constructs a <code>SecretKeyEntry</code> with a
         * <code>SecretKey</code>.
         *
         * @param secretKey the <code>SecretKey</code>
         *
         * @exception NullPointerException if <code>secretKey</code>
         *      is <code>null</code>
         */
 public SecretKeyEntry(SecretKey secretKey) {
 if (secretKey == null) {
 throw new NullPointerException("invalid null input");
            }
 this.sKey = secretKey;
        }


 /**
         * Gets the <code>SecretKey</code> from this entry.
         *
         * @return the <code>SecretKey</code> from this entry
         */
 public SecretKey getSecretKey() {
 return sKey;
        }


 /**
         * Returns a string representation of this SecretKeyEntry.
         * @return a string representation of this SecretKeyEntry.
         */
 public String toString() {
 return "Secret key entry with algorithm " + sKey.getAlgorithm();
        }
    }