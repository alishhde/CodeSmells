 @Override
 public int hashCode() {
 final int prime = 31;
 int result = 1;
 result = prime * result + (this.alias == null ? 0 : this.alias.hashCode());
 result = prime * result + (this.ciphers == null ? 0 : this.ciphers.hashCode());
 result = prime * result + (this.hostnameVerification ? 1231 : 1237);
 result = prime * result + (this.keyStore == null ? 0 : this.keyStore.hashCode());
 result = prime * result + Arrays.hashCode(this.keyStorePassword);
 result = prime * result + (this.protocol == null ? 0 : this.protocol.hashCode());
 result = prime * result + (this.sslManagerOpts == null ? 0 : this.sslManagerOpts.hashCode());
 result = prime * result + (this.trustStore == null ? 0 : this.trustStore.hashCode());
 return result;
    }