 public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {


 XMLSecurityPropertyManager spm = (XMLSecurityPropertyManager)componentManager.getProperty(XML_SECURITY_PROPERTY_MANAGER);
 if (spm == null) {
 spm = new XMLSecurityPropertyManager();
 setProperty(XML_SECURITY_PROPERTY_MANAGER, spm);
        }


 XMLSecurityManager sm = (XMLSecurityManager)componentManager.getProperty(SECURITY_MANAGER);
 if (sm == null)
 setProperty(SECURITY_MANAGER,new XMLSecurityManager(true));


 faccessExternalSchema = spm.getValue(XMLSecurityPropertyManager.Property.ACCESS_EXTERNAL_SCHEMA);


 fGrammarBucket.reset();


 fSubGroupHandler.reset();


 boolean parser_settings = true;
 // If the component manager is the loader config don't bother querying it since it doesn't
 // recognize the PARSER_SETTINGS feature. Prevents an XMLConfigurationException from being
 // thrown.
 if (componentManager != fLoaderConfig) {
 parser_settings = componentManager.getFeature(PARSER_SETTINGS, true);
        }


 if (!parser_settings || !fSettingsChanged){
 // need to reprocess JAXP schema sources
 fJAXPProcessed = false;
 // reinitialize grammar bucket
 initGrammarBucket();
 if (fDeclPool != null) {
 fDeclPool.reset();
            }
 return;
        }


 //pass the component manager to the factory..
 fNodeFactory.reset(componentManager);


 // get registered entity manager to be able to resolve JAXP schema-source property:
 // Note: in case XMLSchemaValidator has created the loader,
 // the entity manager property is null
 fEntityManager = (XMLEntityManager)componentManager.getProperty(ENTITY_MANAGER);


 // get the error reporter
 fErrorReporter = (XMLErrorReporter)componentManager.getProperty(ERROR_REPORTER);


 // Determine schema dv factory to use
 SchemaDVFactory dvFactory = null;
 dvFactory = fSchemaHandler.getDVFactory();
 if (dvFactory == null) {
 dvFactory = SchemaDVFactory.getInstance();
 fSchemaHandler.setDVFactory(dvFactory);
        }


 // get schema location properties
 try {
 fExternalSchemas = (String) componentManager.getProperty(SCHEMA_LOCATION);
 fExternalNoNSSchema = (String) componentManager.getProperty(SCHEMA_NONS_LOCATION);
        } catch (XMLConfigurationException e) {
 fExternalSchemas = null;
 fExternalNoNSSchema = null;
        }


 // get JAXP sources if available
 fJAXPSource = componentManager.getProperty(JAXP_SCHEMA_SOURCE, null);
 fJAXPProcessed = false;


 // clear grammars, and put the one for schema namespace there
 fGrammarPool = (XMLGrammarPool) componentManager.getProperty(XMLGRAMMAR_POOL, null);
 initGrammarBucket();


 boolean psvi = componentManager.getFeature(AUGMENT_PSVI, false);


 // Only use the decl pool when there is no chance that the schema
 // components will be exposed or cached.
 // TODO: when someone calls loadGrammar(XMLInputSource), the schema is
 // always exposed even without the use of a grammar pool.
 // Disabling the "decl pool" feature for now until we understand when
 // it can be safely used.
 if (!psvi && fGrammarPool == null && false) {
 if (fDeclPool != null) {
 fDeclPool.reset();
            }
 else {
 fDeclPool = new XSDeclarationPool();
            }
 fCMBuilder.setDeclPool(fDeclPool);
 fSchemaHandler.setDeclPool(fDeclPool);
 if (dvFactory instanceof SchemaDVFactoryImpl) {
 fDeclPool.setDVFactory((SchemaDVFactoryImpl)dvFactory);
                ((SchemaDVFactoryImpl)dvFactory).setDeclPool(fDeclPool);
            }
        } else {
 fCMBuilder.setDeclPool(null);
 fSchemaHandler.setDeclPool(null);
 if (dvFactory instanceof SchemaDVFactoryImpl) {
                ((SchemaDVFactoryImpl)dvFactory).setDeclPool(null);
            }
        }


 // get continue-after-fatal-error feature
 try {
 boolean fatalError = componentManager.getFeature(CONTINUE_AFTER_FATAL_ERROR, false);
 if (!fatalError) {
 fErrorReporter.setFeature(CONTINUE_AFTER_FATAL_ERROR, fatalError);
            }
        } catch (XMLConfigurationException e) {
        }
 // set full validation to false
 fIsCheckedFully = componentManager.getFeature(SCHEMA_FULL_CHECKING, false);


 // get generate-synthetic-annotations feature
 fSchemaHandler.setGenerateSyntheticAnnotations(componentManager.getFeature(GENERATE_SYNTHETIC_ANNOTATIONS, false));
 fSchemaHandler.reset(componentManager);
    }