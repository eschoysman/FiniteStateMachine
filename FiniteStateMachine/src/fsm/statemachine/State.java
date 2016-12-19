package fsm.statemachine;

import java.util.function.Consumer;

import fsm.statemachine.exceptions.StateMachineException;

public class State<C,E> {

	private static int generalId = 0;
	private int id;
	private String name;
	private boolean isFinal = false;
	
	private StateMachineFunction<C,E> enterAction = (f,e,t,c)->{};
	private Consumer<C> executeAction = c->{};
	private StateMachineFunction<C,E> exitAction = (f,e,t,c)->{};

	public State() {
		id = generalId++;
		this.setName("state"+id);
	}
	public State(String name) {
		id = generalId++;
		this.setName(name);
	}
	public State(String name, StateMachineFunction<C,E> enterAction, Consumer<C> executeAction, StateMachineFunction<C,E> exitAction) {
		id = generalId++;
		this.setName(name);
		setEnterAction(enterAction);
		setExecuteAction(executeAction);
		setExitAction(exitAction);
	}

	public State<C,E> setEnterAction(StateMachineFunction<C,E> enterAction) {
		if(enterAction!=null)
			this.enterAction = enterAction;
		return this;
	}
	public State<C,E> setExecuteAction(Consumer<C> executeAction) {
		if(executeAction!=null)
			this.executeAction = executeAction;
		return this;
	}
	public State<C,E> setExitAction(StateMachineFunction<C,E> exitAction) {
		if(exitAction!=null)
			this.exitAction = exitAction;
		return this;
	}
	
	public State<C,E> setActions(StateMachineFunction<C,E> enterAction, Consumer<C> executeAction, StateMachineFunction<C,E> exitAction) {
		return setEnterAction(enterAction).setExecuteAction(executeAction).setExitAction(exitAction);
	}
	
	public State<C,E> setFinal(boolean isFinal) {
		this.isFinal = isFinal;
		return this;
	}
	public boolean isFinal() {
		return this.isFinal;
	}
	
	public void enter(State<C,E> from, Event<E> event, State<C,E> to, C context) throws StateMachineException {
		if(enterAction!=null)
			enterAction.execute(from, event, to, context);
	}
	public void execute(C context) {
		if(executeAction!=null)
			executeAction.accept(context);
	}
	public void exit(State<C,E> from, Event<E> event, State<C,E> to, C context) throws StateMachineException{
		if(exitAction!=null)
			exitAction.execute(from, event, to, context);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "State [name=" + name + ", isFinal=" + isFinal + "]";
	}
	
}
