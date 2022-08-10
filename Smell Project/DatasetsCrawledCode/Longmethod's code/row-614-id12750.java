 @Override
 public boolean isValidSyntax( Object value )
    {
 String strValue;


 if ( value == null )
        {
 if ( LOG.isDebugEnabled() )
            {
 LOG.debug( I18n.err( I18n.ERR_13210_SYNTAX_INVALID, "null" ) );
            }
 
 return false;
        }


 if ( value instanceof String )
        {
 strValue = ( String ) value;
        }
 else if ( value instanceof byte[] )
        {
 strValue = Strings.utf8ToString( ( byte[] ) value );
        }
 else
        {
 strValue = value.toString();
        }


 // We must have at least '(cp)', '(xr)' or '(ca)'
 if ( strValue.length() < 4 )
        {
 if ( LOG.isDebugEnabled() )
            {
 LOG.debug( I18n.err( I18n.ERR_13210_SYNTAX_INVALID, value ) );
            }
 
 return false;
        }


 // Check the opening and closing parenthesis
 if ( ( strValue.charAt( 0 ) != '(' )
            || ( strValue.charAt( strValue.length() - 1 ) != ')' ) )
        {
 if ( LOG.isDebugEnabled() )
            {
 LOG.debug( I18n.err( I18n.ERR_13210_SYNTAX_INVALID, value ) );
            }
 
 return false;
        }


 Set<String> keywords = new HashSet<>();
 int len = strValue.length() - 1;
 boolean needKeyword = true;


 // 
 for ( int i = 1; i < len; /* */)
        {
 // Skip spaces
 while ( ( i < len ) && ( strValue.charAt( i ) == ' ' ) )
            {
 i++;
            }


 int pos = i;


 // Search for a keyword
 while ( ( i < len ) && Chars.isAlphaASCII( strValue, pos ) )
            {
 pos++;
            }


 if ( pos == i )
            {
 // No keyword : error
 if ( LOG.isDebugEnabled() )
                {
 LOG.debug( I18n.err( I18n.ERR_13210_SYNTAX_INVALID, value ) );
                }
 
 return false;
            }


 String keyword = strValue.substring( i, pos );
 i = pos;


 if ( !DSE_BITS.contains( keyword ) )
            {
 // Unknown keyword
 if ( LOG.isDebugEnabled() )
                {
 LOG.debug( I18n.err( I18n.ERR_13210_SYNTAX_INVALID, value ) );
                }
 
 return false;
            }


 // Check that the keyword has not been met
 if ( keywords.contains( keyword ) )
            {
 if ( LOG.isDebugEnabled() )
                {
 LOG.debug( I18n.err( I18n.ERR_13210_SYNTAX_INVALID, value ) );
                }
 
 return false;
            }


 keywords.add( keyword );
 needKeyword = false;


 // Skip spaces
 while ( ( i < len ) && ( strValue.charAt( i ) == ' ' ) )
            {
 i++;
            }


 // Do we have another keyword ?
 if ( ( i < len ) && ( strValue.charAt( i ) == '$' ) )
            {
 // yes
 i++;
 needKeyword = true;
            }
        }


 // We are done
 if ( LOG.isDebugEnabled() )
        {
 if ( needKeyword )
            {
 LOG.debug( I18n.err( I18n.ERR_13210_SYNTAX_INVALID, value ) );
            }
 else
            {
 LOG.debug( I18n.msg( I18n.MSG_13701_SYNTAX_VALID, value ) );
            }
        }


 return !needKeyword;
    }