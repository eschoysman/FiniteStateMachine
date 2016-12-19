package fsm.exceptions;

public final class UnexpectedStateException extends StateMachineException {
	private static final long serialVersionUID = -7439643873894760239L;
	public UnexpectedStateException() {
		super();
	}
 	public UnexpectedStateException(String message) {
 		super(message);
 	}
 	public UnexpectedStateException(String message, Throwable cause) {
 		super(message,cause);
 	}
	public UnexpectedStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message,cause,enableSuppression,writableStackTrace);
	}
 	public UnexpectedStateException(Throwable cause) {
 		super(cause);
 	}
}