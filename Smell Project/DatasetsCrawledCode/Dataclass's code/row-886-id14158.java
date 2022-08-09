public class JdbcFieldInfo extends FieldInfo
{
 private int sqlType;


 public JdbcFieldInfo()
  {
  }


 public JdbcFieldInfo(String columnName, String pojoFieldExpression, SupportType type, int sqlType)
  {
 super(columnName, pojoFieldExpression, type);


 this.sqlType = sqlType;
  }


 public int getSqlType()
  {
 return sqlType;
  }


 /**
   * Set the sql data type for this {@link JdbcFieldInfo}
   * @param sqlType
   */
 public void setSqlType(int sqlType)
  {
 this.sqlType = sqlType;
  }
}