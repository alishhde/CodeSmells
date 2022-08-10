 private void sessionEnd0(@Nullable IgniteInternalTx tx, boolean threwEx) throws IgniteCheckedException {
 try {
 if (tx == null) {
 if (sesLsnrs != null && sesHolder.get().contains(store)) {
 for (CacheStoreSessionListener lsnr : sesLsnrs)
 lsnr.onSessionEnd(locSes, !threwEx);
                }


 if (!sesHolder.get().ended(store))
 store.sessionEnd(!threwEx);
            }
        }
 catch (Exception e) {
 if (!threwEx)
 throw U.cast(e);
        }
 finally {
 if (sesHolder != null)
 sesHolder.set(null);
        }
    }