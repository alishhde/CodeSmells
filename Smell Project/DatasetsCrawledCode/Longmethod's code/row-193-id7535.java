 public LongRect getSelectionBounds(ItemSelection selection, ChartContext context) {


 XYItemSelection sel = (XYItemSelection)selection;
 XYItem item  = sel.getItem();
 int selectedValueIndex = sel.getValueIndex();


 if (selectedValueIndex == -1 ||
 selectedValueIndex >= item.getValuesCount())
 // This happens on reset - bounds of the selection are unknown, let's clear whole area
 return new LongRect(0, 0, context.getViewportWidth(),
 context.getViewportHeight());
 else
 return getViewBounds(item, selectedValueIndex, context);
    }