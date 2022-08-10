 @Override
 public Object getValue(final String columnLabel, final Class<?> type) throws SQLException {
 Object result;
 if (Object.class == type) {
 result = decrypt(columnLabel, resultSet.getObject(columnLabel));
        } else if (boolean.class == type) {
 result = decrypt(columnLabel, resultSet.getBoolean(columnLabel));
        } else if (byte.class == type) {
 result = decrypt(columnLabel, resultSet.getByte(columnLabel));
        } else if (short.class == type) {
 result = decrypt(columnLabel, resultSet.getShort(columnLabel));
        } else if (int.class == type) {
 result = decrypt(columnLabel, resultSet.getInt(columnLabel));
        } else if (long.class == type) {
 result = decrypt(columnLabel, resultSet.getLong(columnLabel));
        } else if (float.class == type) {
 result = decrypt(columnLabel, resultSet.getFloat(columnLabel));
        } else if (double.class == type) {
 result = decrypt(columnLabel, resultSet.getDouble(columnLabel));
        } else if (String.class == type) {
 result = decrypt(columnLabel, resultSet.getString(columnLabel));
        } else if (BigDecimal.class == type) {
 result = decrypt(columnLabel, resultSet.getBigDecimal(columnLabel));
        } else if (byte[].class == type) {
 result = resultSet.getBytes(columnLabel);
        } else if (Date.class == type) {
 result = resultSet.getDate(columnLabel);
        } else if (Time.class == type) {
 result = resultSet.getTime(columnLabel);
        } else if (Timestamp.class == type) {
 result = resultSet.getTimestamp(columnLabel);
        } else if (URL.class == type) {
 result = resultSet.getURL(columnLabel);
        } else if (Blob.class == type) {
 result = resultSet.getBlob(columnLabel);
        } else if (Clob.class == type) {
 result = resultSet.getClob(columnLabel);
        } else if (SQLXML.class == type) {
 result = resultSet.getSQLXML(columnLabel);
        } else if (Reader.class == type) {
 result = resultSet.getCharacterStream(columnLabel);
        } else {
 result = decrypt(columnLabel, resultSet.getObject(columnLabel));
        }
 return result;
    }