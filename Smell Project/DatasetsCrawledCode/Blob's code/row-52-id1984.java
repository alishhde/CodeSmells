 private class AssignmentTask implements Runnable {
 final Map<Path,List<KeyExtent>> assignmentFailures;
 HostAndPort location;
 private Map<KeyExtent,List<PathSize>> assignmentsPerTablet;


 public AssignmentTask(Map<Path,List<KeyExtent>> assignmentFailures, String location,
 Map<KeyExtent,List<PathSize>> assignmentsPerTablet) {
 this.assignmentFailures = assignmentFailures;
 this.location = HostAndPort.fromString(location);
 this.assignmentsPerTablet = assignmentsPerTablet;
    }


 private void handleFailures(Collection<KeyExtent> failures, String message) {
 for (KeyExtent ke : failures) {
 List<PathSize> mapFiles = assignmentsPerTablet.get(ke);
 synchronized (assignmentFailures) {
 for (PathSize pathSize : mapFiles) {
 List<KeyExtent> existingFailures = assignmentFailures.get(pathSize.path);
 if (existingFailures == null) {
 existingFailures = new ArrayList<>();
 assignmentFailures.put(pathSize.path, existingFailures);
            }


 existingFailures.add(ke);
          }
        }


 log.info("Could not assign {} map files to tablet {} because : {}.  Will retry ...",
 mapFiles.size(), ke, message);
      }
    }


 @Override
 public void run() {
 HashSet<Path> uniqMapFiles = new HashSet<>();
 for (List<PathSize> mapFiles : assignmentsPerTablet.values())
 for (PathSize ps : mapFiles)
 uniqMapFiles.add(ps.path);


 log.debug("Assigning {} map files to {} tablets at {}", uniqMapFiles.size(),
 assignmentsPerTablet.size(), location);


 try {
 List<KeyExtent> failures = assignMapFiles(context, location, assignmentsPerTablet);
 handleFailures(failures, "Not Serving Tablet");
      } catch (AccumuloException | AccumuloSecurityException e) {
 handleFailures(assignmentsPerTablet.keySet(), e.getMessage());
      }
    }


  }