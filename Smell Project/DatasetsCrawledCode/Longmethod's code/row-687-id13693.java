 private void parseQuotedValue(byte prev) throws IOException {
 final byte newLine = this.newLine;
 final byte delimiter = this.delimiter;
 final TextOutput output = this.output;
 final TextInput input = this.input;
 final byte quote = this.quote;


 ch = input.nextCharNoNewLineCheck();


 while (!(prev == quote && (ch == delimiter || ch == newLine || isWhite(ch)))) {
 if (ch != quote) {
 if (prev == quote) { // unescaped quote detected
 if (parseUnescapedQuotes) {
 output.append(quote);
 output.append(ch);
 parseQuotedValue(ch);
 break;
          } else {
 throw new TextParsingException(
 context,
 "Unescaped quote character '"
                    + quote
                    + "' inside quoted value of CSV field. To allow unescaped quotes, set 'parseUnescapedQuotes' to 'true' in the CSV parser settings. Cannot parse CSV input.");
          }
        }
 output.append(ch);
 prev = ch;
      } else if (prev == quoteEscape) {
 output.append(quote);
 prev = NULL_BYTE;
      } else {
 prev = ch;
      }
 ch = input.nextCharNoNewLineCheck();
    }


 // Handles whitespaces after quoted value:
 // Whitespaces are ignored (i.e., ch <= ' ') if they are not used as delimiters (i.e., ch != ' ')
 // For example, in tab-separated files (TSV files), '\t' is used as delimiter and should not be ignored
 // Content after whitespaces may be parsed if 'parseUnescapedQuotes' is enabled.
 if (ch != newLine && ch <= ' ' && ch != delimiter) {
 final DrillBuf workBuf = this.workBuf;
 workBuf.resetWriterIndex();
 do {
 // saves whitespaces after value
 workBuf.writeByte(ch);
 ch = input.nextChar();
 // found a new line, go to next record.
 if (ch == newLine) {
 return;
        }
      } while (ch <= ' ' && ch != delimiter);


 // there's more stuff after the quoted value, not only empty spaces.
 if (!(ch == delimiter || ch == newLine) && parseUnescapedQuotes) {


 output.append(quote);
 for(int i =0; i < workBuf.writerIndex(); i++){
 output.append(workBuf.getByte(i));
        }
 // the next character is not the escape character, put it there
 if (ch != quoteEscape) {
 output.append(ch);
        }
 // sets this character as the previous character (may be escaping)
 // calls recursively to keep parsing potentially quoted content
 parseQuotedValue(ch);
      }
    }


 if (!(ch == delimiter || ch == newLine)) {
 throw new TextParsingException(context, "Unexpected character '" + ch
          + "' following quoted value of CSV field. Expecting '" + delimiter + "'. Cannot parse CSV input.");
    }
  }