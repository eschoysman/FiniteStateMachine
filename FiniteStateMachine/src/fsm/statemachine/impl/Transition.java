package fsm.statemachine.impl;

import fsm.statemachine.AbstractTransition;
import fsm.statemachine.Context;
import fsm.statemachine.Event;
import fsm.statemachine.IState;
import fsm.statemachine.StateMachineFunction;

public class Transition<C extends Context,E> extends AbstractTransition<C, E> {

	public Transition(IState<C, E> from, E event, IState<C, E> to, StateMachineFunction<C, E> enterAction,
			StateMachineFunction<C, E> executeAction, StateMachineFunction<C, E> exitAction,
			TRANSITION_ERROR_HANDLING handler) {
		super(from, event, to, enterAction, executeAction, exitAction, handler);
	}

	public Transition(IState<C, E> from, E event, IState<C, E> to) {
		super(from, event, to);
	}

	public Transition(IState<C, E> from, Event<E> event, IState<C, E> to, StateMachineFunction<C, E> enterAction,
			StateMachineFunction<C, E> executeAction, StateMachineFunction<C, E> exitAction,
			TRANSITION_ERROR_HANDLING handler) {
		super(from, event, to, enterAction, executeAction, exitAction, handler);
	}

	public Transition(IState<C, E> from, Event<E> event, IState<C, E> to) {
		super(from, event, to);
	}


	
}
