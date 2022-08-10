 public static String shortenDbName(String dbName, int desiredLength) {
 StringBuilder dbBuf = new StringBuilder(dbName);
 if (dbBuf.length() > desiredLength) {
 // remove one vowel at a time, starting at beginning
 for (int i = dbBuf.length() - 1; i > 0; i--) {
 // don't remove vowels that are at the beginning of the string (taken care of by the i > 0) or right after an underscore
 if (dbBuf.charAt(i - 1) == '_') {
 continue;
                }


 char curChar = dbBuf.charAt(i);
 if (vowelBag.indexOf(curChar) > 0) {
 dbBuf.deleteCharAt(i);
                }
            }
        }


 // remove all double underscores
 while (dbBuf.indexOf("__") > 0) {
 dbBuf.deleteCharAt(dbBuf.indexOf("__"));
        }


 while (dbBuf.length() > desiredLength) {
 boolean removedChars = false;


 int usIndex = dbBuf.lastIndexOf("_");
 while (usIndex > 0 && dbBuf.length() > desiredLength) {
 // if this is the first word in the group, don't pull letters off unless it is 4 letters or more
 int prevUsIndex = dbBuf.lastIndexOf("_", usIndex - 1);
 if (prevUsIndex < 0 && usIndex < 4) {
 break;
                }


 // don't remove characters to reduce the size two less than three characters between underscores
 if (prevUsIndex >= 0 && (usIndex - prevUsIndex) <= 4) {
 usIndex = prevUsIndex;
 continue;
                }


 // delete the second to last character instead of the last, better chance of being unique
 dbBuf.deleteCharAt(usIndex - 2);
 removedChars = true;
 if (usIndex > 2) {
 usIndex = dbBuf.lastIndexOf("_", usIndex - 2);
                } else {
 break;
                }
            }


 // now delete the char at the end of the string if necessary
 if (dbBuf.length() > desiredLength) {
 int removeIndex = dbBuf.length() - 1;
 int prevRemoveIndex = dbBuf.lastIndexOf("_", removeIndex - 1);
 // don't remove characters to reduce the size two less than two characters between underscores
 if (prevRemoveIndex < 0 || (removeIndex - prevRemoveIndex) >= 3) {
 // delete the second to last character instead of the last, better chance of being unique
 dbBuf.deleteCharAt(removeIndex - 1);
 removedChars = true;
                }
            }


 // remove all double underscores
 while (dbBuf.indexOf("__") > 0) {
 dbBuf.deleteCharAt(dbBuf.indexOf("__"));
 removedChars = true;
            }


 // if we didn't remove anything break out to avoid an infinite loop
 if (!removedChars) {
 break;
            }
        }


 // remove all double underscores
 while (dbBuf.indexOf("__") > 0) {
 dbBuf.deleteCharAt(dbBuf.indexOf("__"));
        }


 while (dbBuf.length() > desiredLength) {
 // still not short enough, get more aggressive
 // don't remove the first segment, just remove the second over and over until we are short enough
 int firstUs = dbBuf.indexOf("_");
 if (firstUs > 0) {
 int nextUs = dbBuf.indexOf("_", firstUs + 1);
 if (nextUs > 0) {
 //Debug.logInfo("couldn't shorten enough normally, removing second segment from " + dbBuf, module);
 dbBuf.delete(firstUs, nextUs);
                }
            }
        }


 //Debug.logInfo("Shortened " + dbName + " to " + dbBuf.toString(), module);
 return dbBuf.toString();
    }