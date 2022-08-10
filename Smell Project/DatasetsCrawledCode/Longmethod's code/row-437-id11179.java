 private void addRelevantPluginDependenciesToClasspath(Set<URL> path) throws MojoExecutionException {
 if (hasCommandlineArgs()) {
 arguments = parseCommandlineArgs();
        }


 try {
 Iterator<Artifact> iter = this.determineRelevantPluginDependencies().iterator();
 while (iter.hasNext()) {
 Artifact classPathElement = iter.next();


 // we must skip org.osgi.core, otherwise we get a
 // java.lang.NoClassDefFoundError: org.osgi.vendor.framework property not set
 if (classPathElement.getArtifactId().equals("org.osgi.core")) {
 if (getLog().isDebugEnabled()) {
 getLog().debug("Skipping org.osgi.core -> " + classPathElement.getGroupId() + "/" + classPathElement.getArtifactId() + "/" + classPathElement.getVersion());
                    }
 continue;
                }


 getLog().debug("Adding plugin dependency artifact: " + classPathElement.getArtifactId()
                                   + " to classpath");
 path.add(classPathElement.getFile().toURI().toURL());
            }
        } catch (MalformedURLException e) {
 throw new MojoExecutionException("Error during setting up classpath", e);
        }


    }