 @Test
 public void testGroupByOrderPreservingDescSort() throws Exception {
 Properties props = PropertiesUtil.deepCopy(TEST_PROPERTIES);
 Connection conn = DriverManager.getConnection(getUrl(), props);
 String tableName = generateUniqueName();
 conn.createStatement().execute("CREATE TABLE " + tableName + " (k1 char(1) not null, k2 char(1) not null," +
 " constraint pk primary key (k1,k2)) split on ('ac','jc','nc')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('a', 'a')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('a', 'b')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('a', 'c')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('a', 'd')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('j', 'a')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('j', 'b')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('j', 'c')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('j', 'd')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('n', 'a')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('n', 'b')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('n', 'c')");
 conn.createStatement().execute("UPSERT INTO " + tableName + " VALUES('n', 'd')");
 conn.commit();
 QueryBuilder queryBuilder = new QueryBuilder()
            .setSelectExpression("K1,COUNT(*)")
            .setSelectColumns(Lists.newArrayList("K1"))
            .setFullTableName(tableName)
            .setGroupByClause("K1")
            .setOrderByClause("K1 DESC");
 ResultSet rs = executeQuery(conn, queryBuilder);
 assertTrue(rs.next());
 assertEquals("n", rs.getString(1));
 assertEquals(4, rs.getLong(2));
 assertTrue(rs.next());
 assertEquals("j", rs.getString(1));
 assertEquals(4, rs.getLong(2));
 assertTrue(rs.next());
 assertEquals("a", rs.getString(1));
 assertEquals(4, rs.getLong(2));
 assertFalse(rs.next());
 String expectedPhoenixPlan = "CLIENT PARALLEL 1-WAY REVERSE FULL SCAN OVER " + tableName + "\n" +
 "    SERVER FILTER BY FIRST KEY ONLY\n" +
 "    SERVER AGGREGATE INTO ORDERED DISTINCT ROWS BY [K1]";
 validateQueryPlan(conn, queryBuilder, expectedPhoenixPlan, null);
    }