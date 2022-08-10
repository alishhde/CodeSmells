 @Override
 public void updateExperiment(String airavataExperimentId, ExperimentModel experiment) throws RegistryServiceException, TException {
 try {
 experimentCatalog = RegistryFactory.getDefaultExpCatalog();
 if (!experimentCatalog.isExist(ExperimentCatalogModelType.EXPERIMENT, airavataExperimentId)) {
 logger.error(airavataExperimentId, "Update request failed, Experiment {} doesn't exist.", airavataExperimentId);
 throw new RegistryServiceException("Requested experiment id " + airavataExperimentId + " does not exist in the system..");
            }


 ExperimentStatus experimentStatus = getExperimentStatusInternal(airavataExperimentId);
 if (experimentStatus != null){
 ExperimentState experimentState = experimentStatus.getState();
 switch (experimentState){
 case CREATED: case VALIDATED:
 if(experiment.getUserConfigurationData() != null && experiment.getUserConfigurationData()
                                .getComputationalResourceScheduling() != null){
 String compResourceId = experiment.getUserConfigurationData()
                                    .getComputationalResourceScheduling().getResourceHostId();
 ComputeResourceDescription computeResourceDescription = appCatalog.getComputeResource()
                                    .getComputeResource(compResourceId);
 if(!computeResourceDescription.isEnabled()){
 logger.error("Compute Resource is not enabled by the Admin!");
 AiravataSystemException exception = new AiravataSystemException();
 exception.setAiravataErrorType(AiravataErrorType.INTERNAL_ERROR);
 exception.setMessage("Compute Resource is not enabled by the Admin!");
 throw exception;
                            }
                        }
 experimentCatalog.update(ExperimentCatalogModelType.EXPERIMENT, experiment, airavataExperimentId);
 logger.debug(airavataExperimentId, "Successfully updated experiment {} ", experiment.getExperimentName());
 break;
 default:
 logger.error(airavataExperimentId, "Error while updating experiment. Update experiment is only valid for experiments " +
 "with status CREATED, VALIDATED, CANCELLED, FAILED and UNKNOWN. Make sure the given " +
 "experiment is in one of above statuses... ");
 AiravataSystemException exception = new AiravataSystemException();
 exception.setAiravataErrorType(AiravataErrorType.INTERNAL_ERROR);
 exception.setMessage("Error while updating experiment. Update experiment is only valid for experiments " +
 "with status CREATED, VALIDATED, CANCELLED, FAILED and UNKNOWN. Make sure the given " +
 "experiment is in one of above statuses... ");
 throw exception;
                }
            }
        } catch (RegistryException e) {
 logger.error(airavataExperimentId, "Error while updating experiment", e);
 RegistryServiceException exception = new RegistryServiceException();
 exception.setMessage("Error while updating experiment. More info : " + e.getMessage());
 throw exception;
        } catch (AppCatalogException e) {
 logger.error(airavataExperimentId, "Error while updating experiment", e);
 RegistryServiceException exception = new RegistryServiceException();
 exception.setMessage("Error while updating experiment. More info : " + e.getMessage());
 throw exception;
        }
    }