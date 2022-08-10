 @Override
 public void processRTTmeasurement(long measuredRTT, Exchange exchange, int retransmissionCount){		
 //System.out.println("Measured an RTT of " + measuredRTT + " after using " + retransmissionCount + " retries." );
 RemoteEndpoint endpoint = getRemoteEndpoint(exchange);
 int rtoType = endpoint.getExchangeEstimatorState(exchange);
 
 // The basic rto algorithm does not care for the blind estimator, set weak/strong to false
 endpoint.setBlindStrong(false);
 endpoint.setBlindWeak(false);
 //Perform normal update of the RTO
 updateEstimator(measuredRTT, rtoType, endpoint);


	}