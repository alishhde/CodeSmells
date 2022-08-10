 private void addUTF8Region(StructurePointer clazz, String slotName,
 String additionalInfo, AbstractPointer utf8String)
 throws CorruptDataException {
 long offset = utf8String.getAddress() - clazz.getAddress();
 /* We do not want to print UTF8 outside of the ROM class. */
 long clazzSize = ((J9ROMClassPointer) clazz).romSize().longValue();
 if ((offset > 0) && (offset < clazzSize)) {
 if (utf8String.notNull()) {
 long UTF8Length = getUTF8Length(J9UTF8Pointer.cast(utf8String));
 if (utf8String.getAddress() < firstJ9_ROM_UTF8) {
 firstJ9_ROM_UTF8 = utf8String.getAddress();
				}
 if ((utf8String.getAddress() + UTF8Length) > lastJ9_ROM_UTF8) {
 lastJ9_ROM_UTF8 = utf8String.getAddress() + UTF8Length;
				}
 classRegions.add(new J9ClassRegion(utf8String,
 SlotType.J9_ROM_UTF8, slotName, additionalInfo,
 UTF8Length, offset, true));
			}
		}
	}