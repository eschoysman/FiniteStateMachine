package fsm;

public class Context<C> {

	private C context;

	public Context(C context) {
		this.context = context;
	}

	public C getContext() {
		return this.context;
	}

	public void setContext(C context) {
		this.context = context;
	}

}