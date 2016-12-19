package fsm.exceptions;

public final class UnexpectedTransitionException extends StateMachineException {
	private static final long serialVersionUID = 3419665312156217543L;
	public UnexpectedTransitionException() {
		super();
	}
 	public UnexpectedTransitionException(String message) {
 		super(message);
 	}
 	public UnexpectedTransitionException(String message, Throwable cause) {
 		super(message,cause);
 	}
	public UnexpectedTransitionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message,cause,enableSuppression,writableStackTrace);
	}
 	public UnexpectedTransitionException(Throwable cause) {
 		super(cause);
 	}
}