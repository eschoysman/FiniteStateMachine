package fsm;

@FunctionalInterface
public interface StateMachineFunction<C,E> {

	public void apply(State<C,E> from, Event<E> event, State<C,E> to, C context);
	
}
