 public void onKapuaEvent(ServiceEvent kapuaEvent) throws KapuaException {
 if (kapuaEvent == null) {
 //service bus error. Throw some exception?
        }


 LOG.info("GroupService: received kapua event from {}, operation {}", kapuaEvent.getService(), kapuaEvent.getOperation());
 if ("account".equals(kapuaEvent.getService()) && "delete".equals(kapuaEvent.getOperation())) {
 deleteGroupByAccountId(kapuaEvent.getScopeId(), kapuaEvent.getEntityId());
        }
    }