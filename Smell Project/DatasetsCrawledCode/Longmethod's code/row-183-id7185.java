 private void validateArrayStep(String arrStep)
 throws QueryException
  {
 boolean wildAllowed  = true;    // * is allowed initially
 boolean digitAllowed = true;    // Digit is allowed as next char
 boolean commaAllowed = false;   // Comma is allowed as next char


 boolean afterDigit    = false;  // Last non-space was a digit
 boolean toAllowed     = false;  // Any space after digit allows "to"
 boolean afterTo       = false;  // After "to" expecting range end
 boolean toInProgress  = false;  // Prior char was 't' in "to"
 boolean spaceRequired = false;  // A whitespace is required (after "to")
 boolean digitRequired = false;  // Digit required after comma or "to"


 for (int i = 1; i < arrStep.length() - 1; ++i)
    {
 char currentChar = arrStep.charAt(i);


 if (currentChar == '*')
      {
 if (!wildAllowed)
 throwArrayException(arrStep);


 wildAllowed  = false;  // We've seen the only allowed wildcard
 digitAllowed = false;  // Only whitespace is allowed afterward
      }
 else if (currentChar == ',')
      {
 if (!commaAllowed)
 throwArrayException(arrStep);


 commaAllowed  = false;
 toAllowed     = false;
 afterDigit    = false;
 afterTo       = false;
 digitRequired = true;  // Next non-space must be a digit
      }
 else if ("0123456789".indexOf(currentChar) >= 0)
      {
 if (!digitAllowed)
 throwArrayException(arrStep);


 wildAllowed   = false; // Wildcard no longer allowed
 commaAllowed  = true;
 afterDigit    = true;
 digitRequired = false;
      }
 else if (" \t\n\r".indexOf(currentChar) >= 0)
      {
 // Whitespace not allowed when parsing "to"
 if (toInProgress)
 throwArrayException(arrStep);


 if (afterDigit)
        {
 // Last non-space was a digit - next non-space is "to" or comma
 digitAllowed = false;
 toAllowed    = !afterTo;
 commaAllowed = true;
        }
 else if (spaceRequired)
        {
 // This is the whitespace required after "to"
 digitAllowed  = true;
 spaceRequired = false;
 digitRequired = true;  // At least one digit must follow
        }
      }
 else if (currentChar == 't')
      {
 if (!toAllowed)
 throwArrayException(arrStep);


 toInProgress = true;  // Next char must be the 'o' in "to"
 commaAllowed = false;
 afterDigit   = false;
      }
 else if (currentChar == 'o')
      {
 if (!toInProgress)
 throwArrayException(arrStep);


 toInProgress  = false;
 toAllowed     = false;
 afterTo       = true;
 spaceRequired = true;  // "to" must be followed by whitespace
      }
 else
      {
 // Invalid character
 throwArrayException(arrStep);
      }
    }


 // Empty array or only whitespace found
 if (wildAllowed)
 throwArrayException(arrStep);


 // Incomplete "to" or comma sequence at end of subscript
 if (toInProgress || spaceRequired || digitRequired)
 throwArrayException(arrStep);
  }