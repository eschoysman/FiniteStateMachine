package fsm.statemachine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import fsm.statemachine.exceptions.*;

public class StateMachine<C,E> {
	
	public enum TRANSITION_ERROR_HANDLING {ERROR,IGNORE}
	public enum RESULT {SUCCESS,RUNNING,ERROR}
	
	private State<C,E> currentState;
	private Set<State<C,E>> statesPool;
	private Set<Event<E>> eventsPool;
	private Set<Transition<C,E>> transitionsPool;
	private Map<Tuple<State<C,E>, Event<E>>, Transition<C,E>> stateMachine;
	private TRANSITION_ERROR_HANDLING handler = TRANSITION_ERROR_HANDLING.ERROR;
	private RESULT result = null;
	
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
	public State<C,E> addStartState() {
		State<C,E> state = new State<C,E>();
		statesPool.add(state);
		this.currentState = state;
		return state;
	}
	public State<C,E> addState(String name) {
		return addState(name,null,null,null);
	}
	public State<C,E> addStartState(String name) {
		return addStartState(name,null,null,null);
	}
	public boolean addState(State<C,E> state) {
		return statesPool.add(state);
	}
	public boolean addStartState(State<C,E> state) {
		this.currentState = state;
		return statesPool.add(state);
	}
	public State<C,E> addState(String name, StateMachineFunction<C,E> enterAction, Consumer<C> executeAction, StateMachineFunction<C,E> exitAction) {
		State<C,E> state = new State<C,E>(name,enterAction,executeAction,exitAction);
		statesPool.add(state);
		return state;
	}
	public State<C,E> addStartState(String name, StateMachineFunction<C,E> enterAction, Consumer<C> executeAction, StateMachineFunction<C,E> exitAction) {
		State<C,E> state = new State<C,E>(name,enterAction,executeAction,exitAction);
		statesPool.add(state);
		this.currentState = state;
		return state;
	}
	
	public Transition<C,E> addTransition(State<C,E> from, Event<E> event, State<C,E> to) throws UndefinedStateException {
		return addTransition(from, event, to, null, null, null);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Transition<C,E> addTransition(State<C,E> from, Event<E> event, State<C,E> to, StateMachineFunction<C,E> enterAction, StateMachineFunction<C,E> executeAction, StateMachineFunction<C,E> exitAction) throws UndefinedStateException {
		if(getState(to)==null) {
			throw new UndefinedStateException("Destination state not found.");
		}
		Transition<C,E> transition = new Transition<C,E>(from,event,to,enterAction,executeAction,exitAction);
		transitionsPool.add(transition);
		stateMachine.put(new Tuple(from,event),transition);
		return transition;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean addTransition(Transition<C,E> transition) {
		boolean ok = transitionsPool.add(transition);
		if(ok) stateMachine.put(new Tuple(transition.getFrom(),transition.getEvent()),transition);
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
	
	public void setTransitionErrorHandler(TRANSITION_ERROR_HANDLING handler) {
		this.handler = handler;
	}
	public void setStartState(State<C,E> startState) {
		this.currentState = startState;
	}
	public void setFinalState(State<C,E> finalState) {
		finalState.setFinal(true);
	}
	
	public boolean isFinalState() throws UndefinedStateException {
		if(currentState==null) {
			throw new UndefinedStateException("Starting state or current state is not defined!");
		}
		return currentState.isFinal();
	}
	public State<C,E> getCurrentState() {
		return currentState;
	}
	
	public State<C,E> fire(E event, C context) throws StateMachineException {
		return fire(getEvent(event),context);
	}
	public State<C,E> fire(Event<E> event, C context) throws StateMachineException {
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
		Transition<C,E> trans = stateMachine.get(new Tuple<C,E>(currentState,event));
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
		State<C,E> toState = trans.getDestination();
		try {
			trans.fire(currentState,event,toState,context);
			result = RESULT.SUCCESS;
		} catch(UnexpectedTransitionException e) {
			if(handler == TRANSITION_ERROR_HANDLING.ERROR) {
				result = RESULT.ERROR;
				throw e;
			}
			else {
				result = RESULT.ERROR;
				System.err.println("Error during transition. TRANSITION_ERROR_HANDLING="+handler+" selected, the transition is ignored and the transition is rollbacked.");
				toState = currentState;
			}
		} finally {
			context = copy(contextBak);
			currentState = toState;
		}
System.out.println("context end: "+context);
		return currentState;
	}
	
	public void executeState(C context) {
		currentState.execute(context);
	}
	
	public State<C,E> getState(String name) {
		for(State<C,E> s : statesPool)
			if(s.getName().equals(name))
				return s;
		return null;
	}
	public State<C,E> getState(State<C,E> state) {
		for(State<C,E> s : statesPool)
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
	public List<Transition<C,E>> getTransitions(State<C,E> fromState) {
		List<Transition<C,E>> result = new ArrayList<>();
		for(Transition<C,E> t : transitionsPool)
			if(t.getFrom().equals(fromState))
				result.add(t);
		return result;
	}

	public Event<E> getTransitionEvent(State<C,E> a, State<C,E> b) {
		if(getState(a)==null || getState(b)==null)
			return null;
		for(Event<E> e : eventsPool) {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Tuple<C,E> tmp = new Tuple(a,e);
			if(stateMachine.get(tmp)!=null && stateMachine.get(tmp).getDestination().equals(b))
				return e;
		}
		return null;
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
