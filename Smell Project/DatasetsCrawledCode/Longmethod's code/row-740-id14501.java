 public List<PropertyType> fromProps(Map<String, Object> m) {
 List<PropertyType> props = new ArrayList<PropertyType>();
 for (Map.Entry<String, Object> entry : m.entrySet()) {
 String key = entry.getKey();
 Object val = entry.getValue();


 PropertyType propEl = new PropertyType();
 propEl.setName(key);
 ObjectFactory factory = new ObjectFactory();
 if (val.getClass().isArray()) {
 ArrayType arrayEl = new ArrayType();
 propEl.getContent().add(factory.createArray(arrayEl));
 for (Object o : normalizeArray(val)) {
 setValueType(propEl, o);
 ValueType valueType =  new ValueType();
 valueType.getContent().add(o.toString());
 arrayEl.getValue().add(valueType);
                }
            } else if (val instanceof List) {
 ArrayType listEl = new ArrayType();
 propEl.getContent().add(factory.createList(listEl));
 handleCollectionValue((Collection<?>) val, propEl, listEl);
            } else if (val instanceof Set) {
 ArrayType setEl = new ArrayType();
 propEl.getContent().add(factory.createSet(setEl));
 handleCollectionValue((Collection<?>) val, propEl, setEl);
            } else if (val instanceof String
                    || val instanceof Character
                    || val instanceof Boolean
                    || val instanceof Byte) {
 setValueType(propEl, val);
 propEl.setValue(val.toString());
            } else if (val instanceof Long
                    || val instanceof Double
                    || val instanceof Float
                    || val instanceof Integer
                    || val instanceof Short) {
 // various numbers..   maybe "val instanceof Number"?
 setValueType(propEl, val);
 propEl.setValue(val.toString());
            } else {
 // Don't add this property as the value type is not supported
 continue;
            }
 props.add(propEl);
        }
 return props;
    }