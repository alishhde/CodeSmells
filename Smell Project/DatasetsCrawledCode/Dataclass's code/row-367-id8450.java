 public static class CounterMark
    {
 private final Row row;
 private final ColumnMetadata column;
 private final CellPath path;


 private CounterMark(Row row, ColumnMetadata column, CellPath path)
        {
 this.row = row;
 this.column = column;
 this.path = path;
        }


 public Clustering clustering()
        {
 return row.clustering();
        }


 public ColumnMetadata column()
        {
 return column;
        }


 public CellPath path()
        {
 return path;
        }


 public ByteBuffer value()
        {
 return path == null
                 ? row.getCell(column).value()
                 : row.getCell(column, path).value();
        }


 public void setValue(ByteBuffer value)
        {
 // This is a bit of a giant hack as this is the only place where we mutate a Row object. This makes it more efficient
 // for counters however and this won't be needed post-#6506 so that's probably fine.
 assert row instanceof BTreeRow;
            ((BTreeRow)row).setValue(column, path, value);
        }
    }