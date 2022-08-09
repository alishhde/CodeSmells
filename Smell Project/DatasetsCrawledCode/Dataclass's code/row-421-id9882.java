public class TableAliasImpl extends SQLSyntaxElementBase<TableAlias, TableAlias>
 implements TableAlias
{
 private final String _tableAlias;


 private final ColumnNameList _columnAliases;


 public TableAliasImpl( SQLProcessorAggregator processor, String tableAlias, ColumnNameList columnNames )
    {
 this( processor, TableAlias.class, tableAlias, columnNames );
    }


 protected TableAliasImpl( SQLProcessorAggregator processor, Class<? extends TableAlias> implementingClass,
 String tableAlias, ColumnNameList columnNames )
    {
 super( processor, implementingClass );
 Objects.requireNonNull( tableAlias, "table alias table name" );
 this._tableAlias = tableAlias;
 this._columnAliases = columnNames;
    }


 public ColumnNameList getColumnAliases()
    {
 return this._columnAliases;
    }


 public String getTableAlias()
    {
 return this._tableAlias;
    }


 @Override
 protected boolean doesEqual( TableAlias another )
    {
 return this._tableAlias.equals( another.getTableAlias() )
               && bothNullOrEquals( this._columnAliases, another.getColumnAliases() );
    }
}