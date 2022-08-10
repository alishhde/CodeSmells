 private static byte[] decodeUrl( byte[] bytes ) throws UrlDecoderException
    {
 if ( bytes == null )
        {
 return Strings.EMPTY_BYTES;
        }


 ByteArrayOutputStream buffer = new ByteArrayOutputStream();


 for ( int i = 0; i < bytes.length; i++ )
        {
 int b = bytes[i];


 if ( b == '%' )
            {
 try
                {
 int u = Character.digit( ( char ) bytes[++i], 16 );
 int l = Character.digit( ( char ) bytes[++i], 16 );


 if ( ( u == -1 ) || ( l == -1 ) )
                    {
 throw new UrlDecoderException( I18n.err( I18n.ERR_13040_INVALID_URL_ENCODING ) );
                    }


 buffer.write( ( char ) ( ( u << 4 ) + l ) );
                }
 catch ( ArrayIndexOutOfBoundsException aioobe )
                {
 throw new UrlDecoderException( I18n.err( I18n.ERR_13040_INVALID_URL_ENCODING ), aioobe );
                }
            }
 else
            {
 buffer.write( b );
            }
        }


 return buffer.toByteArray();
    }