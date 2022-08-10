 protected SQLBuffer toBulkOperation(ClassMapping mapping, Select sel,
 JDBCStore store, Object[] params, Map updateParams) {
 SQLBuffer sql = new SQLBuffer(this);
 if (updateParams == null) {
 if (requiresTargetForDelete) {
 sql.append("DELETE ");
 SQLBuffer deleteTargets = getDeleteTargets(sel);
 sql.append(deleteTargets);
 sql.append(" FROM ");
          } else {
 sql.append("DELETE FROM ");
          }
        }
 else
 sql.append("UPDATE ");
 sel.addJoinClassConditions();


 // if there is only a single table in the select, then we can
 // just issue a single DELETE FROM TABLE WHERE <conditions>
 // statement; otherwise, since SQL doesn't allow deleting
 // from one of a multi-table select, we need to issue a subselect
 // like DELETE FROM TABLE WHERE EXISTS
 // (SELECT 1 FROM TABLE t0 WHERE t0.ID = TABLE.ID); also, some
 // databases do not allow aliases in delete statements, which
 // also causes us to use a subselect
 Collection<String> selectedTables = getSelectTableAliases(sel);
 if (selectedTables.size() == 1 && supportsSubselect
            && allowsAliasInBulkClause) {
 SQLBuffer from;
 if (sel.getFromSelect() != null)
 from = getFromSelect(sel, false);
 else
 from = getFrom(sel, false);


 sql.append(from);
 appendUpdates(sel, store, sql, params, updateParams,
 allowsAliasInBulkClause);


 SQLBuffer where = sel.getWhere();
 if (where != null && !where.isEmpty()) {
 sql.append(" WHERE ");
 sql.append(where);
            }
 return sql;
        }


 Table table = mapping.getTable();
 String tableName = getFullName(table, false);


 // only use a  subselect if the where is not empty; otherwise
 // an unqualified delete or update will work
 if (sel.getWhere() == null || sel.getWhere().isEmpty()) {
 sql.append(tableName);
 appendUpdates(sel, store, sql, params, updateParams, false);
 return sql;
        }


 // we need to use a subselect if we are to bulk delete where
 // the select includes multiple tables; if the database
 // doesn't support it, then we need to signal this by returning null
 if (!supportsSubselect || !supportsCorrelatedSubselect)
 return null;


 Column[] pks = mapping.getPrimaryKeyColumns();
 sel.clearSelects();
 sel.setDistinct(true);


 // if we have only a single PK, we can use a non-correlated
 // subquery (using an IN statement), which is much faster than
 // a correlated subquery (since a correlated subquery needs
 // to be executed once for each row in the table)
 if (pks.length == 1) {
 sel.select(pks[0]);
 sql.append(tableName);
 appendUpdates(sel, store, sql, params, updateParams, false);
 sql.append(" WHERE ").
 append(pks[0]).append(" IN (").
 append(sel.toSelect(false, null)).append(")");
        } else {
 sel.clearSelects();
 sel.setDistinct(false);


 // since the select is using a correlated subquery, we
 // only need to select a bogus virtual column
 sel.select("1", null);


 // add in the joins to the table
 Column[] cols = table.getPrimaryKey().getColumns();
 SQLBuffer buf = new SQLBuffer(this);
 buf.append("(");
 for (int i = 0; i < cols.length; i++) {
 if (i > 0)
 buf.append(" AND ");


 // add in "t0.PK = MYTABLE.PK"
 buf.append(sel.getColumnAlias(cols[i])).append(" = ").
 append(table).append(catalogSeparator).append(cols[i]);
            }
 buf.append(")");
 sel.where(buf, null);


 sql.append(tableName);
 appendUpdates(sel, store, sql, params, updateParams, false);
 sql.append(" WHERE EXISTS (").
 append(sel.toSelect(false, null)).append(")");
        }
 return sql;
    }