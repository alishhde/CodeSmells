public interface CassandraSieveRepositoryModule {


 CassandraModule MODULE = CassandraModule.builder()
        .table(CassandraSieveTable.TABLE_NAME)
        .comment("Holds SIEVE scripts.")
        .options(options -> options)
        .statement(statement -> statement
            .addPartitionKey(CassandraSieveTable.USER_NAME, text())
            .addClusteringColumn(CassandraSieveTable.SCRIPT_NAME, text())
            .addColumn(CassandraSieveTable.SCRIPT_CONTENT, text())
            .addColumn(CassandraSieveTable.IS_ACTIVE, cboolean())
            .addColumn(CassandraSieveTable.SIZE, bigint()))
        .table(CassandraSieveSpaceTable.TABLE_NAME)
        .comment("Holds per user current space occupied by SIEVE scripts.")
        .options(options -> options)
        .statement(statement -> statement
            .addPartitionKey(CassandraSieveSpaceTable.USER_NAME, text())
            .addColumn(CassandraSieveSpaceTable.SPACE_USED, counter()))
        .table(CassandraSieveQuotaTable.TABLE_NAME)
        .comment("Holds per user size limitations for SIEVE script storage.")
        .options(options -> options)
        .statement(statement -> statement
            .addPartitionKey(CassandraSieveQuotaTable.USER_NAME, text())
            .addColumn(CassandraSieveQuotaTable.QUOTA, bigint()))
        .table(CassandraSieveClusterQuotaTable.TABLE_NAME)
        .comment("Holds default size limitations for SIEVE script storage.")
        .options(options -> options)
        .statement(statement -> statement
            .addPartitionKey(CassandraSieveClusterQuotaTable.NAME, text())
            .addColumn(CassandraSieveClusterQuotaTable.VALUE, bigint()))
        .table(CassandraSieveActiveTable.TABLE_NAME)
        .comment("Denormalisation table. Allows per user direct active SIEVE script retrieval.")
        .options(options -> options)
        .statement(statement -> statement
            .addPartitionKey(CassandraSieveActiveTable.USER_NAME, text())
            .addColumn(CassandraSieveActiveTable.SCRIPT_NAME, text())
            .addColumn(CassandraSieveActiveTable.DATE, timestamp()))
        .build();


}