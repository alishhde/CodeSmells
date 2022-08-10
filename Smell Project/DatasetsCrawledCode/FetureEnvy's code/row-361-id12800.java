 private static void listZookeeper(ServerContext context) throws Exception {
 System.out.println("Listing volumes referenced in zookeeper");
 TreeSet<String> volumes = new TreeSet<>();


 volumes.add(getTableURI(MetadataTableUtil.getRootTabletDir(context)));
 ArrayList<LogEntry> result = new ArrayList<>();
 MetadataTableUtil.getRootLogEntries(context, result);
 for (LogEntry logEntry : result) {
 getLogURIs(volumes, logEntry);
    }


 for (String volume : volumes)
 System.out.println("\tVolume : " + volume);


  }