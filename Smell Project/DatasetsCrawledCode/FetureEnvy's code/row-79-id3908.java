 public boolean isVisible(final IStructuredSelection selection) {
 final ChangeItem[] changes = (ChangeItem[]) SelectionUtils.selectionToArray(getSelection(), ChangeItem.class);


 // Enable for any delete
 for (final ChangeItem change : changes) {
 if (change.getChangeType().contains(ChangeType.DELETE)) {
 return true;
            }
        }


 return false;
    }