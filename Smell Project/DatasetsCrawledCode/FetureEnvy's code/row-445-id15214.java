 @Override
 protected Endpoint createEndpoint(final String uri, final String remaining,
 final Map<String, Object> parameters) throws Exception {


 final int concurrentConsumers = getAndRemoveParameter(parameters, "concurrentConsumers", Integer.class, defaultConcurrentConsumers);
 final boolean limitConcurrentConsumers = getAndRemoveParameter(parameters, "limitConcurrentConsumers", Boolean.class, true);


 if (limitConcurrentConsumers && concurrentConsumers > MAX_CONCURRENT_CONSUMERS) {
 throw new IllegalArgumentException(
 "The limitConcurrentConsumers flag in set to true. ConcurrentConsumers cannot be set at a value greater than "
                            + MAX_CONCURRENT_CONSUMERS + " was " + concurrentConsumers);
        }


 if (concurrentConsumers < 0) {
 throw new IllegalArgumentException("concurrentConsumers found to be " + concurrentConsumers 
                                               + ", must be greater than 0");
        }


 int size = 0;
 if (parameters.containsKey("size")) {
 size = getAndRemoveParameter(parameters, "size", int.class);
 if (size <= 0) {
 throw new IllegalArgumentException("size found to be " + size + ", must be greater than 0");
            }
        }


 // Check if the pollTimeout argument is set (may be the case if Disruptor component is used as drop-in
 // replacement for the SEDA component.
 if (parameters.containsKey("pollTimeout")) {
 throw new IllegalArgumentException("The 'pollTimeout' argument is not supported by the Disruptor component");
        }


 final DisruptorWaitStrategy waitStrategy = getAndRemoveParameter(parameters, "waitStrategy", DisruptorWaitStrategy.class, defaultWaitStrategy);
 final DisruptorProducerType producerType = getAndRemoveParameter(parameters, "producerType", DisruptorProducerType.class, defaultProducerType);
 final boolean multipleConsumers = getAndRemoveParameter(parameters, "multipleConsumers", boolean.class, defaultMultipleConsumers);
 final boolean blockWhenFull = getAndRemoveParameter(parameters, "blockWhenFull", boolean.class, defaultBlockWhenFull);


 final DisruptorReference disruptorReference = getOrCreateDisruptor(uri, remaining, size, producerType, waitStrategy);
 final DisruptorEndpoint disruptorEndpoint = new DisruptorEndpoint(uri, this, disruptorReference, concurrentConsumers, multipleConsumers, blockWhenFull);
 disruptorEndpoint.setWaitStrategy(waitStrategy);
 disruptorEndpoint.setProducerType(producerType);
 disruptorEndpoint.configureProperties(parameters);


 return disruptorEndpoint;
    }