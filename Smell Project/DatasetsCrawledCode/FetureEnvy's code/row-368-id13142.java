 private void writeJSON( final Writer pw, final ServiceReference service, final boolean fullDetails, final Locale locale, final String filter )
 throws IOException
    {
 final ServiceReference[] allServices = this.getServices(filter);
 final String statusLine = getStatusLine( allServices );


 final ServiceReference[] services = ( service != null ) ? new ServiceReference[]
                { service } : allServices;


 final JSONWriter jw = new JSONWriter( pw );


 jw.object();


 jw.key( "status" );
 jw.value( statusLine );


 jw.key( "serviceCount" );
 jw.value( allServices.length );


 jw.key( "data" );


 jw.array();


 for ( int i = 0; i < services.length; i++ )
                {
 serviceInfo( jw, services[i], fullDetails || service != null, locale );
                }


 jw.endArray();


 jw.endObject();


    }