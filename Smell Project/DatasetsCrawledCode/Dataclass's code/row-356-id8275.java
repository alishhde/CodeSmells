@SuppressWarnings("serial")
public class ForeignKeyInfo implements Serializable {


 public class ForeignKeyCol {
 public String parentTableName;
 public String parentDatabaseName;
 public String parentColName;
 public String childColName;
 public Integer position;


 public ForeignKeyCol(String parentTableName, String parentDatabaseName, String parentColName,
 String childColName, Integer position) {
 this.parentTableName = parentTableName;
 this.parentDatabaseName = parentDatabaseName;
 this.parentColName = parentColName;
 this.childColName = childColName;
 this.position = position;
    }
  }


 // Mapping from constraint name to list of foreign keys
 Map<String, List<ForeignKeyCol>> foreignKeys;
 String childTableName;
 String childDatabaseName;


 public ForeignKeyInfo() {}


 public ForeignKeyInfo(List<SQLForeignKey> fks, String childTableName, String childDatabaseName) {
 this.childTableName = childTableName;
 this.childDatabaseName = childDatabaseName;
 foreignKeys = new TreeMap<String, List<ForeignKeyCol>>();
 if (fks == null) {
 return;
    }
 for (SQLForeignKey fk : fks) {
 if (fk.getFktable_db().equalsIgnoreCase(childDatabaseName) &&
 fk.getFktable_name().equalsIgnoreCase(childTableName)) {
 ForeignKeyCol currCol = new ForeignKeyCol(fk.getPktable_name(), fk.getPktable_db(),
 fk.getPkcolumn_name(), fk.getFkcolumn_name(), fk.getKey_seq());
 String constraintName = fk.getFk_name();
 if (foreignKeys.containsKey(constraintName)) {
 foreignKeys.get(constraintName).add(currCol);
        } else {
 List<ForeignKeyCol> currList = new ArrayList<ForeignKeyCol>();
 currList.add(currCol);
 foreignKeys.put(constraintName, currList);
        }
      }
    }
  }


 public String getChildTableName() {
 return childTableName;
  }


 public String getChildDatabaseName() {
 return childDatabaseName;
  }


 public Map<String, List<ForeignKeyCol>> getForeignKeys() {
 return foreignKeys;
  }


 public void setChildTableName(String tableName) {
 this.childTableName = tableName;
  }


 public void setChildDatabaseName(String databaseName) {
 this.childDatabaseName = databaseName;
  }


 public void setForeignKeys(Map<String, List<ForeignKeyCol>> foreignKeys) {
 this.foreignKeys = foreignKeys;
  }


 @Override
 public String toString() {
 StringBuilder sb = new StringBuilder();
 sb.append("Foreign Keys for " + childDatabaseName+"."+childTableName+":");
 sb.append("[");
 if (foreignKeys != null && foreignKeys.size() > 0) {
 for (Map.Entry<String, List<ForeignKeyCol>> me : foreignKeys.entrySet()) {
 sb.append(" {Constraint Name: " + me.getKey() + ",");
 List<ForeignKeyCol> currCol = me.getValue();
 if (currCol != null && currCol.size() > 0) {
 for (ForeignKeyCol fkc : currCol) {
 sb.append (" (Parent Column Name: " + fkc.parentDatabaseName +
 "."+ fkc.parentTableName + "." + fkc.parentColName +
 ", Column Name: " + fkc.childColName + ", Key Sequence: " + fkc.position+ "),");
          }
 sb.setLength(sb.length()-1);
        }
 sb.append("},");
      }
 sb.setLength(sb.length()-1);
    }
 sb.append("]");
 return sb.toString();
  }
}