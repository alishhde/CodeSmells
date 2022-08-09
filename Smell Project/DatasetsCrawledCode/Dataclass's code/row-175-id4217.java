public class OsgiRequirementAdapter implements Requirement {
 private static final Logger logger = LoggerFactory.getLogger(OsgiRequirementAdapter.class);
 
 private final org.osgi.resource.Requirement requirement;
 
 public OsgiRequirementAdapter(org.osgi.resource.Requirement requirement) {
 if (requirement == null)
 throw new NullPointerException("Missing required parameter: requirement");
 this.requirement = requirement;
	}


 public String getComment() {
 return null;
	}


 public String getFilter() {
 return requirement.getDirectives().get(Constants.FILTER_DIRECTIVE);
	}


 public String getName() {
 return NamespaceTranslator.translate(requirement.getNamespace());
	}


 public boolean isExtend() {
 return false;
	}


 public boolean isMultiple() {
 String multiple = requirement.getDirectives().get(Namespace.REQUIREMENT_CARDINALITY_DIRECTIVE);
 return Namespace.CARDINALITY_MULTIPLE.equals(multiple);
	}


 public boolean isOptional() {
 String resolution = requirement.getDirectives().get(Constants.RESOLUTION_DIRECTIVE);
 return Constants.RESOLUTION_OPTIONAL.equals(resolution);
	}


 public boolean isSatisfied(Capability capability) {
 logger.debug(LOG_ENTRY, "isSatisfied", capability);
 boolean result = ResourceHelper.matches(requirement, new FelixCapabilityAdapter(capability, null));
 logger.debug(LOG_EXIT, "isSatisfied", result);
 return result;
	}


}