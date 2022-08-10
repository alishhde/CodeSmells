 @Override
 public WikiPage getPageInfo( String page, int version )
 throws ProviderException
    {
 int latest = findLatestVersion(page);
 int realVersion;


 WikiPage p = null;


 if( version == WikiPageProvider.LATEST_VERSION ||
 version == latest ||
            (version == 1 && latest == -1) )
        {
 //
 // Yes, we need to talk to the top level directory
 // to get this version.
 //
 // I am listening to Press Play On Tape's guitar version of
 // the good old C64 "Wizardry" -tune at this moment.
 // Oh, the memories...
 //
 realVersion = (latest >= 0) ? latest : 1;


 p = super.getPageInfo( page, WikiPageProvider.LATEST_VERSION );


 if( p != null )
            {
 p.setVersion( realVersion );
            }
        }
 else
        {
 //
 //  The file is not the most recent, so we'll need to
 //  find it from the deep trenches of the "OLD" directory
 //  structure.
 //
 realVersion = version;
 File dir = findOldPageDir( page );


 if( !dir.exists() || !dir.isDirectory() )
            {
 return null;
            }


 File file = new File( dir, version+FILE_EXT );


 if( file.exists() )
            {
 p = new WikiPage( m_engine, page );


 p.setLastModified( new Date(file.lastModified()) );
 p.setVersion( version );
            }
        }


 //
 //  Get author and other metadata information
 //  (Modification date has already been set.)
 //
 if( p != null )
        {
 try
            {
 Properties props = getPageProperties( page );
 String author = props.getProperty( realVersion+".author" );
 if ( author == null )
                {
 // we might not have a versioned author because the
 // old page was last maintained by FileSystemProvider
 Properties props2 = getHeritagePageProperties( page );
 author = props2.getProperty( WikiPage.AUTHOR );
                }
 if ( author != null )
                {
 p.setAuthor( author );
                }


 String changenote = props.getProperty( realVersion+".changenote" );
 if( changenote != null ) p.setAttribute( WikiPage.CHANGENOTE, changenote );


 // Set the props values to the page attributes
 setCustomProperties(p, props);
            }
 catch( IOException e )
            {
 log.error( "Cannot get author for page"+page+": ", e );
            }
        }


 return p;
    }