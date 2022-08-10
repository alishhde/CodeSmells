 public String toString() {


 if (subject == null || pubKey == null || interval == null
            || issuer == null || algId == null || serialNum == null) {
 throw new NullPointerException("X.509 cert is incomplete");
        }
 StringBuilder sb = new StringBuilder();


 sb.append("[\n");
 sb.append("  " + version.toString() + "\n");
 sb.append("  Subject: " + subject.toString() + "\n");
 sb.append("  Signature Algorithm: " + algId.toString() + "\n");
 sb.append("  Key:  " + pubKey.toString() + "\n");
 sb.append("  " + interval.toString() + "\n");
 sb.append("  Issuer: " + issuer.toString() + "\n");
 sb.append("  " + serialNum.toString() + "\n");


 // optional v2, v3 extras
 if (issuerUniqueId != null) {
 sb.append("  Issuer Id:\n" + issuerUniqueId.toString() + "\n");
        }
 if (subjectUniqueId != null) {
 sb.append("  Subject Id:\n" + subjectUniqueId.toString() + "\n");
        }
 if (extensions != null) {
 Collection<Extension> allExts = extensions.getAllExtensions();
 Extension[] exts = allExts.toArray(new Extension[0]);
 sb.append("\nCertificate Extensions: " + exts.length);
 for (int i = 0; i < exts.length; i++) {
 sb.append("\n[" + (i+1) + "]: ");
 Extension ext = exts[i];
 try {
 if (OIDMap.getClass(ext.getExtensionId()) == null) {
 sb.append(ext.toString());
 byte[] extValue = ext.getExtensionValue();
 if (extValue != null) {
 DerOutputStream out = new DerOutputStream();
 out.putOctetString(extValue);
 extValue = out.toByteArray();
 HexDumpEncoder enc = new HexDumpEncoder();
 sb.append("Extension unknown: "
                                      + "DER encoded OCTET string =\n"
                                      + enc.encodeBuffer(extValue) + "\n");
                        }
                    } else
 sb.append(ext.toString()); //sub-class exists
                } catch (Exception e) {
 sb.append(", Error parsing this extension");
                }
            }
 Map<String,Extension> invalid = extensions.getUnparseableExtensions();
 if (invalid.isEmpty() == false) {
 sb.append("\nUnparseable certificate extensions: " + invalid.size());
 int i = 1;
 for (Extension ext : invalid.values()) {
 sb.append("\n[" + (i++) + "]: ");
 sb.append(ext);
                }
            }
        }
 sb.append("\n]");
 return sb.toString();
    }