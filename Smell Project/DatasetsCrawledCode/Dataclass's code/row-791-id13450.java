 public static class Statement extends RoleElt {


 @JsonProperty("Sid")
 public String sid = newSid();


 /**
     * Default effect is Deny; forces callers to switch on Allow.
     */
 @JsonProperty("Effect")
 public Effects effect;


 @JsonProperty("Action")
 public List<String> action = new ArrayList<>(1);


 @JsonProperty("Resource")
 public List<String> resource = new ArrayList<>(1);


 public Statement(final Effects effect) {
 this.effect = effect;
    }


 @Override
 public void validate() {
 requireNonNull(sid, "Sid");
 requireNonNull(effect, "Effect");
 checkState(!(action.isEmpty()), "Empty Action");
 checkState(!(resource.isEmpty()), "Empty Resource");
    }


 public Statement setAllowed(boolean f) {
 effect = effect(f);
 return this;
    }


 public Statement addActions(String... actions) {
 Collections.addAll(action, actions);
 return this;
    }


 public Statement addActions(Collection<String> actions) {
 action.addAll(actions);
 return this;
    }


 public Statement addResources(String... resources) {
 Collections.addAll(resource, resources);
 return this;
    }


 /**
     * Add a list of resources.
     * @param resources resource list
     * @return this statement.
     */
 public Statement addResources(Collection<String> resources) {
 resource.addAll(resources);
 return this;
    }
  }