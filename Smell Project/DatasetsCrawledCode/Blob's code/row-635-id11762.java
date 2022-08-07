@KapuaProvider
public class CredentialServiceImpl extends AbstractKapuaConfigurableService implements CredentialService {


 private static final Logger LOGGER = LoggerFactory.getLogger(CredentialServiceImpl.class);


 public CredentialServiceImpl() {
 super(CredentialService.class.getName(), AuthenticationDomains.CREDENTIAL_DOMAIN, AuthenticationEntityManagerFactory.getInstance());
    }


 @Override
 public Credential create(CredentialCreator credentialCreator)
 throws KapuaException {
 //
 // Argument Validation
 ArgumentValidator.notNull(credentialCreator, "credentialCreator");
 ArgumentValidator.notNull(credentialCreator.getScopeId(), "credentialCreator.scopeId");
 ArgumentValidator.notNull(credentialCreator.getUserId(), "credentialCreator.userId");
 ArgumentValidator.notNull(credentialCreator.getCredentialType(), "credentialCreator.credentialType");
 ArgumentValidator.notNull(credentialCreator.getCredentialStatus(), "credentialCreator.credentialStatus");
 if (credentialCreator.getCredentialType() != CredentialType.API_KEY) {
 ArgumentValidator.notEmptyOrNull(credentialCreator.getCredentialPlainKey(), "credentialCreator.credentialKey");
        }


 if (credentialCreator.getCredentialType() == CredentialType.PASSWORD) {
 //
 // Check if a PASSWORD credential already exists for the user
 CredentialListResult existingCredentials = findByUserId(credentialCreator.getScopeId(), credentialCreator.getUserId());
 for (Credential credential : existingCredentials.getItems()) {
 if (credential.getCredentialType().equals(CredentialType.PASSWORD)) {
 throw new KapuaExistingCredentialException(CredentialType.PASSWORD);
                }
            }


 //
 // Validate Password regex
 ArgumentValidator.match(credentialCreator.getCredentialPlainKey(), CommonsValidationRegex.PASSWORD_REGEXP, "credentialCreator.credentialKey");
        }


 //
 // Check access
 KapuaLocator locator = KapuaLocator.getInstance();
 AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
 PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
 authorizationService.checkPermission(permissionFactory.newPermission(AuthenticationDomains.CREDENTIAL_DOMAIN, Actions.write, credentialCreator.getScopeId()));


 //
 // Do create
 Credential credential = null;
 EntityManager em = AuthenticationEntityManagerFactory.getEntityManager();
 try {
 em.beginTransaction();


 //
 // Do pre persist magic on key values
 String fullKey = null;
 switch (credentialCreator.getCredentialType()) {
 case API_KEY: // Generate new api key
 SecureRandom random = SecureRandom.getInstance("SHA1PRNG");


 KapuaAuthenticationSetting setting = KapuaAuthenticationSetting.getInstance();
 int preLength = setting.getInt(KapuaAuthenticationSettingKeys.AUTHENTICATION_CREDENTIAL_APIKEY_PRE_LENGTH);
 int keyLength = setting.getInt(KapuaAuthenticationSettingKeys.AUTHENTICATION_CREDENTIAL_APIKEY_KEY_LENGTH);


 byte[] bPre = new byte[preLength];
 random.nextBytes(bPre);
 String pre = Base64.encodeToString(bPre).substring(0, preLength);


 byte[] bKey = new byte[keyLength];
 random.nextBytes(bKey);
 String key = Base64.encodeToString(bKey);


 fullKey = pre + key;


 credentialCreator = new CredentialCreatorImpl(credentialCreator.getScopeId(),
 credentialCreator.getUserId(),
 credentialCreator.getCredentialType(),
 fullKey,
 credentialCreator.getCredentialStatus(),
 credentialCreator.getExpirationDate());


 break;
 case PASSWORD:
 default:
 // Don't do nothing special
 break;


            }


 credential = CredentialDAO.create(em, credentialCreator);
 credential = CredentialDAO.find(em, credential.getScopeId(), credential.getId());


 em.commit();


 //
 // Do post persist magic on key values
 switch (credentialCreator.getCredentialType()) {
 case API_KEY:
 credential.setCredentialKey(fullKey);
 break;
 case PASSWORD:
 default:
 credential.setCredentialKey(fullKey);
            }
        } catch (Exception pe) {
 em.rollback();
 throw KapuaExceptionUtils.convertPersistenceException(pe);
        } finally {
 em.close();
        }


 return credential;
    }


 @Override
 public Credential update(Credential credential)
 throws KapuaException {
 //
 // Argument Validation
 ArgumentValidator.notNull(credential, "credential");
 ArgumentValidator.notNull(credential.getId(), "credential.id");
 ArgumentValidator.notNull(credential.getScopeId(), "credential.scopeId");
 ArgumentValidator.notNull(credential.getUserId(), "credential.userId");
 ArgumentValidator.notNull(credential.getCredentialType(), "credential.credentialType");
 ArgumentValidator.notEmptyOrNull(credential.getCredentialKey(), "credential.credentialKey");


 //
 // Check access
 KapuaLocator locator = KapuaLocator.getInstance();
 AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
 PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
 authorizationService.checkPermission(permissionFactory.newPermission(AuthenticationDomains.CREDENTIAL_DOMAIN, Actions.write, credential.getScopeId()));


 return entityManagerSession.onTransactedResult(em -> {
 Credential currentCredential = CredentialDAO.find(em, credential.getScopeId(), credential.getId());


 if (currentCredential == null) {
 throw new KapuaEntityNotFoundException(Credential.TYPE, credential.getId());
            }


 if (currentCredential.getCredentialType() != credential.getCredentialType()) {
 throw new KapuaIllegalArgumentException("credentialType", credential.getCredentialType().toString());
            }


 // Passing attributes??
 return CredentialDAO.update(em, credential);
        });
    }


 @Override
 public Credential find(KapuaId scopeId, KapuaId credentialId)
 throws KapuaException {
 // Validation of the fields
 ArgumentValidator.notNull(scopeId, "scopeId");
 ArgumentValidator.notNull(credentialId, "credentialId");


 //
 // Check Access
 KapuaLocator locator = KapuaLocator.getInstance();
 AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
 PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
 authorizationService.checkPermission(permissionFactory.newPermission(AuthenticationDomains.CREDENTIAL_DOMAIN, Actions.read, scopeId));


 return entityManagerSession.onResult(em -> CredentialDAO.find(em, scopeId, credentialId));
    }


 @Override
 public CredentialListResult query(KapuaQuery<Credential> query)
 throws KapuaException {
 //
 // Argument Validation
 ArgumentValidator.notNull(query, "query");
 ArgumentValidator.notNull(query.getScopeId(), "query.scopeId");


 //
 // Check Access
 KapuaLocator locator = KapuaLocator.getInstance();
 AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
 PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
 authorizationService.checkPermission(permissionFactory.newPermission(AuthenticationDomains.CREDENTIAL_DOMAIN, Actions.read, query.getScopeId()));


 return entityManagerSession.onResult(em -> CredentialDAO.query(em, query));
    }


 @Override
 public long count(KapuaQuery<Credential> query)
 throws KapuaException {
 //
 // Argument Validation
 ArgumentValidator.notNull(query, "query");
 ArgumentValidator.notNull(query.getScopeId(), "query.scopeId");


 //
 // Check Access
 KapuaLocator locator = KapuaLocator.getInstance();
 AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
 PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
 authorizationService.checkPermission(permissionFactory.newPermission(AuthenticationDomains.CREDENTIAL_DOMAIN, Actions.read, query.getScopeId()));


 return entityManagerSession.onResult(em -> CredentialDAO.count(em, query));
    }


 @Override
 public void delete(KapuaId scopeId, KapuaId credentialId)
 throws KapuaException {
 //
 // Argument Validation
 ArgumentValidator.notNull(credentialId, "credential.id");
 ArgumentValidator.notNull(scopeId, "credential.scopeId");


 //
 // Check Access
 KapuaLocator locator = KapuaLocator.getInstance();
 AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
 PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
 authorizationService.checkPermission(permissionFactory.newPermission(AuthenticationDomains.CREDENTIAL_DOMAIN, Actions.delete, scopeId));


 entityManagerSession.onTransactedAction(em -> {
 if (CredentialDAO.find(em, scopeId, credentialId) == null) {
 throw new KapuaEntityNotFoundException(Credential.TYPE, credentialId);
            }
 CredentialDAO.delete(em, scopeId, credentialId);
        });
    }


 @Override
 public CredentialListResult findByUserId(KapuaId scopeId, KapuaId userId)
 throws KapuaException {
 //
 // Argument Validation
 ArgumentValidator.notNull(scopeId, "scopeId");
 ArgumentValidator.notNull(userId, "userId");


 //
 // Check Access
 KapuaLocator locator = KapuaLocator.getInstance();
 AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
 PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
 authorizationService.checkPermission(permissionFactory.newPermission(AuthenticationDomains.CREDENTIAL_DOMAIN, Actions.read, scopeId));


 //
 // Build query
 CredentialQuery query = new CredentialQueryImpl(scopeId);
 QueryPredicate predicate = new AttributePredicateImpl<>(CredentialAttributes.USER_ID, userId);
 query.setPredicate(predicate);


 //
 // Query and return result
 return query(query);
    }


 @Override
 public Credential findByApiKey(String apiKey) throws KapuaException {
 //
 // Argument Validation
 ArgumentValidator.notEmptyOrNull(apiKey, "apiKey");


 //
 // Do the find
 Credential credential = null;
 EntityManager em = AuthenticationEntityManagerFactory.getEntityManager();
 try {


 //
 // Build search query
 KapuaAuthenticationSetting setting = KapuaAuthenticationSetting.getInstance();
 int preLength = setting.getInt(KapuaAuthenticationSettingKeys.AUTHENTICATION_CREDENTIAL_APIKEY_PRE_LENGTH);
 String preSeparator = setting.getString(KapuaAuthenticationSettingKeys.AUTHENTICATION_CREDENTIAL_APIKEY_PRE_SEPARATOR);
 String apiKeyPreValue = apiKey.substring(0, preLength).concat(preSeparator);


 //
 // Build query
 KapuaQuery<Credential> query = new CredentialQueryImpl();
 AttributePredicateImpl<CredentialType> typePredicate = new AttributePredicateImpl<>(CredentialAttributes.CREDENTIAL_TYPE, CredentialType.API_KEY);
 AttributePredicateImpl<String> keyPredicate = new AttributePredicateImpl<>(CredentialAttributes.CREDENTIAL_KEY, apiKeyPreValue, Operator.STARTS_WITH);


 AndPredicateImpl andPredicate = new AndPredicateImpl();
 andPredicate.and(typePredicate);
 andPredicate.and(keyPredicate);


 query.setPredicate(andPredicate);


 //
 // Query
 CredentialListResult credentialListResult = CredentialDAO.query(em, query);


 //
 // Parse the result
 credential = credentialListResult.getFirstItem();


        } catch (Exception e) {
 throw KapuaExceptionUtils.convertPersistenceException(e);
        } finally {
 em.close();
        }


 //
 // Check Access
 if (credential != null) {
 KapuaLocator locator = KapuaLocator.getInstance();
 AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
 PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
 authorizationService.checkPermission(permissionFactory.newPermission(AuthenticationDomains.CREDENTIAL_DOMAIN, Actions.read, credential.getId()));
        }


 return credential;
    }


 @Override
 public void unlock(KapuaId scopeId, KapuaId credentialId) throws KapuaException {
 //
 // Argument Validation
 ArgumentValidator.notNull(scopeId, "scopeId");
 ArgumentValidator.notNull(credentialId, "credentialId");


 //
 // Check Access
 KapuaLocator locator = KapuaLocator.getInstance();
 AuthorizationService authorizationService = locator.getService(AuthorizationService.class);
 PermissionFactory permissionFactory = locator.getFactory(PermissionFactory.class);
 authorizationService.checkPermission(permissionFactory.newPermission(AuthenticationDomains.CREDENTIAL_DOMAIN, Actions.write, scopeId));


 Credential credential = find(scopeId, credentialId);
 credential.setLoginFailures(0);
 credential.setFirstLoginFailure(null);
 credential.setLoginFailuresReset(null);
 credential.setLockoutReset(null);
 update(credential);
    }


 private long countExistingCredentials(CredentialType credentialType, KapuaId scopeId, KapuaId userId) throws KapuaException {
 KapuaLocator locator = KapuaLocator.getInstance();
 CredentialFactory credentialFactory = locator.getFactory(CredentialFactory.class);
 KapuaQuery<Credential> credentialQuery = credentialFactory.newQuery(scopeId);
 CredentialType ct = credentialType;
 QueryPredicate credentialTypePredicate = new AttributePredicateImpl<>(CredentialAttributes.CREDENTIAL_TYPE, ct);
 QueryPredicate userIdPredicate = new AttributePredicateImpl<>(CredentialAttributes.USER_ID, userId);
 QueryPredicate andPredicate = new AndPredicateImpl().and(credentialTypePredicate).and(userIdPredicate);
 credentialQuery.setPredicate(andPredicate);
 return count(credentialQuery);
    }


 //@ListenServiceEvent(fromAddress="account")
 //@ListenServiceEvent(fromAddress="user")
 public void onKapuaEvent(ServiceEvent kapuaEvent) throws KapuaException {
 if (kapuaEvent == null) {
 //service bus error. Throw some exception?
        }
 LOGGER.info("CredentialService: received kapua event from {}, operation {}", kapuaEvent.getService(), kapuaEvent.getOperation());
 if ("user".equals(kapuaEvent.getService()) && "delete".equals(kapuaEvent.getOperation())) {
 deleteCredentialByUserId(kapuaEvent.getScopeId(), kapuaEvent.getEntityId());
        } else if ("account".equals(kapuaEvent.getService()) && "delete".equals(kapuaEvent.getOperation())) {
 deleteCredentialByAccountId(kapuaEvent.getScopeId(), kapuaEvent.getEntityId());
        }
    }


 private void deleteCredentialByUserId(KapuaId scopeId, KapuaId userId) throws KapuaException {
 KapuaLocator locator = KapuaLocator.getInstance();
 CredentialFactory credentialFactory = locator.getFactory(CredentialFactory.class);


 CredentialQuery query = credentialFactory.newQuery(scopeId);
 query.setPredicate(new AttributePredicateImpl<>(CredentialAttributes.USER_ID, userId));


 CredentialListResult credentialsToDelete = query(query);


 for (Credential c : credentialsToDelete.getItems()) {
 delete(c.getScopeId(), c.getId());
        }
    }


 private void deleteCredentialByAccountId(KapuaId scopeId, KapuaId accountId) throws KapuaException {
 KapuaLocator locator = KapuaLocator.getInstance();
 CredentialFactory credentialFactory = locator.getFactory(CredentialFactory.class);


 CredentialQuery query = credentialFactory.newQuery(accountId);


 CredentialListResult credentialsToDelete = query(query);


 for (Credential c : credentialsToDelete.getItems()) {
 delete(c.getScopeId(), c.getId());
        }
    }


}