 private List<FlowSnapshotMetadata> createFlowSnapshotMetdata(final Flow flow) {
 final List<FlowSnapshotMetadata> flowSnapshotMetadataList = new ArrayList<>();


 final Map<Integer, Flow.FlowPointer> versions = flow.getVersions();
 for (Map.Entry<Integer, Flow.FlowPointer> entry : versions.entrySet()) {
 final Integer version = entry.getKey();
 final Flow.FlowPointer flowPointer = entry.getValue();


 final FlowSnapshotMetadata snapshotMetadata = new FlowSnapshotMetadata();
 snapshotMetadata.setVersion(version);
 snapshotMetadata.setAuthor(flowPointer.getAuthor());
 snapshotMetadata.setComments(flowPointer.getComment());
 snapshotMetadata.setCreated(flowPointer.getCreated());
 flowSnapshotMetadataList.add(snapshotMetadata);
        }


 return flowSnapshotMetadataList;
    }