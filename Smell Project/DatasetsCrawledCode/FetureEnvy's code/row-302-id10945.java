 private Collection<MavenProject> getAggregatedProjects()
    {
 Map<Path, MavenProject> reactorProjectsMap = new HashMap<>();
 for ( MavenProject reactorProject : this.reactorProjects )
        {
 reactorProjectsMap.put( reactorProject.getBasedir().toPath(), reactorProject );
        }


 return modulesForAggregatedProject( project, reactorProjectsMap );
    }