 public float computeTableUnit(PercentBaseContext percentBaseContext, int contentAreaIPD) {


 int sumCols = 0;
 float factors = 0;
 float unit = 0;


 /* calculate the total width (specified absolute/percentages),
         * and work out the total number of factors to use to distribute
         * the remaining space (if any)
         */
 for (Object colWidth1 : colWidths) {
 Length colWidth = (Length) colWidth1;
 if (colWidth != null) {
 sumCols += colWidth.getValue(percentBaseContext);
 if (colWidth instanceof RelativeNumericProperty) {
 factors += ((RelativeNumericProperty) colWidth).getTableUnits();
                } else if (colWidth instanceof TableColLength) {
 factors += ((TableColLength) colWidth).getTableUnits();
                }
            }
        }


 /* distribute the remaining space over the accumulated
         * factors (if any)
         */
 if (factors > 0) {
 if (sumCols < contentAreaIPD) {
 unit = (contentAreaIPD - sumCols) / factors;
            } else {
 log.warn("No space remaining to distribute over columns.");
            }
        }


 return unit;
    }