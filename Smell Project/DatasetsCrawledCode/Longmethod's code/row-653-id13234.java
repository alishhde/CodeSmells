 @Override
 public void execute() throws ResourceUnavailableException, InsufficientCapacityException, ServerApiException, ConcurrentOperationException,
 ResourceAllocationException, NetworkRuleConflictException {


 Map<String, String> dm = new HashMap();


 dm.put(ApiConstants.S3_ACCESS_KEY, getAccessKey());
 dm.put(ApiConstants.S3_SECRET_KEY, getSecretKey());
 dm.put(ApiConstants.S3_END_POINT, getEndPoint());
 dm.put(ApiConstants.S3_BUCKET_NAME, getBucketName());


 if (getSigner() != null && (getSigner().equals(ApiConstants.S3_V3_SIGNER) || getSigner().equals(ApiConstants.S3_V4_SIGNER))) {
 dm.put(ApiConstants.S3_SIGNER, getSigner());
        }
 if (isHttps() != null) {
 dm.put(ApiConstants.S3_HTTPS_FLAG, isHttps().toString());
        }
 if (getConnectionTimeout() != null) {
 dm.put(ApiConstants.S3_CONNECTION_TIMEOUT, getConnectionTimeout().toString());
        }
 if (getMaxErrorRetry() != null) {
 dm.put(ApiConstants.S3_MAX_ERROR_RETRY, getMaxErrorRetry().toString());
        }
 if (getSocketTimeout() != null) {
 dm.put(ApiConstants.S3_SOCKET_TIMEOUT, getSocketTimeout().toString());
        }
 if (getConnectionTtl() != null) {
 dm.put(ApiConstants.S3_CONNECTION_TTL, getConnectionTtl().toString());
        }
 if (getUseTCPKeepAlive() != null) {
 dm.put(ApiConstants.S3_USE_TCP_KEEPALIVE, getUseTCPKeepAlive().toString());
        }


 try{
 ImageStore result = _storageService.discoverImageStore(null, null, "S3", null, dm);
 ImageStoreResponse storeResponse;
 if (result != null) {
 storeResponse = _responseGenerator.createImageStoreResponse(result);
 storeResponse.setResponseName(getCommandName());
 storeResponse.setObjectName("imagestore");
 setResponseObject(storeResponse);
            } else {
 throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Failed to add S3 Image Store.");
            }
        } catch (DiscoveryException ex) {
 s_logger.warn("Exception: ", ex);
 throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, ex.getMessage());
        }
    }