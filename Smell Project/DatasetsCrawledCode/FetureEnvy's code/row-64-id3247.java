 public static void setResultSortOrder(ResultSortOrder resultSortOrder, HttpSession session) {
 ContentSearchOptions contentSearchOptions = getContentSearchOptions(session);
 contentSearchOptions.resultSortOrder = resultSortOrder;
 contentSearchOptions.changed = true;
        }