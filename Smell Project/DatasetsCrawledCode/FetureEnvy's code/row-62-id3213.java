 @Override
 public void onNodeSelected(TreeNodeElement<D> node, SignalEvent event) {
 getSelectionModel().setTreeActive(true);
 selectNode(node.getData(), event, true);
        }