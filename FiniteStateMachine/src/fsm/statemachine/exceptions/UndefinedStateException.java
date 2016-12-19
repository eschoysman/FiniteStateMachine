package fsm.statemachine.exceptions;

public final class UndefinedStateException extends StateMachineException {
	private static final long serialVersionUID = 4235198218752006515L;
	public UndefinedStateException() { super(); }
 	public UndefinedStateException(String message) { super(message); }
 	public UndefinedStateException(String message, Throwable cause) { super(message,cause); }
	public UndefinedStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) { super(message,cause,enableSuppression,writableStackTrace); }
 	public UndefinedStateException(Throwable cause) { super(cause); }
}