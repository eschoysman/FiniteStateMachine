package fsm;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import fsm.exceptions.UnexpectedTransitionException;

public class State<C,E> {

	private static int generalId = 0;
	private int id;
	private String name;

//	private Map<Event<E>,Transition<C,E>> transitions;

	public State() {
		id = generalId++;
		this.setName("state"+id);
//		this.transitions = new HashMap<Event<E>,Transition<C,E>>();
	}
	public State(String name) {
		id = generalId++;
		this.setName(name);
//		this.transitions = new HashMap<Event<E>,Transition<C,E>>();
	}
//
//	public final void transit(Event<E> event, Transition<C,E> transition) {
//		transitions.put(event,transition);
//	}
//
//	public final State<C,E> fire(Event<E> event, Context<C> context) throws UnexpectedTransitionException {
//		Transition<C,E> transition = transitions.get(event);
//		if(transition==null) {
//			throw new UnexpectedTransitionException();
//		}
//		State<C,E> destination = transition.getDestination();
//		this.exit(this,event,destination,context);
//		transition.fire(this,event,transition.getDestination(),context);
//		return destination;
//	}

	public void enter(State<C,E> from, Event<E> event, State<C,E> to, Context<C> context) {}
	public void execute(Context<C> context) {}
	public void exit(State<C,E> from, Event<E> event, State<C,E> to, Context<C> context) {}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}