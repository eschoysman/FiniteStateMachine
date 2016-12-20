package fsm.statemachine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fsm.statemachine.exceptions.StateMachineException;
import fsm.statemachine.exceptions.TransitionException;
import fsm.statemachine.exceptions.UndefinedEventException;
import fsm.statemachine.exceptions.UndefinedStateException;
import fsm.statemachine.exceptions.UnexpectedTransitionException;
import fsm.statemachine.impl.RESULT;
import fsm.statemachine.impl.TRANSITION_ERROR_HANDLING;
import fsm.statemachine.impl.Tuple;

public class StateMachine2<C extends Context,E> {
	
	private IState<C, E> currentState;
	private Set<IState<C,E>> statesPool;
	private Set<Event<E>> eventsPool;
	private Set<ITransition<C,E>> transitionsPool;
	private Map<Tuple<C,E>, ITransition<C,E>> stateMachine;
	private TRANSITION_ERROR_HANDLING handler = TRANSITION_ERROR_HANDLING.ERROR;
	private RESULT result = null;
	
	public StateMachine2() {
		this.statesPool = new HashSet<>();
		this.eventsPool = new HashSet<>();
		this.transitionsPool = new HashSet<>();
		this.stateMachine = new HashMap<Tuple<C,E>,ITransition<C,E>>();
	}
	
	public IState<C,E> addState(IState<C,E> state) {
		return statesPool.add(state) ? state : null;
	}
	public IState<C,E> addStartState(IState<C,E> state) {
		this.currentState = state;
		return addState(state);
	}
	
	public ITransition<C, E> addTransition(ITransition<C,E> transition) throws UndefinedStateException {
		if(getState(transition.getDestination())==null) {
			throw new UndefinedStateException("Destination state not found.");
		}
		boolean ok = transitionsPool.add(transition);
		if(ok) stateMachine.put(new Tuple<C,E>(transition.getFrom(),transition.getEvent()),transition);
		return ok ? transition : null;
	}
	
	public Event<E> addEvent(E event) {
		Event<E> triggeredEvent = new fsm.statemachine.Event<E>(event);
		eventsPool.add(triggeredEvent);
		return triggeredEvent;
	}
	public boolean addEvent(Event<E> event) {
		return eventsPool.add(event);
	}
	public void addEvents(Collection<E> events) throws UndefinedEventException {
		events.forEach(this::addEvent);
	}
	@SuppressWarnings("unchecked")
	public void addEvents(E... events) throws UndefinedEventException {
		for(E event : events)
			addEvent(event);
	}
	
	public StateMachine2<C,E> setTransitionErrorHandler(TRANSITION_ERROR_HANDLING handler) {
		this.handler = handler;
		return this;
	}
	public StateMachine2<C,E> setStartState(IState<C, E> startState) {
		this.currentState = startState;
		return this;
	}
	public StateMachine2<C,E> setFinalState(IState<C, E> finalState) {
		finalState.setFinal(true);
		return this;
	}
	
	public boolean isFinalState() throws UndefinedStateException {
		if(currentState==null) {
			throw new UndefinedStateException("Starting state or current state is not defined!");
		}
		return currentState.isFinal();
	}
	public IState<C, E> getCurrentState() {
		return currentState;
	}
	
	public IState<C, E> fire(E event, C context) throws StateMachineException {
		return fire(getEvent(event),context);
	}
	public IState<C, E> fire(Event<E> event, C context) throws StateMachineException {
		result = RESULT.RUNNING;
		if(currentState==null) {
			result = RESULT.ERROR;
			throw new UndefinedStateException("Starting state or current state is not defined!");
		}
		if(currentState.isFinal()) {
			result = RESULT.ERROR;
			throw new UndefinedStateException("Current state (\""+currentState.getName()+"\") is a final state, no transition are allowed.");
		}
		if(getState(currentState)==null) {
			result = RESULT.ERROR;
			throw new UndefinedStateException("Current state not found.");
		}
		if(getEvent(event)==null) {
			result = RESULT.ERROR;
			throw new UndefinedEventException("Event \""+event.getEvent()+"\" not found.");
		}
		ITransition<C, E> trans = stateMachine.get(new Tuple<C,E>(currentState,event));
		if(trans==null) {
			result = RESULT.ERROR;
			throw new TransitionException("Transition not found from state \""+currentState.getName()+"\" and event \""+event.getEvent()+"\"");
		}
		if(getState(trans.getDestination())==null) {
			result = RESULT.ERROR;
			throw new UndefinedStateException("Destination state not found.");
		}
System.out.println("context start: "+context);
		C contextBak = copy(context);
		IState<C, E> toState = trans.getDestination();
		try {
			trans.fire(currentState,event,toState,context);
			result = RESULT.SUCCESS;
			currentState = toState;
		} catch(UnexpectedTransitionException e) {
			result = RESULT.ERROR;
			TRANSITION_ERROR_HANDLING tmpHandler = trans.getHandler()==null ? handler : trans.getHandler();
			switch(tmpHandler) {
				case ERROR:
					throw e;
				case ROLLBACK:
					System.err.println("Error during transition. TRANSITION_ERROR_HANDLING="+handler+" selected, the transition is rollbacked.");
					context = copy(contextBak);
					break;
				case IGNORE:
					currentState = toState;
					break;
			}
		}
System.out.println("context end: "+context);
		return currentState;
	}
	
	public void executeState(C context) {
		currentState.execute(context);
	}
	
	public IState<C, E> getState(String name) {
		for(IState<C, E> s : statesPool)
			if(s.getName().equals(name))
				return s;
		return null;
	}
	public IState<C, E> getState(IState<C, E> state) {
		for(IState<C, E> s : statesPool)
			if(s.getId()==state.getId() || s.getName().equals(state.getName()))
				return s;
		return null;
	}
	public Event<E> getEvent(E event) {
		for(Event<E> e : eventsPool)
			if(e.getEvent().equals(event))
				return e;
		return null;
	}
	public Event<E> getEvent(Event<E> event) {
		for(Event<E> e : eventsPool)
			if(e.getId()==event.getId() || e.getEvent().equals(event))
				return e;
		return null;
	}
	public List<ITransition<C,E>> getTransitions(IState<C, E> fromState) {
		List<ITransition<C,E>> result = new ArrayList<>();
		for(ITransition<C,E> t : transitionsPool)
			if(t.getFrom().equals(fromState))
				result.add(t);
		return result;
	}

	public Set<ITransition<C,E>> getTransitions() {
		return transitionsPool;
	}
	public Set<Event<E>> getEvents() {
		return eventsPool;
	}
	public Set<IState<C,E>> getStates() {
		return statesPool;
	}
	
	public RESULT getTransitionResult() {
		return result;
	}

	@SuppressWarnings("unchecked")
	private C copy(C input) {
		try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(input);
			byte[] byteData = bos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
			C result = (C) new ObjectInputStream(bais).readObject();
			bais.close();
			return result;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
