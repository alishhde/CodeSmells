 public void validateTwoSegments(final IndexableAdapter adapter1, final IndexableAdapter adapter2)
  {
 if (adapter1.getNumRows() != adapter2.getNumRows()) {
 throw new SegmentValidationException(
 "Row count mismatch. Expected [%d] found [%d]",
 adapter1.getNumRows(),
 adapter2.getNumRows()
      );
    }
    {
 final Set<String> dimNames1 = Sets.newHashSet(adapter1.getDimensionNames());
 final Set<String> dimNames2 = Sets.newHashSet(adapter2.getDimensionNames());
 if (!dimNames1.equals(dimNames2)) {
 throw new SegmentValidationException(
 "Dimension names differ. Expected [%s] found [%s]",
 dimNames1,
 dimNames2
        );
      }
 final Set<String> metNames1 = Sets.newHashSet(adapter1.getMetricNames());
 final Set<String> metNames2 = Sets.newHashSet(adapter2.getMetricNames());
 if (!metNames1.equals(metNames2)) {
 throw new SegmentValidationException("Metric names differ. Expected [%s] found [%s]", metNames1, metNames2);
      }
    }
 final RowIterator it1 = adapter1.getRows();
 final RowIterator it2 = adapter2.getRows();
 long row = 0L;
 while (it1.moveToNext()) {
 if (!it2.moveToNext()) {
 throw new SegmentValidationException("Unexpected end of second adapter");
      }
 final RowPointer rp1 = it1.getPointer();
 final RowPointer rp2 = it2.getPointer();
      ++row;
 if (rp1.getRowNum() != rp2.getRowNum()) {
 throw new SegmentValidationException("Row number mismatch: [%d] vs [%d]", rp1.getRowNum(), rp2.getRowNum());
      }
 try {
 validateRowValues(rp1, adapter1, rp2, adapter2);
      }
 catch (SegmentValidationException ex) {
 throw new SegmentValidationException(ex, "Validation failure on row %d: [%s] vs [%s]", row, rp1, rp2);
      }
    }
 if (it2.moveToNext()) {
 throw new SegmentValidationException("Unexpected end of first adapter");
    }
 if (row != adapter1.getNumRows()) {
 throw new SegmentValidationException(
 "Actual Row count mismatch. Expected [%d] found [%d]",
 row,
 adapter1.getNumRows()
      );
    }
  }