 public Map< String, String > listLanguages(PageContext pageContext)
    {
 Map< String, String > resultMap = new LinkedHashMap<>();
 String clientLanguage = ((HttpServletRequest) pageContext.getRequest()).getLocale().toString();


 List< String > entries = ClassUtil.classpathEntriesUnder( DIRECTORY );
 for( String name : entries ) {
 if ( name.equals( I18NRESOURCE_EN ) ||
                    (name.startsWith( I18NRESOURCE_PREFIX ) && name.endsWith( I18NRESOURCE_SUFFIX ) ) )
            {
 if (name.equals( I18NRESOURCE_EN )) {
 name = I18NRESOURCE_EN_ID;
                }    else {
 name = name.substring(I18NRESOURCE_PREFIX.length(), name.lastIndexOf(I18NRESOURCE_SUFFIX));
                }
 Locale locale = new Locale(name.substring(0, 2), ((name.indexOf("_") == -1) ? "" : name.substring(3, 5)));
 String defaultLanguage = "";
 if (clientLanguage.startsWith(name))
                {
 defaultLanguage = LocaleSupport.getLocalizedMessage(pageContext, I18NDEFAULT_LOCALE);
                }
 resultMap.put(name, locale.getDisplayName(locale) + " " + defaultLanguage);
            }
        }


 return resultMap;
    }