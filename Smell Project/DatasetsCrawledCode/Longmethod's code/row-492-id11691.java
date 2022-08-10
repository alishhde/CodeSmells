 private LdapComparator<?> classLoadComparator( SchemaManager schemaManager, String oid, String className,
 Attribute byteCode ) throws LdapException
    {
 // Try to class load the comparator
 LdapComparator<?> comparator;
 Class<?> clazz;
 String byteCodeStr = StringConstants.EMPTY;


 if ( byteCode == null )
        {
 try
            {
 clazz = Class.forName( className );
            }
 catch ( ClassNotFoundException cnfe )
            {
 LOG.error( I18n.err( I18n.ERR_16056_CANNOT_FIND_CMP_CTOR, className ) );
 throw new LdapSchemaException( I18n.err( I18n.ERR_16057_CANNOT_FIND_CMP_CLASS, cnfe.getMessage() ) );
            }
        }
 else
        {
 classLoader.setAttribute( byteCode );
 
 try
            {
 clazz = classLoader.loadClass( className );
            }
 catch ( ClassNotFoundException cnfe )
            {
 LOG.error( I18n.err( I18n.ERR_16058_CANNOT_LOAD_CMP_CTOR, className ) );
 throw new LdapSchemaException( I18n.err( I18n.ERR_16059_CANNOT_LOAD_CMP_CLASS, cnfe.getMessage() ) );
            }


 byteCodeStr = new String( Base64.encode( byteCode.getBytes() ) );
        }


 // Create the comparator instance. Either we have a no argument constructor,
 // or we have one which takes an OID. Lets try the one with an OID argument first
 try
        {
 Constructor<?> constructor = clazz.getConstructor( new Class[]
                { String.class } );
 
 try
            {
 comparator = ( LdapComparator<?> ) constructor.newInstance( oid );
            }
 catch ( InvocationTargetException ite )
            {
 LOG.error( I18n.err( I18n.ERR_16060_CANNOT_INVOKE_CMP_CTOR, className ) );
 throw new LdapSchemaException( I18n.err( I18n.ERR_16061_CANNOT_INVOKE_CMP_CLASS, ite.getMessage() ) );
            }
 catch ( InstantiationException ie )
            {
 LOG.error( I18n.err( I18n.ERR_16062_CANNOT_INST_CMP_CTOR_CLASS, className ) );
 throw new LdapSchemaException( I18n.err( I18n.ERR_16063_CANNOT_INST_CMP_CLASS, ie.getMessage() ) );
            }
 catch ( IllegalAccessException ie )
            {
 LOG.error( I18n.err( I18n.ERR_16064_CANNOT_ACCESS_CMP_CTOR, className ) );
 throw new LdapSchemaException( I18n.err( I18n.ERR_16065_CANNOT_ACCESS_CMP_CLASS, ie.getMessage() ) );
            }
        }
 catch ( NoSuchMethodException nsme )
        {
 // Ok, let's try with the constructor without argument.
 // In this case, we will have to check that the OID is the same than
 // the one we got in the Comparator entry
 try
            {
 clazz.getConstructor();
            }
 catch ( NoSuchMethodException nsme2 )
            {
 LOG.error( I18n.err( I18n.ERR_16066_CANNOT_FIND_CMP_CTOR_METH_CLASS, className ) );
 throw new LdapSchemaException( I18n.err( I18n.ERR_16067_CANNOT_FIND_CMP_CTOR_METH, nsme2.getMessage() ) );
            }
 
 try
            { 
 comparator = ( LdapComparator<?> ) clazz.newInstance();
            }
 catch ( InstantiationException ie )
            {
 LOG.error( I18n.err( I18n.ERR_16062_CANNOT_INST_CMP_CTOR_CLASS, className ) );
 throw new LdapSchemaException( I18n.err( I18n.ERR_16063_CANNOT_INST_CMP_CLASS, ie.getMessage() ) );
            }
 catch ( IllegalAccessException iae )
            {
 LOG.error( I18n.err( I18n.ERR_16064_CANNOT_ACCESS_CMP_CTOR, className ) );
 throw new LdapSchemaException( I18n.err( I18n.ERR_16065_CANNOT_ACCESS_CMP_CLASS, iae.getMessage() ) );
            }


 if ( !comparator.getOid().equals( oid ) )
            {
 String msg = I18n.err( I18n.ERR_16021_DIFFERENT_COMPARATOR_OID, oid, comparator.getOid() );
 throw new LdapInvalidAttributeValueException( ResultCodeEnum.UNWILLING_TO_PERFORM, msg, nsme );
            }
        }


 // Update the loadable fields
 comparator.setBytecode( byteCodeStr );
 comparator.setFqcn( className );


 // Inject the SchemaManager for the comparator who needs it
 comparator.setSchemaManager( schemaManager );


 return comparator;
    }