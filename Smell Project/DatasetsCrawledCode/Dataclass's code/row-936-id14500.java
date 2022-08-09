 static class JobDefinitionLocator extends JobSpecificationVisitor<Object> {


 List<JobDefinition> jobDefinitions = new ArrayList<JobDefinition>();


 public List<JobDefinition> getJobDefinitions() {
 return jobDefinitions;
		}


 @Override
 public Object walk(Object context, Flow sjs) {
 for (JobNode jobNode : sjs.getSeries()) {
 walk(context, jobNode);
			}
 return context;
		}


 @Override
 public Object walk(Object context, JobDefinition jd) {
 jobDefinitions.add(jd);
 return context;
		}


 @Override
 public Object walk(Object context, JobReference jr) {
 return context;
		}


 @Override
 public Object walk(Object context, Split pjs) {
 for (JobNode jobNode : pjs.getSeries()) {
 walk(context, jobNode);
			}
 return context;
		}


	}