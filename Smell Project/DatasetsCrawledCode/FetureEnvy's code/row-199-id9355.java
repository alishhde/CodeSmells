 public static IndexFailurePolicy getFailurePolicy(RegionCoprocessorEnvironment env)
 throws IOException {
 Configuration conf = env.getConfiguration();
 try {
 IndexFailurePolicy committer =
 conf.getClass(INDEX_FAILURE_POLICY_CONF_KEY, PhoenixIndexFailurePolicy.class,
 IndexFailurePolicy.class).newInstance();
 return committer;
    } catch (InstantiationException e) {
 throw new IOException(e);
    } catch (IllegalAccessException e) {
 throw new IOException(e);
    }
  }