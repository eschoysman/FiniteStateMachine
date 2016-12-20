package fsm.statemachine;

import java.util.function.Consumer;

import fsm.statemachine.exceptions.StateMachineException;
import fsm.statemachine.impl.State;

public class AbstractState<C extends Context,E> implements IState<C,E> {

	protected static int generalId = 0;
	protected int id;
	private String name;
	private boolean isFinal = false;
	private StateMachineFunction<C,E> enterAction;
	private Consumer<C> executeAction;
	private StateMachineFunction<C,E> exitAction;

	protected AbstractState() {
		id = generalId++;
		this.setName("state"+id);
	}
	protected AbstractState(String name) {
		id = generalId++;
		this.setName(name);
	}
	protected AbstractState(String name, StateMachineFunction<C,E> enterAction, Consumer<C> executeAction, StateMachineFunction<C,E> exitAction) {
		id = generalId++;
		this.setName(name);
		setEnterAction(enterAction);
		setExecuteAction(executeAction);
		setExitAction(exitAction);
	}

	@Override
	public IState<C, E> setEnterAction(StateMachineFunction<C,E> enterAction) {
		this.enterAction = enterAction;
		return this;
	}
	@Override
	public IState<C, E> setExecuteAction(Consumer<C> executeAction) {
		this.executeAction = executeAction;
		return this;
	}
	@Override
	public IState<C, E> setExitAction(StateMachineFunction<C,E> exitAction) {
		this.exitAction = exitAction;
		return this;
	}
	@Override
	public IState<C, E> setActions(StateMachineFunction<C,E> enterAction, Consumer<C> executeAction, StateMachineFunction<C,E> exitAction) {
		return setEnterAction(enterAction).setExecuteAction(executeAction).setExitAction(exitAction);
	}
	
	@Override
	public IState<C, E> setFinal(boolean isFinal) {
		this.isFinal = isFinal;
		return this;
	}
	@Override
	public boolean isFinal() {
		return this.isFinal;
	}

	@Override
	public void enter(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException {
		if(enterAction!=null)
			enterAction.execute(from, event, to, context);
	}
	@Override
	public void execute(C context) {
		if(executeAction!=null)
			executeAction.accept(context);
	}
	@Override
	public void exit(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException {
		if(exitAction!=null)
			exitAction.execute(from, event, to, context);
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public IState<C, E> setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		State other = (State) obj;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "State [name=" + name + ", isFinal=" + isFinal + "]";
	}

}