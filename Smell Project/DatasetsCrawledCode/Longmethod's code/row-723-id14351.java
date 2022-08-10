 private boolean fetchNext() throws IgniteCheckedException {
 if (data == null)
 return false;


 try {
 if (!data.next()) {
 close();


 return false;
            }


 if (res != null) {
 Value[] values = res.currentRow();


 for (int c = 0; c < row.length; c++) {
 Value val = values[c];


 if (val instanceof GridH2ValueCacheObject) {
 GridH2ValueCacheObject valCacheObj = (GridH2ValueCacheObject)values[c];


 row[c] = valCacheObj.getObject(true);
                    }
 else
 row[c] = val.getObject();
                }
            }
 else {
 for (int c = 0; c < row.length; c++)
 row[c] = data.getObject(c + 1);
            }


 return true;
        }
 catch (SQLException e) {
 throw new IgniteSQLException(e);
        }
    }