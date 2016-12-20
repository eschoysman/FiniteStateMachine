package fsm.statemachine;

import fsm.statemachine.exceptions.StateMachineException;

@FunctionalInterface
public interface StateMachineFunction<C extends Context,E> {

	public void execute(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException;
	
}
