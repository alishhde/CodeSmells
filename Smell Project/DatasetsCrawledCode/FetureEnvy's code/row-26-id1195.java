 @Override
 public boolean visitObjectReference(final Pointer objRef, boolean compressed) {
 return visitObjectReferenceInline(objRef, 0, compressed);
    }