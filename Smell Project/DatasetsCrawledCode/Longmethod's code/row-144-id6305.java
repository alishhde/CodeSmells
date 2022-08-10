 private boolean configureHA(final Long resourceId, final HAResource.ResourceType resourceType, final Boolean enable, final String haProvider) {
 return Transaction.execute(new TransactionCallback<Boolean>() {
 @Override
 public Boolean doInTransaction(TransactionStatus status) {
 HAConfigVO haConfig = (HAConfigVO) haConfigDao.findHAResource(resourceId, resourceType);
 if (haConfig == null) {
 haConfig = new HAConfigVO();
 if (haProvider != null) {
 haConfig.setHaProvider(haProvider);
                    }
 if (enable != null) {
 haConfig.setEnabled(enable);
 haConfig.setManagementServerId(ManagementServerNode.getManagementServerId());
                    }
 haConfig.setResourceId(resourceId);
 haConfig.setResourceType(resourceType);
 if (Strings.isNullOrEmpty(haConfig.getHaProvider())) {
 throw new ServerApiException(ApiErrorCode.PARAM_ERROR, "HAProvider is not provided for the resource, failing configuration.");
                    }
 if (haConfigDao.persist(haConfig) != null) {
 return true;
                    }
                } else {
 if (enable != null) {
 haConfig.setEnabled(enable);
                    }
 if (haProvider != null) {
 haConfig.setHaProvider(haProvider);
                    }
 if (Strings.isNullOrEmpty(haConfig.getHaProvider())) {
 throw new ServerApiException(ApiErrorCode.PARAM_ERROR, "HAProvider is not provided for the resource, failing configuration.");
                    }
 return haConfigDao.update(haConfig.getId(), haConfig);
                }
 return false;
            }
        });
    }