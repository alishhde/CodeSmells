 private void addDataAccessNodes(UaFolderNode rootNode) {
 // DataAccess folder
 UaFolderNode dataAccessFolder = new UaFolderNode(
 getNodeContext(),
 newNodeId("HelloWorld/DataAccess"),
 newQualifiedName("DataAccess"),
 LocalizedText.english("DataAccess")
        );


 getNodeManager().addNode(dataAccessFolder);
 rootNode.addOrganizes(dataAccessFolder);


 // AnalogItemType node
 try {
 AnalogItemNode node = (AnalogItemNode) getNodeFactory().createNode(
 newNodeId("HelloWorld/DataAccess/AnalogValue"),
 Identifiers.AnalogItemType,
 true
            );


 node.setBrowseName(newQualifiedName("AnalogValue"));
 node.setDisplayName(LocalizedText.english("AnalogValue"));
 node.setDataType(Identifiers.Double);
 node.setValue(new DataValue(new Variant(3.14d)));


 node.setEURange(new Range(0.0, 100.0));


 getNodeManager().addNode(node);
 dataAccessFolder.addOrganizes(node);
        } catch (UaException e) {
 logger.error("Error creating AnalogItemType instance: {}", e.getMessage(), e);
        }
    }