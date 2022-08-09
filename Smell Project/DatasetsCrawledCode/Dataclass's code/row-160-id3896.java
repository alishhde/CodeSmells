final class JsonObjects {


 private static final Jsonb JSON = JsonbBuilder.create();


 private JsonObjects() {
    }


 static List<Column> getColumns(JsonObject jsonObject) {
 Map<String, Object> map = JSON.fromJson(jsonObject.toString(), Map.class);
 return Columns.of(map);
    }


}