 public static class ScanOptions extends CommonOpts {


 @Parameter(names = "-s", description = "Start row (inclusive) of scan")
 private String startRow;


 @Parameter(names = "-e", description = "End row (inclusive) of scan")
 private String endRow;


 @Parameter(names = "-c", description = "Columns of scan in comma separated format: "
        + "<<columnfamily>[:<columnqualifier>]{,<columnfamily>[:<columnqualifier>]}> ")
 private List<String> columns;


 @Parameter(names = "-r", description = "Exact row to scan")
 private String exactRow;


 @Parameter(names = "-p", description = "Row prefix to scan")
 private String rowPrefix;


 @Parameter(names = {"-esc", "--escape-non-ascii"}, help = true,
 description = "Hex encode non ascii bytes", arity = 1)
 public boolean hexEncNonAscii = true;


 @Parameter(names = "--raw", help = true,
 description = "Show underlying key/values stored in Accumulo. Interprets the data using Fluo "
            + "internal schema, making it easier to comprehend.")
 public boolean scanAccumuloTable = false;


 @Parameter(names = "--json", help = true,
 description = "Export key/values stored in Accumulo as JSON file.")
 public boolean exportAsJson = false;


 @Parameter(names = "--ntfy", help = true, description = "Scan active notifications")
 public boolean scanNtfy = false;


 public String getStartRow() {
 return startRow;
    }


 public String getEndRow() {
 return endRow;
    }


 public String getExactRow() {
 return exactRow;
    }


 public String getRowPrefix() {
 return rowPrefix;
    }


 public List<String> getColumns() {
 if (columns == null) {
 return Collections.emptyList();
      }
 return columns;
    }


 /**
     * Check if the parameters informed can be used together.
     */
 private void checkScanOptions() {
 if (this.scanAccumuloTable && this.exportAsJson) {
 throw new IllegalArgumentException(
 "Both \"--raw\" and \"--json\" can not be set together.");
      }


 if (this.scanAccumuloTable && this.scanNtfy) {
 throw new IllegalArgumentException(
 "Both \"--raw\" and \"--ntfy\" can not be set together.");
      }
    }


 public ScanUtil.ScanOpts getScanOpts() {
 EnumSet<ScanFlags> flags = EnumSet.noneOf(ScanFlags.class);


 ScanUtil.setFlag(flags, help, ScanFlags.HELP);
 ScanUtil.setFlag(flags, hexEncNonAscii, ScanFlags.HEX);
 ScanUtil.setFlag(flags, scanAccumuloTable, ScanFlags.ACCUMULO);
 ScanUtil.setFlag(flags, exportAsJson, ScanFlags.JSON);
 ScanUtil.setFlag(flags, scanNtfy, ScanFlags.NTFY);


 return new ScanUtil.ScanOpts(startRow, endRow, columns, exactRow, rowPrefix, flags);
    }


 public static ScanOptions parse(String[] args) {
 ScanOptions opts = new ScanOptions();
 parse("fluo scan", opts, args);
 return opts;
    }
  }