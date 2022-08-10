 protected void baselineLayout(int targetSpan, int axis, int[] offsets, int[] spans) {
 int totalAscent = (int)(targetSpan * getAlignment(axis));
 int totalDescent = targetSpan - totalAscent;


 int n = getViewCount();


 for (int i = 0; i < n; i++) {
 View v = getView(i);
 float align = v.getAlignment(axis);
 float viewSpan;


 if (v.getResizeWeight(axis) > 0) {
 // if resizable then resize to the best fit


 // the smallest span possible
 float minSpan = v.getMinimumSpan(axis);
 // the largest span possible
 float maxSpan = v.getMaximumSpan(axis);


 if (align == 0.0f) {
 // if the alignment is 0 then we need to fit into the descent
 viewSpan = Math.max(Math.min(maxSpan, totalDescent), minSpan);
                } else if (align == 1.0f) {
 // if the alignment is 1 then we need to fit into the ascent
 viewSpan = Math.max(Math.min(maxSpan, totalAscent), minSpan);
                } else {
 // figure out the span that we must fit into
 float fitSpan = Math.min(totalAscent / align,
 totalDescent / (1.0f - align));
 // fit into the calculated span
 viewSpan = Math.max(Math.min(maxSpan, fitSpan), minSpan);
                }
            } else {
 // otherwise use the preferred spans
 viewSpan = v.getPreferredSpan(axis);
            }


 offsets[i] = totalAscent - (int)(viewSpan * align);
 spans[i] = (int)viewSpan;
        }
    }