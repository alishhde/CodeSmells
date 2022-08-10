 private boolean isChildOfOrEqualToAnyFolder(IResource resource) {
 for (int i= 0; i < fFolders.length; i++) {
 IFolder folder= fFolders[i];
 if (folder.equals(resource) || ParentChecker.isDescendantOf(resource, folder)) {
 return true;
				}
			}
 return false;
		}