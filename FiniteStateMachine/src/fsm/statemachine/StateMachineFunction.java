package fsm.statemachine;

import fsm.statemachine.exceptions.StateMachineException;

@FunctionalInterface
public interface StateMachineFunction<C,E> {

	public void execute(State<C,E> from, Event<E> event, State<C,E> to, C context) throws StateMachineException;
	
}
