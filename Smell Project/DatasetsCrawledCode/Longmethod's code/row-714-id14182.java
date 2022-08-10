 @Override
 protected void writeTransactionResponse(ResponseCode response, String explanation) throws IOException {
 HttpCommunicationsSession commSession = (HttpCommunicationsSession) peer.getCommunicationsSession();
 if(TransferDirection.RECEIVE.equals(direction)){
 switch (response) {
 case CONFIRM_TRANSACTION:
 logger.debug("{} Confirming transaction. checksum={}", this, explanation);
 commSession.setChecksum(explanation);
 break;
 case TRANSACTION_FINISHED:
 logger.debug("{} Finishing transaction.", this);
 break;
 case CANCEL_TRANSACTION:
 logger.debug("{} Canceling transaction. explanation={}", this, explanation);
 TransactionResultEntity resultEntity = apiClient.commitReceivingFlowFiles(transactionUrl, ResponseCode.CANCEL_TRANSACTION, null);
 ResponseCode cancelResponse = ResponseCode.fromCode(resultEntity.getResponseCode());
 switch (cancelResponse) {
 case CANCEL_TRANSACTION:
 logger.debug("{} CANCEL_TRANSACTION, The transaction is canceled on server properly.", this);
 break;
 default:
 logger.warn("{} CANCEL_TRANSACTION, Expected the transaction is canceled on server, but received {}.", this, cancelResponse);
 break;
                    }
 break;
            }
        } else {
 switch (response) {
 case FINISH_TRANSACTION:
 // The actual HTTP request will be sent in readTransactionResponse.
 logger.debug("{} Finished sending flow files.", this);
 break;
 case BAD_CHECKSUM: {
 TransactionResultEntity resultEntity = apiClient.commitTransferFlowFiles(transactionUrl, ResponseCode.BAD_CHECKSUM);
 ResponseCode badChecksumCancelResponse = ResponseCode.fromCode(resultEntity.getResponseCode());
 switch (badChecksumCancelResponse) {
 case CANCEL_TRANSACTION:
 logger.debug("{} BAD_CHECKSUM, The transaction is canceled on server properly.", this);
 break;
 default:
 logger.warn("{} BAD_CHECKSUM, Expected the transaction is canceled on server, but received {}.", this, badChecksumCancelResponse);
 break;
                        }


                    }
 break;
 case CONFIRM_TRANSACTION:
 // The actual HTTP request will be sent in readTransactionResponse.
 logger.debug("{} Transaction is confirmed.", this);
 break;
 case CANCEL_TRANSACTION: {
 logger.debug("{} Canceling transaction.", this);
 TransactionResultEntity resultEntity = apiClient.commitTransferFlowFiles(transactionUrl, ResponseCode.CANCEL_TRANSACTION);
 ResponseCode cancelResponse = ResponseCode.fromCode(resultEntity.getResponseCode());
 switch (cancelResponse) {
 case CANCEL_TRANSACTION:
 logger.debug("{} CANCEL_TRANSACTION, The transaction is canceled on server properly.", this);
 break;
 default:
 logger.warn("{} CANCEL_TRANSACTION, Expected the transaction is canceled on server, but received {}.", this, cancelResponse);
 break;
                        }
                    }
 break;
            }
        }
    }