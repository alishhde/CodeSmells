public class CoordActionQueryExecutor extends
 QueryExecutor<CoordinatorActionBean, CoordActionQueryExecutor.CoordActionQuery> {


 public enum CoordActionQuery {
 UPDATE_COORD_ACTION,
 UPDATE_COORD_ACTION_STATUS_PENDING_TIME,
 UPDATE_COORD_ACTION_FOR_INPUTCHECK,
 UPDATE_COORD_ACTION_FOR_PUSH_INPUTCHECK,
 UPDATE_COORD_ACTION_DEPENDENCIES,
 UPDATE_COORD_ACTION_FOR_START,
 UPDATE_COORD_ACTION_FOR_MODIFIED_DATE,
 UPDATE_COORD_ACTION_RERUN,
 GET_COORD_ACTION,
 GET_COORD_ACTION_STATUS,
 GET_COORD_ACTIVE_ACTIONS_COUNT_BY_JOBID,
 GET_COORD_ACTIONS_BY_LAST_MODIFIED_TIME,
 GET_COORD_ACTIONS_STATUS_UNIGNORED,
 GET_COORD_ACTIONS_PENDING_COUNT,
 GET_ACTIVE_ACTIONS_IDS_FOR_SLA_CHANGE,
 GET_ACTIVE_ACTIONS_JOBID_FOR_SLA_CHANGE,
 GET_TERMINATED_ACTIONS_FOR_DATES,
 GET_TERMINATED_ACTION_IDS_FOR_DATES,
 GET_ACTIVE_ACTIONS_FOR_DATES,
 GET_COORD_ACTIONS_WAITING_READY_SUBMITTED_OLDER_THAN,
 GET_COORD_ACTIONS_FOR_RECOVERY_OLDER_THAN,
 GET_COORD_ACTION_FOR_SLA,
 GET_COORD_ACTION_FOR_INPUTCHECK
    };


 private static CoordActionQueryExecutor instance = new CoordActionQueryExecutor();


 private CoordActionQueryExecutor() {
    }


 public static QueryExecutor<CoordinatorActionBean, CoordActionQueryExecutor.CoordActionQuery> getInstance() {
 return CoordActionQueryExecutor.instance;
    }


 @Override
 public Query getUpdateQuery(CoordActionQuery namedQuery, CoordinatorActionBean actionBean, EntityManager em)
 throws JPAExecutorException {


 Query query = em.createNamedQuery(namedQuery.name());
 switch (namedQuery) {
 case UPDATE_COORD_ACTION:
 query.setParameter("actionNumber", actionBean.getActionNumber());
 query.setParameter("actionXml", actionBean.getActionXmlBlob());
 query.setParameter("consoleUrl", actionBean.getConsoleUrl());
 query.setParameter("createdConf", actionBean.getCreatedConfBlob());
 query.setParameter("errorCode", actionBean.getErrorCode());
 query.setParameter("errorMessage", actionBean.getErrorMessage());
 query.setParameter("externalStatus", actionBean.getExternalStatus());
 query.setParameter("missingDependencies", actionBean.getMissingDependenciesBlob());
 query.setParameter("runConf", actionBean.getRunConfBlob());
 query.setParameter("timeOut", actionBean.getTimeOut());
 query.setParameter("trackerUri", actionBean.getTrackerUri());
 query.setParameter("type", actionBean.getType());
 query.setParameter("createdTime", actionBean.getCreatedTimestamp());
 query.setParameter("externalId", actionBean.getExternalId());
 query.setParameter("jobId", actionBean.getJobId());
 query.setParameter("lastModifiedTime", new Date());
 query.setParameter("nominalTime", actionBean.getNominalTimestamp());
 query.setParameter("slaXml", actionBean.getSlaXmlBlob());
 query.setParameter("status", actionBean.getStatus().toString());
 query.setParameter("id", actionBean.getId());
 break;


 case UPDATE_COORD_ACTION_STATUS_PENDING_TIME:
 query.setParameter("status", actionBean.getStatus().toString());
 query.setParameter("pending", actionBean.getPending());
 query.setParameter("lastModifiedTime", new Date());
 query.setParameter("id", actionBean.getId());
 break;


 case UPDATE_COORD_ACTION_FOR_INPUTCHECK:
 query.setParameter("status", actionBean.getStatus().toString());
 query.setParameter("lastModifiedTime", new Date());
 query.setParameter("actionXml", actionBean.getActionXmlBlob());
 query.setParameter("missingDependencies", actionBean.getMissingDependenciesBlob());
 query.setParameter("id", actionBean.getId());
 break;


 case UPDATE_COORD_ACTION_FOR_PUSH_INPUTCHECK:
 query.setParameter("status", actionBean.getStatus().toString());
 query.setParameter("lastModifiedTime", new Date());
 query.setParameter("actionXml", actionBean.getActionXmlBlob());
 query.setParameter("pushMissingDependencies", actionBean.getPushMissingDependenciesBlob());
 query.setParameter("id", actionBean.getId());
 break;


 case UPDATE_COORD_ACTION_DEPENDENCIES:
 query.setParameter("missingDependencies", actionBean.getMissingDependenciesBlob());
 query.setParameter("pushMissingDependencies", actionBean.getPushMissingDependenciesBlob());
 query.setParameter("id", actionBean.getId());
 break;


 case UPDATE_COORD_ACTION_FOR_START:
 query.setParameter("status", actionBean.getStatus().toString());
 query.setParameter("lastModifiedTime", new Date());
 query.setParameter("runConf", actionBean.getRunConfBlob());
 query.setParameter("externalId", actionBean.getExternalId());
 query.setParameter("pending", actionBean.getPending());
 query.setParameter("errorCode", actionBean.getErrorCode());
 query.setParameter("errorMessage", actionBean.getErrorMessage());
 query.setParameter("id", actionBean.getId());
 break;


 case UPDATE_COORD_ACTION_FOR_MODIFIED_DATE:
 query.setParameter("lastModifiedTime", actionBean.getLastModifiedTimestamp());
 query.setParameter("id", actionBean.getId());
 break;


 case UPDATE_COORD_ACTION_RERUN:
 query.setParameter("actionXml", actionBean.getActionXmlBlob());
 query.setParameter("status", actionBean.getStatusStr());
 query.setParameter("externalId", actionBean.getExternalId());
 query.setParameter("externalStatus", actionBean.getExternalStatus());
 query.setParameter("rerunTime", actionBean.getRerunTimestamp());
 query.setParameter("lastModifiedTime", actionBean.getLastModifiedTimestamp());
 query.setParameter("createdTime", actionBean.getCreatedTimestamp());
 query.setParameter("createdConf", actionBean.getCreatedConfBlob());
 query.setParameter("runConf", actionBean.getRunConfBlob());
 query.setParameter("missingDependencies", actionBean.getMissingDependenciesBlob());
 query.setParameter("pushMissingDependencies", actionBean.getPushMissingDependenciesBlob());
 query.setParameter("errorCode", actionBean.getErrorCode());
 query.setParameter("errorMessage", actionBean.getErrorMessage());
 query.setParameter("id", actionBean.getId());
 break;


 default:
 throw new JPAExecutorException(ErrorCode.E0603, "QueryExecutor cannot set parameters for "
                        + namedQuery.name());
        }
 return query;
    }


 @Override
 public Query getSelectQuery(CoordActionQuery namedQuery, EntityManager em, Object... parameters)
 throws JPAExecutorException {
 Query query = em.createNamedQuery(namedQuery.name());
 CoordActionQuery caQuery = (CoordActionQuery) namedQuery;
 switch (caQuery) {
 case GET_COORD_ACTION:
 case GET_COORD_ACTION_STATUS:
 case GET_COORD_ACTION_FOR_SLA:
 case GET_COORD_ACTION_FOR_INPUTCHECK:
 query.setParameter("id", parameters[0]);
 break;
 case GET_COORD_ACTIONS_BY_LAST_MODIFIED_TIME:
 query.setParameter("lastModifiedTime", new Timestamp(((Date) parameters[0]).getTime()));
 break;
 case GET_COORD_ACTIONS_STATUS_UNIGNORED:
 query.setParameter("jobId", parameters[0]);
 break;
 case GET_COORD_ACTIONS_PENDING_COUNT:
 query.setParameter("jobId", parameters[0]);
 break;
 case GET_ACTIVE_ACTIONS_IDS_FOR_SLA_CHANGE:
 query.setParameter("ids", parameters[0]);
 break;
 case GET_ACTIVE_ACTIONS_JOBID_FOR_SLA_CHANGE:
 query.setParameter("jobId", parameters[0]);
 break;
 case GET_TERMINATED_ACTIONS_FOR_DATES:
 case GET_TERMINATED_ACTION_IDS_FOR_DATES:
 case GET_ACTIVE_ACTIONS_FOR_DATES:
 query.setParameter("jobId", parameters[0]);
 query.setParameter("startTime", new Timestamp(((Date) parameters[1]).getTime()));
 query.setParameter("endTime", new Timestamp(((Date) parameters[2]).getTime()));
 break;
 case GET_COORD_ACTIONS_FOR_RECOVERY_OLDER_THAN:
 query.setParameter("lastModifiedTime", new Timestamp(((Date) parameters[0]).getTime()));
 break;
 case GET_COORD_ACTIONS_WAITING_READY_SUBMITTED_OLDER_THAN:
 query.setParameter("lastModifiedTime", new Timestamp(((Date) parameters[0]).getTime()));
 query.setParameter("currentTime", new Timestamp(new Date().getTime()));
 break;


 default:
 throw new JPAExecutorException(ErrorCode.E0603, "QueryExecutor cannot set parameters for "
                        + caQuery.name());
        }
 return query;
    }


 @Override
 public int executeUpdate(CoordActionQuery namedQuery, CoordinatorActionBean jobBean) throws JPAExecutorException {
 JPAService jpaService = Services.get().get(JPAService.class);
 EntityManager em = jpaService.getEntityManager();
 Query query = getUpdateQuery(namedQuery, jobBean, em);
 int ret = jpaService.executeUpdate(namedQuery.name(), query, em);
 return ret;
    }


 @Override
 public CoordinatorActionBean get(CoordActionQuery namedQuery, Object... parameters) throws JPAExecutorException {
 CoordinatorActionBean bean = getIfExist(namedQuery, parameters);
 if (bean == null) {
 throw new JPAExecutorException(ErrorCode.E0605, getSelectQuery(namedQuery,
 Services.get().get(JPAService.class).getEntityManager(), parameters).toString());
        }
 return bean;
    }


 @Override
 public CoordinatorActionBean getIfExist(CoordActionQuery namedQuery, Object... parameters) throws JPAExecutorException {
 JPAService jpaService = Services.get().get(JPAService.class);
 EntityManager em = jpaService.getEntityManager();
 Query query = getSelectQuery(namedQuery, em, parameters);
 Object ret = jpaService.executeGet(namedQuery.name(), query, em);
 if (ret == null) {
 return null;
        }
 CoordinatorActionBean bean = constructBean(namedQuery, ret);
 return bean;
    }


 @Override
 public List<CoordinatorActionBean> getList(CoordActionQuery namedQuery, Object... parameters)
 throws JPAExecutorException {
 JPAService jpaService = Services.get().get(JPAService.class);
 EntityManager em = jpaService.getEntityManager();
 Query query = getSelectQuery(namedQuery, em, parameters);
 List<?> retList = (List<?>) jpaService.executeGetList(namedQuery.name(), query, em);
 List<CoordinatorActionBean> beanList = new ArrayList<CoordinatorActionBean>();
 if (retList != null) {
 for (Object ret : retList) {
 beanList.add(constructBean(namedQuery, ret));
            }
        }
 return beanList;
    }


 private CoordinatorActionBean constructBean(CoordActionQuery namedQuery, Object ret) throws JPAExecutorException {
 CoordinatorActionBean bean;
 Object[] arr;
 switch (namedQuery) {
 case GET_COORD_ACTION:
 bean = (CoordinatorActionBean) ret;
 break;
 case GET_COORD_ACTIONS_BY_LAST_MODIFIED_TIME:
 bean = new CoordinatorActionBean();
 bean.setJobId((String) ret);
 break;
 case GET_COORD_ACTION_STATUS:
 bean = new CoordinatorActionBean();
 bean.setStatusStr((String)ret);
 break;
 case GET_COORD_ACTIONS_STATUS_UNIGNORED:
 arr = (Object[]) ret;
 bean = new CoordinatorActionBean();
 bean.setStatusStr((String)arr[0]);
 bean.setPending((Integer)arr[1]);
 break;
 case GET_ACTIVE_ACTIONS_IDS_FOR_SLA_CHANGE:
 case GET_ACTIVE_ACTIONS_JOBID_FOR_SLA_CHANGE:
 arr = (Object[]) ret;
 bean = new CoordinatorActionBean();
 bean.setId((String)arr[0]);
 bean.setNominalTime((Timestamp)arr[1]);
 bean.setCreatedTime((Timestamp)arr[2]);
 bean.setActionXmlBlob((StringBlob)arr[3]);
 break;
 case GET_TERMINATED_ACTIONS_FOR_DATES:
 bean = (CoordinatorActionBean) ret;
 break;
 case GET_TERMINATED_ACTION_IDS_FOR_DATES:
 bean = new CoordinatorActionBean();
 bean.setId((String) ret);
 break;
 case GET_ACTIVE_ACTIONS_FOR_DATES:
 arr = (Object[]) ret;
 bean = new CoordinatorActionBean();
 bean.setId((String)arr[0]);
 bean.setJobId((String)arr[1]);
 bean.setStatusStr((String) arr[2]);
 bean.setExternalId((String) arr[3]);
 bean.setPending((Integer) arr[4]);
 bean.setNominalTime((Timestamp) arr[5]);
 bean.setCreatedTime((Timestamp) arr[6]);
 break;
 case GET_COORD_ACTIONS_FOR_RECOVERY_OLDER_THAN:
 arr = (Object[]) ret;
 bean = new CoordinatorActionBean();
 bean.setId((String)arr[0]);
 bean.setJobId((String)arr[1]);
 bean.setStatusStr((String) arr[2]);
 bean.setExternalId((String) arr[3]);
 bean.setPending((Integer) arr[4]);
 break;
 case GET_COORD_ACTIONS_WAITING_READY_SUBMITTED_OLDER_THAN:
 arr = (Object[]) ret;
 bean = new CoordinatorActionBean();
 bean.setId((String)arr[0]);
 bean.setJobId((String)arr[1]);
 bean.setStatusStr((String) arr[2]);
 bean.setExternalId((String) arr[3]);
 bean.setPushMissingDependenciesBlob((StringBlob) arr[4]);
 break;
 case GET_COORD_ACTION_FOR_SLA:
 arr = (Object[]) ret;
 bean = new CoordinatorActionBean();
 bean.setId((String) arr[0]);
 bean.setJobId((String) arr[1]);
 bean.setStatusStr((String) arr[2]);
 bean.setExternalId((String) arr[3]);
 bean.setLastModifiedTime((Timestamp) arr[4]);
 break;
 case GET_COORD_ACTION_FOR_INPUTCHECK:
 arr = (Object[]) ret;
 bean = new CoordinatorActionBean();
 bean.setId((String) arr[0]);
 bean.setActionNumber((Integer) arr[1]);
 bean.setJobId((String) arr[2]);
 bean.setStatus(CoordinatorAction.Status.valueOf((String) arr[3]));
 bean.setRunConfBlob((StringBlob) arr[4]);
 bean.setNominalTime(DateUtils.toDate((Timestamp) arr[5]));
 bean.setCreatedTime(DateUtils.toDate((Timestamp) arr[6]));
 bean.setActionXmlBlob((StringBlob) arr[7]);
 bean.setMissingDependenciesBlob((StringBlob) arr[8]);
 bean.setPushMissingDependenciesBlob((StringBlob) arr[9]);
 bean.setTimeOut((Integer) arr[10]);
 bean.setExternalId((String) arr[11]);
 break;


 default:
 throw new JPAExecutorException(ErrorCode.E0603, "QueryExecutor cannot construct action bean for "
                        + namedQuery.name());
        }
 return bean;
    }


 @Override
 public Object getSingleValue(CoordActionQuery namedQuery, Object... parameters) throws JPAExecutorException {
 JPAService jpaService = Services.get().get(JPAService.class);
 EntityManager em = jpaService.getEntityManager();
 Query query = getSelectQuery(namedQuery, em, parameters);
 Object ret = jpaService.executeGet(namedQuery.name(), query, em);
 if (ret == null) {
 throw new JPAExecutorException(ErrorCode.E0604, query.toString());
        }
 return ret;
    }
}