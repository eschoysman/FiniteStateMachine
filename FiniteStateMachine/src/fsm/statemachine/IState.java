package fsm.statemachine;

import java.util.function.Consumer;

import fsm.statemachine.exceptions.StateMachineException;

public interface IState<C extends Context,E> {

	public IState<C, E> setEnterAction(StateMachineFunction<C, E> enterAction);
	public IState<C, E> setExecuteAction(Consumer<C> executeAction);
	public IState<C, E> setExitAction(StateMachineFunction<C, E> exitAction);
	public IState<C, E> setActions(StateMachineFunction<C, E> enterAction, Consumer<C> executeAction, StateMachineFunction<C, E> exitAction);
	public IState<C, E> setFinal(boolean isFinal);
	public boolean isFinal();
	public void enter(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException;
	public void execute(C context);
	public void exit(IState<C, E> from, Event<E> event, IState<C, E> to, C context) throws StateMachineException;
	public String getName();
	public IState<C, E> setName(String name);
	public int getId();
	public int hashCode();
	public boolean equals(Object obj);
	public String toString();

}