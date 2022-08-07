public final class LdapProtocolUtils
{
 /** A delimiter for the replicaId */
 public static final String COOKIE_DELIM = ",";


 /** the prefix for replicaId value */
 public static final String REPLICA_ID_PREFIX = "rid=";


 public static final int REPLICA_ID_PREFIX_LEN = REPLICA_ID_PREFIX.length();


 /** the prefix for Csn value */
 public static final String CSN_PREFIX = "csn=";


 private static final int CSN_PREFIX_LEN = CSN_PREFIX.length();


 private static final Logger LOG = LoggerFactory.getLogger( LdapProtocolUtils.class );




 private LdapProtocolUtils()
    {
    }




 /**
     * Extracts request controls from a request to populate into an
     * OperationContext.
     *
     * @param opContext the context to populate with request controls
     * @param request the request to extract controls from
     */
 public static void setRequestControls( OperationContext opContext, Request request )
    {
 if ( request.getControls() != null )
        {
 opContext
                .addRequestControls( request.getControls().values().toArray( LdapProtocolConstants.EMPTY_CONTROLS ) );
        }
    }




 /**
     * Extracts response controls from a an OperationContext to populate into
     * a Response object.
     *
     * @param opContext the context to extract controls from
     * @param response the response to populate with response controls
     */
 public static void setResponseControls( OperationContext opContext, Response response )
    {
 response.addAllControls( opContext.getResponseControls() );
    }




 public static byte[] createCookie( int replicaId, String csn )
    {
 // the syncrepl cookie format (compatible with OpenLDAP)
 // rid=nn,csn=xxxz
 String replicaIdStr = StringUtils.leftPad( Integer.toString( replicaId ), 3, '0' );
 return Strings.getBytesUtf8( REPLICA_ID_PREFIX + replicaIdStr + COOKIE_DELIM + CSN_PREFIX + csn );
    }




 /**
     * Check the cookie syntax. A cookie must have the following syntax :
     * { rid={replicaId},csn={CSN} }
     *
     * @param cookieString The cookie
     * @return <tt>true</tt> if the cookie is valid
     */
 public static boolean isValidCookie( String cookieString )
    {
 if ( ( cookieString == null ) || ( cookieString.trim().length() == 0 ) )
        {
 return false;
        }


 int pos = cookieString.indexOf( COOKIE_DELIM );


 // position should start from REPLICA_ID_PREFIX_LEN or higher cause a cookie can be
 // like "rid=0,csn={csn}" or "rid=11,csn={csn}"
 if ( pos <= REPLICA_ID_PREFIX_LEN )
        {
 return false;
        }


 String replicaId = cookieString.substring( REPLICA_ID_PREFIX_LEN, pos );


 try
        {
 Integer.parseInt( replicaId );
        }
 catch ( NumberFormatException e )
        {
 LOG.debug( "Failed to parse the replica id {}", replicaId );
 return false;
        }


 if ( pos == cookieString.length() )
        {
 return false;
        }


 String csnString = cookieString.substring( pos + 1 + CSN_PREFIX_LEN );


 return Csn.isValid( csnString );
    }




 /**
     * returns the CSN present in cookie
     *
     * @param cookieString the cookie
     * @return The CSN
     */
 public static String getCsn( String cookieString )
    {
 int pos = cookieString.indexOf( COOKIE_DELIM );
 return cookieString.substring( pos + 1 + CSN_PREFIX_LEN );
    }




 /**
     * returns the replica id present in cookie
     *
     * @param cookieString  the cookie
     * @return The replica Id
     */
 public static int getReplicaId( String cookieString )
    {
 String replicaId = cookieString.substring( REPLICA_ID_PREFIX_LEN, cookieString.indexOf( COOKIE_DELIM ) );


 return Integer.parseInt( replicaId );
    }
}