 public synchronized void start(BundleContext context) throws Exception
    {
 PermissionAdminImpl pai = null;


 SecureAction action = new SecureAction();


 Permissions permissions = new Permissions(context, action);


 File tmp = context.getDataFile("security" + File.separator + "tmp");
 if ((tmp == null) || (!tmp.isDirectory() && !tmp.mkdirs()))
        {
 throw new IOException("Can't create tmp dir.");
        }
 // TODO: log something if we can not clean-up the tmp dir
 File[] old = tmp.listFiles();
 if (old != null)
        {
 for (int i = 0; i < old.length; i++)
            {
 old[i].delete();
            }
        }


 if ("TRUE".equalsIgnoreCase(getProperty(context,
 SecurityConstants.ENABLE_PERMISSIONADMIN_PROP,
 SecurityConstants.ENABLE_PERMISSIONADMIN_VALUE)))
        {
 File cache = context.getDataFile("security" + File.separator
                + "pa.txt");
 if ((cache == null) || (!cache.isFile() && !cache.createNewFile()))
            {
 throw new IOException("Can't create cache file");
            }
 pai = new PermissionAdminImpl(permissions, new PropertiesCache(
 cache, tmp, action));
        }


 ConditionalPermissionAdminImpl cpai = null;


 if ("TRUE".equalsIgnoreCase(getProperty(context,
 SecurityConstants.ENABLE_CONDPERMADMIN_PROP,
 SecurityConstants.ENABLE_CONDPERMADMIN_VALUE)))
        {
 File cpaCache = context.getDataFile("security" + File.separator
                + "cpa.txt");
 if ((cpaCache == null)
                || (!cpaCache.isFile() && !cpaCache.createNewFile()))
            {
 throw new IOException("Can't create cache file");
            }


 LocalPermissions localPermissions = new LocalPermissions(
 permissions);


 cpai = new ConditionalPermissionAdminImpl(permissions,
 new Conditions(action), localPermissions, new PropertiesCache(
 cpaCache, tmp, action), pai);
        }


 if ((pai != null) || (cpai != null))
        {
 String crlList = getProperty(context,
 SecurityConstants.CRL_FILE_PROP,
 SecurityConstants.CRL_FILE_VALUE);
 String storeList = getProperty(context,
 SecurityConstants.KEYSTORE_FILE_PROP,
 SecurityConstants.KEYSTORE_FILE_VALUE);
 String passwdList = getProperty(context,
 SecurityConstants.KEYSTORE_PASS_PROP,
 SecurityConstants.KEYSTORE_PASS_VALUE);
 String typeList = getProperty(context,
 SecurityConstants.KEYSTORE_TYPE_PROP,
 SecurityConstants.KEYSTORE_TYPE_VALUE);
 String osgi_keystores = getProperty(context,
 Constants.FRAMEWORK_TRUST_REPOSITORIES, null);
 if (osgi_keystores != null)
            {
 StringTokenizer tok = new StringTokenizer(osgi_keystores,
 File.pathSeparator);


 if (storeList.length() == 0)
                {
 storeList += "file:" + tok.nextToken();
 passwdList += " ";
 typeList += "JKS";
                }
 while (tok.hasMoreTokens())
                {
 storeList += "|file:" + tok.nextToken();
 passwdList += "| ";
 typeList += "|JKS";
                }
            }


 StringTokenizer storeTok = new StringTokenizer(storeList, "|");
 StringTokenizer passwdTok = new StringTokenizer(passwdList, "|");
 StringTokenizer typeTok = new StringTokenizer(typeList, "|");


 if ((storeTok.countTokens() != typeTok.countTokens())
                || (passwdTok.countTokens() != storeTok.countTokens()))
            {
 throw new BundleException(
 "Each CACerts keystore must have one type and one passwd entry and vice versa.");
            }


 SecurityProvider provider = new SecurityProviderImpl(crlList,
 typeList, passwdList, storeList, pai, cpai, action, ((Felix) context.getBundle(0)).getLogger());


            ((Felix) context.getBundle(0)).setSecurityProvider(provider);
        }


 if (pai != null)
        {
 context.registerService(PermissionAdmin.class.getName(), pai, null);
        }


 if (cpai != null)
        {
 context.registerService(ConditionalPermissionAdmin.class.getName(),
 cpai, null);
        }
    }