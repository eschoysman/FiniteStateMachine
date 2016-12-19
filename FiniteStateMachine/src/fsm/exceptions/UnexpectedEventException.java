package fsm.exceptions;

public final class UnexpectedEventException extends StateMachineException {
	private static final long serialVersionUID = 6590028160883647984L;
	public UnexpectedEventException() {
		super();
	}
 	public UnexpectedEventException(String message) {
 		super(message);
 	}
 	public UnexpectedEventException(String message, Throwable cause) {
 		super(message,cause);
 	}
	public UnexpectedEventException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message,cause,enableSuppression,writableStackTrace);
	}
 	public UnexpectedEventException(Throwable cause) {
 		super(cause);
 	}
}