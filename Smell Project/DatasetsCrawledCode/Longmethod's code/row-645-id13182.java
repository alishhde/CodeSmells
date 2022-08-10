 private void grantOrRevokeRoleOnGroup(List<HivePrincipal> hivePrincipals, List<String> roles,
 HivePrincipal grantorPrinc, boolean isGrant) throws HiveAuthzPluginException,
 HiveAccessControlException {
 try {
 sentryClient = getSentryClient();
 // get principals
 Set<String> groups = Sets.newHashSet();
 for (HivePrincipal principal : hivePrincipals) {
 if (principal.getType() != HivePrincipalType.GROUP) {
 String msg =
 SentryHiveConstants.GRANT_REVOKE_NOT_SUPPORTED_FOR_PRINCIPAL + principal.getType();
 throw new HiveAuthzPluginException(msg);
        }
 groups.add(principal.getName());
      }


 // grant/revoke role to/from principals
 for (String roleName : roles) {
 if (isGrant) {
 sentryClient.grantRoleToGroups(grantorPrinc.getName(), roleName, groups);
        } else {
 sentryClient.revokeRoleFromGroups(grantorPrinc.getName(), roleName, groups);
        }
      }


    } catch (SentryAccessDeniedException e) {
 HiveOperation hiveOp = isGrant ? HiveOperation.GRANT_ROLE : HiveOperation.REVOKE_ROLE;
 executeOnFailureHooks(hiveOp, e);
    } catch (SentryUserException e) {
 String msg = "Error when sentryClient grant/revoke role:" + e.getMessage();
 executeOnErrorHooks(msg, e);
    } finally {
 if (sentryClient != null) {
 sentryClient.close();
      }
    }
  }