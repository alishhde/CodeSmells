 @Override
 @SuppressWarnings("unchecked")
 public int executeUpdate(final String inSql) throws SQLException {
 this.sql = inSql;
 if (this.sql == null) {
 throw new SQLException("sql is null");
        }


 trimSQL();


 if (this.sql.length() == 0) {
 throw new SQLException("empty sql");
        }


 String lowcaseSql = this.sql.toLowerCase();


 Object req = null;
 // TODO use patterns
 if (lowcaseSql.startsWith("create domain") || lowcaseSql.startsWith("create table")) { //$NON-NLS-1$
 int pos = this.sql.lastIndexOf(" ");
 String domain = convertSQLIdentifierToCatalogFormat(this.sql.substring(pos + 1).trim(),
 DELIMITED_IDENTIFIER_QUOTE);
 req = new CreateDomainRequest().withDomainName(domain);
        } else if (lowcaseSql.startsWith("delete domain") || lowcaseSql.startsWith("delete table") //$NON-NLS-1$
                || lowcaseSql.startsWith("drop table")) {
 int pos = this.sql.lastIndexOf(" ");
 String domain = convertSQLIdentifierToCatalogFormat(this.sql.substring(pos + 1).trim(),
 DELIMITED_IDENTIFIER_QUOTE);
 List<String> pending = this.conn.getPendingColumns(domain);
 if (pending != null) {
 pending = new ArrayList<>(pending);
 for (String attr : pending) {
 this.conn.removePendingColumn(domain, attr);
                }
            }
 req = new DeleteDomainRequest().withDomainName(domain);
        } else if (lowcaseSql.startsWith("delete from")) {
 req = prepareDeleteRowRequest();
        } else if (lowcaseSql.startsWith("alter table ")) {
 req = prepareDropAttributeRequest();
        } else if (lowcaseSql.startsWith("insert ")) {
 req = prepareInsertRequest();
        } else if (lowcaseSql.startsWith("update ")) {
 req = prepareUpdateRequest();
        } else if (lowcaseSql.startsWith("create testdomain ")) {
 req = new ArrayList<>();


 String domain = convertSQLIdentifierToCatalogFormat(this.sql.substring(this.sql.lastIndexOf(" ") + 1).trim(), //$NON-NLS-1$
 DELIMITED_IDENTIFIER_QUOTE);
            ((List<Object>) req).add(new CreateDomainRequest().withDomainName(domain));


 ReplaceableAttribute attr  = new ReplaceableAttribute().withName("attr1").withValue("val1").withReplace(Boolean.TRUE);
 for (int i = 0; i < 570; i++) {
                ((List<Object>) req).add(new PutAttributesRequest().withDomainName(domain).withItemName("item" + i).withAttributes(attr));
            }
        }


 if (req != null) {
 int result = executeSDBRequest(req);
 if (this.params != null) {
 for (Object obj : this.params) {
 if (obj instanceof SimpleDBItemName) {
                        ((SimpleDBItemName) obj).setPersisted(true);
                    }
                }
            }
 return result;
        }


 throw new SQLException("unsupported update: " + this.sql);
    }