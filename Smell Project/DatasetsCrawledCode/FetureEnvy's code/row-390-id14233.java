 @Override
 public boolean makeAcquisitionUnstealable(final MessageInstanceConsumer<?> consumer)
    {
 EntryState state = _state;
 if(state instanceof StealableConsumerAcquiredState
           && ((StealableConsumerAcquiredState) state).getConsumer() == consumer)
        {
 UnstealableConsumerAcquiredState unstealableState = ((StealableConsumerAcquiredState) state).getUnstealableState();
 boolean updated = _stateUpdater.compareAndSet(this, state, unstealableState);
 if(updated)
            {
 notifyStateChange(state, unstealableState);
            }
 return updated;
        }
 return state instanceof UnstealableConsumerAcquiredState
               && ((UnstealableConsumerAcquiredState) state).getConsumer() == consumer;
    }