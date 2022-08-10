 protected Context getContinuationContext(Name n) throws NamingException {
 Object obj = lookup(n.get(0));
 CannotProceedException cpe = new CannotProceedException();
 cpe.setResolvedObj(obj);
 cpe.setEnvironment(myEnv);
 return NamingManager.getContinuationContext(cpe);
    }