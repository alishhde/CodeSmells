 @Test
 public void checkAlternativeConstructor() {


 // Local Declarations
 MasterDetailsPair mDetailsP;
 DataComponent dComponent;
 String MasterType1 = "TypeOne!";
 // Setup DataComponent
 dComponent = new DataComponent();
 dComponent.setName(MasterType1);
 IEntry entry = new StringEntry();
 // Add entry to dComponent
 dComponent.addEntry(entry);


 // Call Alternative Constructor
 mDetailsP = new MasterDetailsPair(MasterType1, dComponent);


 // Check values. Should be typeone and equal to the declared
 // dataComponent
 assertEquals(MasterType1, mDetailsP.getMaster());
 assertTrue(dComponent.equals(mDetailsP.getDetails()));


 // Try to pass null to the constructor - sets values appropriately
 mDetailsP = new MasterDetailsPair(null, dComponent); // null master
 assertNull(mDetailsP.getMaster());
 assertTrue(dComponent.equals(mDetailsP.getDetails()));


 // DataComponent null
 mDetailsP = new MasterDetailsPair(MasterType1, null);
 assertEquals(MasterType1, mDetailsP.getMaster());
 assertNull(mDetailsP.getDetails());


 // Both null
 mDetailsP = new MasterDetailsPair(null, null);
 assertNull(mDetailsP.getMaster());
 assertNull(mDetailsP.getDetails());


	}