package fsm.statemachine.exceptions;

public class StateMachineException extends Exception {
	private static final long serialVersionUID = 5810448403793344691L;
	public StateMachineException() { super(); }
 	public StateMachineException(String message) { super(message); }
 	public StateMachineException(String message, Throwable cause) { super(message,cause); }
	public StateMachineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) { super(message,cause,enableSuppression,writableStackTrace); }
 	public StateMachineException(Throwable cause) { super(cause); }
}