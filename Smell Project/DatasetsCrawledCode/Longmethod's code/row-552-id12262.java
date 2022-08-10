 private void parseArray(NameSegment nameSeg) {
 String name = nameSeg.getPath();
 ArraySegment arraySeg = ((ArraySegment) nameSeg.getChild());
 int index = arraySeg.getIndex();
 RequestedColumnImpl member = getImpl(name);
 if (member == null) {
 member = new RequestedColumnImpl(this, name);
 projection.add(name, member);
    } else if (member.isSimple()) {


 // Saw both a and a[x]. Occurs in project list.
 // Project all elements.


 member.projectAllElements();
 return;
    } else if (member.hasIndex(index)) {
 throw UserException
        .validationError()
        .message("Duplicate array index in project list: %s[%d]",
 member.fullName(), index)
        .build(logger);
    }
 member.addIndex(index);


 // Drills SQL parser does not support map arrays: a[0].c
 // But, the SchemaPath does support them, so no harm in
 // parsing them here.


 if (! arraySeg.isLastPath()) {
 parseInternal(nameSeg);
    }
  }