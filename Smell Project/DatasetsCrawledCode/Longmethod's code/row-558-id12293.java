 @Override
 public boolean isExists(Object identifier) throws AppCatalogException {
 HashMap<String, String> ids;
 if (identifier instanceof Map) {
 ids = (HashMap) identifier;
        } else {
 logger.error("Identifier should be a map with the field name and it's value");
 throw new AppCatalogException("Identifier should be a map with the field name and it's value");
        }


 EntityManager em = null;
 try {
 em = AppCatalogJPAUtils.getEntityManager();
 ComputeResourcePreference existingPreference = em.find(ComputeResourcePreference.class,
 new ComputeResourcePreferencePK(ids.get(ComputeResourcePreferenceConstants.GATEWAY_ID),
 ids.get(ComputeResourcePreferenceConstants.RESOURCE_ID)));
 if (em.isOpen()) {
 if (em.getTransaction().isActive()){
 em.getTransaction().rollback();
                }
 em.close();
            }
 return existingPreference != null;
        }catch (Exception e) {
 logger.error(e.getMessage(), e);
 throw new AppCatalogException(e);
        } finally {
 if (em != null && em.isOpen()) {
 if (em.getTransaction().isActive()) {
 em.getTransaction().rollback();
                }
 em.close();
            }
        }
    }