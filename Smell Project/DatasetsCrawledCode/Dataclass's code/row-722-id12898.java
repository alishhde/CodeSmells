public class AggregatorColumns implements Comparable<AggregatorColumns> {
 private List<String> columnNames;
 private List<String> columnValues;


 public AggregatorColumns(List<String> columnNames, List<String> columnValues) {
 this.columnNames = columnNames;
 this.columnValues = columnValues;
    }


 public List<String> getColumnNames() {
 return columnNames;
    }


 public List<String> getColumnValues() {
 return columnValues;
    }


 @Override
 public String toString() {
 StringBuilder sb = new StringBuilder();
 for (String columnValue : columnValues) {
 sb.append(columnValue);
 sb.append(",");
        }


 return sb.deleteCharAt(sb.length() - 1).toString();
    }


 @Override
 public int compareTo(AggregatorColumns o) {
 if (this.columnValues.size() > o.columnValues.size()) {
 return 1;
        } else if (this.columnValues.size() < o.columnValues.size()) {
 return -1;
        } else {
 return this.toString().compareTo(o.toString());
        }
    }
}