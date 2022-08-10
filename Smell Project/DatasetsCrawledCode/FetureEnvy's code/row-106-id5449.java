 private QueryBuilder convertCustomFlagCriterion(SearchQuery.CustomFlagCriterion criterion) {
 QueryBuilder termQueryBuilder = termQuery(JsonMessageConstants.USER_FLAGS, criterion.getFlag());
 if (criterion.getOperator().isSet()) {
 return termQueryBuilder;
        } else {
 return boolQuery().mustNot(termQueryBuilder);
        }
    }