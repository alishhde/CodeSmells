 public Object doExecute() throws Exception {


 Group group = groupManager.findGroupByName(groupName);
 if (group == null) {
 System.err.println("Cluster group " + groupName + " doesn't exist");
 return null;
        }


 CellarSupport support = new CellarSupport();
 support.setClusterManager(clusterManager);
 support.setGroupManager(groupManager);
 support.setConfigurationAdmin(configurationAdmin);


 if (!in && !out) {
 in = true;
 out = true;
        }
 if (!whitelist && !blacklist) {
 whitelist = true;
 blacklist = true;
        }


 if (pid == null || pid.isEmpty()) {
 // display mode
 if (in) {
 System.out.println("INBOUND:");
 if (whitelist) {
 System.out.print("\twhitelist: ");
 Set<String> list = support.getListEntries(Configurations.WHITELIST, groupName, Constants.CATEGORY, EventType.INBOUND);
 System.out.println(list.toString());
                }
 if (blacklist) {
 System.out.print("\tblacklist: ");
 Set<String> list = support.getListEntries(Configurations.BLACKLIST, groupName, Constants.CATEGORY, EventType.INBOUND);
 System.out.println(list.toString());
                }
            }
 if (out) {
 System.out.println("OUTBOUND:");
 if (whitelist) {
 System.out.print("\twhitelist: ");
 Set<String> list = support.getListEntries(Configurations.WHITELIST, groupName, Constants.CATEGORY, EventType.OUTBOUND);
 System.out.println(list.toString());
                }
 if (blacklist) {
 System.out.print("\tblacklist: ");
 Set<String> list = support.getListEntries(Configurations.BLACKLIST, groupName, Constants.CATEGORY, EventType.OUTBOUND);
 System.out.println(list.toString());
                }
            }
        } else {
 // edit mode
 System.out.println("Updating blocking policy for " + pid);
 if (in) {
 if (whitelist) {
 System.out.println("\tinbound whitelist ...");
 support.switchListEntry(Configurations.WHITELIST, groupName, Constants.CATEGORY, EventType.INBOUND, pid);
                }
 if (blacklist) {
 System.out.println("\tinbound blacklist ...");
 support.switchListEntry(Configurations.BLACKLIST, groupName, Constants.CATEGORY, EventType.INBOUND, pid);
                }
            }
 if (out) {
 if (whitelist) {
 System.out.println("\toutbound whitelist ...");
 support.switchListEntry(Configurations.WHITELIST, groupName, Constants.CATEGORY, EventType.OUTBOUND, pid);
                }
 if (blacklist) {
 System.out.println("\toutbound blacklist ...");
 support.switchListEntry(Configurations.BLACKLIST, groupName, Constants.CATEGORY, EventType.OUTBOUND, pid);
                }
            }
        }


 return null;
    }