 public void set(Object obj, Object value)
 throws IllegalArgumentException, IllegalAccessException
    {
 ensureObj(obj);
 if (isFinal) {
 throwFinalFieldIllegalAccessException(value);
        }
 if (value == null) {
 throwSetIllegalArgumentException(value);
        }
 if (value instanceof Byte) {
 unsafe.putInt(obj, fieldOffset, ((Byte) value).byteValue());
 return;
        }
 if (value instanceof Short) {
 unsafe.putInt(obj, fieldOffset, ((Short) value).shortValue());
 return;
        }
 if (value instanceof Character) {
 unsafe.putInt(obj, fieldOffset, ((Character) value).charValue());
 return;
        }
 if (value instanceof Integer) {
 unsafe.putInt(obj, fieldOffset, ((Integer) value).intValue());
 return;
        }
 throwSetIllegalArgumentException(value);
    }