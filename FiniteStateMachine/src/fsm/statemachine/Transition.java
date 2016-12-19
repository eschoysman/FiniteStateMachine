package fsm.statemachine;

import fsm.statemachine.exceptions.StateMachineException;
import fsm.statemachine.exceptions.UnexpectedTransitionException;

public class Transition<C,E> {
	private static int generalId = 0;
	private int id;
	private State<C,E> from,destination;
	private Event<E> event;
	
	private StateMachineFunction<C,E> enterAction = (f,e,t,c)->{};
	private StateMachineFunction<C,E> executeAction = (f,e,t,c)->{};
	private StateMachineFunction<C,E> exitAction = (f,e,t,c)->{};

	public Transition(State<C,E> from, Event<E> event, State<C,E> to) {
		setId(generalId++);
		this.from = from;
		this.event = event;
		this.destination = to;
	}
	public Transition(State<C,E> from, Event<E> event, State<C,E> to, StateMachineFunction<C,E> enterAction, StateMachineFunction<C,E> executeAction, StateMachineFunction<C,E> exitAction) {
		setId(generalId++);
		this.from = from;
		this.event = event;
		this.destination = to;
		setEnterAction(enterAction);
		setExecuteAction(executeAction);
		setExitAction(exitAction);
	}
	
	public Transition<C,E> setEnterAction(StateMachineFunction<C,E> enterAction) {
		if(enterAction!=null)
			this.enterAction = enterAction;
		return this;
	}
	public Transition<C,E> setExecuteAction(StateMachineFunction<C,E> executeAction) {
		if(executeAction!=null)
			this.executeAction = executeAction;
		return this;
	}
	public Transition<C,E> setExitAction(StateMachineFunction<C,E> exitAction) {
		if(exitAction!=null)
			this.exitAction = exitAction;
		return this;
	}
	
	public Transition<C,E> setActions(StateMachineFunction<C,E> enterAction, StateMachineFunction<C,E> executeAction, StateMachineFunction<C,E> exitAction) {
		return setEnterAction(enterAction).setExecuteAction(executeAction).setExitAction(exitAction);
	}
	
	public final void fire(State<C,E> from, Event<E> event, State<C,E> to, C context) throws UnexpectedTransitionException {
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
	public void enter(State<C,E> from, Event<E> event, State<C,E> to, C context) throws StateMachineException {
		if(enterAction!=null)
			enterAction.execute(from, event, to, context);
	}
	public void run(State<C,E> from, Event<E> event, State<C,E> to, C context) throws StateMachineException {
		if(executeAction!=null)
			executeAction.execute(from, event, to, context);
	}
	public void exit(State<C,E> from, Event<E> event, State<C,E> to, C context) throws StateMachineException {
		if(exitAction!=null)
			exitAction.execute(from, event, to, context);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public State<C,E> getFrom() {
		return from;
	}
	public Event<E> getEvent() {
		return event;
	}
	public State<C,E> getDestination() {
		return this.destination;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
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
		Transition other = (Transition) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Transition [from=" + from + ", event=" + event + ", destination=" + destination + "]";
	}
	
}
