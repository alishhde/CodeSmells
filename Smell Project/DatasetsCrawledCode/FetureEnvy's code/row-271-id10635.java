 @Override
 public void endAccess() {
 super.endAccess() ;
 if(manager instanceof ClusterManagerBase) {
            ((ClusterManagerBase)manager).registerSessionAtReplicationValve(this);
        }
    }