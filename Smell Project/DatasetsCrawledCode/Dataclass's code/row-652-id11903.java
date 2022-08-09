 public static class BaseColumnInfo implements Serializable {


 /**
     * Serial version id.
     */
 private static final long serialVersionUID = 1L;


 /**
     * The table and alias info encapsulated in a different class.
     */
 private TableAliasInfo tabAlias;


 /**
     * The metastore column information. The column can be null
     * and that denotes that the expression is dependent on the row
     * of the table and not particular column. This can happen in case
     * of count(1).
     */
 private FieldSchema column;


 /**
     * @return the tabAlias
     */
 public TableAliasInfo getTabAlias() {
 return tabAlias;
    }


 /**
     * @param tabAlias the tabAlias to set
     */
 public void setTabAlias(TableAliasInfo tabAlias) {
 this.tabAlias = tabAlias;
    }


 /**
     * @return the column
     */
 public FieldSchema getColumn() {
 return column;
    }


 /**
     * @param column the column to set
     */
 public void setColumn(FieldSchema column) {
 this.column = column;
    }


 @Override
 public String toString() {
 return tabAlias + ":" + column;
    }


 @Override
 public int hashCode() {
 return (column != null ? column.hashCode() : 7)
        + (tabAlias != null ? tabAlias.hashCode() : 11);
    }


 @Override
 public boolean equals(Object obj) {
 if (this == obj) {
 return true;
      }
 if (!(obj instanceof BaseColumnInfo)) {
 return false;
      }
 BaseColumnInfo ci = (BaseColumnInfo) obj;
 return (column == null ? ci.column == null : column.equals(ci.column))
        && (tabAlias == null ? ci.tabAlias == null : tabAlias.equals(ci.tabAlias));
    }
  }