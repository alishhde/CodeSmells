 @Override
 public BatchResponsePart executeChangeSet(final BatchHandler handler, final List<ODataRequest> requests)
 throws ODataException {
 List<ODataResponse> responses = new ArrayList<ODataResponse>();
 try {
 oDataJPAContext.getODataJPATransaction().begin();


 for (ODataRequest request : requests) {
 oDataJPAContext.setODataContext(getContext());
 ODataResponse response = handler.handleRequest(request);
 if (response.getStatus().getStatusCode() >= HttpStatusCodes.BAD_REQUEST.getStatusCode()) {
 // Rollback
 oDataJPAContext.getODataJPATransaction().rollback();
 List<ODataResponse> errorResponses = new ArrayList<ODataResponse>(1);
 errorResponses.add(response);
 return BatchResponsePart.responses(errorResponses).changeSet(false).build();
        }
 responses.add(response);
      }
 oDataJPAContext.getODataJPATransaction().commit();


 return BatchResponsePart.responses(responses).changeSet(true).build();
    } catch (Exception e) {
 throw new ODataException("Error on processing request content:" + e.getMessage(), e);
    } finally {
 close(true);
    }
  }