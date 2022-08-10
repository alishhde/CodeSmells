 public static boolean checkExplicitUserPassword(ManagementContext mgmt, String user, String password) {
 BrooklynProperties properties = ((ManagementContextInternal)mgmt).getBrooklynProperties();
 String expectedPassword = properties.getConfig(BrooklynWebConfig.PASSWORD_FOR_USER(user));
 String salt = properties.getConfig(BrooklynWebConfig.SALT_FOR_USER(user));
 String expectedSha256 = properties.getConfig(BrooklynWebConfig.SHA256_FOR_USER(user));
 
 return checkPassword(password, expectedPassword, expectedSha256, salt);
    }