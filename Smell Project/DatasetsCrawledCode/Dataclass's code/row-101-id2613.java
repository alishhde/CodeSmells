 public static class MetaColumn implements Named {
 public final String tableCat;
 public final String tableSchem;
 public final String tableName;
 public final String columnName;
 public final int dataType;
 public final String typeName;
 public final Integer columnSize;
 public final Integer bufferLength = null;
 public final Integer decimalDigits;
 public final Integer numPrecRadix;
 public final int nullable;
 public final String remarks = null;
 public final String columnDef = null;
 public final Integer sqlDataType = null;
 public final Integer sqlDatetimeSub = null;
 public final Integer charOctetLength;
 public final int ordinalPosition;
 @NotNull
 public final String isNullable;
 public final String scopeCatalog = null;
 public final String scopeSchema = null;
 public final String scopeTable = null;
 public final Short sourceDataType = null;
 @NotNull
 public final String isAutoincrement = "";
 @NotNull
 public final String isGeneratedcolumn = "";


 public MetaColumn(
 String tableCat,
 String tableSchem,
 String tableName,
 String columnName,
 int dataType,
 String typeName,
 Integer columnSize,
 Integer decimalDigits,
 Integer numPrecRadix,
 int nullable,
 Integer charOctetLength,
 int ordinalPosition,
 String isNullable) {
 this.tableCat = tableCat;
 this.tableSchem = tableSchem;
 this.tableName = tableName;
 this.columnName = columnName;
 this.dataType = dataType;
 this.typeName = typeName;
 this.columnSize = columnSize;
 this.decimalDigits = decimalDigits;
 this.numPrecRadix = numPrecRadix;
 this.nullable = nullable;
 this.charOctetLength = charOctetLength;
 this.ordinalPosition = ordinalPosition;
 this.isNullable = isNullable;
    }


 @Override
 public String getName() {
 return columnName;
    }
  }