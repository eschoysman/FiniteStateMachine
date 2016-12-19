package fsm.statemachine.exceptions;

public final class UndefinedEventException extends StateMachineException {
	private static final long serialVersionUID = 6590028160883647984L;
	public UndefinedEventException() { super(); }
 	public UndefinedEventException(String message) { super(message); }
 	public UndefinedEventException(String message, Throwable cause) { super(message,cause); }
	public UndefinedEventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) { super(message,cause,enableSuppression,writableStackTrace); }
 	public UndefinedEventException(Throwable cause) { super(cause); }
}