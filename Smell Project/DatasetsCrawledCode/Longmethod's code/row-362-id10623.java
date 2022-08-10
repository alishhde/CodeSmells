 public ClientListenerResponse doHandle(OdbcRequest req) {
 if (!busyLock.enterBusy())
 return new OdbcResponse(IgniteQueryErrorCode.UNKNOWN,
 "Failed to handle ODBC request because node is stopping: " + req);


 if (actx != null)
 AuthorizationContext.context(actx);


 try {
 switch (req.command()) {
 case QRY_EXEC:
 return executeQuery((OdbcQueryExecuteRequest)req);


 case QRY_EXEC_BATCH:
 return executeBatchQuery((OdbcQueryExecuteBatchRequest)req);


 case STREAMING_BATCH:
 return dispatchBatchOrdered((OdbcStreamingBatchRequest)req);


 case QRY_FETCH:
 return fetchQuery((OdbcQueryFetchRequest)req);


 case QRY_CLOSE:
 return closeQuery((OdbcQueryCloseRequest)req);


 case META_COLS:
 return getColumnsMeta((OdbcQueryGetColumnsMetaRequest)req);


 case META_TBLS:
 return getTablesMeta((OdbcQueryGetTablesMetaRequest)req);


 case META_PARAMS:
 return getParamsMeta((OdbcQueryGetParamsMetaRequest)req);


 case MORE_RESULTS:
 return moreResults((OdbcQueryMoreResultsRequest)req);
            }


 return new OdbcResponse(IgniteQueryErrorCode.UNKNOWN, "Unsupported ODBC request: " + req);
        }
 finally {
 AuthorizationContext.clear();


 busyLock.leaveBusy();
        }
    }