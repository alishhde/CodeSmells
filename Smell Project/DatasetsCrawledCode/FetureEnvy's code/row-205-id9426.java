 private void generateReport( Locale locale, LinkcheckModel linkcheckModel )
    {
 LinkcheckReportGenerator reportGenerator = new LinkcheckReportGenerator( i18n );


 reportGenerator.setExcludedHttpStatusErrors( excludedHttpStatusErrors );
 reportGenerator.setExcludedHttpStatusWarnings( excludedHttpStatusWarnings );
 reportGenerator.setExcludedLinks( excludedLinks );
 reportGenerator.setExcludedPages( excludedPages );
 reportGenerator.setHttpFollowRedirect( httpFollowRedirect );
 reportGenerator.setHttpMethod( httpMethod );
 reportGenerator.setOffline( offline );


 reportGenerator.generateReport( locale, linkcheckModel, getSink() );
 closeReport();


 // Copy the images
 copyStaticResources();
    }