 protected static void initialize()
    {
 STRAM.setChildren(Sets.newHashSet(APPLICATION, TEMPLATE));
 APPLICATION.setChildren(Sets.newHashSet(GATEWAY, OPERATOR, STREAM));
 OPERATOR.setChildren(Sets.newHashSet(PORT));
 PORT.setChildren(Sets.newHashSet(UNIFIER));


 STRAM_ELEMENT_TO_CONF_ELEMENT.clear();


 //Initialize StramElement to ConfElement
 for (ConfElement confElement: ConfElement.values()) {
 STRAM_ELEMENT_TO_CONF_ELEMENT.put(confElement.getStramElement(), confElement);


 for (StramElement sElement: confElement.getAllRelatedElements()) {
 STRAM_ELEMENT_TO_CONF_ELEMENT.put(sElement, confElement);
        }
      }


 //Initialize attributes
 for (ConfElement confElement: ConfElement.values()) {
 if (confElement.getParent() == null) {
 continue;
        }


 setAmbiguousAttributes(confElement);
      }


 // build context to conf element map
 CONTEXT_TO_CONF_ELEMENT.clear();


 for (ConfElement confElement: ConfElement.values()) {
 CONTEXT_TO_CONF_ELEMENT.put(confElement.getContextClass(), confElement);
      }


 //Check if all the context classes are accounted for
 Set<Class<? extends Context>> confElementContextClasses = Sets.newHashSet();


 for (ConfElement confElement: ConfElement.values()) {
 if (confElement.getContextClass() == null) {
 continue;
        }


 confElementContextClasses.add(confElement.getContextClass());
      }


 if (!ContextUtils.CONTEXT_CLASSES.equals(confElementContextClasses)) {
 throw new IllegalStateException("All the context classes " + ContextUtils.CONTEXT_CLASSES + " found in "
                                        + Context.class + " are not used by ConfElements " + confElementContextClasses);
      }
    }