 private static int characterSet(String stmt, int offset, int depth) {
 if (stmt.length() > offset + 14) {
 char c1 = stmt.charAt(++offset);
 char c2 = stmt.charAt(++offset);
 char c3 = stmt.charAt(++offset);
 char c4 = stmt.charAt(++offset);
 char c5 = stmt.charAt(++offset);
 char c6 = stmt.charAt(++offset);
 char c7 = stmt.charAt(++offset);
 char c8 = stmt.charAt(++offset);
 char c9 = stmt.charAt(++offset);
 char c10 = stmt.charAt(++offset);
 char c11 = stmt.charAt(++offset);
 char c12 = stmt.charAt(++offset);
 char c13 = stmt.charAt(++offset);
 char c14 = stmt.charAt(++offset);
 if ((c1 == 'H' || c1 == 'h') && (c2 == 'A' || c2 == 'a') && (c3 == 'R' || c3 == 'r')
                    && (c4 == 'A' || c4 == 'a') && (c5 == 'C' || c5 == 'c') && (c6 == 'T' || c6 == 't')
                    && (c7 == 'E' || c7 == 'e') && (c8 == 'R' || c8 == 'r') && (c9 == '_')
                    && (c10 == 'S' || c10 == 's') && (c11 == 'E' || c11 == 'e') && (c12 == 'T' || c12 == 't')
                    && (c13 == '_')) {
 switch (c14) {
 case 'R':
 case 'r':
 return characterSetResults(stmt, offset);
 case 'C':
 case 'c':
 return characterSetC(stmt, offset);
 default:
 return OTHER;
                }
            }
        }
 return OTHER;
    }