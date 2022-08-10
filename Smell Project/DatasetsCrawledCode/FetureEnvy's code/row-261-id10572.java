 private static void handleMarkerAddition(final IMarker marker, final String type) {
 final IResource resource = marker.getResource();
 final int lineNumber = marker.getAttribute(IMarker.LINE_NUMBER, -1);
 final int offset = marker.getAttribute(IMarker.CHAR_START, -1);
 final int endoffset = marker.getAttribute(IMarker.CHAR_END, -1);
 final long markerId = marker.getId();


 Map<IResource, List<InternalMarker>> typeSpecificMarkers;


 synchronized (MARKERS) {
 if (MARKERS.containsKey(type)) {
 typeSpecificMarkers = MARKERS.get(type);
			} else {
 typeSpecificMarkers = new HashMap<IResource, List<InternalMarker>>();
 MARKERS.put(type, typeSpecificMarkers);
			}


 List<InternalMarker> fileSpecificMarkers;
 if (typeSpecificMarkers.containsKey(resource)) {
 fileSpecificMarkers = typeSpecificMarkers.get(resource);
			} else {
 fileSpecificMarkers = new ArrayList<InternalMarker>();
 typeSpecificMarkers.put(resource, fileSpecificMarkers);
			}


 boolean found = false;
 for (int i = 0; !found && i < fileSpecificMarkers.size(); i++) {
 if (fileSpecificMarkers.get(i).markerID == markerId) {
 found = true;
				}
			}


 if (!found) {
 final InternalMarker temp = new InternalMarker();
 temp.row = lineNumber;
 temp.offset = offset;
 temp.endoffset = endoffset;
 temp.markerID = markerId;
 fileSpecificMarkers.add(temp);
			}


 markUsed(type, resource, markerId);
		}
	}