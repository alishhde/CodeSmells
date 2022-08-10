 public PullPoint create(String queueName) throws UnableToCreatePullPointFault {
 org.oasis_open.docs.wsn.b_2.CreatePullPoint request
            = new org.oasis_open.docs.wsn.b_2.CreatePullPoint();
 request.getOtherAttributes().put(NotificationBroker.QNAME_PULLPOINT_QUEUE_NAME, queueName);
 CreatePullPointResponse response = createPullPoint.createPullPoint(request);
 return new PullPoint(response.getPullPoint());
    }