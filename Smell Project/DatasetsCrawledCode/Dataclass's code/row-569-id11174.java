 public static class RowRange {
 private Text startRow;
 private Text endRow;


 public RowRange(KeyExtent ke) {
 this.startRow = ke.getPrevEndRow();
 this.endRow = ke.getEndRow();
    }


 public RowRange(TRowRange trr) {
 this.startRow = ByteBufferUtil.toText(trr.startRow);
 this.endRow = ByteBufferUtil.toText(trr.endRow);
    }


 public RowRange(Text startRow, Text endRow) {
 this.startRow = startRow;
 this.endRow = endRow;
    }


 public Range toRange() {
 return new Range(startRow, false, endRow, true);
    }


 public TRowRange toThrift() {
 return new TRowRange(TextUtil.getByteBuffer(startRow), TextUtil.getByteBuffer(endRow));
    }


 public Text getStartRow() {
 return startRow;
    }


 public Text getEndRow() {
 return endRow;
    }


 @Override
 public String toString() {
 return startRow + " " + endRow;
    }
  }