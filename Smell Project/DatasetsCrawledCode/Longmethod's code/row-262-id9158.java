 private V1SelfSubjectAccessReview prepareSelfSubjectAccessReview(
 Operation operation,
 Resource resource,
 String resourceName,
 Scope scope,
 String namespaceName) {
 LOGGER.entering();
 V1SelfSubjectAccessReviewSpec subjectAccessReviewSpec = new V1SelfSubjectAccessReviewSpec();


 subjectAccessReviewSpec.setResourceAttributes(
 prepareResourceAttributes(operation, resource, resourceName, scope, namespaceName));


 V1SelfSubjectAccessReview subjectAccessReview = new V1SelfSubjectAccessReview();
 subjectAccessReview.setApiVersion("authorization.k8s.io/v1");
 subjectAccessReview.setKind("SelfSubjectAccessReview");
 subjectAccessReview.setMetadata(new V1ObjectMeta());
 subjectAccessReview.setSpec(subjectAccessReviewSpec);
 LOGGER.exiting(subjectAccessReview);
 return subjectAccessReview;
  }