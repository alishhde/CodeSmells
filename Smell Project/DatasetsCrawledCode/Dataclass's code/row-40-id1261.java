public interface ServletConstants {
 String PAGE_HEADER
          = "<!DOCTYPE html>\n" +
 "<html lang=\"en\">\n" +
 "<head>\n" +
 "    <meta charset=\"UTF-8\">\n" +
 "    <title>Weblogic Monitoring Exporter</title>\n" +
 "</head>\n" +
 "<body>";


 // The locations of the servlets relative to the web app
 String MAIN_PAGE = "";
 String METRICS_PAGE = "metrics";
 String CONFIGURATION_PAGE = "configure";


 /** The header used by a web client to send its authentication credentials. **/
 String AUTHENTICATION_HEADER = "Authorization";


 /** The header used by a web client to send cookies as part of a request. */
 String COOKIE_HEADER = "Cookie";


 // The field which defines the configuration update action
 String EFFECT_OPTION = "effect";


 // The possible values for the effect
 String DEFAULT_ACTION = ServletConstants.REPLACE_ACTION;
 String REPLACE_ACTION = "replace";
 String APPEND_ACTION = "append";
}