 @Override
 public CallableStatement prepareCall(String sql) throws SQLException {
 checkState();


 PreparedStatementHolder stmtHolder = null;
 PreparedStatementKey key = new PreparedStatementKey(sql, getCatalog(), MethodType.Precall_1);


 boolean poolPreparedStatements = holder.isPoolPreparedStatements();


 if (poolPreparedStatements) {
 stmtHolder = holder.getStatementPool().get(key);
        }


 if (stmtHolder == null) {
 try {
 stmtHolder = new PreparedStatementHolder(key, conn.prepareCall(sql));
 holder.getDataSource().incrementPreparedStatementCount();
            } catch (SQLException ex) {
 handleException(ex, sql);
            }
        }


 initStatement(stmtHolder);


 DruidPooledCallableStatement rtnVal = new DruidPooledCallableStatement(this, stmtHolder);


 holder.addTrace(rtnVal);


 return rtnVal;
    }