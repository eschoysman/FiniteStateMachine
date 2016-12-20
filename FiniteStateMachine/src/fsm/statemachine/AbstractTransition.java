package fsm.statemachine;

import fsm.statemachine.exceptions.StateMachineException;
import fsm.statemachine.impl.TRANSITION_ERROR_HANDLING;
import fsm.statemachine.impl.Transition;

public class AbstractTransition<C extends Context, E> implements ITransition<C,E> {

	private static int generalId = 0;
	private int id;
	private IState<C, E> from;
	private IState<C, E> destination;
	private Event<E> event;
	private TRANSITION_ERROR_HANDLING handler;
	private StateMachineFunction<C,E> enterAction = (f,e,t,c)->{};
	private StateMachineFunction<C,E> executeAction = (f,e,t,c)->{};
	private StateMachineFunction<C,E> exitAction = (f,e,t,c)->{};

	protected AbstractTransition(IState<C, E> from, Event<E> event, IState<C, E> to) {
		setId(generalId++);
		this.from = from;
		this.event = event;
		this.destination = to;
	}
	protected AbstractTransition(IState<C, E> from, Event<E> event, IState<C, E> to, StateMachineFunction<C,E> enterAction, StateMachineFunction<C,E> executeAction, StateMachineFunction<C,E> exitAction, TRANSITION_ERROR_HANDLING handler) {
		setId(generalId++);
		this.from = from;
		this.event = event;
		this.destination = to;
		setEnterAction(enterAction);
		setExecuteAction(executeAction);
		setExitAction(exitAction);
		setExitAction(exitAction);
		setHandler(handler);
	}
	protected AbstractTransition(IState<C, E> from, E event, IState<C, E> to) {
		setId(generalId++);
		this.from = from;
		this.event = new Event<E>(event);
		this.destination = to;
	}
	protected AbstractTransition(IState<C, E> from, E event, IState<C, E> to, StateMachineFunction<C,E> enterAction, StateMachineFunction<C,E> executeAction, StateMachineFunction<C,E> exitAction, TRANSITION_ERROR_HANDLING handler) {
		setId(generalId++);
		this.from = from;
		this.event = new Event<E>(event);
		this.destination = to;
		setEnterAction(enterAction);
		setExecuteAction(executeAction);
		setExitAction(exitAction);
		setExitAction(exitAction);
		setHandler(handler);
	}

	@Override
	public ITransition<C, E> setEnterAction(StateMachineFunction<C,E> enterAction) {
		this.enterAction = enterAction;
		return this;
	}
	@Override
	public ITransition<C, E> setExecuteAction(StateMachineFunction<C,E> executeAction) {
		this.executeAction = executeAction;
		return this;
	}
	@Override
	public ITransition<C, E> setExitAction(StateMachineFunction<C,E> exitAction) {
		this.exitAction = exitAction;
		return this;
	}

	@Override
	public void enter(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException {
		if(enterAction!=null)
			enterAction.execute(from, event, to, context);
	}
	@Override
	public void run(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException {
		if(executeAction!=null)
			executeAction.execute(from, event, to, context);
	}
	@Override
	public void exit(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException {
		if(exitAction!=null)
			exitAction.execute(from, event, to, context);
	}

	@Override
	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}

	@Override
	public IState<C, E> getFrom() {
		return from;
	}

	@Override
	public Event<E> getEvent() {
		return event;
	}

	@Override
	public IState<C, E> getDestination() {
		return this.destination;
	}

	@Override
	public TRANSITION_ERROR_HANDLING getHandler() {
		return handler;
	}

	@Override
	public ITransition<C, E> setHandler(TRANSITION_ERROR_HANDLING handler) {
		this.handler = handler;
		return this;
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
			if (other.getDestination() != null)
				return false;
		} else if (!destination.equals(other.getDestination()))
			return false;
		if (event == null) {
			if (other.getEvent() != null)
				return false;
		} else if (!event.equals(other.getEvent()))
			return false;
		if (from == null) {
			if (other.getFrom() != null)
				return false;
		} else if (!from.equals(other.getFrom()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transition [from=" + from + ", event=" + event + ", destination=" + destination + "]";
	}

}