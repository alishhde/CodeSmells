 private void addOutputContainerData() {
 @SuppressWarnings("resource")
 final VarCharVector fragmentIdVector = (VarCharVector) container.getValueAccessorById(
 VarCharVector.class,
 container.getValueVectorId(SchemaPath.getSimplePath("Fragment")).getFieldIds())
      .getValueVector();
 AllocationHelper.allocate(fragmentIdVector, 1, 50);
 @SuppressWarnings("resource")
 final BigIntVector summaryVector = (BigIntVector) container.getValueAccessorById(BigIntVector.class,
 container.getValueVectorId(SchemaPath.getSimplePath("Number of records written")).getFieldIds())
          .getValueVector();
 AllocationHelper.allocate(summaryVector, 1, 8);
 fragmentIdVector.getMutator().setSafe(0, fragmentUniqueId.getBytes());
 fragmentIdVector.getMutator().setValueCount(1);
 summaryVector.getMutator().setSafe(0, counter);
 summaryVector.getMutator().setValueCount(1);


 container.setRecordCount(1);
  }