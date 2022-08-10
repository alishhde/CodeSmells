 public Clause getClause(Resource resource) {
 String symbolicName = ResourceHelper.getSymbolicNameAttribute(resource);
 Version version = ResourceHelper.getVersionAttribute(resource);
 String type = ResourceHelper.getTypeAttribute(resource);
 for (Clause clause : clauses) {
 if (symbolicName.equals(clause.getPath())
					&& clause.getDeployedVersion().equals(version)
					&& type.equals(clause.getType()))
 return clause;
		}
 return null;
	}