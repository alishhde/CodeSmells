 protected String getTableStatus( Statement sStatement )
 throws SQLException
    {
 ResultSet statusResultSet = sStatement.executeQuery( "show table status" );
 StringBuilder statusString = new StringBuilder();
 int numColumns = statusResultSet.getMetaData().getColumnCount();
 while ( statusResultSet.next() )
        {
 statusString.append( "\n" );
 for ( int i = 1; i <= numColumns; i++ )
            {
 statusString.append( statusResultSet.getMetaData().getColumnLabel( i ) + " ["
                    + statusResultSet.getString( i ) + "]  |  " );
            }
        }
 return statusString.toString();
    }