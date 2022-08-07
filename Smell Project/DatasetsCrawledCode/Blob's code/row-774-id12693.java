 public static class DiscoverAnnotatedBeans implements DynamicDeployer {
 public AppModule deploy(AppModule appModule) throws OpenEJBException {
 for (EjbModule ejbModule : appModule.getEjbModules()) {
 ejbModule.initAppModule(appModule);
 setModule(ejbModule);
 try {
 deploy(ejbModule);
                } finally {
 removeModule();
                }
            }
 for (ClientModule clientModule : appModule.getClientModules()) {
 clientModule.initAppModule(appModule);
 setModule(clientModule);
 try {
 deploy(clientModule);
                } finally {
 removeModule();
                }
            }
 for (ConnectorModule connectorModule : appModule.getConnectorModules()) {
 connectorModule.initAppModule(appModule);
 setModule(connectorModule);
 try {
 deploy(connectorModule);
                } finally {
 removeModule();
                }
            }
 for (WebModule webModule : appModule.getWebModules()) {
 webModule.initAppModule(appModule);
 setModule(webModule);
 try {
 deploy(webModule);
                } finally {
 removeModule();
                }
            }
 final AdditionalBeanDiscoverer discoverer = SystemInstance.get().getComponent(AdditionalBeanDiscoverer.class);
 if (discoverer != null) {
 appModule = discoverer.discover(appModule);
            }
 return appModule;
        }


 public ClientModule deploy(ClientModule clientModule) throws OpenEJBException {


 if (clientModule.getApplicationClient() == null){
 clientModule.setApplicationClient(new ApplicationClient());
            }


 // Lots of jars have main classes so this might not even be an app client.
 // We're not going to scrape it for @LocalClient or @RemoteClient annotations
 // unless they flag us specifically by adding a META-INF/application-client.xml
 //
 // ClientModules that already have a AnnotationFinder have been generated automatically
 // from an EjbModule, so we don't skip those ever.
 if (clientModule.getFinder() == null && clientModule.getAltDDs().containsKey("application-client.xml"))


 if (clientModule.getApplicationClient() != null && clientModule.getApplicationClient().isMetadataComplete()) return clientModule;




 IAnnotationFinder finder = clientModule.getFinder();


 if (finder == null) {
 try {
 finder = FinderFactory.createFinder(clientModule);
                } catch (MalformedURLException e) {
 startupLogger.warning("startup.scrapeFailedForClientModule.url", clientModule.getJarLocation());
 return clientModule;
                } catch (Exception e) {
 startupLogger.warning("startup.scrapeFailedForClientModule", e, clientModule.getJarLocation());
 return clientModule;
                }
            }


 // This method is also called by the deploy(EjbModule) method to see if those
 // modules have any @LocalClient or @RemoteClient classes
 for (Annotated<Class<?>> clazz : finder.findMetaAnnotatedClasses(LocalClient.class)) {
 clientModule.getLocalClients().add(clazz.get().getName());
            }


 for (Annotated<Class<?>> clazz : finder.findMetaAnnotatedClasses(RemoteClient.class)) {
 clientModule.getRemoteClients().add(clazz.get().getName());
            }


 if (clientModule.getApplicationClient() == null){
 if (clientModule.getRemoteClients().size() > 0 || clientModule.getLocalClients().size() > 0) {
 clientModule.setApplicationClient(new ApplicationClient());
                }
            }


 return clientModule;
        }


 public ConnectorModule deploy(ConnectorModule connectorModule) throws OpenEJBException {


 org.apache.openejb.jee.Connector connector = connectorModule.getConnector();
 if (connector == null) {
 connector = new org.apache.openejb.jee.Connector();
        	}


 // JCA 1.6 - 18.3.1 do not look at annotations if the provided connector
 // deployment descriptor is "meta-data complete".


 float specVersion = 0;
 try {
 specVersion = Float.parseFloat(connector.getVersion());
        	} catch (Exception e) {
        	}


 if (specVersion < 1.6 || Boolean.TRUE.equals(connector.isMetadataComplete())) {
 return connectorModule;
			}




 IAnnotationFinder finder = connectorModule.getFinder();
 if (finder == null) {
 try {
 finder = FinderFactory.createFinder(connectorModule);
 connectorModule.setFinder(finder);
				} catch (Exception e) {
 // TODO: some sort of error
 return connectorModule;
				}
        	}


 List<Class<?>> connectorClasses = finder.findAnnotatedClasses(Connector.class);


 // are we allowed to have more than one connector class? Not without a deployment descriptor
 if (connector.getResourceAdapter() == null || connector.getResourceAdapter().getResourceAdapterClass() == null ||  connector.getResourceAdapter().getResourceAdapterClass().length() == 0) {
 if (connectorClasses.size() == 0) {
 // fail some validation here too
        		}


 if (connectorClasses.size() > 1) {
 // too many connector classes, this is against the spec
 // something like connectorModule.getValidation().fail(ejbName, "abstractAnnotatedAsBean", annotationClass.getSimpleName(), beanClass.get().getName());
        		}
        	}


 Class<?> connectorClass = null;
 if (connectorClasses.size() == 1) {
 connectorClass = connectorClasses.get(0);
        	}


 if (connectorClasses.size() > 1) {
 for (Class<?> cls : connectorClasses) {
 if (cls.getName().equals(connector.getResourceAdapter().getResourceAdapterClass())) {
 connectorClass = cls;
 break;
        			}
        		}
        	}


 if (connectorClass != null) {
 if (connector.getResourceAdapter() == null) {
 connector.setResourceAdapter(new ResourceAdapter());
	    		}


 if (connector.getResourceAdapter().getResourceAdapterClass() == null || connector.getResourceAdapter().getResourceAdapterClass().length() == 0) {
 connector.getResourceAdapter().setResourceAdapterClass(connectorClass.getName());
	    		}


 Connector connectorAnnotation = connectorClass.getAnnotation(Connector.class);


 connector.setDisplayNames(getTexts(connector.getDisplayNames(), connectorAnnotation.displayName()));
 connector.setDescriptions(getTexts(connector.getDescriptions(), connectorAnnotation.description()));


 connector.setEisType(getString(connector.getEisType(), connectorAnnotation.eisType()));
 connector.setVendorName(getString(connector.getVendorName(), connectorAnnotation.vendorName()));
 connector.setResourceAdapterVersion(getString(connector.getResourceAdapterVersion(), connectorAnnotation.version()));


 if (connector.getIcons().isEmpty()) {
 int smallIcons = connectorAnnotation.smallIcon().length;
 int largeIcons = connectorAnnotation.largeIcon().length;


 for (int i = 0; i < smallIcons && i < largeIcons; i++) {
 Icon icon = new Icon();
 // locale can't be specified in the annotation and it is en by default
 // so on other systems it doesn't work because Icon return the default locale
 icon.setLang(Locale.getDefault().getLanguage());
 if (i < smallIcons) {
 icon.setSmallIcon(connectorAnnotation.smallIcon()[i]);
	    				}


 if (i < largeIcons) {
 icon.setLargeIcon(connectorAnnotation.largeIcon()[i]);
	    				}


 connector.getIcons().add(icon);
	    			}
	    		}


 if (connector.getLicense() == null) {
 License license = new License();
 connector.setLicense(license);
 license.setLicenseRequired(connectorAnnotation.licenseRequired());
	    		}


 connector.getLicense().setDescriptions(getTexts(connector.getLicense().getDescriptions(), connectorAnnotation.licenseDescription()));




 SecurityPermission[] annotationSecurityPermissions = connectorAnnotation.securityPermissions();
 List<org.apache.openejb.jee.SecurityPermission> securityPermission = connector.getResourceAdapter().getSecurityPermission();
 if (securityPermission == null || securityPermission.size() == 0) {
 for (SecurityPermission sp : annotationSecurityPermissions) {
 org.apache.openejb.jee.SecurityPermission permission = new org.apache.openejb.jee.SecurityPermission();
 permission.setSecurityPermissionSpec(sp.permissionSpec());
 permission.setDescriptions(stringsToTexts(sp.description()));
 securityPermission.add(permission);
					}
				}


 Class<? extends WorkContext>[] annotationRequiredWorkContexts = connectorAnnotation.requiredWorkContexts();
 List<String> requiredWorkContext = connector.getRequiredWorkContext();
 if (requiredWorkContext.size() == 0) {
 for (Class<? extends WorkContext> cls : annotationRequiredWorkContexts) {
 requiredWorkContext.add(cls.getName());
					}
				}


 OutboundResourceAdapter outboundResourceAdapter = connector.getResourceAdapter().getOutboundResourceAdapter();
 if (outboundResourceAdapter == null) {
 outboundResourceAdapter = new OutboundResourceAdapter();
 connector.getResourceAdapter().setOutboundResourceAdapter(outboundResourceAdapter);
				}


 List<AuthenticationMechanism> authenticationMechanisms = outboundResourceAdapter.getAuthenticationMechanism();
 javax.resource.spi.AuthenticationMechanism[] authMechanisms = connectorAnnotation.authMechanisms();
 if (authenticationMechanisms.size() == 0) {
 for (javax.resource.spi.AuthenticationMechanism am : authMechanisms) {
 AuthenticationMechanism authMechanism = new AuthenticationMechanism();
 authMechanism.setAuthenticationMechanismType(am.authMechanism());
 authMechanism.setCredentialInterface(am.credentialInterface().toString());
 authMechanism.setDescriptions(stringsToTexts(am.description()));


 authenticationMechanisms.add(authMechanism);
					}
				}


 if (outboundResourceAdapter.getTransactionSupport() == null) {
 outboundResourceAdapter.setTransactionSupport(TransactionSupportType.fromValue(connectorAnnotation.transactionSupport().toString()));
				}


 if (outboundResourceAdapter.isReauthenticationSupport() == null) {
 outboundResourceAdapter.setReauthenticationSupport(connectorAnnotation.reauthenticationSupport());
				}
        	} else {
 // we couldn't process a connector class - probably a validation issue which we should warn about.
        	}


 // process @ConnectionDescription(s)
 List<Class<?>> classes = finder.findAnnotatedClasses(ConnectionDefinitions.class);
 for (Class<?> cls : classes) {
 ConnectionDefinitions connectionDefinitionsAnnotation = cls.getAnnotation(ConnectionDefinitions.class);
 ConnectionDefinition[] definitions = connectionDefinitionsAnnotation.value();


 for (ConnectionDefinition definition : definitions) {
 processConnectionDescription(connector.getResourceAdapter(), definition, cls);
				}
			}


 classes = finder.findAnnotatedClasses(ConnectionDefinition.class);
 for (Class<?> cls : classes) {
 ConnectionDefinition connectionDefinitionAnnotation = cls.getAnnotation(ConnectionDefinition.class);
 processConnectionDescription(connector.getResourceAdapter(), connectionDefinitionAnnotation, cls);
			}




 InboundResourceadapter inboundResourceAdapter = connector.getResourceAdapter().getInboundResourceAdapter();
 if (inboundResourceAdapter == null) {
 inboundResourceAdapter = new InboundResourceadapter();
 connector.getResourceAdapter().setInboundResourceAdapter(inboundResourceAdapter);
        	}


 MessageAdapter messageAdapter = inboundResourceAdapter.getMessageAdapter();
 if (messageAdapter == null) {
 messageAdapter = new MessageAdapter();
 inboundResourceAdapter.setMessageAdapter(messageAdapter);
        	}


 classes = finder.findAnnotatedClasses(Activation.class);
 for (Class<?> cls : classes) {
 MessageListener messageListener = null;
 Activation activationAnnotation = cls.getAnnotation(Activation.class);


 List<MessageListener> messageListeners = messageAdapter.getMessageListener();
 for (MessageListener ml : messageListeners) {
 if (cls.getName().equals(ml.getActivationSpec().getActivationSpecClass())) {
 messageListener = ml;
 break;
					}
				}


 if (messageListener == null) {
 Class<?>[] listeners = activationAnnotation.messageListeners();
 for (Class<?> listener : listeners) {
 messageAdapter.addMessageListener(new MessageListener(listener.getName(), cls.getName()));
					}
				}
			}


 classes = finder.findAnnotatedClasses(AdministeredObject.class);
 List<AdminObject> adminObjects = connector.getResourceAdapter().getAdminObject();
 for (Class<?> cls : classes) {
 AdministeredObject administeredObjectAnnotation = cls.getAnnotation(AdministeredObject.class);
 Class[] adminObjectInterfaces = administeredObjectAnnotation.adminObjectInterfaces();


 AdminObject adminObject = null;
 for (AdminObject admObj : adminObjects) {
 if (admObj.getAdminObjectClass().equals(cls.getName())) {
 adminObject = admObj;
					}
				}


 if (adminObject == null) {
 for (Class iface : adminObjectInterfaces) {
 AdminObject newAdminObject = new AdminObject();
 newAdminObject.setAdminObjectClass(cls.getName());
 newAdminObject.setAdminObjectInterface(iface.getName());
 adminObjects.add(newAdminObject);
					}
				}
			}


 // need to make a list of classes to process for config properties


 // resource adapter
 String raCls = connector.getResourceAdapter().getResourceAdapterClass();
 process(connectorModule.getClassLoader(), raCls, connector.getResourceAdapter());


 // managedconnectionfactory
 if (connector.getResourceAdapter() != null && connector.getResourceAdapter().getOutboundResourceAdapter() != null) {
 List<org.apache.openejb.jee.ConnectionDefinition> connectionDefinitions = connector.getResourceAdapter().getOutboundResourceAdapter().getConnectionDefinition();
 for (org.apache.openejb.jee.ConnectionDefinition connectionDefinition : connectionDefinitions) {
 process(connectorModule.getClassLoader(), connectionDefinition.getManagedConnectionFactoryClass(), connectionDefinition);
				}
        	}


 // administeredobject
 if (connector.getResourceAdapter() != null) {
 List<AdminObject> raAdminObjects = connector.getResourceAdapter().getAdminObject();
 for (AdminObject raAdminObject : raAdminObjects) {
 process(connectorModule.getClassLoader(), raAdminObject.getAdminObjectClass(), raAdminObject);
				}
        	}


 // activationspec
 if (connector.getResourceAdapter() != null && connector.getResourceAdapter().getInboundResourceAdapter() != null && connector.getResourceAdapter().getInboundResourceAdapter().getMessageAdapter() != null) {
 List<MessageListener> messageListeners = connector.getResourceAdapter().getInboundResourceAdapter().getMessageAdapter().getMessageListener();
 for (MessageListener messageListener : messageListeners) {
 ActivationSpec activationSpec = messageListener.getActivationSpec();
 process(connectorModule.getClassLoader(), activationSpec.getActivationSpecClass(), activationSpec);
				}
        	}


 return connectorModule;
        }


 void process(ClassLoader cl, String cls, Object object) {


 List<ConfigProperty> configProperties = null;
 try {
 // grab a list of ConfigProperty objects
 configProperties = (List<ConfigProperty>) object.getClass().getDeclaredMethod("getConfigProperty").invoke(object);
			} catch (Exception e) {
			}


 if (configProperties == null) {
 // can't get config properties
 return;
			}


 ClassLoader classLoader = cl;
 if (classLoader == null) {
 classLoader = Thread.currentThread().getContextClassLoader();
			}


 final List<String> allowedTypes = Arrays.asList(new String[] { Boolean.class.getName(), String.class.getName(), Integer.class.getName(), Double.class.getName(), Byte.class.getName(), Short.class.getName(), Long.class.getName(), Float.class.getName(), Character.class.getName()});


 try {
 Class<?> clazz = classLoader.loadClass(realClassName(cls));
 Object o = clazz.newInstance();


 // add any introspected properties
 BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
 PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();


 for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
 String name = propertyDescriptor.getName();
 Class<?> type = propertyDescriptor.getPropertyType();
 if (type == null) {
 continue;
                    }
 if (type.isPrimitive()) {
 type = getWrapper(type.getName());
					}


 if (! allowedTypes.contains(type.getName())) {
 continue;
					}


 if (! containsConfigProperty(configProperties, name)) {
 if (type != null) {
 ConfigProperty configProperty = new ConfigProperty();
 configProperties.add(configProperty);


 Object value = null;
 try {
 value = propertyDescriptor.getReadMethod().invoke(o);
							} catch (Exception e) {
							}


 javax.resource.spi.ConfigProperty annotation = propertyDescriptor.getWriteMethod().getAnnotation(javax.resource.spi.ConfigProperty.class);
 if (annotation == null) {
 try {
 // if there's no annotation on the setter, we'll try and scrape one off the field itself (assuming the same name)
 annotation = clazz.getDeclaredField(name).getAnnotation(javax.resource.spi.ConfigProperty.class);
                                } catch (Exception ignored) {
 // no-op : getDeclaredField() throws exceptions and does not return null
                                }
                            }


 configProperty.setConfigPropertyName(name);
 configProperty.setConfigPropertyType(getConfigPropertyType(annotation, type));
 if (value != null) {
 configProperty.setConfigPropertyValue(value.toString());
							}


 if (annotation != null) {
 if (annotation.defaultValue() != null && annotation.defaultValue().length() > 0) {
 configProperty.setConfigPropertyValue(annotation.defaultValue());
								}
 configProperty.setConfigPropertyConfidential(annotation.confidential());
 configProperty.setConfigPropertyIgnore(annotation.ignore());
 configProperty.setConfigPropertySupportsDynamicUpdates(annotation.supportsDynamicUpdates());
 configProperty.setDescriptions(stringsToTexts(annotation.description()));
							}
						}
					}
				}


 // add any annotated fields we haven't already picked up
 Field[] declaredFields = clazz.getDeclaredFields();
 for (Field field : declaredFields) {
 javax.resource.spi.ConfigProperty annotation = field.getAnnotation(javax.resource.spi.ConfigProperty.class);


 String name = field.getName();
 Object value = null;
 try {
 value = field.get(o);
					} catch (Exception e) {
					}


 if (! containsConfigProperty(configProperties, name)) {
 String type = getConfigPropertyType(annotation, field.getType());


 if (type != null) {
 ConfigProperty configProperty = new ConfigProperty();
 configProperties.add(configProperty);


 configProperty.setConfigPropertyName(name);
 configProperty.setConfigPropertyType(type);
 if (value != null) {
 configProperty.setConfigPropertyValue(value.toString());
							}


 if (annotation != null) {
 if (annotation.defaultValue() != null) {
 configProperty.setConfigPropertyValue(annotation.defaultValue());
								}
 configProperty.setConfigPropertyConfidential(annotation.confidential());
 configProperty.setConfigPropertyIgnore(annotation.ignore());
 configProperty.setConfigPropertySupportsDynamicUpdates(annotation.supportsDynamicUpdates());
							}
						}
					}
				}
			} catch (Exception e) {
 e.printStackTrace();
			}
		}


 private String getConfigPropertyType(javax.resource.spi.ConfigProperty annotation, Class<?> type) {
 Class<?> t = (annotation == null) ? null : annotation.type();
 if (t == null && type != null) {
 return type.getName();
            } else if (t == null) {
 return null;
            }


 if (t.equals(Object.class)) {
 t = type;
			}
 if (t == null) { // t == null && type == null
 return null;
            }


 if (t.isPrimitive()) {
 t = getWrapper(t.getName());
			}


 return t.getName();
		}


 private boolean containsConfigProperty(List<ConfigProperty> configProperties, String name) {
 for (ConfigProperty configProperty : configProperties) {
 if (configProperty.getConfigPropertyName().equals(name)) {
 return true;
				}
			}


 return false;
		}


 private Class<?> getWrapper(String primitiveType) {
 final Map<String,Class<?>> builtInMap = new HashMap<String,Class<?>>();{
 builtInMap.put("int", Integer.class);
 builtInMap.put("long", Long.class);
 builtInMap.put("double", Double.class);
 builtInMap.put("float", Float.class);
 builtInMap.put("boolean", Boolean.class);
 builtInMap.put("char", Character.class);
 builtInMap.put("byte", Byte.class);
 builtInMap.put("void", Void.class);
 builtInMap.put("short", Short.class);
			}


 return builtInMap.get(primitiveType);
		}


 private void processConnectionDescription(ResourceAdapter resourceAdapter, ConnectionDefinition connectionDefinitionAnnotation, Class<?> cls) {
 // try and find the managed connection factory


 OutboundResourceAdapter outboundResourceAdapter = resourceAdapter.getOutboundResourceAdapter();
 if (outboundResourceAdapter == null) {
 outboundResourceAdapter = new OutboundResourceAdapter();
 resourceAdapter.setOutboundResourceAdapter(outboundResourceAdapter);
			}


 List<org.apache.openejb.jee.ConnectionDefinition> connectionDefinition = outboundResourceAdapter.getConnectionDefinition();


 org.apache.openejb.jee.ConnectionDefinition definition = null;
 for (org.apache.openejb.jee.ConnectionDefinition cd : connectionDefinition) {
 if (cd.getManagedConnectionFactoryClass().equals(cls.getName())) {
 definition = cd;
 break;
				}
			}


 if (definition == null) {
 definition = new org.apache.openejb.jee.ConnectionDefinition();
 outboundResourceAdapter.getConnectionDefinition().add(definition);
			}


 if (definition.getManagedConnectionFactoryClass() == null) {
 definition.setManagedConnectionFactoryClass(cls.getName());
			}


 if (definition.getConnectionInterface() == null) {
 definition.setConnectionInterface(connectionDefinitionAnnotation.connection().getName());
			}


 if (definition.getConnectionImplClass() == null) {
 definition.setConnectionImplClass(connectionDefinitionAnnotation.connectionImpl().getName());
			}


 if (definition.getConnectionFactoryInterface() == null) {
 definition.setConnectionFactoryInterface(connectionDefinitionAnnotation.connectionFactory().getName());
			}


 if (definition.getConnectionFactoryImplClass() == null) {
 definition.setConnectionFactoryImplClass(connectionDefinitionAnnotation.connectionFactoryImpl().getName());
			}
		}


 private Text[] stringsToTexts(String[] strings) {
 if (strings == null) {
 return null;
			}


 Text[] result = new Text[strings.length];
 for (int i = 0; i < result.length; i++) {
 result[i] = new Text();
 result[i].setValue(strings[i]);
			}


 return result;
		}


 private String getString(String descriptorString, String annotationString) {
 if (descriptorString != null && descriptorString.length() > 0) {
 return descriptorString;
			}


 if (annotationString != null && annotationString.length() > 0) {
 return annotationString;
			}


 return null;
		}


 private Text[] getTexts(Text[] originalTexts, String[] newStrings) {
 if (newStrings != null && newStrings.length > 0 && (originalTexts == null || originalTexts.length == 0)) {
 Text[] texts = new Text[newStrings.length];
 for (int i = 0; i < newStrings.length; i++) {
 texts[i] = new Text(null, newStrings[i]);
				}


 return texts;
			} else {
 return originalTexts;
			}
		}


 public WebModule deploy(WebModule webModule) throws OpenEJBException {
 WebApp webApp = webModule.getWebApp();
 if (webApp != null && (webApp.isMetadataComplete())) return webModule;


 try {
 if (webModule.getFinder() == null) {
 webModule.setFinder(FinderFactory.createFinder(webModule));
                }
            } catch (Exception e) {
 startupLogger.warning("Unable to scrape for @WebService or @WebServiceProvider annotations. AnnotationFinder failed.", e);
 return webModule;
            }


 if (webApp == null) {
 webApp = new WebApp();
 webModule.setWebApp(webApp);
            }


 List<String> existingServlets = new ArrayList<String>();
 for (Servlet servlet : webApp.getServlet()) {
 if (servlet.getServletClass() != null) {
 existingServlets.add(servlet.getServletClass());
                }
            }


 IAnnotationFinder finder = webModule.getFinder();
 List<Class> classes = new ArrayList<Class>();
 classes.addAll(finder.findAnnotatedClasses(WebService.class));
 classes.addAll(finder.findAnnotatedClasses(WebServiceProvider.class));


 for (Class<?> webServiceClass : classes) {
 // If this class is also annotated @Stateless or @Singleton, we should skip it
 if (webServiceClass.isAnnotationPresent(Singleton.class) || webServiceClass.isAnnotationPresent(Stateless.class)) {
 webModule.getEjbWebServices().add(webServiceClass.getName());
 continue;
                }


 int modifiers = webServiceClass.getModifiers();
 if (!Modifier.isPublic(modifiers) || Modifier.isFinal(modifiers) || isAbstract(modifiers)) {
 continue;
                }


 if (existingServlets.contains(webServiceClass.getName())) continue;


 // create webApp and webservices objects if they don't exist already


 // add new <servlet/> element
 Servlet servlet = new Servlet();
 servlet.setServletName(webServiceClass.getName());
 servlet.setServletClass(webServiceClass.getName());
 webApp.getServlet().add(servlet);
            }


 /*
            * REST
            */
 // get by annotations
 webModule.getRestClasses().addAll(findRestClasses(webModule, finder));
 addJaxRsProviders(finder, webModule.getJaxrsProviders(), Provider.class);


 // Applications with a default constructor
 // findSubclasses will not work by default to gain a lot of time
 // look FinderFactory for the flag to activate it or
 // use @ApplicationPath("/")
 List<Class<? extends Application>> applications = finder.findSubclasses(Application.class);
 for (Class<? extends Application> app : applications) {
 addRestApplicationIfPossible(webModule, app);
            }


 // look for ApplicationPath, it will often return the same than the previous one
 // but without finder.link() invocation it still works
 // so it can save a lot of startup time
 List<Annotated<Class<?>>> applicationsByAnnotation = finder.findMetaAnnotatedClasses(ApplicationPath.class);
 for (Annotated<Class<?>> annotatedApp : applicationsByAnnotation) {
 final Class<?> app = annotatedApp.get();
 if (!Application.class.isAssignableFrom(app)) {
 logger.error("class '" + app.getName() + "' is annotated with @ApplicationPath but doesn't implement " + Application.class.getName());
 continue;
                }


 addRestApplicationIfPossible(webModule, (Class<? extends Application>) app);
            }


 /*
             * JSF
             */
 final ClassLoader classLoader = webModule.getClassLoader();
 for (String jsfClass : JSF_CLASSES) {
 final Class<? extends Annotation> clazz;
 try {
 clazz = (Class<? extends Annotation>) classLoader.loadClass(jsfClass);
                } catch (ClassNotFoundException e) {
 continue;
                }


 final List<Annotated<Class<?>>> found = finder.findMetaAnnotatedClasses(clazz);
 final Set<String> convertedClasses = new HashSet<String>(found.size());
 for (Annotated<Class<?>> annotated : found) {
 convertedClasses.add(annotated.get().getName());
                }
 webModule.getJsfAnnotatedClasses().put(jsfClass, convertedClasses);
            }


 /*
             * Servlet, Filter, Listener
             */


 Map<String, String> urlByClasses = null;
 for (String apiClassName : WEB_CLASSES) {
 final Class<? extends Annotation> clazz;
 try {
 clazz = (Class<? extends Annotation>) classLoader.loadClass(apiClassName);
                } catch (ClassNotFoundException e) {
 continue;
                }


 if (urlByClasses == null) { // try to reuse scanning info, maybe some better indexing can be a nice idea
 if (finder instanceof FinderFactory.ModuleLimitedFinder) {
 final IAnnotationFinder limitedFinder = ((FinderFactory.ModuleLimitedFinder) finder).getDelegate();
 if (limitedFinder instanceof AnnotationFinder) {
 final Archive archive = ((AnnotationFinder) limitedFinder).getArchive();
 if (archive instanceof WebappAggregatedArchive) {
 final Map<URL, List<String>> index = ((WebappAggregatedArchive) archive).getClassesMap();
 urlByClasses = new HashMap<String, String>();
 for (Map.Entry<URL, List<String>> entry : index.entrySet()) {
 final String url = entry.getKey().toExternalForm();
 for (String current : entry.getValue()) {
 urlByClasses.put(current,  url);
                                    }
                                }
                            }
                        }
                    }
                }


 final List<Annotated<Class<?>>> found = finder.findMetaAnnotatedClasses(clazz);
 addWebAnnotatedClassInfo(urlByClasses, webModule.getWebAnnotatedClasses(), found);
            }


 if (urlByClasses != null) {
 urlByClasses.clear();
            }


 return webModule;
        }


 private void addJaxRsProviders(final IAnnotationFinder finder, final Collection<String> set, final Class<? extends Annotation> annotation) {
 for (Annotated<Class<?>> provider : finder.findMetaAnnotatedClasses(annotation)) {
 set.add(provider.get().getName());
            }
        }


 private static void addRestApplicationIfPossible(final WebModule webModule, final Class<? extends Application> app) {
 if (app.getConstructors().length == 0) {
 webModule.getRestApplications().add(app.getName());
            } else {
 for (Constructor<?> ctr : app.getConstructors()) {
 if (ctr.getParameterTypes().length == 0) {
 webModule.getRestApplications().add(app.getName());
 break;
                    }
                }
            }
        }


 public EjbModule deploy(EjbModule ejbModule) throws OpenEJBException {
 if (ejbModule.getEjbJar() != null && ejbModule.getEjbJar().isMetadataComplete()) return ejbModule;




 try {
 if (ejbModule.getFinder() == null) {
 ejbModule.setFinder(FinderFactory.createFinder(ejbModule));
                }
            } catch (MalformedURLException e) {
 startupLogger.warning("startup.scrapeFailedForModule", ejbModule.getJarLocation());
 return ejbModule;
            } catch (Exception e) {
 startupLogger.warning("Unable to scrape for @Stateful, @Stateless, @Singleton or @MessageDriven annotations. AnnotationFinder failed.", e);
 return ejbModule;
            }


 IAnnotationFinder finder = ejbModule.getFinder();




 final List<String> managedClasses;
            {
 final Beans beans = ejbModule.getBeans();


 if (beans != null) {
 managedClasses = beans.getManagedClasses();
 final List<String> classNames = getBeanClasses(finder);
 for (String rawClassName : classNames) {
 final String className = realClassName(rawClassName);
 try {
 final ClassLoader loader = ejbModule.getClassLoader();
 final Class<?> clazz = loader.loadClass(className);


 // The following can NOT be beans in CDI


 // 1. Non-static inner classes
 if (clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers())) continue;
//
//                            // 2. Abstract classes (unless they are an @Decorator)
//                            if (Modifier.isAbstract(clazz.getModifiers()) && !clazz.isAnnotationPresent(javax.decorator.Decorator.class)) continue;
//
 // 3. Implementations of Extension
 if (Extension.class.isAssignableFrom(clazz)) continue;


 managedClasses.add(className);
                        } catch (ClassNotFoundException e) {
 // todo log debug warning
                        } catch (java.lang.NoClassDefFoundError e) {
 // no-op
                        }
                    }


 // passing jar location to be able to manage maven classes/test-classes which have the same moduleId
 String id = ejbModule.getModuleId();
 if (ejbModule.getJarLocation() != null && ejbModule.getJarLocation().contains(ejbModule.getModuleId() + "/target/test-classes".replace("/", File.separator))) {
 // with maven if both src/main/java and src/test/java are deployed
 // moduleId.Comp exists twice so it fails
 // here we simply modify the test comp bean name to avoid it
 id += "_test";
                    }
 final String name = BeanContext.Comp.openejbCompName(id);
 final org.apache.openejb.jee.ManagedBean managedBean = new CompManagedBean(name, BeanContext.Comp.class);
 managedBean.setTransactionType(TransactionType.BEAN);
 ejbModule.getEjbJar().addEnterpriseBean(managedBean);
                } else {
 managedClasses = new ArrayList<String>();
                }
            }


 final Set<Class<?>> specializingClasses = new HashSet<Class<?>>();




 // Fill in default sessionType for xml declared EJBs
 for (EnterpriseBean bean : ejbModule.getEjbJar().getEnterpriseBeans()) {
 if (!(bean instanceof SessionBean)) continue;


 SessionBean sessionBean = (SessionBean) bean;


 if (sessionBean.getSessionType() != null) continue;


 try {
 final Class<?> clazz = ejbModule.getClassLoader().loadClass(bean.getEjbClass());
 sessionBean.setSessionType(getSessionType(clazz));
                } catch (Throwable handledInValidation) {
 // no-op
                }
            }


 // Fill in default ejbName for xml declared EJBs
 for (EnterpriseBean bean : ejbModule.getEjbJar().getEnterpriseBeans()) {
 if (bean.getEjbClass() == null) continue;
 if (bean.getEjbName() == null || bean.getEjbName().startsWith("@NULL@")) {
 ejbModule.getEjbJar().removeEnterpriseBean(bean.getEjbName());
 try {
 final Class<?> clazz = ejbModule.getClassLoader().loadClass(bean.getEjbClass());
 final String ejbName = getEjbName(bean, clazz);
 bean.setEjbName(ejbName);
                    } catch (Throwable handledInValidation) {
                    }
 ejbModule.getEjbJar().addEnterpriseBean(bean);
                }
            }
 /* 19.2:  ejb-name: Default is the unqualified name of the bean class */


 EjbJar ejbJar = ejbModule.getEjbJar();
 for (Annotated<Class<?>> beanClass : finder.findMetaAnnotatedClasses(Singleton.class)) {


 if (beanClass.isAnnotationPresent(Specializes.class)) {
 managedClasses.remove(beanClass.get().getName());
 specializingClasses.add(beanClass.get());
 continue;
                }


 Singleton singleton = beanClass.getAnnotation(Singleton.class);
 String ejbName = getEjbName(singleton, beanClass.get());


 if (!isValidEjbAnnotationUsage(Singleton.class, beanClass, ejbName, ejbModule)) continue;


 EnterpriseBean enterpriseBean = ejbJar.getEnterpriseBean(ejbName);
 if (enterpriseBean == null) {
 enterpriseBean = new SingletonBean(ejbName, beanClass.get());
 ejbJar.addEnterpriseBean(enterpriseBean);
                }
 if (enterpriseBean.getEjbClass() == null) {
 enterpriseBean.setEjbClass(beanClass.get());
                }
 if (enterpriseBean instanceof SessionBean) {
 SessionBean sessionBean = (SessionBean) enterpriseBean;
 sessionBean.setSessionType(SessionType.SINGLETON);


 if (singleton.mappedName() != null) {
 sessionBean.setMappedName(singleton.mappedName());
                    }
                }
 LegacyProcessor.process(beanClass.get(), enterpriseBean);
            }


 for (Annotated<Class<?>> beanClass : finder.findMetaAnnotatedClasses(Stateless.class)) {


 if (beanClass.isAnnotationPresent(Specializes.class)) {
 managedClasses.remove(beanClass.get().getName());
 specializingClasses.add(beanClass.get());
 continue;
                }


 Stateless stateless = beanClass.getAnnotation(Stateless.class);
 String ejbName = getEjbName(stateless, beanClass.get());


 if (!isValidEjbAnnotationUsage(Stateless.class, beanClass, ejbName, ejbModule)) continue;


 EnterpriseBean enterpriseBean = ejbJar.getEnterpriseBean(ejbName);
 if (enterpriseBean == null) {
 enterpriseBean = new StatelessBean(ejbName, beanClass.get());
 ejbJar.addEnterpriseBean(enterpriseBean);
                }
 if (enterpriseBean.getEjbClass() == null) {
 enterpriseBean.setEjbClass(beanClass.get());
                }
 if (enterpriseBean instanceof SessionBean) {
 SessionBean sessionBean = (SessionBean) enterpriseBean;
 sessionBean.setSessionType(SessionType.STATELESS);


 if (stateless.mappedName() != null) {
 sessionBean.setMappedName(stateless.mappedName());
                    }
                }
 LegacyProcessor.process(beanClass.get(), enterpriseBean);
            }


 // The Specialization code is good, but it possibly needs to be moved to after the full processing of the bean
 // the plus is that it would get the required interfaces.  The minus is that it would get all the other items


 // Possibly study alternatives.  Alternatives might have different meta data completely while it seems Specializing beans inherit all meta-data


 // Anyway.. the qualifiers aren't getting inherited, so we need to fix that


 for (Annotated<Class<?>> beanClass : finder.findMetaAnnotatedClasses(Stateful.class)) {


 if (beanClass.isAnnotationPresent(Specializes.class)) {
 managedClasses.remove(beanClass.get().getName());
 specializingClasses.add(beanClass.get());
 continue;
                }


 Stateful stateful = beanClass.getAnnotation(Stateful.class);
 String ejbName = getEjbName(stateful, beanClass.get());


 if (!isValidEjbAnnotationUsage(Stateful.class, beanClass, ejbName, ejbModule)) continue;


 EnterpriseBean enterpriseBean = ejbJar.getEnterpriseBean(ejbName);
 if (enterpriseBean == null) {
 enterpriseBean = new StatefulBean(ejbName, beanClass.get());
 ejbJar.addEnterpriseBean(enterpriseBean);
                }
 if (enterpriseBean.getEjbClass() == null) {
 enterpriseBean.setEjbClass(beanClass.get());
                }
 if (enterpriseBean instanceof SessionBean) {
 SessionBean sessionBean = (SessionBean) enterpriseBean;
 // TODO: We might be stepping on an xml override here
 sessionBean.setSessionType(SessionType.STATEFUL);
 if (stateful.mappedName() != null) {
 sessionBean.setMappedName(stateful.mappedName());
                    }
                }
 LegacyProcessor.process(beanClass.get(), enterpriseBean);
            }


 for (Annotated<Class<?>> beanClass : finder.findMetaAnnotatedClasses(ManagedBean.class)) {


 if (beanClass.isAnnotationPresent(Specializes.class)) {
 managedClasses.remove(beanClass.get().getName());
 specializingClasses.add(beanClass.get());
 continue;
                }


 ManagedBean managed = beanClass.getAnnotation(ManagedBean.class);
 String ejbName = getEjbName(managed, beanClass.get());


 // TODO: this is actually against the spec, but the requirement is rather silly
 // (allowing @Stateful and @ManagedBean on the same class)
 // If the TCK doesn't complain we should discourage it
 if (!isValidEjbAnnotationUsage(ManagedBean.class, beanClass, ejbName, ejbModule)) continue;


 EnterpriseBean enterpriseBean = ejbJar.getEnterpriseBean(ejbName);
 if (enterpriseBean == null) {
 enterpriseBean = new org.apache.openejb.jee.ManagedBean(ejbName, beanClass.get());
 ejbJar.addEnterpriseBean(enterpriseBean);
                }
 if (enterpriseBean.getEjbClass() == null) {
 enterpriseBean.setEjbClass(beanClass.get());
                }
 if (enterpriseBean instanceof SessionBean) {
 SessionBean sessionBean = (SessionBean) enterpriseBean;
 sessionBean.setSessionType(SessionType.MANAGED);


 final TransactionType transactionType = sessionBean.getTransactionType();
 if (transactionType == null) sessionBean.setTransactionType(TransactionType.BEAN);
                }
            }


 for (Annotated<Class<?>> beanClass : finder.findMetaAnnotatedClasses(MessageDriven.class)) {


 if (beanClass.isAnnotationPresent(Specializes.class)) {
 managedClasses.remove(beanClass.get().getName());
 specializingClasses.add(beanClass.get());
 continue;
                }


 MessageDriven mdb = beanClass.getAnnotation(MessageDriven.class);
 String ejbName = getEjbName(mdb, beanClass.get());


 if (!isValidEjbAnnotationUsage(MessageDriven.class, beanClass, ejbName, ejbModule)) continue;


 MessageDrivenBean messageBean = (MessageDrivenBean) ejbJar.getEnterpriseBean(ejbName);
 if (messageBean == null) {
 messageBean = new MessageDrivenBean(ejbName);
 ejbJar.addEnterpriseBean(messageBean);
                }
 if (messageBean.getEjbClass() == null) {
 messageBean.setEjbClass(beanClass.get());
                }
 LegacyProcessor.process(beanClass.get(), messageBean);
            }




 for (Class<?> specializingClass : sortClassesParentFirst(new ArrayList<Class<?>>(specializingClasses))) {


 final Class<?> parent = specializingClass.getSuperclass();


 if (parent == null || parent.equals(Object.class)) {
 ejbModule.getValidation().fail(specializingClass.getSimpleName(), "specializes.extendsNothing", specializingClass.getName());
                }


 boolean found = false;


 for (EnterpriseBean enterpriseBean : ejbJar.getEnterpriseBeans()) {


 final String ejbClass = enterpriseBean.getEjbClass();


 if (ejbClass != null && ejbClass.equals(parent.getName())) {
 managedClasses.remove(ejbClass);
 enterpriseBean.setEjbClass(specializingClass.getName());
 found = true;
                    }
                }


 if (!found) {
 ejbModule.getValidation().fail(specializingClass.getSimpleName(), "specializes.extendsSimpleBean", specializingClass.getName());
                }
            }


 AssemblyDescriptor assemblyDescriptor = ejbModule.getEjbJar().getAssemblyDescriptor();
 if (assemblyDescriptor == null) {
 assemblyDescriptor = new AssemblyDescriptor();
 ejbModule.getEjbJar().setAssemblyDescriptor(assemblyDescriptor);
            }


 startupLogger.debug("Searching for annotated application exceptions (see OPENEJB-980)");
 List<Class<?>> appExceptions = finder.findAnnotatedClasses(ApplicationException.class);
 for (Class<?> exceptionClass : appExceptions) {
 startupLogger.debug("...handling " + exceptionClass);
 ApplicationException annotation = exceptionClass.getAnnotation(ApplicationException.class);
 if (assemblyDescriptor.getApplicationException(exceptionClass) == null) {
 startupLogger.debug("...adding " + exceptionClass + " with rollback=" + annotation.rollback());
 assemblyDescriptor.addApplicationException(exceptionClass, annotation.rollback(), annotation.inherited());
                } else {
 mergeApplicationExceptionAnnotation(assemblyDescriptor, exceptionClass, annotation);
                }
            }


 // ejb can be rest bean and only then in standalone so scan providers here too
 // adding them to app since they should be in the app classloader
 if (ejbModule.getAppModule() != null) {
 addJaxRsProviders(finder, ejbModule.getAppModule().getJaxRsProviders(), Provider.class);
            }


 if (ejbModule.getAppModule() != null) {
 for (PersistenceModule pm : ejbModule.getAppModule().getPersistenceModules()) {
 for (org.apache.openejb.jee.jpa.unit.PersistenceUnit pu : pm.getPersistence().getPersistenceUnit()) {
 if ((pu.isExcludeUnlistedClasses() == null || !pu.isExcludeUnlistedClasses())
                                && "true".equalsIgnoreCase(pu.getProperties().getProperty(OPENEJB_JPA_AUTO_SCAN))) {
 final String packageName = pu.getProperties().getProperty(OPENEJB_JPA_AUTO_SCAN_PACKAGE);


 // no need of meta currently since JPA providers doesn't support it
 final List<Class<?>> classes = new ArrayList<Class<?>>();
 classes.addAll(finder.findAnnotatedClasses(Entity.class));
 classes.addAll(finder.findAnnotatedClasses(Embeddable.class));
 classes.addAll(finder.findAnnotatedClasses(MappedSuperclass.class));
 final List<String> existingClasses = pu.getClazz();
 for (Class<?> clazz : classes) {
 final String name = clazz.getName();
 if ((packageName == null || name.startsWith(packageName)) && !existingClasses.contains(name)) {
 pu.getClazz().add(name);
                                }
                            }
 pu.setScanned(true);
                        }
                    }
                }
            }


 return ejbModule;
        }


 private SessionType getSessionType(Class<?> clazz) {
 if (clazz.isAnnotationPresent(Stateful.class)) return SessionType.STATEFUL;
 if (clazz.isAnnotationPresent(Stateless.class)) return SessionType.STATELESS;
 if (clazz.isAnnotationPresent(Singleton.class)) return SessionType.SINGLETON;
 if (clazz.isAnnotationPresent(ManagedBean.class)) return SessionType.MANAGED;
 return null;
        }


 private String getEjbName(EnterpriseBean bean, Class<?> clazz) {


 if (bean instanceof SessionBean) {
 SessionBean sessionBean = (SessionBean) bean;
 switch (sessionBean.getSessionType()) {
 case STATEFUL: {
 final Stateful annotation = clazz.getAnnotation(Stateful.class);
 if (annotation != null && specified(annotation.name())) {
 return annotation.name();
                        }
                    }
 case STATELESS: {
 final Stateless annotation = clazz.getAnnotation(Stateless.class);
 if (annotation != null && specified(annotation.name())) {
 return annotation.name();
                        }
                    }
 case SINGLETON: {
 final Singleton annotation = clazz.getAnnotation(Singleton.class);
 if (annotation != null && specified(annotation.name())) {
 return annotation.name();
                        }
                    }
                }
            }


 if (bean instanceof MessageDrivenBean) {
 final MessageDriven annotation = clazz.getAnnotation(MessageDriven.class);
 if (annotation != null && specified(annotation.name())) {
 return annotation.name();
                }
            }


 return clazz.getSimpleName();
        }


 private static boolean specified(final String name) {
 return name != null && name.length() != 0;
        }


 private List<String> getBeanClasses(IAnnotationFinder finder) {


 //  What we're hoping in this method is to get lucky and find
 //  that our 'finder' instances is an AnnotationFinder that is
 //  holding an AggregatedArchive so we can get the classes that
 //  that pertain to each URL for CDI purposes.
 //
 //  If not we call finder.getAnnotatedClassNames() which may return
 //  more classes than actually apply to CDI.  This can "pollute"
 //  the CDI class space and break injection points


 if (!(finder instanceof FinderFactory.ModuleLimitedFinder)) return finder.getAnnotatedClassNames();


 final IAnnotationFinder delegate = ((FinderFactory.ModuleLimitedFinder) finder).getDelegate();
 if (!(delegate instanceof AnnotationFinder)) return finder.getAnnotatedClassNames();


 final AnnotationFinder annotationFinder = (AnnotationFinder) delegate;


 final Archive archive = annotationFinder.getArchive();
 if (!(archive instanceof WebappAggregatedArchive)) return finder.getAnnotatedClassNames();


 final List<String> classes = new ArrayList<String>();


 final WebappAggregatedArchive aggregatedArchive = (WebappAggregatedArchive) archive;
 final Map<URL, List<String>> map = aggregatedArchive.getClassesMap();


 for (Map.Entry<URL, List<String>> entry : map.entrySet()) {


 if (hasBeansXml(entry.getKey())) classes.addAll(entry.getValue());
            }


 return classes;
        }


 public static boolean hasBeansXml(URL url) {
 if (url.getPath().endsWith("WEB-INF/classes/")) return true;
 if (url.getPath().endsWith("!/META-INF/beans.xml")) return true;
 try {
 final URLClassLoader loader = new URLClassLoader(new URL[]{ url } , new EmptyResourcesClassLoader());
 String[] paths = {
 "META-INF/beans.xml",
 "WEB-INF/beans.xml",
 "/WEB-INF/beans.xml",
 "/META-INF/beans.xml",
                };


 for (String path : paths) {
 if (loader.findResource(path) != null) return true;
                }
            } catch (Exception e) {
            }
 return false;
        }


 private String getEjbName(MessageDriven mdb, Class<?> beanClass) {
 return (mdb.name().isEmpty() ? beanClass.getSimpleName() : mdb.name());
        }


 private String getEjbName(Stateful stateful, Class<?> beanClass) {
 return (stateful.name().isEmpty() ? beanClass.getSimpleName() : stateful.name());
        }


 private String getEjbName(Stateless stateless, Class<?> beanClass) {
 return (stateless.name().isEmpty() ? beanClass.getSimpleName() : stateless.name());
        }


 private String getEjbName(Singleton singleton, Class<?> beanClass) {
 return (singleton.name().isEmpty() ? beanClass.getSimpleName() : singleton.name());
        }


 private String getEjbName(ManagedBean managed, Class<?> beanClass) {
 return (managed.value().isEmpty() ? beanClass.getSimpleName() : managed.value());
        }


 private boolean isValidEjbAnnotationUsage(Class annotationClass, Annotated<Class<?>> beanClass, String ejbName, EjbModule ejbModule) {
 List<Class<? extends Annotation>> annotations = new ArrayList(asList(Singleton.class, Stateless.class, Stateful.class, MessageDriven.class));
 annotations.remove(annotationClass);


 boolean b = true;
 for (Class<? extends Annotation> secondAnnotation : annotations) {
 Annotation annotation = beanClass.getAnnotation(secondAnnotation);


 if (annotation == null) continue;


 String secondEjbName = null;
 if (annotation instanceof Stateful) {
 secondEjbName = getEjbName((Stateful) annotation, beanClass.get());
                } else if (annotation instanceof Stateless) {
 secondEjbName = getEjbName((Stateless) annotation, beanClass.get());
                } else if (annotation instanceof Singleton) {
 secondEjbName = getEjbName((Singleton) annotation, beanClass.get());
                } else if (annotation instanceof MessageDriven) {
 secondEjbName = getEjbName((MessageDriven) annotation, beanClass.get());
                }


 if (ejbName.equals(secondEjbName)) {
 ejbModule.getValidation().fail(ejbName, "multiplyAnnotatedAsBean", annotationClass.getSimpleName(), secondAnnotation.getSimpleName(), ejbName, beanClass.get().getName());
                }
            }


 // not a dynamic proxy implemented bean
 if (beanClass.getAnnotation(PersistenceContext.class) == null
                    && beanClass.getAnnotation(Proxy.class) == null
                    && beanClass.get().isInterface()) {
 ejbModule.getValidation().fail(ejbName, "interfaceAnnotatedAsBean", annotationClass.getSimpleName(), beanClass.get().getName());
 return false;
            }


 if (!beanClass.get().isInterface() && isAbstract(beanClass.get().getModifiers())) {
 ejbModule.getValidation().fail(ejbName, "abstractAnnotatedAsBean", annotationClass.getSimpleName(), beanClass.get().getName());
 return false;
            }


 return b;
        }


    }