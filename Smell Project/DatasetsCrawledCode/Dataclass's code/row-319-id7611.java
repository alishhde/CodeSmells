public interface Region<S, E> {


 /**
	 * Gets the region and state machine unique id.
	 *
	 * @return the region and state machine unique id
	 */
 UUID getUuid();


 /**
	 * Gets the region and state machine id. This identifier
	 * is provided for users disposal and can be set from
	 * a various ways to build a machines.
	 *
	 * @return the region and state machine id
	 */
 String getId();


 /**
	 * Start the region.
	 */
 void start();


 /**
	 * Stop the region.
	 */
 void stop();


 /**
	 * Send an event {@code E} wrapped with a {@link Message} to the region.
	 *
	 * @param event the wrapped event to send
	 * @return true if event was accepted
	 */
 boolean sendEvent(Message<E> event);


 /**
	 * Send an event {@code E} to the region.
	 *
	 * @param event the event to send
	 * @return true if event was accepted
	 */
 boolean sendEvent(E event);


 /**
	 * Gets the current {@link State}.
	 *
	 * @return current state
	 */
 State<S,E> getState();


 /**
	 * Gets the {@link State}s defined in this region. Returned collection is
	 * an unmodifiable copy because states in a state machine are immutable.
	 *
	 * @return immutable copy of states
	 */
 Collection<State<S, E>> getStates();


 /**
	 * Gets a {@link Transition}s for this region.
	 *
	 * @return immutable copy of transitions
	 */
 Collection<Transition<S,E>> getTransitions();


 /**
	 * Checks if region complete. Region is considered to be completed if it has
	 * reached its end state and no further event processing is happening.
	 *
	 * @return true, if complete
	 */
 boolean isComplete();


 /**
	 * Adds the state listener.
	 *
	 * @param listener the listener
	 */
 void addStateListener(StateMachineListener<S, E> listener);


 /**
	 * Removes the state listener.
	 *
	 * @param listener the listener
	 */
 void removeStateListener(StateMachineListener<S, E> listener);


}