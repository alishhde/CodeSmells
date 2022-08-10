 public String toString() {
 StringBuilder sb = new StringBuilder();


 sb.append(Constants.INDENT);
 sb.append("kdf: 0x");
 sb.append(Functions.toFullHexString(kdf));
 sb.append(Constants.NEWLINE);


 sb.append(Constants.INDENT);
 sb.append("pSharedDataLen: ");
 sb.append(pSharedData.length);
 sb.append(Constants.NEWLINE);


 sb.append(Constants.INDENT);
 sb.append("pSharedData: ");
 sb.append(Functions.toHexString(pSharedData));
 sb.append(Constants.NEWLINE);


 sb.append(Constants.INDENT);
 sb.append("pPublicDataLen: ");
 sb.append(pPublicData.length);
 sb.append(Constants.NEWLINE);


 sb.append(Constants.INDENT);
 sb.append("pPublicData: ");
 sb.append(Functions.toHexString(pPublicData));
 //buffer.append(Constants.NEWLINE);


 return sb.toString();
    }