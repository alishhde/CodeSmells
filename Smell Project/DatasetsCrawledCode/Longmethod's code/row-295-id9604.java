 @Transactional(propagation = Propagation.MANDATORY)
 public Map<Long, List<BiologicalDataItem>> loadBookmarkItemsByBookmarkIds(Collection<Long> bookmarkIds) {
 if (bookmarkIds == null || bookmarkIds.isEmpty()) {
 return Collections.emptyMap();
        }


 Long listId = daoHelper.createTempLongList(bookmarkIds);
 Map<Long, List<BiologicalDataItem>> itemsMap = new HashMap<>();


 getJdbcTemplate().query(loadBookmarksItemsQuery, rs -> {
 BiologicalDataItem dataItem = BiologicalDataItemDao.BiologicalDataItemParameters.getRowMapper()
                .mapRow(rs, 0);
 long bookmarkId = rs.getLong(BookmarkItemParameters.BOOKMARK_ID.name());
 if (!itemsMap.containsKey(bookmarkId)) {
 itemsMap.put(bookmarkId, new ArrayList<>());
            }
 itemsMap.get(bookmarkId).add(dataItem);
        }, listId);


 daoHelper.clearTempList(listId);
 return itemsMap;
    }