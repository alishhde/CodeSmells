 public void registerProjectsToFileBasedWorkspace(Iterable<URI> projectURIs, FileBasedWorkspace workspace)
 throws N4JSCompileException {


 // TODO GH-783 refactor FileBasedWorkspace, https://github.com/eclipse/n4js/issues/783
 // this is reverse mapping of the one that is kept in the workspace
 Map<String, URI> registeredProjects = new HashMap<>();
 workspace.getAllProjectLocationsIterator().forEachRemaining(uri -> {
 String projectName = workspace.getProjectDescription(uri).getProjectName();
 registeredProjects.put(projectName, URIUtils.normalize(uri));
		});


 // register all projects with the file based workspace.
 for (URI uri : projectURIs) {
 URI projectURI = URIUtils.normalize(uri);


 final ProjectDescription projectDescription = projectDescriptionLoader
					.loadProjectDescriptionAtLocation(projectURI);


 if (projectDescription == null) {
 throw new N4JSCompileException(
 "Cannot load project description for project at " + projectURI.toFileString()
								+ ". Make sure the project contains a valid package.json file.");
			}


 final String projectName = projectDescription.getProjectName();


 if (skipRegistering(projectName, projectURI, registeredProjects)) {
 if (logger != null && logger.isCreateDebugOutput()) {
 logger.debug("Skipping already registered project '" + projectURI + "'");
				}
 /*
				 * We could call FileBasedWorkspace.registerProject which would fail silently. Still to avoid potential
				 * side effects and to keep {@code registeredProjects} management simpler,we will skip it explicitly.
				 */
 continue;
			}


 try {
 if (logger != null && logger.isCreateDebugOutput()) {
 logger.debug("Registering project '" + projectURI + "'");
				}
 workspace.registerProject(projectURI);
 registeredProjects.put(projectName, projectURI);
			} catch (N4JSBrokenProjectException e) {
 throw new N4JSCompileException("Unable to register project '" + projectURI + "'", e);
			}
		}
	}