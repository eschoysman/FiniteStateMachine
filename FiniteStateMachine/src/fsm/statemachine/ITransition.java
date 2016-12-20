package fsm.statemachine;

import fsm.statemachine.exceptions.StateMachineException;
import fsm.statemachine.exceptions.UnexpectedTransitionException;
import fsm.statemachine.impl.TRANSITION_ERROR_HANDLING;

public interface ITransition<C extends Context,E> {

	public ITransition<C, E> setEnterAction(StateMachineFunction<C, E> enterAction);
	public ITransition<C, E> setExecuteAction(StateMachineFunction<C, E> executeAction);
	public ITransition<C, E> setExitAction(StateMachineFunction<C, E> exitAction);
	public default ITransition<C, E> setActions(StateMachineFunction<C, E> enterAction, StateMachineFunction<C, E> executeAction, StateMachineFunction<C, E> exitAction) {
		return setEnterAction(enterAction).setExecuteAction(executeAction).setExitAction(exitAction);
	}
	public default void fire(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws UnexpectedTransitionException {
		try {
			from.exit(from, event, to, context);
			this.enter(from,event,to,context);
			this.run(from,event,to,context);
			this.exit(from,event,to,context);
			to.enter(from,event,to,context);
		} catch(Exception e) {
			throw new UnexpectedTransitionException(e);
		}
	}
	public void enter(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException;
	public void run(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException;
	public void exit(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException;
	public int getId();
	public IState<C, E> getFrom();
	public Event<E> getEvent();
	public IState<C, E> getDestination();
	public TRANSITION_ERROR_HANDLING getHandler();
	public ITransition<C, E> setHandler(TRANSITION_ERROR_HANDLING handler);
	public int hashCode();
	public boolean equals(Object obj);
	public String toString();

}