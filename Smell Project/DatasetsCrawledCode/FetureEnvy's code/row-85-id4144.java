 public void delete(UserInfo user) throws CoreException {
 JSONObject sites = getSites(user);
 if (!sites.has(getId())) {
 //nothing to do, site does not exist
 return;
		}
 sites.remove(getId());
 user.setProperty(SiteConfigurationConstants.KEY_SITE_CONFIGURATIONS, sites.toString());
 OrionConfiguration.getMetaStore().updateUser(user);
	}