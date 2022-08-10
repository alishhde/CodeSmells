 protected void refreshInternal(Collection objs, OpCallbacks call) {
 if (objs == null || objs.isEmpty())
 return;
 List<Exception> exceps = null;
 try {
 // collect instances that need a refresh
 Collection<OpenJPAStateManager> load = null;
 StateManagerImpl sm;
 Object obj;
 for (Iterator<?> itr = objs.iterator(); itr.hasNext();) {
 obj = itr.next();
 if (obj == null)
 continue;


 try {
 sm = getStateManagerImpl(obj, true);
 if ((processArgument(OpCallbacks.OP_REFRESH, obj, sm, call)
                        & OpCallbacks.ACT_RUN) == 0)
 continue;


 if (sm != null) {
 if (sm.isDetached())
 throw newDetachedException(obj, "refresh");
 else if (sm.beforeRefresh(true)) {
 if (load == null)
 load = new ArrayList<>(objs.size());
 load.add(sm);
                        }
 int level = _fc.getReadLockLevel();
 int timeout = _fc.getLockTimeout();
 _lm.refreshLock(sm, level, timeout, null);
 sm.readLocked(level, level);
                    } else if (assertPersistenceCapable(obj).pcIsDetached()
                        == Boolean.TRUE)
 throw newDetachedException(obj, "refresh");
                } catch (OpenJPAException ke) {
 exceps = add(exceps, ke);
                }
            }


 // refresh all
 if (load != null) {
 Collection<Object> failed = _store.loadAll(load, null,
 StoreManager.FORCE_LOAD_REFRESH, _fc, null);
 if (failed != null && !failed.isEmpty())
 exceps = add(exceps, newObjectNotFoundException(failed));


 // perform post-refresh transitions and make sure all fetch
 // group fields are loaded
 for (Iterator<OpenJPAStateManager> itr = load.iterator(); itr.hasNext();) {
 sm = (StateManagerImpl) itr.next();
 if (failed != null && failed.contains(sm.getId()))
 continue;


 try {
 sm.afterRefresh();
 sm.load(_fc, StateManagerImpl.LOAD_FGS, null, null,
 false);
                    } catch (OpenJPAException ke) {
 exceps = add(exceps, ke);
                    }
                }
            }


 // now invoke postRefresh on all the instances
 for (Iterator<?> itr = objs.iterator(); itr.hasNext();) {
 try {
 sm = getStateManagerImpl(itr.next(), true);
 if (sm != null && !sm.isDetached())
 fireLifecycleEvent(sm.getManagedInstance(), null,
 sm.getMetaData(), LifecycleEvent.AFTER_REFRESH);
                } catch (OpenJPAException ke) {
 exceps = add(exceps, ke);
                }
            }
        } catch (OpenJPAException ke) {
 throw ke;
        } catch (RuntimeException re) {
 throw new GeneralException(re);
        }
 throwNestedExceptions(exceps, false);
    }