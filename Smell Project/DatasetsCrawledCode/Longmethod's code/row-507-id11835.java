 @Override
 public Option[] getOptions() {
 return new Option[] {
 OptionBuilder
              .withArgName("HOST")
              .hasArg()
              .isRequired(false)
              .withDescription("Address of the Zookeeper ensemble; defaults to: "+ZK_HOST)
              .create("zkHost"),
 OptionBuilder
              .withArgName("CONFIG")
              .hasArg()
              .isRequired(false)
              .withDescription("Autoscaling config file, defaults to the one deployed in the cluster.")
              .withLongOpt("config")
              .create("a"),
 OptionBuilder
              .withDescription("Show calculated suggestions")
              .withLongOpt("suggestions")
              .create("s"),
 OptionBuilder
              .withDescription("Show ClusterState (collections layout)")
              .withLongOpt("clusterState")
              .create("c"),
 OptionBuilder
              .withDescription("Show calculated diagnostics")
              .withLongOpt("diagnostics")
              .create("d"),
 OptionBuilder
              .withDescription("Show sorted nodes with diagnostics")
              .withLongOpt("sortedNodes")
              .create("n"),
 OptionBuilder
              .withDescription("Redact node and collection names (original names will be consistently randomized)")
              .withLongOpt("redact")
              .create("r"),
 OptionBuilder
              .withDescription("Show summarized collection & node statistics.")
              .create("stats"),
 OptionBuilder
              .withDescription("Turn on all options to get all available information.")
              .create("all")


      };
    }