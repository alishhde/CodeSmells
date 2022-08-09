 static class OfflineIteratorEnvironment implements IteratorEnvironment {


 private final Authorizations authorizations;
 private AccumuloConfiguration conf;
 private boolean useSample;
 private SamplerConfiguration sampleConf;


 public OfflineIteratorEnvironment(Authorizations auths, AccumuloConfiguration acuTableConf,
 boolean useSample, SamplerConfiguration samplerConf) {
 this.authorizations = auths;
 this.conf = acuTableConf;
 this.useSample = useSample;
 this.sampleConf = samplerConf;
    }


 @Deprecated
 @Override
 public AccumuloConfiguration getConfig() {
 return conf;
    }


 @Override
 public IteratorScope getIteratorScope() {
 return IteratorScope.scan;
    }


 @Override
 public boolean isFullMajorCompaction() {
 return false;
    }


 @Override
 public boolean isUserCompaction() {
 return false;
    }


 private ArrayList<SortedKeyValueIterator<Key,Value>> topLevelIterators = new ArrayList<>();


 @Deprecated
 @Override
 public void registerSideChannel(SortedKeyValueIterator<Key,Value> iter) {
 topLevelIterators.add(iter);
    }


 @Override
 public Authorizations getAuthorizations() {
 return authorizations;
    }


 SortedKeyValueIterator<Key,Value> getTopLevelIterator(SortedKeyValueIterator<Key,Value> iter) {
 if (topLevelIterators.isEmpty())
 return iter;
 ArrayList<SortedKeyValueIterator<Key,Value>> allIters = new ArrayList<>(topLevelIterators);
 allIters.add(iter);
 return new MultiIterator(allIters, false);
    }


 @Override
 public boolean isSamplingEnabled() {
 return useSample;
    }


 @Override
 public SamplerConfiguration getSamplerConfiguration() {
 return sampleConf;
    }


 @Override
 public IteratorEnvironment cloneWithSamplingEnabled() {
 if (sampleConf == null)
 throw new SampleNotPresentException();
 return new OfflineIteratorEnvironment(authorizations, conf, true, sampleConf);
    }
  }