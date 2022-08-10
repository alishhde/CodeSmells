 private EntityCollection createETStreamOnComplexProp(Edm edm, OData odata) {
 EntityCollection entityCollection = new EntityCollection();


 Link readLink = new Link();
 readLink.setRel(Constants.NS_MEDIA_READ_LINK_REL);
 readLink.setHref("readLink");
 Entity entity = new Entity();
 entity.addProperty(createPrimitive("PropertyStream", createImage("darkturquoise")));
 readLink.setInlineEntity(entity);
 
 Link readLink1 = new Link();
 readLink1.setRel(Constants.NS_MEDIA_READ_LINK_REL);
 readLink1.setHref("readLink");
 entity = new Entity();
 entity.addProperty(createPrimitive("PropertyEntityStream", createImage("darkturquoise")));
 readLink1.setInlineEntity(entity);
 
 entityCollection.getEntities().add(new Entity()
        .addProperty(createPrimitive("PropertyInt16", Short.MAX_VALUE))
        .addProperty(createPrimitive("PropertyInt32", Integer.MAX_VALUE))
        .addProperty(new Property(null, "PropertyEntityStream", ValueType.PRIMITIVE, readLink1))
        .addProperty(createComplex("PropertyCompWithStream",
 ComplexTypeProvider.nameCTWithStreamProp.getFullQualifiedNameAsString(),
 new Property(null, "PropertyStream", ValueType.PRIMITIVE, readLink),
 createComplex("PropertyComp", 
 ComplexTypeProvider.nameCTTwoPrim.getFullQualifiedNameAsString(),
 createPrimitive("PropertyInt16", (short) 333),
 createPrimitive("PropertyString", "TEST123")))));
 
 Link editLink = new Link();
 editLink.setRel(Constants.NS_MEDIA_EDIT_LINK_REL);
 editLink.setHref("http://mediaserver:1234/editLink");
 editLink.setMediaETag("eTag");
 editLink.setType("image/jpeg");
 entity = new Entity();
 entity.addProperty(createPrimitive("PropertyStream", createImage("royalblue")));
 editLink.setInlineEntity(entity);
 
 Link editLink2 = new Link();
 editLink2.setRel(Constants.NS_MEDIA_EDIT_LINK_REL);
 editLink2.setHref("http://mediaserver:1234/editLink");
 editLink2.setMediaETag("eTag");
 editLink2.setType("image/jpeg");
 entity = new Entity();
 entity.addProperty(createPrimitive("PropertyEntityStream", createImage("royalblue")));
 editLink2.setInlineEntity(entity);


 entityCollection.getEntities().add(new Entity()
        .addProperty(createPrimitive("PropertyInt16", (short) 7))
        .addProperty(createPrimitive("PropertyInt32", (Integer) 10))
        .addProperty(new Property(null, "PropertyEntityStream", ValueType.PRIMITIVE, editLink2))
        .addProperty(createComplex("PropertyCompWithStream",
 ComplexTypeProvider.nameCTWithStreamProp.getFullQualifiedNameAsString(),
 new Property(null, "PropertyStream", ValueType.PRIMITIVE, editLink),
 createComplex("PropertyComp", 
 ComplexTypeProvider.nameCTTwoPrim.getFullQualifiedNameAsString(),
 createPrimitive("PropertyInt16", (short) 333),
 createPrimitive("PropertyString", "TEST123")))));


 setEntityType(entityCollection, edm.getEntityType(EntityTypeProvider.nameETStreamOnComplexProp));
 createEntityId(edm, odata, "ESStreamOnComplexProp", entityCollection);
 createOperations("ESStreamOnComplexProp", entityCollection, EntityTypeProvider.nameETStreamOnComplexProp);
 return entityCollection;
  }