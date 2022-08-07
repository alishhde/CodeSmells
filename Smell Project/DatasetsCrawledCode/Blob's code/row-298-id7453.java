@Repository
public class UserDao {


 private static final String DEFAULT_USER_CREDENTIALS_PROPERTIES = "users-credentials.properties";


 private static final Logger LOG = LoggerFactory.getLogger(UserDao.class);


 private Properties userLogins;


 @PostConstruct
 public void init() {
 loadFileLoginsDetails();
    }


 void loadFileLoginsDetails() {
 InputStream inStr = null;
 try {
 Configuration configuration = ApplicationProperties.get();
 inStr = ApplicationProperties.getFileAsInputStream(configuration, "atlas.authentication.method.file.filename", DEFAULT_USER_CREDENTIALS_PROPERTIES);
 userLogins = new Properties();
 userLogins.load(inStr);
        } catch (IOException | AtlasException e) {
 LOG.error("Error while reading user.properties file", e);
 throw new RuntimeException(e);
        } finally {
 if(inStr != null) {
 try {
 inStr.close();
                } catch(Exception excp) {
 // ignore
                }
            }
        }
    }


 public User loadUserByUsername(final String username)
 throws AuthenticationException {
 String userdetailsStr = userLogins.getProperty(username);
 if (userdetailsStr == null || userdetailsStr.isEmpty()) {
 throw new UsernameNotFoundException("Username not found."
                    + username);
        }
 String password = "";
 String role = "";
 String dataArr[] = userdetailsStr.split("::");
 if (dataArr != null && dataArr.length == 2) {
 role = dataArr[0];
 password = dataArr[1];
        } else {
 LOG.error("User role credentials is not set properly for {}", username);
 throw new AtlasAuthenticationException("User role credentials is not set properly for " + username );
        }


 List<GrantedAuthority> grantedAuths = new ArrayList<>();
 if (StringUtils.hasText(role)) {
 grantedAuths.add(new SimpleGrantedAuthority(role));
        } else {
 LOG.error("User role credentials is not set properly for {}", username);
 throw new AtlasAuthenticationException("User role credentials is not set properly for " + username );
        }


 User userDetails = new User(username, password, grantedAuths);


 return userDetails;
    }
 


 @VisibleForTesting
 public void setUserLogins(Properties userLogins) {
 this.userLogins = userLogins;
    }




 public static String getSha256Hash(String base) throws AtlasAuthenticationException {
 try {
 MessageDigest digest = MessageDigest.getInstance("SHA-256");
 byte[] hash = digest.digest(base.getBytes("UTF-8"));
 StringBuffer hexString = new StringBuffer();


 for (byte aHash : hash) {
 String hex = Integer.toHexString(0xff & aHash);
 if (hex.length() == 1) hexString.append('0');
 hexString.append(hex);
            }
 return hexString.toString();


        } catch (Exception ex) {
 throw new AtlasAuthenticationException("Exception while encoding password.", ex);
        }
    }


}