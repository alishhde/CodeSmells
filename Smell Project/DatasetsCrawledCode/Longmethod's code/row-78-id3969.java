 private synchronized Map getResourceBundleEntries(final Bundle bundle)
    {
 String file = (String) bundle.getHeaders().get(Constants.BUNDLE_LOCALIZATION);
 if (file == null)
        {
 file = Constants.BUNDLE_LOCALIZATION_DEFAULT_BASENAME;
        }


 // remove leading slash
 if (file.startsWith("/")) //$NON-NLS-1$
        {
 file = file.substring(1);
        }


 // split path and base name
 int slash = file.lastIndexOf('/');
 String fileName = file.substring(slash + 1);
 String path = (slash <= 0) ? "/" : file.substring(0, slash); //$NON-NLS-1$


 HashMap resourceBundleEntries = new HashMap();


 Enumeration locales = bundle.findEntries(path, fileName + "*.properties", false); //$NON-NLS-1$
 if (locales != null)
        {
 while (locales.hasMoreElements())
            {
 URL entry = (URL) locales.nextElement();


 // calculate the key
 String entryPath = entry.getPath();
 final int start = entryPath.lastIndexOf('/') + 1 + fileName.length(); // path,
 // slash
 // and
 // base
 // name
 final int end = entryPath.length() - 11; // .properties suffix
 entryPath = entryPath.substring(start, end);


 // the default language is "name.properties" thus the entry
 // path is empty and must default to "_"+DEFAULT_LOCALE
 if (entryPath.length() == 0)
                {
 entryPath = "_" + DEFAULT_LOCALE; //$NON-NLS-1$
                }


 // only add this entry, if the "language" is not provided
 // by the main bundle or an earlier bound fragment
 if (!resourceBundleEntries.containsKey(entryPath))
                {
 resourceBundleEntries.put(entryPath, entry);
                }
            }
        }


 return resourceBundleEntries;
    }