package fsm.statemachine.impl;

import java.util.function.Consumer;

import fsm.statemachine.AbstractState;
import fsm.statemachine.Context;
import fsm.statemachine.StateMachineFunction;

public class State<C extends Context,E> extends AbstractState<C, E> {

	public State() {
		super();
	}

	public State(String name, StateMachineFunction<C, E> enterAction, Consumer<C> executeAction,
			StateMachineFunction<C, E> exitAction) {
		super(name, enterAction, executeAction, exitAction);
	}

	public State(String name) {
		super(name);
	}

}
