 public Step deleteJobAsync(
 String name,
 String namespace,
 V1DeleteOptions deleteOptions,
 ResponseStep<V1Status> responseStep) {
 return createRequestAsync(
 responseStep, new RequestParams("deleteJob", namespace, name, deleteOptions), DELETE_JOB);
  }