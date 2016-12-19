package fsm;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import fsm.exceptions.StateMachineException;
import fsm.exceptions.UndefinedStateException;
import fsm.exceptions.UnexpectedEventException;
import fsm.exceptions.UnexpectedStateException;
import fsm.exceptions.UnexpectedTransitionException;

public class StateMachine<C,E> {
	
	private State<C,E> currentState;
	private Set<State<C,E>> statesPool;
	private Set<Event<E>> eventsPool;
	private Set<Transition<C,E>> transitionsPool;
	private Map<Tuple<State<C,E>, Event<E>>, Transition<C,E>> stateMachine;

	public StateMachine() {
		this.statesPool = new HashSet<>();
		this.eventsPool = new HashSet<>();
		this.transitionsPool = new HashSet<>();
		this.stateMachine = new HashMap<Tuple<State<C,E>,Event<E>>,Transition<C,E>>();
	}

	public State<C,E> addState() {
		State<C,E> state = new State<C,E>();
		statesPool.add(state);
		return state;
	}
	public State<C,E> addState(String name) {
		State<C,E> state = new State<C,E>(name);
		statesPool.add(state);
		return state;
	}
	public boolean addState(State<C,E> state) {
		return statesPool.add(state);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Transition<C,E> addTransition(State<C,E> from, Event<E> event, State<C,E> to) {
		Transition<C,E> transition = new Transition<C,E>(to);
		transitionsPool.add(transition);
		stateMachine.put(new Tuple(from,event),transition);
		return transition;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean addTransition(State<C,E> from, Event<E> event, Transition<C,E> transition) {
		boolean ok = transitionsPool.add(transition);
		stateMachine.put(new Tuple(from,event),transition);
		return ok;
	}
	public Event<E> addEvent(E event) {
		Event<E> triggeredEvent = new Event<E>(event);
		eventsPool.add(triggeredEvent);
		return triggeredEvent;
	}
	public boolean addEvent(Event<E> event) {
		return eventsPool.add(event);
	}

	public void setStartState(State<C,E> startState) {
		this.currentState = startState;
	}

	public void fire(Event<E> event, Context<C> context) throws StateMachineException {
		if(currentState==null) {
			throw new UndefinedStateException();
		}
		if(!statesPool.contains(currentState)) {
			throw new UnexpectedStateException();
		}
		if(!eventsPool.contains(event)) {
			throw new UnexpectedEventException();
		}
		Transition<C,E> trans = stateMachine.get(new Tuple<C,E>(currentState,event));
		if(trans==null) {
			throw new UnexpectedTransitionException();
		}
		State<C,E> toState = trans.getDestination();
		trans.fire(currentState,event,toState,context);
		currentState = toState;
	}
	
	@SuppressWarnings("hiding")
	private class Tuple<C,E> {
		@SuppressWarnings("unused")
		private State<C,E> state;
		@SuppressWarnings("unused")
		private Event<E> event;
		public Tuple(State<C,E> state,Event<E> event) {
			this.state = state;
			this.event = event;
		}
	}

}
