package fsm.statemachine.exceptions;

public final class TransitionException extends StateMachineException {
	private static final long serialVersionUID = -881483771563660160L;
	public TransitionException() { super(); }
 	public TransitionException(String message) { super(message); }
 	public TransitionException(String message, Throwable cause) { super(message,cause); }
	public TransitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) { super(message,cause,enableSuppression,writableStackTrace); }
 	public TransitionException(Throwable cause) { super(cause); }
}