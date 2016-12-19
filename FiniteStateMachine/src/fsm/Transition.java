package fsm;

public class Transition<C,E> {
	private static int generalId = 0;
	private int id;
	private State<C,E> destination;

	private State<C,E> from, to;

	public Transition(State<C,E> destination) {
		setId(generalId++);
		this.destination = destination;
	}
	
	public State<C,E> getDestination() {
		return this.destination;
	}

	public final void fire(State<C,E> from, Event<E> event, State<C,E> to, Context<C> context) {
		from.exit(from, event, to, context);
		this.enter(from,event,to,context);
		this.run(from,event,to,context);
		this.exit(from,event,to,context);
		to.enter(from,event,to,context);
	}
	public final void enter(State<C,E> from, Event<E> event, State<C,E> to, Context<C> context) {}
	public final void run(State<C,E> from, Event<E> event, State<C,E> to, Context<C> context) {}
	public final void exit(State<C,E> from, Event<E> event, State<C,E> to, Context<C> context) {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public State<C,E> getFrom() {
		return from;
	}
	public void setFrom(State<C,E> from) {
		this.from = from;
	}

	public State<C,E> getTo() {
		return to;
	}
	public void setTo(State<C,E> to) {
		this.to = to;
	}

}
