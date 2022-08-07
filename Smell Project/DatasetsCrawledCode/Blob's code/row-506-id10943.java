public interface IAopReferenceModel {
 
 void start();


 void shutdown();


 void removeProject(IJavaProject project);


 void addProject(IJavaProject project, IAopProject aopProject);


 void fireModelChanged();


 List<IAopReference> getAdviceDefinition(IJavaElement je);


 List<IAopReference> getAllReferences();
 
 List<IAopReference> getAllReferencesForResource(IResource resource);


 IAopProject getProject(IJavaProject project);
 
 Collection<IAopProject> getProjects();


 boolean isAdvice(IJavaElement je);


 boolean isAdvised(IJavaElement je);
 
 boolean isAdvised(IBean bean);


 void registerAopModelChangedListener(IAopModelChangedListener listener);


 void unregisterAopModelChangedListener(IAopModelChangedListener listener);
 
 void clearProjects();


}